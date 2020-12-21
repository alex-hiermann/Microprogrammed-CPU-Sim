package cpu_sim.bus;

/**
 * @author Alex Hiermann
 */
public class DataBus extends SystemBus {

    public DataBus(String type) {
        super(type);
    }

    @Override
    public byte getBus16bit() {
        return super.getBus16bit();
    }

    @Override
    public void setBus16bit(byte bus16bit) {
        super.setBus16bit(bus16bit);
    }
}