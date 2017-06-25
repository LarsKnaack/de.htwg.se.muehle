/**
 * Muehlegame
 * Copyright (c) 2015, Thomas Ammann, Johannes Finckh
 *
 * @author Thomas Amann, Johannes Finckh
 * @version 1.0
 */

package models;


public interface IPlayer {

    /**
     * returns the name of the player
     * @return String name
     * */
    String getName();

    /**
     * returns the color of the player
     * @return char color
     * */
    char getColor();

}