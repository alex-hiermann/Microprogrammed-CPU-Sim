package cpu_sim;


import cpu_sim.computer.Memory;
import cpu_sim.ui.App;
import javafx.scene.control.Alert;

public class Program {

    Script script = new Script();

    public void run() {
        if (!script.compile()) {
            Program.stop("ERROR: There was an error while compiling your script.\nPlease check your syntax!", false);
            return;
        }
        if (!writeMemory()) {
            Program.stop("ERROR: There was a fatal error while writing the script into the memory!", false);
            return;
        }
        if (!startProcessor()) Program.stop("ERROR: There was a runtime error while executing the script!", false);
    }

    public static void stop(String error, boolean successful) {
        if (successful) {
            System.out.println("DU HAST ES GESCHAFFT!!!!");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (error.isEmpty()) error = "Unknown error!";
        alert.setTitle("ERROR!");
        alert.setContentText(error);
        alert.showAndWait();
    }

    public static boolean startProcessor() {
        return App.processor.executeCode();
    }

    public boolean writeMemory() {
        return App.memory.setMemory(0, Memory.convertBSToBoolAr(script.machineCode));
    }

    public void setScript(Script script) {
        this.script = script;
    }
}