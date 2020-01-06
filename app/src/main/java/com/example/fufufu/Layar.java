package com.example.fufufu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.fufufu.Login;
import com.example.fufufu.admin.MainActivity;
import com.example.fufufu.mahasiswa.MainActivity2;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Layar extends AppCompatActivity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser mUser = mAuth.getCurrentUser();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if(mUser == null)
        {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        }
        else{
            if(mUser.getEmail().equals("novalina351@gmail.com"))
            {


                Intent intent = new Intent(Layar.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            else
            {

                Intent intent = new Intent(Layar.this, MainActivity2.class);
                startActivity(intent);
                finish();
            }

        }

    }
}
