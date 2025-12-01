package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter; 
import java.awt.event.MouseEvent;   
import java.awt.geom.RoundRectangle2D;
import java.util.HashMap;
import java.util.Map;
import java.util.List;                 
import model.SessionManager;         
import model.PenggunaModel;          
import model.NotifikasiModel;        
import service.NotifikasiService;    

public class HomeView extends JFrame {

    private JPanel mainContainerPanel;
    private JPanel dynamicContentPanel;
    private CardLayout cardLayout;

    private Map<String, JButton> menuButtons;
    
    private final Color SIDEBAR_COLOR = new Color(0, 35, 120);
    private final Color SIDEBAR_BG_COLOR = new Color(230, 235, 240);
    private final Color MAIN_BG_COLOR = new Color(248, 249, 250);

    private String USER_NIM_NAME;
    private final String SEMESTER_SKS = "SEMESTER 1 | TA 2025 - 2026 | BEBAN SKS MAKSIMAL : 18 sks";


    public HomeView() {
        setTitle("Dashboard Mahasiswa");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(true);
        getContentPane().setBackground(new Color(245, 246, 247));

        // --- AMBIL DATA DARI SESI UNTUK HEADER ---
        PenggunaModel user = SessionManager.getInstance().getCurrentUser();
        if (user != null) {
            this.USER_NIM_NAME = user.getId() + " - " + user.getNamaLengkap() + " (FAKULTAS TEKNOLOGI INFORMASI - TEKNIK INFORMATIKA)";
        } else {
            this.USER_NIM_NAME = "Loading User Data...";
        }
        // ------------------------------------------------

        initComponents();

        setSize(1300, 650);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
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
        String userRole = user != null ? user.getRole() : "Mahasiswa"; 
        
        String userClass = "TCS103A"; // Asumsi Kelas Mahasiswa
        
        List<NotifikasiModel> notifications = notifService.getNotifications(userRole, userClass);
        int unreadCount = notifications.size();
        
        JLabel notifIcon = new JLabel("<html><span style='color:white;'>" + 
                                       (unreadCount > 0 ? "🔔 (" + unreadCount + ")" : "🔔") + 
                                       "</span></html>"); 
        
        notifIcon.setFont(new Font("Segoe UI", Font.BOLD, 18));
        notifIcon.setBounds(920, 15, 80, 30); 
        notifIcon.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
        panel.add(notifIcon);
        
        // MouseListener untuk memunculkan NotifikasiDetailView
        notifIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                NotifikasiDetailView detailView = new NotifikasiDetailView(HomeView.this, notifications);
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
        
        String[] menuItems = {"HOME", "REGISTRASI ULANG", "REGISTRASI MATAKULIAH", "JADWAL KULIAH", 
                              "TRANSKRIP NILAI", "TAGIHAN", "KALENDER", "LOGOUT"};
        
        JLabel emptyHeader = new JLabel(" ");
        emptyHeader.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 10)); 
        panel.add(emptyHeader);
        
        for (String item : menuItems) {
            JButton button = new JButton(item);
            button.setAlignmentX(Component.LEFT_ALIGNMENT);
            button.setPreferredSize(new Dimension(250, 45)); 
            button.setMaximumSize(new Dimension(250, 45));
            button.setHorizontalAlignment(SwingConstants.LEFT);
            button.setBackground(SIDEBAR_BG_COLOR);
            button.setForeground(SIDEBAR_COLOR);
            
            button.setFont(new Font("Segoe UI", Font.BOLD, 14)); 
            
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
        // PERHATIAN: Asumsi JadwalView, KSTView, NilaiView, TagihanView, KalenderView sudah ada di package view
        JPanel homeContent = createHomeWithRegistrationFormPanel();
        dynamicContentPanel.add(homeContent, "HOME");
        
        JPanel registrasiUlangContent = createRegistrasiUlangErrorPanel();
        dynamicContentPanel.add(registrasiUlangContent, "REGISTRASI ULANG");
        
        // Anda perlu memastikan class-class ini tersedia
        dynamicContentPanel.add(new JadwalView(), "JADWAL KULIAH"); 
        dynamicContentPanel.add(new KSTView(), "REGISTRASI MATAKULIAH"); 
        dynamicContentPanel.add(new NilaiView(), "TRANSKRIP NILAI"); 
        dynamicContentPanel.add(new TagihanView(), "TAGIHAN");
        dynamicContentPanel.add(new KalenderView(), "KALENDER");
        
        cardLayout.show(dynamicContentPanel, "HOME"); 
    }
    
    private JPanel createRegistrasiUlangErrorPanel() {
        JPanel panel = new JPanel(new GridBagLayout()); 
        panel.setBackground(Color.WHITE);
        
        JLabel errorMessage = new JLabel("Err2: Masa registrasi ulang sudah habis, hubungi bagian Administrasi Akademik UKSW");
        errorMessage.setFont(new Font("Segoe UI", Font.BOLD, 14)); 
        errorMessage.setForeground(Color.RED); 
        
        panel.add(errorMessage); 
        return panel;
    }
    
    private JPanel createHomeWithRegistrationFormPanel() {
        JPanel panel = new JPanel(null); 
        panel.setBackground(Color.WHITE);
        
        JLabel welcomeTitle = new JLabel("SELAMAT DATANG");
        welcomeTitle.setFont(new Font("Segoe UI", Font.BOLD, 28)); 
        welcomeTitle.setForeground(SIDEBAR_COLOR);
        welcomeTitle.setBounds(50, 20, 400, 35); 
        panel.add(welcomeTitle);
        
        JLabel sysTitle = new JLabel("DI SISTEM INFORMASI AKADEMIK SATYA WACANA");
        sysTitle.setFont(new Font("Segoe UI", Font.PLAIN, 16)); 
        sysTitle.setBounds(50, 55, 600, 25); 
        panel.add(sysTitle);
        
        JTextArea deskripsi = new JTextArea("UNTUK KEPERLUAN ADMINISTRASI ANDA, SILAHKAN UPDATE ISIAN DI BAWAH INI. DATA INI DIPERGUNAKAN UNTUK SYARAT PELAPORAN KE DIREKTORAT JENDERAL PENDIDIKAN TINGGI (DIKTI), KARENA PENTINGNYA DATA KEPENDUDUKAN MAHASISWA DALAM PELAPORAN PD DIKTI DAN VERIFIKASI KEABSAHAN IJAZAH, MAKA WAJIB MELAKUKAN PENGISIAN NIK DAN NOMOR KK SECARA BENAR");
        deskripsi.setWrapStyleWord(true);
        deskripsi.setLineWrap(true);
        deskripsi.setEditable(false);
        deskripsi.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        deskripsi.setBounds(50, 85, 650, 65); 
        deskripsi.setBorder(null);
        panel.add(deskripsi);
        
        String[] fields = {"Tempat, Tanggal Lahir", "No. Kartu Keluarga", "NIK (Nomor Induk Kependudukan)", 
                           "No. Handphone", "Email", "Nomor Rekening", "Bank", 
                           "Atas Nama Rekening"}; 
        
        int yPos = 160; 
        int fieldHeight = 25;
        for (String field : fields) {
            JLabel label = new JLabel(field + " :");
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
        lanjutButton.setBounds(650 - 150, yPos + 10, 150, 35); 
        panel.add(lanjutButton);
        
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