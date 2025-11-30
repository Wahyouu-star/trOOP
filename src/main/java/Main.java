import controller.LoginController;
import view.LoginView;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Disarankan menjalankan operasi Swing di Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView();
            new LoginController(loginView); // Controller menginisialisasi listener
        });
    }
}