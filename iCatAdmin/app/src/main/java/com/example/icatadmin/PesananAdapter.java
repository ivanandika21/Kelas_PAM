package com.example.icatadmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PesananAdapter extends RecyclerView.Adapter<PesananAdapter.GroomingViewHolder> {
    Context context;
    ArrayList<Pesanan> pesananList;

    public PesananAdapter(Context context, ArrayList<Pesanan> pesananList) {
        this.context = context;
        this.pesananList = pesananList;
    }

    @NonNull
    @Override
    public GroomingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_pesanan, parent, false);

        return new GroomingViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GroomingViewHolder holder, int position) {
        Pesanan pesanan = pesananList.get(position);

        holder.var_tanggal.setText(pesanan.getTanggal());
        holder.var_atasnama.setText(pesanan.getAtasnama());
        holder.var_jeniskucing.setText(pesanan.getJeniskucing());
        holder.var_jenislayanan.setText(pesanan.getJenislayanan());
        holder.var_tujuan.setText(pesanan.getTujuan());
        holder.var_status.setText(pesanan.getStatus());
    }

    @Override
    public int getItemCount() {
        return pesananList.size();
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
