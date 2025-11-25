package com.aplikasi.kasir.dao;

import com.aplikasi.kasir.model.Pegawai;
import com.aplikasi.kasir.model.PegawaiAdmin;
import com.aplikasi.kasir.model.PegawaiKasir;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PegawaiDAO {
    private Connection conn;

    // Constructor menerima koneksi database
    public PegawaiDAO(Connection conn) {
        this.conn = conn;
    }

    // CREATE: Tambah pegawai baru (admin/kasir)
    public void tambahPegawai(Pegawai pegawai, String role) throws SQLException {
        String sql = "INSERT INTO pegawai (id_pegawai, nama, username, password, role) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pegawai.getIdPegawai());
            stmt.setString(2, pegawai.getNama());
            stmt.setString(3, pegawai.getUsername());
            stmt.setString(4, pegawai.getPassword());
            stmt.setString(5, role); // "ADMIN" atau "KASIR"
            stmt.executeUpdate();
        }
    }

    // READ: Ambil semua pegawai
    public List<Pegawai> getAllPegawai() throws SQLException {
        List<Pegawai> list = new ArrayList<>();
        String sql = "SELECT * FROM pegawai";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String role = rs.getString("role");
                Pegawai p;
                if ("ADMIN".equalsIgnoreCase(role)) {
                    p = new PegawaiAdmin(
                            rs.getString("id_pegawai"),
                            rs.getString("nama"),
                            rs.getString("username"),
                            rs.getString("password")
                    );
                } else {
                    p = new PegawaiKasir(
                            rs.getString("id_pegawai"),
                            rs.getString("nama"),
                            rs.getString("username"),
                            rs.getString("password")
                    );
                }
                list.add(p);
            }
        }
        return list;
    }

    // READ: Ambil pegawai berdasarkan username
    public Pegawai getPegawaiByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM pegawai WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String role = rs.getString("role");
                    if ("ADMIN".equalsIgnoreCase(role)) {
                        return new PegawaiAdmin(
                                rs.getString("id_pegawai"),
                                rs.getString("nama"),
                                rs.getString("username"),
                                rs.getString("password")
                        );
                    } else {
                        return new PegawaiKasir(
                                rs.getString("id_pegawai"),
                                rs.getString("nama"),
                                rs.getString("username"),
                                rs.getString("password")
                        );
                    }
                }
            }
        }
        return null;
    }

    // READ: Ambil semua kasir
    public List<PegawaiKasir> getAllKasir() throws SQLException {
        List<PegawaiKasir> list = new ArrayList<>();
        String sql = "SELECT * FROM pegawai WHERE role = 'KASIR'";
        try (Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                PegawaiKasir kasir = new PegawaiKasir(
                        rs.getString("id_pegawai"),
                        rs.getString("nama"),
                        rs.getString("username"),
                        rs.getString("password")
                );
                list.add(kasir);
            }
        }
        return list;
    }

    // UPDATE: Perbarui data pegawai
    public void updatePegawai(Pegawai pegawai) throws SQLException {
        String sql = "UPDATE pegawai SET nama = ?, username = ?, password = ? WHERE id_pegawai = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pegawai.getNama());
            stmt.setString(2, pegawai.getUsername());
            stmt.setString(3, pegawai.getPassword());
            stmt.setString(4, pegawai.getIdPegawai());
            stmt.executeUpdate();
        }
    }

    // Generate ID Pegawai otomatis
    public String generatePegawaiId(String rolePrefix) throws SQLException {
        String sql = "SELECT id_pegawai FROM pegawai ORDER BY id_pegawai DESC LIMIT 1";
        try (Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                String lastId = rs.getString("id_pegawai"); // misalnya PGW005
                int num = Integer.parseInt(lastId.replaceAll("\\D+", "")); // ambil angka
                return rolePrefix + String.format("%03d", num + 1);
            }
        }
        return rolePrefix + "001"; // default kalau belum ada data
    }

    // DELETE: Hapus pegawai berdasarkan ID
    public void deletePegawai(String idPegawai) throws SQLException {
        String sql = "DELETE FROM pegawai WHERE id_pegawai = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idPegawai);
            stmt.executeUpdate();
        }
    }
}