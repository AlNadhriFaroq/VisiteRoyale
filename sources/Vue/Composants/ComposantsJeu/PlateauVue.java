package Vue.Composants.ComposantsJeu;

import Modele.*;

import javax.swing.*;
import java.awt.*;

public class PlateauVue extends JPanel {
    Programme prog;

    JetonVue couronneVue;
    PionVue[] pionsVue;
    CaseVue[] casesVue;

    public PlateauVue(Programme prog) {
        this.prog = prog;
        Plateau plateau = prog.getJeu().getPlateau();

        setBackground(new Color(0, 0, 0, 0));
        setOpaque(false);
        setLayout(new GridLayout(1, Plateau.BORDURE_RGE - Plateau.BORDURE_VRT + 1));

        couronneVue = new JetonVue();
        pionsVue = new PionVue[5];
        pionsVue[0] = new PionVue(Pion.ROI);
        pionsVue[1] = new PionVue(Pion.GAR_VRT);
        pionsVue[2] = new PionVue(Pion.GAR_RGE);
        pionsVue[3] = new PionVue(Pion.SOR);
        pionsVue[4] = new PionVue(Pion.FOU);

        casesVue = new CaseVue[Plateau.BORDURE_RGE - Plateau.BORDURE_VRT + 1];
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
        Jeu jeu = prog.getJeu();
        Carte carte = jeu.getSelectionCartes(jeu.getJoueurCourant()).estVide() ? null : jeu.getSelectionCartes(jeu.getJoueurCourant()).getCarte(jeu.getSelectionCartes(jeu.getJoueurCourant()).getTaille() - 1);
        int deplacement = carte != null ? (carte.estDeplacementGar1Plus1() && jeu.getSelectionPions(1) != null ? 1 : carte.getDeplacement()) : -1;
        int position = -1, destinationVrt = -1, destinationRge = -1, destinationChoisie = -1;
        if (jeu.getSelectionPions(0) != null && deplacement != -1) {
            position = jeu.getPlateau().getPositionPion(jeu.getSelectionPions(0));
            destinationVrt = position - deplacement;
            destinationRge = position + deplacement;
            if (jeu.getSelectionPions(1) != null && jeu.getSelectionDirections(0) != Plateau.DIRECTION_IND) {
                destinationChoisie = jeu.getSelectionDirections(0) == Plateau.DIRECTION_VRT ? destinationVrt : destinationRge;
                position = jeu.getPlateau().getPositionPion(jeu.getSelectionPions(1));
                destinationVrt = position - deplacement;
                destinationRge = position + deplacement;
            }
        }
        for (int c = Plateau.BORDURE_VRT; c < Plateau.BORDURE_RGE + 1; c++) {
            boolean bool1 = (destinationVrt != -1 && c < position) || (destinationRge != -1 && c > position);
            boolean bool2 = (c == destinationVrt && jeu.peutSelectionnerDirection(Plateau.DIRECTION_VRT)) || (c == destinationRge && jeu.peutSelectionnerDirection(Plateau.DIRECTION_RGE));
            boolean bool3 = c == destinationChoisie;
            casesVue[c].mettreAJour(bool1, bool2, false, bool3);
        }
        for (PionVue pionVue : pionsVue) {
            boolean bool1 = jeu.getSelectionPions(0) != null && jeu.getSelectionPions(0).equals(pionVue.getPion());
            boolean bool2 = jeu.getSelectionPions(1) != null && jeu.getSelectionPions(1).equals(pionVue.getPion());
            boolean bool3 = pionVue.getPion().getType().equals(Type.GAR) && jeu.getActivationPrivilegeRoi() == 2;
            pionVue.mettreAJour(jeu.peutSelectionnerPion(pionVue.getPion()), false, bool1 || bool2 || bool3);
        }
        couronneVue.mettreAJour(prog.getJeu().getPlateau().getFaceCouronne());
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Plateau plateau = prog.getJeu().getPlateau();

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

        repaint();
    }
}
