package com.example.workingtimewfh.ui.user.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.workingtimewfh.ExtFunction;
import com.example.workingtimewfh.R;

import com.example.workingtimewfh.WorkingList;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.util.Assert;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    String TAG = "HomeFragment";

    View root;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    Button ShowButton;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FusedLocationProviderClient fusedLocationProviderClient;
    double latitude=0.0,longtitude=0.0;
    String addr=null;
    ExtFunction ext;
    ProgressBar inout;
    String workTime,workDate;
    FloatingActionButton AddingWork;
    Map<String, Object> TotalDetailIn = new HashMap<>();
    Map<String, Object> TotalDetailOut = new HashMap<>();
    boolean flag;
    SharedPreferences CountInOut,CheckSameDate ;
    SharedPreferences.Editor EditorCountInOut,EditorCheckSameDate ;



    @SuppressLint("RestrictedApi")
    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        ShowButton = root.findViewById(R.id.check);
        sp = this.getActivity().getSharedPreferences("WorkingTime", Context.MODE_PRIVATE);
        editor = sp.edit();
        ext = new ExtFunction();
        String StatusWork = sp.getString("working_status", "???");
        AddingWork = root.findViewById(R.id.AddingWork);
        CountInOut = this.getActivity().getSharedPreferences("CountInOut", Context.MODE_PRIVATE);
        EditorCountInOut = CountInOut.edit();
        CheckSameDate = this.getActivity().getSharedPreferences("CheckSameDate", Context.MODE_PRIVATE);
        EditorCheckSameDate = CheckSameDate.edit();

        if(ext.GetSameDay(CheckSameDate.getString("OldDate","???"))){
            Log.d("",CheckSameDate.getString("OldDate","???"));
            flag = true;
        }else{
            Log.d("",CheckSameDate.getString("OldDate","???"));
            EditorCountInOut.clear();
            TotalDetailOut.clear();
            TotalDetailIn.clear();
            EditorCheckSameDate.clear();
            flag = false;
            EditorCheckSameDate.putString("OldDate",ext.GetDate());
            EditorCheckSameDate.commit();
        }

        if (StatusWork.equals("???")) {
            ShowButton.setText("เข้างาน");
            ShowButton.setBackground(getResources().getDrawable(R.drawable.inwork));
        } else {
            if (StatusWork.equals("ออกงาน")) {
                final SharedPreferences NextEx = this.getActivity().getSharedPreferences("NextExit", Context.MODE_PRIVATE);

                if(!ext.ActiveExit(NextEx.getString("TimeNext","???"))){
                    ShowButton.setText("รอ 2 นาที");
                    ShowButton.setEnabled(false);

                }else {
                    ShowButton.setText("ออกงาน");
                    ShowButton.setEnabled(true);
                }

                ShowButton.setBackground(getResources().getDrawable(R.drawable.outwork));
                AddingWork.setVisibility(View.VISIBLE);
                AddingWork.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), WorkingList.class );
                        sp = getActivity().getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE);
                        String UserID = sp.getString("KeyDocument","???");
                        intent.putExtra("UserID",UserID);
                        startActivity(intent);
                    }
                });
            } else if (StatusWork.equals("เข้างาน")) {
                ShowButton.setText("เข้างาน");
                ShowButton.setBackground(getResources().getDrawable(R.drawable.inwork));
            }
        }

        ShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

                    final LocationManager manager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE );
                    if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                        Toast.makeText(getActivity(), "กรุณาเปิด GPS", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        getLocation();

                    }
                }else{
                    ActivityCompat.requestPermissions(getActivity() ,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);

                }

            }
        });

        showName(root);
        showDate(root);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                showTime(root);

            }
        }, 0, 1000);

        return root;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 44 && grantResults.length > 0){
            getLocation();
        }else {
            Toast.makeText(getActivity(),"permission denied",Toast.LENGTH_SHORT).show();
        }
    }


    private void getLocation() {

        inout = root.findViewById(R.id.inoutProgress);
        inout.setVisibility(View.VISIBLE);
        final LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        latitude = 0.0;
        longtitude = 0.0;


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.getActivity());
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                fusedLocationProviderClient.removeLocationUpdates(this);
                if(locationResult != null && locationResult.getLocations().size() > 0){

                    try {
                        int lastesLocationIndex = locationResult.getLocations().size() - 1;
                        latitude = locationResult.getLocations().get(lastesLocationIndex).getLatitude();
                        longtitude = locationResult.getLocations().get(lastesLocationIndex).getLongitude();
                        Geocoder geocoder = new Geocoder(getActivity(),Locale.getDefault());
                        List<Address> addresses = null;
                        addresses = geocoder.getFromLocation(latitude,longtitude,1);
                        addr = addresses.get(0).getAddressLine(0);
                        if(latitude != 0.0 && longtitude != 0.0 && addr != null){
                            WorkingTime();
                            inout.setVisibility(View.GONE);
                        }



                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }

            }
        }, Looper.getMainLooper());

    }


    @SuppressLint("RestrictedApi")
    public void WorkingTime() {


        Toast.makeText(getActivity(),"ตำแหน่งของคุณ : "+addr,Toast.LENGTH_LONG).show();
        sp = this.getActivity().getSharedPreferences("WorkingTime", Context.MODE_PRIVATE);
        editor = sp.edit();
        String StatusWork = sp.getString("working_status","???");
        final SharedPreferences NextEx = this.getActivity().getSharedPreferences("NextExit", Context.MODE_PRIVATE);
        final SharedPreferences.Editor EditorNextEx = NextEx.edit();

            if(StatusWork.equals("เข้างาน") || StatusWork.equals("???")){

                AddingWork.setVisibility(View.VISIBLE);
                AddingWork.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), WorkingList.class );
                        sp = getActivity().getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE);
                        String UserID = sp.getString("KeyDocument","???");
                        intent.putExtra("UserID",UserID);
                        startActivity(intent);
                    }
                });
                Map<String, Object> workCheck = new HashMap<>();
                Map<String, Object> work = new HashMap<>();
                Map<String, Object> detail = new HashMap<>();


                detail.put("time",workTime);
                detail.put("latitude",latitude);
                detail.put("longtitude",longtitude);

                Log.d("",""+flag);
                if(flag){
                    EditorCountInOut.putInt("CountIn",CountInOut.getInt("CountIn",0)+1);
                    EditorCountInOut.commit();
                }else{
                    EditorCountInOut.putInt("CountIn",0);
                    EditorCountInOut.commit();
                }
                TotalDetailIn.put(""+CountInOut.getInt("CountIn",0),detail);

                work.put("inwork",TotalDetailIn);
                workCheck.put(workDate, work);

                sp = this.getActivity().getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE);
                db.collection("WorkingTime").document(sp.getString("KeyDocument","???")).set(workCheck,SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {


                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(),"เข้างานสำเร็จ",Toast.LENGTH_SHORT).show();

                        EditorNextEx.clear();
                        EditorNextEx.putString("TimeNext",ext.GetNextExit());
                        EditorNextEx.commit();


                        ShowButton.setText("รอ 2 นาที");
                        ShowButton.setEnabled(false);
                        ShowButton.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ShowButton.setText("ออกงาน");
                                ShowButton.setEnabled(true);
                            }
                        },1000*60*2);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(),"เข้างานไม่สำเร็จ",Toast.LENGTH_SHORT).show();
                    }
                });

                editor.putString("working_status","ออกงาน");
                editor.commit();
                ShowButton.setText("ออกงาน");
                ShowButton.setBackground(getResources().getDrawable(R.drawable.outwork));
            }else if(StatusWork.equals("ออกงาน")){

                if(!ext.ActiveExit(NextEx.getString("TimeNext","???"))){
                    ShowButton.setText("รอ 2 นาที");
                    ShowButton.setEnabled(false);
                    return;
                }else {
                    ShowButton.setText("ออกงาน");
                    ShowButton.setEnabled(true);
                }

                AddingWork.setVisibility(View.INVISIBLE);
                Map<String, Object> workCheck = new HashMap<>();
                Map<String, Object> work = new HashMap<>();
                Map<String, Object> detail = new HashMap<>();


                detail.put("time",workTime);
                detail.put("latitude",latitude);
                detail.put("longtitude",longtitude);

                TotalDetailOut.put(""+CountInOut.getInt("CountIn",0),detail);

                work.put("outwork",TotalDetailOut);
                workCheck.put(workDate, work);

                sp = this.getActivity().getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE);
                db.collection("WorkingTime").document(sp.getString("KeyDocument","???")).set(workCheck, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(),"ออกงานสำเร็จ",Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(),"ออกงานไม่สำเร็จ",Toast.LENGTH_SHORT).show();
                    }
                });



                editor.putString("working_status","เข้างาน");
                editor.commit();
                ShowButton.setText("เข้างาน");
                ShowButton.setBackground(getResources().getDrawable(R.drawable.inwork));
            }




    }



    public void showTime(View root){
        TextView ShowTime;
        ShowTime = root.findViewById(R.id.timeShow);
        workTime = ext.GetTime();
        ShowTime.setText(workTime);

//        showDate(root);
    }

    public void showDate(View root){
        TextView ShowDate;
        ShowDate = root.findViewById(R.id.showDate);
        workDate = ext.GetDate();
        ShowDate.setText(workDate);
    }

    public void showName(View root){
        TextView NameLastName;
        NameLastName = root.findViewById(R.id.NameLastname);
        sp = this.getActivity().getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE);
        String KeyDocument = sp.getString("KeyDocument","NotKey");

        if(!KeyDocument.equals("NotKey")){
            NameLastName.setText(sp.getString("PREFIX","Not Data")+sp.getString("NAME","Not Data")+" "+sp.getString("LASTNAME","Not Data"));
        }else{
            NameLastName.setText("Not found data!!!");
        }
    }





}