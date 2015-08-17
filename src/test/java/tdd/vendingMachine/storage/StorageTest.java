package tdd.vendingMachine.storage;

import org.junit.Test;
import tdd.vendingMachine.exceptions.WrongShelfNumberException;
import tdd.vendingMachine.money.Price;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Jakub Janczyk on 2015-08-17.
 */
public class StorageTest {
    private final int SHELF_ONE = 1;
    private Storage storage;

    @Test(expected = WrongShelfNumberException.class)
    public void testTakingProductFromEmptyStorageFails() {
        initializeEmptyStorage();

        storage.takeProductFromShelf(SHELF_ONE);
    }

    @Test(expected = WrongShelfNumberException.class)
    public void testCheckingProductPriceFromEmptyStorageFails() {
        initializeEmptyStorage();

        storage.getPriceForShelf(SHELF_ONE);
    }

    @Test
    public void testAddingShelf() {
        initializeEmptyStorage();
        storage.addShelfWithProducts(ProductType.WATER_033, 10);

        assertThat(storage.getPriceForShelf(SHELF_ONE)).isEqualTo(ProductType.WATER_033.getPrice());
    }

    @Test
    public void testAddingShelfWithSpecifiedNumber() {
        int shelfNumber = 5;
        initializeEmptyStorage();
        storage.addShelfWithProducts(ProductType.WATER_033, 10, shelfNumber);

        assertThat(storage.getPriceForShelf(shelfNumber)).isEqualTo(ProductType.WATER_033.getPrice());
    }

    @Test
    public void testStorageInitializationFromList() {
        initializeWithSingleShelf();

        Product product = storage.takeProductFromShelf(SHELF_ONE);
        assertThat(product.getType()).isEqualTo(ProductType.WATER_033);
    }

    @Test
    public void testGettingProductPriceForShelf() {
        initializeWithSingleShelf();

        Price price = storage.getPriceForShelf(SHELF_ONE);

        assertThat(price).isEqualTo(ProductType.WATER_033.getPrice());
    }

    private void initializeEmptyStorage() {
        storage = new Storage();
    }

    private void initializeWithSingleShelf() {
        storage = new Storage().addShelfWithProducts(ProductType.WATER_033, 10);
    }
}
