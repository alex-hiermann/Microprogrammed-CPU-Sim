package cpu_sim.computer;

import cpu_sim.register.Register;

public class Processor {

    private String name;
    private String frequency;
    private int busNumber;
    private short bus16;
    private byte bus8;

    Register[][] registers;

    public Processor(String name, String frequency, int busNumber, short bus16, byte bus8) {
        this.name = name;
        this.frequency = frequency;
        this.busNumber = busNumber;
        this.bus16 = bus16;
        this.bus8 = bus8;

        registers = new Register[][]{new Register[bus16], new Register[bus8]};
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

    public int getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(int busNumber) {
        this.busNumber = busNumber;
    }

    public short getBus16() {
        return bus16;
    }

    public void setBus16(short bus16) {
        this.bus16 = bus16;
    }

    public byte getBus8() {
        return bus8;
    }

    public void setBus8(byte bus8) {
        this.bus8 = bus8;
    }
}