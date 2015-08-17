package tdd.vendingMachine;

import org.junit.Before;
import org.junit.Test;
import tdd.vendingMachine.display.SimpleDisplay;
import tdd.vendingMachine.storage.ProductType;
import tdd.vendingMachine.storage.Storage;

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
}
