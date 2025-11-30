package view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class TagihanView extends JPanel {
    
    private final Color SIDEBAR_COLOR = new Color(0, 35, 120); // Biru Gelap
    private final Color HEADER_COLOR = new Color(0, 35, 120); 

    public TagihanView() {
        // Menggunakan BorderLayout sebagai layout utama panel
        setLayout(new BorderLayout()); 
        setBackground(Color.WHITE);
        initComponents();
    }
    
    private void initComponents() {
        
        // --- Header Title ---
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(Color.WHITE);
        JLabel title = new JLabel("TAGIHAN KEWAJIBAN PEMBAYARAN UANG KULIAH");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(SIDEBAR_COLOR);
        title.setBorder(BorderFactory.createEmptyBorder(15, 15, 0, 10)); 
        headerPanel.add(title);
        
        // Sub-Header Info
        JLabel subTitle = new JLabel("SEMESTER 1 TAHUN AJARAN 2025 - 2026");
        subTitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subTitle.setForeground(SIDEBAR_COLOR);
        subTitle.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 10));
        headerPanel.add(subTitle);
        
        // Atur headerPanel untuk menggunakan BoxLayout agar title dan subtitle tersusun vertikal
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));


        // --- Konten Utama (Simulasi Tabel Menggunakan GridBagLayout) ---
        JPanel contentPanel = createTagihanTableContent();
        
        this.add(headerPanel, BorderLayout.NORTH);
        this.add(new JScrollPane(contentPanel), BorderLayout.CENTER);
    }
    
    private JPanel createTagihanTableContent() {
        // Menggunakan GridBagLayout untuk kontrol tata letak yang sangat detail (simulasi baris dan kolom)
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 10, 4, 10); // Padding antar sel
        
        int row = 0;

        // --- SECTION 1: RINCIAN BIAYA (COST DETAILS) ---
        
        // Baris Header Kolom (2 kolom)
        gbc.gridy = row++;
        gbc.gridx = 0;
        gbc.weightx = 0.6;
        gbc.gridwidth = 1;
        JLabel headerDesc = createHeaderLabel("KOMPONEN");
        panel.add(headerDesc, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.4;
        JLabel headerValue = createHeaderLabel("BIAYA (Rp)");
        panel.add(headerValue, gbc);

        // Sub Header: Rincian Biaya
        gbc.gridy = row++;
        gbc.gridx = 0;
        gbc.gridwidth = 2; // Spanning 2 columns
        JLabel subHeader1 = createSectionHeaderLabel("Rincian Biaya", HEADER_COLOR);
        panel.add(subHeader1, gbc);

        // Data Row 1: Uang Kuliah
        row = addDataRow(panel, row, "Uang Kuliah", "4.500.000");

        // Data Row 2: Denda
        row = addDataRow(panel, row, "Denda", "0");
        
        // Data Row 3: SPP/BPP
        row = addDataRow(panel, row, "SPP/BPP", "1.500.000");

        // Data Row 4: Layanan Kemahasiswaan
        row = addDataRow(panel, row, "Layanan Kemahasiswaan", "324.000");
        
        // --- ROW TOTAL ---
        row = addDataRow(panel, row, "Total Kurang", "6.324.000", Font.BOLD);
        
        // --- ROW TERBAYAR ---
        row = addDataRow(panel, row, "Terbayar", "6.324.000", Font.BOLD);
        
        // --- ROW YANG HARUS DIBAYAR (HIGHLIGHTED) ---
        row = addDataRow(panel, row, "Yang Harus Dibayar", "0", Font.BOLD, Color.RED);
        
        
        // --- SECTION 2: JATUH TEMPO (DEADLINES) ---
        
        gbc.insets = new Insets(15, 10, 4, 10); // Lebih banyak spasi di atas
        gbc.gridy = row++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JLabel subHeader2 = createSectionHeaderLabel("Jatuh Tempo", HEADER_COLOR);
        panel.add(subHeader2, gbc);
        
        gbc.insets = new Insets(4, 10, 4, 10); // Kembalikan spasi normal
        
        // Data Row 5: Jatuh Tempo Pembayaran
        row = addDataRow(panel, row, "Jatuh Tempo Pembayaran", "08 Agustus 2025");
        
        // Data Row 6: Jatuh Tempo Pelunasan
        row = addDataRow(panel, row, "Jatuh Tempo Pelunasan", "07 November 2025");
        
        // Data Row 7: Yang Harus Dibayar (Duplicate from Section 1, for deadline context)
        row = addDataRow(panel, row, "Yang Harus Dibayar", "0", Font.BOLD, Color.RED);

        // Row Keterangan
        row = addDataRow(panel, row, "Bukan Termasuk Denda Keterlambatan", "");


        // Filler untuk memastikan konten rata atas
        gbc.gridy = row;
        gbc.weighty = 1.0; 
        panel.add(new JLabel(""), gbc);
        
        return panel;
    }
    
    // --- Helper Methods for GridBagLayout ---
    
    private JLabel createHeaderLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(HEADER_COLOR);
        label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY)); // Garis bawah tipis
        return label;
    }
    
    private JLabel createSectionHeaderLabel(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(color);
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        return label;
    }
    
    private int addDataRow(JPanel panel, int row, String description, String value) {
        return addDataRow(panel, row, description, value, Font.PLAIN, Color.BLACK);
    }
    
    private int addDataRow(JPanel panel, int row, String description, String value, int fontStyle) {
        return addDataRow(panel, row, description, value, fontStyle, Color.BLACK);
    }

    private int addDataRow(JPanel panel, int row, String description, String value, int fontStyle, Color foreground) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 10, 4, 10); 
        gbc.gridy = row;

        // Kolom Deskripsi (Rata Kiri)
        gbc.gridx = 0;
        gbc.weightx = 0.6;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Segoe UI", fontStyle, 12));
        descLabel.setForeground(foreground);
        panel.add(descLabel, gbc);

        // Kolom Nilai (Rata Kanan)
        gbc.gridx = 1;
        gbc.weightx = 0.4;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", fontStyle, 12));
        valueLabel.setForeground(foreground);
        panel.add(valueLabel, gbc);

        return row + 1;
    }
}