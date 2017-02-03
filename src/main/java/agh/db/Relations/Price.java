package agh.db.Relations;

public class Price {
    public final Integer priceID;
    public final Double pricePercent;
    public final Integer dayDifference;

    public Price(Integer priceID, Double pricePercent, Integer dayDifference) {
        this.priceID = priceID;
        this.pricePercent = pricePercent;
        this.dayDifference = dayDifference;
    }
}