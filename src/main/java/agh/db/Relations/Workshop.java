package agh.db.Relations;

import java.time.LocalTime;

/**
 * Created by Paweł Grochola on 30.01.2017.
 */
public class Workshop {
    public final Integer workshopID;
    public final String workshopName;
    public final Integer capacity;
    public final LocalTime startHour;
    public final LocalTime endHour;
    public final Integer price;

    public Workshop(Integer workshopID, String workshopName, Integer capacity, LocalTime startHour, LocalTime endHour, Integer price) {
        this.workshopID = workshopID;
        this.workshopName = workshopName;
        this.capacity = capacity;
        this.startHour = startHour;
        this.endHour = endHour;
        this.price = price;
    }
}
