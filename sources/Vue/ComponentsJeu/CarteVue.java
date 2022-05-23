package Vue.ComponentsJeu;

import Global.Images;
import Modele.Carte;

import javax.swing.*;
import java.awt.*;

public class CarteVue extends JPanel {
    Carte carte;
    boolean dos;
    Image img;

    public CarteVue() {
        setBackground(new Color(0, 0, 0, 0));
        dos = false;
        img = Images.CARTE_VIDE;
    }

    public Carte getCarte() {
        return carte;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D dessin = (Graphics2D) g;
        dessin.drawImage(img, 0, 0, getWidth()-1, getHeight()-1, null);
    }

    public void mettreAJour(Carte carte, boolean dos) {
        this.carte = carte;
        this.dos = dos;
        if (carte == null)
            img = Images.getImageCarte("Vide");
        else if (dos)
            img = Images.getImageCarte("Dos");
        else
            img = Images.getImageCarte(carte.toString());
        repaint();
    }
}
