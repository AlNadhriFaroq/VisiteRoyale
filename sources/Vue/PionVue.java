package Vue;

import Modele.Pion;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;

public class PionVue extends JPanel implements MouseInputListener {
    private static String path;
    private Pion pion;
    private boolean dragged;

    public PionVue(Pion pion) {
        this.pion = pion;
        this.dragged = false;

        this.addMouseMotionListener(this);
        this.addMouseListener(this);
    }

    public void setDragged(boolean dragged) {
        this.dragged = dragged;
    }

    public boolean isDragged() {
        return dragged;
    }

    /* MOUSE LISTENER*/
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
