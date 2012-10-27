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
public class ComparisonRunnerTest {

    private ComparisonRunner comparisonRunner;

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
        comparisonRunner = null;
    }

    @Test
    public void testFileWithNoDifference() {
        comparisonRunner = new ComparisonRunner(propertyOne, copyOfPropertyOne, Action.SYMMETRIC_DIFFERENCE_IN_NAME);
        ComparisonResult result = comparisonRunner.runComparison();

        assertTrue(result.getUniqueToPropertyOne().isEmpty());
        assertTrue(result.getUniqueToPropertyTwo().isEmpty());

        tearDown();
    }

    @Test
    public void testUniqueNameWithDifferences() {
        comparisonRunner = new ComparisonRunner(propertyOne, propertyTwo, Action.SYMMETRIC_DIFFERENCE_IN_NAME);
        ComparisonResult result = comparisonRunner.runComparison();

        List<String> expectedFromOne = Arrays.asList("one.unique.property.1", "two.unique.property.1", "three.unique.property.1", "four.unique.property.1");
        List<String> expectedFromTwo = Arrays.asList("one.unique.property.2", "two.unique.property.2", "three.unique.property.2", "four.unique.property.2");

        assertTrue(PropertyUtils.isConceptuallyEqual(expectedFromOne, result.getUniqueToPropertyOne()));
        assertTrue(PropertyUtils.isConceptuallyEqual(expectedFromTwo, result.getUniqueToPropertyTwo()));

        tearDown();
    }

    @Test
    public void testValuesWithDifference() {
        comparisonRunner = new ComparisonRunner(propertyOne, propertyTwo, Action.SYMMETRIC_DIFFERENCE_IN_VALUE);
        ComparisonResult result = comparisonRunner.runComparison();

        List<String> expectedFromOne = Arrays.asList("one.unique.property.1", "two.unique.property.1", "three.unique.property.1", "four.unique.property.1");
        List<String> expectedFromTwo = Arrays.asList("one.unique.property.2", "two.unique.property.2", "three.unique.property.2", "four.unique.property.2");

        Map<String, SimpleEntry<String, String>> expectedDifferences = new HashMap<String, SimpleEntry<String, String>>();
        expectedDifferences.put("common.1.diffValue", new SimpleEntry<String, String>("file1", "file2"));
        expectedDifferences.put("common.2.diffValue", new SimpleEntry<String, String>("file1", "file2"));

        assertTrue(PropertyUtils.isConceptuallyEqual(expectedFromOne, result.getUniqueToPropertyOne()));
        assertTrue(PropertyUtils.isConceptuallyEqual(expectedFromTwo, result.getUniqueToPropertyTwo()));

        assertTrue(PropertyUtils.isConceptuallyEqual(expectedDifferences, result.getSymmetricValueDifferences()));

        tearDown();
    }


    @Test
    public void testIntersectionOfValues() {
        comparisonRunner = new ComparisonRunner(propertyOne, propertyTwo, Action.INTERSECTION_OF_VALUES);
        ComparisonResult result = comparisonRunner.runComparison();

        Map<String, String> intersection = new HashMap<String, String>();
        intersection.put("common.1", "one");
        intersection.put("common.2", "two");

        assertTrue(result.getUniqueToPropertyOne().isEmpty());
        assertTrue(result.getUniqueToPropertyTwo().isEmpty());

        assertTrue(PropertyUtils.isConceptuallyEqual(result.getIntersection(), intersection));

        tearDown();
    }

}
