/**
 * Muehlegame
 * Copyright (c) 2015, Thomas Ammann, Johannes Finckh
 *
 * @author Thomas Amann, Johannes Finckh
 * @version 1.0
 */

package controllers;

public interface IGamefieldGraphAdapter {

    /**
     * Set the stones
     *
     * @param Node
     * @return Boolean state of success
     * */
    boolean setStone(int v, char w);

    /**
     * remove the stone
     *
     * @param Node
     * @return Boolean state of success
     * */
    boolean removeStone(int v);

    /**
     * Get the color of the Node
     *
     * @param Node
     * @return char for the color of the Node
     * */
    char getColor(int v);

    /**
     * Move the stone from a Node to another Node
     *
     * @param StartNode
     *            , EndNode
     * @return Boolean state of success
     * */
    boolean move(int startVertex, int endVertex, char w);

    /**
     * Get the number of mills at this Node
     *
     * @param v
     *            for the Node
     * @param c
     *            for the Color of the Node
     * @return number of closed mills (0,1,2)
     * */
    int numberOfMills(int v, char c);


    /**
     * get the id of the instance
     * @return id
     */
    String getId();

    /**
     * set the id of the instance
     * @param id
     */
    void setId(String id);
}