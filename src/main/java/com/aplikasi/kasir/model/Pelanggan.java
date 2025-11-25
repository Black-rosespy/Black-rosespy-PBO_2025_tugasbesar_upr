package com.aplikasi.kasir.model;

public class Pelanggan {
    private String idPelanggan;
    private String nama;
    private String alamat;
    private String noTelepon;

    // Constructor
    public Pelanggan(String idPelanggan, String nama, String alamat, String noTelepon) {
        this.idPelanggan = idPelanggan;
        this.nama = nama;
        this.alamat = alamat;
        this.noTelepon = noTelepon;
    }

    // Getter dan Setter
    public String getIdPelanggan() {
        return idPelanggan;
    }

    public void setIdPelanggan(String idPelanggan) {
        this.idPelanggan = idPelanggan;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }

    // Method tambahan
    public void tampilkanInfo() {
        System.out.println("ID Pelanggan   : " + idPelanggan);
        System.out.println("Nama           : " + nama);
        System.out.println("Alamat         : " + alamat);
        System.out.println("No. Telepon    : " + noTelepon);
    }
}