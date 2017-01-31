package agh.db;

import agh.db.Relations.*;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Paweł Grochola on 31.01.2017.
 */
public class Bookings {
    private final List<Client> clients;
    private final List<Conference> conferences;
    private Integer currentReservationDay = 0;

    public Bookings(List<Client> clients, List<Conference> conferences) {
        this.clients = clients;
        this.conferences = conferences;
    }

    public void createBookings() {
        for (Conference conference : conferences) {
            final Integer maxDayCapacity = conference.conferenceDays.stream()
                    .mapToInt(value -> value.workshops.stream()
                            .mapToInt(workshop -> workshop.capacity)
                            .sum())
                    .max().orElse(0);
            Integer currentAttendees = 0;
            List<Client> clientsForConference = new ArrayList<>();
            while(currentAttendees < maxDayCapacity) {
                Client client = clients.get(ThreadLocalRandom.current().nextInt(0, clients.size()));
                if(!clientsForConference.contains(client)) {
                    clientsForConference.add(client);
                    currentAttendees += client.attendeeList.size();
                }
            }
            List<Pair<Attendee, Client>> attendeesClients = clients.stream()
                    .map(client -> client.attendeeList.stream().map(attendee -> new Pair<>(attendee, client)))
                    .flatMap(Function.identity())
                    .collect(Collectors.toList());
            for(ConferenceDay conferenceDay : conference.conferenceDays) {
                List<Pair<Attendee,Client>> dayAttendees = new ArrayList<>();
                Integer currentAttendee = 0;
                for(Workshop workshop : conferenceDay.workshops) {
                    List<Pair<Attendee,Client>> workshopAttendees = new ArrayList<>();
                    while(workshopAttendees.size() < workshop.capacity) {
                        workshopAttendees.add(attendeesClients.get(currentAttendee));
                        if(dayAttendees.size() < conferenceDay.capacity) {
                            dayAttendees.add(attendeesClients.get(currentAttendee));
                        }
                        currentAttendee++;
                    }

                }
            }

        }
    }
    public BookingConference createBookingDay()

    public ReservationDay createReservationDay(Attendee attendee) {
        ReservationDay reservationDay = new ReservationDay(attendee, currentReservationDay);
        currentReservationDay++;
        return reservationDay;
    }

    public ReservationWorkshop createReservationWorkshop(Attendee attendee) {

    }
}
