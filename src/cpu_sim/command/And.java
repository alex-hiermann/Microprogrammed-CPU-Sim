package cpu_sim.command;

import cpu_sim.computer.Memory;
import cpu_sim.ui.App;

public class And extends Command {

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
    public And(int op1, int op2) {
        this.op1 = op1;
        this.op2 = op2;
    }

    /**
     * performs the logical bitwise "and" with the input on location op1 and the input from location op2 and saves the result at the location of op1
     */
    @Override
    public void function() {
        String bin = Integer.toBinaryString(Integer.parseInt(App.memory.getMemory(op1, 32), 2) & Integer.parseInt(App.memory.getMemory(op2, 32), 2));

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.insert(0, "00000000000000000000000000000000");
        String s = stringBuilder.substring(0, 32 - bin.length()) + bin;

        App.memory.setMemory(op1, Memory.convertBSToBoolArr(s));
    }
}
