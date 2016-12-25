package player;

import model.impl.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class PlayerTest {
    Player player1;
    Player player2;

    @Before
    public void setUp() {
        player1 = new Player("Hans", 's');
        player2 = new Player("Peter", 'w');
    }

    @Test
    public void testCreation() {
        assertNotEquals(null, player1);
        assertNotEquals(null, player2);
    }

    @Test
    public void testGetColor() {
        assertEquals('s', player1.getColor());
        assertEquals('w', player2.getColor());
    }

    @Test
    public void testGetName() {
        assertEquals("Hans", player1.getName());
        assertEquals("Peter", player2.getName());
    }
}
