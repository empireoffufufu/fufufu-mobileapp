package com.example.fufufu.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fufufu.R;
import com.example.fufufu.adapter.Peminjaman2AdapterRecyclerView;
import com.example.fufufu.admin.ListActivity;
import com.example.fufufu.admin.ListActivity2;
import com.example.fufufu.admin.MainActivity;
import com.example.fufufu.admin.propil;
import com.example.fufufu.mahasiswa.MainActivity2;
import com.example.fufufu.mahasiswa.Yakinpinjam;
import com.example.fufufu.model.Pinjam;
import com.example.fufufu.model.Profil;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;

public class Yakinbalik extends AppCompatActivity {

    private static final String TAG = "Yakinpinjam";

    //add Firebase Database stuff
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private  String userID,userEmail;
    private TextView barang,email,barangg,kelasku,namaku,nopeku,tanggal;
    private String Sbarang,barangku,emailku,qnim,qnama,qkelas;
    private String KEY_NAME = "BARANG";
    private Button btnYakin;

    private Animation smalltobig, btta, btta2;
    private TextView textView, subtitle_header;
    private ImageView imageView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kembalikan);


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        userEmail=user.getDisplayName();
        emailku=user.getEmail();


        barang = findViewById(R.id.barang);
        email=findViewById(R.id.textView2);
        btnYakin=findViewById(R.id.btnYakin);
        namaku=findViewById(R.id.namaku);
        kelasku=findViewById(R.id.kelasku);
        nopeku=findViewById(R.id.nopeku);
        tanggal=findViewById(R.id.tanggal);


        //animasi

        smalltobig = AnimationUtils.loadAnimation(this, R.anim.smalltobig);
        btta = AnimationUtils.loadAnimation(this, R.anim.btta);
        btta2 = AnimationUtils.loadAnimation(this, R.anim.btta2);

        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.namaku);
        subtitle_header = findViewById(R.id.textView2);

        // passing animation and start it
        imageView.startAnimation(smalltobig);

        textView.startAnimation(btta);
        subtitle_header.startAnimation(btta);
        btnYakin.startAnimation(btta2);
        kelasku.startAnimation(btta2);
        barang.startAnimation(btta2);
        tanggal.startAnimation(btta2);


        //animasi



        Intent intent=getIntent();
        barangku=intent.getStringExtra("Barang");
        qnama=intent.getStringExtra("Nama");
        qkelas=intent.getStringExtra("Kelas");
        qnim=intent.getStringExtra("Email");
        String qtanggal=intent.getStringExtra("Tanggal");

        barang.setText( barangku);
        namaku.setText(qnama);
        kelasku.setText(qkelas);
        subtitle_header.setText(qnim);
        tanggal.setText(qtanggal);



        btnYakin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    mAuth = FirebaseAuth.getInstance();
                    mFirebaseDatabase = FirebaseDatabase.getInstance();
                    myRef = mFirebaseDatabase.getReference();
                    FirebaseUser user = mAuth.getCurrentUser();
                    userID = user.getUid();
                    userEmail=user.getDisplayName();

                    Bundle extras =getIntent().getExtras();
                    Sbarang=extras.getString(KEY_NAME);

                myRef.child("Peminjaman").child(barangku).removeValue();
                Toast.makeText(Yakinbalik.this, "Sarana Belajar Telah Kembali", Toast.LENGTH_SHORT).show();

                finish();




            }
        });



    }
}
