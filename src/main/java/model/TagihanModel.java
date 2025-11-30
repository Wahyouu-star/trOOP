package model;

public class TagihanModel {

    private String nama;
    private double jumlah;
    private boolean lunas;

    public TagihanModel(String nama, double jumlah, boolean lunas) {
        this.nama = nama;
        this.jumlah = jumlah;
        this.lunas = lunas;
    }

    public String getNama() {
        return nama;
    }

    public double getJumlah() {
        return jumlah;
    }

    public boolean isLunas() {
        return lunas;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setJumlah(double jumlah) {
        this.jumlah = jumlah;
    }

    public void setLunas(boolean lunas) {
        this.lunas = lunas;
    }
}
