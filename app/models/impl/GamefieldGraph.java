/**
 * Muehlegame
 * Copyright (c) 2015, Thomas Ammann, Johannes Finckh
 *
 * @author Thomas Amann, Johannes Finckh
 * @version 1.0
 */

package models.impl;

import akka.actor.UntypedAbstractActor;
import com.google.inject.Inject;
import messages.*;
import models.IGamefieldGraph;
import persistence.dao.IGamefieldDAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class GamefieldGraph extends UntypedAbstractActor implements IGamefieldGraph {

    private static final int NUMBERVERTEX = 24;
    private List<List<Integer>> adjacencyList;
    private vertex vertexes[];

    @Inject
    private IGamefieldDAO gamefieldDAO;

    private String id;

    public GamefieldGraph() {
        init();

        System.out.println("GamefieldID: " + id);
    }

    private void init() {
        adjacencyList = new ArrayList<>(NUMBERVERTEX);
        vertexes = new vertex[NUMBERVERTEX];
        for (int i = 0; i < NUMBERVERTEX; i++) {
            adjacencyList.add(new LinkedList<>());
        }
        createEdges();

        createVertexes();

        id = UUID.randomUUID().toString();
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
    public void onReceive(Object message) throws Throwable {
        boolean answer = false;
        if( message instanceof GetStoneColorMessage) {
            char color = getStoneColorVertex(((GetStoneColorMessage) message).getVertex());
            getSender().tell(new GetStoneColorMessage(color), getSelf());
        } else if(message instanceof SetStoneVertexRequest) {
            answer = receiveMsgSetStoneVertex((SetStoneVertexRequest) message);
            getSender().tell(new BoolAnswer(answer), getSelf());
            updateDB();
        } else if( message instanceof MsgMoveStone) {
            answer = receiveMsgMoveStone((MsgMoveStone) message);
            getSender().tell(new BoolAnswer(answer), getSelf());
            updateDB();
        } else if( message instanceof ResetRequest) {
            if(gamefieldDAO.containsGamefieldGraphByID(getId())) {
                gamefieldDAO.deleteGamefieldByID(getId());
            }
            init();
            updateDB();
            getSender().tell(new BoolAnswer(true), getSelf());
        }
    }

    private void updateDB() {
        if(gamefieldDAO.containsGamefieldGraphByID(getId())) {
            gamefieldDAO.deleteGamefieldByID(getId());
        }
        gamefieldDAO.saveGameField(this);
    }


    private boolean receiveMsgMoveStone(MsgMoveStone message) {
        //TODO: Create Enum for Colors to avoid checking
        /*if (color == 'n' || (color != 'w') && (color != 's')) {
            return false;
        }*/
        if(getStoneColorVertex(message.getEndVertex()) != 'n' ||
                getStoneColorVertex(message.getStartVertex()) != message.getColor()) {
            return false;
        }
        List<Integer> adjacencyList = getAdjacencyList(message.getStartVertex());
        if(!adjacencyList.contains(message.getEndVertex() - 1)) {
            return false;
        }
        return setStoneVertex(message.getStartVertex(), 'n') &&
                setStoneVertex(message.getEndVertex(), message.getColor());
    }

    private boolean receiveMsgSetStoneVertex(SetStoneVertexRequest message) {
        boolean requirement;
        if(message.getColor() == 'n') { //remove stone
            requirement = getStoneColorVertex(message.getVertex()) != 'n';
        } else {
            requirement = getStoneColorVertex(message.getVertex()) == 'n';
        }
        if(requirement) {
            requirement = setStoneVertex(message.getVertex(), message.getColor());
        }
        return requirement;
    }

    class vertex {
        private char color;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}