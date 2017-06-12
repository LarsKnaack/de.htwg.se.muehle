/**
 * Muehlegame
 * Copyright (c) 2015, Thomas Ammann, Johannes Finckh
 *
 * @author Thomas Amann, Johannes Finckh
 * @version 1.0
 */

package model.impl;


import model.IPlayer;

public class Player implements IPlayer {

    private String name;
    private char color;

    public Player(String name, char color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return this.name;
    }

    public char getColor() {
        return this.color;
    }

}