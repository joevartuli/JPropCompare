package jpropcompare.output;

import jpropcompare.comparator.ComparisonResult;

/**
 * Interface allowing multiple output points
 * Author: Joe Vartuli
 * Date: 19/09/11
 */
public interface Output {

    /**
     * Outputs the result of the comparison test in a given format
     * @param result - result to output
     */
    public void outputResult(ComparisonResult result);

}
