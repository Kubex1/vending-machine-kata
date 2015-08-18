package tdd.vendingMachine.money;

import tdd.vendingMachine.exceptions.NotEnoughChangeException;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Created by Jakub Janczyk on 2015-08-18.
 */
public class SimpleCoinDevice implements CoinDevice {

    private List<Coin> change;
    private Map<Coin, Integer> availableCoins;
    private Price remainingChange;

    public SimpleCoinDevice() {
        this.change = new LinkedList<>();
        this.availableCoins = new HashMap<>();
    }

    @Override
    public List<Coin> takeChange() {
        List<Coin> toReturn = new ArrayList<>(this.change);
        Collections.copy(toReturn, this.change);
        if (!this.change.isEmpty()) {
            this.change.clear();
        }
        return toReturn;
    }

    @Override
    public void addToChange(List<Coin> coins) {
        this.change.addAll(coins);
    }

    @Override
    public void prepareChange(List<Coin> currentCoins, Price remainingChange) {
        this.addAvailableCoins(currentCoins);
        this.remainingChange = remainingChange;

        List<Coin> allCoins = this.flatCoinsMapAsSortedList();

        List<Coin> currentChange = getCoinsForChange(allCoins);

        if (notEnoughChange()) {
            this.removeAvailableCoins(currentCoins);
            throw new NotEnoughChangeException();
        }

        this.removeAvailableCoins(currentChange);
        this.change = currentChange;
    }

    private void addAvailableCoins(List<Coin> coins) {
        for (Coin coin : coins) {
            this.availableCoins.put(coin, this.availableCoins.getOrDefault(coin, 0) + 1);
        }
    }

    private List<Coin> flatCoinsMapAsSortedList() {
        List<Coin> allCoins = new LinkedList<>();
        this.availableCoins
                .forEach((coin, count) ->
                        IntStream.range(0, count).forEach(value -> allCoins.add(coin)));
        allCoins.stream()
                .sorted((c1, c2) -> c1.getPrice().compareTo(c2.getPrice()));
        return allCoins;
    }

    private List<Coin> getCoinsForChange(List<Coin> allCoins) {
        List<Coin> currentChange = new LinkedList<>();

        allCoins.stream()
                .filter(this::coinCanBeAddedToChange)
                .forEach(coin -> {
                    this.remainingChange = this.remainingChange.subtract(coin.getPrice());
                    currentChange.add(coin);
                });
        return currentChange;
    }

    private boolean coinCanBeAddedToChange(Coin coin) {
        return coin.getPrice().compareTo(this.remainingChange) <= 0;
    }

    private boolean notEnoughChange() {
        return !this.remainingChange.isEqual(Price.of(0));
    }

    private void removeAvailableCoins(List<Coin> coinsToRemove) {
        for (Coin coin : coinsToRemove) {
            this.availableCoins.put(coin, this.availableCoins.getOrDefault(coin, 0) - 1);
        }
    }
}
