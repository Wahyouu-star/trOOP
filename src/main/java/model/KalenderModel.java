package model;

public class KalenderModel {
    private String namaEvent;
    private String tanggal; // Format YYYY-MM-DD
    private String deskripsi;
    private String pengirimRole;
    private String targetRole;

    public KalenderModel(String namaEvent, String tanggal, String deskripsi, String pengirimRole, String targetRole) {
        this.namaEvent = namaEvent;
        this.tanggal = tanggal;
        this.deskripsi = deskripsi;
        this.pengirimRole = pengirimRole;
        this.targetRole = targetRole;
    }
    
    // Simplifikasi untuk data bawaan yang mungkin ada (optional)
    public KalenderModel(String namaEvent, String tanggal) {
        this(namaEvent, tanggal, "", null, null);
    }
    
    public String getNamaEvent() { return namaEvent; }
    public String getTanggal() { return tanggal; }
    public String getDeskripsi() { return deskripsi; }
    public String getPengirimRole() { return pengirimRole; }
    public String getTargetRole() { return targetRole; }

    // Helper untuk mengambil hari bulan (dipakai untuk mewarnai grid kalender)
    public int getDayOfMonth() {
        try {
            // Asumsi tanggal adalah dalam format YYYY-MM-DD
            return Integer.parseInt(tanggal.substring(8)); 
        } catch (Exception e) {
            return -1;
        }
    }
}