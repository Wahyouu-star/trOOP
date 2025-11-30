package controller;

import view.HomeView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

public class HomeController {
    
    private final HomeView view;

    public HomeController(HomeView view) {
        this.view = view;
        
        // FIX KRITIS: Memanggil initController setelah Frame siap
        SwingUtilities.invokeLater(this::initController); 
    }
    
    private void initController() {
        view.initializeContent(); 

        // Pasang listener ke semua tombol menu
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
                view.dispose();
                // TODO: Logika kembali ke LoginView
            } else {
                view.switchCard(menuName); // Navigasi ke View yang sesuai
            }
        }
    }
}