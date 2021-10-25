package com.example.seminardam_teme;

import android.app.Person;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class User implements Serializable {
    private String name;
    private String email;
    private String phone;
    private byte[] pwdHash;
    private Date dateOfBirth;
    private Locale countryOrRegion;
    private boolean online;
    public User(String name, String phone, String email, byte[] pwd_hash, Date dob, Locale country_or_region) {
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

    public byte[] getPwdHash() {
        return pwdHash;
    }

    public void setPwdHash(byte[] pwdHash) {
        this.pwdHash = pwdHash;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Locale getCountryOrRegion() {
        return countryOrRegion;
    }

    public void setCountryOrRegion(Locale countryOrRegion) {
        this.countryOrRegion = countryOrRegion;
    }

    public long getAge() {
        long today = new Date().getTime();
        return (long)(Math.floor(today - dateOfBirth.getTime()) * MainActivity.MILLIS_YEAR);
    }

    public boolean logIn(String email, String phone, byte[] pwdHash){
        if ((email != null && email.equals(this.email))
                || (phone != null && phone.equals(this.phone))
                && Arrays.equals(pwdHash, this.pwdHash)) {
            online = true;
            return true;
        }
        return false;
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
                ", pwd_hash=" + Arrays.toString(pwdHash) +
                ", dob=" + dateOfBirth +
                ", country_or_region=" + countryOrRegion +
                '}';
    }
}
