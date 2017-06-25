/**
 * Muehlegame
 * Copyright (c) 2015, Thomas Ammann, Johannes Finckh
 *
 * @author Thomas Amann, Johannes Finckh
 * @version 1.0
 */

package observer;

import models.IPlayer;

public interface IObserver {

    /**
     * updatefunction in the observers
     * @param Iplayer the current player, int the current number of mills, boolean the state of the game(ended or not)
     *
     * */
    void update(IPlayer currentPlayer, int anzMills, boolean gameEnded);

}
