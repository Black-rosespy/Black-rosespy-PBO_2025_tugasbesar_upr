package com.aplikasi.kasir.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;

public class DBUtil {

    private static Properties loadProperties() throws Exception {
        Properties props = new Properties();
        try (InputStream input = DBUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new Exception("File db.properties tidak ditemukan di classpath!");
            }
            props.load(input);
        }
        return props;
    }

    public static Connection getConnection() throws Exception {
    Properties props = loadProperties();

    String url = props.getProperty("db.url");
    String user = props.getProperty("db.user");
    String password = props.getProperty("db.password");

    try {
        // Pastikan driver MariaDB ter-load
        Class.forName("org.mariadb.jdbc.Driver");

        return DriverManager.getConnection(url, user, password);
    } catch (SQLException e) {
        throw new Exception("Gagal koneksi ke database: " + e.getMessage(), e);
    }
    }
}