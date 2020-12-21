package cpu_sim.bus;

/**
 * @author Alex Hiermann
 */
public abstract class SystemBus {

    private String type;
    private int bus32bit;
    private short bus16bit;

    public SystemBus(String type) {
        this.type = type;
        this.bus32bit = 0;
        this.bus16bit = 0;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getBus32bit() {
        return bus32bit;
    }

    public void setBus32bit(int bus32bit) {
        this.bus32bit = bus32bit;
    }

    public short getBus16bit() {
        return bus16bit;
    }

    public void setBus16bit(short bus16bit) {
        this.bus16bit = bus16bit;
    }
}
