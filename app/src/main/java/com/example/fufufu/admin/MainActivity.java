package com.example.fufufu.admin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fufufu.Login;
import com.example.fufufu.R;
import com.example.fufufu.mahasiswa.MainActivity2;
import com.example.fufufu.model.Profil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private final int TEXT_RECO_REO_CODE=100;
    private final int BARCODE_RECO_REO_CODE=200;

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;
    private DatabaseReference database;
    private String KEY_NAME = "BARANG";
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    ImageView bgapp, clover;
    LinearLayout textsplash, texthome, menus;
    Animation frombottom;
    Button logout;
    private TextView barangg,nim,nama,kelas;
    private String userEmail,Pnim,Pnama,Pkelas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Pengambilan data, apakah ada yang login.

        auth = FirebaseAuth.getInstance();
        final FirebaseUser user = auth.getCurrentUser();



        database = FirebaseDatabase.getInstance().getReference();
        //Pengecekan, jika tidak ada login. Di arahkan ke Login activity.
        if (user == null) {
            startActivity(new Intent(MainActivity.this, Login.class));
            finish();
        }
        else
        {
if(user.getEmail().equals("novalina351@gmail.com")){

}
else{
    startActivity(new Intent(MainActivity.this, MainActivity2.class));
    finish();}
        }


        //sesi animasi


        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);


        bgapp = (ImageView) findViewById(R.id.bgapp);
        clover = (ImageView) findViewById(R.id.clover);
        textsplash = (LinearLayout) findViewById(R.id.textsplash);
        texthome = (LinearLayout) findViewById(R.id.texthome);
        menus = (LinearLayout) findViewById(R.id.menus);

        bgapp.animate().translationY(-1900).setDuration(800).setStartDelay(300);
        clover.animate().alpha(0).setDuration(800).setStartDelay(600);
        textsplash.animate().translationY(140).alpha(0).setDuration(800).setStartDelay(300);

        texthome.startAnimation(frombottom);
        menus.startAnimation(frombottom);

        //sesi animasi




    }



    public void daftarProfil(View view) {

        Intent intent = new Intent(MainActivity.this, ListActivity.class);
        startActivity(intent);
    }


    public void daftarAntri(View view) {


        Intent intent = new Intent(MainActivity.this, ListActivity2.class);
        startActivity(intent);
    }


    public void daftarBarang(View view) {
        mAuth.signInWithEmailAndPassword("novalina351@gmail.com","admin123")
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {


                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                    }
                });


        Intent intent = new Intent(MainActivity.this, ListSarana.class);
        startActivity(intent);
    }

    public void logout(View view) {
        auth.signOut();
        startActivity(new Intent(MainActivity.this, Login.class));
        finish();
    }
}
