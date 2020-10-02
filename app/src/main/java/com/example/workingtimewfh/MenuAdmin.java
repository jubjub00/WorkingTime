package com.example.workingtimewfh;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Toast;

public class MenuAdmin extends AppCompatActivity {


    private AppBarConfiguration mAppBarConfiguration;
    final String PREF_NAME = "LoginPreferences";
    final String KEY_USERNAME = "username";
    final String KEY_PASSWORD = "password";
    final String KEY_STATUS = "status";
    public static SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);
        Toolbar toolbar = findViewById(R.id.toolbar_admin);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.admin_layout);
        NavigationView navigationView = findViewById(R.id.nav_view_admin);



        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.home_admin,R.id.add_user, R.id.nav_edit)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_admin);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        /*switch (item.getItemId()){
            case R.id.logout:

                    sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                    editor = sp.edit();
                    editor.clear().commit();

                    sp = getSharedPreferences("WorkingTime", Context.MODE_PRIVATE);
                    editor = sp.edit();
                    editor.clear().commit();
                    Intent log1 = new Intent(MenuAdmin.this,MainActivity.class );
                    startActivity(log1);
                    finish();




        }*/
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_admin);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}
