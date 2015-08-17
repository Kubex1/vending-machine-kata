package tdd.vendingMachine.money;

import java.math.BigDecimal;

/**
 * Created by Jakub Janczyk on 2015-08-17.
 */
public class Price {

    private final BigDecimal price;

    public static Price of(double price) {
        return new Price(price);
    }

    private Price(double price) {
        this.price = BigDecimal.valueOf(price);
    }

    @Override
    public String toString() {
        return price.toString();
    }
}
