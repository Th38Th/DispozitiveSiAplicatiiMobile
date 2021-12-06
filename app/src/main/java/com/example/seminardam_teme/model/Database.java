package com.example.seminardam_teme.model;

import android.content.Context;

import androidx.room.Room;

public class Database {

    private static Database database;
    private DatabaseAmazon databaseAmazon;

    private Database(Context context) {
        databaseAmazon = Room.databaseBuilder(context,
                DatabaseAmazon.class, "amazon-database").build();
    }

    public static Database getInstance(Context context){
        if (database == null){
            database = new Database(context);
        }
        return database;
    }

    public DatabaseAmazon getDataBase() {
        return databaseAmazon;
    }
}
