package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.HashMap;
import java.util.Map;

public class AdminView extends JFrame {

    private JPanel mainContainerPanel;
    private JPanel dynamicContentPanel;
    private CardLayout cardLayout;

    private Map<String, JButton> menuButtons;
    
    // Warna dan Konstanta
    private final Color SIDEBAR_COLOR = new Color(0, 35, 120); // Biru Tua
    private final Color SIDEBAR_BG_COLOR = new Color(230, 235, 240); // Abu Muda Sidebar
    private final Color MAIN_BG_COLOR = new Color(248, 249, 250); // Latar Belakang Container
    
    private final String USER_INFO = "SUPER ADMIN";

    public AdminView() {
        setTitle("Dashboard Super Admin");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(true);
        getContentPane().setBackground(new Color(245, 246, 247));

        initComponents();

        setSize(1000, 650); // Ukuran disesuaikan agar proporsional
        setLocationRelativeTo(null);
        
        initializeContent(); 
        setVisible(true);
    }
    
    // Method ini dipanggil untuk menambahkan konten setelah inisialisasi komponen
    public void initializeContent() {
        cardLayout = (CardLayout) dynamicContentPanel.getLayout();
        addInitialContent();
        
        // Tampilkan halaman "HOME" sebagai halaman awal
        switchCard("HOME"); 
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
        
        // Ukuran disesuaikan agar pas dalam frame
        mainContainerPanel.setLayout(null);
        mainContainerPanel.setPreferredSize(new Dimension(960, 550)); 
        
        fullFramePanel.add(mainContainerPanel);
        getContentPane().add(fullFramePanel, BorderLayout.CENTER);
        
        // =================== HEADER INFO BAR (TOP) ===================
        JPanel headerPanel = createHeaderPanel();
        headerPanel.setBounds(0, 0, 960, 60);
        mainContainerPanel.add(headerPanel);
        
        // =================== SIDEBAR MENU (LEFT - 250px) ===================
        JPanel sidebarWrapper = createSidebar();
        sidebarWrapper.setBounds(0, 60, 250, 470);
        mainContainerPanel.add(sidebarWrapper);

        // =================== DYNAMIC CONTENT AREA (RIGHT) ===================
        dynamicContentPanel = new JPanel(new CardLayout());
        dynamicContentPanel.setBounds(260, 60, 680, 470); 
        dynamicContentPanel.setBackground(Color.WHITE);
        mainContainerPanel.add(dynamicContentPanel);
        
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(SIDEBAR_COLOR);
        
        JLabel infoLabel = new JLabel(USER_INFO);
        infoLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setBounds(20, 15, 800, 30);
        panel.add(infoLabel);
        
        JLabel notifIcon = new JLabel("🔔");
        notifIcon.setFont(new Font("Segoe UI", Font.BOLD, 18));
        notifIcon.setForeground(Color.WHITE);
        notifIcon.setBounds(900, 15, 30, 30);
        panel.add(notifIcon);
        
        return panel;
    }

    private JPanel createSidebar() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(SIDEBAR_BG_COLOR);
        
        menuButtons = new HashMap<>();
        
        String[] menuItems = {"HOME", "ATUR TAGIHAN", "ATUR TAGIHAN MATKUL", 
                              "ATUR NOTIFIKASI", "ATUR KALENDER", "KALENDER"};
        
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
            button.setFont(new Font("Segoe UI", Font.BOLD, 15));
            button.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 5));
            button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            button.setFocusPainted(false);
            
            button.addActionListener(e -> {
                switchCard(item);
            });
            
            menuButtons.put(item, button);
            panel.add(button);
        }
        
        highlightButton("HOME");
        return panel;
    }
    
    private void addInitialContent() {
        // Konten HOME (Tampilan Selamat Datang)
        JPanel homeContent = createHomeWelcomePanel();
        dynamicContentPanel.add(homeContent, "HOME");
        
        // Konten Placeholder untuk menu lainnya
        dynamicContentPanel.add(createPlaceholderPanel("ATUR TAGIHAN"), "ATUR TAGIHAN");
        dynamicContentPanel.add(createPlaceholderPanel("ATUR TAGIHAN MATKUL"), "ATUR TAGIHAN MATKUL");
        dynamicContentPanel.add(createPlaceholderPanel("ATUR NOTIFIKASI"), "ATUR NOTIFIKASI");
        dynamicContentPanel.add(createPlaceholderPanel("ATUR KALENDER"), "ATUR KALENDER");
        dynamicContentPanel.add(createPlaceholderPanel("KALENDER"), "KALENDER");
        
        cardLayout.show(dynamicContentPanel, "HOME");
    }
    
    /**
     * Membuat panel Selamat Datang pada halaman HOME
     */
    private JPanel createHomeWelcomePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(Color.WHITE);
        content.setBorder(new EmptyBorder(0, 0, 0, 0));

        JLabel welcomeTitle = new JLabel("SELAMAT DATANG,", SwingConstants.CENTER);
        welcomeTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        welcomeTitle.setForeground(SIDEBAR_COLOR);
        welcomeTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel nameLabel = new JLabel("SUPER ADMIN", SwingConstants.CENTER);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        nameLabel.setForeground(SIDEBAR_COLOR);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel systemTitle = new JLabel("DI SISTEM INFORMASI AKADEMIK SATYA WACANA", SwingConstants.CENTER);
        systemTitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        systemTitle.setForeground(SIDEBAR_COLOR);
        systemTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        content.add(welcomeTitle);
        content.add(nameLabel);
        content.add(Box.createVerticalStrut(10)); // Spacer
        content.add(systemTitle);
        
        panel.add(content); 
        return panel;
    }
    
    private JPanel createPlaceholderPanel(String name) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        JLabel label = new JLabel("INI HALAMAN " + name + " (PLACEHOLDER)", SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 30));
        panel.add(label, BorderLayout.CENTER);
        return panel;
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AdminView();
        });
    }
}