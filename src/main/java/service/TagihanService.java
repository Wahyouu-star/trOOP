// TR-OOP/src/main/java/service/TagihanService.java

package service;

import java.util.ArrayList;
import model.TagihanModel;
import model.Database;
import model.SessionManager; // <-- TAMBAHKAN
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TagihanService {

    private final ArrayList<TagihanModel> tagihanList = new ArrayList<>();

    public TagihanService() {
        // Kosongkan Konstruktor karena data akan diambil dari DB
    }

    public ArrayList<TagihanModel> getTagihan() {
        // Ambil NIM dari sesi
        String nimMahasiswa = SessionManager.getInstance().getCurrentUser().getId(); 
        
        ArrayList<TagihanModel> list = new ArrayList<>();
        String sql = "SELECT nama_tagihan, jumlah, is_lunas FROM tagihan WHERE nim_mhs = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, nimMahasiswa);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                String nama = rs.getString("nama_tagihan");
                double jumlah = rs.getDouble("jumlah");
                boolean lunas = rs.getBoolean("is_lunas");
                list.add(new TagihanModel(nama, jumlah, lunas));
            }
        } catch (SQLException e) {
            System.err.println("Error mengambil tagihan: " + e.getMessage());
        }
        return list;
    }

    public void updatePembayaran(String namaTagihan, boolean status) {
        String nimMahasiswa = SessionManager.getInstance().getCurrentUser().getId(); // Pastikan update hanya untuk user saat ini
        String sql = "UPDATE tagihan SET is_lunas = ? WHERE nama_tagihan = ? AND nim_mhs = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setBoolean(1, status);
            stmt.setString(2, namaTagihan);
            stmt.setString(3, nimMahasiswa);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error update pembayaran: " + e.getMessage());
        }
    }
}