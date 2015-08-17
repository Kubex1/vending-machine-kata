package tdd.vendingMachine;

import org.junit.Before;
import org.junit.Test;
import tdd.vendingMachine.display.SimpleDisplay;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Jakub Janczyk on 2015-08-17.
 */
public class SimpleDisplayTest {
    private SimpleDisplay display;

    @Before
    public void setUp() throws Exception {
        display = new SimpleDisplay();
    }

    @Test
    public void testDisplayHasInitialMessage() throws Exception {
        assertThat(display.getCurrentMessage()).isEqualTo(SimpleDisplay.INITIAL_MESSAGE);
    }

    @Test
    public void testChangingDisplayMessage() {
        String newMessage = "Test Message";
        display.changeMessage(newMessage);

        assertThat(display.getCurrentMessage()).isEqualTo(newMessage);
    }
}
