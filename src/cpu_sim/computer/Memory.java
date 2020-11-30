package cpu_sim.computer;

/**
 * @author Alex Hiermann
 */
public class Memory {

    /**
     * size of the memory in bit
     */
    private final int size = 536870912; //64 Mebibyte -> 2^29

    /**
     * memory itself as a boolean array
     */
    private boolean[] memory;

    /**
     * default constructor for the memory
     * size should not be changeable when initializing
     */
    public Memory() {
        memory = new boolean[size];
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
     * useful to convert a boolean (true/false) to int (1/0)
     *
     * @param bool a boolean value
     * @return a converted int value
     */
    public static int convertBoolToInt(boolean bool) {
        return bool ? 1 : 0;
    }

    /**
     * string to binary int
     * converts a binary string to a binary int
     *
     * @param s a binary string
     * @return a binary int
     */
    public static int stbi(String s) {
        return Integer.parseInt(s, 2);
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