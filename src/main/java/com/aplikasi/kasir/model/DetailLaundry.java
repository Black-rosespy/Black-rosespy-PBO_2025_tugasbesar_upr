package com.aplikasi.kasir.model;

public class DetailLaundry {
    private String idDetail;
    private String jenisLayanan;
    private double beratKg;
    private double tarifPerKg;
    private double subtotal;

    public DetailLaundry(String idDetail, String jenisLayanan, double beratKg, double tarifPerKg) {
        this.idDetail = idDetail;
        this.jenisLayanan = jenisLayanan;
        this.beratKg = beratKg;
        this.tarifPerKg = tarifPerKg;
        this.subtotal = hitungSubtotal();
    }

    public double hitungSubtotal() {
        return this.beratKg * this.tarifPerKg;
    }

    // Getters and Setters
    public String getIdDetail() {
        return idDetail;
    }

    public void setIdDetail(String idDetail) {
        this.idDetail = idDetail;
    }

    public String getJenisLayanan() {
        return jenisLayanan;
    }

    public void setJenisLayanan(String jenisLayanan) {
        this.jenisLayanan = jenisLayanan;
    }

    public double getBeratKg() {
        return beratKg;
    }

    public void setBeratKg(double beratKg) {
        this.beratKg = beratKg;
        // Recalculate subtotal if weight changes
        this.subtotal = hitungSubtotal();
    }
    

    public double getTarifPerKg() {
        return tarifPerKg;
    }

    public void setTarifPerKg(double tarifPerKg) {
        this.tarifPerKg = tarifPerKg;
        // Recalculate subtotal if rate changes
        this.subtotal = hitungSubtotal();
    }
    

    public double getSubtotal() {
        return subtotal;
    }

    // No public setter for subtotal as it is a calculated value
}
