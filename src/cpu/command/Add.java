package cpu.command;

import cpu.computer.Memory;
import cpu.ui.App;

/**
 * @author Alex Hiermann
 */
public class Add extends Command {

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
    public Add(int op1, int op2) {
        this.op1 = op1;
        this.op2 = op2;
    }

    /**
     * adds (op1) to (number in memory at index op2) and saves the result at op2
     */
    @Override
    public void function() {
        App.memory.setMemory(op2, Memory.convertBSToBoolArr(Memory.length32(Integer.toBinaryString(
                op1 + Integer.parseInt(App.memory.getMemory(op2, 1 << 2 + 2 + 1), 2)))));
    }
}
