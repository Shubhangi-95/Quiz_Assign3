package com.example.shubhangi.quiz_assign3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class questions extends SQLiteOpenHelper{
    public static final String DB_NAME="Ques.db";
    public static final String TABLE_NAME="ques_table";
    public static final String COL_1="ID";

    public static final String COL_2="QUES";

    public static final String COL_3="ANS";

    public questions(Context context)
    {
        super(context,DB_NAME,null,1);

    }

    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table IF NOT EXISTS "+ TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,QUES TEXT,ANS TEXT) ");
    }

    public void onUpgrade(SQLiteDatabase db,int oldversion,int newversion)
    {
        // db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        // onCreate(db);
    }

    public boolean insertData(int ID,String QUES,String ANS)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COL_1,ID);

        contentValues.put(COL_2,QUES);
        contentValues.put(COL_3,ANS);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }

    public boolean isempty()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String cnt= "SELECT count(*) FROM " + TABLE_NAME;

        Cursor c =db.rawQuery(cnt,null);
        c.moveToFirst();
        int icnt=c.getInt(0 );

        if(icnt>0)
            return false;
        else
            return true;

    }

    public boolean updatetable(String ques,String ans)
    {
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_3,ans);
        database.update(TABLE_NAME,contentValues,"QUES = ?",new String[] {ques});
        return true;
    }
    public static String[] title= new String[] {
            "Question 1 is how are you",
            "Question 2",
            "Question 3",
            "Question 1 is how are you",
            "Question 2",
            "Question 3",
            "Question 1 is how are you",
            "Question 2",
            "Question 3",
            "Question 1 is how are you",
            "Question 2",
            "Question 3"
    };



}
