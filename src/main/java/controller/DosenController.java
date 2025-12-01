package controller;

import view.DosenView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.SwingUtilities;

public class DosenController {
    
    private final DosenView view;

    public DosenController(DosenView view) {
        this.view = view;
        SwingUtilities.invokeLater(this::initController); 
    }
    
    private void initController() {
        System.out.println("DosenController aktif.");
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
                // TODO: Buka kembali LoginView
            } else {
                view.switchCard(menuName);
            }
        }
    }
}