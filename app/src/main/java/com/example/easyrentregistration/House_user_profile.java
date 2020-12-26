package com.example.easyrentregistration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class House_user_profile extends AppCompatActivity {

    BottomNavigationView bottom_bar;


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(House_user_profile.this);
        builder.setTitle("Exit App");
        builder.setCancelable(true);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_user_profile);

        bottom_bar =findViewById(R.id.bottom_bar);


        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new user_profile()).commit();
        bottom_bar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment temp = null;
                switch (item.getItemId()){
                    case R.id.user_profile :
                        temp = new user_profile();
                        break;
                    case R.id.user_settings :
                        temp = new user_settings();
                        break;
                    }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,temp)
                        .commit();
                return true;
            }
        });



    }
}