package cpu_sim.command;

import cpu_sim.computer.Memory;
import cpu_sim.ui.App;

public class Not extends Command {

    /**
     * first needed operator
     */
    private final int op1;

    /**
     * default constructor
     *
     * @param op1 first needed operator
     */
    public Not(int op1) {
        this.op1 = op1;
    }

    /**
     * flips all bit values of the number at the location op1
     * WARNING: NOT RECOMMENDED TO USE! INTEGER OVERFLOWS POSSIBLE!
     */
    @Override
    public void function() {
        App.memory.setMemory(op1, Memory.convertBSToBoolArr(Memory.length32(Integer.toBinaryString(~(Integer.parseInt(App.memory.getMemory(op1, 32), 2))))));
    }
}
