import cpu_sim.Program;
import cpu_sim.Script;
import cpu_sim.computer.Memory;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Alex Hiermann
 */
public class Main extends Application {

    //for new warning when quick opening another file
    boolean showWarningAgain = true;

    //stores the hyperlinks to compare with, so that they don't duplicate
    Set<String> hyperLinks = new HashSet<>();

    //the program to execute your code
    Program program = new Program();

    public static void main(String[] args) {
//        //first test with memory write and read
//        Memory C = new Memory();
//        C.setMemory(10, new boolean[]{true, false, false, true, false});
//        System.out.println(C.getMemory(10, 5));
//
//        //testing the convert function from both file to script and also textArea to script
//        Script script1 = convertToScript(Path.of("C:/Users/mrale/IdeaProjects/Microprogrammed_CPU/resources/testFiles/testFile3.txt"), new TextArea(), true);
//        System.out.println(script1.toString());
//
//        Script script2 = convertToScript(Path.of(""), new TextArea("Was sollte in einem Skript stehen? Code."), false);
//        System.out.println(script2.toString());
//
//        Script script1 = convertToScript(Path.of("C:/Users/mrale/IdeaProjects/Microprogrammed_CPU/resources/testFiles/testFile1.txt"), new TextArea(), true);
//        System.out.println(script1.compile());

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

        FlowPane flowPane = new FlowPane();
        flowPane.setPadding(new Insets(4, 0, 0, 5));


        //menu for the top of the window
        MenuBar menuBar = new MenuBar();

        //menu 1: File -> Open, Save, Close
        Menu menu1 = new Menu("File");
        MenuItem open = new Menu("Open");
        open.setOnAction(actionEvent -> openFile(primaryStage, textArea, flowPane, Path.of(""), false));

        MenuItem save = new Menu("Save");
        save.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Script");
            File file = fileChooser.showSaveDialog(primaryStage);
            if (file == null) return;

            ObservableList<CharSequence> paragraph = textArea.getParagraphs();
            Iterator<CharSequence> iter = paragraph.iterator();
            try (BufferedWriter out = Files.newBufferedWriter(Path.of(file.getAbsolutePath()))) {
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

        //menu 2 ->
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


        //the buttons on the top right to start, stop or debug
        Image buttonImage = new Image("/buttonImages/stop.png");
        ImageView buttonImageView = new ImageView(buttonImage);
        Button stop = new Button();
        stop.setGraphic(buttonImageView);

        buttonImage = new Image("/buttonImages/debug.png");
        buttonImageView = new ImageView(buttonImage);
        Button debug = new Button();
        debug.setGraphic(buttonImageView);

        buttonImage = new Image("/buttonImages/start.png");
        buttonImageView = new ImageView(buttonImage);
        Button start = new Button();
        start.setGraphic(buttonImageView);
        start.setOnAction(actionEvent -> {
            program.setScript(convertToScript(Path.of(""), textArea, false));
            program.run();
        });

        //header nav bar
        HBox hBoxButtons = new HBox(stop, debug, start);
        Region region = new Region();

        HBox.setHgrow(region, Priority.ALWAYS);
        HBox header = new HBox(menuBar, region, hBoxButtons);
        SubScene subScene = new SubScene(header, Screen.getPrimary().getBounds().getWidth(), 24);


        //splitPane for the "main screen"
        SplitPane splitPane = new SplitPane(flowPane, textArea);
        splitPane.setDividerPositions(0.2);
        SubScene subScene1 = new SubScene(splitPane, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight() - 200);

        VBox fullScene = new VBox();
        fullScene.getChildren().addAll(subScene, subScene1);
        primaryStage.setScene(new Scene(fullScene));
        primaryStage.show();
    }

    /**
     * @param stage the stage
     * @param textArea the textarea itself (if it's not a file)
     * @param flowPane the flowpane
     * @param path path of the file (if it's a file)
     * @param isFile true if it's a file, false if it's a textarea
     */
    public void openFile(Stage stage, TextArea textArea, FlowPane flowPane, Path path, Boolean isFile) {
        Path filePath;
        if (!isFile) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Script");
            File file = fileChooser.showOpenDialog(stage);
            if (file == null) return;
            filePath = Path.of(file.getAbsolutePath());
        } else filePath = path;

        textArea.clear();
        try (BufferedReader in = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = in.readLine()) != null) {
                textArea.appendText(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Hyperlink hyperlink = new Hyperlink(filePath.toString());
        hyperlink.setOnAction(event -> {
            if (showWarningAgain) quickOpenFile(stage, textArea, flowPane, filePath);
            else openFile(stage, textArea, flowPane, filePath, true);
        });
        if (!hyperLinks.contains(filePath.toString())) {
            hyperLinks.add(filePath.toString());
            flowPane.getChildren().add(hyperlink);
        }
    }

    /**
     * @param primaryStage the stage
     * @param textArea the textarea itself (if it's not a file)
     * @param flowPane the flowpane
     * @param path path of the file (if it's a file)
     */
    public void quickOpenFile(Stage primaryStage, TextArea textArea, FlowPane flowPane, Path path) {
        Stage stage = new Stage();
        stage.setTitle("Quick-Open File");
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/icon.png")));
        stage.setResizable(false);
        stage.setWidth(280);
        stage.setHeight(120);

        Label warning = new Label("WARNING: If you open a new File, be sure, \n that you have already saved your old script.");

        Button goAhead = new Button("Go ahead");
        goAhead.setOnAction(actionEvent -> {
            openFile(primaryStage, textArea, flowPane, path, true);
            stage.close();
        });

        Button cancel = new Button("Cancel");
        cancel.setOnAction(actionEvent -> stage.close());

        CheckBox stopReminding = new CheckBox("Don't remind me again");
        stopReminding.setOnAction(actionEvent -> showWarningAgain = false);

        VBox vBox = new VBox(warning);
        vBox.getChildren().addAll(goAhead, cancel, stopReminding);
        Scene scene = new Scene(vBox);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param path path of the file (if it's a file)
     * @param textArea the textarea itself (if it's not a file)
     * @param isFile true if it's a file, false if it's a textarea
     * @return the input of the file / textarea as a script
     */
    public static Script convertToScript(Path path, TextArea textArea, boolean isFile) {
        StringBuilder input = new StringBuilder();

        if (isFile) {
            try (BufferedReader in = Files.newBufferedReader(path)) {
                String line;
                while ((line = in.readLine()) != null) {
                    input.append(line).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            ObservableList<CharSequence> paragraph = textArea.getParagraphs();
            for (CharSequence seq : paragraph) {
                input.append(seq).append("\n");
            }
        }
        return new Script(input.toString());
    }
}