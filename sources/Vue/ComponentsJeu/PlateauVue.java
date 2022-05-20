package Vue.ComponentsJeu;

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
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

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

            if (plateau.getPositionCouronne() == c)
                casesVue[c].add(couronneVue);
            else
                casesVue[c].add(Box.createGlue());

            if (plateau.getPositionPion(Pion.SOR) == c)
                casesVue[c].add(pionsVue[3]);
            else
                casesVue[c].add(Box.createGlue());

            if (plateau.getPositionPion(Pion.ROI) == c)
                casesVue[c].add(pionsVue[0]);
            else if (plateau.getPositionPion(Pion.GAR_VRT) == c)
                casesVue[c].add(pionsVue[1]);
            else if (plateau.getPositionPion(Pion.GAR_RGE) == c)
                casesVue[c].add(pionsVue[2]);
            else
                casesVue[c].add(Box.createGlue());

            if (plateau.getPositionPion(Pion.FOU) == c)
                casesVue[c].add(pionsVue[4]);
            else
                casesVue[c].add(Box.createGlue());
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

    public void mettreAJour() {
        repaint();
    }
}
