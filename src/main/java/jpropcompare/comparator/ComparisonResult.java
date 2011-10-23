package jpropcompare.comparator;

import static java.util.AbstractMap.*;

import java.util.List;
import java.util.Map;

/**
 * ComparisonResult encapsulates the result of the
 * comparison test into a single object
 * Author: Joe Vartuli
 * Date: 15/10/11
 */
public class ComparisonResult {

    private List<String> uniqueToPropertyOne;
    private List<String> uniqueToPropertyTwo;
    private Map<String, SimpleEntry<String, String>> propertyValueDifferences;

    public ComparisonResult(List<String> uniqueToPropertyOne, List<String> uniqueToPropertyTwo, Map<String, SimpleEntry<String, String>> propertyValueDifferences) {
        this.uniqueToPropertyOne = uniqueToPropertyOne;
        this.uniqueToPropertyTwo = uniqueToPropertyTwo;
        this.propertyValueDifferences = propertyValueDifferences;
    }

    public List<String> getUniqueToPropertyOne() {
        return uniqueToPropertyOne;
    }

    public List<String> getUniqueToPropertyTwo() {
        return uniqueToPropertyTwo;
    }

    public Map<String, SimpleEntry<String, String>> getPropertyValueDifferences() {
        return propertyValueDifferences;
    }

    @Override
    public String toString() {
        return "ComparisonResult{ " +
                "uniqueToPropertyOne=" + uniqueToPropertyOne +
                ", uniqueToPropertyTwo=" + uniqueToPropertyTwo +
                ", propertyValueDifferences=" + propertyValueDifferences +
                " }";
    }
}
