package jpropcompare.validation;

import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: joe
 * Date: 30/01/12
 * Time: 8:13 PM
 * To change this template use File | Settings | File Templates.
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


    
    public void runValidation() {
        for (String propertyName : rules.keySet()) {
            Pattern pattern = rules.get(propertyName);
            Matcher matcher = pattern.matcher(propertiesToValidate.getProperty(propertyName));
            if (matcher.matches()) {
                System.out.println(propertyName + " with value " + propertiesToValidate.getProperty(propertyName) + " matches " + pattern);
            } else {
                System.out.println(propertyName + " with value " + propertiesToValidate.getProperty(propertyName) + " does not match " + pattern);
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
