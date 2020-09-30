package com.example.workingtimewfh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    final String PREF_NAME = "LoginPreferences";
    final String KEY_USERNAME = "username";
    final String KEY_PASSWORD = "password";
    final String KEY_STATUS = "status";
    final String KEY_DOCUMENT = "KeyDocument";


    SharedPreferences sp;
    SharedPreferences.Editor editor;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String status ;
    String usern,password;
    View progressBar ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
        Log.d("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",sp.getString(KEY_DOCUMENT,"???"));

        if(!sp.getString(KEY_DOCUMENT,"???").equals("???")){
            final ProgressDialog dialog = ProgressDialog.show(this, "", "Loading. Please wait...", true);

            db.collection("user").document(sp.getString(KEY_DOCUMENT,"???")).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Map<String, Object> a = documentSnapshot.getData();

                        if(!sp.getString(KEY_USERNAME,"???").equals("???") && !sp.getString(KEY_PASSWORD,"???").equals("???") ){
                            if("user".equals(sp.getString(KEY_STATUS,"???")) && ((String)a.get("status_on")).matches("yes")) {
                                dialog.dismiss();
                                Intent log = new Intent(MainActivity.this, MenuUser.class);
                                startActivity(log);
                                finish();
                            }else if("admin".equals(sp.getString(KEY_STATUS,"???"))){
                                dialog.dismiss();
                                Intent log1 = new Intent(MainActivity.this, MenuAdmin.class);
                                startActivity(log1);
                                finish();
                            }else{
                                dialog.dismiss();
                                Toast.makeText(getApplication(),"คุณไม่มีสิทธิ์เข้าใช้งาน",Toast.LENGTH_SHORT).show();
                            }


                        }


                }
            });
        }




    }

    @Override
    public void onClick(View v) {
        usern = ((EditText)(findViewById(R.id.edtuser))).getText().toString();
        password = ((EditText)(findViewById(R.id.edtpass))).getText().toString();
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        db.collection("user").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                String TAG="MainActivity";

                if (task.isSuccessful()) {

                    int correct=0;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if(usern.equals(document.getString("username")) && password.equals(document.getString("password")) && document.getString("status_on").matches("yes") ){
                            correct = 1;


                            status = document.getString("status");
                            editor.putString(KEY_USERNAME,usern);
                            editor.putString(KEY_PASSWORD,password);
                            editor.putString(KEY_STATUS,status);

                            editor.putString(KEY_DOCUMENT,document.getId());
                            editor.putString("NAME",document.getString("name"));
                            editor.putString("LASTNAME",document.getString("lastname"));
                            editor.putString("PREFIX",document.getString("prefix"));

                            editor.commit();
                        }else if(usern.equals(document.getString("username")) && password.equals(document.getString("password")) && document.getString("status_on").matches("no") ){
                            correct = 2;
                        }

                    }
                    if(correct == 0 || correct == 2){

                        if(correct == 2)
                            Toast.makeText(MainActivity.this,"คุณไม่มีสิทธิ์เข้าใช้งาน",Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(MainActivity.this,"ชื่อผู้ใช้หรือรหัสผ่านไม่ถูกต้อง",Toast.LENGTH_SHORT).show();

                        progressBar.setVisibility(View.INVISIBLE);
                    }
                } else {
                    Log.w(TAG, "Error getting data.", task.getException());
                }



                if("user".equals(status)) {
                    Intent log = new Intent(MainActivity.this, MenuUser.class);
                    startActivity(log);
                    finish();

                }else if("admin".equals(status)){
                    Intent log1 = new Intent(MainActivity.this, MenuAdmin.class);
                    startActivity(log1);
                    finish();
                }
            }
        });

    }

    public void onBackPressed() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Exit");
        dialog.setIcon(R.drawable.clock);
        dialog.setCancelable(true);
        dialog.setMessage("Do you want to exit?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        dialog.show();

    }



}
