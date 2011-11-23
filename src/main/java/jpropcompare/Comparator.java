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
 * Author: Joe Vartuli
 * Date: 19/09/11
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
        if (args != null) {
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
                    nextArgument = Arguments.getArgument(arg);
                    if (nextArgument == null) {
                        out.println(Constants.NOT_VALID_ARG + arg);
                    }
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
                   output = new FileOutput(outputFilename, propertyName1, propertyName2);
                } else {
                   output = new ConsoleOutput(propertyName1, propertyName2);
                }

                if (loadingStrategy != null) {
                    comparePropertyFile = new ComparePropertyFile(loadingStrategy, action, output);
                } else {
                    comparePropertyFile = new ComparePropertyFile(propertyName1, propertyName2, action, output);
                }

                comparePropertyFile.runVerboseComparison();
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
            if (LoadingStrategy.class.isAssignableFrom(loadedClass)) {
                try {
                    loadingStrategy = (LoadingStrategy) loadedClass.newInstance();
                    loadingStrategy.initialise(propertyName1, propertyName2, args);
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

    private void determineActionToPerform() throws ActionNotFoundException {
        this.action = Action.getAction(actionName);
        if (this.action == null) {
            throw new ActionNotFoundException(Constants.ACTION_NOT_FOUND.replace(Constants.REQUESTED_ACTION, actionName));
        }
    }

    private void printHelpMessage() {
        StringBuilder stringBuilder = new StringBuilder("Usage: java - jar <JAR_FILE> -p1 <FILE_NAME> -p2 <FILE_NAME> [-ls <LOADING_STRATEGY>] [-a <ACTION>]" + Constants.NEW_LINE);
        stringBuilder.append("  -p1: file of the property name to compare" + Constants.NEW_LINE);
        stringBuilder.append("  -p2: file of the property name to compare" + Constants.NEW_LINE);
        stringBuilder.append("  -ls: custom loading strategy used to load properties in a complex way." + Constants.NEW_LINE);
        stringBuilder.append("       If this is used the values of p1 and p2 are injected into the class" + Constants.NEW_LINE);
        stringBuilder.append("       as a way for you to load different property structures in different ways." + Constants.NEW_LINE);
        stringBuilder.append("  -a: action to perform. Possible values");
        stringBuilder.append("      1: ");
        stringBuilder.append("      2: ");
        stringBuilder.append("      3: ");
        stringBuilder.append("      4: ");
        stringBuilder.append("      5: ");
        out.println(stringBuilder.toString());
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
        Comparator comparator = new Comparator(null);
        comparator.run(args);
    }

}
