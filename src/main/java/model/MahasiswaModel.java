package model;

import java.time.LocalDate;

// Model ini merepresentasikan data detail mahasiswa (Form di HomeView)
public class MahasiswaModel {
    private String nim;
    private String tempatLahir;
    private LocalDate tglLahir;
    private String noKK;
    private String nik;
    private String noHP;
    private String email;
    private String noRekening;
    private String bank;
    private String atasNamaRekening;

    // Tambahkan konstruktor yang sesuai
    public MahasiswaModel(String nim, String tempatLahir, LocalDate tglLahir, String noKK, String nik, String noHP, String email, String noRekening, String bank, String atasNamaRekening) {
        this.nim = nim;
        this.tempatLahir = tempatLahir;
        this.tglLahir = tglLahir;
        this.noKK = noKK;
        this.nik = nik;
        this.noHP = noHP;
        this.email = email;
        this.noRekening = noRekening;
        this.bank = bank;
        this.atasNamaRekening = atasNamaRekening;
    }
    
    // Getters
    public String getNim() { return nim; }
    public String getTempatLahir() { return tempatLahir; }
    public LocalDate getTglLahir() { return tglLahir; }
    public String getNoKK() { return noKK; }
    public String getNik() { return nik; }
    public String getNoHP() { return noHP; }
    public String getEmail() { return email; }
    public String getNoRekening() { return noRekening; }
    public String getBank() { return bank; }
    public String getAtasNamaRekening() { return atasNamaRekening; }
    
    // Helper: Format Tempat, Tanggal Lahir
    public String getTTLString() {
        return String.format("%s, %s", tempatLahir, (tglLahir != null ? tglLahir.toString() : ""));
    }
}