package jpropcompare.comparator;

import jpropcompare.loading.strategy.LoadingStrategy;
import jpropcompare.output.Output;

import java.io.*;
import java.util.Properties;

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

    /**
     * Initialises this class with a loading strategy, action to perform and an output class
     * @param strategy - loading strategy used to create the custom property files required
     * @param action - action to perform on the property files
     * @param output - where to output the results
     */
    public ComparePropertyFile(LoadingStrategy strategy, Action action, Output output) {
        this(strategy.getPropertyFileOne(), strategy.getPropertyFileTwo(), action, output);
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

        InputStream inputStreamOne = null;
        InputStream inputStreamTwo = null;

        try {
            inputStreamOne = this.getClass().getClassLoader().getResourceAsStream(fileNameOne);
            propertyFileOne = new Properties();
            propertyFileOne.load(inputStreamOne);
        } catch (NullPointerException e) {
            throw new NullPointerException("Could not find property file with name " + fileNameOne);
        } catch (IOException e) {
            throw new IllegalArgumentException("An exception occurred when loading property file with name " + fileNameOne, e);
        } finally {
            if (inputStreamOne != null) {
                try {
                    inputStreamOne.close();
                } catch (IOException e) {
                    //
                }
            }
        }

        try {
            inputStreamTwo = this.getClass().getClassLoader().getResourceAsStream(fileNameTwo);
            propertyFileTwo = new Properties();
            propertyFileTwo.load(inputStreamTwo);
        } catch (NullPointerException e) {
            throw new NullPointerException("Could not find property file with name " + fileNameTwo);
        }  catch (IOException e) {
            throw new IllegalArgumentException("An exception occurred when loading file with name " + fileNameTwo, e);
        } finally {
            if (inputStreamTwo != null) {
                try {
                    inputStreamTwo.close();
                } catch (IOException e) {
                    //
                }
            }
        }

    }

    /**
     * Initialises this class with a the two files of the property file, action to perform and an output class
     * @param fileOne - file representing the first property file used in the comparison.
     * @param fileTwo - file representing the second property file used in the comparison.
     * @param action - action to perform on the property files
     * @param output - where to output the results
     */
    public ComparePropertyFile(File fileOne, File fileTwo, Action action, Output output) {
        this((Properties)null, (Properties)null, action, output);

        try {
            FileReader fileReaderOne = new FileReader(fileOne);
            FileReader fileReaderTwo = new FileReader(fileTwo);

            propertyFileOne = new Properties();
            propertyFileOne.load(fileReaderOne);

            propertyFileTwo = new Properties();
            propertyFileTwo.load(fileReaderTwo);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Could not find file", e);
        } catch (IOException e) {
            throw new IllegalArgumentException("An exception occurred when loading file", e);
        }

    }

    /**
     *
     * Initialises this class with a the two property files, the action to perform and an output class
     * @param propertiesOne - first property file used in the comparison
     * @param propertiesTwo - second property file used in the comparison
     * @param action - action to perform on the property files
     * @param output - where to output the results
     */
    public ComparePropertyFile(Properties propertiesOne, Properties propertiesTwo, Action action, Output output) {
        this.action = action;
        this.output = output;
        this.propertyFileOne = propertiesOne;
        this.propertyFileTwo = propertiesTwo;
    }

    /**
     * Executes the specific action over the given property files
     * @return Object representing the result of the comparison
     */
    public ComparisonResult runComparison() {
        return comparePropertyFiles(false);
    }

    /**
     * Executes the specific action over the given property files
     * using an output to display the results
     */
    public void runVerboseComparison() {
        comparePropertyFiles(true);
    }

    /**
     * Runs the actions on the property files
     * @param verbose - determines whether or not the result should be outputted
     * @return result of the comparison
     */
    private ComparisonResult comparePropertyFiles(boolean verbose) {
        CompareProperties compareProperties = new CompareProperties(propertyFileOne, propertyFileTwo, action);

        ComparisonResult comparisonResult = compareProperties.runComparison();

        if (verbose) {
            output.outputResult(comparisonResult);
        }
        return comparisonResult;
    }

}
