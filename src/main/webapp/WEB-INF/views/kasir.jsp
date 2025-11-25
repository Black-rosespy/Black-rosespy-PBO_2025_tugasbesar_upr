<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Kasir - Aplikasi Kasir Laundry</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <div class="container">
        <h2>Menu Kasir</h2>

        <!-- Pesan sukses atau error -->
        <c:if test="${not empty message}">
            <div class="success">${message}</div>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div class="error">${errorMessage}</div>
        </c:if>

        <!-- Form input pelanggan -->
        <h3>Tambah Pelanggan Baru</h3>
        <form action="${pageContext.request.contextPath}/kasir" method="post">
            <input type="hidden" name="action" value="pelanggan">
            <div class="form-group">
                <label for="nama">Nama:</label>
                <input type="text" name="nama" id="nama" required>
            </div>
            <div class="form-group">
                <label for="alamat">Alamat:</label>
                <input type="text" name="alamat" id="alamat" required>
            </div>
            <div class="form-group">
                <label for="noTelepon">No Telepon:</label>
                <input type="text" name="noTelepon" id="noTelepon" required>
            </div>
            <button type="submit">Tambah Pelanggan</button>
        </form>

        <!-- Form input transaksi -->
        <h3>Tambah Transaksi Laundry</h3>
        <form action="${pageContext.request.contextPath}/kasir" method="post">
            <input type="hidden" name="action" value="transaksi">
            <div class="form-group">
                <label for="idPelangganTransaksi">ID Pelanggan:</label>
                <input type="text" name="idPelanggan" id="idPelangganTransaksi" required>
            </div>
            <div class="form-group">
                <label for="tanggalSelesai">Tanggal Selesai:</label>
                <input type="date" name="tanggalSelesai" id="tanggalSelesai" required>
            </div>
            <div class="form-group">
                <label for="jenisLayanan">Jenis Layanan:</label>
                <input type="text" name="jenisLayanan" id="jenisLayanan" required>
            </div>
            <div class="form-group">
                <label for="berat">Berat (Kg):</label>
                <input type="number" step="0.1" name="berat" id="berat" required>
            </div>
            <div class="form-group">
                <label for="tarif">Tarif per Kg:</label>
                <input type="number" step="0.01" name="tarif" id="tarif" required>
            </div>
            <button type="submit">Tambah Transaksi</button>
        </form>

        <!-- Lihat riwayat transaksi -->
        <h3>Lihat Riwayat Transaksi</h3>
        <form action="${pageContext.request.contextPath}/kasir" method="post">
            <input type="hidden" name="action" value="lihat">
            <button type="submit">Lihat Semua Transaksi</button>
        </form>
        

        <!-- Cetak struk -->
        <h3>Cetak Struk</h3>
        <form action="${pageContext.request.contextPath}/kasir" method="post">
            <input type="hidden" name="action" value="struk">
            <div class="form-group">
                <label for="idTransaksiCetak">ID Transaksi:</label>
                <input type="text" name="idTransaksi" id="idTransaksiCetak" required>
            </div>
            <button type="submit">Cetak Struk (PDF)</button>
        </form>
        <p class="info">*Struk hanya dapat dicetak jika status pembayaran sudah <span style="color:green;">Lunas</span>.</p>

        <h3>Daftar Pelanggan</h3>
        <form action="${pageContext.request.contextPath}/kasir" method="post">
            <input type="hidden" name="action" value="daftarPelanggan">
            <button type="submit">Lihat Daftar Pelanggan</button>
        </form>

        <c:if test="${not empty daftarPelanggan}">
            <table border="1" cellpadding="8" cellspacing="0">
                <thead>
                    <tr>
                        <th>ID Pelanggan</th>
                        <th>Nama</th>
                        <th>Alamat</th>
                        <th>No Telepon</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="pelanggan" items="${daftarPelanggan}">
                        <tr>
                            <td>${pelanggan.idPelanggan}</td>
                            <td>${pelanggan.nama}</td>
                            <td>${pelanggan.alamat}</td>
                            <td>${pelanggan.noTelepon}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>

        <!-- Link logout -->
        <p><a href="${pageContext.request.contextPath}/login">Logout</a></p>
    </div>
</body>
</html>