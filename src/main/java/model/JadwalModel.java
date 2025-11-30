package model;

public class JadwalModel {

    private String mataKuliah;
    private String jam;
    private String ruangan;

    public JadwalModel(String mataKuliah, String jam, String ruangan) {
        this.mataKuliah = mataKuliah;
        this.jam = jam;
        this.ruangan = ruangan;
    }

    public String getMataKuliah() {
        return mataKuliah;
    }

    public String getJam() {
        return jam;
    }

    public String getRuangan() {
        return ruangan;
    }
}
