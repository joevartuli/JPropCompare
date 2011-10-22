package jpropcompare.comparator;

import jpropcompare.loading.strategy.LoadingStrategy;
import jpropcompare.output.Output;
import jpropcompare.utilities.PropertyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static java.util.AbstractMap.*;

/**
 * ComparePropertyFile is the main class that provides comparison
 * functionality.
 *
 * Author: Joe Vartuli
 * Date: 19/09/11
 * @since 1.0
 */
public class ComparePropertyFile {

    private Properties propertyFileOne;
    private Properties propertyFileTwo;
    private Action action;
    private Output output;
    private ComparisonResultBuilder comparisonResultBuilder;

    /**
     * Initialises this class with a loading strategy, action to perform and an output class
     * @param strategy - loading strategy used to create the custom property files required
     * @param action - action to perform on the property files
     * @param output - where to output the results
     */
    public ComparePropertyFile(LoadingStrategy strategy, Action action, Output output) {
        this(strategy.getPropertyFileOne(), strategy.getPropertyFileTwo(), action, output);
        comparisonResultBuilder.setFileNameOne(strategy.getPropertyNameOne());
        comparisonResultBuilder.setFileNameTwo(strategy.getPropertyNameTwo());
    }

    /**
     * Initialises this class with a the two file names of the property file, action to perform and an output class
     * @param fileNameOne - file name representing the first property file used in the comparison. Needs to be on the classpath
     * @param fileNameTwo - file name representing the second property file used in the comparison. Needs to be on the classpath
     * @param action - action to perform on the property files
     * @param output - where to output the results
     */
    public ComparePropertyFile(String fileNameOne, String fileNameTwo, Action action, Output output) {
        this((Properties)null, (Properties)null, action, output);

        if (fileNameOne == null || fileNameOne.trim().equals("")) {
            throw new IllegalArgumentException("fileNameOne can not be null or empty");
        }

        if (fileNameTwo == null || fileNameTwo.trim().equals("")) {
            throw new IllegalArgumentException("fileNameTwo can not be null or empty");
        }

        comparisonResultBuilder.setFileNameOne(fileNameOne);
        comparisonResultBuilder.setFileNameTwo(fileNameTwo);

        try {
            InputStream inputStream1 = this.getClass().getClassLoader().getResourceAsStream(fileNameOne);
            propertyFileOne = new Properties();
            propertyFileOne.load(inputStream1);
        } catch (NullPointerException e) {
            throw new NullPointerException("Could not find property file with name " + fileNameOne);
        } catch (IOException e) {
            throw new IllegalArgumentException("An exception occurred when loading property file with name " + fileNameOne, e);
        }

        try {
            InputStream inputStream2 = this.getClass().getClassLoader().getResourceAsStream(fileNameTwo);
            propertyFileTwo = new Properties();
            propertyFileTwo.load(inputStream2);
        } catch (NullPointerException e) {
            throw new NullPointerException("Could not find property file with name " + fileNameOne);
        }  catch (IOException e) {
            throw new IllegalArgumentException("An exception occurred when loading file with name " + fileNameTwo, e);
        }

    }

    private ComparePropertyFile(Properties propertiesOne, Properties propertiesTwo, Action action, Output output) {
        this.action = action;
        this.output = output;
        this.propertyFileOne = propertiesOne;
        this.propertyFileTwo = propertiesTwo;
        this.comparisonResultBuilder = new ComparisonResultBuilder();
    }

    /**
     * Executes the specific action over the given property files
     * @return Object representing the result of the test
     */
    public ComparisonResult runComparison() {
        return comparePropertyFiles(false);
    }

    /**
     * Executes the specific action over the given property files
     */
    public void runVerboseComparison() {
        comparePropertyFiles(true);
    }

    private ComparisonResult comparePropertyFiles(boolean verbose) {
        ComparisonResult result;

        switch (action) {
            case UNIQUE_NAMES:
                findUniquePropertyNames();
                break;
            case COMPARE_VALUES:
                comparePropertyValues();
                break;
        }

        result = comparisonResultBuilder.build();

        if (verbose) {
            output.outputResult(result);
        }
        return result;
    }

    /**
     *  Finds the unique property names in each file regardless of their value. Useful
     *  for determining property differences between environment properties where you would normally
     *  expect values to be different but have property names consistent
     */
    private void findUniquePropertyNames() {

        Set<String> propertyNamesFromPropertyOne = propertyFileOne.stringPropertyNames();
        Set<String> propertyNamesFromTwo = propertyFileTwo.stringPropertyNames();

        List<String> uniqueToPropertyFileOne = PropertyUtils.difference(propertyNamesFromPropertyOne, propertyNamesFromTwo);
        List<String> uniqueToPropertyFileTwo = PropertyUtils.difference(propertyNamesFromTwo, propertyNamesFromPropertyOne);

        comparisonResultBuilder.setUniqueToPropertyOne(uniqueToPropertyFileOne).setUniqueToPropertyTwo(uniqueToPropertyFileTwo);
    }

    /**
     *
     */
    private void comparePropertyValues() {

        findUniquePropertyNames();

        Map<String, SimpleEntry<String, String>> differences = PropertyUtils.propertyValueDifferences(propertyFileOne, propertyFileTwo);

        comparisonResultBuilder.setPropertyValueDifferences(differences);
    }


}
