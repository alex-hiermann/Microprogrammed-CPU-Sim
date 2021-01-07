package cpu_sim.command;

import cpu_sim.computer.Memory;
import cpu_sim.ui.App;

public class Dec extends Command {

    /**
     * first needed operator
     */
    private final int op1;

    /**
     * default constructor
     *
     * @param op1 first needed operator
     */
    public Dec(int op1) {
        this.op1 = op1;
    }

    /**
     * adds (op1 / number in memory at index op1) to (op2 / number in memory at index op2) and saves the result at op2
     */
    @Override
    public void function() {
        new Sub(op1, 1).function();
    }
}
