package com.example.shubhangi.quiz_assign3;

import android.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class BlankFragment extends Fragment {
    RadioButton true1,false1;
    Button save;
    String choice,quest;
    questions mydb;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        Bundle bundle = getArguments();
        quest = bundle.getString("KEY");
        TextView tv = (TextView) view.findViewById(R.id.textview);
        tv.setText(quest);
        mydb= new questions(getActivity());

        true1 = (RadioButton) view.findViewById(R.id.true1);
        false1 = (RadioButton) view.findViewById(R.id.false1);
        save = (Button) view.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (true1.isChecked()) {
                    choice = true1.getText().toString();

                }
                else if (false1.isChecked()) {
                    choice = false1.getText().toString();

                }
                Toast.makeText(getActivity(), choice, Toast.LENGTH_LONG).show();
                boolean flag=mydb.updatetable(quest,choice);
                if(flag==true)

                    Toast.makeText(getActivity(),"UPDATED",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getActivity(),"NOT UPDATED",Toast.LENGTH_LONG).show();

            }
        });

        return view;
    }


}