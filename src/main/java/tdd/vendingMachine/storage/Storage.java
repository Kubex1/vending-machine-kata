package tdd.vendingMachine.storage;

import tdd.vendingMachine.exceptions.WrongShelfNumberException;
import tdd.vendingMachine.money.Price;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jakub Janczyk on 2015-08-17.
 */
public class Storage {

    private final Map<Integer, Shelf> storage;

    public Storage() {
        this.storage = new HashMap<>();
    }

    public Storage addShelfWithProducts(ProductType type, int amount) {
        int maximumNumber = storage.keySet().stream().max(Integer::max).orElse(0);
        return this.addShelfWithProducts(type, amount, maximumNumber + 1);
    }

    public Storage addShelfWithProducts(ProductType type, int amount, int shelfNumber) {
        Shelf shelf = new Shelf().add(type, amount);
        this.storage.put(shelfNumber, shelf);
        return this;
    }

    public Price getPriceForShelf(int shelfNumber) {
        Shelf shelf = getShelf(shelfNumber);
        return shelf.getPrice();
    }

    public Product takeProductFromShelf(int shelfNumber) {
        Shelf shelf = getShelf(shelfNumber);
        return shelf.take();
    }

    private Shelf getShelf(int shelfNumber) {
        Shelf shelf = storage.get(shelfNumber);
        if (shelf == null) {
            throw new WrongShelfNumberException(shelfNumber);
        }
        return shelf;
    }
}
