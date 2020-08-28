package com.example.workingtimewfh.ui.admin.add_user;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.LayoutInflater; import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.workingtimewfh.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class SubPage1 extends Fragment implements AdapterView.OnItemSelectedListener {


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    View rootView;
    int NameTitle = 0,Position = 0,reli = 0;


    public static SubPage1 newInstance() {
        SubPage1 fragment = new SubPage1();

        return fragment;
    }
    public SubPage1() { }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.subpage1, container, false);

        Spinner spinner = rootView.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        CreateNameTitle();
        CreatePosition();
        CreateReligion();

        EditText ID = rootView.findViewById(R.id.addid);
        ID.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(((EditText)rootView.findViewById(R.id.addid)).getText().toString().length() < 13) {
                    ((EditText) rootView.findViewById(R.id.addid)).setError("กรอกให้ครบ 13 หลัก");}
                return false;
            }
        });
        ID.setFilters(new InputFilter[] {new InputFilter.LengthFilter(13)});

        EditText Tel = rootView.findViewById(R.id.addtel);
        Tel.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(((EditText)rootView.findViewById(R.id.addtel)).getText().toString().length() < 10) {
                    ((EditText) rootView.findViewById(R.id.addtel)).setError("กรอกให้ครบ 10 หลัก");}
                return false;
            }
        });
        Tel.setFilters(new InputFilter[] {new InputFilter.LengthFilter(10)});

        return rootView;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void valid(String a, ViewPager pager){
        pager.setCurrentItem(0);
        ((EditText) rootView.findViewById(R.id.addname)).setError(a);
        ((EditText) rootView.findViewById(R.id.addname)).requestFocus();
    }

    public void CreateNameTitle(){
        db.collection("user").document("extension").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                List<String> a = (List<String>) documentSnapshot.getData().get("name_title");
                final List<String> b = a;

                Spinner name_title = rootView.findViewById(R.id.addnametitle);

                ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item, a);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                name_title.setAdapter(aa);
                name_title.setSelection(NameTitle);
                name_title.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                        if(((String)parent.getItemAtPosition(position)).compareTo("เพิ่มคำนำหน้าชื่อ") == 0){
                            //Toast.makeText(getActivity(),""+parent.getItemAtPosition(position),Toast.LENGTH_SHORT).show();
                            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
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
                                    ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item, b);
                                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    Spinner name_title = rootView.findViewById(R.id.addnametitle);
                                    name_title.setAdapter(aa);
                                }
                            }).setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                            AlertDialog alertDialog = alert.create();
                            alertDialog.show();

                        }else{
                            NameTitle = position;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });


    }

    public void CreatePosition(){
        db.collection("user").document("extension").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                List<String> a = (List<String>) documentSnapshot.getData().get("position");
                final List<String> b = a;

                Spinner pos = rootView.findViewById(R.id.addposition);

                ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item, a);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                pos.setAdapter(aa);
                pos.setSelection(Position);
                pos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                        if(((String)parent.getItemAtPosition(position)).compareTo("เพิ่มตำแหน่ง") == 0){
                            //Toast.makeText(getActivity(),""+parent.getItemAtPosition(position),Toast.LENGTH_SHORT).show();
                            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
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
                                    ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item, b);
                                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    Spinner name_title = rootView.findViewById(R.id.addposition);
                                    name_title.setAdapter(aa);
                                }
                            }).setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                            AlertDialog alertDialog = alert.create();
                            alertDialog.show();

                        }else{
                            Position = position;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });


    }
    public void CreateReligion(){
        db.collection("user").document("extension").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                List<String> a = (List<String>) documentSnapshot.getData().get("religion");
                final List<String> b = a;

                Spinner pos = rootView.findViewById(R.id.spinner2);

                ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item, a);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                pos.setAdapter(aa);
                pos.setSelection(reli);
                pos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                        if(((String)parent.getItemAtPosition(position)).compareTo("เพิ่มศาสนา") == 0){
                            //Toast.makeText(getActivity(),""+parent.getItemAtPosition(position),Toast.LENGTH_SHORT).show();
                            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
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
                                    ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item, b);
                                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    Spinner name_title = rootView.findViewById(R.id.spinner2);
                                    name_title.setAdapter(aa);
                                }
                            }).setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                            AlertDialog alertDialog = alert.create();
                            alertDialog.show();

                        }else{
                            reli = position;
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