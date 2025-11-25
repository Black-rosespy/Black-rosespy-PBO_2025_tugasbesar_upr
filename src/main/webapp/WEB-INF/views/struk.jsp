<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Struk Pembayaran - Aplikasi Kasir Laundry</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <div class="container">
        <h2>Struk Pembayaran</h2>

        <!-- Pesan error jika transaksi tidak ditemukan -->
        <c:if test="${not empty errorMessage}">
            <div class="error">${errorMessage}</div>
        </c:if>

        <!-- Tampilkan struk jika ada -->
        <c:if test="${not empty struk}">
            <div class="struk-box">
                <p><strong>ID Struk:</strong> ${struk.idStruk}</p>
                <p><strong>Tanggal Cetak:</strong> ${struk.tanggalCetak}</p>
                <p><strong>ID Transaksi:</strong> ${struk.transaksi.idTransaksi}</p>
                <p><strong>Nama Pelanggan:</strong> ${struk.transaksi.pelanggan.nama}</p>

                <h3>Detail Laundry</h3>
                <table border="1" cellpadding="8" cellspacing="0">
                    <thead>
                        <tr>
                            <th>Jenis Layanan</th>
                            <th>Berat (Kg)</th>
                            <th>Tarif/Kg</th>
                            <th>Subtotal</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="detail" items="${struk.transaksi.detailLaundry}">
                            <tr>
                                <td>${detail.jenisLayanan}</td>
                                <td>${detail.beratKg}</td>
                                <td>Rp ${detail.tarifPerKg}</td>
                                <td>Rp ${detail.subtotal}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <p><strong>Total Bayar:</strong> Rp ${struk.transaksi.jumlahPembayaran}</p>
                <p><strong>Status Bayar:</strong>
                    <c:choose>
                        <c:when test="${struk.transaksi.statusPembayaran}">
                            Lunas
                        </c:when>
                        <c:otherwise>
                            Belum Lunas
                        </c:otherwise>
                    </c:choose>
                </p>

                <hr>
                <p>Terima kasih telah menggunakan layanan Laundry kami!</p>
            </div>
        </c:if>

        <!-- Link kembali -->
        <p><a href="${pageContext.request.contextPath}/kasir">Kembali ke Menu Kasir</a></p>
    </div>
</body>
</html>