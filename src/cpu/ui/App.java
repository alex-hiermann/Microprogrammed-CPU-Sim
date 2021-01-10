package cpu.ui;

import cpu.Program;
import cpu.Script;
import cpu.bus.AddressBus;
import cpu.bus.ControlBus;
import cpu.bus.DataBus;
import cpu.computer.Memory;
import cpu.computer.Processor;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
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
public class App extends Application {

    /**
     * used for new warning, when the user is quick-opening another (new) file
     */
    boolean showWarningAgain = true;

    /**
     * stores the hyperlinks to compare with, so that they don't duplicate themselves
     */
    Set<String> hyperLinks = new HashSet<>();

    /**
     * the memory to save information about the users input
     */
    public static Memory memory = new Memory();

    /**
     * the program to execute the users input
     */
    public static Program program = new Program();

    /**
     * stands for how many registers of 16bit should exists
     */
    private final static int REGISTER16NUMBER = 4;

    /**
     * stands for how many registers of 8bit should exists
     */
    private final static int REGISTER8NUMBER = 8;

    /**
     * the processor to execute commands
     */
    public static Processor processor = new Processor("8085", "3.072 MHz",
            REGISTER16NUMBER, REGISTER8NUMBER);

    /**
     * the address bus to give the processor and memory the used address for data
     */
    public static AddressBus addressBus = new AddressBus("addressBus");

    /**
     * the data bus to transfer data between processor and memory
     */
    public static DataBus dataBus = new DataBus("dataBus");

    /**
     * the control bus to store important information that the processor might need
     */
    public static ControlBus controlBus = new ControlBus("controlBus");

    /**
     * @param args arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * creates the application with gui/javafx window
     *
     * @param primaryStage stage for you javafx application
     */
    @Override
    public void start(Stage primaryStage) {
        //basic and standard window config
        primaryStage.setTitle("CPU-Scripting");
        primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/icon2.png")));
        primaryStage.setMaximized(true);


        //in the "main window" starting with the textArea for the code
        TextArea textArea = new TextArea();
        textArea.setStyle("-fx-font-size: 14");
        textArea.setPromptText("Write you code here");

        FlowPane flowPane = new FlowPane();
        flowPane.setPadding(new Insets(1 << 2, 0, 0, (1 << 2) + 1));


        //menu for the top of the window
        MenuBar menuBar = new MenuBar();

        //menu 1: File -> Open, Save, Close
        Menu menu1 = new Menu("File");
        MenuItem open = new Menu("Open");
        open.setOnAction(actionEvent -> openFile(primaryStage, textArea, flowPane, Path.of("")
                , false));

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
        //MenuItem a = new Menu("a");
        //MenuItem a = new Menu("a");
        //MenuItem a = new Menu("a");
        //menu2.getItems().addAll();

        //menu 3 ->
        Menu menu3 = new Menu("View");
        //MenuItem b = new Menu("b");
        //MenuItem b = new Menu("b");
        //MenuItem b = new Menu("b");
        //menu3.getItems().addAll();

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
        SubScene subScene1 = new SubScene(splitPane, Screen.getPrimary().getBounds().getWidth(),
                Screen.getPrimary().getBounds().getHeight() - 200);

        VBox fullScene = new VBox();
        fullScene.getChildren().addAll(subScene, subScene1);
        primaryStage.setScene(new Scene(fullScene));
        primaryStage.show();
    }

    /**
     * @param stage    the stage
     * @param textArea the textarea itself (if it's not a file)
     * @param flowPane the flowpane
     * @param path     path of the file (if it's a file)
     * @param isFile   true if it's a file, false if it's a textarea
     */
    public void openFile(Stage stage, TextArea textArea, FlowPane flowPane, Path path,
                         Boolean isFile) {
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
     * @param textArea     the textarea itself (if it's not a file)
     * @param flowPane     the flowpane
     * @param path         path of the file (if it's a file)
     */
    public void quickOpenFile(Stage primaryStage, TextArea textArea, FlowPane flowPane, Path path) {
        Stage stage = new Stage();
        stage.setTitle("Quick-Open File");
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/icon2.png")));
        stage.setResizable(false);
        stage.setWidth(280);
        stage.setHeight(120);

        Label warning = new Label("WARNING: If you open a new File, be sure, "
                + "\n that you have already saved your old script.");

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
     * @param path     path of the file (if it's a file)
     * @param textArea the textarea itself (if it's not a file)
     * @param isFile   true if it's a file, false if it's a textarea
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