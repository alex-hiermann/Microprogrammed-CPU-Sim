package cpu_sim.register;

/**
 * @author Alex Hiermann
 */
public class Register8Bit extends Register {

    /**
     * @param bus8 memory of the 8bit register
     * @param name of the register
     */
    public Register8Bit(byte bus8, String name) {
        super(bus8, name);
    }
}
