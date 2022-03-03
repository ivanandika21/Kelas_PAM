package com.example.icat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    private ImageView mainImage;
    private TextView tanggal, judul, deskripsi;
    private String info1, info2, info3;
    int images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mainImage = findViewById(R.id.mainImage);
        tanggal = findViewById(R.id.tanggal);
        judul = findViewById(R.id.judul);
        deskripsi = findViewById(R.id.deskripsi);

        getData();
        setData();
    }

    private void getData() {
        if (getIntent().hasExtra("images") && getIntent().hasExtra("info1")
                && getIntent().hasExtra("info2") && getIntent().hasExtra("info3")) {
            info1 = getIntent().getStringExtra("info1");
            info2 = getIntent().getStringExtra("info2");
            info3 = getIntent().getStringExtra("info3");
            images = getIntent().getIntExtra("images", 1);
        } else {
            Toast.makeText(this, "Tidak ada data",Toast.LENGTH_SHORT).show();
        }
    }

    private void setData() {
        tanggal.setText(info1);
        judul.setText(info2);
        deskripsi.setText(info3);
        mainImage.setImageResource(images);
    }
}