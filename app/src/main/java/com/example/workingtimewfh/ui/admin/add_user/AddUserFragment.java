package com.example.workingtimewfh.ui.admin.add_user;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.workingtimewfh.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kofigyan.stateprogressbar.StateProgressBar;
import com.kofigyan.stateprogressbar.components.StateItem;
import com.kofigyan.stateprogressbar.listeners.OnStateItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AddUserFragment extends Fragment  implements ViewPager.OnPageChangeListener{

    private AddUserViewModel AddUserViewModel;
    Button submit,cancel;
    String Vstatus,Vgender;
    View root,s1;

    List<Bitmap> BitmapImage;
    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String next_id;
    Map<String, Object> user;
    ProgressBar progressBar2,progressBar3;
    int current_position=0;
    String[] descriptionData = {"ข้อมูลส่วนตัว", "ข้อมูลที่อยู่", "ข้อมูล\nการศึกษา", "ข้อมูล\nการทำงาน"};
    MyPageAdapter adapter;
    StateProgressBar stateProgressBar;
    ViewPager pager=null;
    SubPage1  sb1;
    Fragment sub11;
    int getCurrent_position = 0;




    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AddUserViewModel = ViewModelProviders.of(this).get(AddUserViewModel.class);
        root = inflater.inflate(R.layout.fragment_add_user, container, false);
        progressBar3 = root.findViewById(R.id.progressBar3);


        submit = root.findViewById(R.id.save);
        cancel = root.findViewById(R.id.cancel);
        Vstatus = null;
        Vgender = null;

        adapter = new MyPageAdapter(getChildFragmentManager());



        pager =  root.findViewById(R.id.pager);
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(this);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getCurrent_position == 3)
                    getDataFromFragment();
                else{
                    getCurrent_position++;
                    if(getCurrent_position>=3)getCurrent_position=3;
                    pager.setCurrentItem(getCurrent_position);
                    setButton();
                }


            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrent_position--;
                if(getCurrent_position<=0)getCurrent_position=0;
                pager.setCurrentItem(getCurrent_position);
                setButton();
            }
        });



        stateProgressBar = (StateProgressBar) root.findViewById(R.id.your_state_progress_bar_id);
        stateProgressBar.setStateDescriptionData(descriptionData);
        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);

        stateProgressBar.setOnStateItemClickListener(new OnStateItemClickListener() {
            @Override
            public void onStateItemClick(StateProgressBar stateProgressBar, StateItem stateItem, int stateNumber, boolean isCurrentState) {
                pager.setCurrentItem(stateNumber-1);
                switch (stateNumber){
                    case 1:stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
                        getCurrent_position=0;
                        break;
                    case 2:stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                        getCurrent_position=1;
                        break;
                    case 3:stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                        getCurrent_position=2;
                        break;
                    case 4:stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
                        getCurrent_position=3;
                        break;

                }
                setButton();

            }
        });




        return root;
    }
    public void setButton(){
        if(getCurrent_position == 0){
            submit.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.ic_arrow_forward_black_24dp));
            cancel.setVisibility(View.INVISIBLE);
        }else if(getCurrent_position == 1 || getCurrent_position == 2){
            submit.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.ic_arrow_forward_black_24dp));
            cancel.setVisibility(View.VISIBLE);
        }else if(getCurrent_position == 3){

            submit.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.ic_save_black_24dp));
            cancel.setVisibility(View.VISIBLE);
        }
    }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position+1){
            case 1:stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
                getCurrent_position=0;
                break;
            case 2:stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                getCurrent_position=1;
                break;
            case 3:stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                getCurrent_position=2;
                break;
            case 4:stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
                getCurrent_position=3;
                break;

        }
        setButton();


    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }



    void getDataFromFragment(){
        progressBar3.setVisibility(View.VISIBLE);
        SubPage1 fg_subpage1 = (SubPage1) adapter.getItem(0);
        SubPage2 fg_subpage2 = (SubPage2) adapter.getItem(1);
        SubPage3 fg_subpage3 = (SubPage3) adapter.getItem(2);
        SubPage4 fg_subpage4 = (SubPage4) adapter.getItem(3);

        if(fg_subpage1 != null && fg_subpage2 != null && fg_subpage3 != null && fg_subpage4 != null){
            int ok = 0;
            if(!fg_subpage1.ValidAll(pager)){
                getCurrent_position = 0;
                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
                ok = 1;
            }
            if(!fg_subpage2.ValidAll(pager)){
                getCurrent_position = 1;
                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                ok = 1;
            }


            if(ok == 0){
                final Map<String, Object> DataUser = new HashMap<>();
                fg_subpage1.ReadData(DataUser);
                fg_subpage2.ReadData(DataUser);
                fg_subpage3.ReadData(DataUser);
                fg_subpage4.ReadData(DataUser);

                DataUser.put("status_on","yes");

                String txt = "";
                txt += "คำนำหน้าชื่อ "+DataUser.get("prefix")+"\n";
                txt += "ชื่อ "+DataUser.get("name")+"\n";
                txt += "นามสกุล "+DataUser.get("lastname")+"\n";
                txt += "ชื่อภาษาอังกฤษ "+DataUser.get("name_eng")+"\n";
                txt += "นามสกุลภาษาอังกฤษ "+DataUser.get("lastname_eng")+"\n";
                txt += "เลขบัตรประชาชน "+DataUser.get("personal_id")+"\n";
                txt += "เบอร์โทร "+DataUser.get("tel")+"\n";
                txt += "ตำแหน่ง "+DataUser.get("position")+"\n";
                txt += "วันเกิด "+DataUser.get("birth_day")+"\n";
                txt += "เงินเดือน "+DataUser.get("salary")+"\n";
                txt += "สถานภาพ "+DataUser.get("status_family")+"\n";
                txt += "สัญชาติ "+DataUser.get("nationality")+"\n";
                txt += "เชื้อชาติ "+DataUser.get("race")+"\n";
                txt += "ศาสนา "+DataUser.get("religion")+"\n";
                txt += "เพศ "+DataUser.get("gender")+"\n";
                txt += "สถานะ "+DataUser.get("type_employees")+"\n";
                txt += "ที่อยู่ปัจจุบัน"+"\n";
                txt += "\t"+DataUser.get("place_now")+" "+DataUser.get("place_now_sub")+"\n"+
                        "\t"+DataUser.get("place_now_dist")+" "+ DataUser.get("place_now_prov")+" "+DataUser.get("place_now_zipcode")+"\n";
                txt += "ที่อยู่ตามทะเบียนบ้าน"+"\n";
                txt += "\t"+DataUser.get("place_part")+" "+DataUser.get("place_part_sub")+"\n"+
                        "\t"+DataUser.get("place_part_dist")+" "+ DataUser.get("place_part_prov")+" "+DataUser.get("place_part_zipcode")+"\n";
                txt += "ข้อมูลการศึกษา\n";
                if(DataUser.get("education") != null){
                    ArrayList<subDatapage3> a = (ArrayList<subDatapage3>) DataUser.get("education");
                    for (subDatapage3 x:a) {
                        txt += "\t"+x.getLevel()+" "+x.getName()+" "+x.getYear()+" "+x.getGrade()+"\n";
                    }
                }else txt += "\tไม่มีข้อมูล";


                txt += "ข้อมูลการทำงาน\n";
                if(DataUser.get("experience") != null){
                    ArrayList<subDatapage4> a = (ArrayList<subDatapage4>) DataUser.get("experience");
                    for (subDatapage4 x:a) {
                        txt += "\t"+x.getCompany()+" "+x.getPosition()+" "+x.getStart()+" "+x.getEnd()+"\n";
                    }
                }else txt += "\tไม่มีข้อมูล";



                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(txt);
                builder.setPositiveButton("บันทึก", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        db.collection("user").document("extension").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                final Map<String, Object> a = documentSnapshot.getData();


                                DataUser.put("status","user");
                                db.collection("user").document(""+a.get("next_id")).set(DataUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(getActivity(),"เพิ่มข้อมูลพนักงานเรียบร้อย",Toast.LENGTH_SHORT).show();

                                        int next = Integer.parseInt((String) a.get("next_id"));
                                        next += 1;
                                        String txt = "";
                                        for(int i=0;i<6-(String.valueOf(next)).length();i++)
                                            txt += "0";
                                        txt += next;

                                        db.collection("user").document("extension").update("next_id",txt);
                                        progressBar3.setVisibility(View.GONE);
                                        getActivity().onBackPressed();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressBar3.setVisibility(View.GONE);
                                        Toast.makeText(getActivity(),"เพิ่มข้อมูลพนักงานไม่สำเร็จ",Toast.LENGTH_SHORT).show();
                                    }
                                });



                            }
                        });

                    }
                });
                builder.setNegativeButton("แก้ไข", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
                builder.show();




            }

        }






    }




}
