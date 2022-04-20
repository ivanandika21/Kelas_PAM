package com.example.icat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

public class GroomingActivity extends AppCompatActivity {

    private CheckBox var_layananNail, var_layananTitip;
    private RadioGroup var_jenisGrooming, var_jenisKucing;
    private Button var_btnkemaps;
    private String jenisGrooming, jenisKucing, jenisLayananbaru;
    private List<String> jenisLayanan = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grooming);

        var_layananNail = findViewById(R.id.id_layananNail);
        var_layananTitip = findViewById(R.id.id_layananTitip);

        var_jenisGrooming = findViewById(R.id.id_jenisGrooming);
        var_jenisGrooming.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch (id){
                    case R.id.id_layananStandar:
                        jenisGrooming = "Grooming Standar";
                        break;
                    case R.id.id_layananMedical:
                        jenisGrooming = "Grooming Medical";
                        break;
                    case R.id.id_layananHealth:
                        jenisGrooming = "Grooming Health";
                        break;
                }
            }
        });

        var_jenisKucing = findViewById(R.id.id_jenisKucing);
        var_jenisKucing.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch (id){
                    case R.id.id_bulupendek:
                        jenisKucing = "Bulu Pendek";
                        break;
                    case R.id.id_bulupanjang:
                        jenisKucing = "Bulu Panjang";
                        break;
                }
            }
        });

        var_btnkemaps = findViewById(R.id.id_btnkemaps);
        var_btnkemaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (var_layananNail.isChecked()){
                    jenisLayanan.add("Nail Care");
                }
                if (var_layananTitip.isChecked()){
                    jenisLayanan.add("Penitipan");
                }

                jenisLayananbaru = jenisLayanan.toString().replaceAll("[\\[\\]]", "");

                Intent intent = new Intent(getApplicationContext(), MapsGroomingActivity.class);
                intent.putExtra("jenisLayananbaru", jenisLayananbaru);
                intent.putExtra("jenisGrooming", jenisGrooming);
                intent.putExtra("jenisKucing", jenisKucing);
                startActivity(intent);
            }
        });
    }
}