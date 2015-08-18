package tdd.vendingMachine;

import tdd.vendingMachine.display.Display;
import tdd.vendingMachine.money.Coin;
import tdd.vendingMachine.money.CoinDevice;
import tdd.vendingMachine.money.Price;
import tdd.vendingMachine.states.MachineState;
import tdd.vendingMachine.states.WaitingState;
import tdd.vendingMachine.storage.Product;
import tdd.vendingMachine.storage.Storage;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Jakub Janczyk on 2015-08-18.
 */
public class MachineContext {
    private final Display display;
    private final Storage storage;
    private final CoinDevice coinDevice;
    private MachineState machineState;
    private List<Product> returnedProducts;

    public MachineContext(Display display, Storage storage, CoinDevice coinDevice) {
        this.display = display;
        this.storage = storage;
        this.coinDevice = coinDevice;
        this.machineState = new WaitingState(this);
        this.returnedProducts = new LinkedList<>();
    }

    public void changeState(MachineState newState) {
        this.machineState = newState;
    }

    public MachineState getState() {
        return this.machineState;
    }

    public Display getDisplay() {
        return this.display;
    }

    public List<Coin> takeChange() {
        return this.coinDevice.takeChange();
    }

    public void addToChange(List<Coin> coins) {
        this.coinDevice.addToChange(coins);
    }

    public void prepareChange(List<Coin> currentCoins, Price necessaryChange) {
        this.coinDevice.prepareChange(currentCoins, necessaryChange);
    }

    public void returnProduct(int shelfNumber) {
        Product product = this.storage.takeProductFromShelf(shelfNumber);
        returnedProducts.add(product);
    }

    public Product takeProduct() {
        Product toReturn = returnedProducts.stream().findFirst().orElse(null);
        returnedProducts.remove(toReturn);
        return toReturn;
    }

    public Price getPriceForShelf(int shelfNumber) {
        return this.storage.getPriceForShelf(shelfNumber);
    }
}
