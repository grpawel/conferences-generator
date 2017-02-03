package agh.db;

import agh.db.Relations.BookingConference;
import agh.db.Relations.Client;
import agh.db.Relations.Conference;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println(Instant.now());
        final RandomData randomData = new RandomData();
        randomData.read("MOCK_DATA.json");
        final Conferences conferences = new Conferences(randomData);
        conferences.generate();
        final Clients clients = new Clients(randomData);
        clients.generate();
        final Bookings bookings = new Bookings(clients.getClientList(), conferences.getConferenceList());
        bookings.generate();
        String generated = "";
        generated += "USE konferencje\n" +
                "GO\n" +
                "-- remove previous data from database\n" +
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
        generated += bookings.getConferenceBookings().stream()
                .map(BookingConference::toSQL)
                .collect(Collectors.joining("\n"));
        Files.write(Paths.get("generated.sql"), generated.getBytes());
        System.out.println(Instant.now());
    }
}
