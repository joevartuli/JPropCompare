package jpropcompare.exception;

/**
 * Author: Joe Vartuli
 * Date: 7/02/12
 */
public class PropertyValidationException extends RuntimeException {

    public PropertyValidationException() {
    }

    public PropertyValidationException(String message) {
        super(message);
    }

    public PropertyValidationException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public PropertyValidationException(Throwable throwable) {
        super(throwable);
    }

}
