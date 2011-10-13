package jpropcompare.output;

import java.io.PrintStream;

/**
 * ConsoleOut directs all result output to System.out
 * @author: Joe Vartuli
 * @date: 19/09/11
 */
public class ConsoleOutput implements Output {

    private PrintStream out = System.out;

    /**
     * Writes the data directly to the System.out
     * @param data - data to output
     */
    @Override
    public void write(String data) {
        out.println(data);
    }

    @Override
    public void finalise() {
        return;
    }
}
