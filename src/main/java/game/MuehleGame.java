/**
 * Muehlegame
 * Copyright (c) 2015, Thomas Ammann, Johannes Finckh
 *
 * @author Thomas Amann, Johannes Finckh
 * @version 1.0
 */

package game;

import com.google.inject.Guice;
import com.google.inject.Injector;
import controller.IController;
import view.tui.Tui;
import view.gui.Gui;

import java.util.Scanner;


public final class MuehleGame {


    private static Tui tui;
    private static Gui gui;
    private static IController controller;
    private static Scanner sc;
    private static MuehleGame instance = null;

    private MuehleGame () {
        Injector injector = Guice.createInjector(new MuehleModule());
        controller = injector.getInstance(IController.class);
        gui = new Gui(this.controller);
        tui = new Tui(this.controller);
        controller.startServer();
    }

    public static void main(String[] args) {
        MuehleGame game = getInstance();
        gui.beginSetPlayerInfo();
        sc = new Scanner(System.in);

        while (sc.hasNextInt()) {
            game.getTui().handleInput(sc.nextInt());
        }
        sc.close();
    }

    public static MuehleGame getInstance() {
        if(instance == null) {
            instance = new MuehleGame();
        }
        return instance;
    }

    public Tui getTui() {
        return this.tui;
    }

}
