package jpropcompare.comparator;

import jpropcompare.loading.strategy.LoadingStrategy;
import jpropcompare.loading.strategy.LoadingStrategyMock;
import jpropcompare.output.StringOutput;
import org.junit.Test;
import static org.junit.Assert.*;

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

    @Test
    public void testRun() {
        StringOutput stringOutput = new StringOutput(PROPERTY_1, PROPERTY_2);
        comparePropertyFile = new ComparePropertyFile(PROPERTY_1, PROPERTY_2, Action.SYMMETRIC_DIFFERENCE_IN_VALUE, stringOutput);
        comparePropertyFile.runVerboseComparison();
        //System.out.println(stringOutput.result());
        assertFalse(stringOutput.result().isEmpty());
        tearDown();
    }

    @Test
    public void testRunWithLoadingStrategy() {
        StringOutput stringOutput = new StringOutput(PROPERTY_1, PROPERTY_1_COPY);
        LoadingStrategy loadingStrategy = new LoadingStrategyMock();
        loadingStrategy.initialise(PROPERTY_1, PROPERTY_1_COPY, new String[0]);
        comparePropertyFile = new ComparePropertyFile(loadingStrategy, Action.SYMMETRIC_DIFFERENCE_IN_NAME, stringOutput);
        comparePropertyFile.runVerboseComparison();
        assertFalse(stringOutput.result().isEmpty());
        tearDown();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProperty1IsNull() {
        comparePropertyFile = new ComparePropertyFile(null, PROPERTY_1_COPY, Action.SYMMETRIC_DIFFERENCE_IN_NAME, null);
        comparePropertyFile.runComparison();
        tearDown();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProperty1IsEmpty() {
        comparePropertyFile = new ComparePropertyFile("  ", PROPERTY_1_COPY, Action.SYMMETRIC_DIFFERENCE_IN_NAME, null);
        comparePropertyFile.runComparison();
        tearDown();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProperty2IsNull() {
        comparePropertyFile = new ComparePropertyFile(PROPERTY_1, null, Action.SYMMETRIC_DIFFERENCE_IN_NAME, null);
        comparePropertyFile.runComparison();
        tearDown();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProperty2IsEmpty() {
        comparePropertyFile = new ComparePropertyFile(PROPERTY_1, "       ", Action.SYMMETRIC_DIFFERENCE_IN_NAME, null);
        comparePropertyFile.runComparison();
        tearDown();
    }

    @Test(expected = NullPointerException.class)
    public void testProperty1NotFound() {
        comparePropertyFile = new ComparePropertyFile(PROPERTY_1 + "non", PROPERTY_2, Action.SYMMETRIC_DIFFERENCE_IN_NAME, null);
        comparePropertyFile.runComparison();
        tearDown();
    }

    @Test(expected = NullPointerException.class)
    public void testProperty2NotFound() {
        comparePropertyFile = new ComparePropertyFile(PROPERTY_1, PROPERTY_2 + "non", Action.SYMMETRIC_DIFFERENCE_IN_NAME, null);
        comparePropertyFile.runComparison();
        tearDown();
    }

}
