package com.example.pertemuan9;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Pesanan> pesananArrayList;
    PesananAdapter pesananAdapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;
    FloatingActionButton btnPesan;
    ImageView var_kosong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPesan = findViewById(R.id.btn_pesan);
        btnPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PesanActivity.class));
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Mempersiapkan data...");
        progressDialog.show();

        recyclerView = findViewById(R.id.id_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        var_kosong = findViewById(R.id.id_kosong);

        db = FirebaseFirestore.getInstance();
        pesananArrayList = new ArrayList<Pesanan>();
        pesananAdapter = new PesananAdapter(MainActivity.this, pesananArrayList);

        recyclerView.setAdapter(pesananAdapter);

        cekisi();
    }

    private void EventChangeListener() {
        db.collection("orders")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null){
                    if (progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                    Log.e("Firebase error", error.getMessage());
                    return;
                }

                for (DocumentChange dc : value.getDocumentChanges()){
                    if (dc.getType() == DocumentChange.Type.ADDED){
                        pesananArrayList.add(dc.getDocument().toObject(Pesanan.class));
                    }

                    pesananAdapter.notifyDataSetChanged();
                    if (progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                }
            }
        });
    }

    private void cekisi(){
        db.collection("orders")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(task.getResult().size() > 0) {
                                EventChangeListener();
                            } else {
                                if (progressDialog.isShowing()){
                                    progressDialog.dismiss();
                                }
                                var_kosong.setVisibility(View.VISIBLE);
                            }
                        } else {
                            if (progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                            var_kosong.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }
}