package model;

/**
 * Represents a position of a seat in the theater.
 * Starts with (0, 0)
 * @author namvdo
 */
public class Coordinate {
    private final int x;
    private final int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
