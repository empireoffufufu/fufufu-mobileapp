package com.example.fufufu.mahasiswa;

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
import com.example.fufufu.model.Pinjam;
import com.example.fufufu.model.Profil;
import com.example.fufufu.model.Riwayat;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Yakinpinjam extends AppCompatActivity {

    private static final String TAG = "Yakinpinjam";

    //add Firebase Database stuff
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private  String userID,userEmail;
    private TextView barang,email,barangg,kelasku,namaku,nopeku;
    private String Sbarang,barangku,emailku,currentDate;
    private String KEY_NAME = "BARANG";
    private Button btnYakin,btnCancel;

    private Animation smalltobig, btta, btta2;
    private TextView textView, subtitle_header,tanggal;
    private ImageView imageView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pinjamkan);

        Calendar calendar=Calendar.getInstance();
        currentDate= DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        tanggal=findViewById(R.id.tanggal);
        tanggal.setText(currentDate);

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
        btnCancel=findViewById(R.id.btnCancel);


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
        btnCancel.startAnimation(btta2);
        kelasku.startAnimation(btta2);
        barang.startAnimation(btta2);


        //animasi



        Intent intent=getIntent();
       barangku=intent.getStringExtra("Barang");


        Bundle extras =getIntent().getExtras();
        Sbarang=extras.getString(KEY_NAME);

//start ambil profil
        DatabaseReference yourRef = myRef.child("Profil").child(userEmail);
     ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {

                    Profil profil=dataSnapshot.getValue(Profil.class);

                namaku.setText(profil.getNama());
                kelasku.setText(profil.getKelas());
                nopeku.setText(profil.getnoHP());
//masihdicoba

                /**
                 * Inisialisasi adapter dan data hotel dalam bentuk ArrayList
                 * dan mengeset Adapter ke dalam RecyclerView
                 */

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


        if (emailku.equals("novalina351@gmail.com")) {
            btnYakin.setText("Selesai Pinjam");
            barang.setText("PINJM," + barangku + "!");
        }else
        {
            btnYakin.setText("Pinjam");
            barang.setText( Sbarang );
        }



        btnYakin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btnYakin.getText().equals("Pinjam")) {

                    mAuth = FirebaseAuth.getInstance();
                    mFirebaseDatabase = FirebaseDatabase.getInstance();
                    myRef = mFirebaseDatabase.getReference();
                    FirebaseUser user = mAuth.getCurrentUser();
                    userID = user.getUid();
                    userEmail=user.getDisplayName();

                    Bundle extras =getIntent().getExtras();
                    Sbarang=extras.getString(KEY_NAME);

                    String qnama = namaku.getText().toString();
                    String qkelas= kelasku.getText().toString();
                    String qnope=nopeku.getText().toString();

                    Toast.makeText(Yakinpinjam.this, Sbarang, Toast.LENGTH_SHORT).show();

                       if (Sbarang.equals(""))
                        { barangg.setError("TIDAK TERBACA");
                        barangg.requestFocus();
                        }
                        else
                        {

                        submitUser(new Pinjam(
                                Sbarang.toString(),
                                userEmail.toLowerCase(),
                                qkelas.toString(),
                                qnama.toString(),
                                qnope.toString(),
                                currentDate.toString()

                            ));

                        riwayatUser(new Riwayat(
                                Sbarang.toString(),
                                userEmail.toLowerCase(),
                                qnama.toString(),
                                currentDate.toString()

                        ));

                        }


                }else
                {

                    myRef.child("Peminjaman").child(barangku).removeValue();
                    Toast.makeText(Yakinpinjam.this, "Sarana Belajar Telah Kembali", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Yakinpinjam.this, ListActivity2.class);
                    startActivity(intent);
                    finish();

                }




            }
        });



        email.setText(userEmail);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }

    private void riwayatUser(Riwayat riwayat) {
        myRef.child("Riwayat")
                .push()
                .setValue(riwayat)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void Void) {

                        barang.setText("");
                        email.setText("");
                        namaku.setText("");
                        tanggal.setText("");
                        finish();
                    }
                });


    }


    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }




    private void submitUser(final Pinjam pinjam)
    {
        myRef.child("Peminjaman")
                .child(Sbarang)
                .setValue(pinjam)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void Void) {

                        barang.setText("");
                        email.setText("");
                        kelasku.setText("");
                        namaku.setText("");
                        nopeku.setText("");
                        tanggal.setText("");
                        Intent intent = new Intent(Yakinpinjam.this, MainActivity2.class);
                        Toast.makeText(Yakinpinjam.this, "SELAMAT MENGGUNAKAN", Toast.LENGTH_SHORT).show();
                        startActivity(intent);

                        finish();
                    }
                });


    }
}
