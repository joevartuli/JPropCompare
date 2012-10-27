package jpropcompare.comparator;

import jpropcompare.utilities.PropertyUtils;

import java.util.*;
import static java.util.AbstractMap.*;

/**
 * The ComparisonRunner class allows two properties files to be compared with the supplied action
 * This class is for internal use only. See {@link CompareProperties} for external use
 * User: Joe Vartuli
 * Date: 24/10/11
 */
public class ComparisonRunner {

    private Properties a;
    private Properties b;
    private Action action;
    private ComparisonResultBuilder comparisonResultBuilder;

    protected ComparisonRunner(Properties a, Properties b, Action action) {
        this.action = action;
        this.a = a;
        this.b = b;
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

        Set<String> propertyNamesFromFirst = a.stringPropertyNames();
        Set<String> propertyNamesFromSecond = b.stringPropertyNames();

        List<String> uniqueToFirst = PropertyUtils.getSymmetricDifference(propertyNamesFromFirst, propertyNamesFromSecond);
        List<String> uniqueToSecond = PropertyUtils.getSymmetricDifference(propertyNamesFromSecond, propertyNamesFromFirst);

        comparisonResultBuilder.setUniqueToPropertyOne(uniqueToFirst).setUniqueToPropertyTwo(uniqueToSecond);
    }

    /**
     * In addition to finding unique property names in each file, compare property values
     * will also render the property names where the values are different from the a
     * and b property file.
     */
    private void findSymmetricDifferenceInPropertyValues() {

        findSymmetricDifferenceInPropertyNames();

        Map<String, SimpleEntry<String, String>> differences = PropertyUtils.getSymmetricPropertyValueDifference(a, b);

        comparisonResultBuilder.setSymmetricDifferencePropertyValues(differences);
    }


    private void findIntersectionInPropertyValues() {

        Map<String, String> intersection = PropertyUtils.getIntersectionOfPropertyValues(a, b);

        comparisonResultBuilder.setIntersectionPropertyValues(intersection);
    }

}
