package com.example.shubhangi.quiz_assign3;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder>
{

    public static String key="QUESTION";
    Context context;

    ArrayList<String> arrayList = new ArrayList<>();
    public ListAdapter(Context context,ArrayList<String> arrayList)
    {
        this.arrayList=arrayList;
        this.context=context;

    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items,parent,false);

        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder viewHolder, int position) {

        final String ques=arrayList.get(position);
        viewHolder.item.setText(ques);
        ((ListViewHolder) viewHolder).bindview(position);

        viewHolder.item.setOnClickListener((new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        FragmentManager fragmentManager=((Activity) context).getFragmentManager();
                        FragmentTransaction ft= fragmentManager.beginTransaction();
                        Bundle bundle=new Bundle();
                        bundle.putString("KEY",ques);
                        BlankFragment blankFragment=new BlankFragment();
                        blankFragment.setArguments(bundle);
                        ft.replace(R.id.frame,blankFragment);
                        ft.addToBackStack(null);
                        ft.commit();
                    }
                })

        );

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView item;

        public ListViewHolder(View itemview)
        {
            super(itemview);
            item= (TextView) itemview.findViewById(R.id.tv1);
            itemview.setOnClickListener(this);
        }

        public void bindview(int position){

            //textView.setText(questions.title[position]);


        }

        public void onClick(View view)
        {


        }
    }

}