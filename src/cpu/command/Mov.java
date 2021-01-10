package cpu.command;

import cpu.computer.Memory;
import cpu.ui.App;

/**
 * @author Alex Hiermann
 */
public class Mov extends Command {

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
    public Mov(int op1, int op2) {
        this.op1 = op1;
        this.op2 = op2;
    }

    /**
     * move (number in memory at index of op1) to (number in memory at index of op2)
     */
    @Override
    public void function() {
        App.memory.setMemory(op2, Memory.convertBSToBoolArr(App.memory.getMemory(op1,
                1 << 2 + 2 + 1)));
    }
}