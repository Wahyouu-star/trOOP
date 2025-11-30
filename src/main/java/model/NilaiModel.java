package model;

public class NilaiModel {

    private String mataKuliah;
    private double nilai;

    public NilaiModel(String mataKuliah, double nilai) {
        this.mataKuliah = mataKuliah;
        this.nilai = nilai;
    }

    public String getMataKuliah() {
        return mataKuliah;
    }

    public double getNilai() {
        return nilai;
    }
}
