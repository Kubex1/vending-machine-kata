package tdd.vendingMachine.storage;

import org.junit.Before;
import org.junit.Test;
import tdd.vendingMachine.exceptions.EmptyShelfException;
import tdd.vendingMachine.exceptions.AddIncorrectProductTypeException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Jakub Janczyk on 2015-08-17.
 */
public class ShelfTest {
    private Shelf shelf;

    @Before
    public void setUp() throws Exception {
        shelf = new Shelf();
    }

    @Test
    public void testShelfHasTypeAsProduct() {
        shelf.add(ProductType.WATER_033);

        ProductType shelfType = shelf.getType();
        assertThat(shelfType).isEqualTo(ProductType.WATER_033);
        assertThat(shelf.productsOnShelf()).isEqualTo(1);
    }

    @Test
    public void testAddingManyProducts() {
        shelf.add(ProductType.WATER_033, 3);

        assertThat(shelf.getType()).isEqualTo(ProductType.WATER_033);
        assertThat(shelf.productsOnShelf()).isEqualTo(3);
    }

    @Test
    public void testTakingProductFromShelf() {
        shelf.add(ProductType.WATER_033);

        Product product = shelf.take();
        assertThat(shelf.productsOnShelf()).isEqualTo(0);
        assertThat(product.getType()).isEqualTo(ProductType.WATER_033);
    }

    @Test
    public void testTakingProductAndSomeRemainsOnShelf() {
        shelf.add(ProductType.WATER_033, 3);

        shelf.take();
        assertThat(shelf.productsOnShelf()).isEqualTo(2);
    }

    @Test(expected = EmptyShelfException.class)
    public void testTakingFromEmptyShelfGeneratesException() {
        shelf.take();
    }

    @Test(expected = EmptyShelfException.class)
    public void testEmptyShelfGeneratesExceptionIfCheckedForType() {
        shelf.getType();
    }

    @Test(expected = AddIncorrectProductTypeException.class)
    public void testAddingAnotherTypeOfProductGeneratesException() {
        shelf.add(ProductType.WATER_033);
        shelf.add(ProductType.CHOCOLATE_BAR);
    }

    @Test
    public void testGettingPrice() {
        shelf.add(ProductType.WATER_033);

        assertThat(shelf.getPrice()).isEqualTo(ProductType.WATER_033.getPrice());
    }

    @Test(expected = EmptyShelfException.class)
    public void testGettingPriceForEmptyShelf() {
        shelf.getPrice();
    }
}
