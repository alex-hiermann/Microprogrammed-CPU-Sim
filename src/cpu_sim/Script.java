package cpu_sim;

import cpu_sim.command.Command;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Script {

    private boolean correctInput = false;
    private final String input;
    private Set<Command> commands = new HashSet<>();

    public Script() {
        input = "";
    }

    public Script(String input) {
        this.input = input;
    }

    //unfinished!
    public boolean compile() {
        for (int i = 0; i < input.replaceAll("\\n(\\t)?\\n","\n").split("\n").length; i++) {
            Matcher matcher = Pattern.compile("(\\w*:((\\s|\\t)*)?)?(\\w+)((\\s|\\t)*)?(\\w*|\\d*) *[,]?(([+]? *(\\w*|\\d*))+((\\s|\\t)*)?);$").matcher(input.trim().replaceAll("\\n(\\t)?\\n","\n").split("\n")[i].trim());

            if (!Pattern.compile("(\\w*:((\\s|\\t)*)?)?(\\w+)((\\s|\\t)*)?(\\w*|\\d*) *[,]?(([+]? *(\\w*|\\d*))+((\\s|\\t)*)?);$").matcher(input.trim().replaceAll("\\n(\\t)?\\n","\n").split("\n")[i].trim()).find()) return false;
            while (matcher.find()) {
                String command = matcher.group(4);
                String op1 = matcher.group(7);
                String op2 = matcher.group(8);
                System.out.println(command.trim() + "|" + op1.trim() + "|" + op2.trim());
            }
        }
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