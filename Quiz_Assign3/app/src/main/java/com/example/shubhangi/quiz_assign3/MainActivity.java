package com.example.shubhangi.quiz_assign3;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.opencsv.CSVWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import static com.example.shubhangi.quiz_assign3.questions.TABLE_NAME;
public class MainActivity extends AppCompatActivity
{

    questions mydb;
    String path=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mydb = new questions(getApplicationContext());
        if(mydb.isempty())
        {
            mydb.insertData(1,"1. The Language that the computer can understand is called Machine Language.","null");
            mydb.insertData(2,"2. Magnetic Tape used random access method.","null");
            mydb.insertData(3,"3. Twitter is an online social networking and blogging service.","null");
            mydb.insertData(4,"4. Worms and trojan horses are easily detected and eliminated by antivirus software.","null");
            mydb.insertData(5,"5. Dot-matrix, Deskjet, Inkjet and Laser are all types of Printers.","null");
            mydb.insertData(6,"6. GNU / Linux is a open source operating system.","null");
            mydb.insertData(7,"7. Whaling / Whaling attack is a kind of phishing attacks that target senior executives and other high profile to access valuable information.","null");
            mydb.insertData(8,"8. Freeware is software that is available for use at no monetary cost.","null");
            mydb.insertData(9,"9. IPv6 Internet Protocol address is represented as eight groups of four Octal digits.","null");
            mydb.insertData(10,"10.The hexadecimal number system contains digits from 1 - 15.","null");
            mydb.insertData(11,"11.Octal number system contains digits from 0 - 7.","null");
            mydb.insertData(12,"12.MS Word is a hardware.","null");
            mydb.insertData(13,"13.CPU controls only input data of computer.","null");
            mydb.insertData(14,"14.CPU stands for Central Performance Unit.","null");
            mydb.insertData(15,"15.When you include multiple addresses in a message, you should separate each address with a period (.)","null");
            mydb.insertData(16,"16.You cannot format text in an e-mail message.","null");
            mydb.insertData(17,"17.You must include a subject in any mail message you compose.","null");
            mydb.insertData(18,"18.If you want to respond to the sender of a message, click the Respond button.","null");
            mydb.insertData(19,"19.You type the body of a reply the same way you would type the body of a new message.","null");
            mydb.insertData(20,"20.When you reply to a message, you need to enter the text in the Subject: field.","null");
            mydb.insertData(21,"21.You can only print one copy of a selected message. ","null");
            mydb.insertData(22,"22.You cannot preview a message before you print it.","null");
            mydb.insertData(23,"23.There is only one way to print a message.","null");
            mydb.insertData(24,"24.When you print a message, it is automatically deleted from your Inbox. ","null");
            mydb.insertData(25,"25.You need to delete a contact and creat a new one to change contact information.","null");
            mydb.insertData(26,"26.You must complete all fields in the Contact form before you can save the contact.","null");
            mydb.insertData(27,"27.You cannot edit Contact forms.","null");
            mydb.insertData(28,"28.You should always open and attachment before saving it.","null");
            mydb.insertData(29,"29.All attachment are safe.","null");
            boolean result=mydb.insertData(30,"30.It is impossible to send a worm or virus over the Internet using in attachment.","null");
            if(result==true)
            {
                Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(MainActivity.this,"Empty",Toast.LENGTH_LONG).show();
            }
        }
        ArrayList<String> arrayList = new ArrayList<>();
        ListAdapter ad = new ListAdapter(this,arrayList);
        ListFragment fragment=new ListFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame,fragment);
        fragmentTransaction.commit();
    }
    public void csvsub(View view)
    {
        questions mydb = new questions(getApplicationContext());
        File exportDir = new File(path);
        Toast.makeText(getApplicationContext(),exportDir.getPath(),Toast.LENGTH_LONG).show();
        if(!exportDir.exists())
        {
            exportDir.mkdirs();
        }

        File file = new File(exportDir,"data.csv");
        try
        {
            file.createNewFile();
            CSVWriter csvWriter = new CSVWriter(new FileWriter(file));
            SQLiteDatabase mydbase = mydb.getReadableDatabase();
            Cursor cursor =mydbase.rawQuery("SELECT * FROM " + TABLE_NAME , null);
            csvWriter.writeNext(cursor.getColumnNames());
            while(cursor.moveToNext())
            {
                String arr[] = {cursor.getString(0),cursor.getString(1),cursor.getString(2)};
                csvWriter.writeNext(arr);
            }
            ContentValues contentValues= new ContentValues();
            String x=null;
            contentValues.put(questions.COL_3,x);
            mydbase.update(TABLE_NAME,contentValues,null,null);
            csvWriter.close();
            cursor.close();
            ConnectivityManager connectivityManager = (ConnectivityManager)
                    this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                // INTERNET CONNECTED
                Toast.makeText(this,"Internet Available",Toast.LENGTH_LONG).show();
                // String uri=file.getAbsolutePath();
                UploadED up=new UploadED();
                up.execute();
            }
            else {
                Toast.makeText(this,"No Internet",Toast.LENGTH_LONG).show();
                // INTERNET NOT CONNECTED
            }
        }
        catch(Exception sqlEx)
        {
            Log.e("MainActivity",sqlEx.getMessage(),sqlEx);
        }
    }

    private class UploadED extends AsyncTask<String,Integer,String>
    {

        ProgressDialog proDialog;


        @Override
        protected void onPreExecute() {
            proDialog = new ProgressDialog(MainActivity.this);
            this.proDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            this.proDialog.setTitle("  Uploading    ");
            this.proDialog.show();

        }
        protected void onPostExecute(String v) {
            super.onPostExecute(v);
            Log.e("Response", "Response from server: "+v);
            this.proDialog.dismiss();
            Toast.makeText(getApplicationContext(),"File uploaded!!",Toast.LENGTH_SHORT).show();        }


        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            this.proDialog.setProgress((values[0]));
            Log.d("PROG", ""+values[0]);
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                String sourceFileUri = path+"/data.csv";
                System.out.println("Path found"+sourceFileUri);
                HttpURLConnection conn = null;
                DataOutputStream dos = null;
                String lineEnd = "\r\n";
                String twoHyphens = "--";
                String boundary = "*****";
                int br, bA, bufferSize;
                byte[] buffer;
                int maxBufferSize = 1024 * 1024;
                File sourceFile = new File(sourceFileUri);

                if (sourceFile.isFile()) {

                    try {
                        String upLoadServerUri = "https://shubhangi18135.000webhostapp.com/UploadToServer.php";
                        //System.out.println("YEHI path H BHAI");

                        FileInputStream fileInputStream = new FileInputStream(
                                sourceFile);
                        URL url = new URL(upLoadServerUri);

                        // Open a HTTP connection to the URL
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setDoInput(true);
                        conn.setDoOutput(true);
                        conn.setUseCaches(false);
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Connection", "Keep-Alive");
                        conn.setRequestProperty("ENCTYPE",
                                "multipart/form-data");
                        conn.setRequestProperty("Content-Type",
                                "multipart/form-data;boundary=" + boundary);
                        conn.setRequestProperty("uploaded_file", sourceFileUri);
                        dos = new DataOutputStream(conn.getOutputStream());
                        dos.writeBytes(twoHyphens + boundary + lineEnd);
                        dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                                + sourceFileUri + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        bA = fileInputStream.available();
                        bufferSize = Math.min(bA, maxBufferSize);
                        buffer = new byte[bufferSize];
                        br = fileInputStream.read(buffer, 0, bufferSize);
                        while (br > 0) {
                            dos.write(buffer, 0, bufferSize);
                            bA = fileInputStream.available();
                            bufferSize = Math
                                    .min(bA, maxBufferSize);
                            br = fileInputStream.read(buffer, 0,
                                    bufferSize);
                        }
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + twoHyphens
                                + lineEnd);
                        fileInputStream.close();
                        dos.flush();
                        dos.close();

                    } catch (Exception e) {

                    }
                }
            } catch (Exception ex) {
            }
            return "Uploaded!!";
        }

    }
}
