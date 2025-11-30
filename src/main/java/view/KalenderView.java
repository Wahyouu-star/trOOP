package view;

import javax.swing.*;
import java.awt.*;

public class KalenderView extends JPanel {

    private final Color SIDEBAR_COLOR = new Color(0, 35, 120); 

    public KalenderView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initComponents();
    }
    
    private void initComponents() {
        
        // --- PANEL KONTEN UTAMA ---
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        
        // --- 1. HEADER KALENDER (Bulan dan Tahun) ---
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(Color.WHITE);
        JLabel monthLabel = new JLabel("NOVEMBER");
        monthLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        monthLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        headerPanel.add(monthLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // --- 2. GRID KALENDER ---
        JPanel gridPanel = new JPanel(new GridLayout(6, 7, 5, 5)); // 6 baris x 7 kolom
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        // Header Hari
        String[] days = {"MINGGU", "SENIN", "SELASA", "RABU", "KAMIS", "JUMAT", "SABTU"};
        for (String day : days) {
            JLabel dayLabel = new JLabel(day, SwingConstants.CENTER);
            dayLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
            dayLabel.setForeground(SIDEBAR_COLOR);
            gridPanel.add(dayLabel);
        }

        // Isi Tanggal (November dimulai dari Jumat, 1)
        int maxDay = 30;
        int currentDay = 1;
        
        // Sel Kosong Awal (November 1 adalah hari Jumat, butuh 5 sel kosong sebelum Jumat)
        for (int i = 0; i < 5; i++) { 
            gridPanel.add(new JLabel(""));
        }
        
        // Hari ke-1 (Jumat)
        for (int i = 0; i < maxDay; i++) {
            JButton dateButton = new JButton(String.valueOf(currentDay));
            dateButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
            dateButton.setFocusPainted(false);
            
            // Default styling
            dateButton.setForeground(Color.BLACK);
            dateButton.setBackground(new Color(245, 245, 245));

            // FIX: Highlight Tanggal 10 sesuai desain (Merah/Pink)
            if (currentDay == 10) {
                 dateButton.setBackground(new Color(255, 102, 102)); 
                 dateButton.setForeground(Color.WHITE); 
            }
            // FIX: Highlight Tanggal 30 (Hanya untuk konsistensi visual)
             if (currentDay == 30) {
                 dateButton.setBackground(new Color(220, 220, 220)); 
                 dateButton.setForeground(SIDEBAR_COLOR); 
            }
            
            gridPanel.add(dateButton);
            currentDay++;
        }
        
        mainPanel.add(gridPanel, BorderLayout.CENTER);
        
        // --- 3. FOOTER (Catatan Acara) ---
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        footerPanel.setBackground(Color.WHITE);
        JLabel noteLabel = new JLabel("10 November | Hari Pahlawan");
        noteLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        noteLabel.setForeground(Color.GRAY);
        noteLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        footerPanel.add(noteLabel);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);


        this.add(mainPanel, BorderLayout.CENTER);
    }
}