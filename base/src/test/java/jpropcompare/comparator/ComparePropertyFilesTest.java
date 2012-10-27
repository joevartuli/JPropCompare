package jpropcompare.comparator;

import jpropcompare.loading.strategy.LoadingStrategy;
import jpropcompare.output.StringOutput;

import static jpropcompare.comparator.Action.SYMMETRIC_DIFFERENCE_IN_NAME;
import static org.easymock.EasyMock.*;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.util.Properties;

import static org.junit.Assert.*;

/**
 * User: Joe Vartuli
 * Date: 19/09/11
 */
public class ComparePropertyFilesTest {

    private static final String PROPERTY1_PROPERTIES = "./src/test/resources/property1.properties";
    private static final String PROPERTY1_COPY_PROPERTIES = "./src/test/resources/property1-copy.properties";
    private static final String PROPERTY2_PROPERTIES = "./src/test/resources/property2.properties";

    private static FileReader PROPERTY_1_FILE;
    private static FileReader PROPERTY_1_COPY_FILE;
    private static FileReader PROPERTY_2_FILE;
    private static Properties PROPERTY_1;
    private static Properties PROPERTY_1_COPY;
    private static Properties PROPERTY_2;

    private CompareProperties compareProperties;

    @Before
    public void setUp() throws Exception {
        PROPERTY_1_FILE = new FileReader(PROPERTY1_PROPERTIES);
        PROPERTY_1_COPY_FILE = new FileReader(PROPERTY1_COPY_PROPERTIES);
        PROPERTY_2_FILE = new FileReader(PROPERTY2_PROPERTIES);

        PROPERTY_1 = new Properties();
        PROPERTY_1.load(PROPERTY_1_FILE);

        PROPERTY_1_COPY = new Properties();
        PROPERTY_1_COPY.load(PROPERTY_1_COPY_FILE);

        PROPERTY_2 = new Properties();
        PROPERTY_2.load(PROPERTY_2_FILE);

    }

    public void tearDown() {
        compareProperties = null;
    }

    @Test
    public void testRun() {
        StringOutput stringOutput = new StringOutput(PROPERTY1_PROPERTIES, PROPERTY2_PROPERTIES);
        compareProperties = new CompareProperties(PROPERTY_1_FILE, PROPERTY_2_FILE, Action.SYMMETRIC_DIFFERENCE_IN_VALUE, stringOutput);
        compareProperties.runComparison();
        assertFalse(stringOutput.result().isEmpty());
        tearDown();
    }

    @Test
    public void testRunWithLoadingStrategy() {
        StringOutput stringOutput = new StringOutput(PROPERTY1_PROPERTIES, PROPERTY1_COPY_PROPERTIES);
        LoadingStrategy loadingStrategy = createMock(LoadingStrategy.class);

        expect(loadingStrategy.getPropertyFileOne()).andReturn(PROPERTY_1);
        expect(loadingStrategy.getPropertyFileTwo()).andReturn(PROPERTY_2);


        replay(loadingStrategy);

        compareProperties = new CompareProperties(loadingStrategy, SYMMETRIC_DIFFERENCE_IN_NAME, stringOutput);
        compareProperties.runComparison();
        assertFalse(stringOutput.result().isEmpty());
        tearDown();
    }

    @Test(expected = NullPointerException.class)
    public void testProperty1IsNull() {
        compareProperties = new CompareProperties(null, PROPERTY_1_COPY_FILE, SYMMETRIC_DIFFERENCE_IN_NAME, null);
        compareProperties.runComparison();
        tearDown();
    }

    @Test(expected = NullPointerException.class)
    public void testProperty2IsNull() {
        compareProperties = new CompareProperties(PROPERTY_1_FILE, null, SYMMETRIC_DIFFERENCE_IN_NAME, null);
        compareProperties.runComparison();
        tearDown();
    }

}
