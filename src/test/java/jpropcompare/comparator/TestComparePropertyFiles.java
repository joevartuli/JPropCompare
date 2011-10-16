package jpropcompare.comparator;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * User: Joe Vartuli
 * Date: 19/09/11
 */
public class TestComparePropertyFiles {

    private ComparePropertyFile comparePropertyFile;

    private static final String PROPERTY_1 = "property1.properties";
    private static final String PROPERTY_1_COPY = "property1-copy.properties";
    private static final String PROPERTY_2 = "property2.properties";

    public void tearDown() {
        comparePropertyFile = null;
    }

    @Test
    public void testFileWithNoDifference() {
        StringBuilderOutput output = new StringBuilderOutput();
        comparePropertyFile = new ComparePropertyFile(PROPERTY_1, PROPERTY_1_COPY, Action.UNIQUE_NAMES, output);
        ComparisonResult result = comparePropertyFile.runComparison();

        assertTrue(result.getUniqueToPropertyOne().isEmpty());
        assertTrue(result.getUniqueToPropertyTwo().isEmpty());

        tearDown();
    }

    @Test
    public void testUniqueNameWithDifferences() {
        comparePropertyFile = new ComparePropertyFile(PROPERTY_1, PROPERTY_2, Action.UNIQUE_NAMES, null);
        ComparisonResult result = comparePropertyFile.runComparison();

        List<String> expectedFromOne = Arrays.asList("one.unique.property.1", "two.unique.property.1", "three.unique.property.1", "four.unique.property.1");
        List<String> expectedFromTwo = Arrays.asList("one.unique.property.2", "two.unique.property.2", "three.unique.property.2", "four.unique.property.2");

        assertTrue(ListComparator.isConceptuallyEqual(expectedFromOne, result.getUniqueToPropertyOne()));
        assertTrue(ListComparator.isConceptuallyEqual(expectedFromTwo, result.getUniqueToPropertyTwo()));

        tearDown();
    }

    @Test
    public void testValuesWithDifference() {
        comparePropertyFile = new ComparePropertyFile(PROPERTY_1, PROPERTY_2, Action.COMPARE_VALUES, null);
        ComparisonResult result = comparePropertyFile.runComparison();

        List<String> expectedFromOne = Arrays.asList("one.unique.property.1", "two.unique.property.1", "three.unique.property.1", "four.unique.property.1");
        List<String> expectedFromTwo = Arrays.asList("one.unique.property.2", "two.unique.property.2", "three.unique.property.2", "four.unique.property.2");

        assertTrue(ListComparator.isConceptuallyEqual(expectedFromOne, result.getUniqueToPropertyOne()));
        assertTrue(ListComparator.isConceptuallyEqual(expectedFromTwo, result.getUniqueToPropertyTwo()));

        System.out.println(result);

        tearDown();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProperty1IsNull() {
        comparePropertyFile = new ComparePropertyFile(null, PROPERTY_1_COPY, Action.UNIQUE_NAMES, null);
        tearDown();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProperty1IsEmpty() {
        comparePropertyFile = new ComparePropertyFile("  ", PROPERTY_1_COPY, Action.UNIQUE_NAMES, null);
        tearDown();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProperty2IsNull() {
        comparePropertyFile = new ComparePropertyFile(PROPERTY_1, null, Action.UNIQUE_NAMES, null);
        tearDown();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProperty2IsEmpty() {
        comparePropertyFile = new ComparePropertyFile(PROPERTY_1, "       ", Action.UNIQUE_NAMES, null);
        tearDown();
    }

    @Test(expected = NullPointerException.class)
    public void testProperty1NotFound() {
        comparePropertyFile = new ComparePropertyFile(PROPERTY_1 + "non", PROPERTY_2, Action.UNIQUE_NAMES, null);
        tearDown();
    }

    @Test(expected = NullPointerException.class)
    public void testProperty2NotFound() {
        comparePropertyFile = new ComparePropertyFile(PROPERTY_1, PROPERTY_2 + "non", Action.UNIQUE_NAMES, null);
        tearDown();
    }

}
