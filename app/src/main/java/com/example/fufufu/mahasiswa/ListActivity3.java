package com.example.fufufu.mahasiswa;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.fufufu.R;
import com.example.fufufu.adapter.Peminjaman2AdapterRecyclerView;
import com.example.fufufu.model.Pinjam;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListActivity3 extends AppCompatActivity {
    private DatabaseReference database;
    private ArrayList<Pinjam> daftarReqq;
    private Peminjaman2AdapterRecyclerView pinjamanAdapterRecyclerView;
    private ProgressDialog loading;
    private RecyclerView rc_list_request2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list3);

        database = FirebaseDatabase.getInstance().getReference();

        rc_list_request2 = findViewById(R.id.rc_list_request2);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rc_list_request2.setLayoutManager(mLayoutManager);
        rc_list_request2.setItemAnimator(new DefaultItemAnimator());
        loading = ProgressDialog.show(ListActivity3.this,
                null,
                "Please wait...",
                true,
                false);

        database.child("Peminjaman").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                /**
                 * Saat ada data baru, masukkan datanya ke ArrayList
                 */
                daftarReqq = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    /**
                     * Mapping data pada DataSnapshot ke dalam object Wisata
                     * Dan juga menyimpan primary key pada object Wisata
                     * untuk keperluan Edit dan Delete data
                     */
                    Pinjam pinjam = noteDataSnapshot.getValue(Pinjam.class);
                    pinjam.setKey(noteDataSnapshot.getKey());

                    /**
                     * Menambahkan object Wisata yang sudah dimapping
                     * ke dalam ArrayList
                     */
                    daftarReqq.add(pinjam);
                }

                /**
                 * Inisialisasi adapter dan data hotel dalam bentuk ArrayList
                 * dan mengeset Adapter ke dalam RecyclerView
                 */
                pinjamanAdapterRecyclerView = new Peminjaman2AdapterRecyclerView(daftarReqq, ListActivity3.this);
                rc_list_request2.setAdapter(pinjamanAdapterRecyclerView);
                loading.dismiss();
            }


            public void onCancelled(DatabaseError databaseError) {
                /**
                 * Kode ini akan dipanggil ketika ada error dan
                 * pengambilan data gagal dan memprint error nya
                 * ke LogCat
                 */
                System.out.println(databaseError.getDetails() + " " + databaseError.getMessage());
                loading.dismiss();
            }
        });

    }
}
