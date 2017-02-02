package agh.db;

import agh.db.Relations.BookingConference;
import agh.db.Relations.Client;
import agh.db.Relations.Conference;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

/**
 * Created by Paweł Grochola on 30.01.2017.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        RandomData randomData = new RandomData();
        randomData.read("MOCK_DATA.json");
        Conferences conferences = new Conferences(randomData);
        conferences.generate();
        Clients clients = new Clients(randomData);
        clients.generate();
        Bookings bookings = new Bookings(clients.getClientList(), conferences.getConferenceList());
        bookings.createBookings();
        String generated = "";
        generated += "USE konferencje\n" +
                "GO\n" +
                "EXEC sp_MSForEachTable 'DISABLE TRIGGER ALL ON ?'\n" +
                "GO\n" +
                "EXEC sp_MSForEachTable 'ALTER TABLE ? NOCHECK CONSTRAINT ALL'\n" +
                "GO\n" +
                "EXEC sp_MSForEachTable 'DELETE FROM ?'\n" +
                "GO\n" +
                "EXEC sp_MSForEachTable 'ALTER TABLE ? CHECK CONSTRAINT ALL'\n" +
                "GO\n" +
                "EXEC sp_MSForEachTable 'ENABLE TRIGGER ALL ON ?'\n" +
                "GO\n";
        generated += conferences.getConferenceList().stream().map(Conference::toSQL).collect(Collectors.joining("\n"));
        generated += "\n\n";
        generated += clients.getClientList().stream().map(Client::toSQL).collect(Collectors.joining("\n"));
        generated += "\n\n";
        generated += bookings.conferenceBookings.stream().map(BookingConference::toSQL).collect(Collectors.joining("\n"));
        Files.write(Paths.get("generated.sql"), generated.getBytes());
    }
}
