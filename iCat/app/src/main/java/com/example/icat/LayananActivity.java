package com.example.icat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class LayananActivity extends AppCompatActivity {

    private TextView var_judul, var_deskripsi;
    private ImageButton var_carouselbefore, var_carouselnext;
    private ImageSwitcher var_carouselimage;
    int index = 0;

    int indexImg[] = {
            R.drawable.frame1,
            R.drawable.frame2,
            R.drawable.frame3
    };

    String[] indexJudul = {
            "Grooming",
            "Nail Care",
            "Penitipan"
    };

    String[] indexDeskripsi = {
            "Grooming adalah layanan memandikan kucing dengan tenaga professional",
            "Perawatan kuku kucing agar tetap bersih dan terhindar dari penyakit",
            "Kamu bisa menitipkan kucingmu saat kamu sibuk mengurus hal lain"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layanan);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        var_judul = findViewById(R.id.id_judul);
        var_deskripsi = findViewById(R.id.id_deskripsi);

        var_carouselbefore = findViewById(R.id.carouselBefore);
        var_carouselnext = findViewById(R.id.carouselNext);
        var_carouselimage = findViewById(R.id.carouselImage);

        var_carouselbefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                --index;
                if (index < 0) {
                    index = indexImg.length - 1;
                }
                var_carouselimage.setImageResource(indexImg[index]);
                var_judul.setText(indexJudul[index]);
                var_deskripsi.setText(indexDeskripsi[index]);
            }
        });
        var_carouselnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ++index;
                if (index == indexImg.length) {
                    index = 0;
                }
                var_carouselimage.setImageResource(indexImg[index]);
                var_judul.setText(indexJudul[index]);
                var_deskripsi.setText(indexDeskripsi[index]);
            }
        });
        var_carouselimage.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                return imageView;
            }
        });
        var_carouselimage.setImageResource(indexImg[index]);
        var_judul.setText(indexJudul[index]);
        var_deskripsi.setText(indexDeskripsi[index]);
    }
}