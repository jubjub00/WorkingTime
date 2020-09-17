package com.example.workingtimewfh.ui.user.leave;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.workingtimewfh.R;
import android.widget.LinearLayout;
public class add_leave extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_add_leave);

        Spinner spinner = findViewById(R.id.mainLeave);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getApplication(), R.array.mainLeave, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);



    }
}