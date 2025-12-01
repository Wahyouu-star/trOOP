package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    
    // Konfigurasi koneksi Anda
    private static final String URL = "jdbc:mysql://localhost:3306/tr_oop_sia";
    private static final String USER = "root"; // Ganti dengan username MySQL Anda
    private static final String PASSWORD = ""; // Ganti dengan password MySQL Anda

    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Memuat driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Membuat koneksi
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Driver JDBC MySQL tidak ditemukan.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error: Gagal membuat koneksi ke database. Pastikan MySQL sudah berjalan.");
            e.printStackTrace();
        }
        return connection;
    }
}