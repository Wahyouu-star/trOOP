package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList; 
import model.KSTModel; 
import model.SessionManager; // Tambahkan untuk mendapatkan NIM
import service.KSTService; // Tambahkan untuk memanggil logika DB

public class KSTView extends JPanel {

    private final Color HEADER_COLOR = new Color(0, 35, 120);

    public KSTView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initComponents();
    }

    private void initComponents() {

        // --- AMBIL DATA DARI SERVICE (DB) ---
        KSTService kstService = new KSTService();
        ArrayList<KSTModel> courses = kstService.getAvailableCourses();
        
        String[] columnNames = {"KODE MATA KULIAH", "NAMA MATA KULIAH", "SKS", "AKSI"};
        
        // Buat array 2D dari hasil Service
        Object[][] data = new Object[courses.size()][4];
        for (int i = 0; i < courses.size(); i++) {
            KSTModel course = courses.get(i);
            data[i][0] = course.getKodeMatkul();
            data[i][1] = course.getNamaMatkul();
            data[i][2] = String.valueOf(course.getSks()); // SKS diubah ke String
            data[i][3] = "Registrasi";
        }
        // --- SELESAI AMBIL DATA ---


        JTable table = new JTable(data, columnNames); // Gunakan data dinamis
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(25);

        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(230, 230, 230));

        table.getColumnModel().getColumn(0).setPreferredWidth(120);
        table.getColumnModel().getColumn(1).setPreferredWidth(350);
        table.getColumnModel().getColumn(2).setPreferredWidth(50);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

        table.getColumn("AKSI").setCellRenderer(new ButtonRenderer());
        table.getColumn("AKSI").setCellEditor(new ButtonEditor(new JTextField(), table));

        JScrollPane scrollPane = new JScrollPane(table);

        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(Color.WHITE);
        JLabel title = new JLabel("REGISTRASI KARTU STUDI TETAP (KST)");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(HEADER_COLOR);
        title.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));
        header.add(title);

        this.add(header, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setForeground(Color.WHITE);
            setBackground(new Color(170, 0, 0));
            setFocusPainted(false);
            setFont(new Font("Segoe UI", Font.BOLD, 11));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "Registrasi" : value.toString());
            return this;
        }
    }

    // FIXED BUTTON EDITOR DENGAN LOGIKA GENERATE TAGIHAN
    class ButtonEditor extends DefaultCellEditor implements ActionListener {

        protected JButton button;
        private String label;
        private boolean isPushed;
        private JTable table;

        public ButtonEditor(JTextField textField, JTable table) {
            super(textField);
            this.table = table;
            setClickCountToStart(1);

            button = new JButton();
            button.setOpaque(true);
            button.setForeground(Color.WHITE);
            button.setBackground(new Color(170, 0, 0));
            button.setFont(new Font("Segoe UI", Font.BOLD, 11));
            button.addActionListener(this);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {

            label = (value == null) ? "Registrasi" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                // --- 1. Ambil Data Penting ---
                if (!SessionManager.getInstance().isLoggedIn()) {
                     JOptionPane.showMessageDialog(null, "Sesi berakhir. Harap login kembali.", "Error Sesi", JOptionPane.ERROR_MESSAGE);
                     return label;
                }
                
                String nim = SessionManager.getInstance().getCurrentUser().getId(); // Ambil NIM dari sesi
                String kodeMatkul = (String) table.getModel().getValueAt(table.getEditingRow(), 0);
                String namaMatkul = (String) table.getModel().getValueAt(table.getEditingRow(), 1);
                
                String sksString = (String) table.getModel().getValueAt(table.getEditingRow(), 2);
                int sks = 0;
                try {
                    sks = Integer.parseInt(sksString); 
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "SKS tidak valid.", "Error", JOptionPane.ERROR_MESSAGE);
                    return label;
                }
                
                KSTService kstService = new KSTService(); 
                long totalTagihan = 250000L * sks;
                
                // --- 2. Panggil Service untuk Registrasi dan Generate Tagihan ---
                if (kstService.registerCourseAndGenerateBill(nim, kodeMatkul, sks)) {
                    // Sukses
                    JOptionPane.showMessageDialog(null,
                        "Matakuliah " + namaMatkul + " (" + kodeMatkul + ") berhasil ditambahkan ke KRS Anda.\n" +
                        "Tagihan SKS otomatis sebesar Rp " + String.format("%,d", totalTagihan).replace(",", ".") + " telah ditambahkan ke menu Tagihan.",
                        "Registrasi Berhasil",
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // Gagal
                    JOptionPane.showMessageDialog(null,
                        "Gagal melakukan registrasi. Matakuliah ini sudah terdaftar atau terjadi masalah koneksi database/server.",
                        "Registrasi Gagal",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
            isPushed = false;
            return label;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            fireEditingStopped();
        }
    }
}