package model;

public class KalenderModel {
    private String namaEvent;
    private String tanggal;

    public KalenderModel(String namaEvent, String tanggal) {
        this.namaEvent = namaEvent;
        this.tanggal = tanggal;
    }

    public String getNamaEvent() {
        return namaEvent;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setNamaEvent(String namaEvent) {
        this.namaEvent = namaEvent;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
