package jpropcompare.loading.strategy;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * User: Joe Vartuli
 * Date: 19/09/11
 * Time: 4:00 PM
 */
public class TestLoadingStrategy implements LoadingStrategy {

    @Override
    public String getPropertyName1() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setPropertyName1(String propertyName) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getPropertyName2() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setPropertyName2(String propertyName) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Properties getPropertyFile1() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Properties getPropertyFile2() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setArgs(String[] args) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
