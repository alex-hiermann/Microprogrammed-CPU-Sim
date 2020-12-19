package cpu_sim;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Script {

    private boolean correctInput = false;
    private final String input;
    //private Set<Command> commands = new HashSet<>();

    public Script(String input) {
        this.input = input;
    }

    public Script() {
        input = "";
    }

    //unfinished!
    public boolean compile() {
        Matcher matcher = Pattern.compile("(\\w*:((\\s|\\t)*)?)?(\\w+)((\\s|\\t)*)?(\\w*|\\d*)[,+]?(\\w*|\\d*)((\\s|\\t)*)?;").matcher(input);
        if (!matcher.find()) return false;

        return true;
    }

    public boolean execute() {
        return true;
    }

    public boolean getCorrectInput() {
        if (!correctInput) correctInput = this.compile();
        return this.compile();
    }

    @Override
    public String toString() {
        return input;
    }
}