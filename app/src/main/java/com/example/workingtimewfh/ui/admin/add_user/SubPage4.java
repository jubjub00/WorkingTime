package com.example.workingtimewfh.ui.admin.add_user;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater; import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workingtimewfh.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SubPage4 extends Fragment implements DatePickerDialog.OnDateSetListener, View.OnClickListener {

    ArrayList<subDatapage4> experience= new ArrayList<>();
    RecyclerView ShowResult;
    Sub4Adapter subpage4Adapter;
    Button add,add_start,add_end;
    int selected=0;
    Date StartDay=null,EndDay=null;
    String SD=null,ED=null;
    View rootView;


    public static SubPage4 newInstance() {
        SubPage4 fragment = new SubPage4();
        return fragment;
    }
    public SubPage4() { }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.subpage4, container, false);


        ShowResult = rootView.findViewById(R.id.list_work);
        add = rootView.findViewById(R.id.button3);
        add_start = rootView.findViewById(R.id.HW_start);
        add_end = rootView.findViewById(R.id.HW_end);
        add.setOnClickListener(this);
        add_start.setOnClickListener(this);
        add_end.setOnClickListener(this);


        subpage4Adapter = new Sub4Adapter(experience);
        ShowResult.setLayoutManager(new LinearLayoutManager(getActivity()));
        subpage4Adapter.setOnClickListener(new Sub4Adapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                removeItem(position);
            }
        });
        ShowResult.setAdapter(subpage4Adapter);



        return rootView;
    }

    public void removeItem(int position){
        experience.remove(position);
        subpage4Adapter.notifyItemRemoved(position);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.HW_start:
                selected = 0;
                ShowDatePicker();
                break;
            case R.id.HW_end:
                selected = 1;
                ShowDatePicker();
                break;
            case R.id.button3:
                String company = ((EditText)rootView.findViewById(R.id.addlocation)).getText().toString();
                String pos = ((EditText)rootView.findViewById(R.id.addposition)).getText().toString();
                String s = ((Button)rootView.findViewById(R.id.HW_start)).getText().toString();
                String e = ((Button)rootView.findViewById(R.id.HW_end)).getText().toString();

                if(s.compareTo("เลือกวันที่")==0 || e.compareTo("เลือกวันที่")==0) {
                    Toast.makeText(getActivity(),"โปรดเลือกวันที่",Toast.LENGTH_SHORT).show();
                    return;
                }


                SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd");
                try {
                    StartDay = sdf.parse(SD);
                    EndDay= sdf.parse(ED);

                    if(StartDay.compareTo(EndDay) < 0 || StartDay.compareTo(EndDay) == 0 ){

                        if(!company.isEmpty()&&!pos.isEmpty()&&!s.isEmpty()&&!e.isEmpty()){
                            experience.add(new subDatapage4(company,pos,s,e));
                            subpage4Adapter = new Sub4Adapter(experience);
                            ShowResult.setLayoutManager(new LinearLayoutManager(getActivity()));
                            subpage4Adapter.setOnClickListener(new Sub4Adapter.OnItemClickListener() {
                                @Override
                                public void OnItemClick(int position) {
                                    removeItem(position);
                                }
                            });
                            ShowResult.setAdapter(subpage4Adapter);

                            ((EditText)rootView.findViewById(R.id.addlocation)).setText(null);
                            ((EditText)rootView.findViewById(R.id.addposition)).setText(null);
                            ((Button)rootView.findViewById(R.id.HW_start)).setText("เลือกวันที่");
                            ((Button)rootView.findViewById(R.id.HW_end)).setText("เลือกวันที่");

                        }else{
                            Toast.makeText(getActivity(),"กรุณากรอกข้อมูลให้ครบ",Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(getActivity(),"กรุณาเลือกวันที่สิ้นสุดอีกครั้ง",Toast.LENGTH_SHORT).show();
                    }
                } catch (ParseException ee) {
                    ee.printStackTrace();
                }





                break;
        }
    }

    private void ShowDatePicker(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(),
                android.R.style.Theme_Holo_Dialog,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();

    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        if(selected == 0) {
            SD = year+" "+(month+1)+" "+dayOfMonth;
            ((Button) rootView.findViewById(R.id.HW_start)).setText(dayOfMonth + "/" + (month + 1) + "/" + (year + 543));
        }
        else if(selected == 1) {
            ED = year+" "+(month+1)+" "+dayOfMonth;
            ((Button) rootView.findViewById(R.id.HW_end)).setText(dayOfMonth + "/" + (month + 1) + "/" + (year + 543));
        }


    }



}