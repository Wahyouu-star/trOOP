package model;

// Kelas SessionManager untuk menyimpan data pengguna yang sedang login (Pola Singleton)
public class SessionManager {
    
    private static SessionManager instance;
    private PenggunaModel currentUser;

    private SessionManager() {
        // Private constructor
    }

    // Mengembalikan instance tunggal SessionManager
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    // Menyimpan data pengguna setelah login sukses
    public void setCurrentUser(PenggunaModel user) {
        this.currentUser = user;
    }

    // Mengambil data pengguna aktif
    public PenggunaModel getCurrentUser() {
        return currentUser;
    }
    
    // Mengecek apakah ada pengguna yang login
    public boolean isLoggedIn() {
        return currentUser != null;
    }
    
    // Menghapus sesi saat logout
    public void clearSession() {
        this.currentUser = null;
    }
}