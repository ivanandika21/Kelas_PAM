package com.example.icat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    ImageView mainImage;
    TextView judul, deskripsi;
    String info1, info2;
    int images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mainImage = findViewById(R.id.mainImage);
        judul = findViewById(R.id.judul);
        deskripsi = findViewById(R.id.deskripsi);

        getData();
        setData();
    }

    private void getData() {
        if (getIntent().hasExtra("images") && getIntent().hasExtra("info1") && getIntent().hasExtra("info2")) {
            info1 = getIntent().getStringExtra("info1");
            info2 = getIntent().getStringExtra("info2");
            images = getIntent().getIntExtra("images", 1);
        } else {
            Toast.makeText(this, "Tidak ada data",Toast.LENGTH_SHORT).show();
        }
    }

    private void setData() {
        judul.setText(info1);
        deskripsi.setText(info2);
        mainImage.setImageResource(images);
    }
}