package model;

public class KSTModel {

    private String kodeMatkul;
    private String namaMatkul;
    private int sks;

    public KSTModel(String kodeMatkul, String namaMatkul, int sks) {
        this.kodeMatkul = kodeMatkul;
        this.namaMatkul = namaMatkul;
        this.sks = sks;
    }

    public String getKodeMatkul() {
        return kodeMatkul;
    }

    public String getNamaMatkul() {
        return namaMatkul;
    }

    public int getSks() {
        return sks;
    }
}
