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
 *  Collection of utilities to assist in comparing property files.
 * @author: Joe Vartuli
 * @date: 19/09/11
 */
public class PropertyUtils {

    /**
     * Finds all unique property names that are in the first set, but not in the second
     * @param first - Set of property names
     * @param second - Set of property names
     * @return - unique property names that are present in the first set but not in the second
     */
    public static List difference(final Set<String> first, final Set<String> second) {
        List difference = new ArrayList(first);
        for (Iterator iterator = second.iterator(); iterator.hasNext();) {
            difference.remove(iterator.next());
        }
        return difference;
    }



}
