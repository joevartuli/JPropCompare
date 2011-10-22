package jpropcompare;

/**
 *
 * Author: Joe Vartuli
 * Date: 19/09/11
 */
public class Constants {

    public static final String ENTRIES_UNIQUE = "Entries unique to ";
    public static final String NO_DIFFERENCE = "No differences found";
    public static final String DIFFERENCE = "Differences found:";
    public static final String PREFIX = "  + ";
    public static final String NOT_VALID_ARG = "No matching argument with ";

    public static final String NEW_LINE =  System.getProperty("line.separator");

    public static final String CLASS_SUBSTITUTE = "%c%";
    public static final String CLASS_NOT_CREATED = "Unable to instantiate class "+CLASS_SUBSTITUTE+", ensure it has a no argument constructor";
    public static final String CLASS_NOT_FOUND = "Could not find class "+CLASS_SUBSTITUTE;

    public static final String REQUESTED_ACTION = "%a%";
    public static final String ACTION_NOT_FOUND = "The requested action " + REQUESTED_ACTION + " could not be found";

}
