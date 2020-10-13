package com.example.workingtimewfh.ui.admin.home_admin.AdapterTask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class downloadImg {
    private ArrayList<Bitmap> data= new ArrayList<>();

    public synchronized void download(String yy) {

    }

    public ArrayList<Bitmap> value() {
        return data;
    }
}
