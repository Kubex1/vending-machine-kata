package tdd.vendingMachine.exceptions;

/**
 * Created by Jakub Janczyk on 2015-08-17.
 */
public class WrongShelfNumberException extends RuntimeException {
    public WrongShelfNumberException(int wantedShelf) {
        super("Shelf " + wantedShelf + " does not exists.");
    }
}
