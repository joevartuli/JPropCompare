package jpropcompare.validation;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * Author: Joe Vartuli
 * Date: 27/01/12
 */
public class ValidationRuleBuilder {

    //Best way to handle patterns that are allowed to be empty?
    private static final String NUMBER = "{num}";
    private static final String URL = "{url}";
    private static final String EMAIL = "{email}";
    
    private static final Pattern NUMBER_PATTERN = Pattern.compile("[0-9]+");
    private static final Pattern URL_PATTERN = Pattern.compile("(http://)?[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]+(:[0-9]+)?(/)?");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("[0-9]");

    private Map<String, Pattern> rules;

    protected ValidationRuleBuilder() {
        rules = new HashMap<String, Pattern>();
    }
    
    
    protected Map<String, Pattern> build(Map<String, String> rules) {
        for (String property : rules.keySet()) {
            createRule(property, rules.get(property));
        }

        return this.rules;
    }

    protected Map<String, Pattern> build(Properties properties) {
        for (String property : properties.stringPropertyNames()) {
            createRule(property, properties.getProperty(property));
        }

        return this.rules;
    }


    private void createRule(String property, String rule) {
        if (NUMBER.equals(rule)) {
            rules.put(property, NUMBER_PATTERN);
        } else if (URL.equals(rule)) {
            rules.put(property, URL_PATTERN);
        } else if (EMAIL.equals(rule)) {
            rules.put(property, EMAIL_PATTERN);
        } else {
            rules.put(property, Pattern.compile(rule));
        }
    }

}
