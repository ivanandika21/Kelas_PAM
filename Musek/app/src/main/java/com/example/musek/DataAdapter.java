package com.example.musek;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    ArrayList<DataModel> var_daftarlagu;
    Context context;

    public DataAdapter(ArrayList<DataModel> var_daftarlagu, Context context) {
        this.var_daftarlagu = var_daftarlagu;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_item, parent, false);
        return new DataAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder holder, int position) {
        DataModel var_datalagu = var_daftarlagu.get(position);
        holder.var_title.setText(var_datalagu.getTitle());
//        holder.var_artist.setText(var_datalagu.getArtist());
    }

    @Override
    public int getItemCount() {
        return var_daftarlagu.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView var_title, var_artist;
        ImageView var_dot;

        public ViewHolder(View itemView) {
            super(itemView);

            var_title = itemView.findViewById(R.id.id_title);
//            var_artist = itemView.findViewById(R.id.id_artist);
            var_dot = itemView.findViewById(R.id.id_dot);
        }
    }
}
