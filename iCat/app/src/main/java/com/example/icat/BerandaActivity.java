package com.example.icat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class BerandaActivity extends AppCompatActivity {

    private TextView txtHello;
    private ImageButton carouselBefore, carouselNext;
    private ImageSwitcher carouselImage;

    int index = 0;
    int indexImg[] = {R.drawable.frame1, R.drawable.frame2, R.drawable.frame3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda);

        // hubungkan layout activity_beranda dengan BerandaActivity.java
        txtHello = findViewById(R.id.txtHello);

        String user;
        Intent intent = getIntent();
        user = intent.getStringExtra("user");

        txtHello.append(user);


        // Codingan carousel
        carouselBefore = findViewById(R.id.carouselBefore);
        carouselNext = findViewById(R.id.carouselNext);
        carouselImage = findViewById(R.id.carouselImage);

        carouselBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                --index;
                if (index < 0){
                    index = indexImg.length-1;
                }
                carouselImage.setImageResource(indexImg[index]);
            }
        });

        carouselNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index++;
                if (index == indexImg.length){
                    index = 0;
                }
                carouselImage.setImageResource(indexImg[index]);
            }
        });

        carouselImage.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setMaxWidth(250);
                imageView.setMaxHeight(250);

                return imageView;
            }
        });

        carouselImage.setImageResource(indexImg[index]);
    }
}