package cpu.bus;

/**
 * @author Alex Hiermann
 */
public class ControlBus extends SystemBus {

    /**
     * @param type control bus is being used to store a fixed amount of bits
     */
    public ControlBus(String type) {
        super(type);
    }

    /**
     * @return the input of the 16bit memory of the control bus
     */
    @Override
    public short getBus16bit() {
        return super.getBus16bit();
    }

    /**
     * @param bus16bit set the input of the 16bit memory of the control bus
     */
    @Override
    public void setBus16bit(short bus16bit) {
        super.setBus16bit(bus16bit);
    }
}
