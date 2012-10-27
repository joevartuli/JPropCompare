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
        boolean matchFound = false;

        for (ValidationRules validationRule : ValidationRules.values()) {
            if (validationRule.getRuleType().equals(rule)) {
                rules.put(property, validationRule.getMatchingPattern());
                matchFound = true;
                break;
            }
        }

        if (!matchFound) {
            rules.put(property, Pattern.compile(rule));
        }

    }

    private enum ValidationRules {

        //Best way to handle patterns that are allowed to be empty?
        NUMBER("{num}", Pattern.compile("[0-9]+")),
        URL("{url}", Pattern.compile("(http://)?[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]+(:[0-9]+)?(/)?")),
        EMAIL("{email}", Pattern.compile("[A-Za-z\\+0-9\\.\\-]+@[A-Za-z0-9\\.\\-]+\\.[a-zA-Z]+")),
        BOOLEAN("{bool}", Pattern.compile("true|false"));

        private Pattern matchingPattern;
        private String ruleType;

        ValidationRules(String ruleType, Pattern matchingPattern) {
            this.ruleType = ruleType;
            this.matchingPattern = matchingPattern;
        }

        public Pattern getMatchingPattern() {
            return matchingPattern;
        }

        public String getRuleType() {
            return ruleType;
        }
    }

}
