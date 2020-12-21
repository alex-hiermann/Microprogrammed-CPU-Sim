package cpu_sim.command;

import cpu_sim.Program;

public class Hlt extends Command{

    public Hlt() {
    }

    @Override
    public void function() {
        Program.stop("", true);
    }
}
