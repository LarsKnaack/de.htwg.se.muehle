package gamefield;

import model.impl.Mills;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class MillsTest {

    Mills mill;

    @Before
    public void setUp() {
        mill = new Mills();
    }

    @Test
    public void testCreation() {
        assertNotEquals(null, mill);
    }

    @Test
    public void testgetMill1() {
        ArrayList<Integer> mill1 = new ArrayList<Integer>();
        mill1.add(2);
        mill1.add(3);
        assertEquals(mill1, mill.getMill1(1));
    }

    @Test
    public void testgetMill2() {
        ArrayList<Integer> mill2 = new ArrayList<Integer>();
        mill2.add(8);
        mill2.add(7);
        assertEquals(mill2, mill.getMill2(1));
    }
}
