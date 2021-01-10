package cpu.command;

import cpu.computer.Memory;
import cpu.ui.App;

/**
 * @author Alex Hiermann
 */
public class Neg extends Command {

    /**
     * first needed operator
     */
    private final int op1;

    /**
     * default constructor
     *
     * @param op1 first needed operator
     */
    public Neg(int op1) {
        this.op1 = op1;
    }

    /**
     * negates the number at the location op1
     * WARNING: NOT RECOMMENDED TO USE! INTEGER OVERFLOWS POSSIBLE!
     */
    @Override
    public void function() {
        App.memory.setMemory(op1, Memory.convertBSToBoolArr(Memory.length32(Integer.toBinaryString(
                -(Integer.parseInt(App.memory.getMemory(op1, 1 << 2 + 2 + 1), 2))))));
    }
}
