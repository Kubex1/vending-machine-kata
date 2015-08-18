package tdd.vendingMachine.states;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tdd.vendingMachine.MachineContext;
import tdd.vendingMachine.exceptions.EmptyShelfException;
import tdd.vendingMachine.exceptions.WrongShelfNumberException;
import tdd.vendingMachine.money.Coin;
import tdd.vendingMachine.money.Price;

/**
 * Created by Jakub Janczyk on 2015-08-18.
 */
public class ProductSelectedState extends AbstractMachineState implements MachineState {

    public static final Logger LOGGER = LoggerFactory.getLogger(ProductSelectedState.class);

    private final int shelfNumber;

    public ProductSelectedState(MachineContext machineContext, int shelfNumber) {
        super(machineContext);

        this.shelfNumber = shelfNumber;
        initState();
    }

    private void initState() {
        try {
            Price productPrice = machineContext.getPriceForShelf(this.shelfNumber);
            this.display.setRequiredCoinsMessage(productPrice);
        } catch (WrongShelfNumberException | EmptyShelfException e) {
            LOGGER.debug(e.getMessage());
            machineContext.changeState(new WaitingState(machineContext));
        }
    }

    @Override
    public void handleInsert(Coin coin) {
        MachineState newState = new InsertingCoinsState(machineContext, shelfNumber, coin);
        machineContext.changeState(newState);
    }

    @Override
    public void handleCancel() {
        machineContext.changeState(new WaitingState(machineContext));
    }
}
