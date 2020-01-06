package com.example.fufufu.model;

import java.io.Serializable;

public class Pinjam implements Serializable {



    private String barang;
    private String nim;
    private String nama;
    private String kelas;
    private String nohp;
    private String tanggal;


    private String key;

public Pinjam()
    {

    }

    public Pinjam(String barang, String nim, String nama, String kelas, String nohp,String tanggal) {
        this.barang = barang;
        this.nim = nim;
        this.nama = nama;
        this.kelas = kelas;
        this.nohp = nohp;
        this.tanggal=tanggal;
    }





    public String getBarang() {
        return barang;
    }

    public void setBarang(String barang) {
        this.barang =barang;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    @Override
    public String toString() {
        return "Pinjam{" +
                "barang='" + barang + '\'' +
                ", nim='" + nim + '\'' +
                ", nama='" + nama + '\'' +
                ", kelas='" + kelas + '\'' +
                ", nohp='" + nohp + '\'' +
                ", tanggal='" + tanggal + '\'' +
                '}';
    }
}
