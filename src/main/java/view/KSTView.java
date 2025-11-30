package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KSTView extends JPanel {

    private final Color HEADER_COLOR = new Color(0, 35, 120);

    public KSTView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initComponents();
    }

    private void initComponents() {

        String[] columnNames = {"KODE MATA KULIAH", "NAMA MATA KULIAH", "SKS", "AKSI"};
        Object[][] data = {
                {"TCS201", "ALGORITMA & STRUKTUR DATA", "3", "Registrasi"},
                {"TCS202", "SISTEM BASIS DATA", "3", "Registrasi"},
                {"TCS203", "JARINGAN KOMPUTER", "3", "Registrasi"},
                {"TCS204", "P. BERORIENTASI OBJEK II", "3", "Registrasi"},
                {"TCS205", "ETIKA PROFESI", "2", "Registrasi"},
                {"TCS206", "KECERDASAN BUATAN", "3", "Registrasi"},
                {"TCS207", "DATA MINING", "3", "Registrasi"}
        };

        JTable table = new JTable(data, columnNames);
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

    // FIXED BUTTON EDITOR
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
                String kodeMatkul = (String) table.getModel().getValueAt(table.getEditingRow(), 0);
                String namaMatkul = (String) table.getModel().getValueAt(table.getEditingRow(), 1);

                JOptionPane.showMessageDialog(null,
                        "Matakuliah " + namaMatkul + " (" + kodeMatkul + ") telah ditambahkan ke KRS Anda.",
                        "Konfirmasi Registrasi",
                        JOptionPane.INFORMATION_MESSAGE);
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
