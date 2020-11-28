import cpu_sim.Memory;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

/**
 * @author Alex Hiermann
 */
public class Main extends Application {

    public static void main(String[] args) {
        //first test with memory write and read
        Memory C = new Memory();
        C.setMemory(10, new boolean[]{true, false, false, true, false});
        System.out.println(C.getMemory(10, 5));

        launch(args);
    }

    /**
     * creates the gui/javafx window
     *
     * @param primaryStage stage for you javafx application
     */
    @Override
    public void start(Stage primaryStage) {
        //basic and standard window config
        primaryStage.setTitle("CPU-Scripting");
        primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/icon.png")));
        primaryStage.setMaximized(true);

        //in the "main window" starting with the textArea for the code
        TextArea textArea = new TextArea();
        textArea.setStyle("-fx-font-size: 14");
        textArea.setPromptText("Write you code here");

        //menu for the top of the window
        MenuBar menuBar = new MenuBar();

        //menu 1: File -> Open, Save, Close
        Menu menu1 = new Menu("File");
        MenuItem open = new Menu("Open");
        open.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Script");
            File file = fileChooser.showOpenDialog(primaryStage);

            textArea.clear();
            try (BufferedReader in = Files.newBufferedReader(Paths.get(file.getAbsolutePath()))) {
                String line;
                while ((line = in.readLine()) != null) {
                    textArea.appendText(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        MenuItem save = new Menu("Save");
        save.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Script");
            File file = fileChooser.showSaveDialog(primaryStage);

            ObservableList<CharSequence> paragraph = textArea.getParagraphs();
            Iterator<CharSequence> iter = paragraph.iterator();
            try (BufferedWriter out = Files.newBufferedWriter(Paths.get(file.getAbsolutePath()))) {
                while (iter.hasNext()) {
                    CharSequence seq = iter.next();
                    out.append(seq);
                    out.newLine();
                }
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        MenuItem close = new Menu("Close");
        close.setOnAction(actionEvent -> primaryStage.close());
        menu1.getItems().addAll(open, save, close);


        //menu 3 ->
        Menu menu2 = new Menu("Edit");
//        MenuItem a = new Menu("a");
//        MenuItem a = new Menu("a");
//        MenuItem a = new Menu("a");
//        menu2.getItems().addAll();


        //menu 3 ->
        Menu menu3 = new Menu("View");
//        MenuItem b = new Menu("b");
//        MenuItem b = new Menu("b");
//        MenuItem b = new Menu("b");
//        menu3.getItems().addAll();

        menuBar.getMenus().addAll(menu1, menu2, menu3);
        //why VBox and not HBox: a VBox uses you the whole content on the top like a nav header
        VBox vBox = new VBox(menuBar);
        SubScene subScene = new SubScene(vBox, Screen.getPrimary().getBounds().getWidth(), 24);


        //main screen
        //splitPane for the "main screen"
        SplitPane splitPane = new SplitPane(new FlowPane(), textArea);
        splitPane.setDividerPositions(0.2);
        SubScene subScene1 = new SubScene(splitPane, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight() - 200);

        VBox fullScene = new VBox();
        fullScene.getChildren().addAll(subScene, subScene1);
        primaryStage.setScene(new Scene(fullScene));
        primaryStage.show();
    }
}