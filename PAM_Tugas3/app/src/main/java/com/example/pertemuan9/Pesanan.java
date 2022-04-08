package com.example.pertemuan9;

public class Pesanan {

    String nama, alamat, tanggal;

    public Pesanan(){}

    public Pesanan(String nama, String alamat, String tanggal) {
        this.nama = nama;
        this.alamat = alamat;
        this.tanggal = tanggal;
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
}
