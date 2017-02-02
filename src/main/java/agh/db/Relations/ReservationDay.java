package agh.db.Relations;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paweł Grochola on 31.01.2017.
 */
public class ReservationDay {
    public ReservationDay(Attendee attendee, Integer reservationDayID, List<ReservationWorkshop> reservationWorkshopList) {
        this.attendee = attendee;
        this.reservationDayID = reservationDayID;
        this.reservationWorkshopList = reservationWorkshopList;
    }

    public ReservationDay(Attendee attendee, Integer id) {
        this.attendee = attendee;
        this.reservationDayID = id;
        this.reservationWorkshopList = new ArrayList<>();
    }

    public final Attendee attendee;
    public final Integer reservationDayID;
    public final List<ReservationWorkshop> reservationWorkshopList;
}
