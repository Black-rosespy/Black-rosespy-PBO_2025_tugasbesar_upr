package com.aplikasi.kasir.controller;

import com.aplikasi.kasir.dao.PegawaiDAO;
import com.aplikasi.kasir.model.Pegawai;
import com.aplikasi.kasir.model.PegawaiKasir;
import com.aplikasi.kasir.util.DBUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/*")
public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection conn = DBUtil.getConnection()) {
            PegawaiDAO pegawaiDAO = new PegawaiDAO(conn);
            List<PegawaiKasir> daftarKasir = pegawaiDAO.getAllKasir();
            request.setAttribute("daftarKasir", daftarKasir);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Gagal memuat daftar kasir: " + e.getMessage());
        }
        request.getRequestDispatcher("/WEB-INF/views/admin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            action = ""; // Hindari NullPointerException
        }

        try (Connection conn = DBUtil.getConnection()) {
            PegawaiDAO pegawaiDAO = new PegawaiDAO(conn);

            switch (action.toLowerCase()) {
                case "tambah":
                    tambahKasir(request, pegawaiDAO);
                    break;
                case "hapus":
                    hapusKasir(request, pegawaiDAO);
                    break;
                default:
                    request.setAttribute("errorMessage", "Aksi tidak dikenali!");
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Terjadi kesalahan database: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Terjadi kesalahan sistem.");
        }
        request.getRequestDispatcher("/WEB-INF/views/admin.jsp").forward(request, response);
    }

    private void tambahKasir(HttpServletRequest request, PegawaiDAO pegawaiDAO) throws SQLException {
        String idKasir = pegawaiDAO.generatePegawaiId("PGW");
        String namaKasir = request.getParameter("namaKasir");
        String usernameKasir = request.getParameter("usernameKasir");
        String passwordKasir = request.getParameter("passwordKasir");

        PegawaiKasir kasirBaru = new PegawaiKasir(idKasir, namaKasir, usernameKasir, passwordKasir);
        pegawaiDAO.tambahPegawai(kasirBaru, "KASIR");
        request.setAttribute("message", "Kasir baru berhasil ditambahkan!");
    }

    private void hapusKasir(HttpServletRequest request, PegawaiDAO pegawaiDAO) throws SQLException {
        String idKasir = request.getParameter("idKasir");
        pegawaiDAO.deletePegawai(idKasir);
        request.setAttribute("message", "Kasir dengan ID " + idKasir + " berhasil dihapus!");
    }
}