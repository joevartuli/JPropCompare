package jpropcompare.comparator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.AbstractMap.SimpleEntry;

/**
 * Builder class used to construct the comparison result
 * Author: Joe Vartuli
 * Date: 15/10/11
 */
public class ComparisonResultBuilder {

    private List<String> uniqueToPropertyOne;
    private List<String> uniqueToPropertyTwo;
    private Map<String, SimpleEntry<String, String>> symmetricDifferencePropertyValues;
    private Map<String, String> intersectionPropertyValues;

    public ComparisonResultBuilder() {
        this.uniqueToPropertyOne = new ArrayList<String>();
        this.uniqueToPropertyTwo = new ArrayList<String>();
        this.symmetricDifferencePropertyValues = new HashMap<String, SimpleEntry<String, String>>();
        this.intersectionPropertyValues = new HashMap<String, String>();
    }

    public ComparisonResult build() {
        return new ComparisonResult(uniqueToPropertyOne, uniqueToPropertyTwo, symmetricDifferencePropertyValues, intersectionPropertyValues);
    }

    public ComparisonResultBuilder setUniqueToPropertyOne(List<String> uniqueToPropertyOne) {
        this.uniqueToPropertyOne = uniqueToPropertyOne;
        return this;
    }

    public ComparisonResultBuilder setUniqueToPropertyTwo(List<String> uniqueToPropertyTwo) {
        this.uniqueToPropertyTwo = uniqueToPropertyTwo;
        return this;
    }

    public ComparisonResultBuilder setSymmetricDifferencePropertyValues(Map<String, SimpleEntry<String, String>> symmetricDifferencePropertyValues) {
        this.symmetricDifferencePropertyValues = symmetricDifferencePropertyValues;
        return this;
    }

    public ComparisonResultBuilder setIntersectionPropertyValues(Map<String, String> intersectionPropertyValues) {
        this.intersectionPropertyValues = intersectionPropertyValues;
        return this;
    }
}
