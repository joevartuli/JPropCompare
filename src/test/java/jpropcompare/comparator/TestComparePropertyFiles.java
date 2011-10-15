package jpropcompare.comparator;

import jpropcompare.output.ConsoleOutput;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;
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
    public void testNoDifferenceFile() {
        StringBuilderOutput output = new StringBuilderOutput();
        comparePropertyFile = new ComparePropertyFile(PROPERTY_1, PROPERTY_1_COPY, Action.UNIQUE_NAMES, output);
        ComparisonResult result = comparePropertyFile.execute();

        assertTrue(result.getUniqueToPropertyOne().isEmpty());
        assertTrue(result.getUniqueToPropertyTwo().isEmpty());

        tearDown();
    }

    @Test
    public void testDifference() {
        StringBuilderOutput output = new StringBuilderOutput();
        comparePropertyFile = new ComparePropertyFile(PROPERTY_1, PROPERTY_2, Action.UNIQUE_NAMES, output);
        ComparisonResult result = comparePropertyFile.execute();

        List<String> expectedFromOne = Arrays.asList("one.unique.property.1", "two.unique.property.1", "three.unique.property.1", "four.unique.property.1");
        List<String> expectedFromTwo = Arrays.asList("one.unique.property.2", "two.unique.property.2", "three.unique.property.2", "four.unique.property.2");

        assertTrue(ListComparator.isConceptuallyEqual(expectedFromOne, result.getUniqueToPropertyOne()));
        assertTrue(ListComparator.isConceptuallyEqual(expectedFromTwo, result.getUniqueToPropertyTwo()));

        tearDown();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProperty1IsNull() {
        comparePropertyFile = new ComparePropertyFile(null, PROPERTY_1_COPY, Action.UNIQUE_NAMES, new ConsoleOutput());
        tearDown();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProperty1IsEmpty() {
        comparePropertyFile = new ComparePropertyFile("  ", PROPERTY_1_COPY, Action.UNIQUE_NAMES, new ConsoleOutput());
        tearDown();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProperty2IsNull() {
        comparePropertyFile = new ComparePropertyFile(PROPERTY_1, null, Action.UNIQUE_NAMES, new ConsoleOutput());
        tearDown();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProperty2IsEmpty() {
        comparePropertyFile = new ComparePropertyFile(PROPERTY_1, "       ", Action.UNIQUE_NAMES, new ConsoleOutput());
        tearDown();
    }

    @Test(expected = NullPointerException.class)
    public void testProperty1NotFound() {
        comparePropertyFile = new ComparePropertyFile(PROPERTY_1 + "non", PROPERTY_2, Action.UNIQUE_NAMES, new ConsoleOutput());
        tearDown();
    }

    @Test(expected = NullPointerException.class)
    public void testProperty2NotFound() {
        comparePropertyFile = new ComparePropertyFile(PROPERTY_1, PROPERTY_2 + "non", Action.UNIQUE_NAMES, new ConsoleOutput());
        tearDown();
    }



}
