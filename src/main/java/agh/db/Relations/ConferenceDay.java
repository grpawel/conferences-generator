package agh.db.Relations;

import java.util.Collections;
import java.util.List;

public class ConferenceDay {
    public final Integer capacity;
    public final Integer dayNum;
    public final Integer conferenceDayID;
    public final List<Workshop> workshops;

    public ConferenceDay(Integer capacity, Integer dayNum, Integer conferenceDayID, List<Workshop> workshops) {
        this.capacity = capacity;
        this.dayNum = dayNum;
        this.conferenceDayID = conferenceDayID;
        this.workshops = Collections.unmodifiableList(workshops);
    }
}
