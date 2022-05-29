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
    Color couleurLignesDessus;

    public CaseVue(int c) {
        this.c = c;
        selectionnable = false;
        destination = false;
        dessus = false;
        selectionne = false;

        couleurLignes = (c <= Plateau.CHATEAU_VRT || c >= Plateau.CHATEAU_RGE || c == Plateau.FONTAINE) ? new Color(200, 240, 200) : Color.BLACK;
        couleurLignesDessus = (c <= Plateau.CHATEAU_VRT || c >= Plateau.CHATEAU_RGE || c == Plateau.FONTAINE) ? new Color(126, 231, 126) : Color.WHITE;
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

        dessin.setColor(selectionne ? Color.BLUE : (dessus ? (destination ? Color.WHITE : couleurLignesDessus) : (destination ? Color.YELLOW : couleurLignes)));
        dessin.setStroke(new BasicStroke(5));
        dessin.drawRect(0, 0, getWidth(), getHeight());
        dessin.drawLine(0, getHeight() / 4, getWidth(), getHeight() / 4);
    }
}
