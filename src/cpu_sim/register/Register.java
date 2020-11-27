package cpu_sim.register;

public abstract class Register {

    private byte bus8;
    private short bus16;
    private String name;

    public Register(short bus16, String name) {
        this.bus16 = bus16;
        this.name = name;
    }

    public Register(byte bus8, String name) {
        this.bus8 = bus8;
        this.name = name;
    }

    public int getBus8() {
        return bus8;
    }

    public void setBus8(byte bus8) {
        this.bus8 = bus8;
    }

    public long getBus16() {
        return bus16;
    }

    public void setBus16(short bus16) {
        this.bus16 = bus16;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
