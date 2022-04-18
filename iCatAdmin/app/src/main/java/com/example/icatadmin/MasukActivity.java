package com.example.icatadmin;

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

public class MasukActivity extends AppCompatActivity {

    private EditText var_email, var_katasandi;
    private Button var_btnmasuk, var_btnkembali;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masuk);

        var_email = findViewById(R.id.id_email);
        var_katasandi = findViewById(R.id.id_katasandi);

        var_btnkembali = findViewById(R.id.id_btnkembali);
        var_btnmasuk = findViewById(R.id.id_btnmasuk);

        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(MasukActivity.this);
        progressDialog.setTitle("Proses");
        progressDialog.setMessage("Mohon tunggu ...");
        progressDialog.setCancelable(false);

        var_btnmasuk.setOnClickListener(view -> {
            if (var_email.getText().length() > 0 && var_katasandi.getText().length() > 0) {
                masuk(var_email.getText().toString(), var_katasandi.getText().toString());
            } else {
                Toast.makeText(getApplicationContext(), "Silakan lengkapi data!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void masuk(String email, String katasandi) {
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email, katasandi).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    if (task.getResult() != null) {
                        reload();
                    } else {
                        Toast.makeText(getApplicationContext(), "Gagal masuk dengan akun", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Gagal masuk dengan akun", Toast.LENGTH_SHORT).show();
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