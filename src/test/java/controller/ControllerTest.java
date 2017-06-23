package controller;

import controller.impl.Controller;
import controller.impl.GamefieldAdapter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ControllerTest {
    Controller control;

    @Before
    public void setUp() {
        control = new Controller(new GamefieldAdapter());
    }

    @Test
    public void testSetStone() {
        assertEquals(true, control.setStone(1));
        assertEquals(false, control.setStone(0));
        assertEquals(false, control.setStone(26));
        control.setNextPlayer();
        assertEquals(true, control.setStone(3));
        assertEquals(false, control.setStone(1));
    }

    @Test
    public void testgetSettedStonesPlayer1() {
        assertEquals(0, control.getConsumedStonesPlayer1());
    }

    @Test
    public void testgetSettedStonesPlayer2() {
        control.setStone(3);
        control.setStone(2);
        assertEquals(1, control.getConsumedStonesPlayer2());
    }

    @Test
    public void testMoveStone() {
        control.setStone(2);
        assertFalse(control.moveStone(2));
        assertFalse(control.moveStone(23));
        control.setStone(5);
        assertTrue(control.moveStone(2));
        assertFalse(control.moveStone(2));
        assertTrue(control.moveStone(3));
        assertTrue(control.moveStone(5));
        assertFalse(control.moveStone(2));
    }

    @Test
    public void testRemoveStone() {
        control.setStone(1);
        assertEquals(true, control.removeStone(1));
        assertEquals(false, control.removeStone(0));
        assertEquals(false, control.removeStone(3));
        control.setNextPlayer();
        assertEquals(false, control.removeStone(1));
        control.setStone(2);
        control.setStone(3);
        control.setStone(6);
        control.setNextPlayer();
        assertEquals(false, control.removeStone(2));
        control.setNextPlayer();
        assertEquals(false, control.removeStone(3));
        assertEquals(true, control.removeStone(6));

    }

    @Test
    public void testGetAnzClosedMills() {
        control.setStone(1);
        control.setStone(2);
        control.setStone(3); //1Mill
        control.setStone(6);
        control.setStone(8); //0Mill
        control.setStone(4);
        control.setStone(5);
        control.setStone(12);
        control.setStone(20);
        assertEquals(0, control.getAnzClosedMills(6));
        assertEquals(0, control.getAnzClosedMills(8));
        assertEquals(0, control.getAnzClosedMills(2));
        assertEquals(0, control.getAnzClosedMills(3));
        assertEquals(0, control.getAnzClosedMills(0));
        assertEquals(0, control.getAnzClosedMills(29));
    }

    @Test
    public void testrequireInitial() {
        assertEquals(true, control.requireInitial());
        control.setStone(1);
        control.setStone(3);
        control.setStone(5);
        control.setStone(7);
        control.setStone(9);
        control.setStone(11);
        control.setStone(13);
        control.setStone(15);
        control.setNextPlayer();
        control.setStone(2);
        control.setStone(4);
        control.setStone(6);
        control.setStone(8);
        control.setStone(10);
        control.setStone(12);
        control.setStone(14);
        control.setStone(16);
        assertEquals(true, control.requireInitial());
        control.setStone(19);
        control.setNextPlayer();
        control.setStone(18);
        assertEquals(false, control.requireInitial());

    }

    @Test
    public void testgetCurrentPlayerName() {
        assertEquals("Player1", control.getCurrentPlayerName());
        control.setNextPlayer();
        assertEquals("Player2", control.getCurrentPlayerName());
        control.setNextPlayer();
        assertEquals("Player1", control.getCurrentPlayerName());
    }

    @Test
    public void testgameEnded() {
        assertEquals(false, control.gameEnded());

        control.setStone(1);
        control.setStone(2);
        control.setStone(3);
        control.setStone(4);
        control.setStone(5);
        control.setStone(6);
        control.setStone(7);
        control.setStone(8);
        control.setStone(9);
        control.setStone(10);
        control.setStone(11);
        control.setStone(12);
        control.setStone(13);
        control.setStone(14);
        control.setStone(15);
        control.setStone(16);
        control.setStone(17);
        control.setStone(19);

        control.removeStone(2);
        control.removeStone(4);
        control.removeStone(6);
        control.removeStone(8);
        control.removeStone(10);
        control.removeStone(12);

        assertEquals(false, control.gameEnded());
        control.removeStone(14);
        assertEquals(true, control.gameEnded());

        control = null;
        control = new Controller(new GamefieldAdapter());

        control.setStone(1);
        control.setStone(2);
        control.setStone(3);
        control.setStone(4);
        control.setStone(5);
        control.setStone(6);
        control.setStone(7);
        control.setStone(8);
        control.setStone(9);
        control.setStone(10);
        control.setStone(11);
        control.setStone(12);
        control.setStone(13);
        control.setStone(14);
        control.setStone(15);
        control.setStone(16);
        control.setStone(17);
        control.setStone(19);
        control.setNextPlayer();
        control.removeStone(1);
        control.removeStone(3);
        control.removeStone(5);
        control.removeStone(7);
        control.removeStone(9);
        control.removeStone(11);

        assertEquals(false, control.gameEnded());
        control.removeStone(13);
        assertEquals(true, control.gameEnded());
    }

    @Test
    public void testgetWinningPlayer() {
        assertEquals("", control.getWinningPlayer());
        control.setStone(1);
        control.setStone(3);
        control.setStone(5);
        control.setStone(7);
        control.setStone(9);
        control.setStone(11);
        control.setStone(13);
        control.setStone(15);
        control.setStone(17);
        control.setNextPlayer();
        control.setStone(2);
        control.setStone(4);
        control.setStone(6);
        control.setStone(8);
        control.setStone(10);
        control.setStone(12);
        control.setStone(14);
        control.setStone(16);
        control.setStone(19);

        control.removeStone(1);
        control.removeStone(3);
        control.removeStone(5);
        control.removeStone(7);
        control.removeStone(9);
        control.removeStone(11);
        control.removeStone(13);
        control.gameEnded();
        assertEquals("", control.getWinningPlayer());

        control = null;
        control = new Controller(new GamefieldAdapter());
        control.setStone(1);
        control.setStone(3);
        control.setStone(5);
        control.setStone(7);
        control.setStone(9);
        control.setStone(11);
        control.setStone(13);
        control.setStone(15);
        control.setStone(17);
        control.setNextPlayer();
        control.setStone(2);
        control.setStone(4);
        control.setStone(6);
        control.setStone(8);
        control.setStone(10);
        control.setStone(12);
        control.setStone(14);
        control.setStone(16);
        control.setStone(19);
        control.setNextPlayer();
        control.removeStone(2);
        control.removeStone(4);
        control.removeStone(6);
        control.removeStone(8);
        control.removeStone(10);
        control.removeStone(12);
        control.removeStone(14);
        control.gameEnded();
        assertEquals("", control.getWinningPlayer());
    }

    @Test
    public void testmillDeleteStone() {
        control.millDeleteStone(2);
        control.setStone(2);
        control.millDeleteStone(2);
    }

    @Test
    public void testCurrentStonesToDelete() {
        assertEquals(0, control.getCurrentStonesToDelete());
    }

}
