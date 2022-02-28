package com.example.icat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;

public class PesananActivity extends AppCompatActivity {

    private Toolbar main_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan);

        // Codingan Toolbar
        main_toolbar = findViewById(R.id.main_toolbar);
        main_toolbar.setTitle("Buat Pesanan");
        setSupportActionBar(main_toolbar);
    }

    // Codingan Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}