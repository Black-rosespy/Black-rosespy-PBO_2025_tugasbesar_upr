package com.aplikasi.kasir.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Transaksi {
    private String idTransaksi;
    private LocalDate tanggalTerima;
    private LocalDate tanggalSelesai;
    private double jumlahPembayaran;
    private boolean statusPembayaran;

    private Pelanggan pelanggan;                 // relasi ke pelanggan
    private List<DetailLaundry> detailLaundry;   // relasi ke detail layanan

    // Constructor
    public Transaksi(String idTransaksi, LocalDate tanggalTerima, LocalDate tanggalSelesai, Pelanggan pelanggan) {
        this.idTransaksi = idTransaksi;
        this.tanggalTerima = tanggalTerima;
        this.tanggalSelesai = tanggalSelesai;
        this.pelanggan = pelanggan;
        this.detailLaundry = new ArrayList<>();
        this.jumlahPembayaran = 0.0;
        this.statusPembayaran = false;
    }

    // Getter dan Setter
    public String getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(String idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public LocalDate getTanggalTerima() {
        return tanggalTerima;
    }

    public void setTanggalTerima(LocalDate tanggalTerima) {
        this.tanggalTerima = tanggalTerima;
    }

    public LocalDate getTanggalSelesai() {
        return tanggalSelesai;
    }

    public void setTanggalSelesai(LocalDate tanggalSelesai) {
        this.tanggalSelesai = tanggalSelesai;
    }

    public double getJumlahPembayaran() {
        return jumlahPembayaran;
    }

    public boolean isStatusPembayaran() {
        return statusPembayaran;
    }

    public Pelanggan getPelanggan() {
        return pelanggan;
    }

    public void setPelanggan(Pelanggan pelanggan) {
        this.pelanggan = pelanggan;
    }

    public List<DetailLaundry> getDetailLaundry() {
        return detailLaundry;
    }

    // Method untuk menambahkan detail layanan laundry
    public void tambahDetailLaundry(DetailLaundry detail) {
        detailLaundry.add(detail);
    }

    // Method untuk menghitung total biaya
    public void hitungTotal() {
        double total = 0.0;
        for (DetailLaundry d : detailLaundry) {
            total += d.hitungSubtotal();
        }
        this.jumlahPembayaran = total;
    }

    // Method untuk mengubah status pembayaran
    public void ubahStatus(boolean status) {
        this.statusPembayaran = status;
    }

    // Method untuk menampilkan ringkasan transaksi
    public void tampilkanRingkasan() {
        System.out.println("=== Ringkasan Transaksi ===");
        System.out.println("ID Transaksi   : " + idTransaksi);
        System.out.println("Pelanggan      : " + pelanggan.getNama());
        System.out.println("Tanggal Terima : " + tanggalTerima);
        System.out.println("Tanggal Selesai: " + tanggalSelesai);
        System.out.println("Total Bayar    : Rp " + jumlahPembayaran);
        System.out.println("Status Bayar   : " + (statusPembayaran ? "Lunas" : "Belum Lunas"));
    }
}