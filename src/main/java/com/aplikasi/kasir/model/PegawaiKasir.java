package com.aplikasi.kasir.model;

import java.util.ArrayList;
import java.util.List;

public class PegawaiKasir extends Pegawai {
    // Daftar transaksi yang ditangani kasir
    private List<Transaksi> daftarTransaksi;

    // Constructor
    public PegawaiKasir(String idPegawai, String nama, String username, String password) {
        super(idPegawai, nama, username, password);
        this.daftarTransaksi = new ArrayList<>();
    }

    // Method untuk input transaksi baru
    public void inputTransaksi(Transaksi transaksiBaru) {
        daftarTransaksi.add(transaksiBaru);
        System.out.println("Transaksi baru berhasil ditambahkan untuk pelanggan: " 
                           + transaksiBaru.getPelanggan().getNama());
    }

    // Method untuk mencetak struk transaksi
    public void cetakStruk(Transaksi transaksi) {
        Struk struk = new Struk("STRK-" + transaksi.getIdTransaksi(), transaksi);
        struk.buatStruk();
    }

    // Override tampilkanMenu khusus kasir
    @Override
    public void tampilkanMenu() {
        System.out.println("=== Menu Kasir ===");
        System.out.println("1. Input Data Pelanggan");
        System.out.println("2. Input Transaksi");
        System.out.println("3. Lihat Data Transaksi");
        System.out.println("4. Cetak Struk");
        System.out.println("5. Keluar");
    }

    // Getter daftar transaksi
    public List<Transaksi> getDaftarTransaksi() {
        return daftarTransaksi;
    }
}