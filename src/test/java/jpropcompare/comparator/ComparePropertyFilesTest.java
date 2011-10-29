package jpropcompare.comparator;

import jpropcompare.output.ConsoleOutput;
import jpropcompare.utilities.PropertyUtils;
import org.junit.Test;



/**
 * User: Joe Vartuli
 * Date: 19/09/11
 */
public class ComparePropertyFilesTest {

    private ComparePropertyFile comparePropertyFile;

    private static final String PROPERTY_1 = "property1.properties";
    private static final String PROPERTY_1_COPY = "property1-copy.properties";
    private static final String PROPERTY_2 = "property2.properties";

    public void tearDown() {
        comparePropertyFile = null;
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
