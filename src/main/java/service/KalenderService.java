package service;

import java.util.ArrayList;
import model.KalenderModel;

public class KalenderService {

    private final ArrayList<KalenderModel> eventList = new ArrayList<>();

    public KalenderService() {
        // Dummy Data
        eventList.add(new KalenderModel("Ujian Matematika", "2025-12-01"));
        eventList.add(new KalenderModel("Presentasi Project", "2025-12-05"));
    }

    public ArrayList<KalenderModel> getEvents() {
        return eventList;
    }

    public void addEvent(KalenderModel event) {
        eventList.add(event);
    }
}
