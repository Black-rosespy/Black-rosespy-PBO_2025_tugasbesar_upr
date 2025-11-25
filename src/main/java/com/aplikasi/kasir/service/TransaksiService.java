package com.aplikasi.kasir.service;

import com.aplikasi.kasir.dao.TransaksiDAO;
import com.aplikasi.kasir.model.Transaksi;

import java.sql.Connection;
import java.util.List;

public class TransaksiService {
    private TransaksiDAO transaksiDAO;

    public TransaksiService(Connection conn) {
        this.transaksiDAO = new TransaksiDAO(conn);
    }

    // Tambah transaksi dengan validasi dan perhitungan total
    public void tambahTransaksi(Transaksi transaksi) throws Exception {
        if (transaksi.getPelanggan() == null) {
            throw new Exception("Transaksi harus memiliki pelanggan!");
        }
        if (transaksi.getDetailLaundry().isEmpty()) {
            throw new Exception("Transaksi harus memiliki detail laundry!");
        }

        // Hitung total pembayaran sebelum disimpan
        transaksi.hitungTotal();

        transaksiDAO.tambahTransaksi(transaksi);
    }

    // Ambil semua transaksi
    public List<Transaksi> getAllTransaksi() throws Exception {
        return transaksiDAO.getAllTransaksi();
    }

    // Ambil transaksi berdasarkan ID
    public Transaksi getTransaksiById(String idTransaksi) throws Exception {
        if (idTransaksi == null || idTransaksi.isEmpty()) {
            throw new Exception("ID Transaksi tidak boleh kosong!");
        }
        return transaksiDAO.getTransaksiById(idTransaksi);
    }

    // Update transaksi (misalnya status pembayaran)
    public void updateTransaksi(Transaksi transaksi) throws Exception {
        if (transaksi.getIdTransaksi() == null || transaksi.getIdTransaksi().isEmpty()) {
            throw new Exception("ID Transaksi tidak boleh kosong!");
        }
        transaksiDAO.updateTransaksi(transaksi);
    }

    // Hapus transaksi
    public void hapusTransaksi(String idTransaksi) throws Exception {
        if (idTransaksi == null || idTransaksi.isEmpty()) {
            throw new Exception("ID Transaksi tidak boleh kosong!");
        }
        transaksiDAO.deleteTransaksi(idTransaksi);
    }
}