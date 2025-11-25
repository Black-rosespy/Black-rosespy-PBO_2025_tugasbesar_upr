package com.aplikasi.kasir.dao;

import com.aplikasi.kasir.model.Transaksi;
import com.aplikasi.kasir.model.Pelanggan;
import com.aplikasi.kasir.model.DetailLaundry;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransaksiDAO {
    private Connection conn;

    // Constructor menerima koneksi database
    public TransaksiDAO(Connection conn) {
        this.conn = conn;
    }

    // CREATE: Tambah transaksi baru
    public void tambahTransaksi(Transaksi transaksi) throws SQLException {
        // 1. Simpan transaksi utama
        String sqlTransaksi = "INSERT INTO transaksi (id_transaksi, id_pelanggan, tanggal_terima, tanggal_selesai, jumlah_pembayaran, status_pembayaran) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement psTransaksi = conn.prepareStatement(sqlTransaksi)) {
            psTransaksi.setString(1, transaksi.getIdTransaksi());
            psTransaksi.setString(2, transaksi.getPelanggan().getIdPelanggan());
            psTransaksi.setDate(3, Date.valueOf(transaksi.getTanggalTerima()));
            psTransaksi.setDate(4, Date.valueOf(transaksi.getTanggalSelesai()));
            psTransaksi.setDouble(5, transaksi.getJumlahPembayaran());
            psTransaksi.setBoolean(6, transaksi.isStatusPembayaran());
            psTransaksi.executeUpdate();
        }

        // 2. Simpan detail laundry menggunakan batch update untuk efisiensi
        String sqlDetail = "INSERT INTO detail_laundry (id_detail, id_transaksi, jenis_layanan, berat_kg, tarif_per_kg, subtotal) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement psDetail = conn.prepareStatement(sqlDetail)) {
            for (DetailLaundry detail : transaksi.getDetailLaundry()) {
                psDetail.setString(1, detail.getIdDetail());
                psDetail.setString(2, transaksi.getIdTransaksi());
                psDetail.setString(3, detail.getJenisLayanan());
                psDetail.setDouble(4, detail.getBeratKg());
                psDetail.setDouble(5, detail.getTarifPerKg());
                psDetail.setDouble(6, detail.getSubtotal());
                psDetail.addBatch();
            }
            psDetail.executeBatch();
        }
    }

    // READ: Ambil semua transaksi
    public List<Transaksi> getAllTransaksi() throws SQLException {
        List<Transaksi> list = new ArrayList<>();
        // Gunakan JOIN untuk menghindari N+1 query problem
        String sql = "SELECT t.id_transaksi, t.tanggal_terima, t.tanggal_selesai, t.status_pembayaran, " +
                     "p.id_pelanggan, p.nama, p.alamat, p.no_telepon " +
                     "FROM transaksi t JOIN pelanggan p ON t.id_pelanggan = p.id_pelanggan ORDER BY t.tanggal_terima DESC";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                // Gunakan getTransaksiById untuk re-use logic dan memuat detail
                // Ini mungkin kurang efisien untuk daftar yang sangat besar, tapi memastikan data lengkap
                list.add(getTransaksiById(rs.getString("id_transaksi")));
            }
        }
        return list;
    }

    // READ: Ambil transaksi berdasarkan ID
    public Transaksi getTransaksiById(String id) throws SQLException {
        Transaksi transaksi = null;
        // Gunakan JOIN untuk mengambil data pelanggan sekaligus
        String sql = "SELECT * FROM transaksi t JOIN pelanggan p ON t.id_pelanggan = p.id_pelanggan WHERE t.id_transaksi = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Pelanggan pelanggan = new Pelanggan(
                            rs.getString("id_pelanggan"),
                            rs.getString("nama"),
                            rs.getString("alamat"),
                            rs.getString("no_telepon")
                    );
                    transaksi = new Transaksi(
                            rs.getString("id_transaksi"),
                            rs.getDate("tanggal_terima").toLocalDate(),
                            rs.getDate("tanggal_selesai").toLocalDate(),
                            pelanggan
                    );
                    transaksi.ubahStatus(rs.getBoolean("status_pembayaran"));

                    // Ambil dan tambahkan detail laundry terkait
                    List<DetailLaundry> details = getDetailLaundryForTransaksi(id);
                    for(DetailLaundry detail : details) {
                        transaksi.tambahDetailLaundry(detail);
                    }
                    // Hitung total berdasarkan detail yang sudah dimuat
                    transaksi.hitungTotal();
                }
            }
        }
        return transaksi;
    }

    public String generateTransaksiId() throws SQLException {
        String sql = "SELECT id_transaksi FROM transaksi ORDER BY id_transaksi DESC LIMIT 1";
        try (Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                String lastId = rs.getString("id_transaksi"); // TRX010
                int num = Integer.parseInt(lastId.replaceAll("\\D+", ""));
                return "TRX" + String.format("%03d", num + 1);
            }
        }
        return "TRX001";
    }
    

    // Helper method untuk mengambil detail laundry
    private List<DetailLaundry> getDetailLaundryForTransaksi(String idTransaksi) throws SQLException {
        List<DetailLaundry> details = new ArrayList<>();
        String sql = "SELECT * FROM detail_laundry WHERE id_transaksi = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idTransaksi);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DetailLaundry detail = new DetailLaundry(
                        rs.getString("id_detail"),
                        rs.getString("jenis_layanan"),
                        rs.getDouble("berat_kg"),
                        rs.getDouble("tarif_per_kg")
                );
                details.add(detail);
            }
        }
        return details;
    }

    // UPDATE: Method yang hilang, sekarang ditambahkan
    public void updateTransaksi(Transaksi transaksi) throws SQLException {
        String sql = "UPDATE transaksi SET tanggal_selesai = ?, jumlah_pembayaran = ?, status_pembayaran = ?, id_pelanggan = ? WHERE id_transaksi = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(transaksi.getTanggalSelesai()));
            stmt.setDouble(2, transaksi.getJumlahPembayaran());
            stmt.setBoolean(3, transaksi.isStatusPembayaran());
            stmt.setString(4, transaksi.getPelanggan().getIdPelanggan());
            stmt.setString(5, transaksi.getIdTransaksi());
            stmt.executeUpdate();
        }
        // Note: Logika untuk update detail laundry (jika diperlukan) bisa ditambahkan di sini.
        // Biasanya melibatkan penghapusan detail lama dan penambahan detail baru.
    }

    public String generateDetailId() throws SQLException {
        String sql = "SELECT id_detail FROM detail_laundry ORDER BY id_detail DESC LIMIT 1";
        try (Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                String lastId = rs.getString("id_detail"); // contoh: DTL007
                int num = Integer.parseInt(lastId.replaceAll("\\D+", "")); // ambil angka 7
                return "DTL" + String.format("%03d", num + 1); // hasil: DTL008
            }
        }
        return "DTL001"; // default kalau belum ada data
    }

    // DELETE: Hapus transaksi (beserta detailnya)
    public void deleteTransaksi(String idTransaksi) throws SQLException {
        // Hapus detail dulu
        String sqlDetail = "DELETE FROM detail_laundry WHERE id_transaksi = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sqlDetail)) {
            stmt.setString(1, idTransaksi);
            stmt.executeUpdate();
        }

        // Hapus transaksi
        String sqlTransaksi = "DELETE FROM transaksi WHERE id_transaksi = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sqlTransaksi)) {
            stmt.setString(1, idTransaksi);
            stmt.executeUpdate();
        }
    }
}