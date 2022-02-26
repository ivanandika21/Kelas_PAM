package com.example.icat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class BerandaActivity extends AppCompatActivity {

    private TextView txtHello;

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
    }
}