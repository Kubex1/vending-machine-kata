package tdd.vendingMachine.exceptions;

import tdd.vendingMachine.storage.ProductType;

/**
 * Created by Jakub Janczyk on 2015-08-17.
 */
public class AddIncorrectProductTypeException extends RuntimeException {
    public AddIncorrectProductTypeException(ProductType current, ProductType incorrect) {
        super("Cannot add products of type: " + incorrect.getName() + ". Shelf contains products of type: " + current.getName());
    }
}
