package cpu.command;

import cpu.computer.Memory;
import cpu.ui.App;

import java.util.Arrays;

/**
 * @author Alex Hiermann
 */
public class Push extends Command {

    /**
     * first needed operator
     */
    private final int op1;

    /**
     * default constructor
     *
     * @param op1 first needed operator
     */
    public Push(int op1) {
        this.op1 = op1;
    }

    @Override
    public void function() {
        App.memory.stack.push(Memory.convertBSToBoolArr(App.memory.getMemory(op1, 1 << 2 + 2 + 1)));
        System.out.println(Arrays.toString(App.memory.stack.get(0)));
    }
}
