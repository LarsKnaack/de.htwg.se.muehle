/**
 * Muehlegame
 * Copyright (c) 2015, Thomas Ammann, Johannes Finckh
 *
 * @author Thomas Amann, Johannes Finckh
 * @version 1.0
 */

package controllers.impl;

import com.google.inject.Inject;
import controllers.IController;
import controllers.IGamefieldGraphAdapter;
import models.IPlayer;
import models.impl.Player;
import observer.IObservable;
import observer.IObserver;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Controller implements IController, IObservable {
    private static final int MINSTONES = 3;
    private static final int ANZSTONESGES = 18;
    private static final int STONESPLAYER = 9;
    private static final int SLEEPTIME = 5500;
    private final List<IObserver> observers = new ArrayList<IObserver>();
    private IGamefieldGraphAdapter gamefield;
    private IPlayer player1, player2, current;
    private int stonesPlayer1, consumedStonesPlayer1;
    private int stonesPlayer2, consumedStonesPlayer2;
    private int settedStones;
    private String playerWon;
    private int currentStoneToDelete;
    private int selected;
    private volatile Object shutdownSwitch;
    private Logger LOGGER;


    @Inject
    public Controller(IGamefieldGraphAdapter pGamefield) {
        init();
        this.gamefield = pGamefield;
    }

    private void init() {
        this.player1 = new Player("Player1", 'w');
        this.player2 = new Player("Player2", 's');
        this.stonesPlayer1 = STONESPLAYER;
        this.stonesPlayer2 = STONESPLAYER;
        this.current = this.player1;
        this.settedStones = 0;
        this.playerWon = "";
        this.currentStoneToDelete = 0;
        this.selected = 0;
        this.consumedStonesPlayer1 = 0;
        this.consumedStonesPlayer2 = 0;
    }

    @Override
    public boolean setStone(int vertex, char color) {
        boolean temp = gamefield.setStone(vertex, color);

        if (temp) {
            settedStones++;
            this.incSettedStonesPlayer();
            this.getAnzClosedMills(vertex);
            this.updateObservers(vertex);
        }

        return temp;
    }

    @Override
    public boolean setStone(int vertex) {
        return setStone(vertex, getCurrentPlayerColor());
    }

    @Override
    public int getConsumedStonesPlayer1() {
        return this.consumedStonesPlayer1;
    }

    @Override
    public int getConsumedStonesPlayer2() {
        return this.consumedStonesPlayer2;
    }

    private void incSettedStonesPlayer() {
        if (current.equals(player1)) {
            this.consumedStonesPlayer1++;
        } else {
            this.consumedStonesPlayer2++;
        }
    }

    @Override
    public char getVertexColor(int vertex) {
        return gamefield.getColor(vertex);
    }

    @Override
    public boolean moveStone(int vertex) {
        if (selected == 0) {
            if ((this.getVertexColor(vertex) == 'n') || (this.getVertexColor(vertex) != current.getColor())) {
                return false;
            }
            this.selected = vertex;
            return true;
        } else if (selected == vertex) {
            return false;
        } else {
            boolean temp = this.moveStone(selected, vertex);
            this.selected = 0;
            return temp;
        }
    }

    @Override
    public boolean moveStone(int start, int end) {
        boolean temp = gamefield.move(start, end, this.getCurrentPlayerColor());
        if (temp) {
            this.getAnzClosedMills(end);
            this.updateObservers(end);
        }
        return temp;
    }

    @Override
    public boolean removeStone(int vertex) {
        char colorVertex = gamefield.getColor(vertex);
        if (colorVertex == current.getColor()) {
            return false;
        }
        boolean temp = gamefield.removeStone(vertex);
        if (temp) {
            this.decStonesPlayer(colorVertex);
        }
        return temp;
    }

    private void decStonesPlayer(char color) {
        if (color == 'w') {
            this.stonesPlayer2--;
        } else if (color == 's') {
            this.stonesPlayer1--;
        }
    }

    @Override
    public int getAnzClosedMills(int vertex) {

        this.currentStoneToDelete = gamefield.numberOfMills(vertex, this.getCurrentPlayerColor());
        return this.currentStoneToDelete;
    }

    @Override
    public boolean gameEnded() {

        if (this.stonesPlayer1 < MINSTONES) {
            this.playerWon = this.player2.getName();
            return true;
        } else if (this.stonesPlayer2 < MINSTONES) {
            this.playerWon = this.player1.getName();
            return true;
        }
        return false;
    }

    @Override
    public String getWinningPlayer() {
        return this.playerWon;
    }

    @Override
    public boolean requireInitial() {
        return (settedStones != ANZSTONESGES);
    }

    @Override
    public String getCurrentPlayerName() {
        return current.getName();
    }

    @Override
    public void setNextPlayer() {
        if (current.equals(player1)) {
            current = player2;
        } else {
            current = player1;
        }

    }

    @Override
    public char getCurrentPlayerColor() {
        return current.getColor();
    }

    @Override
    public boolean millDeleteStone(int vertex) {
        boolean result = removeStone(vertex);
        if (result) {
            this.currentStoneToDelete--;
        }
        this.updateObservers(vertex);
        return result;
    }


    @Override
    public int getCurrentStonesToDelete() {
        return this.currentStoneToDelete;
    }

    @Override
    public void registerObserver(IObserver observer) {
        observers.add(observer);

    }

    @Override
    public void unregisterObserver(IObserver observer) {
        observers.remove(observer);

    }

    @Override
    public void updateObservers(int vertex) {
        if (this.currentStoneToDelete == 0) {
            this.setNextPlayer();
        }
        for (IObserver observer : observers) {
            observer.update(current, this.currentStoneToDelete, this.gameEnded());
        }
        if (this.gameEnded()) {
            endThread ende = new endThread();
            ende.start();
        }
    }

    @Override
    public String getGamefieldString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 24; ++i) {
            sb.append(this.getVertexColor(i));
        }
        return sb.toString();
    }

    @Override
    public boolean resetGame() {
        init();
        return gamefield.reset();
    }

    class endThread extends Thread {
        public void run() {
            try {
                sleep(SLEEPTIME);
            } catch (InterruptedException e) {

            }
            System.exit(0);
        }
    }

}
