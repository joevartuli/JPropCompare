package jpropcompare.comparator;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import static java.util.AbstractMap.*;

/**
 * Author: Joe Vartuli
 * Date: 15/10/11
 */
public class ComparisonResultBuilder {

    private List<String> uniqueToPropertyOne;
    private List<String> uniqueToPropertyTwo;
    private Map<String, SimpleEntry<String, String>> propertyValueDifferences;

    public ComparisonResultBuilder() {

    }

    public ComparisonResultBuilder(ComparisonResult result) {
        this.uniqueToPropertyOne = result.getUniqueToPropertyOne();
        this.uniqueToPropertyTwo = result.getUniqueToPropertyTwo();
        this.propertyValueDifferences = result.getPropertyValueDifferences();
    }


    public ComparisonResult build() {
        return new ComparisonResult(uniqueToPropertyOne, uniqueToPropertyTwo, propertyValueDifferences);
    }


    public ComparisonResultBuilder setUniqueToPropertyOne(List<String> uniqueToPropertyOne) {
        this.uniqueToPropertyOne = uniqueToPropertyOne;
        return this;
    }

    public ComparisonResultBuilder setUniqueToPropertyTwo(List<String> uniqueToPropertyTwo) {
        this.uniqueToPropertyTwo = uniqueToPropertyTwo;
        return this;
    }

    public ComparisonResultBuilder setPropertyValueDifferences(Map<String, SimpleEntry<String, String>> propertyValueDifferences) {
        this.propertyValueDifferences = propertyValueDifferences;
        return this;
    }
}
