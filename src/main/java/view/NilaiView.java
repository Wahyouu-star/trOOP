// TR-OOP/src/main/java/view/NilaiView.java

package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList; 
import model.SessionManager; 
import service.NilaiService; // Import service utama

public class NilaiView extends JPanel { 

    private final Color HEADER_COLOR = new Color(0, 35, 120); 

    public NilaiView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initComponents();
    }
    
    private void initComponents() {
        
        // --- DATA TABEL ---
        String[] columnNames = {"No", "Kode", "Matakuliah", "SKS", "Nilai", "AK", "Semester", "Tahun Ajaran"};
        
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Transkrip tidak bisa diedit
            }
        };
        
        // --- MENGAMBIL DATA DARI SERVICE (siap DB) ---
        NilaiService nilaiService = new NilaiService();
        // Cek jika ada user login, jika tidak, gunakan dummy ID
        String nim = SessionManager.getInstance().isLoggedIn() ? 
                     SessionManager.getInstance().getCurrentUser().getId() : "DUMMY";
                     
        // ERROR di sini: NilaiService.NilaiDetail di baris 35
        ArrayList<NilaiService.NilaiDetail> transkrip = nilaiService.getTranskrip(nim);
        
        // Mengisi model tabel dengan data dari Service
        for (NilaiService.NilaiDetail rowDetail : transkrip) {
            model.addRow(rowDetail.toRowData());
        }
        
        // --- SUMMARY ROW ---
        // Menggunakan method dari service untuk summary
        Object[] summaryRow = nilaiService.getSummaryRow(transkrip);
        model.addRow(summaryRow);
        
        Object[] ipkRow = nilaiService.getIPKRow(transkrip);
        model.addRow(ipkRow);

        JTable table = new JTable(model);
        
        // --- TABLE STYLING ---
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(25);
        
        // Styling Header
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(230, 230, 230));

        // Pengaturan lebar kolom
        table.getColumnModel().getColumn(0).setPreferredWidth(30);  // No
        table.getColumnModel().getColumn(1).setPreferredWidth(60);  // Kode
        table.getColumnModel().getColumn(2).setPreferredWidth(250); // Matakuliah
        table.getColumnModel().getColumn(3).setPreferredWidth(50);  // SKS
        table.getColumnModel().getColumn(4).setPreferredWidth(50);  // Nilai
        table.getColumnModel().getColumn(5).setPreferredWidth(50);  // AK
        table.getColumnModel().getColumn(6).setPreferredWidth(80);  // Semester
        table.getColumnModel().getColumn(7).setPreferredWidth(100); // Tahun Ajaran
        
        // Rata Tengah (SKS, Nilai, AK)
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer); // SKS
        table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer); // Nilai
        table.getColumnModel().getColumn(5).setCellRenderer(centerRenderer); // AK
        
        // Judul
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(Color.WHITE);
        JLabel title = new JLabel("TRANSKRIP NILAI AKADEMIK");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(HEADER_COLOR);
        title.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));
        header.add(title);
        
        this.add(header, BorderLayout.NORTH);
        this.add(new JScrollPane(table), BorderLayout.CENTER);
    }
}