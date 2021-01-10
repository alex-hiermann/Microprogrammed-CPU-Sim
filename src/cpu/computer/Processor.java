package cpu.computer;

import cpu.command.*;
import cpu.register.Register;
import cpu.register.Register16Bit;
import cpu.register.Register8Bit;
import cpu.ui.App;

/**
 * @author Alex Hiermann
 */
public class Processor {

    /**
     * name of the processor
     * doesn't change anything of the speed of the calculations
     */
    private String name;

    /**
     * frequency of the processor
     * doesn't change anything of the speed of the calculations
     */
    private String frequency;

    /**
     * a array for the different registers
     * these are being used to store little information
     * within the processor and to make it possible, to
     * calculate more quickly, because of less bus transfer
     */
    Register[][] registers;

    /**
     * shows on which command the processor is right now
     */
    private int instructionPointer;

    /**
     * @param name       name of the processor
     * @param frequency  frequency of the processor
     * @param register16 how many 16bit registers you want
     * @param register8  how many 8bit registers you want
     */
    public Processor(String name, String frequency, int register16, int register8) {
        this.name = name;
        this.frequency = frequency;

        registers = new Register[][]{new Register16Bit[register16], new Register8Bit[register8]};
    }

    /**
     * @param commandCounter shows how many commands there are to execute
     * @return true or false if this methode was successfully executed
     */
    public boolean executeCode(int commandCounter) {
        for (instructionPointer = 0; instructionPointer < 80 * commandCounter;
             instructionPointer += 80) {
            System.out.println("Durchgang " + (instructionPointer / 80 + 1));
            String command = App.memory.getMemory(instructionPointer, 16);
            String address = App.memory.getMemory(instructionPointer + 16, 32); //op1
            String data = App.memory.getMemory(instructionPointer + 48, 32); //op2

            App.addressBus.setBus32bit(Integer.parseInt(address, 2));
            App.dataBus.setBus32bit(Integer.parseInt(data, 2));

            switch (command) {
                case "0000000000000001" -> new Add(App.addressBus.getBus32bit(),
                        App.dataBus.getBus32bit()).function();
                case "0000000000000010" -> new And(App.addressBus.getBus32bit(),
                        App.dataBus.getBus32bit()).function();
                case "0000000000000101" -> new Dec(App.addressBus.getBus32bit()).function();
                case "0000000000000110" -> new Idiv(App.addressBus.getBus32bit(),
                        App.dataBus.getBus32bit()).function();
                case "0000000000000111" -> new Imul(App.addressBus.getBus32bit(),
                        App.dataBus.getBus32bit()).function();
                case "0000000000001000" -> new Inc(App.addressBus.getBus32bit()).function();
                case "0000000000010010" -> new Mov(App.addressBus.getBus32bit(),
                        App.dataBus.getBus32bit()).function();
                case "0000000000010011" -> new Neg(App.addressBus.getBus32bit()).function();
                case "0000000000010100" -> new Not(App.addressBus.getBus32bit()).function();
                case "0000000000010101" -> new Or(App.addressBus.getBus32bit(),
                        App.dataBus.getBus32bit()).function();
                case "0000000000011001" -> new Shl(App.addressBus.getBus32bit(),
                        App.dataBus.getBus32bit()).function();
                case "0000000000011010" -> new Shr(App.addressBus.getBus32bit(),
                        App.dataBus.getBus32bit()).function();
                case "0000000000011100" -> new Sub(App.addressBus.getBus32bit(),
                        App.dataBus.getBus32bit()).function();
                case "0000000000011101" -> new Xor(App.addressBus.getBus32bit(),
                        App.dataBus.getBus32bit()).function();
                case "1111111111111111" -> new Hlt().function();
                default -> new Cmp().function();
            }
        }
        return true;
    }

    /**
     * @return the instructionPointer
     */
    public int getInstructionPointer() {
        return instructionPointer;
    }

    /**
     * @param instructionPointer set the value of the instructionPointer
     */
    public void setInstructionPointer(int instructionPointer) {
        this.instructionPointer = instructionPointer;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name set the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the frequency
     */
    public String getFrequency() {
        return frequency;
    }

    /**
     * @param frequency set the value of the frequency
     */
    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
}