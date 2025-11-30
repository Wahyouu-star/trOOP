package service;

import java.util.ArrayList;
import model.TagihanModel;

public class TagihanService {

    private final ArrayList<TagihanModel> tagihanList = new ArrayList<>();

    public TagihanService() {
        tagihanList.add(new TagihanModel("UKT Semester", 3500000, false));
        tagihanList.add(new TagihanModel("Praktikum Sistem Digital", 250000, true));
    }

    public ArrayList<TagihanModel> getTagihan() {
        return tagihanList;
    }

    public void updatePembayaran(String namaTagihan, boolean status) {
        for (TagihanModel t : tagihanList) {
            if (t.getNama().equals(namaTagihan)) {
                t.setLunas(status);
            }
        }
    }
}
 