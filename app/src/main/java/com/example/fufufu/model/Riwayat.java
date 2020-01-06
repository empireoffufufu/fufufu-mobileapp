package com.example.fufufu.model;

import java.io.Serializable;

public class Riwayat implements Serializable {
    private String barang;
    private String nim;
    private String nama;
    private String tanggal;

    private String key;

    public Riwayat()
    {

    }

    public Riwayat(String barang, String nim, String nama, String tanggal) {
        this.barang = barang;
        this.nim = nim;
        this.nama = nama;
        this.tanggal = tanggal;
    }

    public String getBarang() {
        return barang;
    }

    public void setBarang(String barang) {
        this.barang = barang;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "Riwayat{" +
                "barang='" + barang + '\'' +
                ", nim='" + nim + '\'' +
                ", nama='" + nama + '\'' +
                ", tanggal='" + tanggal + '\'' +
                '}';
    }
}
