package com.example.workingtimewfh.ui.admin.home_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workingtimewfh.R;
import com.example.workingtimewfh.ui.admin.home_admin.AdapterTask.TaskStruct;
import com.example.workingtimewfh.ui.admin.home_admin.AdapterTask.adapterTask;
import com.example.workingtimewfh.ui.admin.home_admin.AdapterTask.downloadImg;
import com.example.workingtimewfh.ui.admin.home_admin.AdapterTime.adapterTime;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Detail_working_task extends AppCompatActivity {

    private String date,type,id;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView recyclerView;
    private TextView date_show;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_working_task);

        Intent intent = getIntent();
        date = intent.getStringExtra("date");
        type = intent.getStringExtra("type");
        id = intent.getStringExtra("id");
        recyclerView = findViewById(R.id.RecycleDetail);
        date_show = findViewById(R.id.date);



        if(type.matches("time")){
            date_show.setVisibility(View.GONE);
            db.collection("WorkingTime").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Map<String, Object> GetInOut =  documentSnapshot.getData();
                    Map<String, Object> InOut = (Map<String, Object>) GetInOut.get(date);

                    if(InOut != null){
                        Map<String, Object> in = (Map<String, Object>)InOut.get("inwork");
                        Map<String, Object> out = (Map<String, Object>)InOut.get("outwork");
                        int is=0,os=0;
                        if(in != null) is = in.size();
                        if(out != null) os = out.size();



                        ArrayList<TimeStruct> show = new ArrayList<>();
                        if(is == os){

                            Iterator<String> i = in.keySet().iterator();
                            Iterator<String> o = out.keySet().iterator();
                            ArrayList<TimeStruct> inWork = new ArrayList<>();
                            ArrayList<TimeStruct> outWork = new ArrayList<>();

                            while(i.hasNext()){
                                String i_ = i.next();
                                Map<String, Object> inworking = (Map<String, Object>)in.get(i_);
                                inWork.add(new TimeStruct("เข้างาน",(String)inworking.get("time"),(double)inworking.get("latitude"),(double)inworking.get("longtitude")));
                            }

                            while(o.hasNext()){
                                String i_ = o.next();
                                Map<String, Object> outworking = (Map<String, Object>)out.get(i_);
                                outWork.add(new TimeStruct("ออกงาน",(String)outworking.get("time"),(double)outworking.get("latitude"),(double)outworking.get("longtitude")));
                            }

                            int t_i = 0,t_o = 0;
                            for (int ii = 0 ;ii<is+os;ii++) {
                                if(ii%2 == 0){
                                    show.add(inWork.get(t_i));
                                    t_i++;
                                }else{
                                    show.add(outWork.get(t_o));
                                    t_o++;
                                }
                            }

                        }else{

                            if(is == 0){
                                Iterator<String> o = out.keySet().iterator();
                                ArrayList<TimeStruct> outWork = new ArrayList<>();

                                while(o.hasNext()){
                                    String i_ = o.next();
                                    Map<String, Object> outworking = (Map<String, Object>)out.get(i_);
                                    outWork.add(new TimeStruct("ออกงาน",(String)outworking.get("time"),(double)outworking.get("latitude"),(double)outworking.get("longtitude")));
                                }

                                for (int ii = 0 ;ii<os;ii++) {
                                    show.add(outWork.get(ii));

                                }

                            }else if(os == 0){
                                Iterator<String> i = in.keySet().iterator();
                                ArrayList<TimeStruct> inWork = new ArrayList<>();

                                while(i.hasNext()){
                                    String i_ = i.next();
                                    Map<String, Object> inworking = (Map<String, Object>)in.get(i_);
                                    inWork.add(new TimeStruct("เข้างาน",(String)inworking.get("time"),(double)inworking.get("latitude"),(double)inworking.get("longtitude")));
                                }

                                for (int ii = 0 ;ii<is;ii++) {
                                        show.add(inWork.get(ii));
                                }

                            }else{


                                Iterator<String> i = in.keySet().iterator();
                                Iterator<String> o = out.keySet().iterator();
                                ArrayList<TimeStruct> inWork = new ArrayList<>();
                                ArrayList<TimeStruct> outWork = new ArrayList<>();

                                while(i.hasNext()){
                                    String i_ = i.next();
                                    Map<String, Object> inworking = (Map<String, Object>)in.get(i_);
                                    inWork.add(new TimeStruct("เข้างาน",(String)inworking.get("time"),(double)inworking.get("latitude"),(double)inworking.get("longtitude")));
                                }

                                while(o.hasNext()){
                                    String i_ = o.next();
                                    Map<String, Object> outworking = (Map<String, Object>)out.get(i_);
                                    outWork.add(new TimeStruct("ออกงาน",(String)outworking.get("time"),(double)outworking.get("latitude"),(double)outworking.get("longtitude")));
                                }

                                int t_i = 0,t_o = 0;

                                if(is > os){
                                    for (int ii = 0 ;ii<is+os;ii++) {
                                        if(ii%2 == 0){
                                            show.add(inWork.get(t_i));
                                            t_i++;
                                        }else{
                                            show.add(outWork.get(t_o));
                                            t_o++;
                                        }
                                    }
                                }else{
                                    for (int ii = 0 ;ii<is+os;ii++) {
                                        if(ii%2 == 1){
                                            show.add(inWork.get(t_i));
                                            t_i++;
                                        }else{
                                            show.add(outWork.get(t_o));
                                            t_o++;
                                        }
                                    }
                                }
                            }
                        }

                        adapterTime adapter = new adapterTime(show);
                        adapter.SetOnClickItem(new adapterTime.Onclicked() {
                            @Override
                            public void Clicked(double lat, double lon) {

                                Geocoder geocoder = new Geocoder(getApplication(), Locale.getDefault());
                                List<Address> addresses = null;
                                try {
                                    addresses = geocoder.getFromLocation(14.1701683,101.3597539,1);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                String addr = addresses.get(0).getAddressLine(0);

                                Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194?z=10&q="+addr);
                                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                mapIntent.setPackage("com.google.android.apps.maps");
                                startActivity(mapIntent);

                            }
                        });
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));





                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });





        }else{
            date_show.setVisibility(View.VISIBLE);
            date_show.setText("รายละเอียดการทำงาน วันที่ "+date);
            db.collection("task").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Map<String, Object> AllTask =  documentSnapshot.getData();
                    if(AllTask == null){
                        Toast.makeText(getApplication(),"ไม่มีข้อมูล",Toast.LENGTH_LONG).show();
                        return;
                    }
                    Map<String, Object> TaskTarget = (Map<String, Object>) AllTask.get(date);
                    if(TaskTarget != null){
                        ArrayList<TaskStruct> show = new ArrayList<>();
                        Iterator<String> AllTime = TaskTarget.keySet().iterator();
                        while(AllTime.hasNext()){
                            String time = AllTime.next();

                            Map<String, Object> each_time = (Map<String, Object>) TaskTarget.get(time);
                            ArrayList y = (ArrayList) each_time.get("id_img");
                            final ArrayList<Bitmap> img = new ArrayList<>();
                            if(y != null){
                                for (Object yy : y){
                                    StorageReference islandRef = FirebaseStorage.getInstance().getReference().child("task/"+yy);
                                    final long ONE_MEGABYTE = 1024 * 1024;
                                    islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                        @Override
                                        public void onSuccess(byte[] bytes) {
                                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes , 0, bytes.length);
                                            img.add(bitmap);
                                        }
                                    });

                                }
                            }

                            show.add(new TaskStruct(time,each_time.get("head").toString(),each_time.get("location").toString(),each_time.get("detail").toString()
                            , img ,(double)each_time.get("latitude"),(double)each_time.get("longtitude")));


                        }

                        adapterTask adapter = new adapterTask(show);
                        adapter.SetOnClicked(new adapterTask.OnClicked() {
                            @Override
                            public void Clicked(double lat, double lon) {
                                Geocoder geocoder = new Geocoder(getApplication(), Locale.getDefault());
                                List<Address> addresses = null;
                                try {
                                    addresses = geocoder.getFromLocation(lat,lon,1);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                String addr = addresses.get(0).getAddressLine(0);

                                Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194?z=10&q="+addr);
                                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                mapIntent.setPackage("com.google.android.apps.maps");
                                startActivity(mapIntent);
                            }
                        });
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));



                    }else{
                        Toast.makeText(getApplication(),"ไม่มีข้อมูล",Toast.LENGTH_LONG).show();
                    }



                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });





        }

    }
}
