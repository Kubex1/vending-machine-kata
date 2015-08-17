package tdd.vendingMachine.storage;

import tdd.vendingMachine.money.Price;

/**
 * Created by Jakub Janczyk on 2015-08-17.
 */
public enum ProductType {
    WATER_033("Water 0.33l", Price.of(1.5)),
    CHOCOLATE_BAR("Chocolate Bar", Price.of(3.2));

    private final String name;
    private final Price price;

    ProductType(String name, Price price) {
        this.name = name;
        this.price = price;
    }

    public Price getPrice() {
        return price;
    }

    public String getName() {
        return this.name;
    }


}
