package com.example.easyrentregistration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.auth.GoogleAuthProvider;

public class login extends AppCompatActivity {

    Button btn_forget,btn_login_ok,btn_create_new_user,btn_signin_with_google;
    TextInputLayout email_login,password_login;
    ProgressBar login_progressbar;
    FirebaseAuth mauth;
    GoogleSignInClient mGoogleSignInClient;

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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Toast.makeText(this, "Error! Getting Google's information", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mauth.getCurrentUser();
        if(user != null){
            startActivity(new Intent(login.this,pg_user_profile.class));
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_signin_with_google = findViewById(R.id.btn_signin_with_google);
        btn_forget = findViewById(R.id.btn_forget);
        btn_login_ok = findViewById(R.id.btn_login_ok);
        btn_create_new_user = findViewById(R.id.btn_create_new_user);
        email_login = findViewById(R.id.email_login);
        password_login = findViewById(R.id.password_login);
        login_progressbar =  findViewById(R.id.creating_progressbar);
        mauth = FirebaseAuth.getInstance();

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
        btn_login_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_login.getEditText().getText().toString().trim();
                String password = password_login.getEditText().getText().toString();
                if (email.isEmpty()){
                    email_login.setError("Required");
                    email_login.requestFocus();
                }
                else if (password.isEmpty()){
                    password_login.setError("Required");
                    password_login.requestFocus();
                }
                else if(!email.isEmpty() || !password.isEmpty()){
                    mauth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            if(mauth.getCurrentUser().isEmailVerified()){
                                Intent i = new Intent(login.this,pg_user_profile.class);
                                startActivity(i);
                                finish();
                            }
                            else {
                                Toast.makeText(login.this, "PLease Verify Your Email", Toast.LENGTH_LONG).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(login.this, "Something wrong!!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        //Log-In -1

        //btn_forget is clicked -0
        btn_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_login.getEditText().getText().toString().trim();
                if(!email.isEmpty()){
                    forget_password(email);
                }
                else {
                    email_login.requestFocus();
                    email_login.setError("Enter your Email");
                }
            }
        });
        //btn_forget is clicked -1

        //btn_signin_with_google is clicked -0
        google_signIn_process();
        btn_signin_with_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*

                 */
                    Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                    startActivityForResult(signInIntent, 1000);
            }
        });
        //btn_signin_with_google is clicked -1

        //oncreate-1
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mauth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUserMetadata metadata = mauth.getCurrentUser().getMetadata();
                            if (metadata.getCreationTimestamp() == metadata.getLastSignInTimestamp()) {
                                // The user is new, show them a fancy intro screen!
                                google_signIn_for_newUser();

                            } else {
                                // This is an existing user, show them a welcome back screen.
                                Intent i = new Intent(login.this, pg_user_profile.class);
                                startActivity(i);
                            }


                        } else {
                            Toast.makeText(login.this, "Problem! found in LogIn", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void google_signIn_for_newUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(login.this)
                .setTitle("Donot Press Back While Submitting The Form")
                .setMessage("Press Ok to Create Your account");
        builder.setPositiveButton("create account", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(login.this,create_new_user.class));
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                user.delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    dialog.cancel();
                                }
                            }
                        });

            }
        });
        builder.show();
    }


    private void google_signIn_process() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    private void forget_password(String email) {

        mauth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(login.this, "email sent-Check your Mail", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(login.this, "Account Not Found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }









}