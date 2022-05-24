package Vue;

import Controleur.ControleurMediateur;
import Modele.Carte;
import Modele.Jeu;

import javax.swing.event.MouseInputListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class CarteVue extends JPanel implements MouseInputListener, Comparable<CarteVue> {
    private static final HashMap<String, BufferedImage> images = new HashMap<>();
    private static String path;
    private final JeuVue frame;
    private Carte carte;
    private Boolean dos;
    private Boolean jouable;
    private Boolean dragged;
    private Image image;
    private Image imDos;
    private static String Mypath = "/Images/Cartes/";
    private Jeu jeu;
    private ControleurMediateur ctrl;

    public CarteVue(Jeu jeu, ControleurMediateur ctrl, JeuVue f) {
        this.jeu = jeu;
        this.ctrl = ctrl;
        this.frame = f;
        this.dos = false;
        this.jouable = false;
        this.dragged = false;

        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.setSize(90, 160);
    }

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

    private static BufferedImage getImage(String name) {
        name = Mypath + name + ".png";
        if (!images.containsKey(name))
            images.put(name, Utils.loadImg(name));
        return images.get(name);
    }

    @Override
    public void paintComponent(Graphics g) {
        //this.setSize(90, 160);
        //setBounds(0,0, 400, 400);
        super.paintComponent(g);
        g.drawImage(this.image, 0, 0, 90, 160, this);
    }

    private boolean estValide(int y) {
        return (y > (frame.getHeight()/2) && jeu.getJoueurCourant() == Jeu.JOUEUR_RGE) ||
                (y < (frame.getHeight()/2) && jeu.getJoueurCourant() == Jeu.JOUEUR_VRT);
    }

    /* MOUSE LISTENER*/
    @Override
    public void mouseClicked(MouseEvent e) {
        if (jeu.peutSelectionnerCarte(carte) && estValide(e.getYOnScreen())) {
            ctrl.selectionnerCarte(carte);

            frame.jouerCarte(this);
            if (jeu.getJoueurCourant() == Jeu.JOUEUR_RGE){
                frame.PlacerJeuA(this);
            }else{
                frame.PlacerJeuB(this);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (jeu.peutSelectionnerCarte(carte) && estValide(e.getYOnScreen())){
            this.frame.carteSelecTaille(this, this.frame.carteH/5);
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (jeu.peutSelectionnerCarte(carte) && estValide(e.getYOnScreen())){
            this.frame.carteSelecTaille(this, -(this.frame.carteH/5) );
        }

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        setFocusable(true);
        this.requestFocus();
        this.dragged = true;
        Point p1 = e.getLocationOnScreen();
        Point p2 = this.frame.getLocationOnScreen();
        Point p = new Point(p1.x - p2.x, p1.y - p2.y);
        this.setLocation(p.x - (getWidth() / 2), p.y - (getHeight() / 2));
        this.setVisible(true);
        this.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public int compareTo(CarteVue o) {
        return this.carte.getType().compareTo(o.carte.getType()) * 10 + (this.carte.getDeplacement() - o.carte.getDeplacement());


    }
}
