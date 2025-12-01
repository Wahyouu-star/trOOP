package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import model.KalenderModel; // HARUS ADA
import model.SessionManager; // HARUS ADA
import service.KalenderService; // HARUS ADA

public class KalenderView extends JPanel {

    private final Color SIDEBAR_COLOR = new Color(0, 35, 120); 
    private final Color AGENDA_COLOR = new Color(204, 229, 255); // Warna untuk agenda dari DB

    public KalenderView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initComponents();
    }
    
    private void initComponents() {
        
        // 1. Ambil data event dari service
        String userRole = SessionManager.getInstance().isLoggedIn() ? 
                            SessionManager.getInstance().getCurrentUser().getRole() : "Mahasiswa";
        
        KalenderService kalenderService = new KalenderService();
        List<KalenderModel> eventList = kalenderService.getEvents(userRole);

        // Map event berdasarkan hari bulan
        Map<Integer, List<KalenderModel>> eventsByDay = new HashMap<>();
        for (KalenderModel event : eventList) {
            int day = event.getDayOfMonth();
            if (day > 0) {
                eventsByDay.computeIfAbsent(day, k -> new ArrayList<>()).add(event);
            }
        }
        
        
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
        JPanel gridPanel = createCalendarGrid(eventsByDay); 
        mainPanel.add(gridPanel, BorderLayout.CENTER);
        
        // --- 3. FOOTER (Catatan Acara) ---
        JPanel footerPanel = createFooterPanel(eventList);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);


        this.add(mainPanel, BorderLayout.CENTER);
    }
    
    private JPanel createCalendarGrid(Map<Integer, List<KalenderModel>> eventsByDay) {
        JPanel gridPanel = new JPanel(new GridLayout(6, 7, 5, 5));
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
            final int dayOfMonth = currentDay;
            final List<KalenderModel> todayEvents = eventsByDay.getOrDefault(dayOfMonth, new ArrayList<>());
            
            JButton dateButton = new JButton(String.valueOf(dayOfMonth));
            dateButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
            dateButton.setFocusPainted(false);
            
            Color defaultBg = new Color(245, 245, 245);
            Color agendaColor = defaultBg;
            Color agendaTextColor = Color.BLACK;

            // Logika Coloring: Cek apakah ada event dari DB
            if (!todayEvents.isEmpty()) {
                agendaColor = AGENDA_COLOR; 
                agendaTextColor = SIDEBAR_COLOR;
            }
            
            // Logic Coloring Bawaan (Contoh Hari Pahlawan)
            if (dayOfMonth == 10) {
                 agendaColor = new Color(255, 102, 102); 
                 agendaTextColor = Color.WHITE; 
            } else if (dayOfMonth == 30) {
                 agendaColor = new Color(220, 220, 220); 
                 agendaTextColor = SIDEBAR_COLOR; 
            }
            
            dateButton.setBackground(agendaColor);
            dateButton.setForeground(agendaTextColor);
            
            // Tambahkan Listener untuk Detail Agenda
            dateButton.addActionListener(e -> showAgendaDetail(dayOfMonth, todayEvents));
            
            gridPanel.add(dateButton);
            currentDay++;
        }
        return gridPanel;
    }
    
    private void showAgendaDetail(int day, List<KalenderModel> todayEvents) {
        if (todayEvents.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tidak ada agenda untuk tanggal " + day, "Detail Agenda", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder message = new StringBuilder("<html><b>Agenda Tanggal " + day + "</b><br>");
        
        for (KalenderModel event : todayEvents) {
            message.append("<hr style='border-top: 1px solid #ccc;'>")
                   .append("<b>Acara:</b> ").append(event.getNamaEvent()).append("<br>")
                   .append("<b>Deskripsi:</b> ").append(event.getDeskripsi()).append("<br>")
                   .append("<b>Pengirim:</b> ").append(event.getPengirimRole()).append("<br>")
                   .append("<b>Target:</b> ").append(event.getTargetRole()).append("<br>");
        }
        message.append("</html>");
        
        JOptionPane.showMessageDialog(this, message.toString(), "Detail Agenda", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private JPanel createFooterPanel(List<KalenderModel> eventList) {
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        footerPanel.setBackground(Color.WHITE);
        
        String noteText;

        // Cek apakah ada event dari DB untuk ditampilkan
        if (!eventList.isEmpty()) {
            StringBuilder sb = new StringBuilder("<html>");
            
            // Tambahkan event hari Pahlawan sebagai default event non-DB
            sb.append("10 November | Hari Pahlawan<br>");

            // List 3 event teratas dari DB
            int limit = 3;
            for (int i = 0; i < Math.min(limit, eventList.size()); i++) {
                KalenderModel event = eventList.get(i);
                sb.append("<b>").append(event.getNamaEvent()).append(" (")
                  .append(event.getTanggal()).append(")</b> - Oleh: ")
                  .append(event.getPengirimRole()).append("<br>");
            }
            sb.append("</html>");
            noteText = sb.toString();
        } else {
             // Jika tidak ada event dari DB, tampilkan default Hari Pahlawan
             noteText = "10 November | Hari Pahlawan";
        }
        
        JLabel noteLabel = new JLabel(noteText);
        noteLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        noteLabel.setForeground(Color.GRAY);
        noteLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        footerPanel.add(noteLabel);
        
        return footerPanel;
    }
}