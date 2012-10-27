package jpropcompare.output;

import jpropcompare.Constants;

/**
 * User: Joe Vartuli
 * Date: 2/11/11
 */
public class StringOutput extends StandardOutputFormatter {

    private StringBuilder builder = new StringBuilder();
    private String result;

    public StringOutput(String propertyFilenameOne, String propertyFilenameTwo) {
        super(propertyFilenameOne, propertyFilenameTwo);
    }

    @Override
    public void write(String output) {
        builder.append(output);
        builder.append(Constants.NEW_LINE);
    }

    @Override
    public void finalise() {
        result = builder.toString();
    }

    public String result() {
        return result;
    }
}
