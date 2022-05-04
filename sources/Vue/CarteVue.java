package Vue;

import Modele.Carte;

import javax.swing.event.MouseInputListener;
import javax.swing.*;
import java.awt.event.MouseEvent;

public class CarteVue extends JPanel implements MouseInputListener {
    private static String path;
    private Carte carte;
    private Boolean dos;
    private Boolean jouable;
    private Boolean dragged;

    public CarteVue(Carte carte) {
        this.carte = carte;
        this.dos = true;
        this.jouable = false;
        this.dragged = false;

        this.addMouseMotionListener(this);
        this.addMouseListener(this);
    }

    /* SETTERS/GETTERS */
    public void setJouable(Boolean jouable) {
        this.jouable = jouable;
    }

    public Boolean getJouable() {
        return jouable;
    }

    public Carte getCarte() {
        return carte;
    }

    public void setCarte(Carte carte) {
        this.carte = carte;
    }

    public Boolean getDos() {
        return dos;
    }

    public void setDos(Boolean dos) {
        this.dos = dos;
    }

    public void setDragged(Boolean dragged) {
        this.dragged = dragged;
    }

    public Boolean getDragged() {
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
