package cpu.ui;

import cpu.Program;
import cpu.Script;
import cpu.bus.AddressBus;
import cpu.bus.ControlBus;
import cpu.bus.DataBus;
import cpu.computer.Memory;
import cpu.computer.Processor;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
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
     * used for the console as a textArea
     */
    public static TextArea consoleOutput = new TextArea();

    /**
     * used for the divider Position
     */
    final float dividerPosition = 0.3f;

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
        textArea.getStylesheets().add(getClass().getResource(
                "/css/darkArea.css").toExternalForm());
        textArea.getStylesheets().add("https://fonts.googleapis.com/css2?family=Source+Code+Pro&");
        textArea.setStyle("-fx-font-family: 'Source Code Pro', monospace; -fx-font-size: 14");

        //label for the history titled pane
        Label historyLabel = new Label("History");
        historyLabel.setStyle("-fx-underline: true");
        //vBox for the history titled pane
        VBox vBox = new VBox(historyLabel);
        vBox.getStylesheets().add(getClass().getResource(
                "/css/lightArea.css").toExternalForm());
        //table for the database titled pane
        TableView<CommandInfo> table = new TableView<>();
        ObservableList<CommandInfo> data = FXCollections.observableArrayList(
                new CommandInfo("add op1, op2;", "op1", "op2 + save location",
                        "Adds op1 and op2"),
                new CommandInfo("and op1, op2;", "op1 + save location", "op2",
                        "Logical bitwise \"and\" with the input from "
                                + "the location \"op1\" and \"op2\""),
                new CommandInfo("dec op1;", "op1 + save location", "",
                        "Decrements the input from location \"op1\""),
                new CommandInfo("hlt;", "", "",
                        "Stops the current Program"),
                new CommandInfo("idiv op1, op2;", "op1", "op2 + save location",
                        "Divides op1 with the input from location \"op2\""),
                new CommandInfo("imul op1, op2;", "op1", "op2 + save location",
                        "Multiplies op1 with the input from location \"op2\""),
                new CommandInfo("inc op1;", "op1 + save location", "",
                        "Increments the input from location \"op1\""),
                new CommandInfo("mov op1, op2;", "op1", "op2 + save location",
                        "Moves input from location \"op1\" to \"op2\""),
                new CommandInfo("neg op1;", "op1 + save location", "",
                        "Negates the input from location \"op1\""),
                new CommandInfo("not op1;", "op1 + save location", "",
                        "Flips all bits of the input from location \"op1\""),
                new CommandInfo("or op1, op2;", "op1 + save location", "op2",
                        "Logical bitwise \"or\" with the input from "
                                + "the location \"op1\" and \"op2\""),
                new CommandInfo("pop op1;", "op1 + save location", "",
                        "Pops the last input from the stack and saves it at location \"op1\""),
                new CommandInfo("push op1;", "op1", "",
                        "Pushes the input from location \"op1\" onto the stack"),
                new CommandInfo("shl op1, op2;", "op1 + save location", "op2",
                        "shifts the input from location \"op1\" by op2 into the left direction"
                                + " and saves it at op1"),
                new CommandInfo("shr op1, op2;", "op1 + save location", "op2",
                        "shifts the input from location \"op1\" by op2 into the right direction"
                                + " and saves it at op1"),
                new CommandInfo("sub op1, op2;", "op1 + save location", "op2",
                        "subtracts op2 from op1 and saves the result at \"op2\""),
                new CommandInfo("xor op1, op2;", "op1 + save location", "op2",
                        "Logical bitwise \"xor\" with the input from "
                                + "the location \"op1\" and \"op2\"")
        );
        table.setEditable(true);
        table.getStylesheets().add(getClass().getResource(
                "/css/lightArea.css").toExternalForm());


        TableColumn commandSyntax = new TableColumn("Command Syntax");
        commandSyntax.setCellValueFactory(new PropertyValueFactory<CommandInfo,
                String>("commandSyntax"));

        TableColumn operator1 = new TableColumn("Operator 1");
        operator1.setCellValueFactory(new PropertyValueFactory<CommandInfo, String>("operator1"));

        TableColumn operator2 = new TableColumn("Operator 2");
        operator2.setCellValueFactory(new PropertyValueFactory<CommandInfo, String>("operator2"));

        TableColumn description = new TableColumn("Description");
        description.setCellValueFactory(new PropertyValueFactory<CommandInfo,
                String>("description"));

        table.setItems(data);
        table.getColumns().addAll(commandSyntax, operator1, operator2, description);

        //left pane with a database containing all possible commands on the top
        TitledPane database = new TitledPane("Database", table);
        //and a history of the last recent opened files below
        TitledPane history = new TitledPane("Recent Files", new Label("-- - Empty - --"));
        //both titled panes are stored in the following vbox
        VBox leftBox = new VBox(database, history);
        leftBox.setStyle("-fx-background-color: #a3a3a3");


        //menu for the top of the window
        MenuBar menuBar = new MenuBar();

        //menu 1: File -> Open, Save, Close
        Menu menu1 = new Menu("File");
        MenuItem open = new Menu("Open");
        open.setOnAction(actionEvent -> {
            openFile(primaryStage, textArea, vBox, Path.of(""), false);
            history.setContent(vBox);
        });

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
        header.getStylesheets().add(getClass().getResource(
                "/css/lightArea.css").toExternalForm());


        //splitPane for the "main screen"
        SplitPane splitPane = new SplitPane(leftBox, textArea);
        splitPane.setOrientation(Orientation.HORIZONTAL);
        splitPane.setDividerPositions(dividerPosition, 1);

        consoleOutput.getStylesheets().add(getClass().getResource(
                "/css/darkArea.css").toExternalForm());
        consoleOutput.getStylesheets().add("https://fonts.googleapis.com/css2?family=Source+Code+Pro&");
        consoleOutput.setStyle("-fx-font-family: 'Source Code Pro', monospace; -fx-font-size: 14");

        SplitPane fullscreen = new SplitPane(splitPane, consoleOutput);
        fullscreen.setOrientation(Orientation.VERTICAL);
        fullscreen.prefHeightProperty().bind(primaryStage.widthProperty().multiply(0.80));
        fullscreen.setDividerPositions(0.9);

        primaryStage.setHeight(1080);
        primaryStage.setWidth(1920);

        primaryStage.setScene(new Scene(new VBox(header, fullscreen)));
        primaryStage.show();
    }

    /**
     * @param stage    the stage
     * @param textArea the textarea itself (if it's not a file)
     * @param vBox     the flowPane
     * @param path     path of the file (if it's a file)
     * @param isFile   true if it's a file, false if it's a textarea
     */
    public void openFile(Stage stage, TextArea textArea, VBox vBox, Path path,
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
            if (showWarningAgain) quickOpenFile(stage, textArea, vBox, filePath);
            else openFile(stage, textArea, vBox, filePath, true);
        });
        if (!hyperLinks.contains(filePath.toString())) {
            hyperLinks.add(filePath.toString());
            vBox.getChildren().add(hyperlink);
        }
    }

    /**
     * @param primaryStage the stage
     * @param textArea     the textarea itself (if it's not a file)
     * @param vBox         the flowPane
     * @param path         path of the file (if it's a file)
     */
    public void quickOpenFile(Stage primaryStage, TextArea textArea, VBox vBox, Path path) {
        if (showWarningAgain) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Quick-Open File");
            alert.setResizable(false);
            alert.setHeaderText("Warning: Please be aware of the following");
            alert.setContentText("If you open a new File, be sure, "
                    + "that you have already saved your old script!");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isEmpty()) {
                //alert was closed, no button has been pressed.
                showWarningAgain = false;
                openFile(primaryStage, textArea, vBox, path, true);
                alert.close();
            } else if (result.get() == ButtonType.OK) {
                //"ok"-button is pressed
                showWarningAgain = false;
                openFile(primaryStage, textArea, vBox, path, true);
                alert.close();
            } else if (result.get() == ButtonType.CANCEL) {
                //"cancel"-button is pressed
                alert.close();
            }
        } else openFile(primaryStage, textArea, vBox, path, true);
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