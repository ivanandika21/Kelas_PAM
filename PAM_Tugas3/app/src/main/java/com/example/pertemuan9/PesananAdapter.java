package com.example.pertemuan9;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PesananAdapter extends RecyclerView.Adapter<PesananAdapter.MyViewHolder> {

    Context context;
    ArrayList<Pesanan> pesananArrayList;

    public PesananAdapter(Context context, ArrayList<Pesanan> pesananArrayList) {
        this.context = context;
        this.pesananArrayList = pesananArrayList;
    }

    @NonNull
    @Override
    public PesananAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.row_list, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PesananAdapter.MyViewHolder holder, int position) {

        Pesanan pesanan = pesananArrayList.get(position);

        holder.var_tanggal.setText(pesanan.getTanggal());
        holder.var_nama.setText(pesanan.getNama());
        holder.var_alamat.setText(pesanan.getAlamat());
    }

    @Override
    public int getItemCount() {
        return pesananArrayList.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder {

        TextView var_tanggal, var_nama, var_alamat;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            var_tanggal = itemView.findViewById(R.id.id_tanggal);
            var_nama = itemView.findViewById(R.id.id_nama);
            var_alamat = itemView.findViewById(R.id.id_alamat);
        }
    }
}
