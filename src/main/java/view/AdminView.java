package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import model.SessionManager;         
import view.LoginView;               
import controller.LoginController;   
import service.NotifikasiService;    
import model.KalenderModel;          
import service.KalenderService;      
import model.PenggunaModel;

public class AdminView extends JFrame {

    private JPanel mainContainerPanel;
    private JPanel dynamicContentPanel;
    private CardLayout cardLayout;

    private Map<String, JButton> menuButtons;

    private final Color SIDEBAR_COLOR = new Color(0, 35, 120);
    private final Color SIDEBAR_BG_COLOR = new Color(230, 235, 240);
    private final Color MAIN_BG_COLOR = new Color(248, 249, 250);
    private final String USER_INFO = "SUPER ADMIN";
    private final Color AGENDA_COLOR = new Color(204, 229, 255); // Warna Agenda

    public AdminView() {
        setTitle("Dashboard Super Admin");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(true);
        getContentPane().setBackground(new Color(245, 246, 247));

        initComponents();

        setSize(1100, 700);
        setLocationRelativeTo(null);

        initializeContent();
        setVisible(true);
    }

    public void initializeContent() {
        cardLayout = (CardLayout) dynamicContentPanel.getLayout();
        addInitialContent();
        switchCard("HOME");
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
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1,
                        mainPanelRadius, mainPanelRadius));
                g2.dispose();
            }

            {
                setOpaque(false);
            }
        };

        mainContainerPanel.setLayout(null);
        mainContainerPanel.setPreferredSize(new Dimension(1050, 620));

        fullFramePanel.add(mainContainerPanel);
        getContentPane().add(fullFramePanel, BorderLayout.CENTER);

        JPanel headerPanel = createHeaderPanel();
        headerPanel.setBounds(0, 0, 1050, 60);
        mainContainerPanel.add(headerPanel);

        JPanel sidebarWrapper = createSidebar();
        sidebarWrapper.setBounds(0, 60, 250, 560);
        mainContainerPanel.add(sidebarWrapper);

        dynamicContentPanel = new JPanel(new CardLayout());
        dynamicContentPanel.setBounds(260, 60, 780, 560);
        dynamicContentPanel.setBackground(Color.WHITE);
        mainContainerPanel.add(dynamicContentPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(SIDEBAR_COLOR);

        JLabel infoLabel = new JLabel(USER_INFO);
        infoLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setBounds(20, 15, 800, 30);
        panel.add(infoLabel);

        // --- NOTIFIKASI ICON DINAMIS ---
        NotifikasiService notifService = new NotifikasiService();
        String userRole = "Super Admin";
        
        int unreadCount = notifService.getUnreadCount(userRole, null); 
        
        JLabel notifIcon = new JLabel("<html><span style='color:white;'>" + 
                                       (unreadCount > 0 ? "🔔 (" + unreadCount + ")" : "🔔") + 
                                       "</span></html>"); 

        notifIcon.setFont(new Font("Segoe UI", Font.BOLD, 20));
        notifIcon.setBounds(1000, 15, 60, 30); 
        notifIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(notifIcon);
        
        notifIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                // Asumsi NotifikasiDetailView.java sudah ada
                List<model.NotifikasiModel> notifications = notifService.getNotifications(userRole, null);
                NotifikasiDetailView detailView = new NotifikasiDetailView(AdminView.this, notifications);
                detailView.showDialog();
            }
        });
        // --- END NOTIFIKASI ICON DINAMIS ---

        return panel;
    }

    private JPanel createSidebar() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(SIDEBAR_BG_COLOR);

        menuButtons = new HashMap<>();

        String[] menuItems = {
                "HOME",
                "ATUR TAGIHAN",
                "ATUR TAGIHAN MATKUL",
                "ATUR NOTIFIKASI",
                "ATUR KALENDER",
                "LOGOUT"
        };

        JLabel emptyHeader = new JLabel(" ");
        emptyHeader.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        panel.add(emptyHeader);

        for (String item : menuItems) {
            JButton button = new JButton(item);
            button.setAlignmentX(Component.LEFT_ALIGNMENT);
            button.setPreferredSize(new Dimension(250, 45));
            button.setMaximumSize(new Dimension(250, 45));
            button.setHorizontalAlignment(SwingConstants.LEFT);
            button.setBackground(SIDEBAR_BG_COLOR);
            button.setForeground(SIDEBAR_COLOR);
            button.setFont(new Font("Segoe UI", Font.BOLD, 15));
            button.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 5));
            button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            button.setFocusPainted(false);

            button.addActionListener(e -> {
                if (item.equals("LOGOUT")) {
                    SessionManager.getInstance().clearSession();
                    dispose();
                    
                    // Asumsi LoginView dan Controller ada
                    SwingUtilities.invokeLater(() -> {
                        LoginView loginView = new LoginView();
                        new controller.LoginController(loginView); 
                    });
                } else {
                    switchCard(item);
                }
            });

            menuButtons.put(item, button);
            panel.add(button);
        }
        highlightButton("HOME");
        return panel;
    }

    private void addInitialContent() {

        dynamicContentPanel.add(createHomeWelcomePanel(), "HOME");
        dynamicContentPanel.add(createAturTagihanPanel(), "ATUR TAGIHAN");
        dynamicContentPanel.add(createAturTagihanMatkulPanel(), "ATUR TAGIHAN MATKUL");
        dynamicContentPanel.add(createAturNotifikasiPanel(), "ATUR NOTIFIKASI");
        dynamicContentPanel.add(createAturKalenderPanel(), "ATUR KALENDER");
    }

    private JPanel createHomeWelcomePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(Color.WHITE);

        JLabel welcomeTitle = new JLabel("SELAMAT DATANG,", SwingConstants.CENTER);
        welcomeTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        welcomeTitle.setForeground(SIDEBAR_COLOR);

        JLabel nameLabel = new JLabel("SUPER ADMIN", SwingConstants.CENTER);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        nameLabel.setForeground(SIDEBAR_COLOR);

        JLabel systemTitle = new JLabel("DI SISTEM INFORMASI AKADEMIK SATYA WACANA",
                SwingConstants.CENTER);
        systemTitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        systemTitle.setForeground(SIDEBAR_COLOR);

        content.add(welcomeTitle);
        content.add(nameLabel);
        content.add(Box.createVerticalStrut(10));
        content.add(systemTitle);

        panel.add(content);
        return panel;
    }

    // ========================= ATUR TAGIHAN =========================
    private JPanel createAturTagihanPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel lbl = new JLabel("ATUR TAGIHAN", SwingConstants.LEFT);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lbl.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.add(lbl, BorderLayout.NORTH);

        String[] columnNames = {"NO", "Kode", "Nama Mahasiswa", "Input Nominal", "Kirim"};

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        model.addRow(new Object[]{"1", "672024082", "CHRISTIAN WAHYU SURYA ANGKASA", "Rp 0", "Kirim"});
        model.addRow(new Object[]{"2", "672024089", "ALEXANDER AXL DYANDRA SOEMARDHI PUTRA", "Rp 0", "Kirim"});
        model.addRow(new Object[]{"3", "672024092", "AHMAD HAFIZH", "Rp 0", "Kirim"});
        model.addRow(new Object[]{"4", "672024097", "ELGAN SEVI PRAMUDITA", "Rp 0", "Kirim"});

        JTable table = new JTable(model);

        table.setRowHeight(32);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setBackground(new Color(230, 230, 230));

        table.getColumn("Kirim").setCellRenderer(new ButtonRenderer());
        table.getColumn("Kirim").setCellEditor(new ButtonEditor(new JCheckBox(), table));

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    // ====================== ATUR TAGIHAN MATKUL =====================
    private JPanel createAturTagihanMatkulPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel lbl = new JLabel("ATUR TAGIHAN MATKUL", SwingConstants.LEFT);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lbl.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.add(lbl, BorderLayout.NORTH);

        String[] columnNames = {"NO", "Kode", "Nama Mahasiswa", "SKS", "Jumlah Tagihan", "Kirim"};

        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };

        model.addRow(new Object[]{"1", "672024082", "CHRISTIAN WAHYU SURYA ANGKASA", 14, hitungTagihan(14), "Kirim"});
        model.addRow(new Object[]{"2", "672024089", "ALEXANDER AXL DYANDRA SOEMARDHI PUTRA", 14, hitungTagihan(14), "Kirim"});
        model.addRow(new Object[]{"3", "672024092", "AHMAD HAFIZH", 14, hitungTagihan(14), "Kirim"});
        model.addRow(new Object[]{"4", "672024097", "ELGAN SEVI PRAMUDITA", 14, hitungTagihan(14), "Kirim"});

        JTable table = new JTable(model);
        table.setRowHeight(32);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setBackground(new Color(230, 230, 230));

        model.addTableModelListener(e -> {
            int row = e.getFirstRow();
            int col = e.getColumn();
            if (col == 3 && row >= 0) {
                try {
                    int sks = Integer.parseInt(model.getValueAt(row, 3).toString());
                    model.setValueAt(hitungTagihan(sks), row, 4);
                } catch (NumberFormatException ex) {
                    model.setValueAt(0, row, 3);
                    model.setValueAt(hitungTagihan(0), row, 4);
                }
            }
        });

        table.getColumn("Kirim").setCellRenderer(new ButtonRenderer());
        table.getColumn("Kirim").setCellEditor(new ButtonEditor(new JCheckBox(), table));

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    // ========================= ATUR NOTIFIKASI ======================
    private JPanel createAturNotifikasiPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel lbl = new JLabel("ATUR NOTIFIKASI", SwingConstants.LEFT);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lbl.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.add(lbl, BorderLayout.NORTH);

        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        textArea.setText("");

        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 2, true));
        panel.add(scroll, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(Color.WHITE);
        JButton btnLanjut = new JButton("Lanjut");
        btnLanjut.setBackground(new Color(0, 80, 170));
        btnLanjut.setForeground(Color.WHITE);
        btnLanjut.setFocusPainted(false);
        btnLanjut.setPreferredSize(new Dimension(120, 35));
        btnLanjut.setFont(new Font("Segoe UI", Font.BOLD, 14));

        btnLanjut.addActionListener(e -> {
            String isi = textArea.getText().trim();
            if (isi.isEmpty()) {
                JOptionPane.showMessageDialog(panel,
                        "Silakan isi informasi notifikasi terlebih dahulu.",
                        "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // --- LOGIC DATABASE BARU (GLOBAL NOTIFIKASI) ---
            NotifikasiService notifService = new NotifikasiService();
            
            String judul = ""; 
            String pengirimRole = "Super Admin";
            String targetRole = "ALL"; 
            String targetKelas = null; 
            
            if (notifService.addNotification(judul, isi, pengirimRole, targetRole, targetKelas)) {
                JOptionPane.showMessageDialog(panel,
                    "Notifikasi berhasil dikirim ke seluruh pengguna:\n\n" + isi,
                    "Notifikasi Terkirim", JOptionPane.INFORMATION_MESSAGE);
                textArea.setText("");
            } else {
                 JOptionPane.showMessageDialog(panel,
                    "Gagal menyimpan notifikasi ke database.",
                    "Error Database", JOptionPane.ERROR_MESSAGE);
            }
        });

        bottomPanel.add(btnLanjut);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

    // =========================== KALENDER HELPER METHODS (FIXED) ======================
    
    // Helper: Menampilkan detail agenda
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
    
    // Helper: Membuat grid kalender dengan highlight agenda
    private JPanel createCalendarGridForAdmin() {
        JPanel grid = new JPanel(new GridLayout(6, 7, 5, 5));
        grid.setBackground(Color.WHITE);
        
        KalenderService kalenderService = new KalenderService();
        // Admin melihat event yang ditargetkan ke "Super Admin" atau "ALL"
        List<KalenderModel> eventList = kalenderService.getEvents("Super Admin"); 
        Map<Integer, List<KalenderModel>> eventsByDay = new HashMap<>();
        for (KalenderModel event : eventList) {
            int day = event.getDayOfMonth();
            eventsByDay.computeIfAbsent(day, k -> new ArrayList<>()).add(event);
        }

        String[] days = {"MINGGU", "SENIN", "SELASA", "RABU", "KAMIS", "JUMAT", "SABTU"};
        for (String d : days) {
            JLabel dayLbl = new JLabel(d, SwingConstants.CENTER);
            dayLbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
            grid.add(dayLbl);
        }

        // Looping untuk 35 sel (6 baris x 7 kolom)
        for (int i = 1; i <= 35; i++) {
            if (i >= 5 && i <= 34) { // Asumsi November 1 (i=5) - November 30 (i=34)
                
                final int dayOfMonth = i - 4; // Menghitung hari bulan yang sebenarnya (1-30)
                JButton dayBtn = new JButton(String.valueOf(dayOfMonth));
                dayBtn.setFocusPainted(false);
                dayBtn.setBorder(BorderFactory.createEmptyBorder());
                
                final List<KalenderModel> todayEvents = eventsByDay.getOrDefault(dayOfMonth, new ArrayList<>());

                // Highlighting days with events
                if (!todayEvents.isEmpty()) {
                    dayBtn.setBackground(AGENDA_COLOR); 
                    dayBtn.setForeground(SIDEBAR_COLOR);
                    dayBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
                } else {
                    dayBtn.setBackground(new Color(230, 235, 240));
                }

                // Add listener for detail popup
                dayBtn.addActionListener(e -> {
                     showAgendaDetail(dayOfMonth, todayEvents);
                });

                grid.add(dayBtn);
            } else {
                grid.add(new JLabel("")); // Sel kosong
            }
        }
        
        return grid;
    }
    
    // Helper: Memanggil input dialog untuk menambah agenda
    private void showAgendaInputDialog() {
        JDialog dialog = new JDialog(this, "Tambah Agenda Baru", true); 
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.getContentPane().setBackground(Color.WHITE);
        
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 0, 15));
        
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
        descPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
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
                
                if (title.isEmpty() || dateString.isEmpty() || description.isEmpty()) {
                     JOptionPane.showMessageDialog(dialog, "Harap lengkapi semua kolom.", "Input Kurang", JOptionPane.WARNING_MESSAGE);
                     return;
                }
                
                if (!dateString.matches("\\d{4}-\\d{2}-\\d{2}")) {
                     JOptionPane.showMessageDialog(dialog, "Format tanggal harus YYYY-MM-DD.", "Kesalahan Input", JOptionPane.ERROR_MESSAGE);
                     return;
                }
                
                KalenderService kalenderService = new KalenderService();
                // ADMIN MENAMBAHKAN AGENDA UNTUK SELURUH USER ('ALL')
                KalenderModel newEvent = new KalenderModel(title, dateString, description, "Super Admin", "ALL");
                
                if (kalenderService.addEvent(newEvent)) {
                    JOptionPane.showMessageDialog(dialog, "Agenda Global berhasil ditambahkan.", "Agenda Tersimpan", JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                    refreshAturKalenderPanel(); 
                } else {
                    JOptionPane.showMessageDialog(dialog, "Gagal menyimpan agenda ke database.", "Error Database", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        buttonPanel.add(confirmButton);

        dialog.add(inputPanel, BorderLayout.NORTH);
        dialog.add(descPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.pack();
        dialog.setLocationRelativeTo(AdminView.this);
        dialog.setVisible(true);
    }
    
    // Helper: Merefresh panel kalender setelah penambahan
    private void refreshAturKalenderPanel() {
        // Ganti panel 'ATUR KALENDER' lama dengan yang baru
        // Asumsi ATUR KALENDER berada di Index 4
        dynamicContentPanel.remove(dynamicContentPanel.getComponent(4)); 
        dynamicContentPanel.add(createAturKalenderPanel(), "ATUR KALENDER", 4);
        cardLayout.show(dynamicContentPanel, "ATUR KALENDER");
        dynamicContentPanel.revalidate();
        dynamicContentPanel.repaint();
    }


    private JPanel createAturKalenderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel lbl = new JLabel("ATUR KALENDER", SwingConstants.LEFT);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lbl.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.add(lbl, BorderLayout.NORTH);

        JPanel calendarCard = new JPanel(new BorderLayout());
        calendarCard.setBackground(Color.WHITE);
        calendarCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 20, 20, 20),
                BorderFactory.createLineBorder(new Color(220, 220, 220), 2, true)
        ));

        JLabel monthLabel = new JLabel("NOVEMBER", SwingConstants.CENTER);
        monthLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        monthLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        calendarCard.add(monthLabel, BorderLayout.NORTH);

        JPanel grid = createCalendarGridForAdmin(); // Menggunakan fungsi grid yang diperbaiki
        calendarCard.add(grid, BorderLayout.CENTER);

        JLabel agendaLabel = new JLabel("Status: Kalender dikelola oleh KalenderService", SwingConstants.LEFT);
        agendaLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        agendaLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        calendarCard.add(agendaLabel, BorderLayout.SOUTH);
        panel.add(calendarCard, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setBackground(Color.WHITE);
        JButton btnTambahAgenda = new JButton("Tambahkan Agenda");
        btnTambahAgenda.setBackground(new Color(0, 80, 170));
        btnTambahAgenda.setForeground(Color.WHITE);
        btnTambahAgenda.setFocusPainted(false);
        btnTambahAgenda.setFont(new Font("Segoe UI", Font.BOLD, 13));

        btnTambahAgenda.addActionListener(e -> {
            showAgendaInputDialog();
        });

        bottomPanel.add(btnTambahAgenda);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

    private String hitungTagihan(int sks) {
        int nominal = sks * 250000;
        return "Rp " + String.format("%,d", nominal).replace(",", ".");
    }

    public void switchCard(String cardName) {
        if (cardLayout != null) {
            cardLayout.show(dynamicContentPanel, cardName);
            highlightButton(cardName);
        }
    }

    private void highlightButton(String activeName) {
        for (Map.Entry<String, JButton> entry : menuButtons.entrySet()) {
            if (entry.getKey().equals(activeName)) {
                entry.getValue().setBackground(Color.WHITE);
                entry.getValue().setForeground(Color.RED);
            } else {
                entry.getValue().setBackground(SIDEBAR_BG_COLOR);
                entry.getValue().setForeground(SIDEBAR_COLOR);
            }
        }
    }

    // ================= RENDER TOMBOL "Kirim" ======================
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setBackground(new Color(170, 0, 0));
            setForeground(Color.WHITE);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            setText("Kirim");
            return this;
        }
    }

    // ================= EDITOR TOMBOL "Kirim" ========================
    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private JTable table;

        public ButtonEditor(JCheckBox checkBox, JTable table) {
            super(checkBox);
            this.table = table;

            button = new JButton();
            button.setOpaque(true);
            button.setBackground(new Color(170, 0, 0));
            button.setForeground(Color.WHITE);

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int row = table.getSelectedRow();
                    if (row < 0) return;

                    String nama = table.getValueAt(row, 2).toString();

                    int colCount = table.getColumnCount();
                    int nominalCol = (colCount == 5) ? 3 : 4;
                    String jumlah = table.getValueAt(row, nominalCol).toString();

                    JOptionPane.showMessageDialog(null,
                            "Tagihan berhasil dikirim ke:\n" + nama +
                                    "\nNominal: " + jumlah,
                            "Tagihan Terkirim", JOptionPane.INFORMATION_MESSAGE);

                    fireEditingStopped();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            button.setText("Kirim");
            return button;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminView());
    }
}