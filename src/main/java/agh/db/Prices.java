package agh.db;

import agh.db.Relations.Price;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Prices {
    private static List<Double> PRICES = Collections.unmodifiableList(Arrays.asList(0.25, 0.36, 0.55, 0.85));
    private static List<Integer> DAYS = Collections.unmodifiableList(Arrays.asList(10, 20, 30, 50));
    private static Integer priceID = 0;

    public List<Price> generateRandomPrices() {
        final int count = ThreadLocalRandom.current().nextInt(2, 5);
        final List<Integer> days = getRandomDays(count);
        final List<Double> prices = getRandomPrices(count);
        return IntStream.range(0, count)
                .mapToObj(i -> new Price(priceID++, prices.get(i), days.get(i)))
                .collect(Collectors.toList());
    }

    private List<Double> getRandomPrices(final int count) {
        List<Double> currentPrices = new ArrayList<>(PRICES);
        Collections.shuffle(currentPrices);
        currentPrices = currentPrices.stream()
                .limit(count)
                .map(price -> price * getMultiplier())
                .collect(Collectors.toList());
        currentPrices.sort(Double::compareTo);
        return currentPrices;
    }

    private List<Integer> getRandomDays(final int count) {
        List<Integer> currentDays = new ArrayList<>(DAYS);
        Collections.shuffle(currentDays);
        currentDays = currentDays.stream()
                .limit(count)
                .map(price -> price * getMultiplier())
                .map(Double::intValue)
                .collect(Collectors.toList());
        currentDays.sort(Comparator.reverseOrder());
        return currentDays;
    }

    private Double getMultiplier() {
        return ThreadLocalRandom.current().nextDouble(0.8, 1.15);
    }
}
