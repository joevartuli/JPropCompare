package jpropcompare.comparator;

import static jpropcompare.Constants.*;

import jpropcompare.output.ConsoleOutput;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * User: vartulij
 * Date: 19/09/11
 * Time: 11:15 AM
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

        System.out.print(result);
       // assertEquals(DIFFERENCE, output.getResult());

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
