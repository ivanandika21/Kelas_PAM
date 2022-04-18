package com.example.icatadmin;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Pesanan> pesananList;
    private PesananAdapter pesananAdapter;
    private ProgressDialog progressDialog;
    private FirebaseFirestore db;
    private ImageView var_kosong;
    private String atasnama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Mempersiapkan data...");
        progressDialog.show();

        recyclerView = findViewById(R.id.id_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        var_kosong = findViewById(R.id.id_kosong);

        db = FirebaseFirestore.getInstance();
        pesananList = new ArrayList<Pesanan>();
        pesananAdapter = new PesananAdapter(MainActivity.this, pesananList);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        atasnama = firebaseUser.getDisplayName();

        recyclerView.setAdapter(pesananAdapter);
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
                                pesananList.add(dc.getDocument().toObject(Pesanan.class));
                            }

                            pesananAdapter.notifyDataSetChanged();
                            if (progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                        }
                    }
                });
    }
}