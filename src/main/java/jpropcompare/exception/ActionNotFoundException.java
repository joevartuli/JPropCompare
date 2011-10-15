package jpropcompare.exception;

/**
 * Author: Joe Vartuli
 * Date: 22/09/11
 */
public class ActionNotFoundException extends ComparatorException {

    public ActionNotFoundException() {
        super();
    }

    public ActionNotFoundException(String message) {
        super(message);
    }

    public ActionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActionNotFoundException(Throwable cause) {
        super(cause);
    }
}
