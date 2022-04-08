package com.example.pertemuan9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PesanActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap gMap;
    private Marker selectedMarker;
    private LatLng selectedPlace;

    private FirebaseFirestore db;

    private TextView txtOrderId, txtSelectedPlace;
    private EditText editTextName;
    private Button btnEditOrder, btnOrder, btnPesanan;

    private boolean isNewOrder = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan);

        txtOrderId = findViewById(R.id.txt_orderId);
        txtSelectedPlace = findViewById(R.id.txt_selectedPlace);
        editTextName = findViewById(R.id.editTxt_name);
        btnEditOrder = findViewById(R.id.btn_editOrder);
        btnOrder = findViewById(R.id.btn_order);

        db = FirebaseFirestore.getInstance();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btnPesanan = findViewById(R.id.btn_pesanan);
        btnPesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PesanActivity.this, MainActivity.class));
            }
        });

        btnOrder.setOnClickListener(view -> { saveOrder(); });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;

        LatLng salatiga = new LatLng(-7.3305, 110.5084);

        selectedPlace = salatiga;
        selectedMarker = gMap.addMarker(new MarkerOptions().position(selectedPlace));

        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(selectedPlace, 15.0f));

        gMap.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        selectedPlace = latLng;
        selectedMarker.setPosition(selectedPlace);
        gMap.animateCamera(CameraUpdateFactory.newLatLng(selectedPlace));

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(selectedPlace.latitude, selectedPlace.longitude, 1);
            if (addresses != null) {
                Address place = addresses.get(0);
                StringBuilder street = new StringBuilder();

                for (int i=0; i <= place.getMaxAddressLineIndex(); i++) {
                    street.append(place.getAddressLine(i)).append("\n");
                }

                txtSelectedPlace.setText(street.toString());
            }
            else {
                Toast.makeText(this, "Could not find Address!", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e) {
            Toast.makeText(this, "Error get Address!", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveOrder() {
        Map<String, Object> order = new HashMap<>();
        Map<String, Object> place = new HashMap<>();

        String name = editTextName.getText().toString();
        String orderId = txtOrderId.getText().toString();

        Date now = Calendar.getInstance().getTime();
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, h:mm a");
        String currentTime = df.format(now);


        place.put("address", txtSelectedPlace.getText().toString());
        place.put("lat", selectedPlace.latitude);
        place.put("lng", selectedPlace.longitude);

        order.put("name", name);
        order.put("date", currentTime);
        order.put("addressdata", place);
        order.put("address", txtSelectedPlace.getText().toString());
        DocumentReference ref = db.collection("orders").document();
        String myId = ref.getId();
        order.put("id", myId);


        if (isNewOrder) {
            db.collection("orders").document(myId)
                    .set(order)
                    .addOnSuccessListener(documentReference -> {
                        editTextName.setText("");
                        txtSelectedPlace.setText("Pilih tempat");
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Gagal tambah data order", Toast.LENGTH_SHORT).show();
                    });
        }
        else {
            db.collection("orders").document(orderId)
                    .set(order)
                    .addOnSuccessListener(unused -> {
                        editTextName.setText("");
                        txtSelectedPlace.setText("");
                        txtOrderId.setText(orderId);

                        isNewOrder = true;
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Gagal ubah data order", Toast.LENGTH_SHORT).show();
                    });
        }
    }
}