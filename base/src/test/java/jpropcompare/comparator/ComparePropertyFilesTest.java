package jpropcompare.comparator;

import jpropcompare.loading.strategy.LoadingStrategy;
import jpropcompare.output.StringOutput;
import static org.easymock.EasyMock.*;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import static org.junit.Assert.*;

/**
 * User: Joe Vartuli
 * Date: 19/09/11
 */
public class ComparePropertyFilesTest {

    private ComparePropertyFile comparePropertyFile;

    private static File PROPERTY_1_FILE;
    private static File PROPERTY_1_COPY_FILE;
    private static File PROPERTY_2_FILE;
    private static Properties PROPERTY_1;
    private static Properties PROPERTY_1_COPY;
    private static Properties PROPERTY_2;

    @Before
    public void setUp() throws Exception {
        PROPERTY_1_FILE = new File("./src/test/resources/property1.properties");
        PROPERTY_1_COPY_FILE = new File("./src/test/resources/property1-copy.properties");
        PROPERTY_2_FILE = new File("./src/test/resources/property2.properties");


        FileReader fileReaderOne = new FileReader(PROPERTY_1_FILE);
        FileReader fileReaderTwo = new FileReader(PROPERTY_2_FILE);

        PROPERTY_1 = new Properties();
        PROPERTY_1.load(fileReaderOne);

        PROPERTY_2 = new Properties();
        PROPERTY_2.load(fileReaderTwo);

    }

    public void tearDown() {
        comparePropertyFile = null;
    }

    @Test
    public void testRun() {
        StringOutput stringOutput = new StringOutput(PROPERTY_1_FILE.getName(), PROPERTY_2_FILE.getName());
        comparePropertyFile = new ComparePropertyFile(PROPERTY_1_FILE, PROPERTY_2_FILE, Action.SYMMETRIC_DIFFERENCE_IN_VALUE, stringOutput);
        comparePropertyFile.runVerboseComparison();
        //System.out.println(stringOutput.result());
        assertFalse(stringOutput.result().isEmpty());
        tearDown();
    }

    @Test
    public void testRunWithLoadingStrategy() {
        StringOutput stringOutput = new StringOutput(PROPERTY_1_FILE.getName(), PROPERTY_1_COPY_FILE.getName());
        LoadingStrategy loadingStrategy = createMock(LoadingStrategy.class);

        expect(loadingStrategy.getPropertyFileOne()).andReturn(PROPERTY_1);
        expect(loadingStrategy.getPropertyFileTwo()).andReturn(PROPERTY_2);


        replay(loadingStrategy);

        comparePropertyFile = new ComparePropertyFile(loadingStrategy, Action.SYMMETRIC_DIFFERENCE_IN_NAME, stringOutput);
        comparePropertyFile.runVerboseComparison();
        assertFalse(stringOutput.result().isEmpty());
        tearDown();
    }

    @Test(expected = NullPointerException.class)
    public void testProperty1IsNull() {
        comparePropertyFile = new ComparePropertyFile(null, PROPERTY_1_COPY_FILE, Action.SYMMETRIC_DIFFERENCE_IN_NAME, null);
        comparePropertyFile.runComparison();
        tearDown();
    }

    @Test(expected = NullPointerException.class)
    public void testProperty2IsNull() {
        comparePropertyFile = new ComparePropertyFile(PROPERTY_1_FILE, null, Action.SYMMETRIC_DIFFERENCE_IN_NAME, null);
        comparePropertyFile.runComparison();
        tearDown();
    }

}
