package view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class KSTView extends JFrame {

    public KSTView() {
        setTitle("Registrasi Kartu Studi Tetap (KST)");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        setResizable(true);
        getContentPane().setBackground(new Color(245, 246, 247));

        initComponents("Registrasi Kartu Studi Tetap (KST)");

        setSize(1300, 650);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void initComponents(String viewTitle) {
        
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

        JLabel title = new JLabel(viewTitle);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(0, 35, 120));
        title.setBounds(40, 20, 500, 30);
        mainPanel.add(title);
        
        JLabel info = new JLabel("Formulir pemilihan dan registrasi mata kuliah.");
        info.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        info.setBounds(40, 70, 500, 30);
        mainPanel.add(info);
    }
}