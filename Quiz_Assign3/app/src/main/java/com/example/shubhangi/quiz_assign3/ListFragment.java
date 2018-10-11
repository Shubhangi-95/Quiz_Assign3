package com.example.shubhangi.quiz_assign3;

import android.app.Fragment;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class ListFragment extends Fragment{

    ArrayList<String> arrayList = new ArrayList<>();
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup cotainer, Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_list,cotainer,false);
        RecyclerView recyclerView= (RecyclerView) view.findViewById(R.id.recycler_view);



        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        questions mydb=new questions(getActivity());
        SQLiteDatabase db= mydb.getReadableDatabase();


        String[] questionarray ={questions.COL_2};
        Cursor cursor=db.query(questions.TABLE_NAME,questionarray,null,null,null,null,null);
        cursor.moveToFirst();

        do {

            String temp= cursor.getString(0);
            arrayList.add(temp);


        }while(cursor.moveToNext());


        mydb.close();
        cursor.close();
        ListAdapter listAdapter= new ListAdapter(getActivity(),arrayList);
        recyclerView.setAdapter(listAdapter);

        return view;


    }

}
