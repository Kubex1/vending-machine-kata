package tdd.vendingMachine.money;

import java.util.List;

/**
 * Created by Jakub Janczyk on 2015-08-18.
 */
public interface CoinDevice {

    List<Coin> takeChange();

    void addToChange(List<Coin> coin);

    void prepareChange(List<Coin> currentCoins, Price necessaryChange);
}
