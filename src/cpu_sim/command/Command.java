package cpu_sim.command;

public abstract class Command {

    String name;
    String microCode;

    public Command(String name, String microCode) {
        this.name = name;
        this.microCode = microCode;
    }

    public void function() {
    }
}