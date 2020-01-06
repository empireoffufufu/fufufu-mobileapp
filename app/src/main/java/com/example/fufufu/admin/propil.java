package com.example.fufufu.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class propil extends AppCompatActivity {


    private static final String TAG = propil.class.getSimpleName();
    private Button btnSave,btnCancel;
    EditText nim, nama, alamat, noHP, kelas,email_et;
    TextView grup;
    String email_txt,password_txt,grupstatus,Kkey,Pemail,Ppasword;
    private TextInputEditText password_et;
    private TextInputLayout passwrod_ett;

    private ProgressDialog loading;
    private DatabaseReference database;
    private FirebaseAuth fAuth;
    private FirebaseAuth.AuthStateListener fStateListener;







    public propil()
    {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil);



        database = FirebaseDatabase.getInstance().getReference();

        fAuth = FirebaseAuth.getInstance();
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

        fStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mUser = firebaseAuth.getCurrentUser();
                if (mUser != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + mUser.getUid());
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };


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

               final String eemail = email_et.getText().toString().trim();

             final    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


                if (TextUtils.isEmpty(email_txt)) {
                    Toast.makeText(getApplicationContext(), "Masukan Email !!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password_txt)) {
                    Toast.makeText(getApplicationContext(), "Masukan Password !!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (eemail.matches(emailPattern))
                {
                    Toast.makeText(getApplicationContext(),"valid email address",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
                    return;
                }




                signUp(email_et.getText().toString(), password_et.getText().toString());



                if (Snim.equals("")) {
                    nim.setError("Silahkan masukkan NIM");
                    nim.requestFocus();
                } else {

                    fAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {

                                FirebaseUser mUser = fAuth.getCurrentUser();
                                mUser.sendEmailVerification();
                                UserProfileChangeRequest profileUpdates =   new UserProfileChangeRequest.Builder()
                                        .setDisplayName(nim.getText().toString()).build();
                                mUser.updateProfile(profileUpdates);
                                Toast.makeText(propil.this, "Proses Pendaftaran Berhasil, Silahkan Cek email anda untuk verifikasi\n" +
                                                "Email "+email_txt,
                                        Toast.LENGTH_SHORT).show();

                            }else
                            {
                                Toast.makeText(propil.this, task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                    submitUser(new Profil(
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

                fAuth.signInWithEmailAndPassword("novalina351@gmail.com","admin123")
                        .addOnCompleteListener(propil.this, new OnCompleteListener<AuthResult>() {


                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                            }
                        });
                finish();
            }
        });

    }

    private void signUp(final String email_txt, String password_txt) {
        // start daftar

        fAuth.createUserWithEmailAndPassword(email_txt, password_txt)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        /**
                         * Jika sign in gagal, tampilkan pesan ke user. Jika sign in sukses
                         * maka auth state listener akan dipanggil dan logic untuk menghandle
                         * signed in user bisa dihandle di listener.
                         */
                        if (!task.isSuccessful()) {
                            String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();

                            switch (errorCode) {

                                case "ERROR_INVALID_CUSTOM_TOKEN":
                                    Toast.makeText(propil.this, "The custom token format is incorrect. Please check the documentation.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_CUSTOM_TOKEN_MISMATCH":
                                    Toast.makeText(propil.this, "The custom token corresponds to a different audience.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_INVALID_CREDENTIAL":
                                    Toast.makeText(propil.this, "The supplied auth credential is malformed or has expired.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_INVALID_EMAIL":
                                    Toast.makeText(propil.this, "Format Salah, Silahkan Masukkan Email Anda", Toast.LENGTH_LONG).show();
                                    email_et.setError("Format Salah");
                                    email_et.requestFocus();
                                    break;

                                case "ERROR_WRONG_PASSWORD":
                                    Toast.makeText(propil.this, "Password Anda Salah, Silahkan Ulangi", Toast.LENGTH_LONG).show();
                                    password_et.setError("Password anda Salah ");
                                    password_et.requestFocus();
                                    password_et.setText("");
                                    break;

                                case "ERROR_USER_MISMATCH":
                                    Toast.makeText(propil.this, "The supplied credentials do not correspond to the previously signed in user.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_REQUIRES_RECENT_LOGIN":
                                    Toast.makeText(propil.this, "This operation is sensitive and requires recent authentication. Log in again before retrying this request.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                                    Toast.makeText(propil.this, "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_EMAIL_ALREADY_IN_USE":
                                    Toast.makeText(propil.this, "The email address is already in use by another account.   ", Toast.LENGTH_LONG).show();
                                    password_et.setError("The email address is already in use by another account.");
                                    password_et.requestFocus();
                                    break;

                                case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                                    Toast.makeText(propil.this, "This credential is already associated with a different user account.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_USER_DISABLED":
                                    Toast.makeText(propil.this, "The user account has been disabled by an administrator.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_USER_TOKEN_EXPIRED":
                                    Toast.makeText(propil.this, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_USER_NOT_FOUND":
                                    Toast.makeText(propil.this, "Email tidak pernah terdaftar, Silahkan Mendaftar", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_INVALID_USER_TOKEN":
                                    Toast.makeText(propil.this, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_OPERATION_NOT_ALLOWED":
                                    Toast.makeText(propil.this, "This operation is not allowed. You must enable this service in the console.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_WEAK_PASSWORD":
                                    Toast.makeText(propil.this, "The given password is invalid.", Toast.LENGTH_LONG).show();
                                    password_et.setError("The password is invalid it must 6 characters at least");
                                    password_et.requestFocus();
                                    break;
                            }
                        } else {



                        }

                        // rest of code
                    }
                });

        //finish




    }


    private void submitUser(Profil profil)
    {
        String idemail=database.child(nim.getText().toString()).getKey();
        database.child("Profil")
                .child(idemail)
                // .push()
                .setValue(profil)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void Void) {

                        nim.setText("");
                        nama.setText("");
                        kelas.setText("");
                        alamat.setText("");
                        noHP.setText("");
                        email_et.setText("");
                        password_et.setText("");
                        grup.setText("");
                        Toast.makeText(propil.this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();



                    }
                });

    }

    @Override
    protected void onStart() {
        super.onStart();
        fAuth.addAuthStateListener(fStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (fStateListener != null) {
            fAuth.removeAuthStateListener(fStateListener);
        }
    }

}
