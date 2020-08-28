package com.example.workingtimewfh;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class ExtFunction {



    public String GetTime(){
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH : mm");
        return myDateObj.format(myFormatObj);
    }


    public String GetDate(){
        Format formatter;
        formatter = new SimpleDateFormat("dd MMMM YYYY", new Locale("th", "TH"));
        String []dd=(formatter.format(new Date())).split(" ");
        return dd[0]+" "+dd[1]+" "+(Integer.parseInt(dd[2])+543);
    }

    public boolean GetSameDay(String OldDate){
        if(OldDate.compareTo(GetDate()) == 0)return true;
        return false;
    }

    public int GetMonthNumber(String MonthName){

        if(MonthName.compareTo("มกราคม") == 0 )return 1;
        else if(MonthName.compareTo("กุมภาพันธ์") == 0)return 2;
        else if(MonthName.compareTo("มีนาคม") == 0 )return 3;
        else if(MonthName.compareTo("เมษายน") == 0 )return 4;
        else if(MonthName.compareTo("พฤษภาคม") == 0 )return 5;
        else if(MonthName.compareTo("มิถุนายน") == 0 )return 6;
        else if(MonthName.compareTo("กรกฎาคม") == 0)return 7;
        else if(MonthName.compareTo("สิงหาคม") == 0 )return 8;
        else if(MonthName.compareTo("กันยายน") == 0 )return 9;
        else if(MonthName.compareTo("ตุลาคม") == 0 )return 10;
        else if(MonthName.compareTo("พฤศจิกายน") == 0 )return 11;
        else if(MonthName.compareTo("ธันวาคม") == 0 )return 12;
        return 0;

    }

    public String GetNextExit(){

        String date = GetDate();
        String time = GetTime();

        String str = "";
        String tmp[]=date.split(" ");
        String tmpTime[]=time.split(" ");
        int next = Integer.parseInt(tmpTime[2])+2;

        Log.d("gggggggggg",""+next);
        if(next >= 60){
            if((next%60) >=0 && (next%60) <= 9)
                tmpTime[2] = "0"+(next%60);
            else tmpTime[2] = ""+(next%60);

            tmpTime[0] = ""+(Integer.parseInt(tmpTime[0])+1);

            if(Integer.parseInt(tmpTime[0]) >= 24){
                tmpTime[0] = "00";
                tmp[0] = ""+(Integer.parseInt(tmp[0])+1);
            }
        }

        if(GetMonthNumber(tmp[1]) >=1 && GetMonthNumber(tmp[1]) <= 9) str += tmp[0]+"-0"+GetMonthNumber(tmp[1])+"-"+(Integer.parseInt(tmp[2])-543);
       else str += tmp[0]+"-"+GetMonthNumber(tmp[1])+"-"+(Integer.parseInt(tmp[2])-543);
       str += " "+tmpTime[0]+" : "+tmpTime[2];
        return str;
    }

    public  boolean ActiveExit(String DateInwork){
        if(DateInwork.compareTo("???") == 0)return false;

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH : mm");
        String date = GetDate();
        String time = GetTime();
        String DateNow = "";
        String tmp[] = date.split(" ");
        DateNow += tmp[0]+"-"+GetMonthNumber(tmp[1])+"-"+(Integer.parseInt(tmp[2])-543)+" "+time;

        try {
            Date DateIn = sdf.parse(DateInwork);
            Date DateNew = sdf.parse(DateNow);
            //Log.d("xxxxxxxxxxx",""+DateIn.toString());
            Log.d("gggggggggg",""+DateInwork);
            if(DateIn.before(DateNew)) return true;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }




}
