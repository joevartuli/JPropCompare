package jpropcompare.comparator;

import jpropcompare.loading.strategy.LoadingStrategy;
import jpropcompare.output.Output;

import java.io.*;
import java.util.Properties;

/**
 * CompareProperties is the main class that provides comparison
 * functionality.
 *
 * Author: Joe Vartuli
 * Date: 19/09/11
 * @since 1.0
 */
public class CompareProperties {

    private Properties a;
    private Properties b;
    private Action action;
    private Output output;

    /**
     * Initialises this class with a loading strategy, action to perform and an output class
     * @param strategy - loading strategy used to create the custom property files required
     * @param action - action to perform on the property files
     * @param output - where to output the results
     */
    public CompareProperties(LoadingStrategy strategy, Action action, Output output) {
        this(strategy.getPropertyFileOne(), strategy.getPropertyFileTwo(), action, output);
    }

    /**
     * Initialises this class with a the two files of the property file, action to perform and an output class
     * @param a - file representing the first property file used in the comparison.
     * @param b - file representing the second property file used in the comparison.
     * @param action - action to perform on the property files
     * @param output - where to output the results
     */
    public CompareProperties(Reader a, Reader b, Action action, Output output) {
        this((Properties)null, (Properties)null, action, output);

        try {
            this.a = new Properties();
            this.a.load(a);

            this.b = new Properties();
            this.b.load(b);
        } catch (IOException e) {
            throw new IllegalArgumentException("An exception occurred whilst reading input", e);
        }

    }

    /**
     *
     * Initialises this class with a the two property files, the action to perform and an output class
     * @param a - first property file used in the comparison
     * @param b - second property file used in the comparison
     * @param action - action to perform on the property files
     * @param output - where to output the results
     */
    public CompareProperties(Properties a, Properties b, Action action, Output output) {
        this.action = action;
        this.output = output;
        this.a = a;
        this.b = b;
    }

    /**
     * Executes the specific action over the given property files
     * @return Object representing the result of the comparison
     */
    public ComparisonResult runComparison() {
        ComparisonRunner comparisonRunner = new ComparisonRunner(a, b, action);

        ComparisonResult comparisonResult = comparisonRunner.runComparison();

        if (output!= null) {
            output.outputResult(comparisonResult);
        }
        return comparisonResult;
    }

}
