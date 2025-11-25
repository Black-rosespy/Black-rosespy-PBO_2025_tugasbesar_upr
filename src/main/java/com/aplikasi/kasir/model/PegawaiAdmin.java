package com.aplikasi.kasir.model;

import java.util.ArrayList;
import java.util.List;

public class PegawaiAdmin extends Pegawai {
    // Daftar kasir yang dikelola oleh admin
    private List<PegawaiKasir> daftarKasir;

    // Constructor
    public PegawaiAdmin(String idPegawai, String nama, String username, String password) {
        super(idPegawai, nama, username, password);
        this.daftarKasir = new ArrayList<>();
    }

    // Method untuk menambah kasir baru
    public void tambahKasir(PegawaiKasir kasirBaru) {
        daftarKasir.add(kasirBaru);
        System.out.println("Kasir " + kasirBaru.getNama() + " berhasil ditambahkan.");
    }

    // Method untuk menghapus kasir berdasarkan ID
    public void hapusKasir(String idKasir) {
        boolean removed = daftarKasir.removeIf(k -> k.getIdPegawai().equals(idKasir));
        if (removed) {
            System.out.println("Kasir dengan ID " + idKasir + " berhasil dihapus.");
        } else {
            System.out.println("Kasir dengan ID " + idKasir + " tidak ditemukan.");
        }
    }

    // Override tampilkanMenu khusus admin
    @Override
    public void tampilkanMenu() {
        System.out.println("=== Menu Admin ===");
        System.out.println("1. Tambah Kasir");
        System.out.println("2. Hapus Kasir");
        System.out.println("3. Keluar");
    }

    // Getter daftar kasir
    public List<PegawaiKasir> getDaftarKasir() {
        return daftarKasir;
    }
}