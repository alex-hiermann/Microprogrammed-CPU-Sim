package cpu_sim.command;


import cpu_sim.computer.Memory;
import cpu_sim.ui.App;

/**
 * @author Alex Hiermann
 */
public class Idiv extends Command {

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
    public Idiv(int op1, int op2) {
        this.op1 = op1;
        this.op2 = op2;
    }

    /**
     * divides op1 with the input at location op2 and saves the result at op2
     */
    @Override
    public void function() {
        App.memory.setMemory(op2, Memory.convertBSToBoolArr(Memory.length32(Integer.toBinaryString(op1 / Integer.parseInt(App.memory.getMemory(op2, 32), 2)))));
    }
}