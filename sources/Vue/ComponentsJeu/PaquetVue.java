package Vue.ComponentsJeu;

import Modele.Jeu;
import Modele.Paquet;

import javax.swing.*;
import java.awt.*;

public class PaquetVue extends JPanel {
    Paquet paquet;
    CarteVue[] cartesVue;

    public PaquetVue(Paquet paquet) {
        this.paquet = paquet;

        setBackground(new Color(0, 0, 0, 0));
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        cartesVue = new CarteVue[Jeu.TAILLE_MAIN];
        add(Box.createHorizontalGlue());
        for (int i = 0; i < Jeu.TAILLE_MAIN; i++) {
            cartesVue[i] = new CarteVue(false, false, 110);
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D dessin = (Graphics2D) g;
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
