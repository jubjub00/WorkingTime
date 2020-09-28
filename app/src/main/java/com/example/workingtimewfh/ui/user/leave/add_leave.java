package com.example.workingtimewfh.ui.user.leave;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.workingtimewfh.ExtFunction;
import com.example.workingtimewfh.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class add_leave extends Fragment implements DatePickerDialog.OnDateSetListener, View.OnClickListener {

    private int selectDay = 0;
    String SD=null,ED=null;
    Date StartDay=null,EndDay=null;
    int hour1,min1;
    Spinner spinner;
    View root;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.tab_add_leave, container, false);


        spinner = root.findViewById(R.id.mainLeave);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.mainLeave, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        ((Button)root.findViewById(R.id.DayStart)).setOnClickListener(this);
        ((Button)root.findViewById(R.id.DayEnd)).setOnClickListener(this);
        ((Button)root.findViewById(R.id.TimeStart)).setOnClickListener(this);
        ((Button)root.findViewById(R.id.TimeEnd)).setOnClickListener(this);
        ((Button)root.findViewById(R.id.button4)).setOnClickListener(this);
        ((CheckBox) root.findViewById(R.id.half_day)).setOnClickListener(this);

    return  root;

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

    private void ShowtimePicker1(){
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                getActivity(),
                android.R.style.Theme_Holo_Dialog,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hour1 = hourOfDay;
                        min1 = minute;
                        String time1 = hour1+":"+min1;
                        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                        try {
                            Date date = format.parse(time1);
                            ((Button) root.findViewById(R.id.TimeStart)).setText(""+format.format(date));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                },24,0,true
        );

        timePickerDialog.show();

    }

    private void ShowtimePicker2(){
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                getActivity(),
                android.R.style.Theme_Holo_Dialog,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hour1 = hourOfDay;
                        min1 = minute;
                        String time1 = hour1+":"+min1;
                        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                        try {
                            Date date = format.parse(time1);
                            ((Button) root.findViewById(R.id.TimeEnd)).setText(""+format.format(date));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                },24,0,true
        );

        timePickerDialog.show();

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if(selectDay == 0){
            SD = year+" "+(month+1)+" "+dayOfMonth;
            ((Button) root.findViewById(R.id.DayStart)).setText(dayOfMonth + "/" + (month + 1) + "/" + (year + 543));
        }else{
            ED = year+" "+(month+1)+" "+dayOfMonth;
            ((Button) root.findViewById(R.id.DayEnd)).setText(dayOfMonth + "/" + (month + 1) + "/" + (year + 543));
        }
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.DayStart:
                ShowDatePicker();
                selectDay = 0;
            break;
            case R.id.DayEnd:
                ShowDatePicker();
                selectDay = 1;
                break;
            case R.id.TimeStart:
                ShowtimePicker1();
                break;
            case R.id.TimeEnd:
                ShowtimePicker2();
                break;
            case R.id.half_day:
                boolean checked = ((CheckBox) root.findViewById(R.id.half_day)).isChecked();
                if(!checked) {
                    ((Button)root.findViewById(R.id.TimeStart)).setVisibility(View.GONE);
                    ((Button)root.findViewById(R.id.TimeEnd)).setVisibility(View.GONE);
                    ((TextView)root.findViewById(R.id.textView8)).setVisibility(View.GONE);
                    ((TextView)root.findViewById(R.id.textView11)).setVisibility(View.GONE);
                }else{
                    ((Button)root.findViewById(R.id.TimeStart)).setVisibility(View.VISIBLE);
                    ((Button)root.findViewById(R.id.TimeEnd)).setVisibility(View.VISIBLE);
                    ((TextView)root.findViewById(R.id.textView8)).setVisibility(View.VISIBLE);
                    ((TextView)root.findViewById(R.id.textView11)).setVisibility(View.VISIBLE);
                }
                break;
            case R.id.button4:
                String s = ((Button)root.findViewById(R.id.DayStart)).getText().toString();
                String e = ((Button)root.findViewById(R.id.DayEnd)).getText().toString();

                if(s.compareTo("เลือกวันที่")==0 || e.compareTo("เลือกวันที่")==0) {
                    Toast.makeText(getActivity(),"โปรดเลือกวันที่",Toast.LENGTH_SHORT).show();
                    return;
                }


                SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd");
                if( !((Button)root.findViewById(R.id.TimeStart)).getText().toString().matches("เวลา") && !((Button)root.findViewById(R.id.TimeEnd)).getText().toString().matches("เวลา")) {
                    SD += " " + ((Button) root.findViewById(R.id.TimeStart)).getText().toString();
                    ED += " " + ((Button) root.findViewById(R.id.TimeEnd)).getText().toString();
                    sdf = new SimpleDateFormat("yyyy MM dd HH:mm");
                }
                try {
                    StartDay = sdf.parse(SD);
                    EndDay= sdf.parse(ED);

                    if(StartDay.compareTo(EndDay) < 0 ){


                        String sd = ((Button) root.findViewById(R.id.DayStart)).getText().toString();
                        String ed = ((Button)root.findViewById(R.id.DayEnd)).getText().toString();
                        String st = ((Button)root.findViewById(R.id.TimeStart)).getText().toString();
                        String et = ((Button)root.findViewById(R.id.TimeEnd)).getText().toString();


                        String st_value = ((Button)root.findViewById(R.id.TimeStart)).getText().toString();
                        String et_value = ((Button)root.findViewById(R.id.TimeEnd)).getText().toString();

                        if(((Button)root.findViewById(R.id.TimeStart)).getText().toString().matches("เวลา") || ((Button)root.findViewById(R.id.TimeEnd)).getText().toString().matches("เวลา")) {
                            st_value = null;
                            et_value = null;
                        }

                        HashMap<String,String> data = new HashMap<>();
                        data.put("title",(String)spinner.getSelectedItem());
                        data.put("date_start",sd);
                        data.put("time_start",st_value);
                        data.put("date_end",ed);
                        data.put("time_end",et_value);
                        data.put("status","รออนุมัติ");
                        if(!((EditText)root.findViewById(R.id.LeveDescription)).getText().toString().isEmpty())
                            data.put("description",((EditText)root.findViewById(R.id.LeveDescription)).getText().toString());
                        else
                            data.put("description",null);

                        ExtFunction d = new ExtFunction();
                        HashMap<String,HashMap<String,String>> total = new HashMap<>();
                        total.put(d.GetDate(),data);

                        SharedPreferences sp = this.getActivity().getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE);
                        String id = sp.getString("KeyDocument", "a");
                        if(!id.matches("a")){
                            FirebaseFirestore.getInstance().collection("leave").document(id).set(total, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getActivity(),"เพิ่มการลางานได้สำเร็จ",Toast.LENGTH_SHORT).show();
                                    getActivity().onBackPressed();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(),"เพิ่มการลางานผิดพลาด",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }


                    }else{
                        Toast.makeText(getActivity(),"กรุณาเลือกวันที่เเละเวลาอีกครั้ง",Toast.LENGTH_SHORT).show();
                    }
                } catch (ParseException ee) {
                    ee.printStackTrace();
                }

                break;
        }
    }


}