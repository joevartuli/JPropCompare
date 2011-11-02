package jpropcompare;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * User: Joe Vartuli
 * Date: 21/09/11
 */
public class ComparatorTest {

    private Comparator comparator;
    private ByteArrayOutputStream byteArrayOutputStream;
    private PrintStream printStream;

    private void tearDown()  {
        comparator = null;
        if (printStream != null) {
            printStream.close();
            printStream = null;
            byteArrayOutputStream = null;
        }
    }

    @Test
    public void testComparator() {
        byteArrayOutputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(byteArrayOutputStream);
        comparator = new Comparator(printStream);

        comparator.run((String[]) null);

        assertTrue("Usage".equalsIgnoreCase(byteArrayOutputStream.toString().substring(0, 5)));

        tearDown();
    }


}
