package com.example.icat.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.icat.database.entitas.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE pesanan_id=:pesanan_id")
    User get(int pesanan_id);

    @Query("INSERT INTO user (jenis_layanan,jenis_kucing, isAntarJemput) VALUES(:jenis_layanan,:jenis_kucing,:isAntarJemput)")
    void insertAll(String jenis_layanan, String jenis_kucing, String isAntarJemput);

    @Delete
    void delete(User user);
}
