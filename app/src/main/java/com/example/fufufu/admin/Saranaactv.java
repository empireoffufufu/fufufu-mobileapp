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

import com.example.fufufu.R;
import com.example.fufufu.model.Barang;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Saranaactv extends AppCompatActivity {


    private static final String TAG = "";
    private Button btnSave,btnCancel;
    EditText sarana,stok,kondisi,lokasi;
    String Kkey;

    private ProgressDialog loading;
    private DatabaseReference database;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();



    public Saranaactv()
    {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saranabelajar);


        database = FirebaseDatabase.getInstance().getReference();


        Intent intent=getIntent();
        String Psarana = intent.getStringExtra("sarana");
        String Pstok = intent.getStringExtra("stok");
        String Pkondisi= intent.getStringExtra("kondisi");
        String Plokasi = intent.getStringExtra("lokasi");


        Kkey=intent.getStringExtra("id");





        sarana=findViewById(R.id.sarana);
        stok=findViewById(R.id.Stok);
        kondisi = findViewById(R.id.kondisi);
        lokasi = findViewById(R.id.lokasi);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        sarana.setText(Psarana);
        stok.setText(Pstok);
        kondisi.setText(Pkondisi);
        lokasi.setText(Plokasi);


        if ("".equalsIgnoreCase(stok.getText().toString())) {
            btnSave.setText("Simpan");
            btnCancel.setText("Cancel");
        }else
        {
            btnSave.setText("Edit");
            btnCancel.setText("Delete");
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Ssarana = sarana.getText().toString();
                String Sstok = stok.getText().toString();
                String Skondisi = kondisi.getText().toString();
                String Slokasi = lokasi.getText().toString();

                if (btnSave.getText().equals("Simpan")) {


                    if (Ssarana.equals("")) {
                        sarana.setError("Silahkan masukkan Sarana");
                        sarana.requestFocus();
                    } else if (Sstok.equals("")) {
                        stok.setError("Silahkan masukkan STOK ");
                        stok.requestFocus();
                    } else if (Skondisi.equals("")) {
                        kondisi.setError("Silahkan masukkan kondisi");
                        kondisi.requestFocus();
                    } else if (Slokasi.equals("")) {
                        lokasi.setError("Silahkan masukkan lokasi");
                        lokasi.requestFocus();
                    } else {
                        loading = ProgressDialog.show(Saranaactv.this,
                                null,
                                "Please wait...",
                                true,
                                false);

                        submitUser(new Barang(
                                Ssarana.toString(),
                                Sstok.toString(),
                                Skondisi.toString(),
                                Slokasi.toString()
                                ));
                        finish();
                    }


                }
                else
                {

                    loading = ProgressDialog.show(Saranaactv.this,
                            null,
                            "Please wait...",
                            true,
                            false);

                    editUser(new Barang(
                            Ssarana.toString(),
                            Sstok.toString(),
                            Skondisi.toString(),
                            Slokasi.toString()),Kkey);
                    finish();

                }
            }


        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (btnCancel.getText().equals("Cancel")) {
                    //tutup page
                    finish();
                } else {
                    database.child("Sarana").child(Kkey).removeValue();

                    Toast.makeText(Saranaactv.this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();

                    finish();


                }
            }
        });
    }



    private void editUser(Barang barang, String id) {
        database.child("Sarana")
                .child(id)
                .setValue(barang)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        loading.dismiss();

                        Toast.makeText(Saranaactv.this, "Data berhasil diubah", Toast.LENGTH_SHORT).show();
                        finish();

                    }

                });
    }

    private void submitUser(Barang barang)
    {
        String idemail=database.child(sarana.getText().toString()).getKey();
        database.child("Sarana")
                .child(idemail)
                // .push()
                .setValue(barang)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void Void) {

                        loading.dismiss();
                        sarana.setText("");
                        stok.setText("");
                        kondisi.setText("");
                        lokasi.setText("");
                        Toast.makeText(Saranaactv.this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });


    }


}
