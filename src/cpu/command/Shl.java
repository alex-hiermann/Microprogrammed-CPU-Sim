package cpu.command;

import cpu.computer.Memory;
import cpu.ui.App;

/**
 * @author Alex Hiermann
 */
public class Shl extends Command {

    /**
     * first needed operator
     */
    private final int op1;
    /**
     * second needed operator
     */
    private final int op2;

    /**
     * default constructor
     *
     * @param op1 first needed operator
     * @param op2 second needed operator
     */
    public Shl(int op1, int op2) {
        this.op1 = op1;
        this.op2 = op2;
    }

    /**
     * shifts the input from location (op1) by op2 into the left direction and saves it at op1
     */
    @Override
    public void function() {
        App.memory.setMemory(op1, Memory.convertBSToBoolArr(Memory.length32(Integer.toBinaryString(
                Integer.parseInt(App.memory.getMemory(op1, 1 << 2 + 2 + 1), 2) << op2))));
    }
}
