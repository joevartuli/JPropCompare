package jpropcompare.comparator;

import java.util.List;

/**
 * Author: Joe Vartuli
 * Date: 15/10/11
 */
public class ComparisonResultBuilder {

    private List<String> uniqueToPropertyOne;
    private List<String> uniqueToPropertyTwo;


    public ComparisonResult build() {
        return new ComparisonResult(uniqueToPropertyOne, uniqueToPropertyTwo);
    }


    public ComparisonResultBuilder setUniqueToPropertyOne(List<String> uniqueToPropertyOne) {
        this.uniqueToPropertyOne = uniqueToPropertyOne;
        return this;
    }

    public ComparisonResultBuilder setUniqueToPropertyTwo(List<String> uniqueToPropertyTwo) {
        this.uniqueToPropertyTwo = uniqueToPropertyTwo;
        return this;
    }

}
