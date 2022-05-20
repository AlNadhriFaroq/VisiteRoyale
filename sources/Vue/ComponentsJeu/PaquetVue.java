package Vue.ComponentsJeu;

import Modele.Carte;
import Modele.Paquet;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class PaquetVue extends JPanel {
    Paquet paquet;
    CarteVue[] cartesVue;

    public PaquetVue(Paquet paquet) {
        this.paquet = paquet;
        setBackground(new Color(0, 0, 0, 0));
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        cartesVue = new CarteVue[paquet.getTaille()];
        for (int i = 0; i < paquet.getTaille(); i++) {
            cartesVue[i] = new CarteVue(paquet.getCarte(i));
            add(cartesVue[i]);
        }
    }

    public Paquet getPaquet() {
        return paquet;
    }

    public CarteVue getCarteVue(int indice) {
        return cartesVue[indice];
    }

    public void mettreAJour() {
        repaint();
    }
}
