package Vue.Composants.ComposantsJeu;

import Global.Images;
import Modele.*;
import Vue.GBC;

import javax.swing.*;
import java.awt.*;

public class PaquetVue extends JPanel {
    Jeu jeu;
    Paquet paquet;
    int position;

    CarteVue[] cartesVue;

    public PaquetVue(Jeu jeu, Paquet paquet, int position) {
        this.jeu = jeu;
        this.paquet = paquet;
        this.position = position;

        setBackground(new Color(0, 0, 0, 0));
        setLayout(new GridBagLayout());

        cartesVue = new CarteVue[Jeu.TAILLE_MAIN];
        for (int i = 0; i < Jeu.TAILLE_MAIN; i++)
            cartesVue[i] = new CarteVue();
    }

    public Paquet getPaquet() {
        return paquet;
    }

    public CarteVue getCarteVue(int indice) {
        return cartesVue[indice];
    }

    public boolean contientCarteVue(CarteVue carteVue) {
        for (CarteVue cv : cartesVue)
            if (carteVue.equals(cv))
                return true;
        return false;
    }

    public void redimensionner(int hauteur) {
        Dimension dim = new Dimension(cartesVue.length * hauteur * Images.CARTE_VIDE.getWidth(null) / Images.CARTE_VIDE.getHeight(null), hauteur);
        setMinimumSize(dim);
        setMaximumSize(dim);
        setPreferredSize(dim);
        setSize(dim);
        for (CarteVue carteVue : cartesVue)
            carteVue.redimensionner(hauteur * 8 / 9);
    }

    public void mettreAJour(boolean faceCachee, boolean parcourable, boolean selectionnable) {
        setVisible(false);
        removeAll();
        add(Box.createGlue(), new GBC(0, 0).setWeight(1, 1));
        for (int i = 0; i < paquet.getTaille(); i++) {
            cartesVue[i].mettreAJour(paquet.getCarte(i), faceCachee, parcourable, selectionnable && jeu.peutSelectionnerCarte(paquet.getCarte(i)));
            add(cartesVue[i], new GBC(i + 1, 0).setWeighty(1).setAnchor(position));
        }
        add(Box.createGlue(), new GBC(paquet.getTaille() + 1, 0).setWeight(1, 1));
        repaint();
        setVisible(true);
    }
}
