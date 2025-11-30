package model;

public class NotifikasiModel {
    private String judul;
    private String pesan;

    public NotifikasiModel(String judul, String pesan) {
        this.judul = judul;
        this.pesan = pesan;
    }

    public String getJudul() {
        return judul;
    }

    public String getPesan() {
        return pesan;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }
}
