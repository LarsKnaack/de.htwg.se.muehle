/**
 * Muehlegame
 * Copyright (c) 2015, Thomas Ammann, Johannes Finckh
 *
 * @author Thomas Amann, Johannes Finckh
 * @version 1.0
 */

package views.gui;

import controllers.IController;
import models.IPlayer;
import observer.IObserver;

import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame implements IObserver {

    private static final long serialVersionUID = 1L;
    private static final int XDIM = 650;
    private static final int YDIM = 500;
    private static final int HEADERFONTSIZE = 30;
    private static final int PLAYERFONTSIZE = 20;
    private static final int STONESPLAYERMAX = 9;
    private static final int FSIZEANZSTONES = 12;
    private static final int FSIZESTONES = 14;
    private static final int PANELSIZEX = 100;
    private static final int PANELSIZEY = 500;
    private static final int GRIDX = 10;
    private static final String FONT = "SANS_SERIF";
    private JLabel playerinfo;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private IController controller;
    private JLabel stonesPlayer1;
    private JLabel stonesPlayer2;
    private JLabel anzStonesPlayer1;
    private JLabel anzStonesPlayer2;
    private JLabel titel;
    private Imagepanel backgroudPic;

    public Gui(IController controller) {
        this.setTitle("Muehle-Spiel");
        this.setLayout(new BorderLayout());
        this.setMinimumSize(new Dimension(XDIM, YDIM));
        this.setPreferredSize(new Dimension(XDIM, YDIM));
        this.controller = controller;
        this.controller.registerObserver(this);

        backgroudPic = new Imagepanel(controller);

        this.setTitel();
        this.setPlayerInfo();
        this.setstonesPlayer1();
        this.setStonesPlayer2();
        this.setLeftPanel();
        this.setRightPanel();

        this.add(titel, BorderLayout.NORTH);
        this.add(backgroudPic, BorderLayout.CENTER);
        this.add(leftPanel, BorderLayout.WEST);
        this.add(rightPanel, BorderLayout.EAST);
        this.add(playerinfo, BorderLayout.PAGE_END);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);

    }

    private void setTitel() {
        titel = new JLabel("Muehlespiel", JLabel.CENTER);
        titel.setBackground(Color.BLACK);
        titel.setForeground(Color.RED);
        titel.setOpaque(true);
        titel.setFont(new Font(FONT, Font.ITALIC, HEADERFONTSIZE));
    }

    private void setPlayerInfo() {
        playerinfo = new JLabel("", JLabel.CENTER);
        playerinfo.setBackground(Color.GREEN);
        playerinfo.setForeground(Color.RED);
        playerinfo.setOpaque(true);
        playerinfo.setFont(new Font(FONT, Font.ITALIC, PLAYERFONTSIZE));
    }

    private void setStonesPlayer2() {
        stonesPlayer2 = new JLabel("Steine Player2", JLabel.CENTER);
        anzStonesPlayer2 = new JLabel("" + (STONESPLAYERMAX - controller.getConsumedStonesPlayer2()));
        anzStonesPlayer2.setFont(new Font("SANS_SERIF", Font.ITALIC, FSIZEANZSTONES));
        stonesPlayer2.setBackground(Color.YELLOW);
        stonesPlayer2.setForeground(Color.BLACK);
        stonesPlayer2.setFont(new Font(FONT, Font.ITALIC, FSIZESTONES));
        stonesPlayer2.setOpaque(true);
    }

    private void setstonesPlayer1() {
        stonesPlayer1 = new JLabel("Steine Player1", JLabel.CENTER);
        anzStonesPlayer1 = new JLabel("" + (STONESPLAYERMAX - controller.getConsumedStonesPlayer1()));
        anzStonesPlayer1.setFont(new Font("SANS_SERIF", Font.ITALIC, FSIZEANZSTONES));
        stonesPlayer1.setBackground(Color.YELLOW);
        stonesPlayer1.setForeground(Color.BLACK);
        stonesPlayer1.setFont(new Font(FONT, Font.ITALIC, FSIZESTONES));
        stonesPlayer1.setOpaque(true);
    }

    private void setLeftPanel() {
        leftPanel = new JPanel();
        leftPanel.setBackground(Color.YELLOW);
        leftPanel.setPreferredSize(new Dimension(PANELSIZEX, PANELSIZEY));
        leftPanel.setLayout(new GridLayout(GRIDX, 1));
        leftPanel.add(stonesPlayer1);
        leftPanel.add(anzStonesPlayer1);
        leftPanel.setOpaque(true);
    }

    private void setRightPanel() {
        rightPanel = new JPanel();
        rightPanel.setBackground(Color.YELLOW);
        rightPanel.setPreferredSize(new Dimension(PANELSIZEX, PANELSIZEY));
        rightPanel.setLayout(new GridLayout(GRIDX, 1));
        rightPanel.add(stonesPlayer2);
        rightPanel.add(anzStonesPlayer2);
        rightPanel.setOpaque(true);
    }

    public void beginSetPlayerInfo() {
        playerinfo.setText("Wilkommen! Player 1 darf beginnen!");
    }

    public void setPlayerinfo(String playername, String status) {
        playerinfo.setText(playername + " " + status);
    }

    @Override
    public void update(IPlayer currentPlayer, int anzMills, boolean gameEnded) {
        this.anzStonesPlayer1.setText("" + (STONESPLAYERMAX - controller.getConsumedStonesPlayer1()));
        this.anzStonesPlayer2.setText("" + (STONESPLAYERMAX - controller.getConsumedStonesPlayer2()));
        if (gameEnded) {
            this.setPlayerinfo(currentPlayer.getName(), " hat gewonnen!");
        } else if (anzMills > 0) {
            if (anzMills == 1) {
                this.setPlayerinfo(currentPlayer.getName(), " hat eine Muehle, loesche einen Stein!");
            } else if (anzMills == 2) {
                this.setPlayerinfo(currentPlayer.getName(), " hat zwei Muehlen, loesche zwei Steine!");
            }
        } else {
            this.setPlayerinfo(currentPlayer.getName(), " ist an der Reihe!");
        }
        backgroudPic.paintTokens();
    }


}
