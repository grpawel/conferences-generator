package agh.db;

import agh.db.Relations.Attendee;
import agh.db.Relations.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Paweł Grochola on 31.01.2017.
 */
public class Clients {
    private Integer currentClient = 0;
    private Integer currentAttendee = 0;
    private final RandomData randomData;
    private final List<Client> clientList = new ArrayList<>();

    public Clients(RandomData randomData) {
        this.randomData = randomData;
    }

    public List<Client> getClientList() {
        return clientList;
    }

    public void generate() {
        while (currentAttendee < randomData.getNames().size()) {
            final Client client = createClient();
            clientList.add(client);
        }
    }

    public Client createClient() {
        final String name = randomData.getCompanyNames().get(currentClient);
        final String login = randomData.getLogins().get(currentClient);
        final String password = randomData.getPasswords().get(currentClient);
        final String email = randomData.getEmails().get(currentClient);
        final String phone = randomData.getPhones().get(currentClient);
        final Integer attendeeCount = ThreadLocalRandom.current().nextInt(5, 100);
        final List<Attendee> attendees = IntStream.range(0, attendeeCount)
                .mapToObj(i -> createAttendee())
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        final Client client = new Client(currentClient, name, login, password, false, email, phone, attendees);
        currentClient++;
        return client;
    }

    public Attendee createAttendee() {
        if (currentAttendee >= randomData.getNames().size()) {
            return null;
        }
        final String name = randomData.getNames().get(currentAttendee);
        final String studentCard = randomData.getStudentCards().get(currentAttendee);
        Attendee attendee = new Attendee(name, currentAttendee, studentCard, currentClient);
        currentAttendee++;
        return attendee;
    }
}
