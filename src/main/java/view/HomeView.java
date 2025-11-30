package view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.HashMap;
import java.util.Map;

public class HomeView extends JFrame {

    private JPanel mainContainerPanel; 
    private JPanel dynamicContentPanel; 
    private CardLayout cardLayout;

    private Map<String, JButton> menuButtons;
    
    private final Color SIDEBAR_COLOR = new Color(0, 35, 120); 
    private final Color SIDEBAR_BG_COLOR = new Color(230, 235, 240); 
    private final Color MAIN_BG_COLOR = new Color(248, 249, 250);

    private final String USER_NIM_NAME = "672024097 - ELGAN SEVI PRAMUDITA (FAKULTAS TEKNOLOGI INFORMASI - TEKNIK INFORMATIKA)";
    private final String SEMESTER_SKS = "SEMESTER 1 | TA 2025 - 2026 | BEBAN SKS MAKSIMAL : 18 sks";


    public HomeView() {
        setTitle("Dashboard Mahasiswa");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(true);
        getContentPane().setBackground(new Color(245, 246, 247));

        initComponents(); // Hanya membuat kerangka (Frame dan Panel kosong)

        setSize(1300, 650);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    // ====================================================
    // FIX 1: MENAMBAHKAN initializeContent()
    // ====================================================
    public void initializeContent() {
        // Pastikan CardLayout sudah tersedia
        cardLayout = (CardLayout) dynamicContentPanel.getLayout();
        addInitialContent(); // Panggil logika penambahan konten sebenarnya
    }

    private void initComponents() {
        
        // FIX Centering
        JPanel fullFramePanel = new JPanel(new GridBagLayout()); 
        fullFramePanel.setBackground(new Color(245, 246, 247)); 
        
        // =================== MAIN ROUNDED CONTAINER ===================
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
        
        // =================== HEADER INFO BAR (TOP) ===================
        JPanel headerPanel = createHeaderPanel();
        headerPanel.setBounds(0, 0, 1000, 60);
        mainContainerPanel.add(headerPanel);
        
        // =================== SIDEBAR MENU (LEFT - 250px) ===================
        JPanel sidebarWrapper = createSidebar();
        sidebarWrapper.setBounds(0, 60, 250, 470); 
        mainContainerPanel.add(sidebarWrapper);

        // =================== DYNAMIC CONTENT AREA (RIGHT) ===================
        // Inisialisasi CardLayout di sini
        dynamicContentPanel = new JPanel(new CardLayout()); 
        dynamicContentPanel.setBounds(260, 60, 720, 470); 
        dynamicContentPanel.setBackground(Color.WHITE);
        mainContainerPanel.add(dynamicContentPanel);
        
        // Hapus pemanggilan addInitialContent() dari sini! Akan dipanggil oleh Controller.
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
        
        JLabel notifIcon = new JLabel("🔔"); 
        notifIcon.setFont(new Font("Segoe UI", Font.BOLD, 18));
        notifIcon.setForeground(Color.WHITE);
        notifIcon.setBounds(950, 15, 30, 30);
        panel.add(notifIcon);
        
        return panel;
    }

    private JPanel createSidebar() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(SIDEBAR_BG_COLOR);
        
        menuButtons = new HashMap<>();
        
        String[] menuItems = {"HOME", "REGISTRASI ULANG", "REGISTRASI MATAKULIAH", "JADWAL KULIAH", 
                              "TRANSKRIP NILAI", "TAGIHAN", "KALENDER", "LOGOUT"};
        
        // FIX: Hapus Label "DI SISTEM INFORMASI AKADEMIK" yang redundant
        JLabel emptyHeader = new JLabel(" ");
        emptyHeader.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 10)); // Padding untuk menjaga spasi atas
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
            
            menuButtons.put(item, button);
            panel.add(button);
        }
        
        highlightButton("HOME");
        return panel;
    }
    
    private void addInitialContent() {
        JPanel homeContent = createHomeWithRegistrationFormPanel();
        dynamicContentPanel.add(homeContent, "HOME");
        
        JPanel registrasiUlangContent = createPlaceholderPanel("REGISTRASI ULANG (Info Ringkasan)");
        dynamicContentPanel.add(registrasiUlangContent, "REGISTRASI ULANG");
        
        dynamicContentPanel.add(createPlaceholderPanel("REGISTRASI MATAKULIAH"), "REGISTRASI MATAKULIAH");
        dynamicContentPanel.add(createPlaceholderPanel("JADWAL KULIAH"), "JADWAL KULIAH");
        dynamicContentPanel.add(createPlaceholderPanel("TRANSKRIP NILAI"), "TRANSKRIP NILAI");
        dynamicContentPanel.add(createPlaceholderPanel("TAGIHAN"), "TAGIHAN");
        dynamicContentPanel.add(createPlaceholderPanel("KALENDER"), "KALENDER");
        
        cardLayout.show(dynamicContentPanel, "HOME"); 
    }
    
    private JPanel createHomeWithRegistrationFormPanel() {
        JPanel panel = new JPanel(null); 
        panel.setBackground(Color.WHITE);
        
        // ================= HEADER WELCOME ==================
        JLabel welcomeTitle = new JLabel("SELAMAT DATANG");
        welcomeTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        welcomeTitle.setForeground(SIDEBAR_COLOR);
        welcomeTitle.setBounds(50, 20, 400, 40);
        panel.add(welcomeTitle);
        
        JLabel sysTitle = new JLabel("DI SISTEM INFORMASI AKADEMIK SATYA WACANA");
        sysTitle.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        sysTitle.setBounds(50, 60, 600, 25);
        panel.add(sysTitle);
        
        // ================= REGISTRASI ULANG FORM DETAIL ==================
        JTextArea deskripsi = new JTextArea("UNTUK KEPERLUAN ADMINISTRASI ANDA, SILAHKAN UPDATE ISIAN DI BAWAH INI. DATA INI DIPERGUNAKAN UNTUK SYARAT PELAPORAN KE DIREKTORAT JENDERAL PENDIDIKAN TINGGI (DIKTI), KARENA PENTINGNYA DATA KEPENDUDUKAN MAHASISWA DALAM PELAPORAN PD DIKTI DAN VERIFIKASI KEABSAHAN IJAZAH, MAKA WAJIB MELAKUKAN PENGISIAN NIK DAN NOMOR KK SECARA BENAR");
        deskripsi.setWrapStyleWord(true);
        deskripsi.setLineWrap(true);
        deskripsi.setEditable(false);
        deskripsi.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        deskripsi.setBounds(50, 100, 650, 70); 
        deskripsi.setBorder(null);
        panel.add(deskripsi);
        
        // Form Fields
        String[] fields = {"Tempat, Tanggal Lahir", "No. Kartu Keluarga", "NIK (Nomor Induk Kependudukan)", 
                           "No. Handphone", "Email", "Nomor Rekening", "Bank", 
                           "Atas Nama Rekening", "Hubungan Dengan Pemilik Rekening"};
        
        int yPos = 180; 
        int fieldHeight = 25;
        for (String field : fields) {
            JLabel label = new JLabel(field + " :");
            // FIX: Alignment Kanan pada Label untuk kerapian
            label.setHorizontalAlignment(SwingConstants.RIGHT); 
            label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            label.setBounds(50, yPos, 190, fieldHeight); 
            panel.add(label);
            
            JTextField textField = new JTextField();
            textField.setBounds(250, yPos, 400, fieldHeight); 
            panel.add(textField);
            
            yPos += 30;
        }
        
        JButton lanjutButton = new JButton("Lanjut");
        lanjutButton.setBackground(SIDEBAR_COLOR);
        lanjutButton.setForeground(Color.WHITE);
        lanjutButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        // FIX: Posisi Tombol
        lanjutButton.setBounds(650 - 150, yPos - 5, 150, 35); 
        panel.add(lanjutButton);

        return panel;
    }
    
    private JPanel createPlaceholderPanel(String name) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        JLabel label = new JLabel("INI HALAMAN " + name, SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 30));
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }
    
    public void switchCard(String cardName) {
        cardLayout.show(dynamicContentPanel, cardName);
        highlightButton(cardName);
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

    public Map<String, JButton> getMenuButtons() {
        return menuButtons;
    }
}