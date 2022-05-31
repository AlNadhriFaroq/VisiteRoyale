package Vue.Composants.ComposantsJeu;

import Global.Images;
import Modele.Carte;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class CarteVue extends JPanel {
    Carte carte;
    boolean faceCachee;
    boolean parcourable;
    boolean selectionnable;
    Shape forme;
    Image img;

    public CarteVue() {
        this.faceCachee = false;
        this.parcourable = false;
        this.selectionnable = true;
        img = Images.CARTE_VIDE;

        setBackground(new Color(0, 0, 0, 0));
        setOpaque(false);
    }

    public Carte getCarte() {
        return carte;
    }

    public boolean estParcourable() {
        return parcourable;
    }

    public void redimensionner(int hauteur) {
        Dimension dim = new Dimension(hauteur * img.getWidth(null) / img.getHeight(null), hauteur);
        setMinimumSize(dim);
        setMaximumSize(dim);
        setPreferredSize(dim);
        setSize(dim);
    }

    public void mettreAJour(Carte carte, boolean faceCachee, boolean parcourable, boolean selectionnable) {
        this.carte = carte;
        this.faceCachee = faceCachee;
        this.parcourable = parcourable;
        this.selectionnable = selectionnable;

        if (carte == null)
            img = Images.getImageCarte("Vide");
        else if (faceCachee)
            img = Images.getImageCarte("Dos");
        else
            img = Images.getImageCarte(carte.toString());
        repaint();
    }

    @Override
    public boolean contains(int x, int y) {
        if (forme == null || !forme.getBounds().equals(getBounds()))
            forme = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15);
        return forme.contains(x, y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D dessin = (Graphics2D) g;

        dessin.drawImage(img, 0, 0, getWidth(), getHeight(), null);
        dessin.setColor(new Color(0, 0, 0, 60));
        if (!faceCachee && !selectionnable)
            dessin.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
    }
}
