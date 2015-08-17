package tdd.vendingMachine.exceptions;

/**
 * Created by Jakub Janczyk on 2015-08-17.
 */
public class EmptyShelfException extends RuntimeException {
    public EmptyShelfException() {
        super("Given shelf is empty!");
    }
}
