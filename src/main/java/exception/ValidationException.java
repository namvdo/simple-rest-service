package exception;

/**
 * @author namvdo
 * The instance of this class will be intialized when
 * there are some validation errors when users enter
 * numbers/strings
 */
public class ValidationException extends Exception {
    private final String message;

    public ValidationException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
