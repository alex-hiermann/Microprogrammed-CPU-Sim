package cpu_sim.register;

/**
 * @author Alex Hiermann
 */
public abstract class Register {

    /**
     * memory for the 8bit registers
     */
    private byte bus8;

    /**
     * memory for the 16bit registers
     */
    private short bus16;

    /**
     * name of the register
     */
    private String name;

    /**
     * @param bus16 memory of the 16bit register
     * @param name of the register
     */
    public Register(short bus16, String name) {
        this.bus16 = bus16;
        this.name = name;
    }

    /**
     * @param bus8 memory of the 8bit register
     * @param name of the register
     */
    public Register(byte bus8, String name) {
        this.bus8 = bus8;
        this.name = name;
    }

    /**
     * @return bus8
     */
    public int getBus8() {
        return bus8;
    }

    /**
     * @param bus8 set the value of the bus8 memory
     */
    public void setBus8(byte bus8) {
        this.bus8 = bus8;
    }

    /**
     * @return bus16
     */
    public long getBus16() {
        return bus16;
    }

    /**
     * @param bus16 set the value of the bus16 memory
     */
    public void setBus16(short bus16) {
        this.bus16 = bus16;
    }

    /**
     * @return the name of the register
     */
    public String getName() {
        return name;
    }

    /**
     * @param name set the name of the register
     */
    public void setName(String name) {
        this.name = name;
    }
}
