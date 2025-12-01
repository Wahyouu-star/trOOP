package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class DosenJadwalView extends JPanel { 

    private final Color HEADER_COLOR = new Color(0, 35, 120); 

    public DosenJadwalView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initComponents();
    }
    
    private void initComponents() {
        
        // 8 Kolom sesuai desain Dosen
        String[] columnNames = {"NO", "MATA KULIAH", "HARI", "JAM", "RUANG", "KETERANGAN", "JML MHS", "AKSI"};
        Object[][] data = {
            {"1", "ALJABAR LINIER", "Senin", "11-13", "CKT002", "D hari", "30", "Lihat Absen"},
            {"2", "PENG. PEMROGRAMAN WEB H", "Selasa", "08-10", "FT1453", "D hari", "35", "Lihat Absen"},
            {"3", "KECERDASAN BUATAN X", "Rabu", "13-15", "FT1004", "12 dan Hari 14", "25", "Lihat Absen"}
        };
        
        JTable table = new JTable(data, columnNames);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(25);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Styling Header
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(230, 230, 230));

        // Pengaturan lebar kolom
        table.getColumnModel().getColumn(0).setPreferredWidth(30);  
        table.getColumnModel().getColumn(1).setPreferredWidth(200); 
        table.getColumnModel().getColumn(2).setPreferredWidth(60);  
        table.getColumnModel().getColumn(3).setPreferredWidth(80);  
        table.getColumnModel().getColumn(4).setPreferredWidth(60); 
        table.getColumnModel().getColumn(5).setPreferredWidth(120); 
        table.getColumnModel().getColumn(6).setPreferredWidth(80); 
        table.getColumnModel().getColumn(7).setPreferredWidth(100); // AKSI
        
        // Penengahan data NO dan JML MHS
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); 
        table.getColumnModel().getColumn(6).setCellRenderer(centerRenderer); 
        
        // Tambahkan Button Renderer/Editor jika perlu tombol interaktif di kolom AKSI
        // table.getColumn("AKSI").setCellRenderer(new ButtonRenderer());
        // table.getColumn("AKSI").setCellEditor(new ButtonEditor(new JTextField(), table));


        JScrollPane scrollPane = new JScrollPane(table);
        
        // Tambahkan Judul
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(Color.WHITE);
        JLabel title = new JLabel("JADWAL MENGAJAR");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(HEADER_COLOR);
        title.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));
        header.add(title);
        
        this.add(header, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
    }
}