package validation;

import exception.InvalidPartyException;
import exception.TheaterException;
import exception.ValidationException;
import model.Theater;


/**
 * @author namvdo
 */
public class BaseValidation {
    private BaseValidation() {

    }

    /**
     * Check whether the values input to set up the theater
     * is in the right format.
     *
     * @param row         the number of row
     * @param column      the number of column
     * @param minDistance the min distance
     * @return the boolean
     * @throws ValidationException the validation exception
     */
    public static boolean isValidSetupTheater(String row, String column, String minDistance) throws ValidationException {
        try {
            int r = Integer.parseInt(row);
            int c = Integer.parseInt(column);
            int distance = Integer.parseInt(minDistance);
            if (r <= 0 || c <= 0 || distance <= 0) {
                throw new ValidationException("Invalid input, try again!");
            }
            return true;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Checks whether this seat position is valid
     *
     * @param party   the party from the client
     * @param row     the current row from the client
     * @param column  the current column from the client
     * @param theater the current theater
     * @return the boolean
     * @throws ValidationException   the validation exception
     * @throws InvalidPartyException the invalid party exception
     * @throws TheaterException      the theater exception
     */
    public static boolean isValidSeatPosition(String party, String row, String column, Theater theater)
            throws ValidationException, InvalidPartyException, TheaterException {
        try {
            if (theater == null) {
                throw new TheaterException("There is no theater that has been setting up.");
            }
            if ("".equals(party) || "X".equals(party) || "0".equals(party) || "-".equals(party) || !areLetters(party)) {
                throw new InvalidPartyException("Party cannot either be empty, or use reversed keyword, or something that" +
                        "are not letters.");
            }
            int r = Integer.parseInt(row);
            int c = Integer.parseInt(column);
            if (r < 0 || c < 0 || r >= theater.getNumberOfRows() || c >= theater.getColumnsPerRow()) {
                throw new ValidationException("Invalid position, try again!");
            } else {
                return true;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Checks whether the required seats are in the right format.
     *
     * @param requiredSeats the required seats
     * @param theater       the current theater
     * @return true if valid, false otherwise
     * @throws ValidationException the validation exception
     * @throws TheaterException    the theater exception
     */
    public static boolean isValidRequiredSeatsInput(String requiredSeats, Theater theater) throws ValidationException,
            TheaterException {
        try {
            if (theater == null) {
                throw new TheaterException("There is no theater that has been setting up.");
            }
            int seats = Integer.parseInt(requiredSeats);
            if (seats <= 0 || seats >= theater.getAllNumberOfSeats()) {
                throw new ValidationException("The required number of seats is either too large or too small");
            }
            return true;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean areLetters(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }
}
