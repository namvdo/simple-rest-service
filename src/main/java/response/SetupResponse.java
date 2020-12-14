package response;


/**
 * The instance of this class will be created when
 * the view theater set up request is sent.
 *
 * @param <T> the type parameter
 * @author namvdo
 */
public class SetupResponse<T> extends BaseResponse {
    private final T theaterSetup;

    public SetupResponse(String result, String message, T theaterSetup) {
        super(result, message);
        this.theaterSetup = theaterSetup;
    }

    public T getTheaterSetup() {
        return theaterSetup;
    }
}
