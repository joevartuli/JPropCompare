package jpropcompare.utilities;


import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * User: Joe Vartuli
 * Date: 15/10/11
 */
public class TestPropertyUtils {

    @Test
    public void testCombinationOfConceptuallyEqualArrays() {

        List<String> listOne = Arrays.asList("one", "two", "three", "four");
        List<String> listTwo = Arrays.asList("one", "two", "three", "four");
        List<String> listThree = Arrays.asList("four", "two", "three", "one");

        assertTrue(PropertyUtils.isConceptuallyEqual(listOne, listTwo));
        assertTrue(PropertyUtils.isConceptuallyEqual(listOne, listThree));
    }

    @Test
    public void testCombinationOfInEqualArrays() {

        List<String> listOne = Arrays.asList("one", "two", "three", "four");
        List<String> listTwo = Arrays.asList("one", "two", "three");
        List<String> listThree = Arrays.asList("one", "one", "two", "three", "four");
        List<String> listFour = Arrays.asList("one", "two", "three", "four1");

        assertFalse(PropertyUtils.isConceptuallyEqual(listOne, listTwo));
        assertFalse(PropertyUtils.isConceptuallyEqual(listOne, listThree));
        assertFalse(PropertyUtils.isConceptuallyEqual(listOne, listFour));
    }
}
