package jpropcompare.output;

import jpropcompare.Constants;
import jpropcompare.comparator.ComparisonResult;

import static java.util.AbstractMap.*;
import java.util.List;
import java.util.Map;

/**
 * User: Joe Vartuli
 * Date: 22/10/11
 */
public abstract class StandardOutputFormatter implements Output {

    private String propertyOneName;
    private String propertyTwoName;

    protected StandardOutputFormatter(String propertyOneName, String propertyTwoName) {
        this.propertyOneName = propertyOneName;
        this.propertyTwoName = propertyTwoName;
    }

    /**
     * Output string given to process
     * @param output - String to output
     */
    public abstract void write(String output);

    /**
     * Method that allows the instantiation or destruction of
     * any required or open resources
     */
    public abstract void finalise();

    public final void outputResult(ComparisonResult result) {

        boolean propertyOneEmpty = true;
        boolean propertyTwoEmpty = true;
        boolean propertyDifferencesEmpty = true;

        List<String> uniqueToPropertyFileOne = result.getUniqueToPropertyOne();
        List<String> uniqueToPropertyFileTwo = result.getUniqueToPropertyTwo();
        Map<String, SimpleEntry<String, String>> propertyValueDifferences = result.getSymmetricValueDifferences();

        if (uniqueToPropertyFileOne != null && !uniqueToPropertyFileOne.isEmpty()) {
            propertyOneEmpty = false;
            write(Constants.ENTRIES_UNIQUE + propertyOneName + ":");
            for (String name : uniqueToPropertyFileOne) {
                write(Constants.PREFIX + name);
            }
        }

        if (uniqueToPropertyFileTwo != null && !uniqueToPropertyFileTwo.isEmpty()) {
            propertyTwoEmpty = false;
            write(Constants.ENTRIES_UNIQUE + propertyTwoName  + ":");
            for (String name : uniqueToPropertyFileTwo) {
                write(Constants.PREFIX + name);
            }
        }

        if (propertyValueDifferences != null && !propertyValueDifferences.isEmpty()) {
            propertyDifferencesEmpty = false;
            write(Constants.DIFFERENCE + " " + propertyOneName + " | " + propertyTwoName);
            for (String key : propertyValueDifferences.keySet()) {
                SimpleEntry<String, String> value = propertyValueDifferences.get(key);
                write(Constants.PREFIX + key + ": " + value.getKey() + " | " + value.getValue());
            }
        }

        if (propertyOneEmpty && propertyTwoEmpty && propertyDifferencesEmpty) {
            write(Constants.NO_DIFFERENCE);
        }

        finalise();
    }

}
