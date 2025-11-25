<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head>
    <title>Daftar Transaksi Laundry</title>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #ccc;
            padding: 8px;
            text-align: left;
        }
        th {
            background: #f2f2f2;
        }
        .success {
            color: green;
            font-weight: bold;
        }
        .error {
            color: red;
            font-weight: bold;
        }
        .disabled {
            color: gray;
            font-style: italic;
        }
    </style>
</head>
<body>
<h2>Daftar Transaksi Laundry</h2>

<!-- Pesan sukses atau error -->
<c:if test="${not empty message}">
    <p class="success">${message}</p>
</c:if>
<c:if test="${not empty errorMessage}">
    <p class="error">${errorMessage}</p>
</c:if>

<!-- Tabel daftar transaksi -->
<table>
    <thead>
    <tr>
        <th>ID Transaksi</th>
        <th>Pelanggan</th>
        <th>Tanggal Terima</th>
        <th>Tanggal Selesai</th>
        <th>Total Pembayaran</th>
        <th>Status Pembayaran</th>
        <th>Aksi</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="transaksi" items="${daftarTransaksi}">
        <tr>
            <td>${transaksi.idTransaksi}</td>
            <td>${transaksi.pelanggan.nama}</td>
            <td>${transaksi.tanggalTerima}</td>
            <td>${transaksi.tanggalSelesai}</td>
            <td>${transaksi.jumlahPembayaran}</td>
            <td>
                <c:choose>
                    <c:when test="${transaksi.statusPembayaran}">
                        <span style="color:green;">Lunas</span>
                    </c:when>
                    <c:otherwise>
                        <span style="color:red;">Belum Lunas</span>
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                <!-- Form untuk ubah status pembayaran -->
                <form action="${pageContext.request.contextPath}/kasir" method="post" style="display:inline;">
                    <input type="hidden" name="action" value="ubahStatusPembayaran"/>
                    <input type="hidden" name="idTransaksi" value="${transaksi.idTransaksi}"/>
                    <select name="statusPembayaran">
                        <option value="true" ${transaksi.statusPembayaran ? "selected" : ""}>Lunas</option>
                        <option value="false" ${!transaksi.statusPembayaran ? "selected" : ""}>Belum Lunas</option>
                    </select>
                    <button type="submit">Update</button>
                </form>

                <!-- Cetak struk hanya jika lunas -->
                <c:choose>
                    <c:when test="${transaksi.statusPembayaran}">
                        <form action="${pageContext.request.contextPath}/kasir" method="post" style="display:inline;">
                            <input type="hidden" name="action" value="struk"/>
                            <input type="hidden" name="idTransaksi" value="${transaksi.idTransaksi}"/>
                            <button type="submit">Cetak Struk (PDF)</button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <span class="disabled">Struk hanya tersedia jika lunas</span>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>