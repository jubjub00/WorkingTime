package com.example.workingtimewfh.ui.user.history;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.workingtimewfh.DialogA;
import com.example.workingtimewfh.ExtFunction;
import com.example.workingtimewfh.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class HistoryFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private HistoryViewModel HistoryViewModel;
    Button ss,se,Ssave;
    int selected=0;
    Date StartDay=null,EndDay=null;
    String SD=null,ED=null;
    SharedPreferences sp;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ExtFunction ext;
    ProgressBar WaitingDate;
    ListView ShowDateWorking;
    ArrayList<HashMap<String, String>> ArrTask;
    HashMap<String,Object> mm;
    int pos=-1;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HistoryViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_history, container, false);
        ss = root.findViewById(R.id.SelectStart);
        ss.setOnClickListener(this);
        se = root.findViewById(R.id.SelectEnd);
        se.setOnClickListener(this);
        Ssave = root.findViewById(R.id.SelectSave);
        Ssave.setOnClickListener(this);
        ext = new ExtFunction();
        WaitingDate = root.findViewById(R.id.WaitingDate);
        ShowDateWorking = root.findViewById(R.id.ShowDateWorking);

        StartDay=null;
        EndDay=null;
        SD=null;
        ED=null;


        return root;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.SelectStart:
                selected = 0;
                ShowDatePicker();
                break;
            case R.id.SelectEnd:
                selected = 1;
                ShowDatePicker();
                break;
            case R.id.SelectSave:
                if(SD != null && ED != null){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd");
                    try {
                        StartDay = sdf.parse(SD);
                        EndDay= sdf.parse(ED);

                        if(StartDay.compareTo(EndDay) < 0 || StartDay.compareTo(EndDay) == 0 ){
                            WaitingDate.setVisibility(View.VISIBLE);
                            GetWorkingTime();
                        }else{
                            Toast.makeText(getActivity(),"กรุณาเลือกวันที่สิ้นสุดอีกครั้ง",Toast.LENGTH_SHORT).show();
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(getActivity(),"กรุณาเลือกวันที่",Toast.LENGTH_SHORT).show();
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

        if(selected == 0){
            SD = year+" "+(month+1)+" "+dayOfMonth;
            ss.setText(dayOfMonth+"/"+(month+1)+"/"+(year+543));
        }else if(selected == 1){
            ED = year+" "+(month+1)+" "+dayOfMonth;
            se.setText(dayOfMonth+"/"+(month+1)+"/"+(year+543));
        }

    }

    public void GetWorkingTime() {
        sp = this.getActivity().getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE);
        ArrTask = new ArrayList<HashMap<String, String>>();
        pos = -1;

        db.collection("WorkingTime").document(sp.getString("KeyDocument", "???")).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {


            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isComplete()){
                    mm = (HashMap<String,Object>) task.getResult().getData();

                    if(mm == null){
                        WaitingDate.setVisibility(View.GONE);
                        Toast.makeText(getActivity(),"ไม่พบข้อมูล",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Map<String, Object> sortedTask = new TreeMap<>();
                    sortedTask.putAll(mm);
                    Iterator iterator = sortedTask.keySet().iterator();
                    boolean found = false;
                    while( iterator.hasNext() ) {

                        String DateWork = iterator.next().toString();
                        String DaySection[] = DateWork.split(" ");

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd");
                        try {
                            Date DataDay = sdf.parse((Integer.parseInt(DaySection[2])-543)+" "+(ext.GetMonthNumber(DaySection[1]))+" "+DaySection[0]);

                            if((StartDay.compareTo(DataDay) < 0 || StartDay.compareTo(DataDay) == 0) && (DataDay.compareTo(EndDay) < 0 || DataDay.compareTo(EndDay) == 0) ){

                                HashMap<String, String> PerTask = new HashMap<String, String>();
                                PerTask.put("Date", DateWork);
                                PerTask.put("DES","แตะเพื่อดูข้อมูล");
                                ArrTask.add(PerTask);
                                found = true;

                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                    }

                    if(!found)Toast.makeText(getActivity(),"ไม่พบข้อมูล",Toast.LENGTH_SHORT).show();
                    SimpleAdapter sAdap = new SimpleAdapter(getActivity(), ArrTask, R.layout.list_date_working,new String[] {"Date", "DES"}, new int[] {R.id.ShowDateWorking, R.id.des});
                    ShowDateWorking.setAdapter(sAdap);

                    ShowDateWorking.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            pos = position;
                            HashMap<String,Object> TimeInDay = (HashMap<String, Object>) mm.get(ArrTask.get(position).get("Date"));
                            Iterator iterator = TimeInDay.keySet().iterator();
                            String in[]=null,out[]=null;

                            while( iterator.hasNext() ) {

                                String StatusWork = iterator.next().toString();
                                HashMap<String,Object> TimeWork = (HashMap<String, Object>) TimeInDay.get(StatusWork);

                                if(StatusWork.compareTo("inwork") == 0)in = new String[TimeWork.size()];
                                else if(StatusWork.compareTo("outwork") == 0)out = new String[TimeWork.size()];


                                Iterator iter = TimeWork.keySet().iterator();
                                int i=0;
                                while( iter.hasNext() ) {
                                    String TimeWorking = iter.next().toString();
                                    HashMap<String,Object> TimeWorkingData = (HashMap<String, Object>) TimeWork.get(TimeWorking);

                                    if(StatusWork.compareTo("inwork") == 0)in[i] = TimeWorkingData.get("time").toString();
                                    else if(StatusWork.compareTo("outwork") == 0)out[i] = TimeWorkingData.get("time").toString();
                                    i++;


                                    //Toast.makeText(getActivity(),StatusWork+" "+TimeWorkingData.get("time").toString(),Toast.LENGTH_SHORT).show();
                                }
                            }

                            String SumString[] ;
                            if(out != null)SumString = new String[in.length+out.length];
                            else SumString = new String[in.length];


                            int inn=0,outt=0;
                            for(int i=0;i<SumString.length;i++){

                                if(i%2==0){
                                    SumString[i] = in[inn]+"       เข้างาน";
                                    inn++;
                                }else if(i%2==1){
                                    SumString[i] = out[outt]+"       ออกงาน";
                                    outt++;
                                }
                            }
                            DialogA sss = new DialogA();
                            sss.setPos(pos);
                            sss.setArrTask(ArrTask);
                            sss.setString(SumString);
                            sss.show(getFragmentManager(),"ok");
                        }
                    });





                }
                WaitingDate.setVisibility(View.GONE);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }







}
