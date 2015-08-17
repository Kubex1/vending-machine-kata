package tdd.vendingMachine;

import org.junit.Before;
import org.junit.Test;
import tdd.vendingMachine.display.SimpleDisplay;

import static org.assertj.core.api.Assertions.assertThat;

public class VendingMachineTest {

    private VendingMachine vendingMachine;

    @Before
    public void setUp() throws Exception {
        SimpleDisplay display = new SimpleDisplay();
        vendingMachine = new VendingMachine(display);
    }

    @Test
    public void testMachineDisplaysInitialDisplayText() throws Exception {
        String initialDisplayMessage = vendingMachine.getDisplayMessage();

        assertThat(initialDisplayMessage).isEqualTo(SimpleDisplay.INITIAL_MESSAGE);
    }
}
