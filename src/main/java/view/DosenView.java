package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import model.SessionManager; 
import model.PenggunaModel; 
import model.NotifikasiModel;
import model.KalenderModel;
import service.NotifikasiService; 
import service.KalenderService; // BARU

public class DosenView extends JFrame {

    private JPanel mainContainerPanel; 
    private JPanel dynamicContentPanel; 
    private CardLayout cardLayout;

    private Map<String, JButton> menuButtons;
    
    // HAPUS AgendaDetail internal dan agendaMap, diganti oleh KalenderService

    private final Color SIDEBAR_COLOR = new Color(0, 35, 120); 
    private final Color SIDEBAR_BG_COLOR = new Color(230, 235, 240); 
    private final Color MAIN_BG_COLOR = new Color(248, 249, 250);
    private final Color AGENDA_COLOR = new Color(204, 229, 255); 

    private String USER_NIM_NAME;
    private final String SEMESTER_SKS = "SEMESTER 1 TA 2025 - 2026";


    public DosenView() {
        setTitle("Dashboard Dosen");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(true);
        getContentPane().setBackground(new Color(245, 246, 247));
        
        // --- AMBIL DATA USER DARI SESI ---
        PenggunaModel user = SessionManager.getInstance().getCurrentUser();
        if (user != null) {
            this.USER_NIM_NAME = user.getId() + " - " + user.getNamaLengkap() + " (FAKULTAS TEKNOLOGI INFORMASI - TEKNIK INFORMATIKA)";
        } else {
            this.USER_NIM_NAME = "Loading User Data...";
        }
        // --------------------------------

        initComponents();

        setSize(1300, 650);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    // Dipanggil oleh DosenController
    public void initializeContent() {
        cardLayout = (CardLayout) dynamicContentPanel.getLayout();
        addInitialContent(); 
    }

    private void initComponents() {
        
        JPanel fullFramePanel = new JPanel(new GridBagLayout()); 
        fullFramePanel.setBackground(new Color(245, 246, 247)); 
        
        int mainPanelRadius = 40;
        
        mainContainerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(MAIN_BG_COLOR);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, mainPanelRadius, mainPanelRadius));
                g2.dispose();
            }
            { setOpaque(false); } 
        };
        
        mainContainerPanel.setLayout(null); 
        mainContainerPanel.setPreferredSize(new Dimension(1000, 550));
        
        fullFramePanel.add(mainContainerPanel);
        getContentPane().add(fullFramePanel, BorderLayout.CENTER); 
        
        // HEADER
        JPanel headerPanel = createHeaderPanel();
        headerPanel.setBounds(0, 0, 1000, 60);
        mainContainerPanel.add(headerPanel);
        
        // SIDEBAR
        JPanel sidebarWrapper = createSidebar();
        sidebarWrapper.setBounds(0, 60, 250, 470); 
        mainContainerPanel.add(sidebarWrapper);

        // CONTENT AREA
        dynamicContentPanel = new JPanel(new CardLayout()); 
        dynamicContentPanel.setBounds(260, 60, 720, 470); 
        dynamicContentPanel.setBackground(Color.WHITE);
        mainContainerPanel.add(dynamicContentPanel);
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(SIDEBAR_COLOR); 
        
        JLabel infoLabel1 = new JLabel(USER_NIM_NAME);
        infoLabel1.setFont(new Font("Segoe UI", Font.BOLD, 12));
        infoLabel1.setForeground(Color.WHITE);
        infoLabel1.setBounds(20, 10, 800, 15);
        panel.add(infoLabel1);
        
        JLabel infoLabel2 = new JLabel(SEMESTER_SKS);
        infoLabel2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        infoLabel2.setForeground(Color.WHITE);
        infoLabel2.setBounds(20, 30, 800, 15);
        panel.add(infoLabel2);
        
        // --- 🔔 NOTIFIKASI ICON DINAMIS ---
        PenggunaModel user = SessionManager.getInstance().getCurrentUser();
        NotifikasiService notifService = new NotifikasiService();
        String userRole = user != null ? user.getRole() : "Dosen";
        
        String userClass = null; 
        
        List<NotifikasiModel> notifications = notifService.getNotifications(userRole, userClass);
        int unreadCount = notifications.size();
        
        JLabel notifIcon = new JLabel("<html><span style='color:white;'>" + 
                                       (unreadCount > 0 ? "🔔 (" + unreadCount + ")" : "🔔") + 
                                       "</span></html>"); 
        
        notifIcon.setFont(new Font("Segoe UI", Font.BOLD, 18));
        notifIcon.setBounds(920, 15, 80, 30); 
        notifIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(notifIcon);
        
        notifIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                // NotifikasiDetailView harus dibuat di file NotifikasiDetailView.java
                NotifikasiDetailView detailView = new NotifikasiDetailView(DosenView.this, notifications);
                detailView.showDialog();
            }
        });
        // --- END NOTIFIKASI ICON DINAMIS ---
        
        return panel;
    }

    private JPanel createSidebar() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(230, 235, 240)); 
        
        menuButtons = new HashMap<>();
        
        String[] menuItems = {"HOME", "JADWAL MENGAJAR", "ABSENSI MAHASISWA", "ATUR NOTIFIKASI", "ATUR KALENDER", "KALENDER", "LOGOUT"};
        
        JLabel emptyHeader = new JLabel(" ");
        emptyHeader.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 10)); 
        panel.add(emptyHeader);
        
        for (String item : menuItems) {
            JButton button = new JButton(item);
            button.setAlignmentX(Component.LEFT_ALIGNMENT);
            button.setPreferredSize(new Dimension(250, 45)); 
            button.setMaximumSize(new Dimension(250, 45));
            button.setHorizontalAlignment(SwingConstants.LEFT);
            button.setBackground(new Color(230, 235, 240));
            button.setForeground(SIDEBAR_COLOR);
            button.setFont(new Font("Segoe UI", Font.BOLD, 14)); 
            button.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 5)); 
            button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            button.setFocusPainted(false);
            
            // Controller akan menambahkan listener
            menuButtons.put(item, button);
            panel.add(button);
        }
        
        highlightButton("HOME");
        return panel;
    }
    
    // --- KALENDER LOGIC BARU (SERVICE BASED) ---
    
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
    
    private JPanel createCalendarGrid() {
        JPanel gridPanel = new JPanel(new GridLayout(6, 7, 5, 5)); 
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        // Ambil data dari service
        String userRole = "Dosen";
        KalenderService kalenderService = new KalenderService();
        List<KalenderModel> eventList = kalenderService.getEvents(userRole);

        Map<Integer, List<KalenderModel>> eventsByDay = new HashMap<>();
        for (KalenderModel event : eventList) {
            int day = event.getDayOfMonth();
            eventsByDay.computeIfAbsent(day, k -> new ArrayList<>()).add(event);
        }
        
        String[] days = {"MINGGU", "SENIN", "SELASA", "RABU", "KAMIS", "JUMAT", "SABTU"};
        for (String day : days) {
            JLabel dayLabel = new JLabel(day, SwingConstants.CENTER);
            dayLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
            dayLabel.setForeground(SIDEBAR_COLOR);
            gridPanel.add(dayLabel);
        }

        int maxDay = 30;
        int currentDay = 1;
        
        for (int i = 0; i < 5; i++) { 
            gridPanel.add(new JLabel(""));
        }
        
        for (int i = 0; i < maxDay; i++) {
            final int dayOfMonth = currentDay; 
            JButton dateButton = new JButton(String.valueOf(dayOfMonth));
            dateButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
            dateButton.setFocusPainted(false);
            
            Color defaultBg = new Color(245, 245, 245);
            Color agendaColor = defaultBg;
            Color agendaTextColor = Color.BLACK;

            List<KalenderModel> todayEvents = eventsByDay.getOrDefault(dayOfMonth, new ArrayList<>());

            if (!todayEvents.isEmpty()) {
                agendaColor = AGENDA_COLOR;
                agendaTextColor = SIDEBAR_COLOR;
                dateButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
            } 
            
            // Logic Coloring Bawaan (Contoh UTS/Hari Pahlawan)
            if (dayOfMonth == 10) { 
                 agendaColor = new Color(255, 102, 102); 
                 agendaTextColor = Color.WHITE; 
            } else if (dayOfMonth == 30) {
                 agendaColor = new Color(220, 220, 220); 
                 agendaTextColor = SIDEBAR_COLOR; 
            }
            
            dateButton.setBackground(agendaColor);
            dateButton.setForeground(agendaTextColor);
            
            // Tambahkan listener dengan passing data event hari ini
            dateButton.addActionListener(e -> showAgendaDetail(dayOfMonth, todayEvents)); 
            
            gridPanel.add(dateButton);
            currentDay++;
        }
        return gridPanel;
    }
    
    private void refreshAturKalenderPanel() {
        dynamicContentPanel.remove(dynamicContentPanel.getComponent(dynamicContentPanel.getComponentCount() - 1));
        dynamicContentPanel.remove(dynamicContentPanel.getComponent(dynamicContentPanel.getComponentCount() - 1));
        dynamicContentPanel.remove(dynamicContentPanel.getComponent(dynamicContentPanel.getComponentCount() - 1));
        
        dynamicContentPanel.add(createAturKalenderPanel(), "ATUR KALENDER");
        dynamicContentPanel.add(new KalenderView(), "KALENDER");
        cardLayout.show(dynamicContentPanel, "ATUR KALENDER");
        dynamicContentPanel.revalidate();
        dynamicContentPanel.repaint();
    }
    
    private void showAgendaInputDialog() {
        JDialog dialog = new JDialog(this, "Tambah Agenda Baru", true); 
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.getContentPane().setBackground(Color.WHITE);
        
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JTextField titleField = new JTextField(20);
        inputPanel.add(new JLabel("Nama Acara:"));
        inputPanel.add(titleField);
        
        JTextField dateField = new JTextField("YYYY-MM-DD");
        inputPanel.add(new JLabel("Tanggal (YYYY-MM-DD):"));
        inputPanel.add(dateField);
        
        JTextArea descArea = new JTextArea(3, 20);
        descArea.setLineWrap(true);
        JScrollPane descScrollPane = new JScrollPane(descArea);
        
        JPanel descPanel = new JPanel(new BorderLayout());
        descPanel.add(new JLabel("Deskripsi:"), BorderLayout.NORTH);
        descPanel.add(descScrollPane, BorderLayout.CENTER);
        
        JButton confirmButton = new JButton("Konfirmasi");
        confirmButton.setBackground(SIDEBAR_COLOR);
        confirmButton.setForeground(Color.WHITE);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText().trim();
                String dateString = dateField.getText().trim();
                String description = descArea.getText().trim();
                
                if (title.isEmpty() || dateString.isEmpty()) {
                     JOptionPane.showMessageDialog(dialog, "Nama Acara dan Tanggal wajib diisi.", "Input Kurang", JOptionPane.WARNING_MESSAGE);
                     return;
                }
                
                if (!dateString.matches("\\d{4}-\\d{2}-\\d{2}")) {
                     JOptionPane.showMessageDialog(dialog, "Format tanggal harus YYYY-MM-DD.", "Kesalahan Input", JOptionPane.ERROR_MESSAGE);
                     return;
                }

                KalenderService kalenderService = new KalenderService();
                KalenderModel newEvent = new KalenderModel(title, dateString, description, "Dosen", "Mahasiswa");
                
                if (kalenderService.addEvent(newEvent)) {
                    JOptionPane.showMessageDialog(dialog, "Agenda berhasil ditambahkan untuk Mahasiswa.", "Agenda Tersimpan", JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                    refreshAturKalenderPanel(); 
                } else {
                    JOptionPane.showMessageDialog(dialog, "Gagal menyimpan agenda ke database.", "Error Database", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(confirmButton);

        dialog.add(inputPanel, BorderLayout.NORTH);
        dialog.add(descPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.pack();
        dialog.setLocationRelativeTo(DosenView.this);
        dialog.setVisible(true);
    }
    
    private JPanel createAturKalenderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(Color.WHITE);
        JLabel monthLabel = new JLabel("ATUR KALENDER AGENDA");
        monthLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        monthLabel.setForeground(SIDEBAR_COLOR);
        monthLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        headerPanel.add(monthLabel);
        panel.add(headerPanel, BorderLayout.NORTH);

        JPanel gridPanel = createCalendarGrid(); 
        panel.add(gridPanel, BorderLayout.CENTER);
        
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(Color.WHITE);
        
        JButton tambahAgendaButton = new JButton("Tambahkan Agenda");
        tambahAgendaButton.setBackground(SIDEBAR_COLOR);
        tambahAgendaButton.setForeground(Color.WHITE);
        tambahAgendaButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tambahAgendaButton.setFocusPainted(false);
        
        tambahAgendaButton.addActionListener(e -> showAgendaInputDialog()); 
        
        footerPanel.add(tambahAgendaButton);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        panel.add(footerPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createDosenHomePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); 
        gbc.gridx = 0;

        JLabel welcomeLabel = new JLabel("SELAMAT DATANG,");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        welcomeLabel.setForeground(SIDEBAR_COLOR);
        gbc.gridy = 0;
        panel.add(welcomeLabel, gbc);

        JLabel nameLabel = new JLabel("PRATYAKSA OCSA N. SIAAN");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 26)); 
        nameLabel.setForeground(SIDEBAR_COLOR);
        gbc.gridy = 1;
        panel.add(nameLabel, gbc);

        JLabel systemInfoLabel = new JLabel("DI SISTEM INFORMASI AKADEMIK SATYA WACANA");
        systemInfoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        systemInfoLabel.setForeground(Color.GRAY);
        gbc.gridy = 2;
        panel.add(systemInfoLabel, gbc);
        
        return panel;
    }
    
    private JPanel createDosenAbsensiPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        String[] columnNames = {"NO", "KODE", "MATA KULIAH", "HARI", "JAM", "RUANG", "AKSI"};
        Object[][] data = {
            {"1", "TCS121A", "PEMROGRAMAN BERORIENTASI OBJEK A", "Selasa", "11-13", "FT1455", "Ambil Absen"},
            {"2", "TCS121B", "PEMROGRAMAN BERORIENTASI OBJEK B", "Rabu", "10-12", "FT1455", "Ambil Absen"},
            {"3", "TCS121C", "PEMROGRAMAN BERORIENTASI OBJEK C", "Selasa", "08-10", "FT1453", "Ambil Absen"},
            {"4", "TCS121D", "PEMROGRAMAN BERORIENTASI OBJEK D", "Kamis", "10-12", "FT1453", "Ambil Absen"},
            {"5", "TCS121E", "PEMROGRAMAN BERORIENTASI OBJEK E", "Senin", "13-16", "FT1455", "Ambil Absen"},
        };
        
        JTable table = new JTable(data, columnNames);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(25);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(230, 230, 230));

        table.getColumnModel().getColumn(0).setPreferredWidth(40);  
        table.getColumnModel().getColumn(1).setPreferredWidth(90);  
        table.getColumnModel().getColumn(2).setPreferredWidth(270); 
        table.getColumnModel().getColumn(3).setPreferredWidth(80);  
        table.getColumnModel().getColumn(4).setPreferredWidth(70);  
        table.getColumnModel().getColumn(5).setPreferredWidth(80);  
        table.getColumnModel().getColumn(6).setPreferredWidth(130); 
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); 
        
        JScrollPane scrollPane = new JScrollPane(table);
        
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(Color.WHITE);
        JLabel title = new JLabel("DAFTAR MATA KULIAH UNTUK ABSENSI");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(SIDEBAR_COLOR);
        title.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));
        header.add(title);
        
        panel.add(header, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createAturNotifikasiPanel() {
        JPanel panel = new JPanel(new GridBagLayout()); 
        panel.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 
        gbc.anchor = GridBagConstraints.WEST;
        
        JLabel title = new JLabel("ATUR NOTIFIKASI");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(SIDEBAR_COLOR);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3; 
        panel.add(title, gbc);
        
        JLabel infoLabel = new JLabel("Masukkan informasi:");
        infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        panel.add(infoLabel, gbc);

        JTextArea messageArea = new JTextArea(10, 60); 
        messageArea.setWrapStyleWord(true);
        messageArea.setLineWrap(true);
        messageArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        messageArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        
        JScrollPane scrollPane = new JScrollPane(messageArea);
        scrollPane.setPreferredSize(new Dimension(650, 200)); 
        
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        panel.add(scrollPane, gbc);
        
        String[] classes = {"Semua Kelas", "TCS121A", "TCS121B", 
                            "TCS121C", "TCS121D", 
                            "TCS121E", "TCS121F", 
                            "TCS121G", "TCS121H", 
                            "TCS121I", "TCS121J"};
                            
        JComboBox<String> classSelector = new JComboBox<>(classes);
        classSelector.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        classSelector.setPreferredSize(new Dimension(300, 30));

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0; 
        gbc.insets = new Insets(20, 10, 10, 10);
        panel.add(classSelector, gbc);
        
        JButton lanjutButton = new JButton("Lanjut");
        lanjutButton.setBackground(SIDEBAR_COLOR);
        lanjutButton.setForeground(Color.WHITE);
        lanjutButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        lanjutButton.addActionListener(e -> {
            String isi = messageArea.getText().trim();
            String selectedClass = (String) classSelector.getSelectedItem();
            
            if (isi.isEmpty() || selectedClass == null || selectedClass.equals("Semua Kelas")) {
                JOptionPane.showMessageDialog(panel,
                    "Silakan isi pesan dan pilih kelas spesifik yang dituju.",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            NotifikasiService notifService = new NotifikasiService();
            
            String judul = ""; 
            String pengirimRole = "Dosen";
            String targetRole = "Mahasiswa"; 
            String targetKelas = selectedClass; 
            
            if (notifService.addNotification(judul, isi, pengirimRole, targetRole, targetKelas)) {
                JOptionPane.showMessageDialog(panel,
                    "Notifikasi berhasil dikirim ke Mahasiswa di kelas " + targetKelas + ":\n\n" + isi,
                    "Notifikasi Terkirim", JOptionPane.INFORMATION_MESSAGE);
                messageArea.setText("");
            } else {
                 JOptionPane.showMessageDialog(panel,
                    "Gagal menyimpan notifikasi ke database.",
                    "Error Database", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.gridx = 2;
        gbc.weightx = 0;
        lanjutButton.setPreferredSize(new Dimension(150, 30));
        panel.add(lanjutButton, gbc);
        
        gbc.gridy = 4;
        gbc.weighty = 1.0; 
        panel.add(new JLabel(""), gbc);
        
        return panel;
    }
    
    private void addInitialContent() {
        
        dynamicContentPanel.add(createDosenHomePanel(), "HOME"); 
        // Asumsi DosenJadwalView, KalenderView sudah ada
        dynamicContentPanel.add(new DosenJadwalView(), "JADWAL MENGAJAR"); 
        dynamicContentPanel.add(createDosenAbsensiPanel(), "ABSENSI MAHASISWA"); 
        dynamicContentPanel.add(createAturNotifikasiPanel(), "ATUR NOTIFIKASI");
        
        dynamicContentPanel.add(createAturKalenderPanel(), "ATUR KALENDER");
        
        dynamicContentPanel.add(new KalenderView(), "KALENDER");
        
        cardLayout.show(dynamicContentPanel, "HOME"); 
    }
    
    public void switchCard(String cardName) {
        cardLayout.show(dynamicContentPanel, cardName);
        highlightButton(cardName);
    }
    
    private void highlightButton(String activeName) {
        Color sidebarBg = new Color(230, 235, 240);
        for (Map.Entry<String, JButton> entry : menuButtons.entrySet()) {
            if (entry.getKey().equals(activeName)) {
                entry.getValue().setBackground(Color.WHITE); 
                entry.getValue().setForeground(new Color(170, 0, 0)); 
            } else {
                entry.getValue().setBackground(sidebarBg); 
                entry.getValue().setForeground(SIDEBAR_COLOR);
            }
        }
    }
    
    public Map<String, JButton> getMenuButtons() {
        return menuButtons;
    }
}