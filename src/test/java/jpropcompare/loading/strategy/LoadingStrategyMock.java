package jpropcompare.loading.strategy;

import java.io.IOException;
import java.util.Properties;

/**
 * User: Joe Vartuli
 * Date: 2/11/11
 */
public class LoadingStrategyMock implements LoadingStrategy {

    private Properties propertyOne;
    private Properties propertyTwo;

    public LoadingStrategyMock(String filenameOne, String filenameTwo) {
        propertyOne = new Properties();
        propertyTwo = new Properties();

        try {
            propertyOne.load(this.getClass().getClassLoader().getResourceAsStream(filenameOne));
            propertyTwo.load(this.getClass().getClassLoader().getResourceAsStream(filenameTwo));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Properties getPropertyFileOne() {
        return propertyOne;
    }

    @Override
    public Properties getPropertyFileTwo() {
        return propertyTwo;
    }

}
