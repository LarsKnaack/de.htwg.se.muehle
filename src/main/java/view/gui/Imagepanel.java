/**
 * Muehlegame
 * Copyright (c) 2015, Thomas Ammann, Johannes Finckh
 *
 * @author Thomas Amann, Johannes Finckh
 * @version 1.0
 */

package view.gui;

import controller.IController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Imagepanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final int ARRAYSIZE = 25;
    private static final int ANZVERTEXES = 24;
    private static final int RESIZEOVAL = 16;
    private static final int SIZEEMPTYTOKEN = 8;
    InputStream stream = getClass().getResourceAsStream("/spielbrett.jpg");
    private EmptyToken et[];
    private Image pic;
    //private Image pic = Toolkit.getDefaultToolkit().getImage("spielbrett.jpg");
    private IController controller;
    private double xydiffs[][];
    private Graphics g;
    private int select1;

    public Imagepanel(IController controller) {
        try {
            pic = ImageIO.read(stream);
        } catch (IOException e) {
        }
        this.controller = controller;
        this.et = new EmptyToken[ARRAYSIZE];
        xydiffs = new double[ANZVERTEXES][2];
        select1 = 0;
        this.fillXydiffs();
    }

    private void fillXydiffs() {
        try {
            InputStream stream = getClass().getResourceAsStream("/pixeltab.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(stream));
            String zeile = null;
            int i = 0;
            while ((zeile = in.readLine()) != null) {
                String splitresult[] = zeile.split(" ");
                double temp1 = Double.parseDouble(splitresult[0]);
                double temp2 = Double.parseDouble(splitresult[1]);
                xydiffs[i][0] = temp1;
                xydiffs[i][1] = temp2;
                i++;

            }
            in.close();
        } catch (IOException e) {
            System.exit(1);
        }
    }

    public void paintComponent(Graphics g) {
        this.g = g;
        g.drawImage(pic, 0, 0, this.getWidth(), this.getHeight(), this);
        this.paintTokens();
        this.fillWithEmptyTokens();
    }

    public void setSelected(int vertex) {
        if (controller.getVertexColor(vertex) != 'n') {
            if (select1 == 0) {
                select1 = vertex;
                this.paintTokens();
                this.paintToken(vertex);

            } else {
                controller.moveStone(select1, vertex);
                select1 = 0;
            }
        }
    }

    public void paintToken(int vertex) {
        int xdiff = (int) Math.round(this.getSize().width
                / xydiffs[vertex - 1][0]);
        int ydiff = (int) Math.round(this.getSize().height
                / xydiffs[vertex - 1][1]);
        g.setColor(Color.RED);
        g.fillOval(this.getSize().width - xdiff, this.getSize().height - ydiff,
                this.getSize().width / RESIZEOVAL, this.getSize().height / RESIZEOVAL);

    }

    public void paintTokens() {
        for (int i = 1; i < ARRAYSIZE; i++) {
            if (controller.getVertexColor(i) == 's') {
                int xdiff = (int) Math.round(this.getSize().width
                        / xydiffs[i - 1][0]);
                int ydiff = (int) Math.round(this.getSize().height
                        / xydiffs[i - 1][1]);
                g.setColor(Color.black);
                g.fillOval(this.getSize().width - xdiff, this.getSize().height
                                - ydiff, this.getSize().width / RESIZEOVAL,
                        this.getSize().height / RESIZEOVAL);
            }
            if (controller.getVertexColor(i) == 'w') {
                int xdiff = (int) Math.round(this.getSize().width
                        / xydiffs[i - 1][0]);
                int ydiff = (int) Math.round(this.getSize().height
                        / xydiffs[i - 1][1]);
                g.setColor(Color.white);
                g.fillOval(this.getSize().width - xdiff, this.getSize().height
                                - ydiff, this.getSize().width / RESIZEOVAL,
                        this.getSize().height / RESIZEOVAL);
            }
        }
    }

    public void fillWithEmptyTokens() {
        int sizeX = this.getSize().width / SIZEEMPTYTOKEN;
        int sizeY = this.getSize().height / SIZEEMPTYTOKEN;
        int xdiff = 0;
        int ydiff = 0;

        for (int i = 1; i < ARRAYSIZE; i++) {
            xdiff = (int) Math.round(this.getSize().width / xydiffs[i - 1][0]);
            ydiff = (int) Math.round(this.getSize().height / xydiffs[i - 1][1]);
            et[i] = new EmptyToken(controller, i, this.getSize().width - xdiff,
                    this.getSize().height - ydiff, sizeX, sizeY);
            this.add(et[i]);

            i++;
            xdiff = (int) Math.round(this.getSize().width / xydiffs[i - 1][0]);
            ydiff = (int) Math.round(this.getSize().height / xydiffs[i - 1][1]);
            et[i] = new EmptyToken(controller, i, this.getSize().width - xdiff,
                    this.getSize().height - ydiff, sizeX, sizeY);
            this.add(et[i]);
        }

    }
}
