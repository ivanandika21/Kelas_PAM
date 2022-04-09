package com.example.pertemuan9;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

        holder.var_tanggal.setText(pesanan.getDate());
        holder.var_nama.setText(pesanan.getName());
        holder.var_tujuan.setText(pesanan.getTujuan());
        holder.var_id.setText(pesanan.getId());
        holder.var_mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditActivity.class);
                intent.putExtra("abc", pesanan.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pesananArrayList.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder {

        TextView var_tanggal, var_nama, var_tujuan, var_id;
        LinearLayout var_mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            var_tanggal = itemView.findViewById(R.id.id_tanggal);
            var_nama = itemView.findViewById(R.id.id_nama);
            var_tujuan = itemView.findViewById(R.id.id_tujuan);
            var_id = itemView.findViewById(R.id.id_idorder);
            var_mainLayout = itemView.findViewById(R.id.id_mainLayout);
        }
    }
}
