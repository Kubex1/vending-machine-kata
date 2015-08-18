package tdd.vendingMachine.display;

import tdd.vendingMachine.money.Price;

/**
 * Created by Jakub Janczyk on 2015-08-17.
 */
public interface Display {
    String getCurrentMessage();

    void setRequiredCoinsMessage(Price productPrice);

    void setInitialMessage(String displayMessage);
}
