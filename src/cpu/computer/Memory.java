package cpu.computer;

import java.util.Stack;

/**
 * @author Alex Hiermann
 */
public class Memory {

    /**
     * is the size of the memory in bit
     */
    private final int size = 536870912;


    /**
     * is the size for the used stack
     */
    public final int stackSize = 8388608;

    /**
     * stack as a java.util.stack using Boolean
     * used to store information temporarily
     */
    public final Stack<boolean[]> stack = new Stack<>();

    /**
     * memory as a boolean[]
     * used to store multiple true's and false's
     * useful to store and read information
     */
    private final boolean[] memory;

    /**
     * stands for the length of a binaryString
     */
    public static final int STRINGLENGTH = 32;

    /**
     * default constructor for the memory
     * size should not be changeable when initializing
     */
    public Memory() {
        stack.setSize(stackSize); //1 boolean == 1 bit -> 1 MiB for Stack
        memory = new boolean[size - stack.size()]; //(2^29 -> 64 MiB) - 1 MiB
    }

    /**
     * set the bit on position pos in memory to 1 (true)
     *
     * @param pos position for the bit you want to set
     */
    public void setBit(int pos) {
        memory[pos] = true;
    }

    /**
     * set the bit on position pos in memory to 0 (false)
     *
     * @param pos position for the bit you want to unset
     */
    public void clearBit(int pos) {
        memory[pos] = false;
    }

    /**
     * inverts(/flips) the bit on position pos (1 -> 0, 0 -> 1)
     *
     * @param pos position for the bit you want to unset
     */
    public void invertBit(int pos) {
        memory[pos] = !memory[pos];
    }

    /**
     * needed to read information out of your memory
     * returns a binary string
     *
     * @param start start position for the range you want to read
     * @param range the range of the memory you want to read
     * @return a range of your memory in binary
     */
    public String getMemory(int start, int range) {
        StringBuilder values = new StringBuilder();

        for (int i = start; i < start + range; i++) {
            values.append(convertBoolToInt(memory[i]));
        }
        return values.toString();
    }

    /**
     * needed to set information in your memory
     * returns a binary string
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
    public static boolean[] convertBSToBoolArr(String binaryString) {
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
        if (bool) return 1;
        else return 0;
    }

    /**
     * converts a string to a binary string
     * returns a string which is being converted to a binary string
     *
     * @param s a binary string
     * @return a binary string
     */
    public static String stbi(String s) {
        return Integer.toBinaryString(Integer.parseInt(s));
    }

    /**
     * turn a binary string with any length into a binary string filled up to a length 32 with zeros
     *
     * @param s a binary string
     * @return binary string with length 32
     */
    public static String length32(String s) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.insert(0, "00000000000000000000000000000000");
        return stringBuilder.substring(0, STRINGLENGTH - s.length()) + s;
    }

    /**
     * get the memory itself as a boolean[]
     *
     * @return the memory itself as a boolean[]
     */
    public boolean[] getMemory() {
        return memory;
    }

    /**
     * get the size of the memory
     *
     * @return size of the memory
     */
    public int getSize() {
        return size;
    }
}