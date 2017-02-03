package agh.db.Relations;

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
