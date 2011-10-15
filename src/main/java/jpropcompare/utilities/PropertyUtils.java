/*
 *  Inspired by the Commons Collections package created by Apache
 *  http://commons.apache.org/collections/
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package jpropcompare.utilities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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



}
