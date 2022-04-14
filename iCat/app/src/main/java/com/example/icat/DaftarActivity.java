package com.example.icat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class DaftarActivity extends AppCompatActivity {

    private EditText var_namalengkap, var_email, var_katasandi, var_konfirmasi;
    private Button var_btndaftar, var_btnkembali;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        var_namalengkap = findViewById(R.id.id_namalengkap);
        var_email = findViewById(R.id.id_email);
        var_katasandi = findViewById(R.id.id_katasandi);
        var_konfirmasi = findViewById(R.id.id_konfirmasi);

        var_btnkembali = findViewById(R.id.id_btnkembali);
        var_btndaftar = findViewById(R.id.id_btndaftar);

        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(DaftarActivity.this);
        progressDialog.setTitle("Proses");
        progressDialog.setMessage("Mohon tunggu ...");
        progressDialog.setCancelable(false);

        var_btnkembali.setOnClickListener(view -> {
            finish();
            startActivity(new Intent(getApplicationContext(), LandingActivity.class));
        });

        var_btndaftar.setOnClickListener(view -> {
           if (var_namalengkap.getText().length() > 0 &&
                   var_email.getText().length() > 0 &&
                   var_katasandi.getText().length() > 0 &&
                   var_konfirmasi.getText().length() > 0) {
               if (var_katasandi.getText().toString().equals(var_konfirmasi.getText().toString())){
                   daftar(var_namalengkap.getText().toString(),
                           var_email.getText().toString(),
                           var_katasandi.getText().toString());
               } else {
                   Toast.makeText(getApplicationContext(), "Kata sandi tidak cocok!", Toast.LENGTH_SHORT).show();
               }
           } else {
               Toast.makeText(getApplicationContext(), "Silakan lengkapi data!", Toast.LENGTH_SHORT).show();
           }
        });
    }

    private void daftar(String namalengkap, String email, String katasandi) {
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, katasandi).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    FirebaseUser firebaseUser = task.getResult().getUser();
                    if (firebaseUser != null) {
                        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                                .setDisplayName(namalengkap).build();
                        firebaseUser.updateProfile(request).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                reload();
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), "Gagal mendaftarkan akun", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }

    private void reload() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }
}