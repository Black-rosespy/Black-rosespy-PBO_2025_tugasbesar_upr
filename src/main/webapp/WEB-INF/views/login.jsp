<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login - Aplikasi Kasir Laundry</title>
    <link rel="stylesheet" type="text/css" href="assets/css/style.css">
</head>
<body>
    <div class="container">
        <h2>Login Aplikasi Kasir Laundry</h2>

        <!-- Tampilkan pesan error jika ada -->
        <c:if test="${not empty errorMessage}">
            <div class="error">${errorMessage}</div>
        </c:if>

        <!-- Form login -->
        <form action="login" method="post">
            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" name="username" id="username" required>
            </div>

            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" name="password" id="password" required>
            </div>

            <div class="form-group">
                <button type="submit">Login</button>
            </div>
        </form>
    </div>
</body>
</html>