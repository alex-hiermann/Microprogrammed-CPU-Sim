import cpu_sim.Memory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Alex Hiermann
 */
public class Main extends Application {

    public static void main(String[] args) {
        //first test with memory write and read
        Memory C = new Memory();
        C.setMemory(10, new boolean[]{true, false, false, true, false});
        System.out.println(C.getMemory(0, 15));

        launch(args);
    }

    /**
     * creates the gui / javafx window
     *
     * @param primaryStage stage for you javafx application
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("CPU-Scripting");
        primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/icon.png")));

        MenuBar menuBar = new MenuBar();

        Menu menu1 = new Menu("File");
        MenuItem open = new Menu("Open");
        MenuItem save = new Menu("Save");
        MenuItem close = new Menu("Close");
        menu1.getItems().addAll(open, save, close);

        Menu menu2 = new Menu("Edit");
//        MenuItem a = new Menu("a");
//        MenuItem a = new Menu("a");
//        MenuItem a = new Menu("a");
//        menu2.getItems().addAll();

        Menu menu3 = new Menu("View");
//        MenuItem b = new Menu("b");
//        MenuItem b = new Menu("b");
//        MenuItem b = new Menu("b");
//        menu3.getItems().addAll();

        menuBar.getMenus().addAll(menu1, menu2, menu3);
        VBox vBox = new VBox(menuBar);

        primaryStage.setScene(new Scene(vBox, 960, 600));
        primaryStage.show();
    }
}