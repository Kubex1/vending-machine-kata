package tdd.vendingMachine.money;

/**
 * Created by Jakub Janczyk on 2015-08-17.
 */
public enum Coin {
    TEN_GR(Price.of(0.1)),
    TWENTY_GR(Price.of(0.2)),
    FIFTY_GR(Price.of(0.5)),
    ONE_ZL(Price.of(1)),
    TWO_ZL(Price.of(2)),
    FIVE_ZL(Price.of(5));

    private final Price price;

    Coin(Price price) {
        this.price = price;
    }
}
