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

    public final void outputResult(ComparisonResult result) {

        boolean propertyOneEmpty = true;
        boolean propertyTwoEmpty = true;
        boolean propertyDifferencesEmpty = true;

        List<String> uniqueToPropertyFileOne = result.getUniqueToPropertyOne();
        List<String> uniqueToPropertyFileTwo = result.getUniqueToPropertyTwo();
        Map<String, SimpleEntry<String, String>> propertyValueDifferences = result.getPropertyValueDifferences();

        if (uniqueToPropertyFileOne != null && !uniqueToPropertyFileOne.isEmpty()) {
            propertyOneEmpty = false;
            write(Constants.ENTRIES_UNIQUE + "one");
            for (String name : uniqueToPropertyFileOne) {
                write(Constants.PREFIX + name);
            }
        }

        if (uniqueToPropertyFileTwo != null && !uniqueToPropertyFileTwo.isEmpty()) {
            propertyTwoEmpty = false;
            write(Constants.ENTRIES_UNIQUE + "two");
            for (String name : uniqueToPropertyFileTwo) {
                write(Constants.PREFIX + name);
            }
        }

        if (propertyValueDifferences != null && !propertyValueDifferences.isEmpty()) {
            propertyDifferencesEmpty = false;
            write(Constants.DIFFERENCE);
            for (String key : propertyValueDifferences.keySet()) {
                SimpleEntry<String, String> value = propertyValueDifferences.get(key);
                write(key + " - " + "one" + ": " + value.getKey() + " <> " + "two" + ": " + value.getValue());
            }
        }

        if (propertyOneEmpty && propertyTwoEmpty && propertyDifferencesEmpty) {
            write(Constants.NO_DIFFERENCE);
        }

        finalise();
    }

}