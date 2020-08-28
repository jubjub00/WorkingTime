package com.example.workingtimewfh.ui.user.home;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.workingtimewfh.MainActivity;
import com.example.workingtimewfh.MenuAdmin;
import com.example.workingtimewfh.MenuUser;
import com.example.workingtimewfh.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;


    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");

    }


    public LiveData<String> getText() {
        return mText;
    }





}