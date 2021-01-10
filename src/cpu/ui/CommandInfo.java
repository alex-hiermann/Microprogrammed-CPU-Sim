package cpu.ui;

/**
 * @author Alex Hiermann
 */
public class CommandInfo {

    /**
     * the name of the command
     */
    private final String commandSyntax;

    /**
     * the usage of the first operator
     */
    private final String operator1;

    /**
     * the usage of the second operator
     */
    private final String operator2;

    /**
     * the usage of the whole command
     */
    private final String description;


    /**
     * default constructor
     * is used to define all info the user could need about any command
     *
     * @param commandSyntax the name of the command
     * @param operator1     the usage of the first operator
     * @param operator2     the usage of the second operator
     * @param description   the usage of the whole command
     */
    public CommandInfo(String commandSyntax, String operator1, String operator2,
                       String description) {
        this.commandSyntax = commandSyntax;
        this.operator1 = operator1;
        this.operator2 = operator2;
        this.description = description;
    }

    /**
     * @return the name of the command
     */
    public String getCommandSyntax() {
        return commandSyntax;
    }

    /**
     * @return the usage of the first operator
     */
    public String getOperator1() {
        return operator1;
    }

    /**
     * @return the usage of the second operator
     */
    public String getOperator2() {
        return operator2;
    }

    /**
     * @return the usage of the whole command
     */
    public String getDescription() {
        return description;
    }
}