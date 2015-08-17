package tdd.vendingMachine.storage;

/**
 * Created by Jakub Janczyk on 2015-08-17.
 */
public enum ProductType {
    WATER_033("Water 0.33l"),
    CHOCOLATE_BAR("Chocolate Bar");

    private final String name;

    ProductType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
