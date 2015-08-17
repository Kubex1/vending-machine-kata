package tdd.vendingMachine.display;

/**
 * Created by Jakub Janczyk on 2015-08-17.
 */
public class SimpleDisplay implements Display {
    public static final String INITIAL_MESSAGE = "Select Product";
    private String message;

    public SimpleDisplay() {
        this.message = INITIAL_MESSAGE;
    }

    public String getCurrentMessage() {
        return message;
    }

    @Override
    public void changeMessage(String message) {
        this.message = message;
    }
}
