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


import java.util.ArrayList;
import java.util.List;

public class saranaAdapterRecyclerView extends RecyclerView.Adapter<saranaAdapterRecyclerView.MyViewHolder> {

    //ini untuk sarana
    private List<Barang> pinjamList;
    private Activity mActivity;
    //private Context mContext;
    private String KEY_NAME = "email";

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout list_root;
        public TextView sarana,stok,kondisi,lokasi;


        public MyViewHolder(View view) {
            super(view);
            list_root = view.findViewById(R.id.list_root);
            sarana = view.findViewById(R.id.sarana);
            stok = view.findViewById(R.id.stok);
            kondisi = view.findViewById(R.id.kondisi);
            lokasi = view.findViewById(R.id.lokasi);


        }
    }

    public saranaAdapterRecyclerView(ArrayList<Barang> pinjamList, Activity activity) {
        this.pinjamList = pinjamList;
        this.mActivity = activity;
        // mContext=context;
    }


    @Override
    public saranaAdapterRecyclerView.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_sarana, parent, false);

        return new saranaAdapterRecyclerView.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final saranaAdapterRecyclerView.MyViewHolder holder, final int position) {
        final Barang movie = pinjamList.get(position);

        //  holder.barang.setText(movie.getBarang());
        holder.sarana.setText(movie.getSarana());
        holder.stok.setText(movie.getJumlah());
        holder.kondisi.setText(movie.getKondisi());
        holder.lokasi.setText(movie.getLokasi());

        holder.list_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String qemail=movie.getKey();

                Intent i = new Intent(mActivity, Saranaactv.class);
                i.putExtra("id",movie.getKey());
                i.putExtra("sarana",movie.getSarana());
                i.putExtra("stok",movie.getJumlah());
                i.putExtra("kondisi",movie.getKondisi());
                i.putExtra("lokasi",movie.getLokasi());
               mActivity.startActivity(i);


            }
        });

    }

    @Override
    public int getItemCount() {
        return pinjamList.size();
    }

}
