package agh.db.Relations;

import java.util.List;

/**
 * Created by Paweł Grochola on 30.01.2017.
 */
public class ConferenceDay {
    public final Integer capacity;
    private final Integer dayNum;
    private final Integer conferenceDayID;
    public final List<Workshop> workshops;
    public ConferenceDay(Integer capacity, Integer dayNum, Integer conferenceDayID, List<Workshop> workshops) {
        this.capacity = capacity;
        this.dayNum = dayNum;
        this.conferenceDayID = conferenceDayID;
        this.workshops = workshops;
    }
}
