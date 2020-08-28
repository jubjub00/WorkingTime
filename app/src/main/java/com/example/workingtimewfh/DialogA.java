package com.example.workingtimewfh;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class DialogA extends AppCompatDialogFragment {

    private ArrayList<HashMap<String, String>> ArrTask;
    private int pos;
    String s[];
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.MyAlertDialogTheme);
        CharSequence[] items =null;

        items = s;
        TextView title = new TextView(getActivity());
        title.setText("การทำงานวันที่ "+ArrTask.get(pos).get("Date"));
        title.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.kanit_medium));
        title.setTextSize(20);
        title.setTextColor(Color.BLACK);

        builder.setCustomTitle(title).setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });


        return builder.create();
    }

    public void setPos(int pos){
        this.pos = pos;
    }
    public void setArrTask(ArrayList<HashMap<String, String>> ArrTask){
        this.ArrTask = ArrTask;
    }
    public void setString(String s[]){
        this.s = s;
    }
}