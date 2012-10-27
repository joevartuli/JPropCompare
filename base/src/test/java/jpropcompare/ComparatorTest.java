package jpropcompare;

import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * User: Joe Vartuli
 * Date: 21/09/11
 */
public class ComparatorTest {

    private Comparator comparator;
    private ByteArrayOutputStream byteArrayOutputStream;
    private PrintStream printStream;


    @Test
    public void testComparatorWithNullArguments() {
        byteArrayOutputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(byteArrayOutputStream);
        comparator = new Comparator(printStream);

        String[] args = {};

        comparator.run(args);

        String expectedErrorMsg = "First property name file can not be null";
        String errorMsg = byteArrayOutputStream.toString();

        assertEquals(expectedErrorMsg, errorMsg.substring(0, expectedErrorMsg.length()));
    }

    @Test
    public void testComparatorWithFirstPropertyArgument() {
        byteArrayOutputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(byteArrayOutputStream);
        comparator = new Comparator(printStream);

        String[] args = {"-p1", "first"};

        comparator.run(args);

        String expectedErrorMsg = "Second property name file can not be null";
        String errorMsg = byteArrayOutputStream.toString();

        assertEquals(expectedErrorMsg, errorMsg.substring(0, expectedErrorMsg.length()));
    }

    @Test
    public void testComparatorWithLoadingStrategy() {
        byteArrayOutputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(byteArrayOutputStream);
        comparator = new Comparator(printStream);

        String[] args = {"-ls", "LoadingStrategySupplied.class"};

        comparator.run(args);

        String expectedErrorMsg = "First property name file can not be null";
        String errorMsg = byteArrayOutputStream.toString();

        assertEquals(expectedErrorMsg, errorMsg.substring(0, expectedErrorMsg.length()));
    }

    @Test
    public void testComparatorWithIncorrectAction() {
        byteArrayOutputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(byteArrayOutputStream);
        comparator = new Comparator(printStream);

        String[] args = {"-p1", "p1", "-p2", "p2", "-a", "11"};

        comparator.run(args);

        String expectedErrorMsg = "The requested action 11 could not be found";
        String errorMsg = byteArrayOutputStream.toString();

        assertEquals(expectedErrorMsg, errorMsg.substring(0, expectedErrorMsg.length()));
    }

    @Test
    public void testComparatorWithCorrectArguments() {
        byteArrayOutputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(byteArrayOutputStream);
        comparator = new Comparator(printStream);

        String[] args = {"-p1", "p1", "-p2", "p2", "-a", "4"};

        comparator.run(args);
    }

    @After
    public void tearDown()  {
        comparator = null;
        if (printStream != null) {
            printStream.close();
            printStream = null;
            byteArrayOutputStream = null;
        }
    }


}
