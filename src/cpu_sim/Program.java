package cpu_sim;


import cpu_sim.computer.Memory;
import cpu_sim.ui.App;
import javafx.scene.control.Alert;

public class Program {

    Script script = new Script();
    private static int commandCounter;

    public void run() {
        if (!script.compile()) {
            Program.stop("ERROR: There was an error while compiling your script.\nPlease check your syntax!", false);
            return;
        }
        commandCounter = script.input.split(";").length;
        if (!writeMemory()) {
            Program.stop("ERROR: There was a fatal error while writing the script into the memory!", false);
            return;
        }
        if (!startProcessor()) {
            Program.stop("ERROR: There was a runtime error while executing the script!", false);
        }
    }

    public static void stop(String error, boolean successful) {
        if (successful) {
            System.out.println("#4200 | " + App.memory.getMemory(4200, 32) + " | int value: " + Integer.parseInt(App.memory.getMemory(4200, 32), 2));
            System.out.println("#4232 | " + App.memory.getMemory(4232, 32) + " | int value: " + Integer.parseInt(App.memory.getMemory(4232, 32), 2));
            System.out.println("#4264 | " + App.memory.getMemory(4264, 32) + " | int value: " + Integer.parseInt(App.memory.getMemory(4264, 32), 2));
            System.out.println("#4296 | " + App.memory.getMemory(4296, 32) + " | int value: " + Integer.parseInt(App.memory.getMemory(4296, 32), 2));
            return;
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (error.isEmpty()) error = "Unknown error!";
        alert.setTitle("ERROR!");
        alert.setContentText(error);
        alert.showAndWait();
    }

    public static boolean startProcessor() {
        return App.processor.executeCode(commandCounter);
    }

    public boolean writeMemory() {
        return App.memory.setMemory(0, Memory.convertBSToBoolArr(script.machineCode));
    }

    public void setScript(Script script) {
        this.script = script;
    }
}