package tdd.vendingMachine.money;

import java.math.BigDecimal;

/**
 * Created by Jakub Janczyk on 2015-08-17.
 */
public class Price implements Comparable<Price> {

    private final BigDecimal value;

    public static Price of(double value) {
        return new Price(BigDecimal.valueOf(value));
    }

    private Price(BigDecimal value) {
        this.value = value;
    }

    public Price subtract(Price price) {
        return new Price(this.value.subtract(price.value));
    }

    public boolean isEqual(Price price) {
        return this.compareTo(price) == 0;
    }

    public Price getNecessaryChange() {
        return new Price(this.value.negate());
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public int compareTo(Price price) {
        return this.value.compareTo(price.value);
    }
}
