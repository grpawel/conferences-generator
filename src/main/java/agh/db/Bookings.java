package agh.db;

import agh.db.Relations.*;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Bookings {
    private final List<Client> clients;
    private final List<Conference> conferences;
    private final List<BookingConference> conferenceBookings = new ArrayList<>();
    private Integer currentReservationDay = 0;
    private Integer currentReservationWorkshop = 0;
    private Integer currentBookingDay = 0;
    private Integer currentBookingWorkshop = 0;
    private Integer currentBookingConference = 0;

    public Bookings(List<Client> clients, List<Conference> conferences) {
        this.clients = clients;
        this.conferences = conferences;
    }

    public List<BookingConference> getConferenceBookings() {
        return conferenceBookings;
    }

    public void generate() {
        for (Conference conference : conferences) {
            final List<Attendee> conferenceAttendees = getAttendeesListForDay(conference);
            final List<BookingDay> dayBookings = new ArrayList<>();
            for (ConferenceDay conferenceDay : conference.conferenceDays) {
                final List<Attendee> dayAttendees = new ArrayList<>();
                final List<Pair<List<Attendee>, Workshop>> allWorkshopsAttendees = new ArrayList<>();
                Integer dayCapacityToFill = ThreadLocalRandom.current().nextInt(0, conferenceDay.capacity * 2);
                if (dayCapacityToFill > conferenceDay.capacity) {
                    dayCapacityToFill = conferenceDay.capacity;
                }
                Integer currentAttendee = 0;
                for (Workshop workshop : conferenceDay.workshops) {
                    Integer workshopCapacityToFill = ThreadLocalRandom.current().nextInt(0, workshop.capacity * 2);
                    if (workshopCapacityToFill > workshop.capacity) {
                        workshopCapacityToFill = workshop.capacity;
                    }
                    final List<Attendee> workshopAttendees = new ArrayList<>();
                    while (workshopAttendees.size() < workshopCapacityToFill
                            && dayAttendees.size() < dayCapacityToFill) {
                        workshopAttendees.add(conferenceAttendees.get(currentAttendee));
                        dayAttendees.add(conferenceAttendees.get(currentAttendee));
                        currentAttendee++;
                    }
                    allWorkshopsAttendees.add(new Pair<>(workshopAttendees, workshop));
                }
                dayBookings.addAll(createBookingsForDay(conferenceDay, dayAttendees, allWorkshopsAttendees));
            }
            final Map<Integer, List<BookingDay>> clientDayBookings = dayBookings.stream()
                    .collect(Collectors.groupingBy(dayBooking -> dayBooking.clientID));
            final List<BookingConference> bookingConferences = clientDayBookings
                    .entrySet().stream()
                    .map(entry -> new BookingConference(
                            currentBookingConference++,
                            conference.conferenceID,
                            entry.getKey(),
                            conference.startDate.minusDays(ThreadLocalRandom.current().nextInt(14, 36)),
                            entry.getValue())).collect(Collectors.toList());
            conferenceBookings.addAll(bookingConferences);
        }
    }

    private List<BookingDay> createBookingsForDay(
            final ConferenceDay conferenceDay,
            final List<Attendee> dayAttendees,
            final List<Pair<List<Attendee>, Workshop>> allWorkshopAttendees) {
        final List<BookingDay> bookingDays =
                dayAttendees.stream()
                        .collect(Collectors.groupingBy(attendee -> attendee.clientID))
                        .entrySet().stream()
                        .map(entry -> new Pair<>(new Pair<>(entry.getKey(), currentBookingDay++), entry.getValue()))
                        .map(entry -> new BookingDay(
                                entry.getKey().getValue(),
                                conferenceDay.conferenceDayID,
                                Math.toIntExact(entry.getValue().stream().filter(attendee -> attendee.studentCard == null).count()),
                                Math.toIntExact(entry.getValue().stream().filter(attendee -> attendee.studentCard != null).count()),
                                entry.getValue().stream()
                                        .map(attendee -> new ReservationDay(attendee, currentReservationDay++)).collect(Collectors.toList()),
                                entry.getKey().getKey()))
                        .collect(Collectors.toList());
        final Map<Attendee, ReservationDay> attendeeReservationDayMap = bookingDays.stream()
                .map(bookingDay -> bookingDay.reservationDayList.stream())
                .flatMap(Function.identity())
                .collect(Collectors.toMap(reservationDay -> reservationDay.attendee, Function.identity()));
        for (BookingDay bookingDay : bookingDays) {
            final Integer clientID = bookingDay.clientID;
            final List<Pair<List<Attendee>, Workshop>> clientWorkshopAttendees = allWorkshopAttendees.stream()
                    .map(pair -> new Pair<>(
                            pair.getKey().stream().filter(attendee -> Objects.equals(attendee.clientID, clientID)).collect(Collectors.toList()),
                            pair.getValue()))
                    .filter(pair -> !pair.getKey().isEmpty())
                    .collect(Collectors.toList());
            final List<BookingWorkshop> bookingWorkshops = createAndAddBookingWorkshops(clientWorkshopAttendees, attendeeReservationDayMap);
            bookingWorkshops.forEach(bookingDay::addBookingWorkshop);
        }
        return bookingDays;

    }

    private Integer getMaxConferenceDayCapacity(final Conference conference) {
        return Math.max(
                conference.conferenceDays.stream()
                        .mapToInt(value -> value.workshops.stream()
                                .mapToInt(workshop -> workshop.capacity)
                                .sum())
                        .max().orElse(0),
                conference.conferenceDays.stream()
                        .mapToInt(value -> value.capacity)
                        .max().orElse(0));
    }

    private List<Attendee> getAttendeesListForDay(final Conference conference) {
        final Integer maxDayCapacity = getMaxConferenceDayCapacity(conference);
        Integer currentAttendees = 0;
        final List<Client> clientsForConference = new ArrayList<>();
        while (currentAttendees < maxDayCapacity) {
            final Client client = clients.get(ThreadLocalRandom.current().nextInt(0, clients.size()));
            if (!clientsForConference.contains(client)) {
                clientsForConference.add(client);
                currentAttendees += client.attendeeList.size();
            }
        }
        return clientsForConference.stream()
                .map(client -> client.attendeeList.stream())
                .flatMap(Function.identity())
                .collect(Collectors.toList());

    }

    private List<BookingWorkshop> createAndAddBookingWorkshops(final List<Pair<List<Attendee>, Workshop>> allWorkshopAttendees,
                                                               final Map<Attendee, ReservationDay> attendeeReservationDayMap) {

        List<Pair<BookingWorkshop, List<ReservationWorkshop>>> bookingWorkshops = allWorkshopAttendees.stream()
                .map(pair -> new Pair<>(
                        new BookingWorkshop(currentBookingWorkshop++, pair.getValue().workshopID, pair.getKey().size()),
                        pair.getKey()))
                .map(pair -> new Pair<>(
                        pair.getKey(),
                        pair.getValue().stream()
                                .map(attendee -> new ReservationWorkshop(
                                        currentReservationWorkshop++,
                                        pair.getKey().bookingWorkshopID, attendee)).collect(Collectors.toList())))
                .collect(Collectors.toList());
        bookingWorkshops.forEach(pair -> pair.getValue()
                .forEach(reservationWorkshop -> attendeeReservationDayMap
                        .get(reservationWorkshop.attendee)
                        .addReservationWorkshop(reservationWorkshop)));
        return bookingWorkshops.stream()
                .map(Pair::getKey)
                .collect(Collectors.toList());
    }
}
