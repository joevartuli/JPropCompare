package jpropcompare.comparator;

import jpropcompare.Constants;
import jpropcompare.loading.strategy.LoadingStrategy;
import jpropcompare.output.Output;
import jpropcompare.utilities.PropertyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Set;

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
    private String fileNameOne;
    private String fileNameTwo;
    private Action action;
    private Output output;

    /**
     * Initialises this class with a loading strategy, action to perform and an output class
     * @param strategy - loading strategy used to create the custom property files required
     * @param action - action to perform on the property files
     * @param output - where to output the results
     */
    public ComparePropertyFile(LoadingStrategy strategy, Action action, Output output) {
        this.action = action;
        this.output = output;
        this.propertyFileOne = strategy.getPropertyFile1();
        this.fileNameOne = strategy.getPropertyName1();
        this.propertyFileTwo = strategy.getPropertyFile2();
        this.fileNameTwo = strategy.getPropertyName2();
    }

    /**
     * Initialises this class with a the two file names of the property file, action to perform and an output class
     * @param fileNameOne - file name representing the first property file used in the comparison. Needs to be on the classpath
     * @param fileNameTwo - file name representing the second property file used in the comparison. Needs to be on the classpath
     * @param action - action to perform on the property files
     * @param output - where to output the results
     */
    public ComparePropertyFile(String fileNameOne, String fileNameTwo, Action action, Output output) {

        if (fileNameOne == null || fileNameOne.trim().equals("")) {
            throw new IllegalArgumentException("fileNameOne can not be null or empty");
        }

        if (fileNameTwo == null || fileNameTwo.trim().equals("")) {
            throw new IllegalArgumentException("fileNameTwo can not be null or empty");
        }

        this.action = action;
        this.output = output;
        this.fileNameOne = fileNameOne;
        this.fileNameTwo = fileNameTwo;


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

    /**
     * Executes the specific action over the given property files
     * @return Object representing the result of the test
     */
    public ComparisonResult execute() {
        ComparisonResult result = null;
        switch (action) {
            case UNIQUE_NAMES:
                result = uniquePropertyNames();
                break;
        }
        output.finalise();
        return result;
    }

    /**
     *  Finds the unique property names in each file regardless of their value. Useful
     *  for determining property differences between environment properties where you would normally
     *  expect values to be different but have property names consistent
     * @return Object representing the result of the test
     */
    private ComparisonResult uniquePropertyNames() {

        Set<String> propertyNamesFromPropertyOne = propertyFileOne.stringPropertyNames();
        Set<String> propertyNamesFromTwo = propertyFileTwo.stringPropertyNames();

        boolean propertyOneEmpty = true;
        boolean propertyTwoEmpty = true;

        List<String> uniqueToPropertyFileOne = PropertyUtils.difference(propertyNamesFromPropertyOne, propertyNamesFromTwo);
        List<String> uniqueToPropertyFileTwo = PropertyUtils.difference(propertyNamesFromTwo, propertyNamesFromPropertyOne);

        if (uniqueToPropertyFileOne != null && !uniqueToPropertyFileOne.isEmpty()) {
            propertyOneEmpty = false;
            output.write(Constants.ENTRIES_UNIQUE + fileNameOne);
            for (String name : uniqueToPropertyFileOne) {
                output.write(Constants.PREFIX + name);
            }
        }

        if (uniqueToPropertyFileTwo != null && !uniqueToPropertyFileTwo.isEmpty()) {
            propertyTwoEmpty = false;
            output.write(Constants.ENTRIES_UNIQUE + fileNameTwo);
            for (String name : uniqueToPropertyFileTwo) {
                output.write(Constants.PREFIX + name);
            }
        }

        if (propertyOneEmpty && propertyTwoEmpty) {
            output.write(Constants.NO_DIFFERENCE);
        }

        ComparisonResultBuilder builder = new ComparisonResultBuilder();
        builder.setUniqueToPropertyOne(uniqueToPropertyFileOne).setUniqueToPropertyTwo(uniqueToPropertyFileTwo);
        return builder.build();
    }


}
