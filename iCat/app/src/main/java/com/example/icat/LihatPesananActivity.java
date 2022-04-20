package com.example.icat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.icat.adapters.PesananGroomingAdapter;
import com.example.icat.models.PesananGrooming;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class LihatPesananActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<PesananGrooming> pesananGroomingList;
    private PesananGroomingAdapter pesananGroomingAdapter;
    private ProgressDialog progressDialog;
    private FirebaseFirestore db;
    private ImageView var_kosong;
    private String atasnama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_pesanan);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Mempersiapkan data...");
        progressDialog.show();

        recyclerView = findViewById(R.id.id_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        var_kosong = findViewById(R.id.id_kosong);

        db = FirebaseFirestore.getInstance();
        pesananGroomingList = new ArrayList<PesananGrooming>();
        pesananGroomingAdapter = new PesananGroomingAdapter(LihatPesananActivity.this, pesananGroomingList);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        atasnama = firebaseUser.getDisplayName();

        recyclerView.setAdapter(pesananGroomingAdapter);
        cekisi();
    }

    private void cekisi(){
        db.collection("grooming")
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

    private void EventChangeListener() {
        db.collection("grooming")
                .whereEqualTo("atasnama", atasnama)
                .orderBy("timestamp", Query.Direction.DESCENDING)
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
                                pesananGroomingList.add(dc.getDocument().toObject(PesananGrooming.class));
                            }

                            pesananGroomingAdapter.notifyDataSetChanged();
                            if (progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                        }
                    }
                });
    }
}