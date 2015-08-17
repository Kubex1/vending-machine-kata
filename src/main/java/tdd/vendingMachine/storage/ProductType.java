package tdd.vendingMachine.storage;

import java.math.BigDecimal;

/**
 * Created by Jakub Janczyk on 2015-08-17.
 */
public enum ProductType {
    WATER_033("Water 0.33l", BigDecimal.valueOf(1.2)),
    CHOCOLATE_BAR("Chocolate Bar", BigDecimal.valueOf(2.0));

    private final String name;
    private final BigDecimal price;

    ProductType(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getName() {
        return this.name;
    }


}
