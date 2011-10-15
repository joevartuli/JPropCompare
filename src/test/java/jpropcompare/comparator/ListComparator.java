package jpropcompare.comparator;

import java.util.List;

/**
 * User: Joe Vartuli
 * Date: 15/10/11
 */
public class ListComparator {

    public static boolean isConceptuallyEqual(List<String> listOne, List<String> listTwo) {
        boolean equal = false;

        if (listOne.size() == listTwo.size()) {
                if (listTwo.containsAll(listOne)) {
                    equal = true;
                }
        }

        return equal;
    }
}
