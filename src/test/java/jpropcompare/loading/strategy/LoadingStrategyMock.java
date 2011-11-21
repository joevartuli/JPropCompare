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

    @Override
    public void initialise(String propertyFileNameOne, String propertyFileNameTwo, String[] args) {
        propertyOne = new Properties();
        propertyTwo = new Properties();

        try {
            propertyOne.load(this.getClass().getClassLoader().getResourceAsStream(propertyFileNameOne));
            propertyTwo.load(this.getClass().getClassLoader().getResourceAsStream(propertyFileNameTwo));
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
