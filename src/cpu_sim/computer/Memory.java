package cpu_sim.computer;

import java.util.Stack;

/**
 * @author Alex Hiermann
 */
public class Memory {

    /**
     * size of the memory in bit
     */
    private final int size = 536870912;

    private Stack<Boolean> stack = new Stack<>();

    /**
     * memory itself as a boolean array
     */
    private boolean[] memory;

    /**
     * default constructor for the memory
     * size should not be changeable when initializing
     */
    public Memory() {
        stack.setSize(8388608); //1 boolean == 1 bit -> 1 MiB for Stack
        memory = new boolean[size-stack.size()]; //(2^29 -> 64 MiB) - 1 MiB
    }

    /**
     * set the bit at pos in memory to 1 (true)
     *
     * @param pos position for the bit you want to set
     */
    public void setBit(int pos) {
        memory[pos] = true;
    }

    /**
     * set the bit at pos in memory to 0 (false)
     *
     * @param pos position for the bit you want to unset
     */
    public void clearBit(int pos) {
        memory[pos] = false;
    }

    /**
     * inverts the bit at pos (1 -> 0, 0 -> 1)
     *
     * @param pos position for the bit you want to unset
     */
    public void invertBit(int pos) {
        memory[pos] = !memory[pos];
    }

    /**
     * useful to read some content out of your memory
     *
     * @param start start position for the range you want to read
     * @param range the range of the memory you want to read
     * @return a range of your memory
     */
    public String getMemory(int start, int range) {
        StringBuilder values = new StringBuilder();

        for (int i = start; i < start + range; i++) {
            values.append(convertBoolToInt(memory[i]));
        }
        return values.toString();
    }

    /**
     * useful to set some content out of your memory
     *
     * @param start start position for the range you want to read
     * @param input the range of the memory you want to read
     * @return a range of your memory
     */
    public boolean setMemory(int start, boolean[] input) {
        if (start + input.length > memory.length || input.length == 0) return false;

        int inputIndex = 0;
        for (int i = start; i < start + input.length; i++) {
            memory[i] = input[inputIndex];
            inputIndex++;
        }
        return true;
    }

    /**
     * useful to convert a binary string into a boolean array
     *
     * @param binaryString a string with only binary digits
     * @return the binary string as a boolean array
     */
    public static boolean[] convertBSToBoolAr(String binaryString) {
        boolean[] boolArr = new boolean[binaryString.length()];
        for (int i = 0; i < binaryString.length(); i++) boolArr[i] = binaryString.charAt(i) == '1';
        return boolArr;
    }

    /**
     * useful to convert a boolean (true/false) to int (1/0)
     *
     * @param bool a boolean value
     * @return a converted int value
     */
    public static int convertBoolToInt(boolean bool) {
        return bool ? 1 : 0;
    }

    /**
     * converts a string to a binary string
     *
     * @param s a binary string
     * @return a binary string
     */
    public static String stbi(String s) {
        return Integer.toBinaryString(Integer.parseInt(s));
    }

    /**
     * @return memory
     */
    public boolean[] getMemory() {
        return memory;
    }

    /**
     * @return size
     */
    public int getSize() {
        return size;
    }
}