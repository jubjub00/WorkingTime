package com.example.workingtimewfh.ui.admin.home_admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.workingtimewfh.R;

public class DetailTime extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_time);

        Intent GetId = getIntent();
        String date = GetId.getStringExtra("date");

    }
}
