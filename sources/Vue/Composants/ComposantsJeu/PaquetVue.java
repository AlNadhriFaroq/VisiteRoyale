package Vue.Composants.ComposantsJeu;

import Global.Images;
import Modele.Jeu;
import Modele.Paquet;

import javax.swing.*;
import java.awt.*;

public class PaquetVue extends JPanel {
    Paquet paquet;
    CarteVue[] cartesVue;

    public PaquetVue(Paquet paquet, boolean alenvers) {
        this.paquet = paquet;

        setBackground(new Color(0, 0, 0, 0));
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        cartesVue = new CarteVue[Jeu.TAILLE_MAIN];
        add(Box.createHorizontalGlue());
        for (int i = 0; i < Jeu.TAILLE_MAIN; i++) {
            cartesVue[i] = new CarteVue(alenvers);
            add(cartesVue[i]);
        }
        add(Box.createHorizontalGlue());
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
            carteVue.redimensionner(getHeight() * 8 / 9);
    }

    public void mettreAJour(boolean cachee) {
        removeAll();
        add(Box.createHorizontalGlue());
        for (int i = 0; i < paquet.getTaille(); i++) {
            cartesVue[i].mettreAJour(paquet.getCarte(i), false, false, cachee);
            add(cartesVue[i]);
        }
        add(Box.createHorizontalGlue());
        repaint();
    }
}
