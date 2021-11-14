package com.example.seminardam_teme;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;

public class Produs {
    private Bitmap imagine;
    private String denumire;
    private float pret;
    private String descriere;

    public Produs(String nume, float pret, String descriere, Bitmap imagine) {
        this.denumire = nume;
        this.pret = pret;
        this.descriere = descriere;
        this.imagine = imagine;
    }

    public Produs(String nume, float pret, String descriere) {
        this(nume, pret, descriere, null);
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
                "imagine='" + imagine + '\'' +
                ", nume='" + denumire + '\'' +
                ", pret=" + pret +
                ", descriere='" + descriere + '\'' +
                '}';
    }

    public Bitmap getImagine() {
        return imagine;
    }

    public void setImagine(Bitmap imagine) {
        this.imagine = imagine;
    }
}