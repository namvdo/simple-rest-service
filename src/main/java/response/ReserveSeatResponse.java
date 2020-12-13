package response;

/**
 * The instance of this class will be created when
 * the reserve seat request is sent.
 * @param <T> the type parameter
 * @author namvdo
 */
public class ReserveSeatResponse<T> extends BaseResponse{
    private final String seatNumber;
    private final T location;
    private final String yourParty;


    public ReserveSeatResponse(String result, String message, String yourParty, T location, String label) {
        super(result, message);
        this.seatNumber = label;
        this.yourParty = yourParty;
        this.location = location;
    }


    public String getSeatNumber() {
        return seatNumber;
    }


    public T getLocation() {
        return location;
    }


    public String getYourParty() {
        return yourParty;
    }
}
