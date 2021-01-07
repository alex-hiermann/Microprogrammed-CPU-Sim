package cpu_sim.command;

import cpu_sim.computer.Memory;
import cpu_sim.ui.App;

public class Sub extends Command {

    private final int op1;
    private final int op2;

    public Sub(int op1, int op2) {
        this.op1 = op1;
        this.op2 = op2;
    }

    @Override
    public void function() {
        String bin = Integer.toBinaryString(Integer.parseInt(App.memory.getMemory(op1, 32), 2) - op2);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.insert(0, "00000000000000000000000000000000");
        String s = stringBuilder.substring(0, 32 - bin.length()) + bin;

        App.memory.setMemory(op1, Memory.convertBSToBoolArr(s));
    }
}
