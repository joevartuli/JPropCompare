package jpropcompare.comparator;

import java.util.List;

/**
 * Author: Joe Vartuli
 * Date: 15/10/11
 */
public class ComparisonResult {

    private List<String> uniqueToPropertyOne;
    private List<String> uniqueToPropertyTwo;

    public ComparisonResult(List<String> uniqueToPropertyOne, List<String> uniqueToPropertyTwo) {
        this.uniqueToPropertyOne = uniqueToPropertyOne;
        this.uniqueToPropertyTwo = uniqueToPropertyTwo;
    }

    public List<String> getUniqueToPropertyOne() {
        return uniqueToPropertyOne;
    }

    public List<String> getUniqueToPropertyTwo() {
        return uniqueToPropertyTwo;
    }

    @Override
    public String toString() {
        return "ComparisonResult{" +
                "uniqueToPropertyOne=" + uniqueToPropertyOne +
                ", uniqueToPropertyTwo=" + uniqueToPropertyTwo +
                '}';
    }
}
