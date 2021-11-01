package com.example.seminardam_teme;

import android.graphics.drawable.Drawable;
import android.media.Image;

public class Produs {
    private Drawable imagine;
    private String denumire;
    private float pret;
    private String descriere;

    public Produs(String nume, float pret, String descriere, Drawable imagine) {
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

    public Drawable getImagine() {
        return imagine;
    }

    public void setImagine(Drawable imagine) {
        this.imagine = imagine;
    }
}