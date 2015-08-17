package tdd.vendingMachine;

import tdd.vendingMachine.display.SimpleDisplay;

public class VendingMachine {

    private final SimpleDisplay display;

    public VendingMachine(SimpleDisplay display) {
        this.display = display;
    }

    public String getDisplayMessage() {
        return display.getCurrentMessage();
    }
}
