package com.example.icat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.example.icat.database.AppDatabase;
import com.example.icat.database.entitas.User;
import com.google.android.material.textfield.TextInputEditText;

public class TambahActivity extends AppCompatActivity {

    private Toolbar main_toolbar;
    private TextInputEditText full_name, email;
    private Button btn_save;
    private AppDatabase database;
    private int uid = 0;
    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);
        full_name = findViewById(R.id.full_name);
        email = findViewById(R.id.email);
        btn_save = findViewById(R.id.btn_save);
        database = AppDatabase.getInstance(getApplicationContext());

        // Codingan Toolbar
        main_toolbar = findViewById(R.id.main_toolbar);
        main_toolbar.setTitle("Buat Pesanan");
        setSupportActionBar(main_toolbar);

        Intent intent = getIntent();
        uid = intent.getIntExtra("uid", 0);
        if (uid > 0) {
            isEdit = true;
            User user = database.userDao().get(uid);
            full_name.setText(user.fullName);
            email.setText(user.email);
        } else {
            isEdit = false;
        }

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                User user = new User();
//                user.fullName = full_name.getText().toString();
//                user.email = email.getText().toString();
                if (isEdit) {
                    database.userDao().update(uid, full_name.getText().toString(), email.getText().toString());
                } else {
                    database.userDao().insertAll(full_name.getText().toString(), email.getText().toString());
                }
                finish();
            }
        });
    }

    // Codingan Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}