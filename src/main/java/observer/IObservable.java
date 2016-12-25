/**
 * Muehlegame
 * Copyright (c) 2015, Thomas Ammann, Johannes Finckh
 *
 * @author Thomas Amann, Johannes Finckh
 * @version 1.0
 */

package observer;

public interface IObservable {

    /**
     * register an observer
     * @param Iobserver
     * */
    void registerObserver(IObserver observer);

    /**
     * unregister an observer
     * @param Iobserver
     * */
    void unregisterObserver(IObserver observer);

    /**
     * update the observers
     * @param int vertex
     * */
    void updateObservers(int vertex);
}
