package jpropcompare.output;

import jpropcompare.comparator.ComparisonResult;

/**
 * Interface allowing multiple output points
 * Author: Joe Vartuli
 * Date: 19/09/11
 */
public interface Output {

    /**
     * Result data given to the Output for processing
     * @param data - resulting data
     */
    public void write(String data);

    public void outputResult(ComparisonResult result);

    public void finalise();

}
