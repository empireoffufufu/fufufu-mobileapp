package com.example.fufufu.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class Profil implements Serializable{

    private String nim;
    private String nama;
    private String kelas;
    private String alamat;
    private String noHP;

    private String email;
    private String password;
    private String grup;

    private String key;

    public Profil(){

    }

    public Profil(String nim, String nama, String kelas, String alamat, String noHP, String email, String password, String grup)
    {
        this.nim = nim;
        this.nama = nama;
        this.kelas=kelas;
        this.alamat=alamat;
        this.noHP=noHP;
        this.email=email;
        this.grup=grup;
        this.password=password;

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


       public String getalamat() {
        return alamat;
    }

    public void setalamat(String alamat) {
        this.alamat = alamat;
    }


    public String getnoHP() {
        return noHP;
    }

    public void setnoHP(String noHP) {
        this.noHP = noHP;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGrup() {
        return grup;
    }

    public void setGrup(String grup) {
        this.grup = grup;
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return " "+nama+"\n" +
                " "+nim+"\n" +
                " "+kelas+"\n" +
                " "+alamat+"\n" +
                " "+email+"\n" +
                " "+password+"\n" +
                " "+grup+"\n" +
                " "+noHP;
    }


}