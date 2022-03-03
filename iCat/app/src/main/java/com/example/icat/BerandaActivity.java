package com.example.icat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.icat.adapter.InfoAdapter;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BerandaActivity extends AppCompatActivity {

    private TextView txtHello;
    private ImageButton carouselBefore, carouselNext, keChat;
    private ImageSwitcher carouselImage;
    private FloatingActionButton btn_add;
    private MaterialCardView btn_pesanan;
    private String tanggal[], judul[], deskripsi[];
    private Toolbar main_toolbar;
    private RecyclerView recyler_info;
    int index = 0;
    int images[] = {
            R.drawable.frame1,
            R.drawable.frame2,
            R.drawable.frame3
    };
    int indexImg[] = {
            R.drawable.frame1,
            R.drawable.frame2,
            R.drawable.frame3
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda);

        recyler_info = findViewById(R.id.recyler_info);
        tanggal = getResources().getStringArray(R.array.tanggal);
        judul = getResources().getStringArray(R.array.judul);
        deskripsi = getResources().getStringArray(R.array.deskripsi);
        InfoAdapter infoAdapter = new InfoAdapter(this, tanggal, judul, deskripsi, images);
        recyler_info.setAdapter(infoAdapter);
        recyler_info.setLayoutManager(new LinearLayoutManager(this));

        keChat = findViewById(R.id.keChat);
        keChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BerandaActivity.this, ChatActivity.class));
            }
        });

        btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BerandaActivity.this, TambahActivity.class));
            }
        });

        btn_pesanan = findViewById(R.id.btn_pesanan);
        btn_pesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BerandaActivity.this, PesananActivity.class));
            }
        });

        txtHello = findViewById(R.id.txtHello);
        String user;
        Intent intent = getIntent();
        user = intent.getStringExtra("user");
        user = user.substring(0, 1).toUpperCase() + user.substring(1);
        txtHello.append(user);

        carouselBefore = findViewById(R.id.carouselBefore);
        carouselNext = findViewById(R.id.carouselNext);
        carouselImage = findViewById(R.id.carouselImage);
        carouselBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                --index;
                if (index < 0) {
                    index = indexImg.length - 1;
                }
                carouselImage.setImageResource(indexImg[index]);
            }
        });
        carouselNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ++index;
                if (index == indexImg.length) {
                    index = 0;
                }
                carouselImage.setImageResource(indexImg[index]);
            }
        });
        carouselImage.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setMaxWidth(1000);
                imageView.setMaxHeight(600);
                return imageView;
            }
        });
        carouselImage.setImageResource(indexImg[index]);

        main_toolbar = findViewById(R.id.main_toolbar);
        main_toolbar.setTitle("Beranda");
        setSupportActionBar(main_toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}