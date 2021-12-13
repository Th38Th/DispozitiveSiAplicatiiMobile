package com.example.seminardam_teme.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProdusDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Produs... produses);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Produs> produse);

    @Update
    void update(Produs... produse);

    @Delete
    void delete(Produs produs);

    @Query("SELECT * FROM produse")
    List<Produs> getProduse();

}
