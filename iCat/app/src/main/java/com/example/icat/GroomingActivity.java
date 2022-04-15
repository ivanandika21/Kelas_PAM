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

    private CheckBox var_layananstandar, var_layananmedical, var_layananhealth;
    private RadioGroup bulu;
    private Button var_btnkemaps;
    private String jeniskucing, jenislayananbaru;
    private List<String> jenislayanan = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grooming);

        var_layananstandar = findViewById(R.id.id_layananstandar);
        var_layananmedical = findViewById(R.id.id_layananmedical);
        var_layananhealth = findViewById(R.id.id_layananhealth);

        if (var_layananstandar.isChecked()){
            jenislayanan.add("Standar Grooming");
        }
        if (var_layananmedical.isChecked()){
            jenislayanan.add("Medical Grooming");
        }
        if (var_layananhealth.isChecked()){
            jenislayanan.add("Health Grooming");
        }

        jenislayananbaru = jenislayanan.toString();

        bulu = findViewById(R.id.bulu);
        bulu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch (id){
                    case R.id.id_bulupendek:
                        jeniskucing = "Bulu Pendek";
                        break;
                    case R.id.id_bulupanjang:
                        jeniskucing = "Bulu Panjang";
                        break;
                }
            }
        });

        var_btnkemaps = findViewById(R.id.id_btnkemaps);
        var_btnkemaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("jenislayananbaru", jenislayananbaru);
                intent.putExtra("jeniskucing", jeniskucing);
                startActivity(intent);
            }
        });
    }
}