package tdd.vendingMachine.display;

import tdd.vendingMachine.money.Price;

/**
 * Created by Jakub Janczyk on 2015-08-17.
 */
public class SimpleDisplay implements Display {
    public static final String INITIAL_MESSAGE = "Select product";
    public static final String NO_COINS_MESSAGE = "No coins for change. Select product.";
    public static final String REQUIRED_COINS_MESSAGE = "Insert %s PLN";
    private String message;

    public SimpleDisplay() {
        this.message = INITIAL_MESSAGE;
    }

    @Override
    public String getCurrentMessage() {
        return message;
    }

    @Override
    public void setRequiredCoinsMessage(Price productPrice) {
        this.message = String.format(REQUIRED_COINS_MESSAGE, productPrice);
    }

    @Override
    public void setInitialMessage(String message) {
        this.message = message;
    }
}
