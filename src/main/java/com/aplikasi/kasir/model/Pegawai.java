package com.aplikasi.kasir.model;

public class Pegawai {
    private String idPegawai;
    private String nama;
    private String username;
    private String password;

    // Constructor
    public Pegawai(String idPegawai, String nama, String username, String password) {
        this.idPegawai = idPegawai;
        this.nama = nama;
        this.username = username;
        this.password = password;
    }

    // Getter dan Setter
    public String getIdPegawai() {
        return idPegawai;
    }

    public void setIdPegawai(String idPegawai) {
        this.idPegawai = idPegawai;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Method login sederhana
    public boolean login(String inputUsername, String inputPassword) {
        return this.username.equals(inputUsername) && this.password.equals(inputPassword);
    }

    // Method tampilkan menu (akan dioverride oleh subclass)
    public void tampilkanMenu() {
        System.out.println("Menu Pegawai umum.");
        System.out.println("1. Login");
        System.out.println("2. Keluar");
    }
}