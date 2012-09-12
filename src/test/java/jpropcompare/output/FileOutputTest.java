package jpropcompare.output;

import jpropcompare.comparator.ComparisonResult;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertTrue;

/**
 * User: Joseph Vartuli
 * Date: 12/09/12
 *
 * @since:
 */
public class FileOutputTest {

    private static final String OUTPUT_FILENAME = "target/outputFile.txt";
    private static final String FILENAME_1 = "filename1";
    private static final String FILENAME_2 = "filename2";

    private static final List<String> UNIQUE_TO_PROPERTY_ONE = new ArrayList<String>();
    private static final List<String> UNIQUE_TO_PROPERTY_TWO = new ArrayList<String>();

    static {
        UNIQUE_TO_PROPERTY_ONE.add("property1,1234");
        UNIQUE_TO_PROPERTY_ONE.add("property1,2345");
        UNIQUE_TO_PROPERTY_ONE.add("property1,3456");
        UNIQUE_TO_PROPERTY_TWO.add("property2,1234");
        UNIQUE_TO_PROPERTY_TWO.add("property2,2345");
        UNIQUE_TO_PROPERTY_TWO.add("property2,3456");
    }

    private FileOutput fileOutput;

    private void setUp() {
        fileOutput = new FileOutput(OUTPUT_FILENAME, FILENAME_1, FILENAME_2);
    }

    @Test
    public void testUniqueFileEntries() throws IOException {
        setUp();

        ComparisonResult comparisonResult = createMock(ComparisonResult.class);
        expect(comparisonResult.getUniqueToPropertyOne()).andReturn(UNIQUE_TO_PROPERTY_ONE);
        expect(comparisonResult.getUniqueToPropertyTwo()).andReturn(UNIQUE_TO_PROPERTY_TWO);
        expect(comparisonResult.getSymmetricValueDifferences()).andReturn(null);

        replay(comparisonResult);

        fileOutput.outputResult(comparisonResult);

        boolean isFileEqual = isFileContentEqual(OUTPUT_FILENAME, "src/test/resources/UniqueFileEntries.txt");
        assertTrue(isFileEqual);

        verify(comparisonResult);
        cleanUp();
    }

    private boolean isFileContentEqual(String filenameOne, String filenameTwo) throws IOException {
        File fileOne = new File(filenameOne);
        File fileTwo = new File(filenameTwo);

        BufferedReader fileReaderOne = new BufferedReader(new FileReader(fileOne));
        BufferedReader fileReaderTwo = new BufferedReader(new FileReader(fileTwo));

        String temp;
        StringBuilder contentOfFileOne = new StringBuilder();
        StringBuilder contentOfFileTwo = new StringBuilder();

        while ((temp = fileReaderOne.readLine()) != null) {
            contentOfFileOne.append(temp);
        }

        temp = null;

        while ((temp = fileReaderTwo.readLine()) != null) {
            contentOfFileTwo.append(temp);
        }

        return contentOfFileOne.toString().equals(contentOfFileTwo.toString());
    }

    private void cleanUp() {
        File file = new File(OUTPUT_FILENAME);

        if (file.exists()) {
            file.delete();
        }
    }

}
