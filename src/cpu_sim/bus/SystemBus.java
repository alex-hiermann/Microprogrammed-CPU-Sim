package cpu_sim.bus;

/**
 * @author Alex Hiermann
 */
public abstract class SystemBus {

    /**
     * type is a variable, used to show to actual usage of a bus
     * of course you should use a data bus as a data bus and so on
     * but if not, here you can say, that the usage of it is not normal
     */
    private String type;

    /**
     * this variable is a 32bit memory (int 32bit - 1)
     * used to store any bits while transferring data between
     * the memory and the processor for example
     */
    private int bus32bit;

    /**
     * this variable is a 16bit memory (short 16bit - 1)
     * used to store any bits while transferring data between
     * the memory and the processor for example
     */
    private short bus16bit;

    /**
     * @param type is the usage of this bus
     */
    public SystemBus(String type) {
        this.type = type;
        this.bus32bit = 0;
        this.bus16bit = 0;
    }

    /**
     * @return the type of a bus
     */
    public String getType() {
        return type;
    }

    /**
     * @param type set the type of a bus
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the input of the 32bit memory of the address bus
     */
    public int getBus32bit() {
        return bus32bit;
    }

    /**
     * @param bus32bit set the input of the 32bit memory of the address bus
     */
    public void setBus32bit(int bus32bit) {
        this.bus32bit = bus32bit;
    }

    /**
     * @return the input of the 16bit memory of the data bus
     */
    public short getBus16bit() {
        return bus16bit;
    }

    /**
     * @param bus16bit set the input of the 16bit memory of the data bus
     */
    public void setBus16bit(short bus16bit) {
        this.bus16bit = bus16bit;
    }
}
