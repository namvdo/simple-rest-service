package exception;

/**
 * @author namvdo
 * The instance of this class will be intialized when
 * users enter invalid parties.
 */
public class InvalidPartyException extends Exception {
    private final String message;

    public InvalidPartyException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
