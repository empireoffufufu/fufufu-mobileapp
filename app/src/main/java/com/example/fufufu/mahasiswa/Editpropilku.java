package com.example.fufufu.mahasiswa;

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
import com.example.fufufu.admin.Editpropil;
import com.example.fufufu.mahasiswa.MainActivity2;
import com.example.fufufu.model.Profil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import android.support.design.widget.TextInputLayout;
import android.support.design.widget.TextInputEditText;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Editpropilku extends AppCompatActivity {


    private static final String TAG = "";
    private Button btnSave;
    EditText nama, alamat, noHP, kelas;
    TextView grup,email_et,nim;
    private TextInputEditText password_et;
    private TextInputLayout passwrod_ett;
    String email_txt,password_txt,grupstatus,Kkey,Pemail,Ppasword,userEmail,Pnim,Pnama,Pkelas,Palamat,Pnohp;
    private DatabaseReference myRef;
    private ProgressDialog loading;
    private DatabaseReference database;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();



    public Editpropilku()
    {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprofilku);


        database = FirebaseDatabase.getInstance().getReference();


        email_et=findViewById(R.id.emailET);
        password_et=findViewById(R.id.passwordET);
        nim = findViewById(R.id.nim);
        nama = findViewById(R.id.nama);
        kelas = findViewById(R.id.kelas);
        alamat = findViewById(R.id.alamat);
        noHP = findViewById(R.id.noHP);
        grup=findViewById(R.id.grup);
        btnSave = findViewById(R.id.btnSave);

        passwrod_ett=findViewById(R.id.passwordETT);

//start ambil profil
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userEmail=user.getDisplayName();
        DatabaseReference yourRef =database.child("Profil").child(userEmail);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {

                Profil profil=dataSnapshot.getValue(Profil.class);

                 Pnim= profil.getNim();
                 Pnama =profil.getNama();
                 Pkelas= profil.getKelas();
                 Palamat = profil.getalamat();
                Pnohp= profil.getnoHP();
                Pemail= profil.getEmail();
                Ppasword= profil.getPassword();

                Kkey=profil.getKey();

                nim.setText(Pnim);
                nama.setText(Pnama);
                kelas.setText(Pkelas);
                alamat.setText(Palamat);
                noHP.setText(Pnohp);
                email_et.setText(Pemail);
                password_et.setText(Ppasword);



            }


            public void onCancelled(DatabaseError databaseError) {
                /**
                 * Kode ini akan dipanggil ketika ada error dan
                 * pengambilan data gagal dan memprint error nya
                 * ke LogCat
                 */
                System.out.println(databaseError.getDetails() + " " + databaseError.getMessage());

            }
        };
        yourRef.addListenerForSingleValueEvent(eventListener);

        // finsih profil



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               String Snim = nim.getText().toString();
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



                    editUser(new Profil(
                            Snim.toLowerCase(),
                            Snama.toString(),
                            Skelas.toUpperCase(),
                            Salamat.toString(),
                            SnoHP.toLowerCase(),
                            email_txt.toString(),
                            password_txt.toString(),
                            grupstatus.toLowerCase()));
                    Toast.makeText(Editpropilku.this, "Data Berhasil Diperbarui ",
                            Toast.LENGTH_LONG).show();

                }
                finish();
            }


        });



    }



    private void editUser(Profil profil) {
        String nimku = nim.getText().toString();
        FirebaseUser mUser = mAuth.getCurrentUser();

        mUser.updatePassword(password_et.getText().toString());
        database.child("Profil")
                .child(nimku)
                .setValue(profil)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        finish();

                    }

                });

    }

}



