package jpropcompare.comparator;

import jpropcompare.Constants;
import jpropcompare.loading.strategy.LoadingStrategy;
import jpropcompare.output.Output;
import jpropcompare.utilities.PropertyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Properties;
import java.util.Set;

/**
 * ComparePropertyFile is the main class that provides comparison
 * functionality.
 *
 * @author: Joe Vartuli
 * @date: 19/09/11
 */
public class ComparePropertyFile {

    private Properties propertyFile1;
    private Properties propertyFile2;
    private String fileName1;
    private String fileName2;
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
        this.propertyFile1 = strategy.getPropertyFile1();
        this.fileName1 = strategy.getPropertyName1();
        this.propertyFile2 = strategy.getPropertyFile2();
        this.fileName2 = strategy.getPropertyName2();
    }

    /**
     * Initialises this class with a the two file names of the property file, action to perform and an output class
     * @param fileName1 - file name representing the first property file used in the comparison. Needs to be on the classpath
     * @param fileName2 - file name representing the second property file used in the comparison. Needs to be on the classpath
     * @param action - action to perform on the property files
     * @param output - where to output the results
     */
    public ComparePropertyFile(String fileName1, String fileName2, Action action, Output output) {

        if (fileName1 == null || fileName1.trim().equals("")) {
            throw new IllegalArgumentException("fileName1 can not be null or empty");
        }

        if (fileName2 == null || fileName2.trim().equals("")) {
            throw new IllegalArgumentException("fileName2 can not be null or empty");
        }

        this.action = action;
        this.output = output;
        this.fileName1 = fileName1;
        this.fileName2 = fileName2;


        try {
            InputStream inputStream1 = this.getClass().getClassLoader().getResourceAsStream(fileName1);
            propertyFile1 = new Properties();
            propertyFile1.load(inputStream1);
        } catch (NullPointerException e) {
            throw new NullPointerException("Could not find property file with name " + fileName1);
        } catch (IOException e) {
            throw new IllegalArgumentException("An exception occurred when loading property file with name " + fileName1, e);
        }

        try {
            InputStream inputStream2 = this.getClass().getClassLoader().getResourceAsStream(fileName2);
            propertyFile2 = new Properties();
            propertyFile2.load(inputStream2);
        } catch (NullPointerException e) {
            throw new NullPointerException("Could not find property file with name " + fileName1);
        }  catch (IOException e) {
            throw new IllegalArgumentException("An exception occurred when loading file with name " + fileName2, e);
        }

    }

    /**
     *  Executes the specific action over the given property files
     */
    public void execute() {
        switch (action) {
            case UNIQUE_NAMES:
                uniquePropertyNames();
                break;
        }
        output.finalise();
    }

    /**
     *  Finds the unique property names in each file regardless of their value. Useful
     *  for determining property differences between environment properties where you would normally
     *  expect values to be different but have property names consistent
     */
    private void uniquePropertyNames() {

        Set<String> propertyNamesFrom1 = propertyFile1.stringPropertyNames();
        Set<String> propertyNamesFrom2 = propertyFile2.stringPropertyNames();

        boolean property1Empty = true;
        boolean property2Empty = true;

        Collection<String> uniqueToProperty1 = PropertyUtils.difference(propertyNamesFrom1, propertyNamesFrom2);
        Collection<String> uniqueToProperty2 = PropertyUtils.difference(propertyNamesFrom2, propertyNamesFrom1);

        if (uniqueToProperty1 != null && !uniqueToProperty1.isEmpty()) {
            property1Empty = false;
            output.write(Constants.ENTRIES_UNIQUE + fileName1);
            for (String name : uniqueToProperty1) {
                output.write(Constants.PREFIX + name);
            }
        }

        if (uniqueToProperty2 != null && !uniqueToProperty2.isEmpty()) {
            property2Empty = false;
            output.write(Constants.ENTRIES_UNIQUE + fileName2);
            for (String name : uniqueToProperty2) {
                output.write(Constants.PREFIX + name);
            }
        }

        if (property1Empty && property2Empty) {
            output.write(Constants.NO_DIFFERENCE);
        }

    }


}
