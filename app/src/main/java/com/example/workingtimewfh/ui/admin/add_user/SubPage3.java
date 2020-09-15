package com.example.workingtimewfh.ui.admin.add_user;

import android.icu.text.DecimalFormat;
import android.icu.text.Transliterator;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater; import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workingtimewfh.R;

import java.nio.file.attribute.PosixFileAttributes;
import java.util.ArrayList;
import java.util.Collection;

public class SubPage3 extends Fragment implements AdapterView.OnItemSelectedListener {

    Sub3Adapter sub3Adapter;
    RecyclerView Result_education;
    ArrayList<subDatapage3> item_all = new ArrayList<>();
    ArrayList<Integer> seq_level = new ArrayList<>();
    CheckBox NoneEducation;

    int seq;
    String level;
    Button add;


    public static SubPage3 newInstance() {
        SubPage3 fragment = new SubPage3();
        return fragment;
    }
    public SubPage3() { }



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.subpage3, container, false);
        NoneEducation = rootView.findViewById(R.id.none_education);

        Spinner spinner = rootView.findViewById(R.id.educateLevel);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.education, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        Result_education = rootView.findViewById(R.id.list_education);
        add = rootView.findViewById(R.id.button3);

        sub3Adapter = new Sub3Adapter(item_all);
        sub3Adapter.setOnClickListener(new Sub3Adapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                removeItem(position);
            }
        });

        Result_education.setLayoutManager(new LinearLayoutManager(getActivity()));
        Result_education.setAdapter(sub3Adapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((EditText)rootView.findViewById(R.id.addschool)).getText().toString();
                String year = ((EditText)rootView.findViewById(R.id.year)).getText().toString();
                String grade = ((EditText)rootView.findViewById(R.id.gpa)).getText().toString();

                int a=0;
                double b=0;
                if(name.isEmpty()){
                    name = "-";
                }
                if(year.isEmpty()){
                   a=0;
                }
                if(grade.isEmpty()){
                    b=0;
                }
                if(!name.isEmpty()&&!year.isEmpty()&&!grade.isEmpty()){
                    a = Integer.valueOf(year);
                    b = Double.valueOf(grade);
                }
                item_all.add(new subDatapage3(level,name,a,b));

                ((EditText)rootView.findViewById(R.id.addschool)).setText(null);
                ((EditText)rootView.findViewById(R.id.year)).setText(null);
                ((EditText)rootView.findViewById(R.id.gpa)).setText(null);

                seq_level.add(seq);

                for(int i=0;i<seq_level.size();i++){
                    for(int j=0;j<seq_level.size();j++){
                        if(i != j){
                            if(seq_level.get(i) < seq_level.get(j)){
                                int tmp = seq_level.get(i);
                                seq_level.set(i,seq_level.get(j));
                                seq_level.set(j,tmp);

                                subDatapage3 temp = item_all.get(i);
                                item_all.set(i,item_all.get(j));
                                item_all.set(j,temp);
                            }
                        }
                    }
                }

                sub3Adapter = new Sub3Adapter(item_all);
                sub3Adapter.setOnClickListener(new Sub3Adapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(int position) {
                        removeItem(position);
                    }
                });
                Result_education.setLayoutManager(new LinearLayoutManager(getActivity()));
                Result_education.setAdapter(sub3Adapter);

            }
        });


        NoneEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) NoneEducation).isChecked();
                if(checked){
                    ((Button)rootView.findViewById(R.id.button3)).setEnabled(false);
                    while(!item_all.isEmpty() && !seq_level.isEmpty())
                        removeItem(0);


                }else {
                    ((Button)rootView.findViewById(R.id.button3)).setEnabled(true);
                }
            }
        });

        return rootView;
    }

    public void removeItem(int position){
        item_all.remove(position);
        seq_level.remove(position);
        sub3Adapter.notifyItemRemoved(position);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        level = parent.getItemAtPosition(position).toString();
        seq = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }










}