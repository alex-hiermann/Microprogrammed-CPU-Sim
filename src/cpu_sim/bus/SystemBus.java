package cpu_sim.bus;

/**
 * @author Alex Hiermann
 */
public abstract class SystemBus {

    private String type;
    private short bus16bit;
    private byte bus8bit;

    public SystemBus(String type, short bus16bit) {
        this.type = type;
        this.bus16bit = bus16bit;
    }

    public SystemBus(String type, byte bus8bit) {
        this.type = type;
        this.bus8bit = bus8bit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public short getBus16bit() {
        return bus16bit;
    }

    public void setBus16bit(short bus16bit) {
        this.bus16bit = bus16bit;
    }

    public byte getBus8bit() {
        return bus8bit;
    }

    public void setBus8bit(byte bus8bit) {
        this.bus8bit = bus8bit;
    }
}
