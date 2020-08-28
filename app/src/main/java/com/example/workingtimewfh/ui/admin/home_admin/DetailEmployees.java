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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class DetailEmployees extends AppCompatActivity {
    HistoryEmployeeAdapter recyclerAdapter;
    FirebaseFirestore db ;
    Query queryR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_employees);

        Intent GetId = getIntent();
        String id = GetId.getStringExtra("id");
        Toast.makeText(getApplication(),""+id,Toast.LENGTH_SHORT).show();

        //db = FirebaseFirestore.getInstance();

        ArrayList<HistoryStruct> dataSet = new ArrayList<>();
        dataSet.add(new HistoryStruct("ข้อมูลส่วนตัว","ชื่อ นามสกุล"));
        dataSet.add(new HistoryStruct("ข้อมูลที่อยู่","ชื่อ นามสกุล"));
        dataSet.add(new HistoryStruct("ข้อมูลการศึกษา","ชื่อ นามสกุล"));
        dataSet.add(new HistoryStruct("ข้อมูลประวัติการทำงาน","ชื่อ นามสกุล"));


        recyclerAdapter = new HistoryEmployeeAdapter(dataSet);

        ViewPager2 recyclerView =  findViewById(R.id.HistoryEmployee);
        //recyclerView.setHasFixedSize(true);


        //recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerAdapter);
    }


}
