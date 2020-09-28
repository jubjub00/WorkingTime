package com.example.workingtimewfh.ui.admin.home_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.workingtimewfh.DialogA;
import com.example.workingtimewfh.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class DetailEmployees extends AppCompatActivity {
    HistoryEmployeeAdapter recyclerAdapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Query queryR;
    String Value_State;
    HashMap<String,Object> mm;
    ArrayList<String> DataToRec;
    ArrayList<String> data;
    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_employees);

        Intent GetId = getIntent();
        id = GetId.getStringExtra("id");
        create_top(id);

        db.collection("user").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Map<String, Object> a = documentSnapshot.getData();
                String s = (String) a.get("status_on");
                if(s.matches("yes")){
                    ((Button)findViewById(R.id.show_status)).setText("สถานะ : เปิด");
                    ((Button)findViewById(R.id.show_status)).setBackground(getDrawable(R.drawable.inwork));
                }else{
                    ((Button)findViewById(R.id.show_status)).setText("สถานะ : ปิด");
                    ((Button)findViewById(R.id.show_status)).setBackground(getDrawable(R.drawable.outwork));
                }

            }
        });


        ((Button)findViewById(R.id.show_status)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.collection("user").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Map<String, Object> a = documentSnapshot.getData();
                        String s = (String) a.get("status_on");
                        if(s.matches("yes")){

                            AlertDialog.Builder builder = new AlertDialog.Builder(DetailEmployees.this);
                            builder.setMessage("ต้องการปิดสถานะพนักงานใช่หรือไม่");
                            builder.setPositiveButton("ใช่", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int ide) {
                                    db.collection("user").document(id).update("status_on","no");
                                    ((Button)findViewById(R.id.show_status)).setText("สถานะ : ปิด");
                                    ((Button)findViewById(R.id.show_status)).setBackground(getDrawable(R.drawable.outwork));
                                }
                            });
                            builder.setNegativeButton("ไม่", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();

                                }
                            });
                            builder.show();

                        }else{

                            AlertDialog.Builder builder = new AlertDialog.Builder(DetailEmployees.this);
                            builder.setMessage("ต้องการเปิดสถานะพนักงานใช่หรือไม่");
                            builder.setPositiveButton("ใช่", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int ide) {

                                    db.collection("user").document(id).update("status_on","yes");
                                    ((Button)findViewById(R.id.show_status)).setText("สถานะ : เปิด");
                                    ((Button)findViewById(R.id.show_status)).setBackground(getDrawable(R.drawable.inwork));

                                }
                            });
                            builder.setNegativeButton("ไม่", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();

                                }
                            });
                            builder.show();

                        }
                    }
                });

            }
        });


        final Spinner spinner_month = findViewById(R.id.spn_month);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.month, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_month.setAdapter(adapter);
        spinner_month.setSelection(0);

        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for(int i = thisYear+543; i >= 2500; i--)
            years.add(Integer.toString(i));

        ArrayAdapter<String> adapter_year = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, years);
        final Spinner spinYear = (Spinner)findViewById(R.id.spn_year);
        spinYear.setAdapter(adapter_year);
        spinYear.setSelection(0);


        spinner_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long ide) {
                Value_State = (String) adapter.getItem(position);
                create_recycle((String)spinner_month.getSelectedItem(),(String)spinYear.getSelectedItem(),id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long ide) {
                Value_State = (String) adapter.getItem(position);
                create_recycle((String)spinner_month.getSelectedItem(),(String)spinYear.getSelectedItem(),id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ((Button)findViewById(R.id.show_all)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create_recycle(null,null,id);
            }
        });



        create_recycle((String)spinner_month.getSelectedItem(),(String)spinYear.getSelectedItem(),id);
    }


    public void create_top(String id){
        db.collection("user").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Map<String, Object> data = documentSnapshot.getData();

                String show_person = "";
                show_person += "คำนำหน้าชื่อ "+data.get("prefix")+"\n";
                show_person += "ชื่อ "+data.get("name")+"\n";
                show_person += "นามสกุล "+data.get("lastname")+"\n";
                show_person += "ชื่อภาษาอังกฤษ "+data.get("name_eng")+"\n";
                show_person += "นามสกุลภาษาอังกฤษ "+data.get("lastname_eng")+"\n";
                show_person += "รหัสบัตรประชาชน "+data.get("personal_id")+"\n";
                show_person += "เบอร์โทรศัพท์ "+data.get("tel")+"\n";
                show_person += "ตำแหน่ง "+data.get("position")+"\n";
                show_person += "วันเกิด "+data.get("birth_day")+"\n";
                show_person += "สถานภาพ "+data.get("status_family")+"\n";
                show_person += "สัญชาติ "+data.get("nationality")+"\n";
                show_person += "เชื้อชาติ "+data.get("race")+"\n";
                show_person += "ศาสนา "+data.get("religion")+"\n";
                show_person += "เพศ "+data.get("gender")+"\n";
                show_person += "ประเภท "+data.get("type_employees");



                String show_place = "";
                show_place += "ที่อยู่ปัจจุบัน\n";
                show_place += "\t"+data.get("place_now")+" "+data.get("place_now_sub")+"\n"+
                        "\t"+data.get("place_now_dist")+" "+data.get("place_now_prov")+" "+data.get("place_now_zipcode")+"\n\n";
                show_place += "ที่อยู่ตามทะเบียนบ้าน\n";
                show_place += "\t"+data.get("place_part")+" "+data.get("place_part_sub")+"\n"+
                        "\t"+data.get("place_part_dist")+" "+data.get("place_part_prov")+" "+data.get("place_part_zipcode");

                String show_education = "";
                ArrayList<Object> a = (ArrayList<Object>) data.get("education");
                if(a != null){
                    for(int i=0;i<a.size();i++) {

                        HashMap<String, String> b = (HashMap<String, String>) a.get(i);
                        show_education += b.get("level") + "\n";
                        show_education += "\t\tชื่อโรงเรียน ";
                        if (((String) b.get("name")).isEmpty())
                            show_education += "ไม่มีข้อมูล" + "\n";
                        else
                            show_education += b.get("name") + "\n";

                        show_education += "\t\tปีที่จบ ";
                        if ((String.valueOf(b.get("year"))).isEmpty())
                            show_education += "ไม่มีข้อมูล" + "   ";
                        else
                            show_education += String.valueOf(b.get("year")) + "   ";

                        show_education += "เกรด ";
                        if ((String.valueOf(b.get("grade"))).isEmpty())
                            show_education += "ไม่มีข้อมูล";
                        else
                            show_education += String.valueOf(b.get("grade")) + "\n\n";
                    }

                }else
                    show_education += "ไม่มีข้อมูล";




                String show_experience = "";
                ArrayList<Object> c = (ArrayList<Object>) data.get("experience");
                if(c != null){
                    for(int i=0;i<c.size();i++) {

                        HashMap<String, String> b = (HashMap<String, String>) c.get(i);

                        show_experience += "\t\tชื่อบริษัท ";
                        if (((String) b.get("company")).isEmpty())
                            show_experience += "ไม่มีข้อมูล" + "\n";
                        else
                            show_experience += b.get("company") + "\n";

                        show_experience += "\t\tชื่อตำแหน่ง ";
                        if (((String) b.get("position")).isEmpty())
                            show_experience += "ไม่มีข้อมูล" + "\n";
                        else
                            show_experience += b.get("position") + "\n";

                        show_experience += "\t\tวันที่เริ่มงาน ";
                        if ((String.valueOf(b.get("start"))).isEmpty())
                            show_experience += "ไม่มีข้อมูล" + "   ";
                        else
                            show_experience += b.get("start") + "   ";

                        show_experience += "วันที่ลาออก ";
                        if ((String.valueOf(b.get("end"))).isEmpty())
                            show_experience += "ไม่มีข้อมูล";
                        else
                            show_experience += b.get("end") + "\n\n";
                    }

                }else
                    show_experience += "ไม่มีข้อมูล";



                ArrayList<HistoryStruct> dataSet = new ArrayList<>();
                dataSet.add(new HistoryStruct("ข้อมูลส่วนตัว",show_person));
                dataSet.add(new HistoryStruct("ข้อมูลที่อยู่",show_place));
                dataSet.add(new HistoryStruct("ข้อมูลการศึกษา",show_education));
                dataSet.add(new HistoryStruct("ข้อมูลประวัติการทำงาน",show_experience));


                recyclerAdapter = new HistoryEmployeeAdapter(dataSet);

                ViewPager2 recyclerView =  findViewById(R.id.HistoryEmployee);
                recyclerView.setAdapter(recyclerAdapter);

            }
        });
    }
    public void create_recycle(final String month, final String year, String id){
        db.collection("WorkingTime").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {


            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                data = new ArrayList<>();
                if(task.isComplete()){
                    DataToRec = new ArrayList<>();
                    mm = (HashMap<String,Object>) task.getResult().getData();

                    if(mm == null){
                        DataToRec.add("ไม่มีข้อมูล");
                        DetailEmployeesAdapter detailEmployeesAdapter= new DetailEmployeesAdapter(DataToRec);
                        ((RecyclerView)findViewById(R.id.RecycleWorkTime)).setLayoutManager(new LinearLayoutManager(getApplication()));
                        ((RecyclerView)findViewById(R.id.RecycleWorkTime)).setAdapter(detailEmployeesAdapter);
                        return;
                    }

                    Map<String, Object> sortedTask = new TreeMap<>();
                    sortedTask.putAll(mm);
                    Iterator iterator = sortedTask.keySet().iterator();

                    while( iterator.hasNext() ) {

                        String DateWork = iterator.next().toString();
                        String DaySection[] = DateWork.split(" ");
                        if(month == null && year == null)
                            DataToRec.add(DaySection[0]+" "+DaySection[1]+" "+DaySection[2]);
                        else
                            if(DaySection[1].matches(month) && DaySection[2].matches(year))
                                DataToRec.add(DaySection[0]+" "+DaySection[1]+" "+DaySection[2]);


                    }
                    if(DataToRec.size() > 0){
                        DetailEmployeesAdapter detailEmployeesAdapter= new DetailEmployeesAdapter(DataToRec);

                        ((RecyclerView)findViewById(R.id.RecycleWorkTime)).setLayoutManager(new LinearLayoutManager(getApplication()));
                        ((RecyclerView)findViewById(R.id.RecycleWorkTime)).setAdapter(detailEmployeesAdapter);
                    }else{
                        DataToRec.add("ไม่มีข้อมูล");
                        DetailEmployeesAdapter detailEmployeesAdapter= new DetailEmployeesAdapter(DataToRec);
                        ((RecyclerView)findViewById(R.id.RecycleWorkTime)).setLayoutManager(new LinearLayoutManager(getApplication()));
                        ((RecyclerView)findViewById(R.id.RecycleWorkTime)).setAdapter(detailEmployeesAdapter);
                    }

                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplication(),"เกิดข้อผิดพลาด",Toast.LENGTH_SHORT).show();
            }
        });
    }


}
