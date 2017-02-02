package agh.db.Relations;

/**
 * Created by Paweł Grochola on 02.02.2017.
 */
public class BookingWorkshop {
    public final Integer bookingWorkshopID;
    public final Integer workshopID;
    public final Integer numberOfAttendees;

    public BookingWorkshop(Integer bookingWorkshopID, Integer workshopID, Integer numberOfAttendees) {
        this.bookingWorkshopID = bookingWorkshopID;
        this.workshopID = workshopID;
        this.numberOfAttendees = numberOfAttendees;
    }
}
