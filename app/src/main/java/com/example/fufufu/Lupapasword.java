package com.example.fufufu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.fufufu.admin.MainActivity;
import com.example.fufufu.mahasiswa.MainActivity2;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Lupapasword extends AppCompatActivity {



    private Animation smalltobig, btta, btta2;

    Button btn_masuk;
    EditText email_et,password_et;
    String email_txt,password_txt;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser mUser = mAuth.getCurrentUser();

    private TextView textView, subtitle_header;
    private ImageView imageView;


    private ProgressDialog loading;
    public Lupapasword() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lupapasword);




        //animasi

        smalltobig = AnimationUtils.loadAnimation(this, R.anim.smalltobig);
        btta = AnimationUtils.loadAnimation(this, R.anim.btta);
        btta2 = AnimationUtils.loadAnimation(this, R.anim.btta2);

        imageView = findViewById(R.id.imageView);
        email_et    = findViewById(R.id.emailET);
        btn_masuk = findViewById(R.id.btn_masuk);
        textView = findViewById(R.id.textView);
        subtitle_header = findViewById(R.id.subtitle_header);

        // passing animation and start it
        imageView.startAnimation(smalltobig);

        textView.startAnimation(btta);
        subtitle_header.startAnimation(btta);
        btn_masuk.startAnimation(btta2);

        email_et.startAnimation(btta2);


        //animasi






        btn_masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading = ProgressDialog.show(Lupapasword.this,
                        null,
                        "Please wait...",
                        true,
                        false);

                mAuth.sendPasswordResetEmail(email_et.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        loading.dismiss();
                         if(task.isSuccessful())
                         {
                             loading = ProgressDialog.show(Lupapasword.this,
                                     null,
                                     "Please wait...",
                                     true,
                                     false);
                            Toast.makeText(Lupapasword.this,"Silahkan Periksa Email Anda",Toast.LENGTH_LONG).show();
                             Intent intent = new Intent(Lupapasword.this, Login.class);
                             startActivity(intent);
                             finish();
                         }else
                         {
                             Toast.makeText(Lupapasword.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                         }
                    }
                });
            }
        });


    }

    public void cancel(View view) {
        startActivity(new Intent(Lupapasword.this, Login.class));
        finish();
    }
}
