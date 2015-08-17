package tdd.vendingMachine.storage;

/**
 * Created by Jakub Janczyk on 2015-08-17.
 */
public class Product {
    private final ProductType type;

    public Product(ProductType type) {
        this.type = type;
    }



    public ProductType getType() {
        return type;
    }
}
