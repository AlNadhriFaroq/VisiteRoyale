package Vue.ComponentsJeu;

import Global.Images;
import Modele.Carte;

import javax.swing.*;
import java.awt.*;

public class CarteVue extends JPanel {
    Carte carte;
    boolean parcourable;
    boolean selectionnable;
    boolean cachee;
    Image img;

    public CarteVue(boolean parcourable, boolean selectionnable, int hauteur) {
        this.parcourable = parcourable;
        this.selectionnable = selectionnable;
        cachee = false;
        img = Images.CARTE_VIDE;

        setBackground(new Color(0, 0, 0, 0));
        redimensionner(hauteur);
    }

    public Carte getCarte() {
        return carte;
    }

    public boolean estParcourable() {
        return parcourable;
    }

    public boolean estSelectionnable() {
        return selectionnable;
    }

    public void redimensionner(int hauteur) {
        Dimension dim = new Dimension(hauteur * img.getWidth(null) / img.getHeight(null), hauteur);
        setMinimumSize(dim);
        setMaximumSize(dim);
        setPreferredSize(dim);
        setSize(dim);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D dessin = (Graphics2D) g;

        dessin.drawImage(img, 0, 0, getWidth()-1, getHeight()-1, null);
        dessin.setColor(new Color(0, 0, 0, 255));
        if (!selectionnable)
            dessin.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
    }

    public void mettreAJour(Carte carte, boolean parcourable, boolean selectionnable, boolean cachee) {
        this.carte = carte;
        this.parcourable = parcourable;
        this.selectionnable = selectionnable;
        this.cachee = cachee;

        if (carte == null)
            img = Images.getImageCarte("Vide");
        else if (cachee)
            img = Images.getImageCarte("Dos");
        else
            img = Images.getImageCarte(carte.toString());
        repaint();
    }
}
