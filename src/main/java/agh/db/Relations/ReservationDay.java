package agh.db.Relations;

import java.util.ArrayList;
import java.util.List;

public class ReservationDay {
    public final Attendee attendee;
    public final Integer reservationDayID;
    private final List<ReservationWorkshop> reservationWorkshopList;

    public ReservationDay(Attendee attendee, Integer id) {
        this.attendee = attendee;
        this.reservationDayID = id;
        this.reservationWorkshopList = new ArrayList<>();
    }

    public void addReservationWorkshop(ReservationWorkshop reservationWorkshop) {
        reservationWorkshopList.add(reservationWorkshop);
    }

    public List<ReservationWorkshop> getReservationWorkshopList() {
        return reservationWorkshopList;
    }
}
