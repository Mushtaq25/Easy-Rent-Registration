package com.example.easyrentregistration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class create_new_user extends AppCompatActivity {

    TextView tv_set_house_type;
    AutoCompleteTextView spinner_PG_type_new_user, spinner_Food_type_new_user, spinner_house_type_new_user, spinner_house_guest_type_new_user, spinner_house_guest_Relegion_type_new_user, spinner_location_new_user;
    ScrollView sv_House_name,sv_house_type,sv_email_password,sv_PG_name,sv_pg_details,sv_house_detail,sv_location,sv_congrulation;
    Button btn_next_location,btn_select_PG,btn_select_House_Rent,btn_next_create_new_user,btn_next_PG_contact_number
            ,btn_next_PG_type,btn_next_House_type,btn_next_newuser_tologIn_page,btn_next_House_contact_number;
    TextInputLayout PG_name_new_user,PG_owner_name_new_user,PG_contact_number_new_user,email_new_user,password_new_user,House_name_new_user,House_owner_name_new_user,
            House_contact_number_new_user,PG_BreakFast_item_new_user,PG_Lunch_item_new_user,PG_Dinner_item_new_user
            ,typed_location_new_user,spinner_tv_PG_type_new_user,spinner_tv_Food_type_new_user
            ,spinner_tv_location_new_user,spinner_tv_house_type_new_user,spinner_tv_house_guest_type_new_user,
            spinner_tv_house_guest_Relegion_type_new_user;
    ProgressBar creating_progressbar;
    private FirebaseAuth mAuth;
    FirebaseFirestore fstore ;
    private StorageReference mStore;

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {

        if (sv_email_password.getVisibility() == View.VISIBLE){
            Intent i = new Intent(create_new_user.this,login.class);
            startActivity(i);
            finish();
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(create_new_user.this);
            builder.setTitle("Form Cannot be Re-Submmited");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.create();
            builder.show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_user);

        spinner_tv_house_type_new_user = findViewById(R.id.spinner_tv_house_type_new_user);
        spinner_tv_house_guest_type_new_user = findViewById(R.id.spinner_tv_house_guest_type_new_user);
        spinner_tv_house_guest_Relegion_type_new_user = findViewById(R.id.spinner_tv_house_guest_Relegion_type_new_user);
        tv_set_house_type  = findViewById(R.id.tv_set_house_type);
        btn_next_location =findViewById(R.id.btn_next_location);
        spinner_tv_location_new_user =findViewById(R.id.spinner_tv_location_new_user);
        spinner_tv_Food_type_new_user = findViewById(R.id.spinner_tv_Food_type_new_user);
        spinner_tv_PG_type_new_user = findViewById(R.id.spinner_tv_PG_type_new_user);
        PG_name_new_user = findViewById(R.id.PG_name_new_user);
        PG_owner_name_new_user = findViewById(R.id.PG_owner_name_new_user);
        PG_contact_number_new_user = findViewById(R.id.PG_contact_number_new_user);
        sv_House_name =findViewById(R.id.sv_House_name);
        sv_house_type = findViewById(R.id.sv_house_type);
        sv_email_password = findViewById(R.id.sv_email_password);
        sv_PG_name = findViewById(R.id.sv_PG_name);
        sv_pg_details = findViewById(R.id.sv_pg_details);
        sv_house_detail = findViewById(R.id.sv_house_detail);
        sv_location = findViewById(R.id.sv_location);
        sv_congrulation = findViewById(R.id.sv_congrulation);
        creating_progressbar = findViewById(R.id.creating_progressbar);
        btn_select_PG = findViewById(R.id.btn_select_PG);
        btn_select_House_Rent = findViewById(R.id.btn_select_House_Rent);
        btn_next_PG_contact_number = findViewById(R.id.btn_next_PG_contact_number);
        btn_next_create_new_user = findViewById(R.id.btn_next_create_new_user);
        btn_next_House_contact_number = findViewById(R.id.btn_next_House_contact_number);
        btn_next_PG_type = findViewById(R.id.btn_next_PG_type);
        btn_next_House_type = findViewById(R.id.btn_next_House_type);
        btn_next_newuser_tologIn_page = findViewById(R.id.btn_next_newuser_tologIn_page);
        email_new_user = findViewById(R.id.email_new_user);
        password_new_user = findViewById(R.id.password_new_user);
        House_name_new_user = findViewById(R.id.House_name_new_user);
        House_owner_name_new_user = findViewById(R.id.House_owner_name_new_user);
        House_contact_number_new_user = findViewById(R.id.House_contact_number_new_user);
        PG_BreakFast_item_new_user = findViewById(R.id.PG_BreakFast_item_new_user);
        PG_Lunch_item_new_user = findViewById(R.id.PG_Lunch_item_new_user);
        PG_Dinner_item_new_user = findViewById(R.id.PG_Dinner_item_new_user);
        typed_location_new_user = findViewById(R.id.typed_location_new_user);
        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        mStore = FirebaseStorage.getInstance().getReference();

        //spinner pg type -0
        spinner_PG_type_new_user = findViewById(R.id.spinner_PG_type_new_user);
        String[] option = {"Girls Paying Guest", "Boys Paying Guest"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.dropdown_item, option);
        spinner_PG_type_new_user.setText(arrayAdapter.getItem(0).toString(), false);
        spinner_PG_type_new_user.setAdapter(arrayAdapter);

        spinner_Food_type_new_user = findViewById(R.id.spinner_Food_type_new_user);
        String[] option1 = {"NON-VEG", "VEG"};
        ArrayAdapter arrayAdapter1 = new ArrayAdapter(this, R.layout.dropdown_item, option1);
        spinner_Food_type_new_user.setText(arrayAdapter1.getItem(0).toString(), false);
        spinner_Food_type_new_user.setAdapter(arrayAdapter1);

        spinner_house_type_new_user = findViewById(R.id.spinner_house_type_new_user);
        String[] option2 = {"Assam Type House", "RCC Building","FLAT"};
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, R.layout.dropdown_item, option2);
        spinner_house_type_new_user.setText(arrayAdapter2.getItem(0).toString(), false);
        spinner_house_type_new_user.setAdapter(arrayAdapter2);

        spinner_house_guest_type_new_user = findViewById(R.id.spinner_house_guest_type_new_user);
        String[] option3 = {"Family Guest", "Only Man", "Only Women"};
        ArrayAdapter arrayAdapter3 = new ArrayAdapter(this, R.layout.dropdown_item, option3);
        spinner_house_guest_type_new_user.setText(arrayAdapter3.getItem(0).toString(), false);
        spinner_house_guest_type_new_user.setAdapter(arrayAdapter3);

        spinner_house_guest_Relegion_type_new_user = findViewById(R.id.spinner_house_guest_Relegion_type_new_user);
        String[] option4 = {"Any-Relegion", "Hindu", "Muslim", "Christain"};
        ArrayAdapter arrayAdapter4 = new ArrayAdapter(this, R.layout.dropdown_item, option4);
        spinner_house_guest_Relegion_type_new_user.setText(arrayAdapter4.getItem(0).toString(), false);
        spinner_house_guest_Relegion_type_new_user.setAdapter(arrayAdapter4);

        spinner_location_new_user = findViewById(R.id.spinner_location_new_user);
        String[] option5 = {"Baksa", "Barpeta", "Biswanath Chariali", "Bongaigaon", "Cachar", "Charaideo", "Chirang", "Darrang", "Dhemaji", "Dhubri", "Dibrugarh", "Dima Hasao", "Goalpara","Guwahati", "Golaghat", "Hailakandi", "Hojai", "Jorhat", "Kamrup", "Karbi Anglong – East", "Karbi Anglong – West", "Karimganj", "Kokrajhar", "North Lakhimpur", "Majuli", "Morigaon", "Nagaon", "Nalbari", "Sivasagar", "Sonitpur", "Hatsingimari", "Tinsukia", "Udalguri"};
        ArrayAdapter arrayAdapter5 = new ArrayAdapter(this, R.layout.dropdown_item, option5);
        spinner_location_new_user.setText(arrayAdapter5.getItem(0).toString(), false);
        spinner_location_new_user.setAdapter(arrayAdapter5);

//spinner pg type -1

        //create user with email and password -0
        sv_email_password.setVisibility(View.VISIBLE);
        btn_next_create_new_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                creating_progressbar.setVisibility(View.VISIBLE);
                String email = email_new_user.getEditText().getText().toString().trim();
                String password = password_new_user.getEditText().getText().toString().trim();

                 if(email.isEmpty()){
                     email_new_user.setError("Required");
                     creating_progressbar.setVisibility(View.GONE);
                 }
                else if(password.isEmpty()){
                    password_new_user.setError("Required");
                     creating_progressbar.setVisibility(View.GONE);
                }
                 else if(password.length() < 8){
                     password_new_user.setError("password should contain min 8 letters");
                     creating_progressbar.setVisibility(View.GONE);
                 }
                 else if(!password_new_user.getEditText().getText().equals("") && password.length() >= 8 && !email_new_user.getEditText().getText().equals("")){

                     mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                         @Override
                         public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                sv_email_password.setVisibility(View.GONE);
                                creating_progressbar.setVisibility(View.GONE);
                                sv_house_type.setVisibility(View.VISIBLE);
                            }
                            else {
                                 creating_progressbar.setVisibility(View.GONE);
                                 Toast.makeText(create_new_user.this, "Failed! contact developer", Toast.LENGTH_SHORT).show();
                            }
                         }
                     });
                 }
            }
        });
        //create user with email and password -1

        //btn_next_newuser_tologIn_page is clicked -0
        btn_next_newuser_tologIn_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sv_congrulation.setVisibility(View.GONE);

                Intent i = new Intent(create_new_user.this,login.class);
                startActivity(i);
                finish();

            }
        });
        //btn_next_newuser_tologIn_page is clicked -1

//btn_next_location is clicked -0
        btn_next_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tv_set_house_type.getText() == "PG"){

                    String user_district = spinner_tv_location_new_user.getEditText().getText().toString();
                    String location = typed_location_new_user.getEditText().getText().toString();

                    Map<String, Object> user = new HashMap<>();
                    user.put("house_location",user_district +","+location );

                    fstore.collection("Market").document(mAuth.getCurrentUser().getUid())
                            .set(user,SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(create_new_user.this, "market-added", Toast.LENGTH_SHORT).show();
                        }
                    });

                    fstore.collection("Pg_Owner_Details").document(mAuth.getCurrentUser().getEmail())
                            .set(user, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            sv_location.setVisibility(View.GONE);
                            sv_congrulation.setVisibility(View.VISIBLE);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(create_new_user.this, "Something Wrong!! contact developer", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                else {

                    String user_district = spinner_tv_location_new_user.getEditText().getText().toString();
                    String location = typed_location_new_user.getEditText().getText().toString();

                    Map<String, Object> user = new HashMap<>();
                    user.put("house_location",user_district +","+location );

                    fstore.collection("Market").document(mAuth.getCurrentUser().getUid())
                            .set(user,SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(create_new_user.this, "market-added", Toast.LENGTH_SHORT).show();
                        }
                    });

                    fstore.collection("House_Owner_Details").document(mAuth.getCurrentUser().getEmail())
                            .set(user, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            sv_location.setVisibility(View.GONE);
                            sv_congrulation.setVisibility(View.VISIBLE);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(create_new_user.this, "Something Wrong!! contact developer", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
//btn_next_location is clicked -1


//creating user for paying guest -0

        btn_next_PG_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(create_new_user.this, spinner_tv_PG_type_new_user.getEditText().getText(), Toast.LENGTH_SHORT).show();
                String PG_type = spinner_tv_PG_type_new_user.getEditText().getText().toString();
                String Type_of_food = spinner_tv_Food_type_new_user.getEditText().getText().toString();
                String breakfast_item = PG_BreakFast_item_new_user.getEditText().getText().toString();
                String lunch_item = PG_Lunch_item_new_user.getEditText().getText().toString();
                String dinner_item = PG_Dinner_item_new_user.getEditText().getText().toString();

                Map<String, Object> user = new HashMap<>();
                user.put("PG_type-",PG_type );
                user.put("Type_of_food",Type_of_food);
                user.put("breakfast_item",breakfast_item);
                user.put("lunch_item",lunch_item);
                user.put("dinner_item",dinner_item);

                fstore.collection("Market").document(mAuth.getCurrentUser().getUid())
                        .set(user,SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(create_new_user.this, "market -added", Toast.LENGTH_SHORT).show();
                    }
                });

                fstore.collection("Pg_Owner_Details").document(mAuth.getCurrentUser().getEmail())
                        .set(user, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        sv_pg_details.setVisibility(View.GONE);
                        sv_location.setVisibility(View.VISIBLE);
                        tv_set_house_type.setText("PG");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(create_new_user.this, "Something Wrong!! contact developer", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btn_next_PG_contact_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String PG_name = PG_name_new_user.getEditText().getText().toString();
                String Owner_full_name = PG_owner_name_new_user.getEditText().getText().toString();
                String PG_contact_number = PG_contact_number_new_user.getEditText().getText().toString();
                String user_Id = mAuth.getCurrentUser().getUid();

                Map<String, Object> user = new HashMap<>();
                user.put("PG_Name-",PG_name );
                user.put("Owner_full_name",Owner_full_name);
                user.put("PG_contact_number",PG_contact_number);
                user.put("user_Id",user_Id);

                fstore.collection("Market").document(mAuth.getCurrentUser().getUid())
                        .set(user,SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(create_new_user.this, "market-added", Toast.LENGTH_SHORT).show();
                    }
                });

                fstore.collection("Pg_Owner_Details").document(mAuth.getCurrentUser().getEmail())
                        .set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        sv_PG_name.setVisibility(View.GONE);
                        sv_pg_details.setVisibility(View.VISIBLE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(create_new_user.this, "Something Wrong!! contact developer", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        //when btn Paying Guest is clicked -0
        btn_select_PG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sv_house_type.setVisibility(View.GONE);
                sv_PG_name.setVisibility(View.VISIBLE);

            }
        });
        //when btn Paying Guest is clicked -1
//creating user for paying guest -1



// creating user for House rent -0

        btn_next_House_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String House_Type = spinner_tv_house_type_new_user.getEditText().getText().toString();
                String Guest_type = spinner_tv_house_guest_type_new_user.getEditText().getText().toString();
                String Guest_Relegion = spinner_tv_house_guest_Relegion_type_new_user.getEditText().getText().toString();

                Map<String, Object> user = new HashMap<>();
                user.put("House_Type-",House_Type );
                user.put("Guest_type",Guest_type);
                user.put("Guest_Relegion",Guest_Relegion);

                fstore.collection("Market").document(mAuth.getCurrentUser().getUid())
                        .set(user,SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(create_new_user.this, "market added", Toast.LENGTH_SHORT).show();
                    }
                });

                fstore.collection("House_Owner_Details").document(mAuth.getCurrentUser().getEmail())
                        .set(user,SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        sv_House_name.setVisibility(View.GONE);
                        sv_house_detail.setVisibility(View.GONE);
                        sv_location.setVisibility(View.VISIBLE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(create_new_user.this, "Something Wrong!! contact developer", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        btn_next_House_contact_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String House_name = House_name_new_user.getEditText().getText().toString();
                String Owner_full_name = House_owner_name_new_user.getEditText().getText().toString();
                String PG_contact_number = House_contact_number_new_user.getEditText().getText().toString();
                String user_Id = mAuth.getCurrentUser().getUid();

                Map<String, Object> user = new HashMap<>();
                user.put("House_Name-",House_name );
                user.put("Owner_full_name",Owner_full_name);
                user.put("House_contact_number",PG_contact_number);
                user.put("user_Id",user_Id);

                fstore.collection("Market").document(mAuth.getCurrentUser().getUid())
                        .set(user,SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(create_new_user.this, "market added", Toast.LENGTH_SHORT).show();
                    }
                });

                fstore.collection("House_Owner_Details").document(mAuth.getCurrentUser().getEmail())
                        .set(user,SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        sv_House_name.setVisibility(View.GONE);
                        sv_house_detail.setVisibility(View.VISIBLE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(create_new_user.this, "Something Wrong!! contact developer", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        //when btn House Guest is clicked -0
        btn_select_House_Rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sv_house_type.setVisibility(View.GONE);
                sv_House_name.setVisibility(View.VISIBLE);

            }
        });
        //when btn House Guest is clicked -1
// creating user for House rent -1

        //on create -1
    }
}