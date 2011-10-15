package jpropcompare.comparator;

import java.util.List;

/**
 * User: Joe Vartuli
 * Date: 15/10/11
 */
public class ComparisonResultBuilder {

    private List<String> uniqueToPropertyOne;
    private List<String> uniqueToPropertyTwo;


    public ComparisonResult build() {
        ComparisonResult result = new ComparisonResult(uniqueToPropertyOne, uniqueToPropertyTwo);
        return result;
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
