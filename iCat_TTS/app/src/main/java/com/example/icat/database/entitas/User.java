package com.example.icat.database.entitas;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey
    public int pesanan_id;

    public String jenis_layanan;

    public String jenis_kucing;

    public String isAntarJemput;
}
