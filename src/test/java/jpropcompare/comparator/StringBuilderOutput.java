package jpropcompare.comparator;

import jpropcompare.output.Output;

/**
 * User: vartulij
 * Date: 21/09/11
 * Time: 9:09 AM
 */
public class StringBuilderOutput implements Output {

    private StringBuilder stringBuilder = new StringBuilder();

    public void write(String data) {
        stringBuilder.append(data);
    }

    public String getResult() {
        return stringBuilder.toString();
    }

    @Override
    public void finalise() {
        return;
    }
}
