package controller;

import view.AdminView;
import view.DosenView; 
import view.HomeView;
import view.LoginView;
import service.AuthService;
import model.PenggunaModel; // Pastikan import ini ada
import model.SessionManager; // Pastikan import ini ada
import javax.swing.*;
import java.util.Map;
import javax.swing.JButton;

public class LoginController {

    private final LoginView view;
    // Gunakan AuthService yang sudah diubah untuk query database
    private final AuthService authService = new AuthService();

    public LoginController(LoginView v) {
        this.view = v;
        initController();
    }

    private void initController() {
        view.getLoginButton().addActionListener(e -> handleLogin());
        view.getLupaButton().addActionListener(e -> {
            JOptionPane.showMessageDialog(view, "Fitur Lupa Password belum diimplementasikan.", "Info", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    private void handleLogin() {
        String role = view.getSelectedRole();
        String username = view.getNIM().trim();
        String password = view.getPassword().trim();

        if (role == null || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Harap lengkapi semua kolom.", "Validasi Gagal", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Panggil metode authenticate yang baru, yang mengembalikan PenggunaModel
        PenggunaModel user = authService.authenticate(role, username, password);

        if (user != null) {
            // Set Session sebelum berpindah View (CRITICAL)
            SessionManager.getInstance().setCurrentUser(user);

            switch (role) {
                case "Mahasiswa" -> {
                    new HomeController(new HomeView());
                    view.dispose();
                }
                case "Dosen" -> {
                    new DosenController(new DosenView());
                    view.dispose();
                }
                case "Super Admin" -> {
                    new AdminController(new AdminView());
                    view.dispose();
                }
            }
        } else {
            JOptionPane.showMessageDialog(view, "NIM / ID atau Password salah, atau peran tidak cocok.", "Gagal Login", JOptionPane.ERROR_MESSAGE);
        }
    }
}