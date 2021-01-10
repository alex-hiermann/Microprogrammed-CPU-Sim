package cpu;

import cpu.computer.Memory;
import cpu.ui.App;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alex Hiermann
 */
public class Script {

    /**
     * input of the script
     * commands expected
     */
    public final String input;

    /**
     * the commands converted into machine-micro-code
     */
    public String machineCode;

    /**
     * the regex which is being used to check if the syntax
     * is correctly used and no illegal arguments
     */
    private final String regex = "(\\w*:((\\s|\\t)*)?)?(\\w+)"
            + "((\\s|\\t)*)?(#?\\w*|#?\\d*) *[,]?(([+]? "
            + "*(#?\\w*|#?\\d*))+((\\s|\\t)*)?);$";

    /**
     * default constructor
     * no input source manually set -> set the input to nothing
     */
    public Script() {
        input = "";
    }

    /**
     * advanced constructor
     * set the input to the input of the manually given input source
     *
     * @param input is the input source for the script
     */
    public Script(String input) {
        this.input = input;
    }

    /**
     * @return true/false if any errors occurred while compiling the script
     */
    public boolean compile() {
        for (int i = 0; i < input.replaceAll("\\n(\\t)?\\n", "\n").split("\n").length; i++) {
            Matcher matcher = Pattern.compile(regex).matcher(input.trim().replaceAll("\\n(\\t)?\\n", "\n").split("\n")[i].trim());
            if (!matcher.find()) {
                System.out.println("VERLIERER!");
                return false;
            }
            while (matcher.find()) {
                String command = matcher.group(1 << 2);
                String op1 = matcher.group((1 << 2) + 2 + 1);
                String op2 = matcher.group(1 << (1 + 2));
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
                        System.out.println("LOOSER");
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
                        System.out.println("ZWEIFACHER LOOSER");
                        if (op1.trim().equals("")) return false;
                        break;

                    case "ret":
                    case "hlt":
                        System.out.println("DREIFACHER LOOSER");
                        if (!op1.trim().equals("") || !op2.trim().equals("")) return false;
                        break;

                    default:
                        System.out.println("ALLGEMEIN LOOSER");
                        break;
                }
            }
        }
        createMachineCode();
        return true;
    }

    /**
     * convert the "normal" script into a machine-micro-code using script
     * which is being used to let the processor execute the program
     */
    @SuppressWarnings("StringConcatenationInLoop")
    public void createMachineCode() {
        machineCode = "";
        for (int i = 0; i < input.replaceAll("\\n(\\t)?\\n", "\n").split("\n").length; i++) {
            Matcher matcher = Pattern.compile(regex).matcher(input.trim().replaceAll("\\n(\\t)?\\n", "\n").split("\n")[i].trim());
            while (matcher.find()) {
                String command = matcher.group(1 << 2);

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
                    default -> machineCode += "";
                }
                String op1 = matcher.group((1 << 2) + 2 + 1).trim();
                String op2 = matcher.group(1 << (1 + 2)).trim();

                //convert op1 into machine code
                operatorToMachineCode(op1);

                //convert op2 into machine code
                operatorToMachineCode(op2);
            }
        }
    }

    /**
     * used to convert a single operator into machine-micro-code language
     *
     * @param op optional operator
     */
    private void operatorToMachineCode(String op) {
        if (isNumeric(op)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.insert(0, "00000000000000000000000000000000");
            String s;
            if (op.contains("#")) {
                op = op.replace("#", "");
                s = App.memory.getMemory(Integer.parseInt(op), 1 << 2 + 2 + 1);
            } else s = Memory.stbi(op);
            machineCode += stringBuilder.substring(0, (1 << 2 + 2 + 1) - Memory.stbi(op).length())
                    + s;
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

    /**
     * @return the input correctly formatted
     */
    @Override
    public String toString() {
        if (machineCode.equals("")) return input;
        else return input + "\n" + machineCode;
    }
}