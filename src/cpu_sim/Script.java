package cpu_sim;

import cpu_sim.command.Command;
import cpu_sim.computer.Memory;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Script {

    private boolean correctInput = false;
    public final String input;
    public String machineCode;
    private Set<Command> commands = new HashSet<>();
    private final String regex = "(\\w*:((\\s|\\t)*)?)?(\\w+)((\\s|\\t)*)?(\\w*|\\d*) *[,]?(([+]? *(\\w*|\\d*))+((\\s|\\t)*)?);$";

    public Script() {
        input = "";
    }

    public Script(String input) {
        this.input = input;
    }

    public boolean compile() {
        for (int i = 0; i < input.replaceAll("\\n(\\t)?\\n", "\n").split("\n").length; i++) {
            Matcher matcher = Pattern.compile(regex).matcher(input.trim().replaceAll("\\n(\\t)?\\n", "\n").split("\n")[i].trim());
            if (!matcher.find()) return false;
            while (matcher.find()) {
                String command = matcher.group(4);
                System.out.print(command + "|");
                String op1 = matcher.group(7);
                String op2 = matcher.group(8);
                if (op1.trim().equals("")) {
                    if (!op2.trim().equals("")) {
                        return false;
                    }
                }

                switch (command) {
                    case "mov":
                    case "lea":
                    case "add":
                    case "sub":
                    case "imul":
                    case "idiv":
                    case "and":
                    case "or":
                    case "xor":
                    case "store":
                    case "shl":
                    case "shr":
                    case "cmp":
                        if (op1.trim().equals("") || op2.trim().equals("")) return false;
                        break;

                    case "push":
                    case "pop":
                    case "inc":
                    case "dec":
                    case "not":
                    case "neg":
                    case "jmp":
                    case "je":
                    case "jne":
                    case "jz":
                    case "jg":
                    case "jge":
                    case "jl":
                    case "jle":
                    case "call":
                        if (op1.trim().equals("")) return false;
                        break;

                    case "ret":
                    case "hlt":
                        if (!op1.trim().equals("") || !op2.trim().equals("")) return false;
                }
            }
        }
        createMachineCode();
        return true;
    }

    public void createMachineCode() {
        machineCode = "";
        for (int i = 0; i < input.replaceAll("\\n(\\t)?\\n", "\n").split("\n").length; i++) {
            Matcher matcher = Pattern.compile(regex).matcher(input.trim().replaceAll("\\n(\\t)?\\n", "\n").split("\n")[i].trim());
            while (matcher.find()) {
                String command = matcher.group(4);

                switch (command) {
                    case "mov":
                        break;

                    case "lea":
                        break;

                    case "add":
                        machineCode += "0000000000000001";
                        break;

                    case "sub":
                        break;

                    case "imul":
                        break;

                    case "idiv":
                        break;

                    case "and":
                        break;

                    case "or":
                        break;

                    case "xor":
                        break;

                    case "store":
                        break;

                    case "shl":
                        break;

                    case "shr":
                        break;

                    case "cmp":
                        break;

                    case "push":
                        break;

                    case "pop":
                        break;

                    case "inc":
                        break;

                    case "dec":
                        break;

                    case "not":
                        break;

                    case "neg":
                        break;

                    case "jmp":
                        break;

                    case "je":
                        break;

                    case "jne":
                        break;

                    case "jz":
                        break;

                    case "jg":
                        break;

                    case "jge":
                        break;

                    case "jl":
                        break;

                    case "jle":
                        break;

                    case "call":
                        break;

                    case "ret":
                        break;

                    case "hlt":
                        machineCode += "1111111111111111";
                        break;
                }
                String op1 = matcher.group(7).trim();
                String op2 = matcher.group(8).trim();

                if (isNumeric(op1)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.insert(0, "00000000000000000000000000000000");
                    machineCode += stringBuilder.substring(0, 32 - Memory.stbi(op1).length()) + Memory.stbi(op1);
                } else machineCode += "00000000000000000000000000000000";

                if (isNumeric(op2)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.insert(0, "00000000000000000000000000000000");
                    machineCode += stringBuilder.substring(0, 32 - Memory.stbi(op2).length()) + Memory.stbi(op2);
                } else machineCode += "00000000000000000000000000000000";
            }
        }
    }

    public boolean isNumeric(String s) {
        Pattern pattern = Pattern.compile("\\d+");
        if (s == null) {
            return false;
        }
        return pattern.matcher(s).matches();
    }

    @Override
    public String toString() {
        return input;
    }
}