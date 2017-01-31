package agh.db.Relations;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Paweł Grochola on 30.01.2017.
 */
public class Conference {
    private final Integer conferenceID;
    private final Double pricePerDay;
    private final LocalDate startDate;
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

}
