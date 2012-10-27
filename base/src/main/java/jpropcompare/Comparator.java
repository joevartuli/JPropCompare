package jpropcompare;

import jpropcompare.comparator.Action;
import jpropcompare.comparator.ComparePropertyFile;
import jpropcompare.exception.ActionNotFoundException;
import jpropcompare.exception.ComparatorException;
import jpropcompare.exception.LoadingStrategyException;
import jpropcompare.loading.strategy.LoadingStrategy;
import jpropcompare.output.ConsoleOutput;
import jpropcompare.output.FileOutput;
import jpropcompare.output.Output;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;

/**
 * The Comparator class provides access to the ComparePropertyFile tool via the command line.
 * Author: Joe Vartuli
 * Date: 19/09/11
 */
public class Comparator {

    private PrintStream out = null;
    private Action action = null;
    private String actionName = null;
    private String fileA = null;
    private String fileB = null;
    private LoadingStrategy loadingStrategy = null;
    private String loadingStrategyClassName = null;
    private String[] args = null;
    private String outputFilename = null;

    private ComparePropertyFile comparePropertyFile;

    public Comparator(PrintStream out) {
        action = Action.getDefaultAction();
        this.out = out;
        if (this.out == null) {
            this.out = System.out;
        }
    }

    public void run(String... args) {
        this.args = args;

        parseArguments();

        try {
            instantiateComparisonTool();
            comparePropertyFile.runComparison();

        } catch (ComparatorException comparatorException) {
            out.println(comparatorException.getMessage() + Constants.NEW_LINE);
            printHelpMessage();
        }
    }

    /**
     * Parses the command line arguments passed in
     */
    private void parseArguments() {

        Arguments nextArgument = null;

        if (args != null) {
            for (String arg : args) {

                if (nextArgument != null) {
                    switch (nextArgument) {
                        case PROPERTY_1:
                            fileA = arg;
                            break;
                        case PROPERTY_2:
                            fileB = arg;
                            break;
                        case LOADING_STRATEGY:
                            loadingStrategyClassName = arg;
                            break;
                        case ACTION:
                            actionName = arg;
                            break;
                        case FILE_OUTPUT:
                            outputFilename = arg;
                            break;
                    }
                    nextArgument = null;
                } else {
                    nextArgument = Arguments.getArgument(arg);
                    if (nextArgument == null) {
                        out.println(Constants.NOT_VALID_ARG + arg);
                    }
                }
            }

        }
    }

    /**
     * Instantiates the ComparePropertyFile object
     *
     * @return true if the ComparePropertyFile object was instantiated correctly, otherwise false
     */
    private void instantiateComparisonTool() throws ComparatorException {

        validateArguments();
        if (loadingStrategyClassName != null) {
            instantiateLoadingStrategy();
        }

        if (actionName != null) {
            determineActionToPerform();
        }

        try {
            FileReader fileReaderA = new FileReader(fileA);
            FileReader fileReaderB = new FileReader(fileB);
            Output output;
            if (outputFilename != null) {
                output = new FileOutput(outputFilename, fileA, fileB);
            } else {
                output = new ConsoleOutput(fileA, fileB);
            }

            if (loadingStrategy != null) {
                comparePropertyFile = new ComparePropertyFile(loadingStrategy, action, output);
            } else {
                comparePropertyFile = new ComparePropertyFile(fileReaderA, fileReaderB, action, output);
            }
        } catch (FileNotFoundException e) {
            throw new ComparatorException(e);
        }

    }

    /**
     * If a Loading Strategy class is provided attempt to instantiate the class and ensure it implements
     * the LoadingStrategy interface
     *
     * @throws LoadingStrategyException
     */
    private void instantiateLoadingStrategy() throws LoadingStrategyException {
        try {
            Class<?> loadedClass = this.getClass().getClassLoader().loadClass(loadingStrategyClassName);
            if (LoadingStrategy.class.isAssignableFrom(loadedClass)) {
                try {
                    loadingStrategy = (LoadingStrategy) loadedClass.newInstance();
                    loadingStrategy.initialise(fileA, fileB, args);
                } catch (InstantiationException e) {
                    throw new LoadingStrategyException(Constants.CLASS_NOT_CREATED.replace(Constants.CLASS_SUBSTITUTE, loadingStrategyClassName), e);
                } catch (IllegalAccessException e) {
                    throw new LoadingStrategyException(Constants.CLASS_NOT_CREATED.replace(Constants.CLASS_SUBSTITUTE, loadingStrategyClassName), e);
                }
            } else {
                throw new LoadingStrategyException(Constants.CLASS_NOT_CREATED.replace(Constants.CLASS_SUBSTITUTE, loadingStrategyClassName));
            }
        } catch (ClassNotFoundException e) {
            throw new LoadingStrategyException(Constants.CLASS_NOT_FOUND.replace(Constants.CLASS_SUBSTITUTE, loadingStrategyClassName), e);
        }
    }

    /**
     * Determines the action to perform on the given property files
     *
     * @throws ActionNotFoundException
     */
    private void determineActionToPerform() throws ActionNotFoundException {
        this.action = Action.getAction(actionName);
        if (this.action == null) {
            throw new ActionNotFoundException(Constants.ACTION_NOT_FOUND.replace(Constants.REQUESTED_ACTION, actionName));
        }
    }

    /**
     * Prints the help message
     */
    private void printHelpMessage() {
        StringBuilder stringBuilder = new StringBuilder("Usage: java - jar <JAR_FILE> -p1 <FILE_NAME> -p2 <FILE_NAME> [-ls <LOADING_STRATEGY>] [-a <ACTION>]" + Constants.NEW_LINE);
        stringBuilder.append("  -p1: file of the property name to compare" + Constants.NEW_LINE);
        stringBuilder.append("  -p2: file of the property name to compare" + Constants.NEW_LINE);
        stringBuilder.append("  -ls: custom loading strategy used to load properties in a complex way." + Constants.NEW_LINE);
        stringBuilder.append("       If this is used the values of p1 and p2 are injected into the class" + Constants.NEW_LINE);
        stringBuilder.append("       as a way for you to load different property structures in different ways." + Constants.NEW_LINE);
        stringBuilder.append("  -a: action to perform. Possible values" + Constants.NEW_LINE);

        for (Action possibleAction : Action.values()) {
            stringBuilder.append("      " + possibleAction.getActionValue() + ": " + possibleAction.getActionDescription() + Constants.NEW_LINE);
        }
        out.println(stringBuilder.toString());
    }

    /**
     * Validates the given command line arguments
     *
     * @throws ComparatorException
     */
    private void validateArguments() throws ComparatorException {
        if (fileA == null) {
            throw new ComparatorException("First property name file can not be null");
        }
        if (fileB == null) {
            throw new ComparatorException("Second property name file can not be null");
        }
    }

    /**
     * Current supported arguments
     */
    private enum Arguments {

        PROPERTY_1("-p1"),
        PROPERTY_2("-p2"),
        ACTION("-a"),
        LOADING_STRATEGY("-ls"),
        FILE_OUTPUT("-f");

        private String arg;

        Arguments(String arg) {
            this.arg = arg;
        }

        public static Arguments getArgument(String arg) {
            Arguments argument = null;

            for (Arguments thisArgument : Arguments.values()) {
                if (thisArgument.arg.equals(arg)) {
                    argument = thisArgument;
                    break;
                }
            }

            return argument;
        }
    }

    /**
     * Main method used to instantiate the comparison tool
     *
     * @param args - command line arguments
     */
    public static void main(String... args) {
        Comparator comparator = new Comparator(null);
        comparator.run(args);
    }

}
