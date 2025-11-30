package controller;

import view.HomeView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.SwingUtilities; // Import baru

public class HomeController {
    
    private final HomeView view;

    public HomeController(HomeView view) {
        this.view = view;
        
        // FIX: Tunda pemanggilan initController() agar View punya waktu untuk dibuat
        SwingUtilities.invokeLater(this::initController); 
    }
    
    private void initController() {
        System.out.println("HomeController Mahasiswa aktif.");
        
        // Panggil metode View untuk memuat konten CardLayout
        view.initializeContent(); 

        // Pasang listener ke semua tombol menu
        for (Map.Entry<String, JButton> entry : view.getMenuButtons().entrySet()) {
            String menuName = entry.getKey();
            JButton button = entry.getValue();
            
            button.addActionListener(new SidebarMenuListener(menuName));
        }
    }
    
    // Inner class untuk menangani klik Sidebar tetap sama
    class SidebarMenuListener implements ActionListener {
        private final String menuName;
        
        public SidebarMenuListener(String menuName) {
            this.menuName = menuName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (menuName.equals("LOGOUT")) {
                view.dispose();
                // TODO: Buka kembali LoginView
            } else {
                view.switchCard(menuName);
            }
        }
    }
}