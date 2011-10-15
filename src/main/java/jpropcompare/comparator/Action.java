package jpropcompare.comparator;

/**
 * Defines the difference actions available to compare property files.
 * Actions available
 * - UNIQUE_NAMES: command line argument "uname"
 *
 * Author: Joe Vartuli
 * Date: 19/09/11
 * @since 1.0
 */
public enum Action {

    UNIQUE_NAMES("uname");

    private String action;

    Action(String action) {
        this.action = action;
    }

    /**
     * Returns the default Action to execute
     * @return - default Action
     */
    public static Action getDefaultAction() {
        return UNIQUE_NAMES;
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
