package service;

import java.util.ArrayList;
import model.NotifikasiModel;

public class NotifikasiService {

    private final ArrayList<NotifikasiModel> notifList = new ArrayList<>();

    public NotifikasiService() {
        notifList.add(new NotifikasiModel("Deadline Tugas Besok", "Jangan lupa submit"));
        notifList.add(new NotifikasiModel("Rapat kelompok jam 10", "Google meet link"));
    }

    public ArrayList<NotifikasiModel> getNotifications() {
        return notifList;
    }

    public void addNotification(NotifikasiModel notif) {
        notifList.add(notif);
    }
}
