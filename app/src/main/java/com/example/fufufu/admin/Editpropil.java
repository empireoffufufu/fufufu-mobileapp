package com.example.fufufu.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.fufufu.Login;
import com.example.fufufu.R;
import com.example.fufufu.mahasiswa.MainActivity2;
import com.example.fufufu.model.Profil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import android.support.design.widget.TextInputLayout;
import android.support.design.widget.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Editpropil extends AppCompatActivity {


    private static final String TAG = "";
    private Button btnSave,btnCancel;
    EditText nim, nama, alamat, noHP, kelas,email_et;
    TextView grup;
    String email_txt,password_txt,grupstatus,Kkey,Pemail,Ppasword;
    private TextInputEditText password_et;
    private TextInputLayout passwrod_ett;

    private ProgressDialog loading;
    private DatabaseReference database;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();



    public Editpropil()
    {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprofil);


        database = FirebaseDatabase.getInstance().getReference();


        Intent intent=getIntent();
        String Pnim = intent.getStringExtra("nim");
        String Pnama = intent.getStringExtra("nama");
        String Pkelas= intent.getStringExtra("kelas");
        String Palamat = intent.getStringExtra("alamat");
        String Pnohp= intent.getStringExtra("nohp");
        Pemail= intent.getStringExtra("email");
        Ppasword= intent.getStringExtra("password");

        Kkey=intent.getStringExtra("id");

        email_et=findViewById(R.id.emailET);
        password_et=findViewById(R.id.passwordET);
        nim = findViewById(R.id.nim);
        nama = findViewById(R.id.nama);
        kelas = findViewById(R.id.kelas);
        alamat = findViewById(R.id.alamat);
        noHP = findViewById(R.id.noHP);
        grup=findViewById(R.id.grup);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        passwrod_ett=findViewById(R.id.passwordETT);

        nim.setText(Pnim);
        nama.setText(Pnama);
        kelas.setText(Pkelas);
        alamat.setText(Palamat);
        noHP.setText(Pnohp);
        email_et.setText(Pemail);
        password_et.setText(Ppasword);



            FirebaseUser userr=mAuth.getCurrentUser();
            if(userr.getEmail().equals("novalina351@gmail.com"))
            {
                mAuth.signOut();

            }
            else
            {


            }

        mAuth.signInWithEmailAndPassword(Pemail,Ppasword)
                .addOnCompleteListener(Editpropil.this, new OnCompleteListener<AuthResult>() {


                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                    }
                });




        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String Snim = nim.getText().toString();
                String Snama = nama.getText().toString();
                String Salamat = alamat.getText().toString();
                String SnoHP = noHP.getText().toString();
                String Skelas = kelas.getText().toString();
                email_txt = email_et.getText().toString();
                password_txt = password_et.getText().toString();
                grupstatus = grup.getText().toString();


                    if (Snim.equals("")) {
                        nim.setError("Silahkan masukkan NIM");
                        nim.requestFocus();
                    } else if (Snama.equals("")) {
                        nama.setError("Silahkan masukkan Nama ");
                        nama.requestFocus();
                    } else if (Salamat.equals("")) {
                        alamat.setError("Silahkan masukkan alamat");
                        alamat.requestFocus();
                    } else if (SnoHP.equals("")) {
                        noHP.setError("Silahkan masukkan No.Hp");
                        noHP.requestFocus();
                    } else if (Skelas.equals("")) {
                        kelas.setError("Silahkan masukkan Kelas");
                        kelas.requestFocus();
                    } else
                {
                    loading = ProgressDialog.show(Editpropil.this,
                            null,
                            "Please wait...",
                            true,
                            false);



                    editUser(new Profil(
                            Snim.toLowerCase(),
                            Snama.toString(),
                            Skelas.toUpperCase(),
                            Salamat.toString(),
                            SnoHP.toLowerCase(),
                            email_txt.toString(),
                            password_txt.toString(),
                            grupstatus.toLowerCase()));



                }
                finish();
            }


        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseUser mUser = mAuth.getCurrentUser();


                    database.child("Profil").child(Kkey).removeValue();
                    mUser.delete();


                    Toast.makeText(Editpropil.this, "Data Terhapus : "+mUser.getEmail(), Toast.LENGTH_SHORT).show();
                FirebaseUser userr=mAuth.getCurrentUser();
                mAuth.signInWithEmailAndPassword("novalina351@gmail.com","admin123")
                        .addOnCompleteListener(Editpropil.this, new OnCompleteListener<AuthResult>() {


                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                            }
                        });
                    finish();



            }
        });

    }



    private void editUser(Profil profil) {
        FirebaseUser mUser = mAuth.getCurrentUser();

        UserProfileChangeRequest profileUpdates =   new UserProfileChangeRequest.Builder()
                .setDisplayName(nim.getText().toString()).build();
        mUser.updateProfile(profileUpdates);
        mUser.updateEmail(email_et.getText().toString());
        mUser.updatePassword(password_et.getText().toString());

        if(mUser.isEmailVerified()) {

        }else
        {
            mUser.sendEmailVerification();
        }
        database.child("Profil")
                .child(nim.getText().toString())
                .setValue(profil)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        loading.dismiss();

                        Toast.makeText(Editpropil.this, "Data Berhasil diperbarui " ,
                                Toast.LENGTH_LONG).show();
                        finish();

                    }

                });
        if(nim.getText().toString().equals(Kkey))
        {

        }
        else
        {
            database.child("Profil").child(Kkey).removeValue();
        }
        mAuth.signInWithEmailAndPassword("novalina351@gmail.com","admin123")
                .addOnCompleteListener(Editpropil.this, new OnCompleteListener<AuthResult>() {


                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                    }
                });

    }

    }



