/**
 * Muehlegame
 * Copyright (c) 2015, Thomas Ammann, Johannes Finckh
 *
 * @author Thomas Amann, Johannes Finckh
 * @version 1.0
 */

package model.impl;

import actors.ModelUpdate;
import com.google.inject.Inject;
import model.AbstractModel;
import model.IGamefieldGraph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GamefieldGraph extends AbstractModel implements IGamefieldGraph {

    private static final int NUMBERVERTEX = 24;
    private List<List<Integer>> adjacencyList;
    private vertex vertexes[];

    @Inject
    public GamefieldGraph() {
        this.type = String.class;
        adjacencyList = new ArrayList<>(NUMBERVERTEX);
        for (int i = 0; i < NUMBERVERTEX; i++) {
            adjacencyList.add(new LinkedList<>());
        }
        createEdges();

        vertexes = new vertex[NUMBERVERTEX];
        createVertexes();
    }

    private void createVertexes() {
        for (int i = 0; i < NUMBERVERTEX; i++) {
            vertexes[i] = new vertex();
            vertexes[i].color = 'n';
        }
    }

    public List<Integer> getAdjacencyList(int vertex) {
        final int v = (vertex - 1);
        if ((v < 0) || (v >= NUMBERVERTEX)) {
            return new ArrayList<>();
        }
        return adjacencyList.get(v);
    }

    public boolean setStoneVertex(int v, char color) {
        final int vertex = v - 1;
        if ((vertex < 0) || (vertex >= NUMBERVERTEX)) {
            return false;
        }
        if ((color != 'n') && (color != 'w') && (color != 's')) {
            return false;
        }

        vertexes[vertex].color = color;
        return true;
    }

    public char getStoneColorVertex(int v) {
        final int vertex = v - 1;
        if ((vertex < 0) || (vertex >= NUMBERVERTEX)) {
            return 'n';
        }
        return vertexes[vertex].color;
    }

    private void createEdges() {
        try {
            InputStream stream = getClass().getResourceAsStream("/GamefieldEdges.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(stream));
            String zeile = null;
            while ((zeile = in.readLine()) != null) {

                String splitresult[] = zeile.split(" ");
                int temp1 = Integer.parseInt(splitresult[0]);
                int temp2 = Integer.parseInt(splitresult[1]);
                adjacencyList.get(temp1).add(temp2);
            }
            in.close();
        } catch (IOException e) {
            System.exit(1);
        }
    }

    @Override
    protected void handleMessage(ModelUpdate update) {
        update.getGamefieldGraph();
        //Copy Values
    }


    class vertex {
        private char color;
    }

}