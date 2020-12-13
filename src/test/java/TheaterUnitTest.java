import model.Theater;
import org.junit.Assert;
import org.junit.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author namvdo
 */
public class TheaterUnitTest {
    @Test
    public void testReserveSeat() {
        Logger logger = Logger.getLogger(TheaterUnitTest.class.getName());
        Theater theater = initSetUp();
        Assert.assertTrue(theater.reserveSeat("I", 1, 1));
        Assert.assertFalse(theater.reserveSeat("X", 2, 2));
        Assert.assertFalse(theater.reserveSeat("Y", 2, 4));
        logger.log(Level.INFO, "{0}", "\n" + theater.toString());
    }
    @Test
    public void testGetAvailableSets() {
        String delimiter = "\n";
        Logger logger = Logger.getLogger(TheaterUnitTest.class.getName());
        Theater theater = initSetUp();
        String seatsAvailable = theater.getSeatSetsAvailableStr(5);
        logger.log(Level.INFO, "{0}", "\n" + seatsAvailable);
        String seats =
                " -  -  -  -  -  -  - " + delimiter +
                " -  -  -  -  -  -  - " + delimiter +
                " 0  0  X  X  0  0  0 " + delimiter +
                " -  -  -  -  -  -  - " + delimiter +
                " Y  0  0  0  0  Z  Z " + delimiter +
                " Z  -  -  -  -  -  - " + delimiter +
                " -  -  -  -  -  -  - " + delimiter;
        Assert.assertEquals(seats, seatsAvailable);
    }

    private Theater initSetUp() {
        Theater theater = new Theater(7,7, 5);
        theater.reserveSeat("X", 2, 2);
        theater.reserveSeat("X", 2, 3);
        theater.reserveSeat("Y", 4, 0);
        theater.reserveSeat("Z", 4, 5);
        theater.reserveSeat("Z", 4, 6);
        theater.reserveSeat("Z", 5, 0);
        return theater;
    }
}
