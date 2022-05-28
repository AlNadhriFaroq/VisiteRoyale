package Vue.Composants.ComposantsJeu;

import Modele.Pion;
import Modele.Plateau;

import javax.swing.*;
import java.awt.*;

public class PlateauVue extends JPanel {
    Plateau plateau;
    JetonVue couronneVue;
    PionVue[] pionsVue;
    CaseVue[] casesVue;

    public PlateauVue(Plateau plateau) {
        this.plateau = plateau;

        setBackground(new Color(0, 0, 0, 0));
        setLayout(new GridLayout(1, 17));

        couronneVue = new JetonVue();
        pionsVue = new PionVue[5];
        pionsVue[0] = new PionVue(Pion.ROI);
        pionsVue[1] = new PionVue(Pion.GAR_VRT);
        pionsVue[2] = new PionVue(Pion.GAR_RGE);
        pionsVue[3] = new PionVue(Pion.SOR);
        pionsVue[4] = new PionVue(Pion.FOU);

        casesVue = new CaseVue[Plateau.BORDURE_RGE+1];
        for (int c = Plateau.BORDURE_VRT; c <= Plateau.BORDURE_RGE; c++) {
            casesVue[c] = new CaseVue(c);
            add(casesVue[c]);

            casesVue[c].add(plateau.getPositionCouronne() == c ? couronneVue : Box.createVerticalGlue());
            casesVue[c].add(plateau.getPositionPion(Pion.SOR) == c ? pionsVue[3] : Box.createVerticalGlue());

            if (plateau.getPositionPion(Pion.ROI) == c)
                casesVue[c].add(pionsVue[0]);
            else if (plateau.getPositionPion(Pion.GAR_VRT) == c)
                casesVue[c].add(pionsVue[1]);
            else if (plateau.getPositionPion(Pion.GAR_RGE) == c)
                casesVue[c].add(pionsVue[2]);
            else
                casesVue[c].add(Box.createVerticalGlue());

            casesVue[c].add(plateau.getPositionPion(Pion.FOU) == c ? pionsVue[4] : Box.createVerticalGlue());
        }
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public JetonVue getCouronneVue() {
        return couronneVue;
    }

    public PionVue getPionVue(int indice) {
        return pionsVue[indice];
    }

    public CaseVue getCaseVue(int indice) {
        return casesVue[indice];
    }

    public void redimensionner(int hauteur) {
        Dimension dim = new Dimension(17 * hauteur / 4, hauteur);
        setMinimumSize(dim);
        setMaximumSize(dim);
        setPreferredSize(dim);
        setSize(dim);
    }

    public void mettreAJour() {
        couronneVue.mettreAJour(plateau.getFaceCouronne());
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int c = Plateau.BORDURE_VRT; c <= Plateau.BORDURE_RGE; c++) {
            casesVue[c].removeAll();

            casesVue[c].add(plateau.getPositionCouronne() == c ? couronneVue : Box.createVerticalGlue());
            casesVue[c].add(plateau.getPositionPion(Pion.SOR) == c ? pionsVue[3] : Box.createVerticalGlue());

            if (plateau.getPositionPion(Pion.ROI) == c)
                casesVue[c].add(pionsVue[0]);
            else if (plateau.getPositionPion(Pion.GAR_VRT) == c)
                casesVue[c].add(pionsVue[1]);
            else if (plateau.getPositionPion(Pion.GAR_RGE) == c)
                casesVue[c].add(pionsVue[2]);
            else
                casesVue[c].add(Box.createVerticalGlue());

            casesVue[c].add(plateau.getPositionPion(Pion.FOU) == c ? pionsVue[4] : Box.createVerticalGlue());
        }
    }
}
