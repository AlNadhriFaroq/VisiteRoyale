package Vue.ComponentsJeu;

import Modele.Plateau;
import Global.Format;

import javax.swing.*;
import java.awt.*;

public class CaseVue extends JPanel {
    int c;
    Color couleurLignes;

    public CaseVue(int c) {
        this.c = c;
        couleurLignes = (c <= Plateau.CHATEAU_VRT || c >= Plateau.CHATEAU_RGE || c == Plateau.FONTAINE) ? new Color(200, 240, 200) : Color.BLACK;
        setLayout(new GridLayout(4, 1));
    }

    public int getCase() {
        return c;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D dessin = (Graphics2D) g;

        setBackground(Format.getCouleurCase(c));

        dessin.setColor(couleurLignes);
        dessin.setStroke(new BasicStroke(5));
        dessin.drawRect(0, 0, getWidth(), getHeight());
        dessin.drawLine(0, getHeight()/4, getWidth(), getHeight()/4);
    }

    public void mettreAJour() {
        repaint();
    }
}
