package com.example.seminardam_teme.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.seminardam_teme.MainActivity;
import com.google.firebase.database.Exclude;


@Entity (tableName = "utilizatori")
public class User implements Serializable {

    @PrimaryKey (autoGenerate = true)
    private int id;
    @ColumnInfo(name="nume_utilizator")
    private String name;
    @ColumnInfo(name="email_utilizator")
    private String email;
    @ColumnInfo(name="telefon_utilizator")
    private String phone;
    @ColumnInfo(name="hash_parola")
    private String pwdHash;
    @ColumnInfo(name="data_nasterii")
    @TypeConverters(DateConverter.class)
    private Date dateOfBirth;
    @ColumnInfo(name = "locale")
    private String countryOrRegion;
    @ColumnInfo(name = "is_online")
    private boolean online;
    public User(int id, String name, String phone, String email, String pwd_hash, Date dob, String country_or_region) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.pwdHash = pwd_hash;
        this.dateOfBirth = dob;
        this.countryOrRegion = country_or_region;
        this.online = false;
    }

    public User() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwdHash() {
        return pwdHash;
    }

    public void setPwdHash(String pwdHash) {
        this.pwdHash = pwdHash;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCountryOrRegion() {
        return countryOrRegion;
    }

    public void setCountryOrRegion(String countryOrRegion) {
        this.countryOrRegion = countryOrRegion;
    }

    @Exclude
    public long getAge() {
        long today = new Date().getTime();
        return (long)(Math.floor(today - dateOfBirth.getTime()) * MainActivity.MILLIS_YEAR);
    }

    public void logOut() {
        online = false;
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", pwd_hash=" + pwdHash +
                ", dob=" + dateOfBirth +
                ", country_or_region=" + countryOrRegion +
                '}';
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
