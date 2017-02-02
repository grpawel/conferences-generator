package agh.db.Relations;

/**
 * Created by Paweł Grochola on 30.01.2017.
 */
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