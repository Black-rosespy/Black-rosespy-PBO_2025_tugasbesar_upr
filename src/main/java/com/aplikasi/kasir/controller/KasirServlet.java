package com.aplikasi.kasir.controller;

import com.aplikasi.kasir.dao.PelangganDAO;
import com.aplikasi.kasir.dao.TransaksiDAO;
import com.aplikasi.kasir.model.Pelanggan;
import com.aplikasi.kasir.model.Transaksi;
import com.aplikasi.kasir.model.DetailLaundry;
import com.aplikasi.kasir.util.DBUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

// iText imports
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@WebServlet("/kasir")
public class KasirServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Default: tampilkan halaman kasir.jsp
        request.getRequestDispatcher("/WEB-INF/views/kasir.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action"); // "pelanggan", "transaksi", "lihat", "struk", "ubahStatusPembayaran"

        try (Connection conn = DBUtil.getConnection()) {
            PelangganDAO pelangganDAO = new PelangganDAO(conn);
            TransaksiDAO transaksiDAO = new TransaksiDAO(conn);

            switch (action) {
                case "pelanggan":
                    // Tambah pelanggan baru
                    String idPelanggan = pelangganDAO.generatePelangganId();
                    String nama = request.getParameter("nama");
                    String alamat = request.getParameter("alamat");
                    String noTelepon = request.getParameter("noTelepon");

                    Pelanggan pelangganBaru = new Pelanggan(idPelanggan, nama, alamat, noTelepon);
                    pelangganDAO.tambahPelanggan(pelangganBaru);

                    request.setAttribute("message", "Data pelanggan berhasil ditambahkan!");
                    request.getRequestDispatcher("/WEB-INF/views/kasir.jsp").forward(request, response);
                    break;

                case "transaksi":
                    // Tambah transaksi baru
                    String idTransaksi = transaksiDAO.generateTransaksiId();
                    String idPelangganTransaksi = request.getParameter("idPelanggan");
                    Pelanggan pelanggan = pelangganDAO.getPelangganById(idPelangganTransaksi);

                    LocalDate tanggalTerima = LocalDate.now();
                    LocalDate tanggalSelesai = LocalDate.parse(request.getParameter("tanggalSelesai"));

                    Transaksi transaksiBaru = new Transaksi(idTransaksi, tanggalTerima, tanggalSelesai, pelanggan);

                    // Tambahkan detail laundry
                    String idDetail = transaksiDAO.generateDetailId();
                    String jenisLayanan = request.getParameter("jenisLayanan");
                    double berat = Double.parseDouble(request.getParameter("berat"));
                    double tarif = Double.parseDouble(request.getParameter("tarif"));

                    DetailLaundry detail = new DetailLaundry(idDetail, jenisLayanan, berat, tarif);
                    transaksiBaru.tambahDetailLaundry(detail);
                    transaksiBaru.hitungTotal();

                    transaksiDAO.tambahTransaksi(transaksiBaru);

                    request.setAttribute("message", "Transaksi berhasil ditambahkan!");
                    request.getRequestDispatcher("/WEB-INF/views/kasir.jsp").forward(request, response);
                    break;

                case "lihat":
                    // Lihat semua transaksi
                    List<Transaksi> daftarTransaksi = transaksiDAO.getAllTransaksi();
                    request.setAttribute("daftarTransaksi", daftarTransaksi);
                    request.getRequestDispatcher("/WEB-INF/views/transaksi.jsp").forward(request, response);
                    break;

                case "struk":
                    // Cetak struk PDF hanya jika transaksi sudah lunas
                    String idTransaksiCetak = request.getParameter("idTransaksi");
                    Transaksi transaksiCetak = transaksiDAO.getTransaksiById(idTransaksiCetak);

                    if (transaksiCetak != null) {
                        if (transaksiCetak.isStatusPembayaran()) {
                            // ✅ Cetak PDF
                            response.setContentType("application/pdf");
                            response.setHeader("Content-Disposition", "inline; filename=struk-" + idTransaksiCetak + ".pdf");

                            try (OutputStream out = response.getOutputStream()) {
                                Document document = new Document();
                                PdfWriter.getInstance(document, out);

                                document.open();
                                document.add(new Paragraph("=== STRUK TRANSAKSI LAUNDRY ==="));
                                document.add(new Paragraph("ID Transaksi: " + transaksiCetak.getIdTransaksi()));
                                document.add(new Paragraph("Nama Pelanggan: " + transaksiCetak.getPelanggan().getNama()));
                                document.add(new Paragraph("Tanggal Terima: " + transaksiCetak.getTanggalTerima()));
                                document.add(new Paragraph("Tanggal Selesai: " + transaksiCetak.getTanggalSelesai()));
                                document.add(new Paragraph("Total Pembayaran: Rp " + transaksiCetak.getJumlahPembayaran()));
                                document.add(new Paragraph("Status: Lunas"));
                                document.add(new Paragraph(" "));

                                // Tambahkan tabel detail laundry
                                PdfPTable table = new PdfPTable(4);
                                table.addCell("ID Detail");
                                table.addCell("Jenis Layanan");
                                table.addCell("Berat (kg)");
                                table.addCell("Tarif per kg");

                                for (DetailLaundry d : transaksiCetak.getDetailLaundry()) {
                                    table.addCell(d.getIdDetail());
                                    table.addCell(d.getJenisLayanan());
                                    table.addCell(String.valueOf(d.getBeratKg()));
                                    table.addCell(String.valueOf(d.getTarifPerKg()));
                                }

                                document.add(table);
                                document.close();
                            }
                        } else {
                            // ❌ Jika belum lunas
                            request.setAttribute("errorMessage", "Transaksi belum lunas, tidak dapat mencetak struk!");
                            request.getRequestDispatcher("/WEB-INF/views/transaksi.jsp").forward(request, response);
                        }
                    } else {
                        request.setAttribute("errorMessage", "Transaksi tidak ditemukan!");
                        request.getRequestDispatcher("/WEB-INF/views/kasir.jsp").forward(request, response);
                    }
                    break;

                case "daftarPelanggan":
                    // Ambil semua pelanggan
                    List<Pelanggan> daftarPelanggan = pelangganDAO.getAllPelanggan();
                    request.setAttribute("daftarPelanggan", daftarPelanggan);
                    request.getRequestDispatcher("/WEB-INF/views/kasir.jsp").forward(request, response);
                    break;

                case "ubahStatusPembayaran":
                    // Ubah status pembayaran transaksi
                    String idTransaksiUbah = request.getParameter("idTransaksi");
                    boolean statusBaru = Boolean.parseBoolean(request.getParameter("statusPembayaran"));

                    Transaksi transaksiUbah = transaksiDAO.getTransaksiById(idTransaksiUbah);
                    if (transaksiUbah != null) {
                        transaksiUbah.ubahStatus(statusBaru);
                        transaksiUbah.hitungTotal(); // tetap konsisten
                        transaksiDAO.updateTransaksi(transaksiUbah);

                        request.setAttribute("message", "Status pembayaran transaksi " + idTransaksiUbah + " berhasil diperbarui!");
                    } else {
                        request.setAttribute("errorMessage", "Transaksi tidak ditemukan!");
                    }
                    // Setelah update, tampilkan daftar transaksi
                    List<Transaksi> daftarTransaksiUpdate = transaksiDAO.getAllTransaksi();
                    request.setAttribute("daftarTransaksi", daftarTransaksiUpdate);
                    request.getRequestDispatcher("/WEB-INF/views/transaksi.jsp").forward(request, response);
                    break;

                default:
                    request.setAttribute("errorMessage", "Aksi tidak dikenali!");
                    request.getRequestDispatcher("/WEB-INF/views/kasir.jsp").forward(request, response);
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Terjadi kesalahan sistem.");
            request.getRequestDispatcher("/WEB-INF/views/kasir.jsp").forward(request, response);
        }
    }
}