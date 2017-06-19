/**
 * Muehlegame
 * Copyright (c) 2015, Thomas Ammann, Johannes Finckh
 *
 * @author Thomas Amann, Johannes Finckh
 * @version 1.0
 */

package controller;

import observer.IObserver;

public interface IController {
    /**
     * Set the stones
     * @param Node
     * @param color
     * @return Boolean state of succes
     * */
    boolean setStone(int vertex, char color);
    boolean setStone(int vertex);

    /**
     * move the stones
     * @param Startnode, Endnode
     * @return Boolean state of succes
     * */
    boolean moveStone(int start, int end);

    /**
     * Remove the stones
     * @param Node
     * @return Boolean state of succes
     * */
    boolean removeStone(int vertex);

    /**
     * Retuns the number of closed mills around one vertex
     * @param Node
     * @return Int number of closed mills*/
    int getAnzClosedMills(int vertex);

    /**
     * Returns if game Ended ( One Player less than three Stones)
     * @return boolean true if end
     * */
    boolean gameEnded();

    /**
     * Returns the winning Player
     * @return String name of the winner
     * */
    String getWinningPlayer();

    /**
     * function to delete Stones if one player gets a mill
     * @param node to delete
     * */
    void millDeleteStone(int vertex);

    /**
     * returns the number of stones to delete, helps to delete stones if mills closed
     * @return int number of closed stones
     * */
    int getCurrentStonesToDelete();

    /**
     * get status if gamefield is filled with stones or not
     * @return true if gamefield filled
     * */
    boolean requireInitial();

    /**
     * Retuns the name of the current player
     * @return String playername
     * */
    String getCurrentPlayerName();

    /**
     * set the controler to the next player
     * */
    void setNextPlayer();

    /**
     * Returns the color of the current player
     * @return char color
     * */
    char getCurrentPlayerColor();

    /**
     * Returns the color of the vertex
     * @return char color
     * */
    char getVertexColor(int vertex);

    /**
     * Register an observer
     * @param IObserver
     * */
    void registerObserver(IObserver observer);

    /**
     * unregister an observer
     * @param Iobserver
     * */
    void unregisterObserver(IObserver observer);

    /**
     * updates the Observers
     * @param vertex
     * */
    void updateObservers(int vertex);

    /**
     * moves a Stone, controler saves the param if it is the startvertex. To move, it is requiered to call move 2 times
     * ( moveStone(startNode); moveStone(endNode);
     * @param int stone
     * @return boolean state of succes
     * */
    boolean moveStone(int vertex);

    /**
     * returns the stones already set by player1
     * @return int already setted Stones
     * */
    int getConsumedStonesPlayer1();

    /**
     * returns the stones already set by player2
     * @return int already setted Stones
     * */
    int getConsumedStonesPlayer2();

    /**
     * TODO: Find better way
     */
    String getGamefieldString();
}
