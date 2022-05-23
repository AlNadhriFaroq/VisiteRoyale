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
        for (int i = 0; i < Jeu.TAILLE_MAIN; i++) {
            cartesVue[i] = new CarteVue();
            add(cartesVue[i]);
        }
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
        for (int i = 0; i < paquet.getTaille(); i++)
            cartesVue[i].mettreAJour(paquet.getCarte(i), cachee);
        for (int i = paquet.getTaille(); i < paquet.getTailleMax(); i++)
            cartesVue[i].mettreAJour(null, false);
        repaint();
    }
}
