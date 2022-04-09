package com.example.pertemuan9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PesanActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener{

    private GoogleMap gMap;
    private Marker selectedMarker;
    private LatLng selectedPlace;

    private FirebaseFirestore db;

    private TextView txtSelectedPlace;
    private EditText editTextName;
    private Button btnOrder;

    String txtOrderId;
    private double saveLat;
    private double saveLong;
    private String tmp_street_tujuan, tmp_street_awal;

    SupportMapFragment supportMapFragment;
    LocationRequest locationRequest;
    Location lastLocation;
    Marker currLocationMarker;
    FusedLocationProviderClient client;


    private boolean isNewOrder = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan);

        txtSelectedPlace = findViewById(R.id.txt_selectedPlace);
        editTextName = findViewById(R.id.editTxt_name);
        btnOrder = findViewById(R.id.btn_order);

        db = FirebaseFirestore.getInstance();

        client = LocationServices.getFusedLocationProviderClient(this);
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);


        btnOrder.setOnClickListener(view -> { saveOrder(); });
    }

    @Override
    public void onPause() {
        super.onPause();

        if (client != null) {
            client.removeLocationUpdates(mLocationCallback);
        }
    }

    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            List<Location> locationList = locationResult.getLocations();
            if (locationList.size() > 0) {
                Location location = locationList.get(locationList.size() - 1);
                lastLocation = location;
                if (currLocationMarker != null) {
                    currLocationMarker.remove();
                }

                // marker ke lokasi awal
                LatLng awal = new LatLng(location.getLatitude(), location.getLongitude());

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(awal);
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                currLocationMarker = gMap.addMarker(markerOptions);

                // marker ke tujuan awal
                saveLat = location.getLatitude();
                saveLong = location.getLongitude();
                LatLng tujuan = new LatLng(saveLat, saveLong);

                selectedPlace = tujuan;
                selectedMarker = gMap.addMarker(new MarkerOptions().position(selectedPlace));
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(selectedPlace, 15.0f));
            }
        }
    };

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;

        locationRequest = new LocationRequest();
        locationRequest.setInterval(120000);
        locationRequest.setFastestInterval(120000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                client.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper());
                gMap.setMyLocationEnabled(true);
            } else {
                checkLocationPermission();
            }
        }
        else {
            client.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper());
            gMap.setMyLocationEnabled(true);
        }

        gMap.setOnMapClickListener(this);
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                new AlertDialog.Builder(this)
                        .setTitle("Membutuhkan Akses Lokasi")
                        .setMessage("Aplikasi ini membutuhkan akses lokasi untuk menentukan lokasi Anda")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(PesanActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        })
                        .create()
                        .show();


            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }

    @SuppressLint({"MissingSuperCall", "MissingPermission"})
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        client.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper());
                        gMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(this, "Permintaak akses lokasi ditolak", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
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
                Address data_tujuan = addresses.get(0);
                StringBuilder street_tujuan = new StringBuilder();

                for (int i=0; i <= data_tujuan.getMaxAddressLineIndex(); i++) {
                    street_tujuan.append(data_tujuan.getAddressLine(i)).append("\n");
                }

                txtSelectedPlace.setText(street_tujuan.toString());
                tmp_street_tujuan = street_tujuan.toString();
            }
            else {
                Toast.makeText(this, "Tidak dapat menemukan alamat!", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e) {
            Toast.makeText(this, "Tidak dapat menemukan alamat!", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveOrder() {
        Map<String, Object> order = new HashMap<>();
        Map<String, Object> data_tujuan = new HashMap<>();
        Map<String, Object> data_awal = new HashMap<>();

        String name = editTextName.getText().toString();
        String orderId = txtOrderId;

        Date now = Calendar.getInstance().getTime();
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, h:mm a");
        String currentTime = df.format(now);


        data_tujuan.put("address", txtSelectedPlace.getText().toString());
        data_tujuan.put("lat", selectedPlace.latitude);
        data_tujuan.put("lng", selectedPlace.longitude);

        data_awal.put("lat", saveLat);
        data_awal.put("lng", saveLong);

        DocumentReference ref = db.collection("orders").document();
        String myId = ref.getId();
        
        order.put("id", myId);
        order.put("name", name);
        order.put("date", currentTime);
        
        order.put("dataawal", data_awal);

        order.put("datatujuan", data_tujuan);
        order.put("tujuan", tmp_street_tujuan);
        


        if (isNewOrder) {
            db.collection("orders")
                    .document(myId)
                    .set(order)
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Gagal tambah data pesanan", Toast.LENGTH_SHORT).show();
                    });
        }
        else {
            db.collection("orders")
                    .document(orderId)
                    .set(order)
                    .addOnSuccessListener(unused -> {
                        isNewOrder = true;
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Gagal ubah data pesanan", Toast.LENGTH_SHORT).show();
                    });
        }
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
        this.finish();
    }
}