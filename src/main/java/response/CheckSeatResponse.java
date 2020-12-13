package response;

import java.util.List;
import java.util.Set;

/**
 * The instance of this class will be created when
 * the check available seat sets request is sent.
 * @author namvdo
 */
public class CheckSeatResponse extends BaseResponse{
    private final String seatsAvailable;
    private final List<Set<Integer>> availablePos;

    public CheckSeatResponse(String result, String message, List<Set<Integer>> availablePositions) {
        super(result, message);
        this.availablePos = availablePositions;
        this.seatsAvailable = representTheaterPos();
    }

    public String getSeatsAvailable() {
        return seatsAvailable;
    }
    private String representTheaterPos() {
        StringBuilder str = new StringBuilder();
        for(Set<Integer> list: availablePos) {
           for(int pos: list) {
               str.append(" ").append(pos).append(" ");
           }
           str.append(" | ");
        }
        return str.toString();
    }
}
