package tdd.vendingMachine;

import org.junit.Before;
import org.junit.Test;
import tdd.vendingMachine.display.SimpleDisplay;
import tdd.vendingMachine.money.Coin;
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
        vendingMachine = new VendingMachine(display, storage);
    }

    @Test
    public void testMachineDisplaysInitialDisplayText() throws Exception {
        String initialDisplayMessage = vendingMachine.getDisplayMessage();

        assertThat(initialDisplayMessage).isEqualTo(SimpleDisplay.INITIAL_MESSAGE);
    }

    @Test
    public void testMachineDisplaysPriceForFirstSelectedShelf() {
        vendingMachine.choose(SHELF_ONE);
        assertThat(vendingMachine.getDisplayMessage()).isEqualTo("Insert 1.5 zl");
    }

    @Test
    public void testMachineDisplaysPriceForSecondSelectedShelf() {
        vendingMachine.choose(SHELF_TWO);
        assertThat(vendingMachine.getDisplayMessage()).isEqualTo("Insert 3.2 zl");
    }

    @Test
    public void testDisplayChangeAfterInsertingOneZl() {
        vendingMachine.choose(SHELF_ONE);
        vendingMachine.insert(Coin.ONE_ZL);

        assertThat(vendingMachine.getDisplayMessage()).isEqualTo("Insert 0.5 zl");
    }

    @Test
    public void testDisplayChangeAfterInsertingTenGr() {
        vendingMachine.choose(SHELF_ONE);
        vendingMachine.insert(Coin.TEN_GR);

        assertThat(vendingMachine.getDisplayMessage()).isEqualTo("Insert 1.4 zl");
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
        vendingMachine.choose(SHELF_ONE);

        vendingMachine.insert(Coin.ONE_ZL);
        vendingMachine.insert(Coin.ONE_ZL);

        assertThat(vendingMachine.getDisplayMessage()).isEqualTo("Unable to give change, returning inserted coins");
        assertThat(vendingMachine.getReturnedCoins()).containsOnly(Coin.ONE_ZL, Coin.ONE_ZL);
        assertThat(vendingMachine.takeProduct()).isNull();
    }

    @Test
    public void testIsChangeGiven() {
        vendingMachine.choose(SHELF_ONE);

        vendingMachine.insert(Coin.ONE_ZL);
        vendingMachine.insert(Coin.TEN_GR);
        vendingMachine.insert(Coin.TWENTY_GR);
        vendingMachine.insert(Coin.FIFTY_GR);

        assertThat(vendingMachine.getReturnedCoins()).containsOnly(Coin.TEN_GR, Coin.TWENTY_GR);
        assertThat(vendingMachine.getDisplayMessage()).isEqualTo(SimpleDisplay.INITIAL_MESSAGE);
        assertThat(vendingMachine.takeProduct()).isNotNull();
    }

    @Test
    public void testChangeFromPreviousPurchase() {
        buyWater();

        assertThat(vendingMachine.getReturnedCoins()).isEmpty();

        vendingMachine.choose(SHELF_ONE);
        vendingMachine.insert(Coin.ONE_ZL);
        vendingMachine.insert(Coin.ONE_ZL);

        assertThat(vendingMachine.getReturnedCoins()).containsOnly(Coin.FIFTY_GR);
        assertThat(vendingMachine.getDisplayMessage()).isEqualTo(SimpleDisplay.INITIAL_MESSAGE);
        assertThat(vendingMachine.takeProduct()).isNotNull();
    }

    @Test
    public void testCoinReturnedIfNoProductChosen() {
        vendingMachine.insert(Coin.FIFTY_GR);

        assertThat(vendingMachine.getDisplayMessage()).isEqualTo(SimpleDisplay.INITIAL_MESSAGE);
        assertThat(vendingMachine.getReturnedCoins()).containsOnly(Coin.FIFTY_GR);
    }

    @Test
    public void testOnlyCurrentlyInsertedCoinsAreReturnedIfNoChange() {
        buyWater();
        vendingMachine.takeProduct();

        vendingMachine.choose(SHELF_ONE);
        vendingMachine.insert(Coin.FIVE_ZL);
        assertThat(vendingMachine.getDisplayMessage()).isEqualTo("Unable to give change, returning inserted coins");
        assertThat(vendingMachine.getReturnedCoins()).containsOnly(Coin.FIVE_ZL);
    }

    @Test
    public void testChangeIsNotLostIfNotCollectedImmediately() {
        vendingMachine.choose(SHELF_ONE);
        vendingMachine.insert(Coin.ONE_ZL);
        vendingMachine.insert(Coin.ONE_ZL);

        vendingMachine.choose(SHELF_ONE);
        vendingMachine.insert(Coin.ONE_ZL);
        vendingMachine.insert(Coin.ONE_ZL);

        assertThat(vendingMachine.getDisplayMessage()).isEqualTo("Unable to give change, returning inserted coins");
        assertThat(vendingMachine.getReturnedCoins()).containsOnly(Coin.ONE_ZL, Coin.ONE_ZL, Coin.ONE_ZL, Coin.ONE_ZL);
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

        assertThat(vendingMachine.getDisplayMessage()).isEqualTo("Insert 1.5 zl");
    }

    @Test
    public void testChoosingAnotherProductWorksAfterCancel() {
        vendingMachine.choose(SHELF_ONE);
        vendingMachine.cancel();
        vendingMachine.choose(SHELF_TWO);

        assertThat(vendingMachine.getDisplayMessage()).isEqualTo("Insert 3.2 zl");
    }

    @Test
    public void testReturningInsertedCoinsAfterCancel() {
        vendingMachine.choose(SHELF_ONE);

        vendingMachine.insert(Coin.ONE_ZL);

        vendingMachine.cancel();

        assertThat(vendingMachine.getReturnedCoins()).containsOnly(Coin.ONE_ZL);
        assertThat(vendingMachine.takeProduct()).isNull();
    }

    @Test
    public void testDisplayingMessageForIncorrectShelfNumber() {
        vendingMachine.choose(10);

        assertThat(vendingMachine.getDisplayMessage()).isEqualTo("Product doesn't exist");
    }

    private void buyWater() {
        vendingMachine.choose(SHELF_ONE);

        vendingMachine.insert(Coin.ONE_ZL);
        vendingMachine.insert(Coin.FIFTY_GR);
    }
}
