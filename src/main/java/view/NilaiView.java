package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

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
        
        // Data Placeholder (Berdasarkan desain figma)
        Object[][] rowData = {
            {"1", "TCS103", "MATEMATIKA DASAR", "3", "B", "9", "A", "2020-2021/1"},
            {"2", "TCS13F", "DASAR-DASAR PEMROGRAMAN", "3", "C", "6", "A", "2020-2021/1"},
            {"3", "TCS20A", "SISTEM BASIS DATA", "3", "A", "12", "B", "2021-2022/1"},
            {"4", "TCS22A", "PENGANTAR TEKNOLOGI INFORMASI", "3", "AB", "10.5", "B", "2021-2022/1"},
            {"5", "TCS1C1", "ALJABAR LINIER", "3", "A", "12", "C", "2022-2023/1"},
            {"6", "HUR41C", "PENDIDIKAN AGAMA KRISTEN", "2", "AB", "7", "C", "2022-2023/1"},
            {"7", "MUM1M", "PANCASILA", "2", "B", "6", "A", "2023-2024/1"},
            {"8", "MUM3F", "BAHASA INDONESIA", "2", "B", "6", "A", "2023-2024/1"},
            {"9", "TCS21C", "ALJABAR DAN MATRIKS", "3", "AB", "7", "B", "2024-2025/2"},
            {"10", "TCS22B", "STATISTIKA DAN PROBABILITAS", "3", "A", "8", "B", "2024-2025/2"},
            {"11", "TCS2FC", "TEORI BAHASA DAN OTOMATA", "3", "BC", "7.5", "A", "2024-2025/2"},
            {"12", "TCS34A", "JARINGAN KOMPUTER", "3", "C", "0", "B", "2024-2025/2"},
            {"13", "TCS35B", "INTEGRASI MAJALAH DAN KOMPUTER", "3", "A", "12", "B", "2024-2025/2"},
            {"14", "TCS3AB", "ALGORITMA DAN STRUKTUR DATA", "3", "A", "12", "B", "2024-2025/2"}
        };
        
        for (Object[] row : rowData) {
            model.addRow(row);
        }
        
        // --- SUMMARY ROW ---
        Object[] summaryRow = {"", "Total", "", "37", "", "106", "", ""};
        model.addRow(summaryRow);
        
        Object[] ipkRow = {"", "IPK", "", "3.41", "", "", "", ""};
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