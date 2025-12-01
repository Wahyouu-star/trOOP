package model;

// Model ini digunakan untuk menyimpan data dasar pengguna yang berhasil login
public class PenggunaModel {

    private String id; // NIM / ID
    private String password;
    private String role;
    private String namaLengkap; // Tambahan field

    public PenggunaModel(String id, String password, String role, String namaLengkap) {
        this.id = id;
        this.password = password;
        this.role = role;
        this.namaLengkap = namaLengkap;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }
}