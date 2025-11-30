package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

// JadwalView sekarang adalah JPanel, bukan JFrame
public class JadwalView extends JPanel { 

    public JadwalView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initComponents();
    }
    
    private void initComponents() {
        
        // 9 Kolom sesuai desain Figma
        String[] columnNames = {"NO", "KODE", "MATA KULIAH", "KODE DOSEN", "NAMA DOSEN", "HARI", "JAM", "RUANG", "KETERANGAN"};
        Object[][] data = {
            {"1", "TCO001", "KEWARGANEGARAAN", "31043", "THEOPHILUS LAURENS A.", "Senin", "11-13", "CKT002", "D hari / Tgl 13 dan 14"},
            {"2", "TC0002", "P. BERORIENTASI OBJEK X", "68909", "ASSISTEN 5", "Selasa", "10-12", "FKT005", "D hari"},
            {"3", "TCO009", "PENG. PEMROGRAMAN WEB H", "68910", "ASSISTEN 9", "Selasa", "08-10", "FT1453", "D hari"},
            {"4", "TC0006", "KECEKAPAN KOMPUTASI C", "68910", "ASSISTEN 10", "Kamis", "10-12", "FT1453", "D hari"},
            {"5", "TC1232", "PENG. PEMROGRAMAN BERORIENTASI OBJEK I", "68975", "PRATYAKSA OCSA N. SIAAN", "Senin", "13-16", "FT1453", "TT dan hari 13"},
            {"6", "TC223N", "PEMROGRAMAN KETH", "67810", "DANANG TRI WIDYANINGSIH", "Kamis", "13-16", "FT1467", "12 dan Hari 14"},
            {"7", "TCI016", "KECERDASAN BUATAN X", "67909", "CHRISTINE DEWI", "Rabu", "13-15", "FT1004", "12 dan Hari 14"},
            {"8", "TCI145", "DASAR PEMROGRAMAN", "67152", "WINAH SEMBIRING", "Rabu", "07-10", "FT1462", "TT dan hari 12"}
        };
        
        JTable table = new JTable(data, columnNames);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(25);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Penting untuk tabel lebar

        // Styling Header
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(230, 230, 230));

        // Pengaturan lebar kolom (Disini Kita Alokasikan Lebar Total sekitar 700px)
        table.getColumnModel().getColumn(0).setPreferredWidth(30);  // NO
        table.getColumnModel().getColumn(1).setPreferredWidth(80);  // KODE
        table.getColumnModel().getColumn(2).setPreferredWidth(160); // MATA KULIAH
        table.getColumnModel().getColumn(3).setPreferredWidth(80);  // KODE DOSEN
        table.getColumnModel().getColumn(4).setPreferredWidth(150); // NAMA DOSEN
        table.getColumnModel().getColumn(5).setPreferredWidth(60);  // HARI
        table.getColumnModel().getColumn(6).setPreferredWidth(80);  // JAM
        table.getColumnModel().getColumn(7).setPreferredWidth(60);  // RUANG
        table.getColumnModel().getColumn(8).setPreferredWidth(100); // KETERANGAN
        
        // Penengahan data NO dan SKS (Contoh: NO)
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); 

        JScrollPane scrollPane = new JScrollPane(table);
        
        // Tambahkan Judul
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(Color.WHITE);
        JLabel title = new JLabel("JADWAL KULIAH");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));
        header.add(title);
        
        this.add(header, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
    }
}