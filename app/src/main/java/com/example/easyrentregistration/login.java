package com.example.easyrentregistration;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputLayout;

public class login extends AppCompatActivity {

    Button btn_forget,btn_login_ok,btn_create_new_user;
    TextInputLayout email_login,password_login;
    ProgressBar login_progressbar;

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(login.this);
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
       AlertDialog alertDialog = builder.create();
        builder.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_forget = findViewById(R.id.btn_forget);
        btn_login_ok = findViewById(R.id.btn_login_ok);
        btn_create_new_user = findViewById(R.id.btn_create_new_user);
        email_login = findViewById(R.id.email_login);
        password_login = findViewById(R.id.password_login);
        login_progressbar =  findViewById(R.id.creating_progressbar);

        //when btn_create_new_user is clicked -0
        btn_create_new_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(login.this,create_new_user.class);
                startActivity(i);
                overridePendingTransition(R.anim.silde_in_right, R.anim.slide_out_left);
            }
        });
        //when btn_create_new_user is clicked -1

        //Log-In -0


        //Log-In -1

        //oncreate-1
    }
}