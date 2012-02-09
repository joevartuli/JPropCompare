package jpropcompare.validation;

import jpropcompare.exception.PropertyValidationException;

import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: Joe Vartuli
 * Date: 30/01/12
 */
public class ValidateProperties {

    private Map<String, Pattern> rules;
    private ValidationRuleBuilder validationRuleBuilder;
    private Properties propertiesToValidate;

    private ValidateProperties() {
        validationRuleBuilder = new ValidationRuleBuilder();
    }


    public ValidateProperties(Map<String, String> rules, Properties propertiesToValidate) {
        this();
        this.propertiesToValidate = propertiesToValidate;
        this.rules = validationRuleBuilder.build(rules);

    }

    public ValidateProperties(Properties rules, Properties propertiesToValidate) {
        this();
        this.propertiesToValidate = propertiesToValidate;
        this.rules = validationRuleBuilder.build(rules);
    }

    /**
     * Validate the given property file with the rules passed in
     * @throws PropertyValidationException - thrown when a property file value fails validation
     */
    public void runValidation() {
        for (String propertyName : rules.keySet()) {
            Pattern pattern = rules.get(propertyName);
            Matcher matcher = pattern.matcher(propertiesToValidate.getProperty(propertyName));
            if (!matcher.matches()) {
                throw new PropertyValidationException(propertyName + " with value " + propertiesToValidate.getProperty(propertyName) + " matches " + pattern);
            }
        }
    }



    public Pattern getPattern(String name) {
        return rules.get(name);
    }

    public Map<String, Pattern> getRules() {
        return rules;
    }
    
}
