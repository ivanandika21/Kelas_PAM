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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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

public class EditActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap gMap;
    private Marker selectedMarker;
    private LatLng selectedPlace;
    private FirebaseFirestore db;
    private TextView txtSelectedPlace;
    private EditText editTextName;
    private Button btnOrder;
    private String abc, tmp_street_tujuan, tmp_street_awal;
    private double saveLat;
    private double saveLong;
    private boolean isNewOrder = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        txtSelectedPlace = findViewById(R.id.txt_selectedPlace);
        editTextName = findViewById(R.id.editTxt_name);
        btnOrder = findViewById(R.id.btn_order);

        getData();

        db = FirebaseFirestore.getInstance();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        isNewOrder = false;

        String orderId = abc;
        DocumentReference order = db.collection("orders").document(orderId);
        order.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String name = document.get("name").toString();

                    Map < String, Object > data_awal = (HashMap < String, Object > ) document.get("dataawal");
                    saveLat = (double) data_awal.get("lat");
                    saveLong = (double) data_awal.get("lng");
                    tmp_street_awal = data_awal.get("address").toString();

                    LatLng awal = new LatLng((double) data_awal.get("lat"), (double) data_awal.get("lng"));

                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(awal);
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                    gMap.addMarker(markerOptions);

                    Map < String, Object > data_tujuan = (HashMap < String, Object > ) document.get("datatujuan");

                    editTextName.setText(name);
                    txtSelectedPlace.setText(data_tujuan.get("address").toString());

                    tmp_street_tujuan = data_tujuan.get("address").toString();

                    LatLng resultPlace = new LatLng((double) data_tujuan.get("lat"), (double) data_tujuan.get("lng"));
                    selectedPlace = resultPlace;
                    selectedMarker.setPosition(selectedPlace);
                    gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(selectedPlace, 15.0f));
                } else {
                    isNewOrder = true;
                    Toast.makeText(this, "Document does not exist!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Unable to read the db!", Toast.LENGTH_SHORT).show();
            }
        });

        btnOrder.setOnClickListener(view -> {
            saveOrder();
        });
    }

    private void getData() {
        if (getIntent().hasExtra("abc")) {
            abc = getIntent().getStringExtra("abc");
        } else {
            Toast.makeText(this, "Tidak ada data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;

        LatLng Salatiga = new LatLng(-7.3305, 110.5084);

        selectedPlace = Salatiga;
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
            List < Address > addresses = geocoder.getFromLocation(selectedPlace.latitude, selectedPlace.longitude, 1);
            if (addresses != null) {
                Address data_tujuan = addresses.get(0);
                StringBuilder street_tujuan = new StringBuilder();

                for (int i = 0; i <= data_tujuan.getMaxAddressLineIndex(); i++) {
                    street_tujuan.append(data_tujuan.getAddressLine(i)).append("\n");
                }

                txtSelectedPlace.setText(street_tujuan.toString());
                tmp_street_tujuan = street_tujuan.toString();
            } else {
                Toast.makeText(this, "Could not find Address!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error get Address!", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveOrder() {
        Map < String, Object > order = new HashMap <> ();
        Map < String, Object > data_tujuan = new HashMap <> ();
        Map < String, Object > data_awal = new HashMap <> ();

        String name = editTextName.getText().toString();
        String orderId = abc;

        Date now = Calendar.getInstance().getTime();
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, h:mm a");
        String currentTime = df.format(now);

        data_tujuan.put("address", txtSelectedPlace.getText().toString());
        data_tujuan.put("lat", selectedPlace.latitude);
        data_tujuan.put("lng", selectedPlace.longitude);

        data_awal.put("address", tmp_street_awal);
        data_awal.put("lat", saveLat);
        data_awal.put("lng", saveLong);

        order.put("id", orderId);
        order.put("name", name);
        order.put("date", currentTime);

        order.put("dataawal", data_awal);
        order.put("awal", tmp_street_awal);

        order.put("datatujuan", data_tujuan);
        order.put("tujuan", tmp_street_tujuan);

        db.collection("orders")
                .document(orderId)
                .set(order)
                .addOnSuccessListener(unused -> {
                    isNewOrder = true;
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Gagal ubah data order", Toast.LENGTH_SHORT).show();
                });

        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
        this.finish();
    }
}