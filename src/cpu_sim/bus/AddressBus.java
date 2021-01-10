package cpu_sim.bus;

/**
 * @author Alex Hiermann
 */
public class AddressBus extends SystemBus {

    /**
     * @param type address bus is being used to store a fixed amount of bits
     */
    public AddressBus(String type) {
        super(type);
    }

    /**
     * @return the input of the 32bit memory of the address bus
     */
    @Override
    public int getBus32bit() {
        return super.getBus32bit();
    }

    /**
     * @param bus32bit set the input of the 32bit memory of the address bus
     */
    @Override
    public void setBus32bit(int bus32bit) {
        super.setBus32bit(bus32bit);
    }
}