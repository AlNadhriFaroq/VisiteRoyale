package Vue;

import Modele.Carte;

import javax.swing.event.MouseInputListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class CarteVue extends JPanel implements MouseInputListener {
    private static final HashMap<String, BufferedImage> images = new HashMap<>();
    private static String path;
    private Carte carte;
    private Boolean dos;
    private Boolean jouable;
    private Boolean dragged;
    private Image image;
    private Image imDos;
    //private static String Mypath = "C:\\Users\\maxdr\\git\\VisiteRoyale\\resources\\Carte\\";
    private static String Mypath = "/Images/Cartes/";

    public CarteVue() {
        this.dos = true;
        this.jouable = false;
        this.dragged = false;

        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.setSize(90, 160);
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
        this.setImage();
    }

    public Boolean isDos() {
        return dos;
    }

    public void setDos(Boolean dos) {
        this.dos = dos;
    }

    public void setDragged(Boolean dragged) {
        this.dragged = dragged;
    }

    public Boolean isDragged() {
        return dragged;
    }
    private void setImage() {
        if (this.isDos()) {
            this.image = getImage("Back");
        } else {
            this.image = getImage(this.carte.toString());
        }
        this.image = this.image.getScaledInstance(90, 160, Image.SCALE_SMOOTH);
    }

    /* GESTION IMAGE */
    private static BufferedImage getImage(String name) {
        name = Mypath + name + ".png";
        if (!images.containsKey(name))
        {
            images.put(name, Utils.loadImg(name));
        }

        return images.get(name);
    }
    /* METHODES */


    @Override
    public void paintComponent(Graphics g){
        //this.setSize(90, 160);
        //setBounds(0,0, 400, 400);
        super.paintComponent(g);
        g.drawImage(this.image, this.getX(), this.getY(), 90, 160, this);
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
