package com.example.icat.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.icat.LihatPesananActivity;
import com.example.icat.R;
import com.example.icat.models.PesananGrooming;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class PesananGroomingAdapter extends RecyclerView.Adapter<PesananGroomingAdapter.GroomingViewHolder> {
    Context context;
    ArrayList<PesananGrooming> pesananGroomingList;
    private FirebaseFirestore database;

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
        holder.var_jenisgrooming.setText(pesananGrooming.getJenisgrooming());
        holder.var_jeniskucing.setText(pesananGrooming.getJeniskucing());
        holder.var_jenislayanan.setText(pesananGrooming.getJenislayanan());
        holder.var_tujuan.setText(pesananGrooming.getTujuan());
        holder.var_status.setText(pesananGrooming.getStatus());

        database = FirebaseFirestore.getInstance();
        String myId = pesananGrooming.getId();
        String statusPesanan = pesananGrooming.getStatus();

        holder.var_mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (statusPesanan.equals("Dipesan")) {
                    new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Batalkan Pesanan?")
                            .setContentText("Anda tetap harus menunggu pihak iCat untuk menanggapi!")
                            .setConfirmText("Ya")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    database.collection("grooming")
                                            .document(myId)
                                            .update("status", "Meminta pembatalan");
                                    sDialog.dismissWithAnimation();
                                    ((LihatPesananActivity) context).finish();
                                    Intent refresh = new Intent(context, LihatPesananActivity.class);
                                    context.startActivity(refresh);
                                }
                            })
                            .setCancelButton("Kembali", new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                } else {
                    new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Pesanan telah diproses!")
                            .show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return pesananGroomingList.size();
    }

    public static class GroomingViewHolder extends RecyclerView.ViewHolder {
        TextView var_tanggal, var_jenisgrooming, var_jeniskucing, var_jenislayanan, var_tujuan, var_status;
        LinearLayout var_mainLayout;

        public GroomingViewHolder(@NonNull View itemView) {
            super(itemView);

            var_tanggal = itemView.findViewById(R.id.id_tanggal);
            var_jenisgrooming = itemView.findViewById(R.id.id_jenisgrooming);
            var_jeniskucing = itemView.findViewById(R.id.id_jeniskucing);
            var_jenislayanan = itemView.findViewById(R.id.id_jenislayanan);
            var_tujuan = itemView.findViewById(R.id.id_tujuan);
            var_status = itemView.findViewById(R.id.id_status);
            var_mainLayout = itemView.findViewById(R.id.id_mainLayout);
        }
    }
}
