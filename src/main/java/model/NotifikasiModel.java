package model;

public class NotifikasiModel {
    private String judul;
    private String pesan;
    private String pengirimRole; // FIELD BARU

    // Constructor DENGAN pengirimRole
    public NotifikasiModel(String judul, String pesan, String pengirimRole) {
        this.judul = judul;
        this.pesan = pesan;
        this.pengirimRole = pengirimRole;
    }

    public String getJudul() {
        return judul;
    }

    public String getPesan() {
        return pesan;
    }
    
    public String getPengirimRole() { // GETTER BARU
        return pengirimRole;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }
    
    public void setPengirimRole(String pengirimRole) { // SETTER BARU
        this.pengirimRole = pengirimRole;
    }
}