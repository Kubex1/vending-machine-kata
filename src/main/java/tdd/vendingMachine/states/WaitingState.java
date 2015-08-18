package tdd.vendingMachine.states;

import tdd.vendingMachine.MachineContext;
import tdd.vendingMachine.display.SimpleDisplay;
import tdd.vendingMachine.money.Coin;

import java.util.Collections;

/**
 * Created by Jakub Janczyk on 2015-08-18.
 */
public class WaitingState extends AbstractMachineState implements MachineState {

    public WaitingState(MachineContext machineContext) {
        this(machineContext, SimpleDisplay.INITIAL_MESSAGE);
    }

    public WaitingState(MachineContext machineContext, String displayMessage) {
        super(machineContext);
        this.display.setInitialMessage(displayMessage);
    }

    @Override
    public void handleChoose(int shelfNumber) {
        MachineState newState = new ProductSelectedState(machineContext, shelfNumber);
        machineContext.changeState(newState);
    }

    @Override
    public void handleInsert(Coin coin) {
        machineContext.addToChange(Collections.singletonList(coin));
    }
}
