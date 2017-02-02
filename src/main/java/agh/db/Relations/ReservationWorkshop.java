package agh.db.Relations;

/**
 * Created by Paweł Grochola on 02.02.2017.
 */
public class ReservationWorkshop {
    public final Integer reservationWorkshopID;
    public final Integer bookingWorkshopID;
    public final Attendee attendee;

    public ReservationWorkshop(Integer reservationWorkshopID, Integer bookingWorkshopID, Attendee attendee) {
        this.reservationWorkshopID = reservationWorkshopID;
        this.bookingWorkshopID = bookingWorkshopID;
        this.attendee = attendee;
    }
}
