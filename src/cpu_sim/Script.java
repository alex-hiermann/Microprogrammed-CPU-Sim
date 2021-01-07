package cpu_sim;

import cpu_sim.command.Command;
import cpu_sim.computer.Memory;
import cpu_sim.ui.App;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Script {

    public final String input;
    public String machineCode;
    private final String regex = "(\\w*:((\\s|\\t)*)?)?(\\w+)((\\s|\\t)*)?(#?\\w*|#?\\d*) *[,]?(([+]? *(#?\\w*|#?\\d*))+((\\s|\\t)*)?);$";

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

    @SuppressWarnings("StringConcatenationInLoop")
    public void createMachineCode() {
        machineCode = "";
        for (int i = 0; i < input.replaceAll("\\n(\\t)?\\n", "\n").split("\n").length; i++) {
            Matcher matcher = Pattern.compile(regex).matcher(input.trim().replaceAll("\\n(\\t)?\\n", "\n").split("\n")[i].trim());
            while (matcher.find()) {
                String command = matcher.group(4);

                switch (command) {
                    case "add" -> machineCode += "0000000000000001";
                    case "and" -> machineCode += "0000000000000010";
                    case "call" -> machineCode += "0000000000000011";
                    case "cmp" -> machineCode += "0000000000000100";
                    case "dec" -> machineCode += "0000000000000101";
                    case "hlt" -> machineCode += "1111111111111111";
                    case "idiv" -> machineCode += "0000000000000110";
                    case "imul" -> machineCode += "0000000000000111";
                    case "inc" -> machineCode += "0000000000001000";
                    case "jmp" -> machineCode += "0000000000001001";
                    case "je" -> machineCode += "0000000000001010";
                    case "jg" -> machineCode += "0000000000001011";
                    case "jge" -> machineCode += "0000000000001100";
                    case "jl" -> machineCode += "0000000000001101";
                    case "jle" -> machineCode += "0000000000001110";
                    case "jne" -> machineCode += "0000000000001111";
                    case "jz" -> machineCode += "0000000000010000";
                    case "lea" -> machineCode += "0000000000010001";
                    case "mov" -> machineCode += "0000000000010010";
                    case "neg" -> machineCode += "0000000000010011";
                    case "not" -> machineCode += "0000000000010100";
                    case "or" -> machineCode += "0000000000010101";
                    case "pop" -> machineCode += "0000000000010110";
                    case "push" -> machineCode += "0000000000010111";
                    case "ret" -> machineCode += "0000000000011000";
                    case "shl" -> machineCode += "0000000000011001";
                    case "shr" -> machineCode += "0000000000011010";
                    case "store" -> machineCode += "0000000000011011";
                    case "sub" -> machineCode += "0000000000011100";
                    case "xor" -> machineCode += "0000000000011101";
                }
                String op1 = matcher.group(7).trim();
                String op2 = matcher.group(8).trim();

                //convert op1 into machine code
                OperatorToMachineCode(op1);

                //convert op2 into machine code
                OperatorToMachineCode(op2);
            }
        }
    }

    private void OperatorToMachineCode(String op) {
        if (isNumeric(op)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.insert(0, "00000000000000000000000000000000");
            String s;
            if (op.contains("#")) {
                op = op.replace("#", "");
                s = App.memory.getMemory(Integer.parseInt(op), 32);
            }
            else s = Memory.stbi(op);
            machineCode += stringBuilder.substring(0, 32 - Memory.stbi(op).length()) + s;
        } else machineCode += "00000000000000000000000000000000";
    }

    /**
     * useful to check if a string only contains numbers
     *
     * @param s string
     * @return if the string only contains numbers (true/false)
     */
    public boolean isNumeric(String s) {
        Pattern pattern = Pattern.compile("#?\\d+");
        if (s == null || s.equalsIgnoreCase("")) {
            return false;
        }
        return pattern.matcher(s).matches();
    }

    @Override
    public String toString() {
        return input;
    }
}