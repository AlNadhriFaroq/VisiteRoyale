package Vue;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;

public class Pion extends JPanel implements MouseInputListener {
    private int position;
    private boolean dragged;

    public void setDragged(boolean dragged) {
        this.dragged = dragged;
    }

    public boolean isDragged() {
        return dragged;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
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
