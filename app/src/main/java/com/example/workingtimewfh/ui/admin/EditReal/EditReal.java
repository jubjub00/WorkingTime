package com.example.workingtimewfh.ui.admin.EditReal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.workingtimewfh.R;
import com.example.workingtimewfh.ui.admin.home_admin.HistoryEmployeeAdapter;
import com.example.workingtimewfh.ui.admin.home_admin.HistoryStruct;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditReal extends AppCompatActivity {

    EditAdapter  recyclerAdapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ViewPager2 recyclerView;
    String id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_real);

        Intent GetId = getIntent();
        id = GetId.getStringExtra("id");
        create_top(id);

        ((Button)findViewById(R.id.edit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (recyclerView.getCurrentItem()){
                    case 0 :

                        Dialog_1();
                        break;
                    case 1 :
                        break;
                    case 2 :
                        break;
                    case 3 :
                        break;
                }
            }
        });


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


                recyclerAdapter = new EditAdapter(dataSet);

                recyclerView =  findViewById(R.id.HistoryEmployee);
                recyclerView.setAdapter(recyclerAdapter);

            }
        });
    }

    public void Dialog_1(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final View view = LayoutInflater.from(this).inflate(R.layout.edit_data_personal,null);
        db.collection("user").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Map<String, Object> data = documentSnapshot.getData();

                ((EditText)view.findViewById(R.id.edit_name)).setText((String)data.get("name"));
                ((EditText)view.findViewById(R.id.edit_lastname)).setText((String)data.get("lastname"));
                ((EditText)view.findViewById(R.id.edit_name_eng)).setText((String)data.get("name_eng"));
                ((EditText)view.findViewById(R.id.edit_personalID)).setText((String)data.get("lastname_eng"));
                ((EditText)view.findViewById(R.id.edit_telephone)).setText((String)data.get("tel"));
                ((Button)view.findViewById(R.id.edit_birthday)).setText((String)data.get("birth_day"));
                ((EditText)view.findViewById(R.id.edit_national)).setText((String)data.get("nationality"));
                //((Spinner)view.findViewById(R.id.edit_rel)).setText((String)data.get("race"));




            }
        });





        /*show_person += "ชื่อภาษาอังกฤษ "+data.get("")+"\n";
        show_person += "นามสกุลภาษาอังกฤษ "+data.get("lastname_eng")+"\n";
        show_person += "รหัสบัตรประชาชน "+data.get("personal_id")+"\n";
        show_person += "เบอร์โทรศัพท์ "+data.get("tel")+"\n";

        show_person += "วันเกิด "+data.get("birth_day")+"\n";
        show_person += "สัญชาติ "+data.get("nationality")+"\n";
        show_person += "เชื้อชาติ "+data.get("race")+"\n";*/





        alert.setView(view).setPositiveButton("บันทึก", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alert.show();



    }

}



