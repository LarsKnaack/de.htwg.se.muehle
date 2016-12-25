package gamefield;

import model.impl.GamefieldGraph;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class GamefieldGraphtest {

    GamefieldGraph graph;

    @Before
    public void setUp() {
        graph = new GamefieldGraph();
    }

    @Test
    public void testCreation() {
        assertNotEquals(null, graph);
    }

    @Test
    public void testgetAdjacencyList() {
        ArrayList<Integer> test = new ArrayList<Integer>();
        test.add(1);
        test.add(3);
        assertEquals(graph.getAdjacencyList(3), test);
        assertEquals(graph.getAdjacencyList(25), new ArrayList<Integer>());
        assertEquals(graph.getAdjacencyList(0), new ArrayList<Integer>());
    }

    @Test
    public void testsetStoneVertex() {
        assertEquals(graph.setStoneVertex(1, 's'), true);
        assertEquals(graph.setStoneVertex(0, 's'), false);
        assertEquals(graph.setStoneVertex(26, 'w'), false);
        assertEquals(graph.setStoneVertex(5, 'y'), false);
        assertEquals(graph.setStoneVertex(19, 'n'), true);
    }

    @Test
    public void testgetStoneColorVertex() {
        graph.setStoneVertex(1, 'w');
        graph.setStoneVertex(2, 's');
        assertEquals(graph.getStoneColorVertex(1), 'w');
        assertEquals(graph.getStoneColorVertex(2), 's');
        assertEquals(graph.getStoneColorVertex(17), 'n');
        assertEquals(graph.getStoneColorVertex(0), 'n');
        assertEquals(graph.getStoneColorVertex(28), 'n');

    }
}
