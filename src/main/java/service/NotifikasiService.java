package service;

import java.util.ArrayList;
import model.NotifikasiModel;
import model.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class NotifikasiService {

    public boolean addNotification(String judul, String pesan, String pengirimRole, String targetRole, String targetKelas) {
        String sql = "INSERT INTO notifikasi (judul, pesan, pengirim_role, target_role, target_kelas) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String finalJudul = judul.isEmpty() ? pesan.substring(0, Math.min(pesan.length(), 40)) + "..." : judul;
            
            stmt.setString(1, finalJudul);
            stmt.setString(2, pesan);
            stmt.setString(3, pengirimRole);
            stmt.setString(4, targetRole);
            stmt.setString(5, targetKelas); 
            
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Gagal menyimpan notifikasi: " + e.getMessage());
            return false;
        }
    }
    
    // getNotifications diubah untuk mengambil pengirim_role
    public ArrayList<NotifikasiModel> getNotifications(String userRole, String userClass) {
        ArrayList<NotifikasiModel> list = new ArrayList<>();
        
        // ADD pengirim_role ke SELECT statement
        String sql = "SELECT judul, pesan, pengirim_role FROM notifikasi WHERE target_role = 'ALL' OR target_role = ? OR target_kelas = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, userRole);
            stmt.setString(2, userClass); 
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                String judul = rs.getString("judul");
                String pesan = rs.getString("pesan");
                String pengirimRole = rs.getString("pengirim_role"); // AMBIL ROLE PENGIRIM
                
                // Update constructor call
                list.add(new NotifikasiModel(judul, pesan, pengirimRole));
            }
        } catch (SQLException e) {
            System.err.println("Gagal mengambil notifikasi: " + e.getMessage());
        }
        return list;
    }
    
    public int getUnreadCount(String userRole, String userClass) {
        return getNotifications(userRole, userClass).size();
    }
}