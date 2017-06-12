/**
 * Muehlegame
 * Copyright (c) 2015, Thomas Ammann, Johannes Finckh
 *
 * @author Thomas Amann, Johannes Finckh
 * @version 1.0
 */

package controller.impl;

import akka.actor.ActorRef;
import akka.pattern.Patterns;
import akka.util.Timeout;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import controller.IGamefieldGraphAdapter;
import messages.*;
import scala.concurrent.Await;
import scala.concurrent.Future;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class GamefieldAdapter implements IGamefieldGraphAdapter {

    private static final int MAXVERTEX = 24;

    @Inject @Named("millsActor")
    private ActorRef millsActor;

    @Inject @Named("gamefieldActor")
    private ActorRef gamefieldActor;

    @Override
    public boolean setStone(int vertex, char color) {
        SetStoneVertexRequest request = new SetStoneVertexRequest(vertex, color);
        BoolAnswer answer = (BoolAnswer) ask(gamefieldActor, request);
        return answer.getContent();
    }

    @Override
    public boolean removeStone(int vertex) {
        return setStone(vertex, 'n');
    }

    @Override
    public char getColor(int vertex) {
        GetStoneColorMessage request = new GetStoneColorMessage(vertex);
        GetStoneColorMessage answer = (GetStoneColorMessage) ask(gamefieldActor, request);
        return answer.getContent();
    }

    @Override
    public boolean move(int startVertex, int endVertex, char color) {
        MsgMoveStone request = new MsgMoveStone(startVertex, endVertex, color);
        BoolAnswer answer = (BoolAnswer) ask(gamefieldActor, request);
        return answer.getContent();
    }

    @Override
    public int numberOfMills(int vertex, char color) {
        if ((vertex < 1) || (vertex > MAXVERTEX) || ((color != 'w') && (color != 's'))) {
            return 0;
        }
        GetMillsRequest request = new GetMillsRequest(vertex);
        GetMillsAnswer answer = (GetMillsAnswer) ask(millsActor, request);
        List<Integer> mill1 = answer.getMill1();
        List<Integer> mill2 = answer.getMill2();
        int numberMills = 0;

        if ((getColor(mill1.get(0)) == getColor(mill1.get(1))) && (color == getColor(mill1.get(0)))) {
            numberMills++;
        }

        if ((getColor(mill2.get(0)) == getColor(mill2.get(1))) && (color == getColor(mill2.get(0)))) {
            numberMills++;
        }


        return numberMills;
    }

    private Object ask(ActorRef actorRef, Object request) {
        final Timeout timeout = new Timeout(5, TimeUnit.SECONDS);
        final Future<Object> future = Patterns.ask(actorRef, request, timeout);
        try {
            return Await.result(future, timeout.duration());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
}