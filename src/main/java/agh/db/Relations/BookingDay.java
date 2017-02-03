package agh.db.Relations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookingDay {
    public final Integer bookingDayID;
    public final Integer conferenceDayID;
    public final Integer numberOfAttendees;
    public final Integer numberOfStudents;
    private final List<BookingWorkshop> bookingWorkshopList;
    public final List<ReservationDay> reservationDayList;
    public final Integer clientID;

    public BookingDay(Integer bookingDayID, Integer conferenceDayID, Integer numberOfAttendees,
                      Integer numberOfStudents, List<ReservationDay> reservationDayList, Integer clientID) {
        this.bookingDayID = bookingDayID;
        this.conferenceDayID = conferenceDayID;
        this.numberOfAttendees = numberOfAttendees;
        this.numberOfStudents = numberOfStudents;
        this.clientID = clientID;
        this.bookingWorkshopList = new ArrayList<>();
        this.reservationDayList = Collections.unmodifiableList(reservationDayList);
    }

    public void addBookingWorkshop(BookingWorkshop bookingWorkshop) {
        bookingWorkshopList.add(bookingWorkshop);
    }

    public List<BookingWorkshop> getBookingWorkshopList() {
        return bookingWorkshopList;
    }
}
