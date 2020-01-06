package com.example.fufufu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.support.design.widget.TextInputLayout;
import android.support.design.widget.TextInputEditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.fufufu.admin.MainActivity;
import com.example.fufufu.mahasiswa.MainActivity2;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {



    private Animation smalltobig, btta, btta2;

    Button btn_masuk;
    EditText email_et;
    private TextInputEditText password_et;
    private TextInputLayout passwrod_ett;
    String email_txt,password_txt;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser mUser = mAuth.getCurrentUser();

    private TextView textView, subtitle_header;
    private ImageView imageView;


    private ProgressDialog loading;
    public Login() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        //animasi

        smalltobig = AnimationUtils.loadAnimation(this, R.anim.smalltobig);
        btta = AnimationUtils.loadAnimation(this, R.anim.btta);
        btta2 = AnimationUtils.loadAnimation(this, R.anim.btta2);

        imageView = findViewById(R.id.imageView);
        email_et    = findViewById(R.id.emailET);
        password_et =findViewById(R.id.passwordET);
        btn_masuk = findViewById(R.id.btn_masuk);
        textView = findViewById(R.id.textView);
        subtitle_header = findViewById(R.id.subtitle_header);
        passwrod_ett=findViewById(R.id.passwordETT);
        // passing animation and start it
        imageView.startAnimation(smalltobig);

        textView.startAnimation(btta);
        subtitle_header.startAnimation(btta);
        btn_masuk.startAnimation(btta2);

        email_et.startAnimation(btta2);
        password_et.startAnimation(btta2);
        passwrod_ett.startAnimation(btta2);

        //animasi





        btn_masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email_txt = email_et.getText().toString();
                password_txt = password_et.getText().toString();
                if (TextUtils.isEmpty(email_txt)) {
                    Toast.makeText(getApplicationContext(), "Masukan Email !!!", Toast.LENGTH_SHORT).show();
                    return;
                }if (TextUtils.isEmpty(password_txt)) {
                    Toast.makeText(getApplicationContext(), "Masukan Password !!!", Toast.LENGTH_SHORT).show();
                    return;
                } else


                    loading = ProgressDialog.show(Login.this,
                            null,
                            "Please wait...",
                            true,
                            false);

                mAuth.signInWithEmailAndPassword(email_txt, password_txt)
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Login sukses, masuk ke Main Activity
                                    mUser = mAuth.getCurrentUser();

                                        if (mUser.getEmail().equals("novalina351@gmail.com")) {


                                            Intent intent = new Intent(Login.this, MainActivity.class);
                                            startActivity(intent);
                                            loading.dismiss();
                                            finish();
                                        } else {
                                            if(mUser.isEmailVerified()) {

                                            Intent intent = new Intent(Login.this, MainActivity2.class);
                                            startActivity(intent);
                                            loading.dismiss();
                                            finish();
                                        }else
                                        {
                                            Toast.makeText(Login.this," Silahkan Verifikasi Email anda lebih dahulu",Toast.LENGTH_LONG).show();
                                            loading.dismiss();;
                                        }
                                        }

                                } else {

                                    String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();

                                    switch (errorCode) {

                                        case "ERROR_INVALID_CUSTOM_TOKEN":
                                            Toast.makeText(Login.this, "The custom token format is incorrect. Please check the documentation.", Toast.LENGTH_LONG).show();
                                            break;

                                        case "ERROR_CUSTOM_TOKEN_MISMATCH":
                                            Toast.makeText(Login.this, "The custom token corresponds to a different audience.", Toast.LENGTH_LONG).show();
                                            break;

                                        case "ERROR_INVALID_CREDENTIAL":
                                            Toast.makeText(Login.this, "The supplied auth credential is malformed or has expired.", Toast.LENGTH_LONG).show();
                                            break;

                                        case "ERROR_INVALID_EMAIL":
                                            Toast.makeText(Login.this, "Format Salah, Silahkan Masukkan Email Anda", Toast.LENGTH_LONG).show();
                                            email_et.setError("Format Salah");
                                            email_et.requestFocus();
                                            break;

                                        case "ERROR_WRONG_PASSWORD":
                                            Toast.makeText(Login.this, "Password Anda Salah, Silahkan Ulangi", Toast.LENGTH_LONG).show();
                                            password_et.setError("Password anda Salah ");
                                            password_et.requestFocus();
                                            password_et.setText("");
                                            break;

                                        case "ERROR_USER_MISMATCH":
                                            Toast.makeText(Login.this, "The supplied credentials do not correspond to the previously signed in user.", Toast.LENGTH_LONG).show();
                                            break;

                                        case "ERROR_REQUIRES_RECENT_LOGIN":
                                            Toast.makeText(Login.this, "This operation is sensitive and requires recent authentication. Log in again before retrying this request.", Toast.LENGTH_LONG).show();
                                            break;

                                        case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                                            Toast.makeText(Login.this, "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.", Toast.LENGTH_LONG).show();
                                            break;

                                        case "ERROR_EMAIL_ALREADY_IN_USE":
                                            Toast.makeText(Login.this, "The email address is already in use by another account.   ", Toast.LENGTH_LONG).show();
                                            password_et.setError("The email address is already in use by another account.");
                                            password_et.requestFocus();
                                            break;

                                        case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                                            Toast.makeText(Login.this, "This credential is already associated with a different user account.", Toast.LENGTH_LONG).show();
                                            break;

                                        case "ERROR_USER_DISABLED":
                                            Toast.makeText(Login.this, "The user account has been disabled by an administrator.", Toast.LENGTH_LONG).show();
                                            break;

                                        case "ERROR_USER_TOKEN_EXPIRED":
                                            Toast.makeText(Login.this, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                                            break;

                                        case "ERROR_USER_NOT_FOUND":
                                            Toast.makeText(Login.this, "Email tidak pernah terdaftar, Silahkan Mendaftar", Toast.LENGTH_LONG).show();
                                            break;

                                        case "ERROR_INVALID_USER_TOKEN":
                                            Toast.makeText(Login.this, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                                            break;

                                        case "ERROR_OPERATION_NOT_ALLOWED":
                                            Toast.makeText(Login.this, "This operation is not allowed. You must enable this service in the console.", Toast.LENGTH_LONG).show();
                                            break;

                                        case "ERROR_WEAK_PASSWORD":
                                            Toast.makeText(Login.this, "The given password is invalid.", Toast.LENGTH_LONG).show();
                                            password_et.setError("The password is invalid it must 6 characters at least");
                                            password_et.requestFocus();
                                            break;

                                    }

                                        loading.dismiss();

                                }


                            }


                        });


            }

        });




    }


    public void resetpassword(View view) {
        startActivity(new Intent(Login.this, Lupapasword.class));
        finish();
    }
}
