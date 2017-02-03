package agh.db.Relations;

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
