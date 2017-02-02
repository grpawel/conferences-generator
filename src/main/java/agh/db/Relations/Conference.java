package agh.db.Relations;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Paweł Grochola on 30.01.2017.
 */
public class Conference {
    public final Integer conferenceID;
    private final Double pricePerDay;
    public final LocalDate startDate;
    private final LocalDate endDate;
    private final String conferenceName;
    private final Double studentPricePercent;
    public final List<ConferenceDay> conferenceDays;
    private final List<Price> prices;

    public Conference(Integer conferenceID, Double pricePerDay, LocalDate startDate, LocalDate endDate, String conferenceName, Double studentPricePercent, List<ConferenceDay> conferenceDays, List<Price> prices) {
        this.conferenceID = conferenceID;
        this.pricePerDay = pricePerDay;
        this.startDate = startDate;
        this.endDate = endDate;
        this.conferenceName = conferenceName;
        this.studentPricePercent = studentPricePercent;
        this.conferenceDays = conferenceDays;
        this.prices = prices;
    }

    @Override
    public String toString() {
        return "Conference{" +
                "conferenceID=" + conferenceID +
                ", pricePerDay=" + pricePerDay +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", conferenceName='" + conferenceName + '\'' +
                ", studentPricePercent=" + studentPricePercent +
                ", conferenceDays=" + conferenceDays +
                '}';
    }

    public String toSQL() {
        String result = "";
        result += "PRINT 'Inserting conference #" + conferenceID + "'\n";
        result += "SET IDENTITY_INSERT Conferences ON\n";
        result += String.format(Locale.US, "INSERT INTO Conferences (" +
                        "ConferenceID, PricePerDay, StartDate, EndDate, ConferenceName, StudentPricePercent" +
                        ")\nVALUES (%d, %f, '%s', '%s', '%s', %f)\n",
                conferenceID, pricePerDay, startDate.format(DateTimeFormatter.ISO_LOCAL_DATE), endDate.format(DateTimeFormatter.ISO_LOCAL_DATE), conferenceName, studentPricePercent);
        result += "SET IDENTITY_INSERT Conferences OFF\n";
        if (!conferenceDays.isEmpty()) {
            result += "SET IDENTITY_INSERT ConferenceDays ON\n";
            result += "INSERT INTO ConferenceDays (ConferenceDayID, Capacity, DayNum, ConferenceID)\nVALUES\n";
            result += conferenceDays.stream()
                    .map(conferenceDay -> String.format("\t(%d, %d, %d, %d)",
                            conferenceDay.conferenceDayID, conferenceDay.capacity, conferenceDay.dayNum, conferenceID))
                    .collect(Collectors.joining(",\n"));
            result += "\nSET IDENTITY_INSERT ConferenceDays OFF\n";
            Stream<Workshop> workshopStream = conferenceDays.stream()
                    .flatMap(conferenceDay -> conferenceDay.workshops.stream());
            if (workshopStream.findAny().isPresent()) {
                result += "SET IDENTITY_INSERT Workshops ON\n";
                result += "INSERT INTO Workshops (WorkshopID, ConferenceDayID, Capacity, StartHour, EndHour, Price, WorkshopName)\nVALUES\n";
                result += conferenceDays.stream()
                        .flatMap(conferenceDay -> conferenceDay.workshops.stream())
                        .map(workshop -> String.format(Locale.US,
                                "\t(%d, %d, %d, '%s', '%s', %d, '%s')",
                                workshop.workshopID, workshop.conferenceDayID, workshop.capacity,
                                workshop.startHour.format(DateTimeFormatter.ISO_LOCAL_TIME), workshop.endHour.format(DateTimeFormatter.ISO_LOCAL_TIME),
                                workshop.price, workshop.workshopName)
                        )
                        .collect(Collectors.joining(",\n"));
                result += "\nSET IDENTITY_INSERT Workshops OFF\n";
            }
        }
        if (!prices.isEmpty()) {
            result += "SET IDENTITY_INSERT Prices ON\n";
            result += prices.stream()
                    .map(price -> String.format(Locale.US,
                            "INSERT INTO Prices (PriceID, ConferenceID, DayDifference, PricePercent)\nVALUES\n" +
                                    "\t(%d, %d, %d, %f)",
                            price.priceID, conferenceID, price.dayDifference, price.pricePercent))
                    .collect(Collectors.joining("\n"));
            result += "\nSET IDENTITY_INSERT Prices OFF\n";
        }
        return result;
    }

}
