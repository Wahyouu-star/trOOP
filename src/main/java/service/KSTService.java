package service;

import java.util.ArrayList;
import model.KSTModel;
import model.Database;
import java.sql.Connection;
import java.sql.PreparedStatement; 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class KSTService {

    // Metode untuk mengambil SEMUA Matakuliah (untuk KSTView)
    public ArrayList<KSTModel> getAvailableCourses() {
        ArrayList<KSTModel> list = new ArrayList<>();
        String sql = "SELECT kode_matkul, nama_matkul, sks FROM matakuliah";
        
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                String kode = rs.getString("kode_matkul");
                String nama = rs.getString("nama_matkul");
                int sks = rs.getInt("sks");
                list.add(new KSTModel(kode, nama, sks));
            }
        } catch (SQLException e) {
            System.err.println("Error mengambil matakuliah: " + e.getMessage());
        }
        return list;
    }

    /**
     * Mendaftarkan matakuliah ke KRS dan membuat tagihan otomatis.
     * Menggunakan Transaction (COMMIT/ROLLBACK)
     */
    public boolean registerCourseAndGenerateBill(String nim, String kodeMatkul, int sks) {
        // KONSTANTA BIAYA
        long biayaPerSKS = 250000;
        long totalBiaya = (long) sks * biayaPerSKS;

        // DATA SEMESTER
        String tahunAjaran = "2024-2025";
        String semester = "GANJIL";
        String statusPembayaran = "BELUM BAYAR";
        String jenisTagihan = "BIAYA SKS - " + kodeMatkul + " (" + tahunAjaran + "/" + semester + ")";

        Connection conn = null;
        try {
            conn = Database.getConnection();
            conn.setAutoCommit(false); // 1. Mulai Transaksi

            // --- PERINTAH 1: SIMPAN KE TABEL KRS ---
            // Kolom di SQL: (nim_mhs, kode_matkul, tahun_ajaran, semester, sks)
            String sqlKRS = "INSERT INTO krs (nim_mhs, kode_matkul, tahun_ajaran, semester, sks) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmtKRS = conn.prepareStatement(sqlKRS)) {
                // Urutan Binding:
                stmtKRS.setString(1, nim);          // 1. nim_mhs (VARCHAR)
                stmtKRS.setString(2, kodeMatkul);   // 2. kode_matkul (VARCHAR)
                stmtKRS.setString(3, tahunAjaran);  // 3. tahun_ajaran (VARCHAR)
                stmtKRS.setString(4, semester);     // 4. semester (VARCHAR)
                stmtKRS.setInt(5, sks);             // 5. sks (INT) <-- HARUS INT
                stmtKRS.executeUpdate();
            }

            // --- PERINTAH 2: BUAT TAGIHAN OTOMATIS ---
            // Kolom di SQL: (nim_mhs, jenis_tagihan, jumlah_sks, total_biaya, status_pembayaran)
            String sqlTagihan = "INSERT INTO tagihan (nim_mhs, jenis_tagihan, jumlah_sks, total_biaya, status_pembayaran) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmtTagihan = conn.prepareStatement(sqlTagihan)) {
                // Urutan Binding:
                stmtTagihan.setString(1, nim);              // 1. nim_mhs (VARCHAR)
                stmtTagihan.setString(2, jenisTagihan);     // 2. jenis_tagihan (VARCHAR)
                stmtTagihan.setInt(3, sks);                 // 3. jumlah_sks (INT)
                stmtTagihan.setLong(4, totalBiaya);         // 4. total_biaya (BIGINT)
                stmtTagihan.setString(5, statusPembayaran);  // 5. status_pembayaran (VARCHAR)
                stmtTagihan.executeUpdate();
            }

            conn.commit(); // 2. COMMIT: Jika kedua perintah berhasil
            return true;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // 3. ROLLBACK: Jika ada error, batalkan
                } catch (SQLException ex) {
                    System.err.println("Rollback gagal: " + ex.getMessage());
                }
            }
            
            if (e.getErrorCode() == 1062) {
                System.err.println("Gagal Registrasi: Matakuliah ganda (sudah terdaftar di periode yang sama).");
            } else {
                 System.err.println("Gagal Registrasi dan Generate Tagihan: " + e.getMessage());
            }
            return false;

        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Gagal menutup koneksi: " + e.getMessage());
                }
            }
        }
    }
}