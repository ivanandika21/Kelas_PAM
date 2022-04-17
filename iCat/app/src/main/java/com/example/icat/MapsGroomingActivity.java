package com.example.icat;

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
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MapsGroomingActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap googleMap;
    private FirebaseFirestore database;
    private FusedLocationProviderClient client;
    private SupportMapFragment supportMapFragment;
    private LocationRequest locationRequest;
    private Button var_btnPesan;
    private ImageButton var_btnLokasiku;
    private Marker markerTerpilih;
    private LatLng latlngTerpilih;
    private TextView var_lokasiTerpilih;
    private String var_jalan, atasnama;
    private double saveLat, saveLong;
    private boolean isNewOrder = true;
    private Location lastLocation;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_grooming);

        var_lokasiTerpilih = findViewById(R.id.txt_selectedPlace);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        atasnama = firebaseUser.getDisplayName();

        database = FirebaseFirestore.getInstance();
        client = LocationServices.getFusedLocationProviderClient(this);
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);

        var_btnLokasiku = findViewById(R.id.id_btnlokasiku);
        var_btnLokasiku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LatLng tujuan = new LatLng(saveLat, saveLong);

                latlngTerpilih = tujuan;
                markerTerpilih.setPosition(latlngTerpilih);
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latlngTerpilih));

                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                List <Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocation(latlngTerpilih.latitude, latlngTerpilih.longitude, 1);
                    if (addresses != null) {
                        Address data_tujuan = addresses.get(0);
                        StringBuilder jalan = new StringBuilder();

                        for (int i = 0; i <= data_tujuan.getMaxAddressLineIndex(); i++) {
                            jalan.append(data_tujuan.getAddressLine(i)).append("\n");
                        }

                        var_lokasiTerpilih.setText(jalan.toString());
                        var_jalan = jalan.toString();
                    } else {
                        Toast.makeText(getApplicationContext(), "Alamat tidak ditemukan, coba pilih area disekitarmu!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Alamat tidak ditemukan, coba pilih area disekitarmu!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        var_btnPesan = findViewById(R.id.id_btnpesan);
        var_btnPesan.setOnClickListener(view -> {
                saveOrder();
        });
    }

    private void saveOrder() {
        Map <String, Object> order = new HashMap <> ();
        Map <String, Object> data_tujuan = new HashMap <> ();

        Intent intent = getIntent();
        String jenislayanan = intent.getStringExtra("jenislayananbaru");
        String jeniskucing = intent.getStringExtra("jeniskucing");

        Date now = Calendar.getInstance().getTime();
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, h:mm a");
        String currentTime = df.format(now);

        DocumentReference ref = database.collection("orders").document();
        String myId = ref.getId();

        data_tujuan.put("address", var_lokasiTerpilih.getText().toString());
        data_tujuan.put("lat", latlngTerpilih.latitude);
        data_tujuan.put("lng", latlngTerpilih.longitude);
        order.put("datatujuan", data_tujuan);
        order.put("tujuan", var_jalan);

        order.put("id", myId);
        order.put("jenislayanan", jenislayanan);
        order.put("jeniskucing", jeniskucing);
        order.put("tanggal", currentTime);
        order.put("atasnama", atasnama);

        database.collection("grooming")
            .document(myId)
            .set(order)
            .addOnFailureListener(e-> {
                    Toast.makeText(this, "Gagal tambah data pesanan", Toast.LENGTH_SHORT).show();
        });

        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
        this.finish();
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;

        locationRequest = new LocationRequest();
        locationRequest.setInterval(360000);
        locationRequest.setFastestInterval(360000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                client.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper());
                this.googleMap.setMyLocationEnabled(true);
            } else {
                checkLocationPermission();
            }
        } else {
            client.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper());
            this.googleMap.setMyLocationEnabled(true);
        }
        this.googleMap.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        latlngTerpilih = latLng;
        markerTerpilih.setPosition(latlngTerpilih);
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latlngTerpilih));

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List <Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latlngTerpilih.latitude, latlngTerpilih.longitude, 1);
            if (addresses != null) {
                Address data_tujuan = addresses.get(0);
                StringBuilder jalan = new StringBuilder();

                for (int i = 0; i <= data_tujuan.getMaxAddressLineIndex(); i++) {
                    jalan.append(data_tujuan.getAddressLine(i)).append("\n");
                }

                var_lokasiTerpilih.setText(jalan.toString());
                var_jalan = jalan.toString();
            } else {
                Toast.makeText(this, "Tidak dapat menemukan alamat!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Tidak dapat menemukan alamat!", Toast.LENGTH_SHORT).show();
        }
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                    new AlertDialog.Builder(this)
                        .setTitle("Membutuhkan Akses Lokasi")
                        .setMessage("Aplikasi ini membutuhkan akses lokasi untuk menentukan lokasi Anda")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(MapsGroomingActivity.this,
                                        new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        }).create().show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @SuppressLint({
            "MissingSuperCall",
            "MissingPermission"
    })
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION:
            {
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        client.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper());
                        googleMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(this, "Permintaan akses lokasi ditolak", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            List <Location> locationList = locationResult.getLocations();
            if (locationList.size() > 0) {
                Location location = locationList.get(locationList.size() - 1);
                lastLocation = location;

                saveLat = location.getLatitude();
                saveLong = location.getLongitude();
                LatLng tujuan = new LatLng(saveLat, saveLong);

                latlngTerpilih = tujuan;
                markerTerpilih = googleMap.addMarker(new MarkerOptions().position(latlngTerpilih));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlngTerpilih, 15.0f));
            }
        }
    };


}