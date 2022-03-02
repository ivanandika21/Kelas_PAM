package com.example.icat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.icat.R;
import com.example.icat.database.entitas.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewAdapter> {

    private List<User> list;
    private Context context;
    private Dialog dialog;

    public interface Dialog {
        void onClick(int position);
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public UserAdapter(Context context, List<User> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user, parent, false);
        return new ViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAdapter holder, int position) {
        holder.jenis_layanan.setText(list.get(position).jenis_layanan);
        holder.jenis_kucing.setText(list.get(position).jenis_kucing);
        holder.isAntarJemput.setText(list.get(position).isAntarJemput);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewAdapter extends RecyclerView.ViewHolder {

        TextView jenis_layanan, jenis_kucing, isAntarJemput;

        public ViewAdapter(@NonNull View itemView) {
            super(itemView);

            jenis_layanan = itemView.findViewById(R.id.jenis_layanan);
            jenis_kucing = itemView.findViewById(R.id.jenis_kucing);
            isAntarJemput = itemView.findViewById(R.id.isAntarJemput);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (dialog != null) {
                        dialog.onClick(getLayoutPosition());
                    }
                }
            });
        }
    }
}
