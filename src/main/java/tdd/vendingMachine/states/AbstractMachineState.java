package tdd.vendingMachine.states;

import tdd.vendingMachine.MachineContext;
import tdd.vendingMachine.display.Display;

/**
 * Created by Jakub Janczyk on 2015-08-18.
 */
public abstract class AbstractMachineState {

    protected final MachineContext machineContext;
    protected final Display display;

    protected AbstractMachineState(MachineContext machineContext) {
        this.machineContext = machineContext;
        display = machineContext.getDisplay();
    }
}
