package jpropcompare.validation;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: joe
 * Date: 30/01/12
 * Time: 8:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class ValidationIntegrationTest {

    private Properties propertiesToValidate;
    private Properties propertyRules;

    @Before
    public void setUp() throws IOException {
        InputStream propsToValidate = this.getClass().getClassLoader().getResourceAsStream("propertiesToValidate.properties");
        propertiesToValidate = new Properties();
        propertiesToValidate.load(propsToValidate);

        InputStream validationRuleStream = this.getClass().getClassLoader().getResourceAsStream("validationRules.properties");
        propertyRules = new Properties();
        propertyRules.load(validationRuleStream);
    }


    @Test
    public void test() {
        ValidateProperties validateProperties = new ValidateProperties(propertyRules, propertiesToValidate);
        validateProperties.runValidation();
    }

}
