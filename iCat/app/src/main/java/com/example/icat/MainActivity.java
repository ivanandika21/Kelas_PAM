package com.example.icat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    private TextView txtMasuk;
    private TextInputLayout txtInputNamaPengguna, txtInputKataSandi;
    private Button btnMasuk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // menghubungkan layout view dengan java
        txtMasuk = findViewById(R.id.txtMasuk);
        txtInputNamaPengguna = findViewById(R.id.txtInputNamaPengguna);
        txtInputKataSandi = findViewById(R.id.txtInputKataSandi);
        btnMasuk = findViewById(R.id.btnMasuk);

        // memberi event ke button login
        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user;
                user = txtInputNamaPengguna.getEditText().getText().toString();

                // txtMasuk.getText().toString();
                Intent intentToBeranda = new Intent(MainActivity.this, BerandaActivity.class);
                intentToBeranda.putExtra("user", user);
                startActivity(intentToBeranda);
            }
        });
    }
}