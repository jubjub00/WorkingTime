package com.example.workingtimewfh.ui.admin.home_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.appwidget.AppWidgetHost;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.workingtimewfh.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Map;

public class DetailEmployees extends AppCompatActivity {
    HistoryEmployeeAdapter recyclerAdapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Query queryR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_employees);

        Intent GetId = getIntent();
        String id = GetId.getStringExtra("id");


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


                //place_part,place_part_sub,place_part_dist,place_part_prov,place_part_zipcode

                String show_place = "";
                show_place += "ที่อยู่ปัจจุบัน\n";
                show_place += "\t"+data.get("place_now")+" "+data.get("place_now_sub")+"\n"+
                            "\t"+data.get("place_now_dist")+" "+data.get("place_now_prov")+" "+data.get("place_now_zipcode")+"\n\n";
                show_place += "ที่อยู่ตามทะเบียนบ้าน\n";
                show_place += "\t"+data.get("place_part")+" "+data.get("place_part_sub")+"\n"+
                        "\t"+data.get("place_part_dist")+" "+data.get("place_part_prov")+" "+data.get("place_part_zipcode");


                ArrayList<HistoryStruct> dataSet = new ArrayList<>();
                dataSet.add(new HistoryStruct("ข้อมูลส่วนตัว",show_person));
                dataSet.add(new HistoryStruct("ข้อมูลที่อยู่",show_place));
                dataSet.add(new HistoryStruct("ข้อมูลการศึกษา","ชื่อ นามสกุล"));
                dataSet.add(new HistoryStruct("ข้อมูลประวัติการทำงาน","ชื่อ นามสกุล"));


                recyclerAdapter = new HistoryEmployeeAdapter(dataSet);

                ViewPager2 recyclerView =  findViewById(R.id.HistoryEmployee);
                recyclerView.setAdapter(recyclerAdapter);

            }
        });


    }


}
