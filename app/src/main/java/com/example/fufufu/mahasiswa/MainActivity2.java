package com.example.fufufu.mahasiswa;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.provider.MediaStore;
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
import android.widget.Toast;

import com.example.fufufu.Login;
import com.example.fufufu.R;
import com.example.fufufu.admin.MainActivity;
import com.example.fufufu.model.Barang;
import com.example.fufufu.model.Profil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;

import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    private final int TEXT_RECO_REO_CODE=100;
    private final int BARCODE_RECO_REO_CODE=200;
    Button btn_daftar;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;
    private DatabaseReference database;
    private String KEY_NAME = "BARANG";
    ImageView bgapp, clover;
    LinearLayout textsplash, texthome, menus;
    Animation frombottom;
    private TextView nim,nama,kelas;
    private String userEmail,Pnim,Pnama,Pkelas;
private String usernim;

    Button logout;
    private TextView barangg,nimku;


    public MainActivity2()
    {

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        // Pengambilan data, apakah ada yang login.
        auth = FirebaseAuth.getInstance();
        final FirebaseUser user = auth.getCurrentUser();


        //sesi animasi


        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);

        database = FirebaseDatabase.getInstance().getReference();
        bgapp = (ImageView) findViewById(R.id.bgapp);
        clover = (ImageView) findViewById(R.id.clover);
        textsplash = (LinearLayout) findViewById(R.id.textsplash);
        texthome = (LinearLayout) findViewById(R.id.texthome);
        menus = (LinearLayout) findViewById(R.id.menus);
        nimku=findViewById(R.id.nimku);
       usernim=user.getDisplayName();

        bgapp.animate().translationY(-1900).setDuration(800).setStartDelay(300);
        clover.animate().alpha(0).setDuration(800).setStartDelay(600);
        textsplash.animate().translationY(140).alpha(0).setDuration(800).setStartDelay(300);

        texthome.startAnimation(frombottom);
        menus.startAnimation(frombottom);
        nimku.setText(usernim);

        //sesi animasi


        nim=findViewById(R.id.nim);
        nama=findViewById(R.id.namaku);
        kelas=findViewById(R.id.kelasku);

//start ambil profil


        DatabaseReference yourRef =database.child("Profil").child(usernim);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {

                Profil profil=dataSnapshot.getValue(Profil.class);

                Pnama =profil.getNama();
                Pkelas= profil.getKelas();


                nim.setText(usernim);
                nama.setText(Pnama);
                kelas.setText(Pkelas);



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





        //Pengecekan, jika tidak ada login. Di arahkan ke Login activity.
        if (user == null) {
            startActivity(new Intent(MainActivity2.this, Login.class));
            finish();
        }



    }

    @Override
    protected void onActivityResult (int requestCode,int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode==TEXT_RECO_REO_CODE)
        {
            if(resultCode==RESULT_OK)
            {
                Bitmap photo = (Bitmap)data.getExtras().get("data");
                textRecognition(photo);
            }else if (resultCode==RESULT_CANCELED)
            {
                Toast.makeText(this,"Operation Canceled by User",Toast.LENGTH_SHORT).show();

            }else {
                Toast.makeText(this,"Failed to Capture Image",Toast.LENGTH_SHORT).show();
            }
        }

        if(requestCode==BARCODE_RECO_REO_CODE)
        {
            if (resultCode==RESULT_OK )
            {
                Bitmap photo=(Bitmap)data.getExtras().get("data");
                barcodeRecognition(photo);
            }else if (resultCode==RESULT_CANCELED)
            {
                Toast.makeText(this,"Operation Canceled by User",Toast.LENGTH_SHORT).show();

            }else {
                Toast.makeText(this,"Silahkan Coba Lagi",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void barcodeRecognition(Bitmap photo) {
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(photo);
        FirebaseVisionBarcodeDetector detector = FirebaseVision.getInstance()
                .getVisionBarcodeDetector();
        FirebaseVisionBarcodeDetectorOptions options =
                new FirebaseVisionBarcodeDetectorOptions.Builder()
                        .setBarcodeFormats(FirebaseVisionBarcode.FORMAT_QR_CODE,
                                FirebaseVisionBarcode.FORMAT_AZTEC)
                        .build();
        Task<List<FirebaseVisionBarcode>> result = detector.detectInImage(image)
                .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionBarcode>>()

                {
                    @Override
                    public void onSuccess(List<FirebaseVisionBarcode> barcodes) {
                        for (FirebaseVisionBarcode barcode: barcodes) {
                            Rect bounds = barcode.getBoundingBox();
                            Point[] corners = barcode.getCornerPoints();

                            String Sbarang = barcode.getRawValue();

                            if(Sbarang !=null && Sbarang !="" )
                            {
                                Intent i = new Intent(MainActivity2.this, Yakinpinjam.class);
                                i.putExtra(KEY_NAME,Sbarang);
                                startActivity(i);
                            }else
                            {
                                Toast.makeText(getApplication(),"QR Code tidak terbaca, coba lagi",Toast.LENGTH_SHORT);
                            }





                            // See API reference for complete list of supported types
                           /*switch (valueType) {
                                case FirebaseVisionBarcode.TYPE_WIFI:
                                    String ssid = barcode.getWifi().getSsid();
                                    String password = barcode.getWifi().getPassword();
                                    int type = barcode.getWifi().getEncryptionType();
                                    break;
                                case FirebaseVisionBarcode.TYPE_URL:
                                    String title = barcode.getUrl().getTitle();
                                    String url = barcode.getUrl().getUrl();
                                    break;
                            }*/
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity2.this, "Ada kesalahan", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void textReco(View view) {
    }

    private void textRecognition(Bitmap photo)
    {

    }

    public void barcodeReco(View view) {
        Intent intent= new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,BARCODE_RECO_REO_CODE);
    }


    public void daftarAntri(View view) {
        Intent intent = new Intent(MainActivity2.this, ListActivity3.class);
        startActivity(intent);
    }

    public void Profilku(View view) {
        Intent intent = new Intent(MainActivity2.this, Editpropilku.class);
        startActivity(intent);
    }

    public void logout(View view) {
        auth.signOut();
        startActivity(new Intent(MainActivity2.this, Login.class));
        finish();
    }
}
