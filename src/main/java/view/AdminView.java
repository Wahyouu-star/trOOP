package view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class AdminView extends JFrame {

    public AdminView() {
        setTitle("Dashboard Super Admin");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        setResizable(true);
        getContentPane().setBackground(new Color(245, 246, 247));

        initComponents("Dashboard Super Admin", new Color(170, 0, 0)); // Merah

        setSize(1300, 650);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void initComponents(String viewTitle, Color titleColor) {
        // Logika layout sama persis
        int mainPanelRadius = 40;
        Color mainPanelColor = new Color(248, 249, 250);
        
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(mainPanelColor);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, mainPanelRadius, mainPanelRadius));
                g2.dispose();
            }
            { setOpaque(false); } 
        };
        
        mainPanel.setLayout(null);
        mainPanel.setBounds(230, 80, 1000, 550); 
        add(mainPanel);

        // --- Konten Spesifik View ---
        JLabel title = new JLabel(viewTitle);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(titleColor);
        title.setBounds(40, 20, 500, 30);
        mainPanel.add(title);
        
        JLabel welcome = new JLabel("Selamat Datang di Dashboard Super Admin!");
        welcome.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        welcome.setBounds(40, 70, 500, 30);
        mainPanel.add(welcome);
    }
}