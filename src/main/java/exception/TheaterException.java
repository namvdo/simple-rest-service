package exception;

/**
 * @author namvdo
 * The instance of this class will be intialized when
 * the theater is already created and cinema operator
 * when to create it again, or when there is no theater created
 * yet.
 */
public class TheaterException extends Exception {
    private final String message;

    public TheaterException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
