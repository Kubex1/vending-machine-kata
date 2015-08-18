package tdd.vendingMachine.money;

/**
 * Created by Jakub Janczyk on 2015-08-17.
 */
public enum Coin {
    GR_10(Price.of(0.1)),
    GR_20(Price.of(0.2)),
    GR_50(Price.of(0.5)),
    PLN_1(Price.of(1)),
    PLN_2(Price.of(2)),
    PLN_5(Price.of(5));

    private final Price price;

    Coin(Price price) {
        this.price = price;
    }

    public Price getPrice() {
        return price;
    }
}
