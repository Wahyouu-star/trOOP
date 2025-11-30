package model;

public class KelasModel {

    private String namaKelas;
    private int jumlahMahasiswa;

    public KelasModel(String namaKelas, int jumlahMahasiswa) {
        this.namaKelas = namaKelas;
        this.jumlahMahasiswa = jumlahMahasiswa;
    }

    public String getNamaKelas() {
        return namaKelas;
    }

    public int getJumlahMahasiswa() {
        return jumlahMahasiswa;
    }
}
