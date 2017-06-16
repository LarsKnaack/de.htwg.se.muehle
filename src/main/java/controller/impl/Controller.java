/**
 * Muehlegame
 * Copyright (c) 2015, Thomas Ammann, Johannes Finckh
 *
 * @author Thomas Amann, Johannes Finckh
 * @version 1.0
 */

package controller.impl;


import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import com.google.inject.Inject;
import controller.IController;
import controller.IGamefieldGraphAdapter;
import model.IPlayer;
import model.impl.Player;
import observer.IObservable;
import observer.IObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionStage;

import static akka.http.javadsl.server.PathMatchers.integerSegment;

public class Controller extends AllDirectives implements IController, IObservable {

    private static final int MINSTONES = 3;
    private static final int ANZSTONESGES = 18;
    private static final int STONESPLAYER = 9;
    private static final int SLEEPTIME = 5500;
    private final List<IObserver> observers = new ArrayList<IObserver>();
    private IGamefieldGraphAdapter gamefield;
    private IPlayer player1, player2, current;
    private int stonesPlayer1, settedStonesPlayer1;
    private int stonesPlayer2, settedStonesPlayer2;
    private int settedStones;
    private String playerWon;
    private int currentStoneToDelete;
    private int selected;
    private volatile Object shutdownSwitch;
    private Logger LOGGER;

    @Inject
    public Controller(IGamefieldGraphAdapter pGamefield) {
        LOGGER = LoggerFactory.getLogger(this.getClass());
        shutdownSwitch = new Object();
        this.gamefield = pGamefield;

        this.player1 = new Player("Player1", 'w');
        this.player2 = new Player("Player2", 's');
        this.stonesPlayer1 = STONESPLAYER;
        this.stonesPlayer2 = STONESPLAYER;
        this.current = this.player1;
        this.settedStones = 0;
        this.playerWon = "";
        this.currentStoneToDelete = 0;
        this.selected = 0;
        this.settedStonesPlayer1 = 0;
        this.settedStonesPlayer2 = 0;
    }

    public void startServer() {
        ActorSystem actorSystem = ActorSystem.create("routes");
        Http http = Http.get(actorSystem);
        final ActorMaterializer materializer = ActorMaterializer.create(actorSystem);
        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = this.createRoute().flow(actorSystem, materializer);
        final CompletionStage<ServerBinding> binding = http.bindAndHandle(routeFlow,
                ConnectHttp.toHost("localhost", 8080), materializer);
        LOGGER.info("Server online at http://localhost:8080/\n");
        System.out.println("Server online at http://localhost:8080/\n");
        Thread runner = new Thread(() -> {
            try {
                synchronized (shutdownSwitch) {
                    shutdownSwitch.wait();
                }
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage());
            }

            binding.thenCompose(ServerBinding::unbind).thenAccept(unbound -> actorSystem.terminate());
            LOGGER.info("REST Server shutdown");
        });
        runner.start();
    }

    private Route createRoute() {
        Route route =
                route(
                        pathPrefix("set_stone", () ->
                                route(
                                        path(integerSegment(), (i) ->
                                                complete((setStone(i) ? StatusCodes.OK : StatusCodes.METHOD_NOT_ALLOWED))
                                        )
                                )
                        ),
                        path("q", () -> get(() -> {
                            LOGGER.info("q request");

                            synchronized (shutdownSwitch) {
                                shutdownSwitch.notify();
                            }
                            endThread ende = new endThread();
                            ende.start();
                            return complete("<h1>quitGame</h1>");
                        })),
                        path("", () -> get(() -> {
                            return complete("<h1>Hello</h1>");
                        }))
                );
        return route;
    }

    @Override
    public boolean setStone(int vertex) {
        boolean temp = gamefield.setStone(vertex, getCurrentPlayerColor());

        if (temp) {
            settedStones++;
            this.incSettedStonesPlayer();
            this.getAnzClosedMills(vertex);
            this.updateObservers(vertex);
        }
        return temp;
    }

    @Override
    public int getSettedStonesPlayer1() {
        return this.settedStonesPlayer1;
    }

    @Override
    public int getSettedStonesPlayer2() {
        return this.settedStonesPlayer2;
    }

    private void incSettedStonesPlayer() {
        if (current.equals(player1)) {
            this.settedStonesPlayer1++;
        } else {
            this.settedStonesPlayer2++;
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
    public void millDeleteStone(int vertex) {
        if (this.removeStone(vertex)) {
            this.currentStoneToDelete--;
        }
        this.updateObservers(vertex);

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

    public Map getVertexMap() {
        Map<Integer, Character> map = new HashMap();

        for (int i = 1; i <= 24; ++i) {
            map.put(i, this.getVertexColor(i));
        }

        return map;
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
