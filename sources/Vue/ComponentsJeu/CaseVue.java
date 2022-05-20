package Vue.ComponentsJeu;

import Vue.Couleur;

import javax.swing.*;
import java.awt.*;

public class CaseVue extends JPanel {
    int c;
    Image img;

    public CaseVue(int c) {
        this.c = c;
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    }

    public int getCase() {
        return c;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D dessin = (Graphics2D) g;
        int[] couleur = Couleur.getCouleurCase(c);
        setBackground(new Color(couleur[0], couleur[1], couleur[2], 255));
        dessin.setColor(Color.BLACK);
        dessin.drawRect(0, 0, getWidth(), getHeight());
    }

    public void mettreAJour() {
        repaint();
    }
}
