package cpu_sim.bus;

/**
 * @author Alex Hiermann
 */
public class AddressBus extends SystemBus{

    public AddressBus(String type) {
        super(type);
    }

    @Override
    public short getBus32bit() {
        return super.getBus32bit();
    }

    @Override
    public void setBus32bit(short bus16bit) {
        super.setBus32bit(bus16bit);
    }
}