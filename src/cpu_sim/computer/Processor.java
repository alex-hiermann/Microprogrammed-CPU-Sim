package cpu_sim.computer;

import cpu_sim.register.Register;
import cpu_sim.register.Register16Bit;
import cpu_sim.register.Register8Bit;

public class Processor {

    private String name;
    private String frequency;
    Register[][] registers;
    private int instructionPointer;

    public Processor(String name, String frequency, int register16, int register8) {
        this.name = name;
        this.frequency = frequency;

        registers = new Register[][]{new Register16Bit[register16], new Register8Bit[register8]};
    }

    public boolean executeCode() {

        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
}