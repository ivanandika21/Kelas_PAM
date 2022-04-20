package com.example.icatadmin;

public class Pesanan {

    String id, atasnama, jenisgrooming, jeniskucing, jenislayanan, tanggal, tujuan, status;

    public Pesanan(){}

    public Pesanan(String id, String atasnama, String jenisgrooming, String jeniskucing, String jenislayanan, String tanggal, String tujuan, String status) {
        this.id = id;
        this.atasnama = atasnama;
        this.jenisgrooming = jenisgrooming;
        this.jeniskucing = jeniskucing;
        this.jenislayanan = jenislayanan;
        this.tanggal = tanggal;
        this.tujuan = tujuan;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAtasnama() {
        return atasnama;
    }

    public void setAtasnama(String atasnama) {
        this.atasnama = atasnama;
    }

    public String getJenisgrooming() {
        return jenisgrooming;
    }

    public void setJenisgrooming(String jenisgrooming) {
        this.jenisgrooming = jenisgrooming;
    }

    public String getJeniskucing() {
        return jeniskucing;
    }

    public void setJeniskucing(String jeniskucing) {
        this.jeniskucing = jeniskucing;
    }

    public String getJenislayanan() {
        return jenislayanan;
    }

    public void setJenislayanan(String jenislayanan) {
        this.jenislayanan = jenislayanan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getTujuan() {
        return tujuan;
    }

    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
