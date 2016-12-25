/**
 * Muehlegame
 * Copyright (c) 2015, Thomas Ammann, Johannes Finckh
 *
 * @author Thomas Amann, Johannes Finckh
 * @version 1.0
 */

package view.gui;

import controller.IController;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EmptyToken extends JLabel implements MouseListener {
    private static final long serialVersionUID = 1L;
    private int number;
    private IController controller;

    public EmptyToken(IController controller, int number, int xpos, int ypos, int sizeX, int sizeY) {
        this.number = number;
        this.setLocation(xpos, ypos);
        this.setSize(sizeX, sizeY);
        this.addMouseListener(this);
        this.controller = controller;

    }

    public int getNumber() {
        return number;
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {

        if (controller.getCurrentStonesToDelete() > 0) {
            controller.millDeleteStone(this.getNumber());
        } else if (controller.requireInitial()) {
            controller.setStone(this.getNumber());
        } else {
            controller.moveStone(this.getNumber());
        }
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

}
