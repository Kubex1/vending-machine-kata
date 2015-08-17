package tdd.vendingMachine;

import org.junit.Test;
import tdd.vendingMachine.display.SimpleDisplay;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Jakub Janczyk on 2015-08-17.
 */
public class SimpleDisplayTest {
    private SimpleDisplay display = new SimpleDisplay();

    @Test
    public void testDisplayHasInitialMessage() throws Exception {
        assertThat(display.getCurrentMessage()).isEqualTo(SimpleDisplay.INITIAL_MESSAGE);
    }
}
