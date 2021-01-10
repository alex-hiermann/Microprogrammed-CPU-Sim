package cpu.command;

import cpu.ui.App;

/**
 * @author Alex Hiermann
 */
public class Pop extends Command {

    /**
     * first needed operator
     */
    private final int op1;

    /**
     * default constructor
     *
     * @param op1 first needed operator
     */
    public Pop(int op1) {
        this.op1 = op1;
    }

    /**
     * pops the last input from the stack and writes it into the memory
     */
    @Override
    public void function() {
        App.memory.setMemory(op1, App.memory.stack.pop());
    }
}
