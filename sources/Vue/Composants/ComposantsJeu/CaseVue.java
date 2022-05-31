package Vue.Composants.ComposantsJeu;

import Modele.Plateau;
import Global.Format;

import javax.swing.*;
import java.awt.*;

public class CaseVue extends JPanel {
    int c;
    boolean selectionnable;
    boolean destination;
    boolean dessus;
    boolean selectionne;

    Color couleurLignes;
    Color couleurLignesDest;
    Color couleurLignesDessus;
    Color couleurLignesSelect;

    public CaseVue(int c) {
        this.c = c;
        selectionnable = false;
        destination = false;
        dessus = false;
        selectionne = false;

        couleurLignes = (c <= Plateau.CHATEAU_VRT || c >= Plateau.CHATEAU_RGE || c == Plateau.FONTAINE) ? new Color(126, 231, 126) : Color.BLACK;
        couleurLignesDest = (c <= Plateau.CHATEAU_VRT || c >= Plateau.CHATEAU_RGE || c == Plateau.FONTAINE) ? new Color(243, 11, 2) : new Color(243, 11, 2, 255);
        couleurLignesDessus = (c <= Plateau.CHATEAU_VRT || c >= Plateau.CHATEAU_RGE || c == Plateau.FONTAINE) ? new Color(255, 97, 90) : new Color(255, 97, 90, 255);
        couleurLignesSelect = (c <= Plateau.CHATEAU_VRT || c >= Plateau.CHATEAU_RGE || c == Plateau.FONTAINE) ? new Color(255, 166, 0) : new Color(255, 166, 0, 255);
        setLayout(new GridLayout(4, 1));
    }

    public int getCase() {
        return c;
    }

    public boolean estSelectionnable() {
        return selectionnable;
    }

    public void mettreAJour(boolean selectionnable, boolean destination, boolean dessus, boolean selectionne) {
        this.selectionnable = selectionnable;
        this.destination = destination;
        this.dessus = dessus;
        this.selectionne = selectionne;
        repaint();
    }

    public void mettreAJour(boolean dessus) {
        this.dessus = dessus;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D dessin = (Graphics2D) g;

        setBackground(Format.getCouleurCase(c));

        dessin.setColor(selectionne ? couleurLignesSelect : (dessus ? couleurLignesDessus : (destination ? couleurLignesDest : couleurLignes)));
        dessin.setStroke(new BasicStroke(5));
        dessin.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        dessin.drawLine(0, getHeight() / 4, getWidth(), getHeight() / 4);
    }
}
