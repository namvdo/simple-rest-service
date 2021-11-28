package model;


import java.util.*;

/**
 * This is the class will create a new theater instance,
 * sketch out all the logic that users will consume in this rest service
 * including create a new theater, check available seat sets,
 * and reserve a seat.
 *
 * @author namvdo
 */
public class Theater {
    private final String[][] allSeats;
    private final int minDistance;

    /**
     * Instantiates a new Theater.
     *
     * @param rows        the rows
     * @param columns     the columns
     * @param minDistance the min distance
     */
    public Theater(int rows, int columns, int minDistance) {
        this.allSeats = new String[rows][columns];
        setDefaultValues(allSeats);
        this.minDistance = minDistance;
    }

    /**
     * Gets min distance.
     *
     * @return the min distance
     */
    public int getMinDistance() {
        return minDistance;
    }

    /**
     * Gets all number of rows in the theater.
     *
     * @return the number of rows
     */
    public int getNumberOfRows() {
        return allSeats.length;
    }

    /**
     * Gets all number of columns per row in the theater.
     *
     * @return the columns per row
     */
    public int getColumnsPerRow() {
        return allSeats[0].length;
    }

    /**
     * Gets all number of seats in the theater.
     *
     * @return the all number of seats
     */
    public int getAllNumberOfSeats() {
        return allSeats.length * allSeats[0].length;
    }

    /**
     * Gets available seat sets which are currently available for booking.
     *
     * @param requiredSeats - number of consecutive seats needed.
     * @return a list of seat sets available for booking
     */
    public List<Set<Integer>> getAvailableSeatSets(int requiredSeats) {
        List<Set<Integer>> availableSets = new ArrayList<>(10);
        int seatsPerRow = getColumnsPerRow();
        int noRows = getNumberOfRows();
        if (requiredSeats > seatsPerRow) {
            return new ArrayList<>();
        }
        for (int i = 0; i < noRows; i++) {
            Set<Integer> consecutiveSeatsAvailable = new HashSet<>();
            for (int j = 0; j < seatsPerRow; j++) {
                int seatLabel = i * seatsPerRow + j + 1;
                // check if the current seat is empty, if it's, add it to the list
                if ("".equals(allSeats[i][j])) {
                    consecutiveSeatsAvailable.add(seatLabel);
                }
                // if the seat is not empty, check whether the previous added seats
                // are enough for required seats, if they are, add the list to the
                // available seat set for booking.
                else if (!"".equals(allSeats[i][j])) {
                    if (consecutiveSeatsAvailable.size() >= requiredSeats) {
                        availableSets.add(consecutiveSeatsAvailable);
                    }
                    consecutiveSeatsAvailable.clear();
                }
                // if we reach the last column and this seat is empty, check if the
                // number of consecutive seats are adequate, if they are
                // add them to the number of available seat set.
                if (j == seatsPerRow - 1 && "".equals(allSeats[i][j])) {
                    consecutiveSeatsAvailable.add(seatLabel);
                    if (consecutiveSeatsAvailable.size() >= requiredSeats) {
                        availableSets.add(consecutiveSeatsAvailable);
                    }
                }
            }
        }
        return availableSets;
    }

    /**
     * This is for the unit test - which is under the test/java folder
     *
     * @param requiredSeat the required seat
     * @return the seat sets available in string representation
     */
    public String getSeatSetsAvailableStr(int requiredSeat) {
        List<Set<Integer>> availableSets = getAvailableSeatSets(requiredSeat);
        String[][] theater = Arrays.stream(allSeats).map(String[]::clone).toArray(String[][]::new);
        StringBuilder stringBuilder = new StringBuilder();
        for (Set<Integer> set : availableSets) {
            for (int pos : set) {
                int row = calculateRowPosition(pos);
                int column = calculateColumnPosition(pos);
                theater[row][column] = "-";
            }
        }
        for (String[] row : theater) {
            for (String pos : row) {
                if ("".equals(pos)) {
                    stringBuilder.append(" 0 ");
                } else {
                    stringBuilder.append(" ").append(pos).append(" ");
                }
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    /**
     * Reserves a seat for clients.
     *
     * @param party  the party the client want to reserve as
     * @param row    the row position in the theater
     * @param column the column position in the theater
     * @return true if succeeded or false otherwise.
     */
    public boolean reserveSeat(String party, int row, int column) {
        if (!"".equals(allSeats[row][column]) || !isValidPosition(row, column)) {
            return false;
        } else {
            Map<String, Coordinate> nearestParties = getTheNearestParties(party, row, column);
            if (!nearestParties.isEmpty()) {
                for (Map.Entry<String, Coordinate> entry : nearestParties.entrySet()) {
                    Coordinate point = entry.getValue();
                    int manhattanDistance = getManhattanDistance(row, point.getX(), column, point.getY());
                    if (manhattanDistance < minDistance) {
                        return false;
                    }
                }
            }
            allSeats[row][column] = party;
            return true;
        }
    }

    private boolean isValidPosition(int row, int column) {
        return row >= 0 && row <= allSeats.length - 1 && column >= 0 && column <= allSeats[0].length - 1;
    }

    /**
     * Helper function to return the nearest parties on the theater.
     *
     * @param party  the current party looking for another parties
     * @param row    the current row of this party
     * @param column the current column of this party
     * @return a map indicates the parties and their coordinates
     */
    private Map<String, Coordinate> getTheNearestParties(String party, int row, int column) {
        // there is a plural, because there can be 2 parties with the same nearest distance
        // with the provided coordinate. Or it can only contain one party, or also there will be 
        // no parties output in case no party found.
        Map<String, Coordinate> nearestParties = new HashMap<>(1);
        // initially sets both forward and backward position as the current required position
        // the position number is viewed in the client view. i.e, rows start with 1, and columns
        // also starts with 1, the purpose is to not create auxiliary row and column variables and
        // update them in each pass inside the loop.
        int currentForwardPosition = row * getColumnsPerRow() + column + 1;
        int currentBackwardPosition = row * getColumnsPerRow() + column + 1;
        // here, we have to scan left and right to get nearest parties,
        // we can whether just go backward with the number of min distance steps and
        // forward with the number of min distance steps
        // or else we need to go to the very first seat and/or go to the very last seat
        // to find the candidate party.
        List<Optional<Coordinate>> limitLeftRight = getLimitLeftRight(row, column);
        int limitLeft;
        int limitRight;
        if (limitLeftRight.get(0).isPresent()) {
            Coordinate point = limitLeftRight.get(0).get();
            limitLeft = (point.getX() * getColumnsPerRow() + column + 1);
        } else {
            limitLeft = 0;
        }
        if (limitLeftRight.get(1).isPresent()) {
            Coordinate point = limitLeftRight.get(1).get();
            limitRight = (point.getX() * getColumnsPerRow() + column + 1);
        } else {
            limitRight = getAllNumberOfSeats();
        }
        boolean terminate = false;
        boolean terminateLeft = false;
        boolean terminateRight = false;
        int xLeft;
        int xRight;
        int yLeft;
        int yRight;
        do {
            xLeft = calculateRowPosition(currentBackwardPosition);
            xRight = calculateRowPosition(currentForwardPosition);
            yLeft = calculateColumnPosition(currentBackwardPosition);
            yRight = calculateColumnPosition(currentForwardPosition);
            if (!"".equals(allSeats[xLeft][yLeft]) && !allSeats[xLeft][yLeft].equals(party)
                    && !terminateLeft) {
                String partyFound = allSeats[xLeft][yLeft];
                nearestParties.put(partyFound, new Coordinate(xLeft, yLeft));
                terminate = true;
            } else if (!"".equals(allSeats[xRight][yRight]) && !allSeats[xRight][yRight].equals(party)
                    && !terminateRight) {
                String partyFound = allSeats[xRight][yRight];
                nearestParties.put(partyFound, new Coordinate(xRight, yRight));
                terminate = true;
            }
            if (currentBackwardPosition != 0) {
                currentBackwardPosition--;
            }
            if (currentForwardPosition != getAllNumberOfSeats()) {
                currentForwardPosition++;
            }
            if (currentBackwardPosition <= limitLeft) {
                terminateLeft = true;
            }
            if (currentForwardPosition >= limitRight) {
                terminateRight = true;
            }
            if (terminateLeft && terminateRight) {
                terminate = true;
            }

        } while (!terminate);
        return nearestParties;
    }

    private int calculateRowPosition(int currentPosition) {
        if (currentPosition <= getColumnsPerRow()) {
            return 0;
        }
        return currentPosition % getColumnsPerRow() == 0 ?
                currentPosition / getColumnsPerRow() - 1 : currentPosition / getColumnsPerRow();

    }

    private int calculateColumnPosition(int currentPosition) {
        int currentRow = calculateRowPosition(currentPosition);
        if (currentPosition <= getColumnsPerRow() || currentRow == 0) {
            return currentPosition - 1;
        } else {
            return currentPosition - (currentRow * getColumnsPerRow()) - 1;
        }
    }

    /**
     * This helper function will calculate the left right limit that we have
     * to search for another parties, instead of going all the way up till the end
     * or all the way down to the beginning.
     *
     * @param row    the current row
     * @param column the current column
     * @return a list of limit coordinates
     */
    private List<Optional<Coordinate>> getLimitLeftRight(int row, int column) {
        List<Optional<Coordinate>> limits = new ArrayList<>(2);
        int yLeft = Math.max(column - minDistance, 0);
        if (yLeft == column) {
            limits.add(Optional.of(new Coordinate(row, yLeft)));
        } else {
            int xLeft = row - (minDistance - yLeft);
            if (xLeft >= 0) {
                limits.add(Optional.of(new Coordinate(xLeft, yLeft)));
            } else {
                limits.add(Optional.empty());
            }
        }
        int yRight = (column + minDistance) <= getColumnsPerRow() ? column + minDistance : getColumnsPerRow() - 1;
        if (yRight == column + minDistance) {
            limits.add(Optional.of(new Coordinate(row, yRight)));
        } else {
            int xRight = row + (minDistance - (yRight - column));
            if (xRight <= getNumberOfRows() - 1) {
                limits.add(Optional.of(new Coordinate(xRight, yRight)));
            } else {
                limits.add(Optional.empty());
            }
        }
        return limits;
    }

    /**
     * This is for calculating the Manhattan Distance between 2 positions
     *
     * @param x1 the first row position
     * @param x2 the second row position
     * @param y1 the first column position
     * @param y2 the second column position
     * @return the Manhattan distance
     */
    private int getManhattanDistance(int x1, int x2, int y1, int y2) {
        return Math.abs(x2 - x1) + Math.abs(y2 - y1);
    }

    private void setDefaultValues(String[][] seats) {
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[0].length; j++) {
                seats[i][j] = "";
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (String[] allSeat : allSeats) {
            for (int j = 0; j < allSeats[0].length; j++) {
                if ("".equals(allSeat[j])) {
                    str.append(" 0 ");
                } else {
                    str.append(" ").append(allSeat[j]).append(" ");
                }
            }
            str.append("\n");
        }
        return str.toString();
    }

    public boolean isPositionFree(int row, int column) {
        return "".equals(allSeats[row][column]);
    }
}
