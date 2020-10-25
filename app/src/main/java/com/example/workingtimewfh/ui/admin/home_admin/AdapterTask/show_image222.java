package com.example.workingtimewfh.ui.admin.home_admin.AdapterTask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.example.workingtimewfh.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class show_image222 extends AppCompatActivity {

    ViewPager2 viewPager2;
    String[] data;
    ArrayList<Uri> lst_uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image222);

        Intent intent = getIntent();
        data = intent.getStringArrayExtra("data");
        lst_uri = new ArrayList<>();
        viewPager2 = findViewById(R.id.list);

        final ProgressDialog dialog = ProgressDialog.show(this, "", "Loading. Please wait...", true);
        FirebaseStorage.getInstance().getReference("task").listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {

                List<StorageReference> it = listResult.getItems();//.iterator();
                Iterator<StorageReference> item = it.iterator();
                while (item.hasNext()){

                   final StorageReference dat = item.next();


                    for(int i = 0;i<data.length;i++ ){
                        if(dat.getName().matches(data[i])){

                            dat.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    lst_uri.add(uri);


                                    adapterImage ad = new adapterImage(lst_uri);
                                    viewPager2.setAdapter(ad);

                                }
                            });

                        }
                    }

                    adapterImage ad = new adapterImage(lst_uri);
                    viewPager2.setAdapter(ad);
                }

                adapterImage ad = new adapterImage(lst_uri);
                viewPager2.setAdapter(ad);
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });


    }
}