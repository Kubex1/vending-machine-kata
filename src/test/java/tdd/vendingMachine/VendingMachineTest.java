package tdd.vendingMachine;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class VendingMachineTest {

    private VendingMachine vendingMachine;

    @Before
    public void setUp() throws Exception {
        vendingMachine = new VendingMachine();
    }

    @Test
    public void testMachineDisplaysInitialDisplayText() throws Exception {
        String initialDisplayText = vendingMachine.getDisplayText();

        assertThat(initialDisplayText).isEqualTo(VendingMachine.INITIAL_DISPLAY_TEXT);
    }
}
