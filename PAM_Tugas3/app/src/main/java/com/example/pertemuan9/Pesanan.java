package com.example.pertemuan9;

public class Pesanan {

    String name, createdDate, address, id;

    public Pesanan(){}

    public Pesanan(String name, String createdDate, String address, String id) {
        this.name = name;
        this.createdDate = createdDate;
        this.address = address;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
