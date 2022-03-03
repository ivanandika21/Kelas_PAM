package com.example.icat.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.icat.DetailActivity;
import com.example.icat.R;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.MyViewHolder> {
    String info1[], info2[];
    int images[];
    Context context;

    public InfoAdapter(Context ct, String judul[], String deskripsi[], int img[]) {
        context = ct;
        info1 = judul;
        info2 = deskripsi;
        images = img;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_info, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.judul.setText(info1[position]);
        holder.deskripsi.setText(info2[position]);
        holder.images.setImageResource(images[position]);

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("info1", info1[position]);
                intent.putExtra("info2", info2[position]);
                intent.putExtra("images", images[position]);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return images.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView judul, deskripsi;
        ImageView images;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.judul);
            deskripsi = itemView.findViewById(R.id.deskripsi);
            images = itemView.findViewById(R.id.images);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
