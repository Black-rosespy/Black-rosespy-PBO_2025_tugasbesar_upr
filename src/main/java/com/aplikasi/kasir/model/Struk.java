package com.aplikasi.kasir.model;

import java.time.LocalDate;

public class Struk {
    private String idStruk;
    private LocalDate tanggalCetak;
    private Transaksi transaksi;

    // Constructor
    public Struk(String idStruk, Transaksi transaksi) {
        this.idStruk = idStruk;
        this.transaksi = transaksi;
        this.tanggalCetak = LocalDate.now(); // otomatis tanggal cetak saat struk dibuat
    }

    // Getter dan Setter
    public String getIdStruk() {
        return idStruk;
    }

    public void setIdStruk(String idStruk) {
        this.idStruk = idStruk;
    }

    public LocalDate getTanggalCetak() {
        return tanggalCetak;
    }

    public void setTanggalCetak(LocalDate tanggalCetak) {
        this.tanggalCetak = tanggalCetak;
    }

    public Transaksi getTransaksi() {
        return transaksi;
    }

    public void setTransaksi(Transaksi transaksi) {
        this.transaksi = transaksi;
    }

    // Method untuk membuat struk pembayaran
    public void buatStruk() {
        System.out.println("======================================");
        System.out.println("            STRUK PEMBAYARAN          ");
        System.out.println("======================================");
        System.out.println("ID Struk       : " + idStruk);
        System.out.println("Tanggal Cetak  : " + tanggalCetak);
        System.out.println("ID Transaksi   : " + transaksi.getIdTransaksi());
        System.out.println("Nama Pelanggan : " + transaksi.getPelanggan().getNama());
        System.out.println("--------------------------------------");
        System.out.println("Detail Laundry:");
        for (DetailLaundry d : transaksi.getDetailLaundry()) {
            System.out.println("- " + d.getJenisLayanan() + " | Berat: " + d.getBeratKg() + " Kg | Tarif/Kg: Rp " 
                               + d.getTarifPerKg() + " | Subtotal: Rp " + d.getSubtotal());
        }
        System.out.println("--------------------------------------");
        System.out.println("Total Bayar    : Rp " + transaksi.getJumlahPembayaran());
        System.out.println("Status Bayar   : " + (transaksi.isStatusPembayaran() ? "Lunas" : "Belum Lunas"));
        System.out.println("======================================");
        System.out.println("   Terima kasih telah menggunakan     ");
        System.out.println("        layanan Laundry kami!         ");
        System.out.println("======================================");
    }
}