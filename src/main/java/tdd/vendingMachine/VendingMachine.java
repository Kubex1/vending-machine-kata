package tdd.vendingMachine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tdd.vendingMachine.display.SimpleDisplay;
import tdd.vendingMachine.exceptions.NotEnoughChangeException;
import tdd.vendingMachine.exceptions.WrongShelfNumberException;
import tdd.vendingMachine.money.Coin;
import tdd.vendingMachine.money.Price;
import tdd.vendingMachine.storage.Product;
import tdd.vendingMachine.storage.Storage;

import java.util.*;
import java.util.stream.IntStream;

public class VendingMachine {

    public static final Logger LOGGER = LoggerFactory.getLogger(VendingMachine.class);

    private final SimpleDisplay display;
    private final Storage storage;
    private int currentShelf;
    private Price remainingPrice;
    private List<Product> returnedProducts;
    private Map<Coin, Integer> availableCoins;
    private List<Coin> currentCoins;
    private List<Coin> change;

    public VendingMachine(SimpleDisplay display, Storage storage) {
        this.display = display;
        this.storage = storage;
        this.availableCoins = new HashMap<>();
        this.change = new LinkedList<>();
        this.returnedProducts = new LinkedList<>();
    }

    public void choose(int shelfNumber) {
        if (this.currentShelf >= 1) {
            return;
        }
        try {
            this.remainingPrice = storage.getPriceForShelf(shelfNumber);
        } catch (WrongShelfNumberException e) {
            LOGGER.debug("Wrong shelf number {}", shelfNumber, e);
            display.changeMessage("Product doesn't exist");
            return;
        }
        this.currentShelf = shelfNumber;
        this.currentCoins = new LinkedList<>();
        displayInsertMessage();
    }

    public void insert(Coin coin) {

        if (currentShelf < 1) {
            change = new LinkedList<>();
            change.add(coin);
            display.changeMessage(SimpleDisplay.INITIAL_MESSAGE);
            return;
        }

        remainingPrice = remainingPrice.subtract(coin.getPrice());
        addCurrentCoin(coin);

        if (remainingPrice.compareTo(Price.of(0.0)) > 0) {
            displayInsertMessage();
            return;
        }

        addAllCoinsToAvailable();

        try {
            change.addAll(prepareChange());
        } catch (NotEnoughChangeException e) {
            display.changeMessage("Unable to give change, returning inserted coins");
            change.addAll(currentCoins);
            return;
        }

        Product currentProduct = storage.takeProductFromShelf(currentShelf);
        returnedProducts.add(currentProduct);
        resetState();
    }

    public void cancel() {
        change.addAll(currentCoins);
        resetState();
    }

    public Product takeProduct() {
        Product toReturn = returnedProducts.stream().findFirst().orElse(null);
        returnedProducts.remove(toReturn);
        return toReturn;
    }

    public List<Coin> getReturnedCoins() {
        List<Coin> toReturn = new ArrayList<>(change);
        Collections.copy(toReturn, change);
        if (!change.isEmpty()) {
            change.clear();
        }
        return toReturn;
    }

    public String getDisplayMessage() {
        return display.getCurrentMessage();
    }

    private void addAllCoinsToAvailable() {
        for (Coin coin : currentCoins) {
            availableCoins.put(coin, availableCoins.getOrDefault(coin, 0) + 1);
        }
    }

    private void addCurrentCoin(Coin coin) {
        currentCoins.add(coin);
    }

    private void resetState() {
        display.changeMessage(SimpleDisplay.INITIAL_MESSAGE);
        this.currentCoins.clear();
        this.currentShelf = -1;
    }

    private void displayInsertMessage() {
        display.changeMessage("Insert " + remainingPrice + " zl");
    }

    private List<Coin> prepareChange() {
        Price remindingChange = remainingPrice.getNecessaryChange();
        List<Coin> allCoins = new LinkedList<>();

        availableCoins.forEach((coin, count) -> IntStream.range(0, count).forEach(value -> allCoins.add(coin)));

        allCoins.stream().sorted((c1, c2) -> c1.getPrice().compareTo(c2.getPrice()));

        List<Coin> currentChange = new LinkedList<>();

        for (Coin coin : allCoins) {
            if (canReturnCoin(coin, remindingChange)) {
                remindingChange = remindingChange.subtract(coin.getPrice());
                currentChange.add(coin);
            }
        }

        if (!remindingChange.isEqual(Price.of(0))) {
            throw new NotEnoughChangeException();
        }

        return currentChange;
    }

    private boolean canReturnCoin(Coin coin, Price remindingChange) {
        return coin.getPrice().compareTo(remindingChange) <= 0;
    }
}
