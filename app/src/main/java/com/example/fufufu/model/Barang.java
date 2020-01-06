package com.example.fufufu.model;

import java.io.Serializable;

public class Barang implements Serializable {

    private String sarana;
    private String jumlah;
    private String kondisi;
    private String lokasi;


    private String key;

    public Barang(String sarana, String jumlah, String kondisi, String lokasi) {
        this.sarana = sarana;
        this.jumlah = jumlah;
        this.kondisi = kondisi;
        this.lokasi = lokasi;

    }

    public Barang() {

    }


    public String getSarana() {
        return sarana;
    }

    public void setSarana(String sarana) {
        this.sarana = sarana;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getKondisi() {
        return kondisi;
    }

    public void setKondisi(String kondisi) {
        this.kondisi = kondisi;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "Barang{" +
                "sarana='" + sarana + '\'' +
                ", jumlah='" + jumlah + '\'' +
                ", kondisi='" + kondisi + '\'' +
                ", lokasi='" + lokasi + '\'' +
                ", key='" + key + '\'' +
                '}';
    }


}
