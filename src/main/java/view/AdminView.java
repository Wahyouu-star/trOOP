package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class AdminView extends JFrame {

    private JPanel mainContainerPanel;
    private JPanel dynamicContentPanel;
    private CardLayout cardLayout;

    private Map<String, JButton> menuButtons;

    private final Color SIDEBAR_COLOR = new Color(0, 35, 120);
    private final Color SIDEBAR_BG_COLOR = new Color(230, 235, 240);
    private final Color MAIN_BG_COLOR = new Color(248, 249, 250);
    private final String USER_INFO = "SUPER ADMIN";

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

        JLabel notifIcon = new JLabel("🔔");
        notifIcon.setFont(new Font("Segoe UI", Font.BOLD, 20));
        notifIcon.setForeground(Color.WHITE);
        notifIcon.setBounds(1000, 15, 30, 30);
        panel.add(notifIcon);

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
                "ATUR KALENDER"
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

            button.addActionListener(e -> switchCard(item));

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

        JTable table = new JTable(model);

        model.addRow(new Object[]{"1", "672024082", "CHRISTIAN WAHYU SURYA ANGKASA", "Rp 0", "Kirim"});
        model.addRow(new Object[]{"2", "672024089", "ALEXANDER AXL DYANDRA SOEMARDHI PUTRA", "Rp 0", "Kirim"});
        model.addRow(new Object[]{"3", "672024092", "AHMAD HAFIZH", "Rp 0", "Kirim"});
        model.addRow(new Object[]{"4", "672024097", "ELGAN SEVI PRAMUDITA", "Rp 0", "Kirim"});

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

            JOptionPane.showMessageDialog(panel,
                    "Notifikasi berhasil dikirim ke seluruh pengguna:\n\n" + isi,
                    "Notifikasi Terkirim", JOptionPane.INFORMATION_MESSAGE);

            textArea.setText("");
        });

        bottomPanel.add(btnLanjut);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

    // =========================== ATUR KALENDER ======================
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
        monthLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
        calendarCard.add(monthLabel, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(6, 7, 5, 5));
        grid.setBackground(Color.WHITE);

        String[] days = {"MINGGU", "SENIN", "SELASA", "RABU", "KAMIS", "JUMAT", "SABTU"};
        for (String d : days) {
            JLabel dayLbl = new JLabel(d, SwingConstants.CENTER);
            dayLbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
            grid.add(dayLbl);
        }

        for (int i = 1; i <= 35; i++) {
            if (i <= 30) {
                JButton dayBtn = new JButton(String.valueOf(i));
                dayBtn.setFocusPainted(false);
                dayBtn.setBackground(new Color(230, 235, 240));
                dayBtn.setBorder(BorderFactory.createEmptyBorder());
                grid.add(dayBtn);
            } else {
                grid.add(new JLabel(""));
            }
        }

        calendarCard.add(grid, BorderLayout.CENTER);

        JLabel agendaLabel = new JLabel("Belum ada agenda.", SwingConstants.LEFT);
        agendaLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        agendaLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        calendarCard.add(agendaLabel, BorderLayout.SOUTH);

        panel.add(calendarCard, BorderLayout.CENTER);

        List<String> agendaList = new ArrayList<>();

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setBackground(Color.WHITE);
        JButton btnTambahAgenda = new JButton("Tambahkan Agenda");
        btnTambahAgenda.setBackground(new Color(0, 80, 170));
        btnTambahAgenda.setForeground(Color.WHITE);
        btnTambahAgenda.setFocusPainted(false);
        btnTambahAgenda.setFont(new Font("Segoe UI", Font.BOLD, 13));

        btnTambahAgenda.addActionListener(e -> {
            String tanggal = JOptionPane.showInputDialog(panel,
                    "Masukkan tanggal (1-30):", "Tambah Agenda",
                    JOptionPane.QUESTION_MESSAGE);
            if (tanggal == null || tanggal.trim().isEmpty()) return;

            String namaAgenda = JOptionPane.showInputDialog(panel,
                    "Masukkan nama agenda:", "Tambah Agenda",
                    JOptionPane.QUESTION_MESSAGE);
            if (namaAgenda == null || namaAgenda.trim().isEmpty()) return;

            String agenda = tanggal + " November : " + namaAgenda;
            agendaList.add(agenda);

            StringBuilder sb = new StringBuilder("<html>");
            for (String a : agendaList) {
                sb.append(a).append("<br>");
            }
            sb.append("</html>");
            agendaLabel.setText(sb.toString());

            JOptionPane.showMessageDialog(panel,
                    "Agenda berhasil ditambahkan:\n" + agenda,
                    "Agenda Ditambahkan", JOptionPane.INFORMATION_MESSAGE);
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
