package com.example.pertemuan9;

public class Pesanan {

    String name, date, awal, tujuan, id;

    public Pesanan(){}

    public Pesanan(String name, String date, String awal, String tujuan, String id) {
        this.name = name;
        this.date = date;
        this.awal = awal;
        this.tujuan = tujuan;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAwal() {
        return awal;
    }

    public void setAwal(String awal) {
        this.awal = awal;
    }

    public String getTujuan() {
        return tujuan;
    }

    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
