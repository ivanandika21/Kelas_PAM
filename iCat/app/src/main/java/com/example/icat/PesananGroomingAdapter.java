package com.example.icat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PesananGroomingAdapter extends RecyclerView.Adapter<PesananGroomingAdapter.GroomingViewHolder> {
    Context context;
    ArrayList<PesananGrooming> pesananGroomingList;

    public PesananGroomingAdapter(Context context, ArrayList<PesananGrooming> pesananGroomingList) {
        this.context = context;
        this.pesananGroomingList = pesananGroomingList;
    }

    @NonNull
    @Override
    public PesananGroomingAdapter.GroomingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_pesanan_grooming, parent, false);

        return new GroomingViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PesananGroomingAdapter.GroomingViewHolder holder, int position) {
        PesananGrooming pesananGrooming = pesananGroomingList.get(position);

        holder.var_tanggal.setText(pesananGrooming.getTanggal());
        holder.var_atasnama.setText(pesananGrooming.getAtasnama());
        holder.var_jeniskucing.setText(pesananGrooming.getJeniskucing());
        holder.var_jenislayanan.setText(pesananGrooming.getJenislayanan());
        holder.var_tujuan.setText(pesananGrooming.getTujuan());
        holder.var_status.setText(pesananGrooming.getStatus());
    }

    @Override
    public int getItemCount() {
        return pesananGroomingList.size();
    }

    public static class GroomingViewHolder extends RecyclerView.ViewHolder {
        TextView var_tanggal, var_atasnama, var_jeniskucing, var_jenislayanan, var_tujuan, var_status;
        LinearLayout var_mainLayout;

        public GroomingViewHolder(@NonNull View itemView) {
            super(itemView);

            var_tanggal = itemView.findViewById(R.id.id_tanggal);
            var_atasnama = itemView.findViewById(R.id.id_atasnama);
            var_jeniskucing = itemView.findViewById(R.id.id_jeniskucing);
            var_jenislayanan = itemView.findViewById(R.id.id_jenislayanan);
            var_tujuan = itemView.findViewById(R.id.id_tujuan);
            var_status = itemView.findViewById(R.id.id_status);
            var_mainLayout = itemView.findViewById(R.id.id_mainLayout);
        }
    }
}
