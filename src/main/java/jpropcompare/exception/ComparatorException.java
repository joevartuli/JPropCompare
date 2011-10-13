package jpropcompare.exception;

/**
 * @author: Joe Vartuli
 * @date: 22/09/11
 */
public class ComparatorException extends Exception {

    public ComparatorException() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public ComparatorException(String message) {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public ComparatorException(String message, Throwable cause) {
        super(message, cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public ComparatorException(Throwable cause) {
        super(cause);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
