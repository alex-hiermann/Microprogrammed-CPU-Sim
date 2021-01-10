package cpu;

import cpu.computer.Memory;
import cpu.ui.App;
import javafx.scene.control.Alert;

/**
 * @author Alex Hiermann
 */
public class Program {

    /**
     * the Script with all commands to execute
     */
    Script script = new Script();

    /**
     * how many commands are used in the script
     */
    private static int commandCounter;

    /**
     * stands for the starting value of the first address
     */
    private static final int STARTING_ADDRESS = 4200;

    /**
     * run the program
     * first compile the script, check if everything is correctly used
     * secondly write the commands into the memory
     * thirdly start the processor and let it execute the converted script
     * if there is a error, every method returns true/false and there are exceptions thrown
     */
    public void run() {
        if (!script.compile()) {
            Program.stop("ERROR: There was an error while compiling your script."
                    + "\nPlease check your syntax!", false);
            return;
        }
        commandCounter = script.input.split(";").length - 1;
        if (!writeMemory()) {
            Program.stop("ERROR: There was a fatal error while "
                    + "writing the script into the memory!", false);
            return;
        }
        if (!startProcessor()) {
            Program.stop("ERROR: There was a runtime error while executing the script!", false);
        }
        Program.stop("", true);
    }

    /**
     * stops the program, if any errors occurred while trying to execute
     * here the alert would throw an useful and manageable exception!
     *
     * @param error      string where a useful exception should be written in
     * @param successful true/false if there should be a alert with exception be thrown
     */
    public static void stop(String error, boolean successful) {
        if (successful) {
            StringBuilder string = new StringBuilder();
            for (int i = STARTING_ADDRESS; i < STARTING_ADDRESS + commandCounter * (
                    1 << 2 + 2 + 1); i += (1 << 2 + 2 + 1)) {
                string.append("#").append(i).append(" | ").append(App.memory.getMemory(i,
                        1 << 2 + 2 + 1)).append(" | int value: ").append(
                        Integer.parseInt(App.memory.getMemory(i, 1 << 2 + 2 + 1), 2)).append("\n");
            }
            App.consoleOutput.setText(string.toString());
            return;
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (error.isEmpty()) error = "Unknown error!";
        alert.setTitle("ERROR!");
        alert.setContentText(error);
        alert.showAndWait();
    }

    /**
     * start the processor -> let the processor execute the into
     * machine-micro-code converted script
     *
     * @return true/false if a error occurred while executing the code
     */
    public static boolean startProcessor() {
        return App.processor.executeCode(commandCounter);
    }

    /**
     * write the machine-micro-code converted script into the memory
     *
     * @return true/false if a error occurred while writing into the memory
     */
    public boolean writeMemory() {
        return App.memory.setMemory(0, Memory.convertBSToBoolArr(script.machineCode));
    }

    /**
     * @param script set the script
     */
    public void setScript(Script script) {
        this.script = script;
    }
}