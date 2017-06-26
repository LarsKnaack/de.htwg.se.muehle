/**
 * Muehlegame
 * Copyright (c) 2015, Thomas Ammann, Johannes Finckh
 *
 * @author Thomas Amann, Johannes Finckh
 * @version 1.0
 */

package models;

import java.util.List;

public interface IGamefieldGraph {

    /**
     * set a stone color on a Node
     * @param vertex: Node
     * @param color: the color to set
     * @return boolean: Boolean state of success
     * */
    boolean setStoneVertex(int vertex, char color);

    /**
     * Get the color of the Node
     * @param Node
     * @return char for the color of the Node
     * */
    char getStoneColorVertex(int vertex);

    /**
     * get a List of all neighbor nodes of a node
     * @param v is the Node
     * return List of integer with are next to v
     * */
    List<Integer> getAdjacencyList(int v);
}