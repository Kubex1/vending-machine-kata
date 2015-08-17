package tdd.vendingMachine;

import tdd.vendingMachine.display.SimpleDisplay;
import tdd.vendingMachine.money.Price;
import tdd.vendingMachine.storage.Storage;

import java.math.BigDecimal;

public class VendingMachine {

    private final SimpleDisplay display;
    private final Storage storage;

    public VendingMachine(SimpleDisplay display, Storage storage) {
        this.display = display;
        this.storage = storage;
    }

    public void choose(int shelfNumber) {
        Price price = storage.getPriceForShelf(shelfNumber);
        display.changeMessage("Insert " + price + " zl");
    }

    public String getDisplayMessage() {
        return display.getCurrentMessage();
    }
}
