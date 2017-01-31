package agh.db.Relations;

/**
 * Created by Paweł Grochola on 30.01.2017.
 */
public class Price {
    private final Double pricePercent;
    private final Integer dayDifference;
    public Price(Double pricePercent, Integer dayDifference) {
        this.pricePercent = pricePercent;
        this.dayDifference = dayDifference;
    }
}