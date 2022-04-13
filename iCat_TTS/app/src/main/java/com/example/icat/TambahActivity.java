package com.example.icat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;

import com.example.icat.database.AppDatabase;
import com.example.icat.database.entitas.User;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class TambahActivity extends AppCompatActivity {

    private Toolbar main_toolbar;
    private CheckBox layanan1, layanan2, layanan3;
    private RadioGroup bulu, antarjemput;
    private Button btn_save;
    private AppDatabase database;
    private List<String> jenis_layanan = new ArrayList<>();
    private String jenis_kucing, isAntarJemput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        layanan1 = findViewById(R.id.layanan1);
        layanan2 = findViewById(R.id.layanan2);
        layanan3 = findViewById(R.id.layanan3);

        database = AppDatabase.getInstance(getApplicationContext());

        bulu = findViewById(R.id.bulu);
        bulu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch (id){
                    case R.id.bulu1:
                        jenis_kucing = "Bulu Pendek";
                        break;
                    case R.id.bulu2:
                        jenis_kucing = "Bulu Panjang";
                        break;
                }
            }
        });

        antarjemput = findViewById(R.id.antarjemput);
        antarjemput.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch (id){
                    case R.id.ya:
                        isAntarJemput = "Ya, jemput kucing saya";
                        break;
                    case R.id.tidak:
                        isAntarJemput = "Tidak, saya akan antar kesana";
                        break;
                }
            }
        });

        btn_save = findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (layanan1.isChecked()){
                    jenis_layanan.add("Standar Grooming");
                }
                if (layanan2.isChecked()){
                    jenis_layanan.add("Medical Grooming");
                }
                if (layanan3.isChecked()){
                    jenis_layanan.add("Health Grooming");
                }

                database.userDao().insertAll(jenis_layanan.toString(), jenis_kucing, isAntarJemput);
                finish();
            }
        });

        main_toolbar = findViewById(R.id.main_toolbar);
        main_toolbar.setTitle("Buat Pesanan");
        setSupportActionBar(main_toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}