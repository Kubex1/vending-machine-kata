package tdd.vendingMachine.states;

import tdd.vendingMachine.money.Coin;

/**
 * Created by Jakub Janczyk on 2015-08-18.
 */
public interface MachineState {
    default void handleChoose(int shelfNumber) {
    }

    default void handleInsert(Coin coin) {
    }

    default void handleCancel() {
    }
}
