package cpu.command;

import cpu.Program;

/**
 * @author Alex Hiermann
 */
public class Hlt extends Command {

    @Override
    public void function() {
        Program.stop("", true);
    }
}
