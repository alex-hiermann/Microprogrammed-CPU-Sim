package cpu_sim;

import cpu_sim.computer.Memory;
import javafx.scene.control.Alert;

public class Program {

    Script script = new Script();
    Memory memory;

    public Program(Memory memory) {
        this.memory = memory;
    }

    public void run() {
        if (!script.compile()) {
            Program.stop("ERROR: There was an error while compiling your script.\nPlease check your syntax!", false);
            return;
        }
        if (!writeMemory()) {
            Program.stop("ERROR: There was a fatal error while writing the script into the memory!", false);
            return;
        }
    }

    public static void stop(String error, boolean successful) {
        if (successful) return;
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (error.isEmpty()) error = "Unknown error!";
        alert.setTitle("ERROR in Script!");
        alert.setContentText(error);
        alert.showAndWait();
    }

    public static void executeCode() {

    }

    public boolean writeMemory() {
        memory.setMemory(0, Memory.convertBSToBoolAr(script.machineCode));
        System.out.println(memory.getMemory(0, 320));
        return true;
    }

    public void setScript(Script script) {
        this.script = script;
    }
}