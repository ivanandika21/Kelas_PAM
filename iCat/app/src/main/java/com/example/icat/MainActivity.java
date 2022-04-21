package com.example.icat;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private TextView var_namalengkap;
    private FirebaseUser firebaseUser;
    private ImageView var_btnlogout;
    private MaterialCardView var_btngrooming, var_btnlayanan, var_btnPesananGrooming, var_btnabout, var_btnHubungi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        var_namalengkap = findViewById(R.id.id_namalengkap);

        var_btnlogout = findViewById(R.id.id_btnlogout);
        var_btngrooming = findViewById(R.id.id_btngrooming);
        var_btnlayanan = findViewById(R.id.id_btnlayanan);
        var_btnPesananGrooming = findViewById(R.id.id_btnPesananGrooming);
        var_btnabout = findViewById(R.id.id_btnabout);
        var_btnHubungi = findViewById(R.id.id_btnHubungi);

        if (firebaseUser != null) {
            var_namalengkap.setText(firebaseUser.getDisplayName());
        } else {
            var_namalengkap.setText("Login Gagal");
        }

        var_btnlogout.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            finish();
        });

        var_btngrooming.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), GroomingActivity.class));
        });

        var_btnlayanan.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), LayananActivity.class));
        });

        var_btnPesananGrooming.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), LihatPesananActivity.class));
        });

        var_btnabout.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), TentangActivity.class));
        });

        var_btnHubungi.setOnClickListener(view -> {
            String url = "https://api.whatsapp.com/send?phone=62895391703636&text=Halo%20admin%2C%20aku%20mau%20tanya%0A";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });
    }
}