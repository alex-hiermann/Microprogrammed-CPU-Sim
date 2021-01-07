package cpu_sim.computer;

import cpu_sim.command.*;
import cpu_sim.register.Register;
import cpu_sim.register.Register16Bit;
import cpu_sim.register.Register8Bit;
import cpu_sim.ui.App;

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

    public boolean executeCode(int commandCounter) {
        for (instructionPointer = 0; instructionPointer < 80 * commandCounter; instructionPointer += 80) {
            System.out.println("Durchgang " + (instructionPointer / 80 + 1));
            String command = App.memory.getMemory(instructionPointer, 16);
            String address = App.memory.getMemory(instructionPointer + 16, 32); //op1
            String data = App.memory.getMemory(instructionPointer + 48, 32); //op2

            App.addressBus.setBus32bit(Integer.parseInt(address, 2));
            App.dataBus.setBus32bit(Integer.parseInt(data, 2));

            switch (command) {
                case "0000000000000001" -> new Add(App.addressBus.getBus32bit(), App.dataBus.getBus32bit()).function();
                case "0000000000000010" -> new And(App.addressBus.getBus32bit(), App.dataBus.getBus32bit()).function();
                case "0000000000000101" -> new Dec(App.addressBus.getBus32bit()).function();
                case "0000000000000110" -> new Idiv(App.addressBus.getBus32bit(), App.dataBus.getBus32bit()).function();
                case "0000000000000111" -> new Imul(App.addressBus.getBus32bit(), App.dataBus.getBus32bit()).function();
                case "0000000000001000" -> new Inc(App.addressBus.getBus32bit()).function();
                case "0000000000010101" -> new Or(App.addressBus.getBus32bit(), App.dataBus.getBus32bit()).function();
                case "0000000000011100" -> new Sub(App.addressBus.getBus32bit(), App.dataBus.getBus32bit()).function();
                case "0000000000011101" -> new Xor(App.addressBus.getBus32bit(), App.dataBus.getBus32bit()).function();
                case "1111111111111111" -> new Hlt().function();
            }
        }
        return true;
    }

    public int getInstructionPointer() {
        return instructionPointer;
    }

    public void setInstructionPointer(int instructionPointer) {
        this.instructionPointer = instructionPointer;
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