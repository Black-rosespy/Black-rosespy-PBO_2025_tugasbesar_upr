<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin - Aplikasi Kasir Laundry</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <div class="container">
        <h2>Menu Admin</h2>

        <!-- Pesan sukses atau error -->
        <c:if test="${not empty message}">
            <div class="success">${message}</div>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div class="error">${errorMessage}</div>
        </c:if>

        <!-- Form tambah kasir -->
        <h3>Tambah Kasir Baru</h3>
        <form action="${pageContext.request.contextPath}/admin" method="post">
            <input type="hidden" name="action" value="tambah">
            <div class="form-group">
                <label for="namaKasir">Nama Kasir:</label>
                <input type="text" name="namaKasir" id="namaKasir" required>
            </div>
            <div class="form-group">
                <label for="usernameKasir">Username:</label>
                <input type="text" name="usernameKasir" id="usernameKasir" required>
            </div>
            <div class="form-group">
                <label for="passwordKasir">Password:</label>
                <input type="password" name="passwordKasir" id="passwordKasir" required>
            </div>
            <button type="submit">Tambah Kasir</button>
        </form>

        <!-- Form hapus kasir -->
        <h3>Hapus Kasir</h3>
        <form action="${pageContext.request.contextPath}/admin" method="post">
            <input type="hidden" name="action" value="hapus">
            <div class="form-group">
                <label for="idKasirHapus">ID Kasir:</label>
                <input type="text" name="idKasir" id="idKasirHapus" required>
            </div>
            <button type="submit">Hapus Kasir</button>
        </form>

        <h3>Daftar Kasir</h3>
        <table border="1" cellpadding="8" cellspacing="0">
            <thead>
                <tr>
                    <th>ID Kasir</th>
                    <th>Nama</th>
                    <th>Username</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="kasir" items="${daftarKasir}">
                    <tr>
                        <td>${kasir.idPegawai}</td>
                        <td>${kasir.nama}</td>
                        <td>${kasir.username}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- Link logout -->
        <p><a href="${pageContext.request.contextPath}/login">Logout</a></p>
    </div>
</body>
</html>