# Aplikasi Kasir Laundry

Aplikasi Kasir Laundry adalah aplikasi web sederhana yang dibangun menggunakan Java untuk mengelola operasional bisnis laundry. Aplikasi ini mencakup manajemen pelanggan, transaksi, dan pegawai dengan dua level akses: Admin dan Kasir.

## Fitur

- **Autentikasi Pengguna**: Sistem login untuk membedakan hak akses.
- **Manajemen Peran (Role)**:
    - **Admin**:
        - Mengelola data pegawai kasir (menambah dan menghapus).
    - **Kasir**:
        - Mengelola data pelanggan.
        - Membuat, melihat, dan memperbarui transaksi laundry.
        - Menghitung total biaya secara otomatis.
        - Mencetak struk (saat ini output ke konsol server).

## Teknologi yang Digunakan

- **Backend**: Java 11
- **Framework Web**: Jakarta EE 10 (Servlet, JSP)
- **Database**: MariaDB (Kompatibel dengan MySQL)
- **Build Tool**: Apache Maven
- **Server Aplikasi**: Apache Tomcat atau server lain yang kompatibel dengan Jakarta EE.

## Struktur Proyek

Proyek ini mengikuti struktur standar proyek Maven untuk aplikasi web:

```
aplikasi-kasir-laundry/
├── src/
│   ├── main/
│   │   ├── java/com/aplikasi/kasir/
│   │   │   ├── controller/ (Servlets)
│   │   │   ├── dao/        (Data Access Objects)
│   │   │   ├── model/      (POJOs/Entities)
│   │   │   ├── service/    (Business Logic - jika ada)
│   │   │   └── util/       (Utility classes, e.g., DBUtil)
│   │   ├── resources/
│   │   │   └── db.properties (Konfigurasi Database)
│   │   └── webapp/
│   │       ├── WEB-INF/
│   │       │   └── web.xml
│   │       ├── *.jsp       (Halaman Web)
│   │       └── css/        (Styling)
├── pom.xml                 (Konfigurasi Maven)
└── README.md               (Dokumentasi Proyek)
```

## Cara Menjalankan Proyek

### Prasyarat

1.  **JDK (Java Development Kit)**: Versi 11 atau yang lebih baru.
2.  **Apache Maven**: Untuk build dan manajemen dependensi.
3.  **Database Server**: MariaDB atau MySQL.
4.  **Web Server**: Apache Tomcat 10.1 atau yang lebih baru.

### Langkah-langkah Setup

1.  **Clone Repository**
    ```bash
    git clone <URL_REPOSITORY_ANDA>
    cd aplikasi-kasir-laundry
    ```

2.  **Setup Database**
    - Buat database baru di MariaDB/MySQL.
      ```sql
      CREATE DATABASE laundry_db;
      ```
    - Jalankan skrip SQL berikut untuk membuat semua tabel yang diperlukan dan mengisi data awal.
      ```sql
      -- Tabel Pegawai (Admin & Kasir)
      CREATE TABLE pegawai (
        id_pegawai VARCHAR(50) NOT NULL,
        nama VARCHAR(100) NOT NULL,
        username VARCHAR(50) NOT NULL,
        password VARCHAR(255) NOT NULL,
        role ENUM('ADMIN', 'KASIR') NOT NULL,
        PRIMARY KEY (id_pegawai),
        UNIQUE INDEX username_UNIQUE (username ASC)
      );

      -- Tabel Pelanggan
      CREATE TABLE pelanggan (
        id_pelanggan VARCHAR(50) NOT NULL,
        nama VARCHAR(100) NOT NULL,
        alamat TEXT NULL,
        no_telepon VARCHAR(20) NOT NULL,
        PRIMARY KEY (id_pelanggan)
      );

      -- Tabel Transaksi
      CREATE TABLE transaksi (
        id_transaksi VARCHAR(50) NOT NULL,
        id_pelanggan VARCHAR(50) NOT NULL,
        id_pegawai VARCHAR(50) NOT NULL,
        tanggal_terima DATE NOT NULL,
        tanggal_selesai DATE NULL,
        jumlah_pembayaran DECIMAL(10, 2) DEFAULT 0.00,
        status_pembayaran BOOLEAN NOT NULL DEFAULT FALSE,
        PRIMARY KEY (id_transaksi),
        CONSTRAINT fk_transaksi_pelanggan FOREIGN KEY (id_pelanggan) REFERENCES pelanggan (id_pelanggan),
        CONSTRAINT fk_transaksi_pegawai FOREIGN KEY (id_pegawai) REFERENCES pegawai (id_pegawai)
      );

      -- Tabel Detail Laundry
      CREATE TABLE detail_laundry (
        id_detail VARCHAR(50) NOT NULL,
        id_transaksi VARCHAR(50) NOT NULL,
        jenis_layanan VARCHAR(100) NOT NULL,
        berat_kg DECIMAL(5, 2) NOT NULL,
        tarif_per_kg DECIMAL(10, 2) NOT NULL,
        subtotal DECIMAL(10, 2) NOT NULL,
        PRIMARY KEY (id_detail),
        CONSTRAINT fk_detail_transaksi FOREIGN KEY (id_transaksi) REFERENCES transaksi (id_transaksi) ON DELETE CASCADE
      );

      -- Data Awal
      INSERT INTO pegawai (id_pegawai, nama, username, password, role) VALUES
      ('ADM-001', 'Admin Utama', 'admin', 'password123', 'ADMIN'),
      ('KSR-001', 'Budi Santoso', 'kasir_budi', 'password123', 'KASIR');
      ```

3.  **Konfigurasi Koneksi Database**
    - Buat atau edit file `src/main/resources/db.properties` dengan detail koneksi database Anda.
      ```properties
      db.url=jdbc:mariadb://localhost:3306/laundry_db
      db.username=root
      db.password=password_anda
      ```

4.  **Build Proyek**
    - Buka terminal di direktori root proyek dan jalankan perintah Maven.
      ```bash
      mvn clean install
      ```
    - Perintah ini akan mengunduh semua dependensi dan membuat file `aplikasi-kasir-laundry.war` di dalam direktori `target/`.

5.  **Deploy Aplikasi**
    - Salin file `target/aplikasi-kasir-laundry.war` ke direktori `webapps/` pada instalasi Apache Tomcat Anda.
    - Jalankan server Tomcat.

6.  **Akses Aplikasi**
    - Buka browser dan akses URL berikut:
      http://localhost:8080/aplikasi-kasir-laundry/login.jsp
    - Anda bisa login menggunakan akun default yang mungkin sudah Anda masukkan ke database.

---

*Dibuat dengan ❤️ untuk manajemen laundry yang lebih baik.*