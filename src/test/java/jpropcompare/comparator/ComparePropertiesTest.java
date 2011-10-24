package jpropcompare.comparator;

import jpropcompare.utilities.PropertyUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.*;
import static java.util.AbstractMap.*;

import static org.junit.Assert.*;

/**
 * User: Joe Vartuli
 * Date: 24/10/11
 */
public class ComparePropertiesTest {

    private CompareProperties compareProperties;

    private Properties propertyOne;
    private Properties copyOfPropertyOne;
    private Properties propertyTwo;

    @Before
    public void setUp() throws IOException {
        propertyOne = new Properties();
        propertyOne.load(this.getClass().getClassLoader().getResourceAsStream("property1.properties"));

        copyOfPropertyOne = new Properties();
        copyOfPropertyOne.load(this.getClass().getClassLoader().getResourceAsStream("property1-copy.properties"));

        propertyTwo = new Properties();
        propertyTwo.load(this.getClass().getClassLoader().getResourceAsStream("property2.properties"));
    }

    public void tearDown() {
        compareProperties = null;
    }

    @Test
    public void testFileWithNoDifference() {
        compareProperties = new CompareProperties(propertyOne, copyOfPropertyOne, Action.UNIQUE_NAMES);
        ComparisonResult result = compareProperties.runComparison();

        assertTrue(result.getUniqueToPropertyOne().isEmpty());
        assertTrue(result.getUniqueToPropertyTwo().isEmpty());

        tearDown();
    }

    @Test
    public void testUniqueNameWithDifferences() {
        compareProperties = new CompareProperties(propertyOne, propertyTwo, Action.UNIQUE_NAMES);
        ComparisonResult result = compareProperties.runComparison();

        List<String> expectedFromOne = Arrays.asList("one.unique.property.1", "two.unique.property.1", "three.unique.property.1", "four.unique.property.1");
        List<String> expectedFromTwo = Arrays.asList("one.unique.property.2", "two.unique.property.2", "three.unique.property.2", "four.unique.property.2");

        assertTrue(PropertyUtils.isConceptuallyEqual(expectedFromOne, result.getUniqueToPropertyOne()));
        assertTrue(PropertyUtils.isConceptuallyEqual(expectedFromTwo, result.getUniqueToPropertyTwo()));

        tearDown();
    }

    @Test
    public void testValuesWithDifference() {
        compareProperties = new CompareProperties(propertyOne, propertyTwo, Action.COMPARE_VALUES);
        ComparisonResult result = compareProperties.runComparison();

        List<String> expectedFromOne = Arrays.asList("one.unique.property.1", "two.unique.property.1", "three.unique.property.1", "four.unique.property.1");
        List<String> expectedFromTwo = Arrays.asList("one.unique.property.2", "two.unique.property.2", "three.unique.property.2", "four.unique.property.2");

        Map<String, SimpleEntry<String, String>> expectedDifferences = new HashMap<String, SimpleEntry<String, String>>();
        expectedDifferences.put("common.1.diffValue", new SimpleEntry<String, String>("file1", "file2"));
        expectedDifferences.put("common.2.diffValue", new SimpleEntry<String, String>("file1", "file2"));

        assertTrue(PropertyUtils.isConceptuallyEqual(expectedFromOne, result.getUniqueToPropertyOne()));
        assertTrue(PropertyUtils.isConceptuallyEqual(expectedFromTwo, result.getUniqueToPropertyTwo()));

        assertTrue(PropertyUtils.isConceptuallyEqual(expectedDifferences, result.getPropertyValueDifferences()));

        tearDown();
    }

}
