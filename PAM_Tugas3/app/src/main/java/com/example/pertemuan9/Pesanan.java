package com.example.pertemuan9;

public class Pesanan {

    String nama, alamat, tanggal, id;

    public Pesanan(){}

    public Pesanan(String nama, String alamat, String tanggal, String id) {
        this.nama = nama;
        this.alamat = alamat;
        this.tanggal = tanggal;
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
