package com.example.workingtimewfh;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MenuUser extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    final String PREF_NAME = "LoginPreferences";
    final String KEY_USERNAME = "username";
    final String KEY_PASSWORD = "password";
    final String KEY_STATUS = "status";
    final String KEY_DOCUMENT = "KeyDocument";

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    String id;
    CircleImageView imageView;
    String Lastimg;
    StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_user);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        final View headView = navigationView.getHeaderView(0);

        sp = getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        id = sp.getString("KeyDocument","null");

        if(!id.matches("null")){
            FirebaseFirestore.getInstance().collection("user").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Map<String, Object> item = documentSnapshot.getData();
                    final String img = (String) item.get("img_profile");

                    imageView = headView.findViewById(R.id.Profile);
                    if(img == null){
                        imageView.setImageDrawable(getApplication().getDrawable(R.drawable.ic_menu_camera));
                    }else{
                        Lastimg = img;
                        StorageReference islandRef = storageRef.child("user/"+img);
                        final long ONE_MEGABYTE = 1024 * 1024;
                        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes , 0, bytes.length);
                                imageView.setImageBitmap(bitmap);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                imageView.setImageDrawable(getApplication().getDrawable(R.drawable.ic_error_black_24dp));
                            }
                        });
                    }

                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                Intent cam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(cam,1);

                        }
                    });

                    ((TextView)headView.findViewById(R.id.name)).setText(item.get("name")+" "+item.get("lastname")+"\n"+"รหัสพนักงาน "+documentSnapshot.getId());


                }
            });
        }


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_history,
                R.id.nav_change,R.id.nav_leave)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                final Bitmap bp = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(bp);
                FirebaseFirestore.getInstance().collection("user").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Map<String, Object> item = documentSnapshot.getData();
                        final String tmp = FirebaseDatabase.getInstance().getReference("user").push().getKey();
                        item.replace("img_profile",tmp);
                        FirebaseFirestore.getInstance().collection("user").document(id).update(item).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                StorageReference mountainsRef = storageRef.child("user/"+tmp);
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                bp.compress(Bitmap.CompressFormat.PNG, 100, baos);
                                byte[] img = baos.toByteArray();
                                UploadTask uploadTask = mountainsRef.putBytes(img);
                                uploadTask.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        Toast.makeText(getApplication(),"เกิดข้อผิดพลาด",Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        Toast.makeText(getApplication(),"อัพเดตรูปโปรไฟล์เรียบร้อย",Toast.LENGTH_SHORT).show();

                                        StorageReference desertRef = storageRef.child("user/"+Lastimg);
                                        desertRef.delete();
                                    }
                                });


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplication(),"เกิดข้อผิดพลาด",Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                });
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplication(), "Cancelled", Toast.LENGTH_LONG).show();
            }

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    protected void onResume() {
        super.onResume();

        sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();


        if(!sp.getString(KEY_DOCUMENT,"???").equals("???")){

            db.collection("user").document(sp.getString(KEY_DOCUMENT,"???")).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Map<String, Object> a = documentSnapshot.getData();

                    if(!sp.getString(KEY_USERNAME,"???").equals("???") && !sp.getString(KEY_PASSWORD,"???").equals("???") ){
                        if("user".equals(sp.getString(KEY_STATUS,"???")) && ((String)a.get("status_on")).matches("no")) {

                            Toast.makeText(getApplication(),"คุณไม่มีสิทธิ์เข้าใช้งาน",Toast.LENGTH_SHORT).show();
                            finish();
                        }



                    }


                }
            });
        }
    }
}
