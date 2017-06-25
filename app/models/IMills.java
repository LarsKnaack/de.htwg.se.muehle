/**
 * Muehlegame
 * Copyright (c) 2015, Thomas Ammann, Johannes Finckh
 *
 * @author Thomas Amann, Johannes Finckh
 * @version 1.0
 */

package models;

import java.util.List;

public interface IMills {

    /**
     * Returns one of the two possible mills at vertex v
     * @param int vertex
     * @return list<integer> 2 vertexes
     * */
    List<Integer> getMill1(int v);

    /**
     * Returns one of the two possible mills at vertex v
     * @param int vertex
     * @return list<integer> 2 vertexes
     * */
    List<Integer> getMill2(int v);

}