package service;

import java.util.ArrayList;
import model.KalenderModel;
import model.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class KalenderService {
    
    // --- METHOD UNTUK MENAMBAHKAN EVENT KE DATABASE ---
    public boolean addEvent(KalenderModel event) {
        String sql = "INSERT INTO kalender (nama_event, tanggal, deskripsi, pengirim_role, target_role) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, event.getNamaEvent());
            stmt.setString(2, event.getTanggal()); // YYYY-MM-DD
            stmt.setString(3, event.getDeskripsi());
            stmt.setString(4, event.getPengirimRole());
            stmt.setString(5, event.getTargetRole());
            
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Gagal menyimpan agenda: " + e.getMessage());
            return false;
        }
    }

    // --- METHOD UNTUK MENGAMBIL EVENT BERDASARKAN ROLE USER ---
    public List<KalenderModel> getEvents(String userRole) {
        List<KalenderModel> eventList = new ArrayList<>();
        
        // Logic: Ambil event yang ditargetkan 'ALL' ATAU ditargetkan ke ROLE pengguna
        String sql = "SELECT nama_event, tanggal, deskripsi, pengirim_role, target_role FROM kalender WHERE target_role = 'ALL' OR target_role = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, userRole);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                String namaEvent = rs.getString("nama_event");
                String tanggal = rs.getString("tanggal");
                String deskripsi = rs.getString("deskripsi");
                String pengirimRole = rs.getString("pengirim_role");
                String targetRole = rs.getString("target_role");
                
                eventList.add(new KalenderModel(namaEvent, tanggal, deskripsi, pengirimRole, targetRole));
            }
        } catch (SQLException e) {
            System.err.println("Gagal mengambil agenda: " + e.getMessage());
        }
        return eventList;
    }
}