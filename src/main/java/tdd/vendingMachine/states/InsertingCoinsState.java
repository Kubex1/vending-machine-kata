package tdd.vendingMachine.states;

import tdd.vendingMachine.MachineContext;
import tdd.vendingMachine.display.SimpleDisplay;
import tdd.vendingMachine.exceptions.NotEnoughChangeException;
import tdd.vendingMachine.money.Coin;
import tdd.vendingMachine.money.Price;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Jakub Janczyk on 2015-08-18.
 */
public class InsertingCoinsState extends AbstractMachineState implements MachineState {
    private final int shelfNumber;
    private List<Coin> currentCoins;
    private Price remainingPrice;

    public InsertingCoinsState(MachineContext machineContext, int shelfNumber, Coin currentCoin) {
        super(machineContext);
        this.shelfNumber = shelfNumber;
        initState(currentCoin);
    }

    private void initState(Coin currentCoin) {
        this.currentCoins = new LinkedList<>();
        this.remainingPrice = this.machineContext.getPriceForShelf(this.shelfNumber);
        handleInsert(currentCoin);
    }

    @Override
    public void handleInsert(Coin coin) {
        this.remainingPrice = this.remainingPrice.subtract(coin.getPrice());
        this.currentCoins.add(coin);

        if (notEnoughCoinsInserted()) {
            this.display.setRequiredCoinsMessage(this.remainingPrice);
            return;
        }

        handlePurchase();
    }

    @Override
    public void handleCancel() {
        this.machineContext.addToChange(this.currentCoins);
        resetStateToWaiting(SimpleDisplay.INITIAL_MESSAGE);
    }

    private boolean notEnoughCoinsInserted() {
        return this.remainingPrice.compareTo(Price.of(0.0)) > 0;
    }

    private void handlePurchase() {
        String message = SimpleDisplay.INITIAL_MESSAGE;
        try {
            preparePurchaseResults();
        } catch (NotEnoughChangeException e) {
            machineContext.addToChange(currentCoins);
            message = SimpleDisplay.NO_COINS_MESSAGE;
        } finally {
            resetStateToWaiting(message);
        }
    }

    private void preparePurchaseResults() {
        this.machineContext.prepareChange(this.currentCoins, this.remainingPrice.getNecessaryChange());
        this.machineContext.returnProduct(this.shelfNumber);
    }

    private void resetStateToWaiting(String message) {
        this.machineContext.changeState(new WaitingState(machineContext, message));
    }
}