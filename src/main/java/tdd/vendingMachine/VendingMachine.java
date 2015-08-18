package tdd.vendingMachine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tdd.vendingMachine.display.SimpleDisplay;
import tdd.vendingMachine.money.Coin;
import tdd.vendingMachine.money.CoinDevice;
import tdd.vendingMachine.states.MachineState;
import tdd.vendingMachine.storage.Product;
import tdd.vendingMachine.storage.Storage;

import java.util.List;

public class VendingMachine {

    public static final Logger LOGGER = LoggerFactory.getLogger(VendingMachine.class);

    private final MachineContext context;

    public VendingMachine(SimpleDisplay display, Storage storage, CoinDevice coinDevice) {
        context = new MachineContext(display, storage, coinDevice);
    }

    public void choose(int shelfNumber) {
        machineState().handleChoose(shelfNumber);
    }

    public void insert(Coin coin) {
        machineState().handleInsert(coin);
    }

    public void cancel() {
        machineState().handleCancel();
    }

    public Product takeProduct() {
        return context.takeProduct();
    }

    public List<Coin> getReturnedCoins() {
        return context.takeChange();
    }

    public String getDisplayMessage() {
        return context.getDisplay().getCurrentMessage();
    }

    private MachineState machineState() {
        return context.getState();
    }
}
