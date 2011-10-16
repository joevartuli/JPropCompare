/*
 *  Inspired by the Commons Collections package created by Apache
 *  http://commons.apache.org/collections/
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package jpropcompare.utilities;


import java.util.*;

import static java.util.AbstractMap.*;

/**
 * Collection of utilities to assist in comparing property files.
 * Author: Joe Vartuli
 * Date: 19/09/11
 */
public class PropertyUtils {

    /**
     * Finds all unique property names that are in the first set, but not in the second
     * @param firstCollection - Set of property names
     * @param secondCollection - Set of property names
     * @return - unique property names that are present in the first set but not in the second
     */
    public static List<String> difference(final Set<String> firstCollection, final Set<String> secondCollection) {
        List<String> difference = new ArrayList<String>(firstCollection);
        for (String value : secondCollection) {
            difference.remove(value);
        }
        return difference;
    }


    public static Map<String, SimpleEntry<String, String>> propertyValueDifferences(Properties propertyFileOne, Properties propertyFileTwo) {
        Map<String, SimpleEntry<String, String>>  differences = new HashMap<String, SimpleEntry<String, String>>();

        for (String key : propertyFileOne.stringPropertyNames()) {
            String propertyOneValue = propertyFileOne.getProperty(key);
            String propertyTwoValue = propertyFileTwo.getProperty(key);
            if (propertyTwoValue != null && !propertyOneValue.equals(propertyTwoValue)) {
                differences.put(key, new SimpleEntry(propertyOneValue, propertyTwoValue));
            }
        }

        return differences;
    }



}
