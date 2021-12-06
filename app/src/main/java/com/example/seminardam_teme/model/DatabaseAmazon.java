package com.example.seminardam_teme.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Produs.class, User.class},version = 1,exportSchema = false)
public abstract class DatabaseAmazon extends RoomDatabase {
    public abstract UserDAO userDAO();
    public abstract ProdusDAO produsDAO();
}
