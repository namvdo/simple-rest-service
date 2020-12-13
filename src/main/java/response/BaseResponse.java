package response;

/**
 * The base response type of other response classes.
 * @author namvdo
 */
public class BaseResponse {
    private final String result;
    private final String message;

    public BaseResponse(String result, String message) {
        this.result = result;
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

}
