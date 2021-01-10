package cpu.command;

import cpu.computer.Memory;
import cpu.ui.App;

/**
 * @author Alex Hiermann
 */
public class Sub extends Command {

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
    public Sub(int op1, int op2) {
        this.op1 = op1;
        this.op2 = op2;
    }

    /**
     * subtracts from (number in memory at index of op1) op2 and saves the result at op1
     */
    @Override
    public void function() {
        String bin = Integer.toBinaryString(Integer.parseInt(App.memory.getMemory(
                op1, 1 << 2 + 2 + 1), 2) - op2);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.insert(0, "00000000000000000000000000000000");
        String s = stringBuilder.substring(0, (1 << 2 + 2 + 1) - bin.length()) + bin;

        App.memory.setMemory(op1, Memory.convertBSToBoolArr(s));
    }
}
