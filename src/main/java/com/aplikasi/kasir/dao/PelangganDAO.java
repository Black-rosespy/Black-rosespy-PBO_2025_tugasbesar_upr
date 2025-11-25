package com.aplikasi.kasir.dao;

import com.aplikasi.kasir.model.Pelanggan;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PelangganDAO {
    private Connection conn;

    // Constructor menerima koneksi database
    public PelangganDAO(Connection conn) {
        this.conn = conn;
    }

    // CREATE: Tambah pelanggan baru
    public void tambahPelanggan(Pelanggan pelanggan) throws SQLException {
        String sql = "INSERT INTO pelanggan (id_pelanggan, nama, alamat, no_telepon) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pelanggan.getIdPelanggan());
            stmt.setString(2, pelanggan.getNama());
            stmt.setString(3, pelanggan.getAlamat());
            stmt.setString(4, pelanggan.getNoTelepon());
            stmt.executeUpdate();
        }
    }

    public String generatePelangganId() throws SQLException {
        String sql = "SELECT id_pelanggan FROM pelanggan ORDER BY id_pelanggan DESC LIMIT 1";
        try (Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                String lastId = rs.getString("id_pelanggan"); // PLG005
                int num = Integer.parseInt(lastId.replaceAll("\\D+", ""));
                return "PLG" + String.format("%03d", num + 1);
            }
        }
        return "PLG001";
    }

    // READ: Ambil semua data pelanggan
    public List<Pelanggan> getAllPelanggan() throws SQLException {
        List<Pelanggan> list = new ArrayList<>();
        String sql = "SELECT * FROM pelanggan";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Pelanggan p = new Pelanggan(
                        rs.getString("id_pelanggan"),
                        rs.getString("nama"),
                        rs.getString("alamat"),
                        rs.getString("no_telepon")
                );
                list.add(p);
            }
        }
        return list;
    }

    // READ: Ambil pelanggan berdasarkan ID
    public Pelanggan getPelangganById(String id) throws SQLException {
        String sql = "SELECT * FROM pelanggan WHERE id_pelanggan = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Pelanggan(
                            rs.getString("id_pelanggan"),
                            rs.getString("nama"),
                            rs.getString("alamat"),
                            rs.getString("no_telepon")
                    );
                }
            }
        }
        return null;
    }

    // UPDATE: Perbarui data pelanggan
    public void updatePelanggan(Pelanggan pelanggan) throws SQLException {
        String sql = "UPDATE pelanggan SET nama = ?, alamat = ?, no_telepon = ? WHERE id_pelanggan = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pelanggan.getNama());
            stmt.setString(2, pelanggan.getAlamat());
            stmt.setString(3, pelanggan.getNoTelepon());
            stmt.setString(4, pelanggan.getIdPelanggan());
            stmt.executeUpdate();
        }
    }

    // DELETE: Hapus pelanggan berdasarkan ID
    public void deletePelanggan(String id) throws SQLException {
        String sql = "DELETE FROM pelanggan WHERE id_pelanggan = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        }
    }
}