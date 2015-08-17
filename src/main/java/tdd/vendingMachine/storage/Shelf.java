package tdd.vendingMachine.storage;

import tdd.vendingMachine.exceptions.AddIncorrectProductTypeException;
import tdd.vendingMachine.exceptions.EmptyShelfException;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by Jakub Janczyk on 2015-08-17.
 */
public class Shelf {

    private List<Product> products;

    public Shelf() {
        this.products = new LinkedList<>();
    }

    public Shelf add(ProductType type) {
        return this.add(type, 1);
    }

    public Shelf add(ProductType type, int amount) {
        if (typesNotMatch(type)) {
            throw new AddIncorrectProductTypeException(this.getType(), type);
        }
        IntStream.range(0, amount).mapToObj(i -> new Product(type)).forEach(this.products::add);
        return this;
    }

    public ProductType getType() {
        checkIfShelfHasProducts();
        return this.products.get(0).getType();
    }

    public int productsOnShelf() {
        return this.products.size();
    }

    public Product take() {
        checkIfShelfHasProducts();
        return this.products.remove(0);
    }

    public BigDecimal getPrice() {
        checkIfShelfHasProducts();
        return this.getType().getPrice();
    }

    private boolean typesNotMatch(ProductType type) {
        return this.productsOnShelf() != 0 && !this.getType().equals(type);
    }

    private void checkIfShelfHasProducts() {
        if (this.productsOnShelf() == 0) {
            throw new EmptyShelfException();
        }
    }
}
