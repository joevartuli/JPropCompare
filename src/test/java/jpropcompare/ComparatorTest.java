package jpropcompare;

import org.junit.Test;

/**
 * User: Joe Vartuli
 * Date: 21/09/11
 */
public class ComparatorTest {

    private Comparator comparator;

    private void tearDown()  {
        comparator = null;
    }

    @Test
    public void testRunWithFile() {
        String[] args = {"-p1","property1.properties", "-p2","property2.properties", "-f", "C:\\test\\test.txt"};
        comparator = new Comparator();
        comparator.run(args);
        tearDown();
    }
}
