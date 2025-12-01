package controller;

import view.DosenView;
import view.LoginView; // Tambahkan
import model.SessionManager; // Tambahkan
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.SwingUtilities;

public class DosenController {
    
    private final DosenView view;

    public DosenController(DosenView view) {
        this.view = view;
        // Gunakan invokeLater untuk memastikan Frame siap sebelum initController dipanggil
        SwingUtilities.invokeLater(this::initController); 
    }
    
    private void initController() {
        System.out.println("DosenController aktif.");
        // PASTIKAN view.initializeContent() dipanggil untuk membuat semua panel/tombol
        view.initializeContent(); 

        // Pasang listener ke semua tombol menu (Ini yang membuat tombol clickable)
        for (Map.Entry<String, JButton> entry : view.getMenuButtons().entrySet()) {
            String menuName = entry.getKey();
            JButton button = entry.getValue();
            
            button.addActionListener(new SidebarMenuListener(menuName));
        }
    }
    
    class SidebarMenuListener implements ActionListener {
        private final String menuName;
        
        public SidebarMenuListener(String menuName) {
            this.menuName = menuName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (menuName.equals("LOGOUT")) {
                SessionManager.getInstance().clearSession(); // Bersihkan sesi
                view.dispose(); // Tutup DosenView
                
                // Buka kembali LoginView
                SwingUtilities.invokeLater(() -> {
                    LoginView loginView = new LoginView();
                    new LoginController(loginView); 
                });
            } else {
                view.switchCard(menuName); // Navigasi ke View yang sesuai
            }
        }
    }
}