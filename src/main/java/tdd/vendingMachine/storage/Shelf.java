package tdd.vendingMachine.storage;

import tdd.vendingMachine.exceptions.AddIncorrectProductTypeException;
import tdd.vendingMachine.exceptions.EmptyShelfException;

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

    public void add(ProductType type) {
        this.add(type, 1);
    }

    public void add(ProductType type, int amount) {
        if (this.getSize() != 0 && !this.getType().equals(type)) {
            throw new AddIncorrectProductTypeException(this.getType(), type);
        }
        IntStream.range(0, amount).mapToObj(i -> new Product(type)).forEach(this.products::add);
    }

    public ProductType getType() {
        checkShelfHasProducts();
        return this.products.get(0).getType();
    }

    public int getSize() {
        return this.products.size();
    }

    public Product take() {
        checkShelfHasProducts();
        return this.products.remove(0);
    }

    private void checkShelfHasProducts() {
        if (this.getSize() == 0) {
            throw new EmptyShelfException();
        }
    }
}
