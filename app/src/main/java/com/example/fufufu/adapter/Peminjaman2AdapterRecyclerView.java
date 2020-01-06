package com.example.fufufu.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fufufu.model.Pinjam;
import com.example.fufufu.R;

import java.util.ArrayList;
import java.util.List;

public class Peminjaman2AdapterRecyclerView extends RecyclerView.Adapter<Peminjaman2AdapterRecyclerView.MyViewHolder> {
//ini untuk antrian yang minjam mahasiswa
    private List<Pinjam> pinjamList;
    private Activity mActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout list_root;
        public TextView barang,email,nama,kelas,nohp,tanggal;

        public MyViewHolder(View view) {
            super(view);
            list_root = view.findViewById(R.id.list_root);
            barang = view.findViewById(R.id.barang);
            email = view.findViewById(R.id.emailET);
            nama = view.findViewById(R.id.nama);
            kelas = view.findViewById(R.id.kelas);
            nohp = view.findViewById(R.id.nohpk);
            tanggal=view.findViewById(R.id.tanggal);

        }
    }

    public Peminjaman2AdapterRecyclerView(ArrayList<Pinjam> pinjamList, Activity activity) {
        this.pinjamList = pinjamList;
        this.mActivity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_pinjam2, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Pinjam movie = pinjamList.get(position);

        holder.barang.setText(movie.getBarang());
        holder.email.setText(movie.getNim());
        holder.nama.setText((movie.getNama()));
        holder.kelas.setText((movie.getKelas()));
        holder.nohp.setText((movie.getNohp()));
        holder.tanggal.setText(movie.getTanggal());

    }


    @Override
    public int getItemCount() {
        return pinjamList.size();
    }


}
