package agh.db;

import agh.db.Relations.Conference;
import agh.db.Relations.ConferenceDay;
import agh.db.Relations.Price;
import agh.db.Relations.Workshop;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Paweł Grochola on 30.01.2017.
 */
public class Conferences {
    private final RandomData randomData;
    private final LocalDate firstConference = LocalDate.of(2014, 1, 1);
    private final LocalDate lastConferenceBefore = LocalDate.of(2017, 1, 1);
    private final Integer conferenceInterval = 15;
    private final Integer conferenceIntervalDeviation = 5;
    private LocalDate currentDate = firstConference;
    private final Integer conferenceLength = 2;
    private final Integer conferenceLengthDeviation = 1;
    private final Integer workshopsInDay = 4;
    private final Integer workshopsInDayDeviation = 4;
    private Integer currentConference = 0;
    private Integer currentConferenceDay = 0;
    private Integer currentWorkshop = 0;
    private Integer currentPrice = 0;
    private final List<Conference> conferenceList = new ArrayList<>();


    public Conferences(RandomData randomData) {
        this.randomData = randomData;
    }

    public List<Conference> getConferenceList() {
        return conferenceList;
    }

    public void generate() {
        while(currentDate.compareTo(lastConferenceBefore) < 0) {
            Conference conference = createConference();
            conferenceList.add(conference);
        }
    }

    public Conference createConference() {
        final Double price = ThreadLocalRandom.current().nextDouble(50, 251);
        final Integer length = conferenceLength +
                ThreadLocalRandom.current().nextInt(-conferenceLengthDeviation, conferenceLengthDeviation+1);
        final LocalDate endDate = currentDate.plusDays(length - 1);
        final String name = randomData.getConferenceNames().get(currentConference);
        final Double studentPrice = ThreadLocalRandom.current().nextDouble(0.5, 0.7);
        List<ConferenceDay> conferenceDays = IntStream.range(0, length)
                .mapToObj(i -> createConferenceDay(i + 1))
                .collect(Collectors.toList());
        final List<Price> prices = new Prices().generateRandomPrices();
        Conference conference = new Conference(currentConference, price, currentDate, endDate, name, studentPrice, conferenceDays, prices);
        currentConference++;
        currentDate = currentDate.plusDays(conferenceInterval + ThreadLocalRandom.current().nextInt(-conferenceIntervalDeviation, conferenceIntervalDeviation+1));
        return conference;
    }

    public ConferenceDay createConferenceDay(Integer dayNum) {
        final Integer capacity = ThreadLocalRandom.current().nextInt(10, 51);
        final Integer workshopsNum = workshopsInDay +
                ThreadLocalRandom.current().nextInt(-workshopsInDayDeviation, workshopsInDayDeviation+1);
        List<Workshop> workshops = IntStream.range(0, workshopsNum)
                .mapToObj(i -> createWorkshop(capacity, currentConferenceDay))
                .collect(Collectors.toList());
        ConferenceDay conferenceDay = new ConferenceDay(capacity, dayNum, currentConferenceDay, workshops);
        currentConferenceDay++;
        return conferenceDay;
    }

    public Workshop createWorkshop(Integer maxCapacity, Integer conferenceDayID) {
        final LocalTime startTime = randomData.getRandomDayTime();
        LocalTime endTime = startTime.plusHours(ThreadLocalRandom.current().nextInt(1, 5));
        if(endTime.isBefore(startTime)) {
            endTime = LocalTime.of(23,59);
        }
        final Integer capacity = ThreadLocalRandom.current().nextInt(5, maxCapacity);
        final Integer price = ThreadLocalRandom.current().nextInt(5, 151);
        Workshop workshop = new Workshop(
                currentWorkshop,
                randomData.getWorkshopNames().get(currentWorkshop),
                capacity,
                startTime,
                endTime,
                price, conferenceDayID);
        currentWorkshop++;
        return workshop;
    }


}