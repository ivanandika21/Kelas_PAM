package com.example.icat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.example.icat.adapter.UserAdapter;
import com.example.icat.database.AppDatabase;
import com.example.icat.database.entitas.User;

import java.util.ArrayList;
import java.util.List;

public class PesananActivity extends AppCompatActivity {

    private Toolbar main_toolbar;
    private RecyclerView recyclerView;
    private Button btn_tambah;
    private AppDatabase database;
    private UserAdapter userAdapter;
    private List<User> list = new ArrayList<>();
    private AlertDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan);

        recyclerView = findViewById(R.id.recycler_view);

        database = AppDatabase.getInstance(getApplicationContext());
        list.clear();
        list.addAll(database.userDao().getAll());
        userAdapter = new UserAdapter(getApplicationContext(), list);
        userAdapter.setDialog(new UserAdapter.Dialog() {
            @Override
            public void onClick(int position) {
                final CharSequence[] dialogItem = {"Edit", "Hapus"};
                dialog = new AlertDialog.Builder(PesananActivity.this);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                Intent intent = new Intent(PesananActivity.this, TambahActivity.class);
                                intent.putExtra("uid", list.get(position).uid);
                                startActivity(intent);
                                break;
                            case 1 :
                                User user = list.get(position);
                                database.userDao().delete(user);
                                onStart();
                                break;
                        }
                    }
                });
                dialog.show();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(userAdapter);

        btn_tambah = findViewById(R.id.btn_tambah);
        btn_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PesananActivity.this, TambahActivity.class));
            }
        });


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

    @Override
    protected void onStart() {
        super.onStart();
        list.clear();
        list.addAll(database.userDao().getAll());
        userAdapter.notifyDataSetChanged();
    }
}