package cpu_sim.bus;

/**
 * @author Alex Hiermann
 */
public class ControlBus extends SystemBus{

    public ControlBus(String type) {
        super(type);
    }

    @Override
    public short getBus16bit() {
        return super.getBus16bit();
    }

    @Override
    public void setBus16bit(short bus16bit) {
        super.setBus16bit(bus16bit);
    }
}
