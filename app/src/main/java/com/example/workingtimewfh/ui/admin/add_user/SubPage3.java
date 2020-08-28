package com.example.workingtimewfh.ui.admin.add_user;

import android.os.Bundle;
import android.view.LayoutInflater; import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workingtimewfh.R;

import java.util.ArrayList;

public class SubPage3 extends Fragment implements AdapterView.OnItemSelectedListener {

    Sub3Adapter sub3Adapter;
    RecyclerView Result_education;
    ArrayList<subDatapage3> item_all = new ArrayList<>();
    ArrayList<Integer> seq_level = new ArrayList<>();
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

        Spinner spinner = rootView.findViewById(R.id.educateLevel);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.education, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        Result_education = rootView.findViewById(R.id.list_education);
        add = rootView.findViewById(R.id.button3);

        sub3Adapter = new Sub3Adapter(item_all);
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
                Result_education.setLayoutManager(new LinearLayoutManager(getActivity()));
                Result_education.setAdapter(sub3Adapter);
            }
        });


        return rootView;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        level = parent.getItemAtPosition(position).toString();
        seq = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }


    private class Sub3Adapter extends  RecyclerView.Adapter<ViewHolder>{
        ArrayList<subDatapage3> item;
        public Sub3Adapter(ArrayList<subDatapage3> item) {
            this.item = item;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.subpage3_sub,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            subDatapage3 a = item.get(position);
            holder.education_level.setText(a.getLevel());
            holder.education_name.setText(a.getName());
            holder.education_year.setText(""+a.getYear());
            holder.education_grade.setText(""+a.getGrade());
        }

        @Override
        public int getItemCount() {
            return item.size();
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        TextView education_level;
        TextView education_name;
        TextView education_year;
        TextView education_grade;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            education_level = itemView.findViewById(R.id.education_level);
            education_name = itemView.findViewById(R.id.education_name);
            education_year = itemView.findViewById(R.id.education_year);
            education_grade = itemView.findViewById(R.id.education_grade);

            ((Button)itemView.findViewById(R.id.delete)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(),"asdf"+itemView.getParent(),Toast.LENGTH_SHORT).show();
                }
            });


        }
    }
}