package view;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.geom.RoundRectangle2D;

public class LoginView extends JFrame {

    private JComboBox<String> roleComboBox;
    private JTextField nimField;
    private JPasswordField passwordField;
    private JButton loginButton, lupaButton;

    private static final String NIM_PLACEHOLDER = "  Masukkan NIM / ID";
    private static final String PASSWORD_PLACEHOLDER = "  Masukkan password";
    private static final String ROLE_PLACEHOLDER = "Pilih peran";

    public LoginView() {
        setTitle("Login SIA.SAT");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);
        getContentPane().setBackground(new Color(245, 246, 247));

        initComponents();

        setSize(1300, 650);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {

        // CONTAINER UTAMA BARU: Mengisi seluruh Frame untuk Centering
        JPanel fullFramePanel = new JPanel(new GridBagLayout()); 
        fullFramePanel.setBackground(new Color(245, 246, 247));
        
        // =================== MAIN PANEL (ROUNDED) ===================
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
        // FIX: Atur ukuran FIXED (Ini yang menghilangkan border luar yang besar)
        mainPanel.setPreferredSize(new Dimension(1100, 550)); 
        
        fullFramePanel.add(mainPanel); 
        getContentPane().add(fullFramePanel, BorderLayout.CENTER); 

        // =================== LOGO ===================
        JLabel logoLabel = new JLabel();
        logoLabel.setBounds(80, 80, 300, 300);
        ImageIcon logoIcon = new ImageIcon("src/assets/logo.png"); 

        if (logoIcon.getIconWidth() > 0) {
            Image scaled = logoIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(scaled));
        }
        mainPanel.add(logoLabel);

        // =================== TEXT INFO ===================
        JLabel infoLabel = new JLabel(
                "<html><br><br>1. Lakukan Registrasi Ulang sebelum masa Registrasi Matakuliah dibuka untuk menghindari antrian.<br>" +
                "2. Lakukan Pembayaran Uang Kuliah sebelum jatuh tempo pembayaran berakhir.<br>" +
                "3. Segera lakukan pembayaran Pelunasan Uang Kuliah sebelum Masa Kuliah berakhir.<br>" +
                "4. Lakukan Pembayaran Uang Kuliah yang terkonfirmasi pada site SIA.SAT atau Bank Online.<br>" +
                "5. Hindari melakukan akses site SIA.SAT menggunakan perangkat berbeda secara bersamaan.<br>" +
                "6. Perhatikan Jadwal Registrasi Ulang dan Jadwal Registrasi Matakuliah Anda.<br><br><br></html>"
        );
        infoLabel.setFont(new Font("SF Pro", Font.PLAIN, 11));
        infoLabel.setBounds(60, 400, 520, 90);
        mainPanel.add(infoLabel);

        // =================== CARD LOGIN ===================
        int cardPanelRadius = 20;
        
        JPanel cardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, cardPanelRadius, cardPanelRadius));
                g2.dispose();
            }
            { setOpaque(false); }
        };
        
        cardPanel.setLayout(null);
        cardPanel.setBounds(550, 80, 430, 300);
        mainPanel.add(cardPanel);

        JLabel title = new JLabel("Login", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(new Color(200, 0, 0));
        title.setBounds(0, 20, 430, 30);
        cardPanel.add(title);

        // =================== COMBO ROLE ===================
        roleComboBox = new JComboBox<>();
        roleComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        roleComboBox.setBounds(60, 80, 310, 30);
        roleComboBox.setSelectedIndex(-1);

        roleComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (index == -1 && roleComboBox.getSelectedIndex() == -1) {
                    setText(ROLE_PLACEHOLDER);
                    setForeground(Color.GRAY);
                }
                return this;
            }
        });

        roleComboBox.addItem("Super Admin");
        roleComboBox.addItem("Dosen");
        roleComboBox.addItem("Mahasiswa");
        cardPanel.add(roleComboBox);

        // =================== NIM INPUT ===================
        nimField = new JTextField();
        nimField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        nimField.setBounds(60, 120, 310, 30);
        nimField.setBorder(new RoundedCornerBorder(15));
        setPlaceholder(nimField, NIM_PLACEHOLDER);
        cardPanel.add(nimField);

        // =================== PASSWORD INPUT ===================
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        passwordField.setBounds(60, 160, 310, 30);
        passwordField.setBorder(new RoundedCornerBorder(15));
        setPlaceholder(passwordField, PASSWORD_PLACEHOLDER);
        cardPanel.add(passwordField);

        lupaButton = new JButton("Lupa Password");
        lupaButton.setBounds(60, 210, 135, 32);
        lupaButton.setFocusPainted(false);
        lupaButton.setBackground(new Color(170, 0, 0));
        lupaButton.setForeground(Color.WHITE);
        lupaButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lupaButton.setBorder(BorderFactory.createEmptyBorder());
        lupaButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cardPanel.add(lupaButton);

        loginButton = new JButton("Login");
        loginButton.setBounds(235, 210, 135, 32);
        loginButton.setFocusPainted(false);
        loginButton.setBackground(new Color(0, 35, 120));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        loginButton.setBorder(BorderFactory.createEmptyBorder());
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cardPanel.add(loginButton);
    }

    private void setPlaceholder(JTextComponent field, String text) {
        field.setForeground(Color.GRAY);
        field.setText(text);

        Character origEcho = null;
        if (field instanceof JPasswordField pf) {
            origEcho = pf.getEchoChar();
            pf.putClientProperty("origEcho", origEcho);
            pf.setEchoChar((char) 0);
        }

        Character finalOrigEcho = origEcho;
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(text)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                    if (field instanceof JPasswordField jPasswordField) {
                        jPasswordField.setEchoChar(finalOrigEcho);
                    }
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setForeground(Color.GRAY);
                    field.setText(text);
                    if (field instanceof JPasswordField jPasswordField) {
                        jPasswordField.setEchoChar((char) 0);
                    }
                }
            }
        });
    }

    private static class RoundedCornerBorder extends AbstractBorder {
        private final int radius;

        RoundedCornerBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.LIGHT_GRAY); 
            g2.draw(new RoundRectangle2D.Float(x, y, width - 1, height - 1, radius, radius)); 
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius / 2, this.radius, this.radius / 2, this.radius);
        }
    }

    public String getNIM() {
        String text = nimField.getText().trim();
        return text.equals(NIM_PLACEHOLDER.trim()) ? "" : text;
    }

    public String getPassword() {
        String password = new String(passwordField.getPassword()).trim();
        return password.equals(PASSWORD_PLACEHOLDER.trim()) ? "" : password;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getLupaButton() {
        return lupaButton;
    }

    public String getSelectedRole() {
        String role = (String) roleComboBox.getSelectedItem();
        return role != null && role.equals(ROLE_PLACEHOLDER) ? null : role;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginView::new);
    }
}