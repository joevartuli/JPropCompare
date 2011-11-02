package jpropcompare.output;

/**
 * User: Joe Vartuli
 * Date: 2/11/11
 */
public class StringOutput extends StandardOutputFormatter {

    private StringBuilder builder = new StringBuilder();

    @Override
    public void write(String output) {
        builder.append(output);
    }

    @Override
    public void finalise() {

    }

    public String result() {
        return builder.toString();
    }
}
