package agh.db.Relations;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paweł Grochola on 31.01.2017.
 */
public class BookingDay {
    public final Integer bookingDayID;
    public final Integer conferenceDayID;
    public final Integer numberOfAttendees;
    public final Integer numberOfStudents;
    public final List<BookingWorkshop> bookingWorkshopList;
    public final List<ReservationDay> reservationDayList;
    public final Integer clientID;

    public BookingDay(Integer bookingDayID, Integer conferenceDayID, Integer numberOfAttendees, Integer numberOfStudents, List<ReservationDay> reservationDayList, Integer clientID) {
        this.bookingDayID = bookingDayID;
        this.conferenceDayID = conferenceDayID;
        this.numberOfAttendees = numberOfAttendees;
        this.numberOfStudents = numberOfStudents;
        this.clientID = clientID;
        this.bookingWorkshopList = new ArrayList<>();
        this.reservationDayList = reservationDayList;
    }
}
