package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.NotifikasiModel;

public class NotifikasiDetailView extends JDialog {

    public NotifikasiDetailView(JFrame owner, List<NotifikasiModel> notifList) {
        super(owner, "Detail Notifikasi", true);
        
        setLayout(new BorderLayout(10, 10));
        setSize(500, 400);
        setLocationRelativeTo(owner);
        getContentPane().setBackground(Color.WHITE);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(new Color(248, 249, 250));
        listPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        if (notifList.isEmpty()) {
            JLabel emptyLabel = new JLabel("Tidak ada notifikasi baru untuk Anda.");
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            listPanel.add(Box.createVerticalGlue());
            listPanel.add(emptyLabel);
            listPanel.add(Box.createVerticalGlue());
        } else {
            for (NotifikasiModel notif : notifList) {
                listPanel.add(createNotifItem(notif));
                listPanel.add(Box.createVerticalStrut(8));
            }
        }

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        JLabel titleLabel = new JLabel("DAFTAR NOTIFIKASI");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        add(titleLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private JPanel createNotifItem(NotifikasiModel notif) {
        JPanel panel = new JPanel(new BorderLayout(5, 0));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80)); 
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        panel.setBackground(Color.WHITE);
        
        // INFORMASI PENGIRIM DITAMBAHKAN
        JLabel pengirim = new JLabel("Dari: " + notif.getPengirimRole());
        pengirim.setFont(new Font("Segoe UI", Font.ITALIC, 10));
        pengirim.setForeground(Color.GRAY);

        JLabel judul = new JLabel("<html><b>" + notif.getJudul() + "</b></html>");
        judul.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        JLabel pesan = new JLabel("<html><p style='width: 380px;'>" + notif.getPesan() + "</p></html>");
        pesan.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.add(judul, BorderLayout.WEST);
        header.add(pengirim, BorderLayout.EAST);

        panel.add(header, BorderLayout.NORTH);
        panel.add(pesan, BorderLayout.CENTER);
        
        return panel;
    }
    
    public void showDialog() {
        this.setVisible(true);
    }
}