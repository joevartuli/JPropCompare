package jpropcompare;

import jpropcompare.comparator.Action;
import jpropcompare.comparator.ComparePropertyFile;
import jpropcompare.exception.ActionNotFoundException;
import jpropcompare.exception.ComparatorException;
import jpropcompare.loading.strategy.LoadingStrategy;
import jpropcompare.exception.LoadingStrategyException;
import jpropcompare.output.ConsoleOutput;
import jpropcompare.output.FileOutput;
import jpropcompare.output.Output;

import java.io.PrintStream;

/**
 * @author: Joe Vartuli
 * @date: 19/09/11
 */
public class Comparator {

    private PrintStream out = null;
    private Action action = null;
    private String actionName = null;
    private String propertyName1 = null;
    private String propertyName2 = null;
    private LoadingStrategy loadingStrategy = null;
    private String loadingStrategyClassName = null;
    private String[] args = null;
    private String outputFilename = null;

    private ComparePropertyFile comparePropertyFile;

    public Comparator() {
        this(null);
    }

    public Comparator(PrintStream out) {
        action = Action.getDefaultAction();
        this.out = out;
        if (this.out == null) {
            this.out = System.out;
        }
    }

    public void run(String... args) {
        this.args = args;

        Arguments nextArgument = null;
        for (String arg : args) {
            if (nextArgument != null) {
                switch (nextArgument) {
                    case PROPERTY_1:
                        propertyName1 = arg;
                        break;
                    case PROPERTY_2:
                        propertyName2 = arg;
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
                Arguments argument = Arguments.getArgument(arg);
                if (argument != null) {
                    nextArgument = argument;
                } else {
                    out.println(Constants.NOT_VALID_ARG + arg);
                    printHelpMessage();
                }
            }
        }

        if (isArgumentValid()) {
            try {
                if (loadingStrategyClassName != null) {
                    instantiateLoadingStrategy();
                }

                if (actionName != null) {
                    determineActionToPerform();
                }

                Output output;
                if (outputFilename != null) {
                    output = getFileOutput();
                } else {
                   output = new ConsoleOutput();
                }

                if (loadingStrategy != null) {
                    comparePropertyFile = new ComparePropertyFile(loadingStrategy, action, output);
                } else {
                    comparePropertyFile = new ComparePropertyFile(propertyName1, propertyName2, action, output);
                }

                comparePropertyFile.execute();
            } catch (ComparatorException e) {
                out.println(e.getMessage());
                printHelpMessage();
            }
        } else {
            printHelpMessage();
        }
    }

    private void instantiateLoadingStrategy() throws LoadingStrategyException {
        try {
            Class<?> loadedClass = this.getClass().getClassLoader().loadClass(loadingStrategyClassName);
            if (loadedClass.isAssignableFrom(LoadingStrategy.class)) {
                try {
                    loadingStrategy = (LoadingStrategy) loadedClass.newInstance();
                    loadingStrategy.setPropertyName1(propertyName1);
                    loadingStrategy.setPropertyName2(propertyName2);
                    loadingStrategy.setArgs(args);
                } catch (InstantiationException e) {
                    throw new LoadingStrategyException(Constants.CLASS_NOT_CREATED.replace(Constants.CLASS_SUBSTITUTE, loadingStrategyClassName), e);
                } catch (IllegalAccessException e) {
                    throw new LoadingStrategyException(Constants.CLASS_NOT_CREATED.replace(Constants.CLASS_SUBSTITUTE, loadingStrategyClassName), e);
                }
            }
        } catch (ClassNotFoundException e) {
            throw new LoadingStrategyException(Constants.CLASS_NOT_FOUND.replace(Constants.CLASS_SUBSTITUTE, loadingStrategyClassName), e);
        }
    }

    private Output getFileOutput() {
        return new FileOutput(this.outputFilename);
    }

    private void determineActionToPerform() throws ActionNotFoundException {
        this.action = Action.getAction(actionName);
        if (this.action == null) {
            throw new ActionNotFoundException(Constants.ACTION_NOT_FOUND.replace(Constants.REQUESTED_ACTION, actionName));
        }
    }

    private void printHelpMessage() {
        out.println("Usage: java - jar <JAR_FILE> -p1 <FILE_NAME> -p2 <FILE_NAME> [-ls <LOADING_STRATEGY>] [-a <ACTION>]");
        out.println("  -p1: file of the property name to compare");
        out.println("  -p2: file of the property name to compare");
        out.println("  -ls: custom loading strategy used to load properties in a complex way.");
        out.println("       If this is used the values of p1 and p2 are injected into the class");
        out.println("        as a way for you to load different property structures in different ways.");
        out.println("  -a: action to perform");
    }

    private boolean isArgumentValid() {
        return propertyName1 != null & propertyName2 != null;
    }

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


    public static void main(String... args) {
        Comparator comparator = new Comparator();
        comparator.run(args);
    }

}
