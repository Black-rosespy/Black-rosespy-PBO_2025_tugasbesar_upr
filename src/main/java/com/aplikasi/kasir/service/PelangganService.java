package com.aplikasi.kasir.service;

import com.aplikasi.kasir.dao.PelangganDAO;
import com.aplikasi.kasir.model.Pelanggan;

import java.sql.Connection;
import java.util.List;

public class PelangganService {
    private PelangganDAO pelangganDAO;

    public PelangganService(Connection conn) {
        this.pelangganDAO = new PelangganDAO(conn);
    }

    // Tambah pelanggan baru dengan validasi
    public void tambahPelanggan(Pelanggan pelanggan) throws Exception {
        if (pelanggan.getNama() == null || pelanggan.getNama().isEmpty()) {
            throw new Exception("Nama pelanggan tidak boleh kosong!");
        }
        if (pelanggan.getNoTelepon() == null || pelanggan.getNoTelepon().isEmpty()) {
            throw new Exception("Nomor telepon wajib diisi!");
        }
        pelangganDAO.tambahPelanggan(pelanggan);
    }

    // Ambil semua pelanggan
    public List<Pelanggan> getAllPelanggan() throws Exception {
        return pelangganDAO.getAllPelanggan();
    }

    // Ambil pelanggan berdasarkan ID
    public Pelanggan getPelangganById(String idPelanggan) throws Exception {
        return pelangganDAO.getPelangganById(idPelanggan);
    }

    // Update data pelanggan
    public void updatePelanggan(Pelanggan pelanggan) throws Exception {
        if (pelanggan.getIdPelanggan() == null || pelanggan.getIdPelanggan().isEmpty()) {
            throw new Exception("ID pelanggan tidak boleh kosong!");
        }
        pelangganDAO.updatePelanggan(pelanggan);
    }

    // Hapus pelanggan
    public void hapusPelanggan(String idPelanggan) throws Exception {
        pelangganDAO.deletePelanggan(idPelanggan);
    }
}