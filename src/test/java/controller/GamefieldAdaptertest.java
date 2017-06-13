package controller;

import controller.impl.GamefieldAdapter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class GamefieldAdaptertest {
    GamefieldAdapter cgf;

    @Before
    public void setUp() {
        cgf = new GamefieldAdapter();
    }

    @Test
    public void testCreation() {
        assertNotEquals(null, cgf);
    }

    @Test
    public void testsetStone() {
        assertEquals(false, cgf.setStone(2, 'a'));
        assertEquals(true, cgf.setStone(1, 'w'));
        assertEquals(false, cgf.setStone(1, 's'));
        assertEquals(false, cgf.setStone(29, 's'));
        assertEquals(false, cgf.setStone(0, 'w'));
    }

    @Test
    public void removeStone() {
        cgf.setStone(1, 's');
        cgf.setStone(2, 'w');
        cgf.setStone(3, 's');
        cgf.setStone(4, 's');
        cgf.setStone(5, 's');
        assertEquals(false, cgf.removeStone(0));
        assertEquals(false, cgf.removeStone(28));
        assertEquals(true, cgf.removeStone(1));
        assertEquals(true, cgf.removeStone(2));
        assertEquals(false, cgf.removeStone(1));
        assertEquals(false, cgf.removeStone(5));
        assertEquals(false, cgf.removeStone(4));
        assertEquals(false, cgf.removeStone(3));
    }

    @Test
    public void getColor() {
        cgf.setStone(1, 's');
        cgf.setStone(2, 'w');
        assertEquals('s', cgf.getColor(1));
        assertEquals('w', cgf.getColor(2));
        assertEquals('n', cgf.getColor(5));
        assertEquals('n', cgf.getColor(0));
        assertEquals('n', cgf.getColor(26));
    }

    @Test
    public void move() {
        cgf.setStone(1, 's');
        cgf.setStone(2, 'w');
        cgf.setStone(3, 's');
        cgf.setStone(4, 's');
        cgf.setStone(5, 's');
        assertEquals(false, cgf.move(1, 2, 's'));
        assertEquals(false, cgf.move(7, 8, 'n'));
        assertEquals(false, cgf.move(7, 8, 'e'));
        assertEquals(false, cgf.move(29, 30, 's'));
        assertEquals(false, cgf.move(0, 1, 's'));
        assertEquals(false, cgf.move(4, 23, 's'));
        assertEquals(true, cgf.move(2, 10, 'w'));
        assertEquals(true, cgf.move(1, 2, 's'));
        assertEquals(false, cgf.move(22, 23, 'n'));
        assertEquals(false, cgf.move(1, 2, 'g'));
    }

    @Test
    public void numberOfMills() {
        cgf.setStone(1, 's');
        cgf.setStone(2, 'w');
        cgf.setStone(3, 's');
        cgf.setStone(4, 's');
        cgf.setStone(5, 's');
        cgf.setStone(6, 's');
        cgf.setStone(7, 's');
        assertEquals(0, cgf.numberOfMills(29, 'w'));
        assertEquals(0, cgf.numberOfMills(17, 'q'));
        assertEquals(0, cgf.numberOfMills(0, 's'));
        assertEquals(0, cgf.numberOfMills(1, 's'));
        assertEquals(1, cgf.numberOfMills(3, 's'));
        assertEquals(0, cgf.numberOfMills(3, 'w'));
        assertEquals(2, cgf.numberOfMills(5, 's'));

    }
}
