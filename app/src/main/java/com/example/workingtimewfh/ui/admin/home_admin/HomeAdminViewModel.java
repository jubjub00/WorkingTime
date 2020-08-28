package com.example.workingtimewfh.ui.admin.home_admin;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeAdminViewModel  {

    private MutableLiveData<String> mText;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<UserStruct> userStructs;
    public HomeAdminViewModel() {


    }


    public LiveData<String> getText() {
        return mText;
    }





}

