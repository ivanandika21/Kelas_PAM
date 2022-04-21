package com.example.icat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

public class TentangActivity extends AppCompatActivity {
    private Button var_btnInsta, var_btnShopee, var_btnTokped;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentang);

        var_btnInsta = findViewById(R.id.id_btnInsta);
        var_btnInsta.setOnClickListener(view -> {
            String url = "http://www.instagram.com/icatgroomer/";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });

        var_btnShopee = findViewById(R.id.id_btnShopee);
        var_btnShopee.setOnClickListener(view -> {
            String url = "http://shopee.co.id/icatpetshop/";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });
        
        var_btnTokped = findViewById(R.id.id_btnTokped);
        var_btnTokped.setOnClickListener(view -> {
            String url = "http://www.tokopedia.com/icatlabs/";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });
    }
}