package jpropcompare.comparator;

/**
 * Defines the difference actions available to compare property files.
 * Actions available
 * - SYMMETRIC_DIFFERENCE_IN_VALUE: command line argument "1"
 * - SYMMETRIC_DIFFERENCE_IN_NAME: command line argument "2"
 *
 * Author: Joe Vartuli
 * Date: 19/09/11
 * @since 1.0
 */
public enum Action {

    SYMMETRIC_DIFFERENCE_IN_VALUE("1", "Finds all property keys that are either unique to a property file or where the same property key differ in value"),
    SYMMETRIC_DIFFERENCE_IN_NAME("2", "Finds all property keys that are unique to a property file"),
    INTERSECTION_OF_VALUES("3", "Finds all property keys that are in both property files and have the same value"),
    UNION_IN_VALUE("4", "Currently unsupported"),
    UNION_IN_NAME("5", "Currently unsupported");
    //SIX("6"),
    //SEVEN("7");

    private String action;
    private String description;

    Action(String action, String description) {
        this.action = action;
        this.description = description;
    }

    /**
     * Returns the default Action to execute
     * @return - default Action
     */
    public static Action getDefaultAction() {
        return SYMMETRIC_DIFFERENCE_IN_VALUE;
    }

    public String getActionValue() {
        return action;
    }
    
    public String getActionDescription() {
        return description;
    }

    /**
     * Finds the matching action based on the string passed in
     * @param action - action requested
     * @return - returns the matching action, otherwise null is returned
     */
    public static Action getAction(String action) {
        Action actionFound = null;

        for (Action thisAction : Action.values()) {
            if (thisAction.action.equals(action)) {
                actionFound = thisAction;
                break;
            }
        }

        return actionFound;
    }

}
