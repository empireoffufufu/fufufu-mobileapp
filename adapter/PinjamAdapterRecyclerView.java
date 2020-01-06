package com.example.fufufu.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fufufu.admin.Editpropil;
import com.example.fufufu.admin.ListActivity;
import com.example.fufufu.model.Profil;
import com.example.fufufu.R;
import com.example.fufufu.admin.propil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class PinjamAdapterRecyclerView extends RecyclerView.Adapter<PinjamAdapterRecyclerView.MyViewHolder> {
//ini untuk profil
    private List<Profil> pinjamList;
    private Activity mActivity;
    //private Context mContext;
    private String KEY_NAME = "email";

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout list_root;
        public TextView barang,nim,nama,kelas,noHp;
        public TextView alamat,email,password;
        public MyViewHolder(View view) {
            super(view);
            list_root = view.findViewById(R.id.list_root);
            barang = view.findViewById(R.id.barang);
            nim = view.findViewById(R.id.nim);
            nama = view.findViewById(R.id.nama);
            kelas = view.findViewById(R.id.kelas);
            noHp = view.findViewById(R.id.noHP);
            alamat=view.findViewById(R.id.aLLamat);
            email=view.findViewById(R.id.eemail);
            password=view.findViewById(R.id.ppasword);

        }
    }

    public PinjamAdapterRecyclerView(ArrayList<Profil> pinjamList, Activity activity) {
        this.pinjamList = pinjamList;
        this.mActivity = activity;
       // mContext=context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pinjam_barang, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Profil movie = pinjamList.get(position);

      //  holder.barang.setText(movie.getBarang());
        holder.nim.setText(movie.getNim());
        holder.nama.setText(movie.getNama());
        holder.kelas.setText(movie.getKelas());
        holder.noHp.setText(movie.getnoHP());
        holder.alamat.setText(movie.getalamat());
        holder.email.setText(movie.getEmail());
        holder.password.setText(movie.getPassword());

        holder.list_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String qemail=movie.getKey();


                Intent i = new Intent(mActivity, Editpropil.class);
              i.putExtra("id",movie.getKey());
                i.putExtra("nim",movie.getNim());
                i.putExtra("nama",movie.getNama());
                i.putExtra("kelas",movie.getKelas());
                i.putExtra("nohp",movie.getnoHP());
                i.putExtra("alamat",movie.getalamat());
                i.putExtra("email",movie.getEmail());
                i.putExtra("password",movie.getPassword());
                mActivity.startActivity(i);


            }
        });

    }

    @Override
    public int getItemCount() {
        return pinjamList.size();
    }


}
