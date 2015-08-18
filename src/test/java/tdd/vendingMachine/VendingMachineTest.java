package tdd.vendingMachine;

import org.junit.Before;
import org.junit.Test;
import tdd.vendingMachine.display.SimpleDisplay;
import tdd.vendingMachine.money.Coin;
import tdd.vendingMachine.money.CoinDevice;
import tdd.vendingMachine.money.Price;
import tdd.vendingMachine.money.SimpleCoinDevice;
import tdd.vendingMachine.storage.ProductType;
import tdd.vendingMachine.storage.Storage;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class VendingMachineTest {

    private final int SHELF_ONE = 1;
    private final int SHELF_TWO = 2;
    private VendingMachine vendingMachine;

    @Before
    public void setUp() throws Exception {
        SimpleDisplay display = new SimpleDisplay();
        Storage storage = new Storage().addShelfWithProducts(ProductType.WATER_033, 10).addShelfWithProducts
                (ProductType.CHOCOLATE_BAR, 5);
        CoinDevice coinDevice = new SimpleCoinDevice();
        vendingMachine = new VendingMachine(display, storage, coinDevice);
    }

    @Test
    public void testMachineDisplaysInitialDisplayText() throws Exception {
        String initialDisplayMessage = vendingMachine.getDisplayMessage();

        assertThat(initialDisplayMessage).isEqualTo(SimpleDisplay.INITIAL_MESSAGE);
    }

    @Test
    public void testMachineDisplaysPriceForFirstSelectedShelf() {
        vendingMachine.choose(SHELF_ONE);
        assertThat(vendingMachine.getDisplayMessage()).isEqualTo(getRequiredCoinsMessage(ProductType.WATER_033
                .getPrice()));
    }

    @Test
    public void testMachineDisplaysPriceForSecondSelectedShelf() {
        vendingMachine.choose(SHELF_TWO);
        assertThat(vendingMachine.getDisplayMessage()).isEqualTo(getRequiredCoinsMessage(ProductType.CHOCOLATE_BAR
                .getPrice()));
    }

    @Test
    public void testDisplayChangeAfterInsertingOneZl() {
        vendingMachine.choose(SHELF_ONE);
        vendingMachine.insert(Coin.PLN_1);

        assertThat(vendingMachine.getDisplayMessage()).isEqualTo(getRequiredCoinsMessage(ProductType.WATER_033
                .getPrice().subtract(Coin.PLN_1.getPrice())));
    }

    @Test
    public void testDisplayChangeAfterInsertingTenGr() {
        vendingMachine.choose(SHELF_ONE);
        vendingMachine.insert(Coin.GR_10);

        assertThat(vendingMachine.getDisplayMessage()).isEqualTo(getRequiredCoinsMessage(ProductType.WATER_033
                .getPrice().subtract(Coin.GR_10.getPrice())));
    }

    @Test
    public void testTakingProduct() {
        buyWater();

        assertThat(vendingMachine.takeProduct().getType()).isEqualTo(ProductType.WATER_033);
        assertThat(vendingMachine.getDisplayMessage()).isEqualTo(SimpleDisplay.INITIAL_MESSAGE);
    }

    @Test
    public void testTakingProductsTwiceReturnsNull() {
        buyWater();

        assertThat(vendingMachine.takeProduct()).isNotNull();
        assertThat(vendingMachine.takeProduct()).isNull();
    }

    @Test
    public void testNoReturnedCoinsIfExactPrice() {
        buyWater();

        assertThat(vendingMachine.getReturnedCoins()).isEqualTo(Collections.emptyList());
    }

    @Test
    public void testCoinsReturnedIfUnableToGiveChange() {
        insertTwoTimesOnePLNForWater();

        assertThat(vendingMachine.getDisplayMessage()).isEqualTo(SimpleDisplay.NO_COINS_MESSAGE);
        assertThat(vendingMachine.getReturnedCoins()).containsOnly(Coin.PLN_1, Coin.PLN_1);
        assertThat(vendingMachine.takeProduct()).isNull();
    }

    @Test
    public void testIsChangeGiven() {
        vendingMachine.choose(SHELF_ONE);

        vendingMachine.insert(Coin.PLN_1);
        vendingMachine.insert(Coin.GR_10);
        vendingMachine.insert(Coin.GR_20);
        vendingMachine.insert(Coin.GR_50);

        assertThat(vendingMachine.getReturnedCoins()).containsOnly(Coin.GR_10, Coin.GR_20);
        assertThat(vendingMachine.getDisplayMessage()).isEqualTo(SimpleDisplay.INITIAL_MESSAGE);
        assertThat(vendingMachine.takeProduct()).isNotNull();
    }

    @Test
    public void testChangeFromPreviousPurchase() {
        buyWater();

        assertThat(vendingMachine.getReturnedCoins()).isEmpty();

        insertTwoTimesOnePLNForWater();

        assertThat(vendingMachine.getReturnedCoins()).containsOnly(Coin.GR_50);
        assertThat(vendingMachine.getDisplayMessage()).isEqualTo(SimpleDisplay.INITIAL_MESSAGE);
        assertThat(vendingMachine.takeProduct()).isNotNull();
    }

    @Test
    public void testCoinReturnedIfNoProductChosen() {
        vendingMachine.insert(Coin.GR_50);

        assertThat(vendingMachine.getDisplayMessage()).isEqualTo(SimpleDisplay.INITIAL_MESSAGE);
        assertThat(vendingMachine.getReturnedCoins()).containsOnly(Coin.GR_50);
    }

    @Test
    public void testOnlyCurrentlyInsertedCoinsAreReturnedIfNoChange() {
        buyWater();
        vendingMachine.takeProduct();

        vendingMachine.choose(SHELF_ONE);
        vendingMachine.insert(Coin.PLN_5);
        assertThat(vendingMachine.getDisplayMessage()).isEqualTo(SimpleDisplay.NO_COINS_MESSAGE);
        assertThat(vendingMachine.getReturnedCoins()).containsOnly(Coin.PLN_5);
    }

    @Test
    public void testChangeIsNotLostIfNotCollectedImmediately() {
        insertTwoTimesOnePLNForWater();

        insertTwoTimesOnePLNForWater();

        assertThat(vendingMachine.getDisplayMessage()).isEqualTo(SimpleDisplay.NO_COINS_MESSAGE);
        assertThat(vendingMachine.getReturnedCoins()).containsOnly(Coin.PLN_1, Coin.PLN_1, Coin.PLN_1, Coin.PLN_1);
    }

    @Test
    public void testProductsAreNotLostIfNotCollectedImmediately() {
        buyWater();
        buyWater();

        assertThat(vendingMachine.takeProduct()).isNotNull();
        assertThat(vendingMachine.takeProduct()).isNotNull();
    }

    @Test
    public void testInitialDisplayMessageAfterCancel() {
        vendingMachine.choose(SHELF_ONE);
        vendingMachine.cancel();

        assertThat(vendingMachine.getDisplayMessage()).isEqualTo(SimpleDisplay.INITIAL_MESSAGE);
    }

    @Test
    public void testChoosingAnotherProductWithoutCancelNotWork() {
        vendingMachine.choose(SHELF_ONE);
        vendingMachine.choose(SHELF_TWO);

        assertThat(vendingMachine.getDisplayMessage()).isEqualTo(getRequiredCoinsMessage(ProductType.WATER_033
                .getPrice()));
    }

    @Test
    public void testChoosingAnotherProductWorksAfterCancel() {
        vendingMachine.choose(SHELF_ONE);
        vendingMachine.cancel();
        vendingMachine.choose(SHELF_TWO);

        assertThat(vendingMachine.getDisplayMessage()).isEqualTo(getRequiredCoinsMessage(ProductType.CHOCOLATE_BAR
                .getPrice()));
    }

    @Test
    public void testReturningInsertedCoinsAfterCancel() {
        vendingMachine.choose(SHELF_ONE);

        vendingMachine.insert(Coin.PLN_1);

        vendingMachine.cancel();

        assertThat(vendingMachine.getReturnedCoins()).containsOnly(Coin.PLN_1);
        assertThat(vendingMachine.takeProduct()).isNull();
    }

    @Test
    public void testNotChangingMessageForIncorrectShelfNumber() {
        vendingMachine.choose(10);

        assertThat(vendingMachine.getDisplayMessage()).isEqualTo(SimpleDisplay.INITIAL_MESSAGE);
    }

    @Test
    public void testCoinsNotAvailableAfterReturning() {
        buyWater();

        insertTwoTimesOnePLNForWater();

        vendingMachine.getReturnedCoins();

        insertTwoTimesOnePLNForWater();

        assertThat(vendingMachine.getDisplayMessage()).isEqualTo(SimpleDisplay.NO_COINS_MESSAGE);
        assertThat(vendingMachine.getReturnedCoins()).containsOnly(Coin.PLN_1, Coin.PLN_1);
    }

    private void insertTwoTimesOnePLNForWater() {
        vendingMachine.choose(SHELF_ONE);
        vendingMachine.insert(Coin.PLN_1);
        vendingMachine.insert(Coin.PLN_1);
    }

    private void buyWater() {
        vendingMachine.choose(SHELF_ONE);

        vendingMachine.insert(Coin.PLN_1);
        vendingMachine.insert(Coin.GR_50);
    }

    private String getRequiredCoinsMessage(Price value) {
        return String.format(SimpleDisplay.REQUIRED_COINS_MESSAGE, value);
    }
}
