package cpu.register;

/**
 * @author Alex Hiermann
 */
public class Register16Bit extends Register {

    /**
     * @param bus16 memory of the 16bit register
     * @param name  of the register
     */
    public Register16Bit(short bus16, String name) {
        super(bus16, name);
    }
}
