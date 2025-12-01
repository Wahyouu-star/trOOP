// TR-OOP/src/main/java/service/AuthService.java

package service;

import model.Database; 
import model.PenggunaModel; // <-- TAMBAHKAN
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthService {
    
    // Ganti return type menjadi PenggunaModel
    public PenggunaModel authenticate(String role, String username, String password) {
        // Ambil semua kolom yang dibutuhkan untuk PenggunaModel
        String sql = "SELECT id, password, role, nama_lengkap FROM pengguna WHERE id = ? AND password = ? AND role = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                // Login sukses, kembalikan objek PenggunaModel
                String id = rs.getString("id");
                String pw = rs.getString("password");
                String userRole = rs.getString("role");
                String namaLengkap = rs.getString("nama_lengkap");
                
                return new PenggunaModel(id, pw, userRole, namaLengkap);
            }
            
            return null; // Login gagal
            
        } catch (SQLException e) {
            System.err.println("Error otentikasi database: " + e.getMessage());
            return null;
        }
    }
}