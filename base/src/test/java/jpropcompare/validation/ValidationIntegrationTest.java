package jpropcompare.validation;

import jpropcompare.exception.PropertyValidationException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * User: Joe Vartuli
 * Date: 30/01/12
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


    @Test(expected = PropertyValidationException.class)
    public void test() {
        ValidateProperties validateProperties = new ValidateProperties(propertyRules, propertiesToValidate);
        validateProperties.runValidation();
    }

}
