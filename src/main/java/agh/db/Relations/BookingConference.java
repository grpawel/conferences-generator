package agh.db.Relations;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Paweł Grochola on 31.01.2017.
 */
public class BookingConference {
    private final Integer bookingConferenceID;
    private final Integer conferenceID;
    private final Integer clientID;
    private final LocalDate bookingDate;
    private final List<BookingDay> bookingDayList;
    //private final List<ReservationDay> reservationDayList;

    public BookingConference(Integer bookingConferenceID, Integer conferenceID, Integer clientID, LocalDate bookingDate) {
        this.bookingConferenceID = bookingConferenceID;
        this.conferenceID = conferenceID;
        this.clientID = clientID;
        this.bookingDate = bookingDate;
    }
}
