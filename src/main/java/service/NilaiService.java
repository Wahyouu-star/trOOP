// TR-OOP/src/main/java/service/NilaiService.java

package service;

import java.util.ArrayList;
import model.NilaiModel;
// import model.Database; // Dihapus untuk sementara
import model.NilaiModel;

// Service untuk mengambil data Transkrip Nilai
public class NilaiService {

    // UBAH DARI private class MENJADI public class AGAR BISA DIAKSES OLEH NilaiView
    public class NilaiDetail {
        String no;
        String kode;
        String matakuliah;
        String sks;
        String nilai;
        String ak;
        String semester;
        String tahunAjaran;

        public NilaiDetail(String no, String kode, String matakuliah, String sks, String nilai, String ak, String semester, String tahunAjaran) {
            this.no = no;
            this.kode = kode;
            this.matakuliah = matakuliah;
            this.sks = sks;
            this.nilai = nilai;
            this.ak = ak;
            this.semester = semester;
            this.tahunAjaran = tahunAjaran;
        }

        // Helper untuk mengembalikan data dalam format array tabel
        public Object[] toRowData() {
            return new Object[]{no, kode, matakuliah, sks, nilai, ak, semester, tahunAjaran};
        }
    }
    
    // Metode utama untuk mengambil data transkrip
    public ArrayList<NilaiDetail> getTranskrip(String nim) {
        ArrayList<NilaiDetail> list = new ArrayList<>();
        
        // **********************************************
        // GANTI INI DENGAN LOGIKA QUERY DATABASE
        // **********************************************
        
        // Data Placeholder (sesuai desain figma NilaiView)
        list.add(new NilaiDetail("1", "TCS103", "MATEMATIKA DASAR", "3", "B", "9", "A", "2020-2021/1"));
        list.add(new NilaiDetail("2", "TCS13F", "DASAR-DASAR PEMROGRAMAN", "3", "C", "6", "A", "2020-2021/1"));
        list.add(new NilaiDetail("3", "TCS20A", "SISTEM BASIS DATA", "3", "A", "12", "B", "2021-2022/1"));
        list.add(new NilaiDetail("4", "TCS22A", "PENGANTAR TEKNOLOGI INFORMASI", "3", "AB", "10.5", "B", "2021-2022/1"));
        list.add(new NilaiDetail("5", "TCS1C1", "ALJABAR LINIER", "3", "A", "12", "C", "2022-2023/1"));
        list.add(new NilaiDetail("6", "HUR41C", "PENDIDIKAN AGAMA KRISTEN", "2", "AB", "7", "C", "2022-2023/1"));
        list.add(new NilaiDetail("7", "MUM1M", "PANCASILA", "2", "B", "6", "A", "2023-2024/1"));
        list.add(new NilaiDetail("8", "MUM3F", "BAHASA INDONESIA", "2", "B", "6", "A", "2023-2024/1"));
        list.add(new NilaiDetail("9", "TCS21C", "ALJABAR DAN MATRIKS", "3", "AB", "7", "B", "2024-2025/2"));
        list.add(new NilaiDetail("10", "TCS22B", "STATISTIKA DAN PROBABILITAS", "3", "A", "8", "B", "2024-2025/2"));
        list.add(new NilaiDetail("11", "TCS2FC", "TEORI BAHASA DAN OTOMATA", "3", "BC", "7.5", "A", "2024-2025/2"));
        list.add(new NilaiDetail("12", "TCS34A", "JARINGAN KOMPUTER", "3", "C", "0", "B", "2024-2025/2"));
        list.add(new NilaiDetail("13", "TCS35B", "INTEGRASI MAJALAH DAN KOMPUTER", "3", "A", "12", "B", "2024-2025/2"));
        list.add(new NilaiDetail("14", "TCS3AB", "ALGORITMA DAN STRUKTUR DATA", "3", "A", "12", "B", "2024-2025/2"));

        return list;
    }
    
    // Metode untuk menghitung total SKS dan IPK dari data di atas
    public Object[] getSummaryRow(ArrayList<NilaiDetail> data) {
        // Logika perhitungan (disini diabaikan, menggunakan dummy)
        return new Object[]{"", "Total", "", "37", "", "106", "", ""};
    }
    
    public Object[] getIPKRow(ArrayList<NilaiDetail> data) {
        return new Object[]{"", "IPK", "", "3.41", "", "", "", ""};
    }
}