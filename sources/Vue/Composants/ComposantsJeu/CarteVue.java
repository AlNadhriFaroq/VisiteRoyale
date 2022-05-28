package Vue.Composants.ComposantsJeu;

import Global.Images;
import Modele.Carte;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class CarteVue extends JPanel {
    Carte carte;
    boolean alenvers;
    boolean parcourable;
    boolean selectionnable;
    boolean cachee;
    Shape shape;
    Image img;

    public CarteVue(boolean alenvers) {
        this.alenvers = alenvers;
        this.parcourable = false;
        this.selectionnable = true;
        cachee = false;
        img = Images.CARTE_VIDE;

        setBackground(new Color(0, 0, 0, 0));
        setOpaque(false);
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setAlignmentY(alenvers ? Component.TOP_ALIGNMENT : Component.BOTTOM_ALIGNMENT);
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
        if (!alenvers)
            setLocation(getX(), getParent().getHeight() - getHeight());
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

    @Override
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15);
        }
        return shape.contains(x, y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D dessin = (Graphics2D) g;

        //dessin.clearRect(0, 0, getWidth(), getHeight());
        dessin.drawImage(img, 0, 0, getWidth()-1, getHeight()-1, null);
        dessin.setColor(new Color(0, 0, 0, 255));
        if (!selectionnable)
            dessin.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
    }
}
