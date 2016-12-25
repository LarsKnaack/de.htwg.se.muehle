/**
 * Muehlegame
 * Copyright (c) 2015, Thomas Ammann, Johannes Finckh
 *
 * @author Thomas Amann, Johannes Finckh
 * @version 1.0
 */

package controller.impl;

import com.google.inject.Inject;
import controller.IGamefieldGraphAdapter;
import model.IGamefieldGraph;
import model.impl.Mills;

import java.util.List;

public class GamefieldAdapter implements IGamefieldGraphAdapter {

    private static final int MAXVERTEX = 24;
    private Mills mill;
    private IGamefieldGraph field;

    @Inject
    public GamefieldAdapter(IGamefieldGraph pField) {
        field = pField;
        mill = new Mills();
    }

    @Override
    public boolean setStone(int v, char w) {
        if (field.getStoneColorVertex(v) != 'n') {
            return false;
        }
        return field.setStoneVertex(v, w);
    }

    @Override
    public boolean removeStone(int v) {
        char stoneColor = field.getStoneColorVertex(v);
        if ((field.getStoneColorVertex(v) == 'n')
                || (numberOfMills(v, stoneColor) > 0)) {
            return false;
        }
        return field.setStoneVertex(v, 'n');
    }

    @Override
    public char getColor(int v) {
        return field.getStoneColorVertex(v);
    }

    @Override
    public boolean move(int startVertex, int endVertex, char w) {
        if (w == 'n') {
            return false;
        }
        if ((w != 'w') && (w != 's')) {
            return false;
        }
        if ('n' != field.getStoneColorVertex(endVertex)) {
            return false;
        }

        if (w != field.getStoneColorVertex(startVertex)) {
            return false;
        }
        List<Integer> temp1 = field.getAdjacencyList(startVertex);
        if (!temp1.contains(endVertex - 1)) {
            return false;
        }

        field.setStoneVertex(startVertex, 'n');
        field.setStoneVertex(endVertex, w);

        return true;
    }

    @Override
    public int numberOfMills(int v, char c) {
        if ((v < 1) || (v > MAXVERTEX) || ((c != 'w') && (c != 's'))) {
            return 0;
        }
        List<Integer> mill1 = mill.getMill1(v);
        List<Integer> mill2 = mill.getMill2(v);
        int numberMills = 0;

        if ((getColor(mill1.get(0)) == getColor(mill1.get(1))) && (c == getColor(mill1.get(0)))) {
            numberMills++;
        }

        if ((getColor(mill2.get(0)) == getColor(mill2.get(1))) && (c == getColor(mill2.get(0)))) {
            numberMills++;
        }


        return numberMills;
    }

}