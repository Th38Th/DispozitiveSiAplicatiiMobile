package com.example.seminardam_teme.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User... user);

    @Update
    void update(User... users);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM utilizatori")
    List<User> getUsers();

    @Query("SELECT * FROM utilizatori WHERE (nume_utilizator==:name " +
            "or email_utilizator==:email " +
            "or telefon_utilizator==:phone) " +
            "and hash_parola==:pwdhash")
    User matchCredentials(String name, String email, String phone, String pwdhash);

    @Query("SELECT * FROM utilizatori WHERE " +
            "email_utilizator==:phoneOrEmail " +
            "or telefon_utilizator==:phoneOrEmail ")
    User getIdenticalUser(String phoneOrEmail);

}
