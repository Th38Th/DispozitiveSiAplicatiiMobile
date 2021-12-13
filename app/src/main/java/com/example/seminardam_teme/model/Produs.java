package com.example.seminardam_teme.model;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "produse")
public class Produs {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name="url_imagine")
    private String urlImagine;
    @ColumnInfo(name="denumire_produs")
    private String denumire;
    @ColumnInfo(name="pret_produs")
    private float pret;
    @ColumnInfo(name="descriere_produs")
    private String descriere;

    public Produs(int id, String denumire, float pret, String descriere, String urlImagine) {
        this.id = id;
        this.denumire = denumire;
        this.pret = pret;
        this.descriere = descriere;
        this.urlImagine = urlImagine;
    }

    @Ignore
    public Produs(String denumire, float pret, String descriere, String urlImagine) {
        this(0, denumire, pret, descriere, urlImagine);
    }

    @Ignore
    public Produs(String denumire, float pret, String descriere) {
        this(0, denumire, pret, descriere, null);
    }

    public Produs(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String nume) {
        this.denumire = nume;
    }

    public float getPret() {
        return pret;
    }

    public void setPret(float pret) {
        this.pret = pret;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    @Override
    public String toString() {
        return "Produs{" +
                "urlImagine='" + urlImagine + '\'' +
                ", nume='" + denumire + '\'' +
                ", pret=" + pret +
                ", descriere='" + descriere + '\'' +
                '}';
    }

    public String getUrlImagine() {
        return urlImagine;
    }

    public void setUrlImagine(String urlImagine) {
        this.urlImagine = urlImagine;
    }

}