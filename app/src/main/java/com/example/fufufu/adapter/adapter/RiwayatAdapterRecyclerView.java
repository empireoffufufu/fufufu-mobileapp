package com.example.fufufu.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fufufu.R;
import com.example.fufufu.admin.Saranaactv;
import com.example.fufufu.model.Barang;
import com.example.fufufu.model.Riwayat;


import java.util.ArrayList;
import java.util.List;

public class RiwayatAdapterRecyclerView extends RecyclerView.Adapter<RiwayatAdapterRecyclerView.MyViewHolder> {

    //ini untuk sarana
    private List<Riwayat> pinjamList;
    private Activity mActivity;
    //private Context mContext;
    private String KEY_NAME = "email";

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout list_root;
        public TextView sarana,nimku,namaku,tanggal;


        public MyViewHolder(View view) {
            super(view);
            list_root = view.findViewById(R.id.list_root);
            sarana = view.findViewById(R.id.sarana);
            nimku = view.findViewById(R.id.nimku);
            namaku = view.findViewById(R.id.namaku);
            tanggal = view.findViewById(R.id.tanggal);


        }
    }

    public RiwayatAdapterRecyclerView(ArrayList<Riwayat> pinjamList, Activity activity) {
        this.pinjamList = pinjamList;
        this.mActivity = activity;
        // mContext=context;
    }


    @Override
    public RiwayatAdapterRecyclerView.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lsit_riwayat, parent, false);

        return new RiwayatAdapterRecyclerView.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RiwayatAdapterRecyclerView.MyViewHolder holder, final int position) {
        final Riwayat movie = pinjamList.get(position);

        //  holder.barang.setText(movie.getBarang());
        holder.sarana.setText(movie.getBarang());
        holder.nimku.setText(movie.getNim());
        holder.namaku.setText(movie.getNama());
        holder.tanggal.setText(movie.getTanggal());


    }

    @Override
    public int getItemCount() {
        return pinjamList.size();
    }

}
