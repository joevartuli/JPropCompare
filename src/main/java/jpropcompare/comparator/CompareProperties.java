package jpropcompare.comparator;

import jpropcompare.utilities.PropertyUtils;

import java.util.*;
import static java.util.AbstractMap.*;

/**
 * User: Joe Vartuli
 * Date: 24/10/11
 */
public class CompareProperties {

    private Properties propertyFileOne;
    private Properties propertyFileTwo;
    private Action action;
    private ComparisonResultBuilder comparisonResultBuilder;

    protected CompareProperties(Properties propertiesOne, Properties propertiesTwo, Action action) {
        this.action = action;
        this.propertyFileOne = propertiesOne;
        this.propertyFileTwo = propertiesTwo;
        this.comparisonResultBuilder = new ComparisonResultBuilder();
    }

    /**
     * Executes the specific action over the given property files
     *
     * @return Object representing the result of the comparison
     */
     ComparisonResult runComparison() {
        ComparisonResult result;

        switch (action) {
            case SYMMETRIC_DIFFERENCE_IN_NAME:
                findSymmetricDifferenceInPropertyNames();
                break;
            case SYMMETRIC_DIFFERENCE_IN_VALUE:
                findSymmetricDifferenceInPropertyValues();
                break;
            case INTERSECTION_OF_VALUES:
                findIntersectionInPropertyValues();
                break;
            case UNION_IN_NAME:
                break;
            case UNION_IN_VALUE:
                break;
        }

        result = comparisonResultBuilder.build();

        return result;
    }

    /**
     * Finds the unique property names in each file regardless of their value. Useful
     * for determining property differences between environment properties where you would normally
     * expect values to be different but have property names consistent
     */
    private void findSymmetricDifferenceInPropertyNames() {

        Set<String> propertyNamesFromOne = propertyFileOne.stringPropertyNames();
        Set<String> propertyNamesFromTwo = propertyFileTwo.stringPropertyNames();

        List<String> uniqueToPropertyFileOne = PropertyUtils.getSymmetricDifference(propertyNamesFromOne, propertyNamesFromTwo);
        List<String> uniqueToPropertyFileTwo = PropertyUtils.getSymmetricDifference(propertyNamesFromTwo, propertyNamesFromOne);

        comparisonResultBuilder.setUniqueToPropertyOne(uniqueToPropertyFileOne).setUniqueToPropertyTwo(uniqueToPropertyFileTwo);
    }

    /**
     * In addition to finding unique property names in each file, compare property values
     * will also render the property names where the values are different from the first
     * and second property file.
     */
    private void findSymmetricDifferenceInPropertyValues() {

        findSymmetricDifferenceInPropertyNames();

        Map<String, SimpleEntry<String, String>> differences = PropertyUtils.getSymmetricPropertyValueDifference(propertyFileOne, propertyFileTwo);

        comparisonResultBuilder.setSymmetricDifferencePropertyValues(differences);
    }


    private void findIntersectionInPropertyValues() {

        Map<String, String> intersection = PropertyUtils.getIntersectionOfPropertyValues(propertyFileOne, propertyFileTwo);

        comparisonResultBuilder.setIntersectionPropertyValues(intersection);
    }

}
