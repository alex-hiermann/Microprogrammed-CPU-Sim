package cpu_sim;

import javafx.scene.control.Alert;

public class Program {

    Script script = new Script();

    public void run() {
        if (!script.compile()) Program.stop("ERROR: There was an error while compiling your script.\nPlease check your syntax!");
        else {
            if (!script.execute()) Program.stop("ERROR: There was a runtime error while executing the script!");
        }
    }

    public static void stop(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (error.isEmpty()) error = "Unknown error!";
        alert.setTitle("ERROR in Script!");
        alert.setContentText(error);
        alert.showAndWait();
    }

    public void setScript(Script script) {
        this.script = script;
    }
}