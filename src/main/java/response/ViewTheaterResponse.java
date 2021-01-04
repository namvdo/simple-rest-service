package response;

/**
 * The instance of this class will be created when
 * the view theater request is sent.
 *
 * @param <T> the type parameter
 * @author namvdo
 */
public class ViewTheaterResponse<T> extends BaseResponse {
    private final T theaterLayout;

    public ViewTheaterResponse(String result, String message, T theater) {
        super(result, message);
        this.theaterLayout = theater;
    }

    public T getTheaterLayout() {
        return theaterLayout;
    }
}
