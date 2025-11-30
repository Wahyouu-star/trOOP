package service;

public class AuthService {
    
    // Kredensial Placeholder: Mahasiswa (mhs/mhs), Dosen (dsn/dsn), Admin (admin/admin)
    public boolean authenticate(String role, String username, String password) {
        // Ganti logika switch ini dengan koneksi database/API Anda
        return switch (role) {
            case "Mahasiswa" -> username.equals("mhs") && password.equals("mhs");
            case "Dosen" -> username.equals("dsn") && password.equals("dsn");
            case "Super Admin" -> username.equals("admin") && password.equals("admin");
            default -> false;
        };
    }
}