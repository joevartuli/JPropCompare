package jpropcompare.exception;

/**
 * @author: Joe Vartuli
 * @date: 22/09/11
 */
public class LoadingStrategyException extends ComparatorException {

    public LoadingStrategyException() {
        super();
    }

    public LoadingStrategyException(String message) {
        super(message);
    }

    public LoadingStrategyException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoadingStrategyException(Throwable cause) {
        super(cause);
    }
}
