package jpropcompare.loading.strategy;

import java.util.Properties;

/**
 * User: Joe Vartuli
 * Date: 19/09/11
 */
public class LoadingStrategyTest implements LoadingStrategy {

    @Override
    public String getPropertyNameOne() {
        return null;
    }

    @Override
    public void setPropertyNameOne(String propertyName) {
    }


    @Override
    public String getPropertyNameTwo() {
        return null;
    }

    @Override
    public void setPropertyNameTwo(String propertyName) {
    }

    @Override
    public Properties getPropertyFileOne() {
        return null;
    }

    @Override
    public Properties getPropertyFileTwo() {
        return null;
    }

    @Override
    public void setArgs(String[] args) {
    }
}
