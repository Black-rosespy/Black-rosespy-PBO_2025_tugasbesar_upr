package com.aplikasi.kasir.controller;

import com.aplikasi.kasir.dao.PegawaiDAO;
import com.aplikasi.kasir.model.Pegawai;
import com.aplikasi.kasir.model.PegawaiAdmin;
import com.aplikasi.kasir.model.PegawaiKasir;
import com.aplikasi.kasir.util.DBUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (Connection conn = DBUtil.getConnection()) {
            PegawaiDAO pegawaiDAO = new PegawaiDAO(conn);
            Pegawai pegawai = pegawaiDAO.getPegawaiByUsername(username);

            if (pegawai != null && pegawai.login(username, password)) {
                // Simpan informasi pegawai ke session
                HttpSession session = request.getSession();
                session.setAttribute("pegawai", pegawai);

                // Redirect sesuai role
                if (pegawai instanceof PegawaiAdmin) {
                    response.sendRedirect(request.getContextPath() + "/admin");
                } else if (pegawai instanceof PegawaiKasir) {
                    response.sendRedirect(request.getContextPath() + "/kasir");
                } else {
                    response.sendRedirect(request.getContextPath() + "/error.jsp");
                }
            } else {
                // Login gagal
                request.setAttribute("errorMessage", "Username atau password salah!");
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Terjadi kesalahan sistem.");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Arahkan ke halaman login jika ada request GET
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }
}