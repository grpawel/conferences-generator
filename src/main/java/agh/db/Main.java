package agh.db;

import java.io.IOException;

/**
 * Created by Paweł Grochola on 30.01.2017.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        RandomData randomData = new RandomData();
        randomData.read("MOCK_DATA.json");
        randomData.getWorkshopNames().stream().limit(10).forEach(System.out::println);
        randomData.getConferenceNames().stream().limit(10).forEach(System.out::println);
        randomData.getPasswords().stream().limit(10).forEach(System.out::println);
        Conferences conferences = new Conferences(randomData);
        conferences.generate();
    }
}
