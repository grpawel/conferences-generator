package agh.db.Relations;

import javafx.util.Pair;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BookingConference {
    public final Integer bookingConferenceID;
    public final Integer conferenceID;
    public final Integer clientID;
    public final LocalDate bookingDate;
    public final List<BookingDay> bookingDayList;

    public BookingConference(Integer bookingConferenceID, Integer conferenceID, Integer clientID, LocalDate bookingDate, List<BookingDay> bookingDayList) {
        this.bookingConferenceID = bookingConferenceID;
        this.conferenceID = conferenceID;
        this.clientID = clientID;
        this.bookingDate = bookingDate;
        this.bookingDayList = Collections.unmodifiableList(bookingDayList);
    }

    public String toSQL() {
        String result = "";
        result += "PRINT 'Inserting bookings #" + bookingConferenceID + "'\n";
        result += "SET IDENTITY_INSERT BookingConference ON\n";
        result += "INSERT INTO BookingConference (BookingConferenceID, ClientID, ConferenceID, BookingDate, IsCancelled)\n";
        result += String.format(Locale.US, "VALUES\n\t(%d, %d, %d, '%s', %d)",
                bookingConferenceID, clientID, conferenceID, bookingDate.format(DateTimeFormatter.ISO_LOCAL_DATE), 0);
        result += "\nSET IDENTITY_INSERT BookingConference OFF\n";

        if (!bookingDayList.isEmpty()) {
            result += "SET IDENTITY_INSERT BookingDay ON\n";
            result += "INSERT INTO BookingDay (BookingDayID, ConferenceDayID, BookingConferenceID, NumberOfAttendees, NumberOfStudents, isCancelled)\nVALUES\n";
            result += bookingDayList.stream()
                    .map(bookingDay -> String.format(Locale.US, "\t(%d, %d, %d, %d, %d, %d)",
                            bookingDay.bookingDayID, bookingDay.conferenceDayID, bookingConferenceID, bookingDay.numberOfAttendees, bookingDay.numberOfStudents, 0))
                    .collect(Collectors.joining(",\n"));
            result += "\nSET IDENTITY_INSERT BookingDay OFF\n";

            final Stream<BookingWorkshop> bookingWorkshopStream = bookingDayList.stream()
                    .flatMap(bookingDay -> bookingDay.getBookingWorkshopList().stream());
            if (bookingWorkshopStream.findAny().isPresent()) {
                result += "SET IDENTITY_INSERT BookingWorkshop ON\n";
                result += "INSERT INTO BookingWorkshop (BookingWorkshopID, BookingDayID, WorkshopID, NumberOfAttendees, isCancelled)\nVALUES\n";
                result += bookingDayList.stream()
                        .map(bookingDay -> bookingDay.getBookingWorkshopList().stream()
                                .map(bookingWorkshop -> new Pair<>(bookingDay.bookingDayID, bookingWorkshop)))
                        .flatMap(Function.identity())
                        .map(pair -> String.format(Locale.US, "\t(%d, %d, %d, %d, %d)",
                                pair.getValue().bookingWorkshopID, pair.getKey(), pair.getValue().workshopID, pair.getValue().numberOfAttendees, 0))
                        .collect(Collectors.joining(",\n"));
                result += "\nSET IDENTITY_INSERT BookingWorkshop OFF\n";
            }

            final Stream<ReservationDay> reservationDayStream = bookingDayList.stream().flatMap(bookingDay -> bookingDay.reservationDayList.stream());
            if (reservationDayStream.findAny().isPresent()) {
                result += "SET IDENTITY_INSERT ReservationDay ON\n";
                result += "INSERT INTO ReservationDay (ReservationDayID, BookingDayID, AttendeeID, StudentCard)\nVALUES\n";
                result += bookingDayList.stream()
                        .map(bookingDay -> bookingDay.reservationDayList.stream()
                                .map(reservationDay -> new Pair<>(bookingDay.bookingDayID, reservationDay)))
                        .flatMap(Function.identity())
                        .map(pair -> String.format(Locale.US, "\t(%d, %d, %d, %s)",
                                pair.getValue().reservationDayID, pair.getKey(), pair.getValue().attendee.attendeeID,
                                pair.getValue().attendee.studentCard == null ? "NULL" : "'" + pair.getValue().attendee.studentCard + "'"))
                        .collect(Collectors.joining(",\n"));
                result += "\nSET IDENTITY_INSERT ReservationDay OFF\n";

                final Stream<ReservationWorkshop> reservationWorkshopStream = bookingDayList.stream()
                        .flatMap(bookingDay -> bookingDay.reservationDayList.stream()
                                .flatMap(reservationDay -> reservationDay.getReservationWorkshopList().stream()));
                if (reservationWorkshopStream.findAny().isPresent()) {
                    result += "SET IDENTITY_INSERT ReservationWorkshop ON\n";
                    result += "INSERT INTO ReservationWorkshop (ReservationWorkshopID, ReservationDayID, BookingWorkshopID)\nVALUES\n";
                    result += bookingDayList.stream()
                            .map(bookingDay -> bookingDay.reservationDayList.stream()
                                    .map(reservationDay -> reservationDay.getReservationWorkshopList().stream()
                                            .map(reservationWorkshop -> new Pair<>(reservationDay.reservationDayID, reservationWorkshop)))
                                    .flatMap(Function.identity()))
                            .flatMap(Function.identity())
                            .map(pair -> String.format(
                                    "\t(%d, %d, %d)",
                                    pair.getValue().reservationWorkshopID, pair.getKey(), pair.getValue().bookingWorkshopID))
                            .collect(Collectors.joining(",\n"));
                    result += "\nSET IDENTITY_INSERT ReservationWorkshop OFF\n";
                }
            }
        }
        return result;
    }
}
