package jpropcompare.comparator;

import jpropcompare.utilities.PropertyUtils;

import java.util.*;
import static java.util.AbstractMap.*;

/**
 * User: Joe Vartuli
 * Date: 24/10/11
 */
public class CompareProperties {

    private Properties first;
    private Properties second;
    private Action action;
    private ComparisonResultBuilder comparisonResultBuilder;

    protected CompareProperties(Properties first, Properties second, Action action) {
        this.action = action;
        this.first = first;
        this.second = second;
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

        Set<String> propertyNamesFromFirst = first.stringPropertyNames();
        Set<String> propertyNamesFromSecond = second.stringPropertyNames();

        List<String> uniqueToFirst = PropertyUtils.getSymmetricDifference(propertyNamesFromFirst, propertyNamesFromSecond);
        List<String> uniqueToSecond = PropertyUtils.getSymmetricDifference(propertyNamesFromSecond, propertyNamesFromFirst);

        comparisonResultBuilder.setUniqueToPropertyOne(uniqueToFirst).setUniqueToPropertyTwo(uniqueToSecond);
    }

    /**
     * In addition to finding unique property names in each file, compare property values
     * will also render the property names where the values are different from the first
     * and second property file.
     */
    private void findSymmetricDifferenceInPropertyValues() {

        findSymmetricDifferenceInPropertyNames();

        Map<String, SimpleEntry<String, String>> differences = PropertyUtils.getSymmetricPropertyValueDifference(first, second);

        comparisonResultBuilder.setSymmetricDifferencePropertyValues(differences);
    }


    private void findIntersectionInPropertyValues() {

        Map<String, String> intersection = PropertyUtils.getIntersectionOfPropertyValues(first, second);

        comparisonResultBuilder.setIntersectionPropertyValues(intersection);
    }

}
