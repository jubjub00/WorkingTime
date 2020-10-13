package com.example.workingtimewfh.ui.admin.EditReal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.workingtimewfh.R;
import com.example.workingtimewfh.ui.admin.home_admin.HistoryEmployeeAdapter;
import com.example.workingtimewfh.ui.admin.home_admin.HistoryStruct;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditReal extends AppCompatActivity {

    EditAdapter  recyclerAdapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ViewPager2 recyclerView;
    String id;
    String Value_NameTitle = null,Value_Position = null,Value_State = null,Value_Religion = null,Gender = null,state = null;
    Spinner name_title,pos,pos2 ;
    int NameTitle = 0,Position = 0,reli = 0;

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
                        Dialog_2();
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

                Value_NameTitle = data.get("prefix").toString();
                Value_Position = data.get("position").toString();
                Value_State = data.get("status_family").toString();
                Value_Religion = data.get("religion").toString();
                Gender = data.get("gender").toString();
                state = data.get("type_employees").toString();


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
                ((EditText)view.findViewById(R.id.edit_lastname_eng)).setText((String)data.get("lastname_eng"));
                ((EditText)view.findViewById(R.id.edit_telephone)).setText((String)data.get("tel"));
                ((EditText)view.findViewById(R.id.edit_national)).setText((String)data.get("nationality"));
                ((EditText)view.findViewById(R.id.edit_thia)).setText((String)data.get("race"));


                Spinner spinner = view.findViewById(R.id.edit_stan);
                final ArrayAdapter<CharSequence> adapt = ArrayAdapter.createFromResource(getApplication(), R.array.status, android.R.layout.simple_spinner_item);
                adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapt);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Value_State = (String)adapt.getItem(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                name_title = view.findViewById(R.id.addnametitle);
                pos = view.findViewById(R.id.edit_position);
                pos2 = view.findViewById(R.id.edit_rel);


                CreateNameTitle(view);
                CreatePosition(view);
                CreateReligion(view);
                CreateSelectStatus(view,data.get("type_employees").toString());



            }
        });


        alert.setView(view).setPositiveButton("บันทึก", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                HashMap<String,Object> data = new HashMap<>() ;
                data.put("name",((EditText)view.findViewById(R.id.edit_name)).getText().toString());
                data.put("lastname",((EditText)view.findViewById(R.id.edit_lastname)).getText().toString());
                data.put("name_eng",((EditText)view.findViewById(R.id.edit_name_eng)).getText().toString());
                data.put("lastname_eng",((EditText)view.findViewById(R.id.edit_lastname_eng)).getText().toString());
                data.put("tel",((EditText)view.findViewById(R.id.edit_telephone)).getText().toString());
                data.put("nationality",((EditText)view.findViewById(R.id.edit_national)).getText().toString());
                data.put("race",((EditText)view.findViewById(R.id.edit_thia)).getText().toString());

                data.put("prefix",((Spinner)view.findViewById(R.id.addnametitle)).getSelectedItem().toString());
                data.put("position",((Spinner)view.findViewById(R.id.edit_position)).getSelectedItem().toString());
                data.put("status_family",((Spinner)view.findViewById(R.id.edit_stan)).getSelectedItem().toString());
                data.put("religion",((Spinner)view.findViewById(R.id.edit_rel)).getSelectedItem().toString());
                data.put("type_employees",((Spinner)view.findViewById(R.id.edit_type_emp)).getSelectedItem().toString());


                db.collection("user").document(id).update(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplication(),"แก้ไขข้อมูลสำเร็จ",Toast.LENGTH_SHORT).show();
                        create_top( id);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplication(),"แก้ไขข้อมูลผิดพลาด",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();



    }


    public void Dialog_2(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final View rootView = LayoutInflater.from(this).inflate(R.layout.subpage2,null);
        db.collection("user").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Map<String, Object> data = documentSnapshot.getData();

                ((EditText)rootView.findViewById(R.id.addaddress1)).setText((String)data.get("place_now"));
                ((EditText)rootView.findViewById(R.id.addcanton1)).setText((String)data.get("place_now_dist"));
                ((EditText)rootView.findViewById(R.id.addDistrict1)).setText((String)data.get("place_now_prov"));
                ((EditText)rootView.findViewById(R.id.addprovince1)).setText((String)data.get("place_now_sub"));
                ((EditText)rootView.findViewById(R.id.addcode1)).setText((String)data.get("place_now_zipcode"));


                ((EditText)rootView.findViewById(R.id.addaddress2)).setText((String)data.get("place_part"));
                ((EditText)rootView.findViewById(R.id.addcanton2)).setText((String)data.get("place_part_dist"));
                ((EditText)rootView.findViewById(R.id.addDistrict2)).setText((String)data.get("place_part_prov"));
                ((EditText)rootView.findViewById(R.id.addprovince2)).setText((String)data.get("place_part_sub"));
                ((EditText)rootView.findViewById(R.id.addcode2)).setText((String)data.get("place_part_zipcode"));


                final ArrayList<String> SUB_DISTRICT_NAME = new ArrayList<>();
                final ArrayList<String> DISTRICT_NAME = new ArrayList<>();
                final ArrayList<String> PROVINCE_NAME = new ArrayList<>();
                final ArrayList<String> ZIPCODE = new ArrayList<>();
                final ArrayList<String> All = new ArrayList<>();
                final CheckBox sameLocation;




                sameLocation = rootView.findViewById(R.id.checkBox);



                try {

                    JSONObject obj = new JSONObject(loadJSONFromAsset());
                    JSONArray userArray = obj.getJSONArray("zipcode");


                    for (int i = 0; i < userArray.length(); i++) {
                        JSONObject userDetail = userArray.getJSONObject(i);

                        SUB_DISTRICT_NAME.add(userDetail.getString("SUB_DISTRICT_NAME"));
                        DISTRICT_NAME.add(userDetail.getString("DISTRICT_NAME"));
                        PROVINCE_NAME.add(userDetail.getString("PROVINCE_NAME"));
                        ZIPCODE.add(userDetail.getString("ZIPCODE"));

                        All.add(userDetail.getString("SUB_DISTRICT_NAME")+" ,"+userDetail.getString("DISTRICT_NAME")+" ,"+userDetail.getString("PROVINCE_NAME"));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                final EditText addr = rootView.findViewById(R.id.addaddress1);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_list_item_1, All);
                final AutoCompleteTextView textView = (AutoCompleteTextView) rootView.findViewById(R.id.addcanton1);
                textView.setAdapter(adapter);
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_list_item_1, DISTRICT_NAME);
                final AutoCompleteTextView textView2 = (AutoCompleteTextView) rootView.findViewById(R.id.addDistrict1);
                textView2.setAdapter(adapter2);
                ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_list_item_1, PROVINCE_NAME);
                final AutoCompleteTextView textView3 = (AutoCompleteTextView) rootView.findViewById(R.id.addprovince1);
                textView3.setAdapter(adapter3);
                ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_list_item_1, ZIPCODE);
                final AutoCompleteTextView textView4= (AutoCompleteTextView) rootView.findViewById(R.id.addcode1);
                textView4.setAdapter(adapter4);

                textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        int a = All.indexOf(textView.getText().toString());
                        //Toast.makeText(getActivity(),"rtrtrtr"+ZIPCODE.get(a),Toast.LENGTH_SHORT).show();
                        textView.setText(SUB_DISTRICT_NAME.get(a));
                        textView2.setText(DISTRICT_NAME.get(a));
                        textView3.setText(PROVINCE_NAME.get(a));
                        textView4.setText(ZIPCODE.get(a));

                    }
                });

                final EditText addr2 = rootView.findViewById(R.id.addaddress2);
                ArrayAdapter<String> adapter11 = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_list_item_1, All);
                final AutoCompleteTextView textView11 = (AutoCompleteTextView) rootView.findViewById(R.id.addcanton2);
                textView11.setAdapter(adapter11);
                ArrayAdapter<String> adapter12 = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_list_item_1, DISTRICT_NAME);
                final AutoCompleteTextView textView12 = (AutoCompleteTextView) rootView.findViewById(R.id.addDistrict2);
                textView12.setAdapter(adapter12);
                ArrayAdapter<String> adapter13 = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_list_item_1, PROVINCE_NAME);
                final AutoCompleteTextView textView13 = (AutoCompleteTextView) rootView.findViewById(R.id.addprovince2);
                textView13.setAdapter(adapter13);
                ArrayAdapter<String> adapter14 = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_list_item_1, ZIPCODE);
                final AutoCompleteTextView textView14= (AutoCompleteTextView) rootView.findViewById(R.id.addcode2);
                textView14.setAdapter(adapter14);

                textView11.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        int a = All.indexOf(textView11.getText().toString());
                        textView11.setText(SUB_DISTRICT_NAME.get(a));
                        textView12.setText(DISTRICT_NAME.get(a));
                        textView13.setText(PROVINCE_NAME.get(a));
                        textView14.setText(ZIPCODE.get(a));


                    }
                });


                sameLocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean checked = ((CheckBox) sameLocation).isChecked();
                        if(checked){


                            ((EditText)rootView.findViewById(R.id.addaddress2)).setText(addr.getText().toString());
                            ((AutoCompleteTextView) rootView.findViewById(R.id.addcanton2)).setText(textView.getText().toString());
                            ((AutoCompleteTextView) rootView.findViewById(R.id.addDistrict2)).setText(textView2.getText().toString());
                            ((AutoCompleteTextView) rootView.findViewById(R.id.addprovince2)).setText(textView3.getText().toString());
                            ((AutoCompleteTextView) rootView.findViewById(R.id.addcode2)).setText(textView4.getText().toString());


                        }else {
                            addr2.setText(null);
                            textView11.setText(null);
                            textView12.setText(null);
                            textView13.setText(null);
                            textView14.setText(null);
                        }
                    }
                });







            }
        });


        alert.setView(rootView).setPositiveButton("บันทึก", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                HashMap<String,Object> data = new HashMap<>();
                data.put("place_now",((EditText)rootView.findViewById(R.id.addaddress1)).getText().toString());
                data.put("place_now_dist",((EditText)rootView.findViewById(R.id.addcanton1)).getText().toString());
                data.put("place_now_prov",((EditText)rootView.findViewById(R.id.addDistrict1)).getText().toString());
                data.put("place_now_sub",((EditText)rootView.findViewById(R.id.addprovince1)).getText().toString());
                data.put("place_now_zipcode",((EditText)rootView.findViewById(R.id.addcode1)).getText().toString());

                data.put("place_part",((EditText)rootView.findViewById(R.id.addaddress2)).getText().toString());
                data.put("place_part_dist",((EditText)rootView.findViewById(R.id.addcanton2)).getText().toString());
                data.put("place_part_prov",((EditText)rootView.findViewById(R.id.addDistrict2)).getText().toString());
                data.put("place_part_sub",((EditText)rootView.findViewById(R.id.addprovince2)).getText().toString());
                data.put("place_part_zipcode",((EditText)rootView.findViewById(R.id.addcode2)).getText().toString());



                db.collection("user").document(id).update(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplication(),"แก้ไขข้อมูลสำเร็จ",Toast.LENGTH_SHORT).show();
                        create_top( id);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplication(),"แก้ไขข้อมูลผิดพลาด",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();



    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getApplication().getAssets().open("zipcode.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }



    private void CreateSelectStatus(View view, String type_employees) {
        Spinner spinner = view.findViewById(R.id.edit_type_emp);
        final ArrayAdapter<CharSequence> adapt = ArrayAdapter.createFromResource(getApplication(), R.array.type, android.R.layout.simple_spinner_item);
        adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapt);
        spinner.setSelection(adapt.getPosition(type_employees));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Value_State = (String)adapt.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    public void CreateNameTitle(View view){
        db.collection("user").document("extension").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                List<String> a = (List<String>) documentSnapshot.getData().get("name_title");
                final List<String> b = a;

                ArrayAdapter aa = new ArrayAdapter(getApplication(),android.R.layout.simple_spinner_item, a);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                name_title.setAdapter(aa);
                NameTitle = aa.getPosition(Value_NameTitle);
                name_title.setSelection(NameTitle);
                Value_NameTitle = (String)name_title.getSelectedItem();
                name_title.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                        if(((String)parent.getItemAtPosition(position)).compareTo("เพิ่มคำนำหน้าชื่อ") == 0){
                            //Toast.makeText(getActivity(),""+parent.getItemAtPosition(position),Toast.LENGTH_SHORT).show();
                            android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(getApplication());
                            final View mView = getLayoutInflater().inflate(R.layout.custom_dialog_nametitle,null);

                            alert.setView(mView);
                            alert.setPositiveButton("บันทึก", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {


                                    b.add(((EditText)mView.findViewById(R.id.editText)).getText().toString());

                                    int ii=0;
                                    for (String x:b) {
                                        if(x.compareTo("เพิ่มคำนำหน้าชื่อ") == 0){
                                            String tmp = b.get(ii);
                                            b.set(ii,b.get(b.size()-1));
                                            b.set(b.size()-1,tmp);
                                        }
                                        ii++;
                                    }

                                    FirebaseFirestore i = FirebaseFirestore.getInstance();
                                    i.collection("user").document("extension").update("name_title",b);
                                    ArrayAdapter aa = new ArrayAdapter(getApplication(),android.R.layout.simple_spinner_item, b);
                                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    Spinner name_title = findViewById(R.id.addnametitle);
                                    name_title.setAdapter(aa);
                                }
                            }).setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                            android.app.AlertDialog alertDialog = alert.create();
                            alertDialog.show();

                        }else{
                            NameTitle = position;
                            Value_NameTitle = (String)name_title.getSelectedItem();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


            }
        });


    }
    public void CreatePosition(View view){
        db.collection("user").document("extension").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                List<String> a = (List<String>) documentSnapshot.getData().get("position");
                final List<String> b = a;

                ArrayAdapter aa = new ArrayAdapter(getApplication(),android.R.layout.simple_spinner_item, a);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                pos.setAdapter(aa);
                Position = aa.getPosition(Value_Position);
                pos.setSelection(Position);
                Value_Position = (String)pos.getSelectedItem();
                pos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                        if(((String)parent.getItemAtPosition(position)).compareTo("เพิ่มตำแหน่ง") == 0){
                            //Toast.makeText(getActivity(),""+parent.getItemAtPosition(position),Toast.LENGTH_SHORT).show();
                            android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(getApplication());
                            final View mView = getLayoutInflater().inflate(R.layout.custom_dialog_nametitle,null);
                            ((TextView)mView.findViewById(R.id.textView6)).setText("เพิ่มตำแหน่ง");
                            ((EditText)mView.findViewById(R.id.editText)).setHint("ใส่ค่าตำแหน่ง");
                            alert.setView(mView);
                            alert.setPositiveButton("บันทึก", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {


                                    b.add(((EditText)mView.findViewById(R.id.editText)).getText().toString());

                                    int ii=0;
                                    for (String x:b) {
                                        if(x.compareTo("เพิ่มตำแหน่ง") == 0){
                                            String tmp = b.get(ii);
                                            b.set(ii,b.get(b.size()-1));
                                            b.set(b.size()-1,tmp);
                                        }
                                        ii++;
                                    }

                                    FirebaseFirestore i = FirebaseFirestore.getInstance();
                                    i.collection("user").document("extension").update("position",b);
                                    ArrayAdapter aa = new ArrayAdapter(getApplication(),android.R.layout.simple_spinner_item, b);
                                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    Spinner name_title = findViewById(R.id.addposition);
                                    name_title.setAdapter(aa);
                                }
                            }).setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                            android.app.AlertDialog alertDialog = alert.create();
                            alertDialog.show();

                        }else{
                            Position = position;
                            Value_Position = (String)pos.getSelectedItem();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });


    }
    public void CreateReligion(View view){
        db.collection("user").document("extension").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                List<String> a = (List<String>) documentSnapshot.getData().get("religion");
                final List<String> b = a;



                ArrayAdapter aa = new ArrayAdapter(getApplication(),android.R.layout.simple_spinner_item, a);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                pos2.setAdapter(aa);
                reli = aa.getPosition(Value_Religion);
                pos2.setSelection(reli);
                Value_Religion = (String)pos2.getSelectedItem();
                pos2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                        if(((String)parent.getItemAtPosition(position)).compareTo("เพิ่มศาสนา") == 0){
                            //Toast.makeText(getActivity(),""+parent.getItemAtPosition(position),Toast.LENGTH_SHORT).show();
                            android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(getApplication());
                            final View mView = getLayoutInflater().inflate(R.layout.custom_dialog_nametitle,null);
                            ((TextView)mView.findViewById(R.id.textView6)).setText("เพิ่มศาสนา");
                            ((EditText)mView.findViewById(R.id.editText)).setHint("ใส่ค่าศาสนา");
                            alert.setView(mView);
                            alert.setPositiveButton("บันทึก", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    b.add(((EditText)mView.findViewById(R.id.editText)).getText().toString());

                                    int ii=0;
                                    for (String x:b) {
                                        if(x.compareTo("เพิ่มศาสนา") == 0){
                                            String tmp = b.get(ii);
                                            b.set(ii,b.get(b.size()-1));
                                            b.set(b.size()-1,tmp);
                                        }
                                        ii++;
                                    }

                                    FirebaseFirestore i = FirebaseFirestore.getInstance();
                                    i.collection("user").document("extension").update("religion",b);
                                    ArrayAdapter aa = new ArrayAdapter(getApplication(),android.R.layout.simple_spinner_item, b);
                                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    Spinner name_title = findViewById(R.id.spinner2);
                                    name_title.setAdapter(aa);
                                }
                            }).setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                            android.app.AlertDialog alertDialog = alert.create();
                            alertDialog.show();

                        }else{
                            reli = position;
                            Value_Religion = (String)pos2.getSelectedItem();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });


    }

}



