package Vue.PanelsEtats;

import Controleur.ControleurMediateur;
import Modele.Jeu;
import Modele.Programme;
import Patterns.Observateur;
import Vue.ComponentsJeu.PaquetVue;
import Vue.ComponentsJeu.PlateauVue;

import javax.swing.*;
import java.awt.*;

public class PanelEnJeu extends JPanel implements Observateur {
    Programme prog;
    PaquetVue mainVrtVue;
    PaquetVue selectionVrtVue;
    PlateauVue plateauVue;
    PaquetVue selectionRgeVue;
    PaquetVue mainRgeVue;

    public PanelEnJeu(Programme prog) {
        this.prog = prog;
        Jeu jeu = prog.getJeu();

        setBackground(new Color(169, 56, 56, 255));
        mainVrtVue = new PaquetVue(jeu.getMain(Jeu.JOUEUR_VRT));
        selectionVrtVue = new PaquetVue(jeu.getSelectionCartes(Jeu.JOUEUR_VRT));
        plateauVue = new PlateauVue(jeu.getPlateau());
        selectionRgeVue = new PaquetVue(jeu.getSelectionCartes(Jeu.JOUEUR_RGE));
        mainRgeVue = new PaquetVue(jeu.getMain(Jeu.JOUEUR_RGE));

        mainVrtVue.setPreferredSize(new Dimension(500, 100));
        selectionVrtVue.setPreferredSize(new Dimension(500, 100));
        plateauVue.setPreferredSize(new Dimension(800, 200));
        selectionRgeVue.setPreferredSize(new Dimension(500, 100));
        mainRgeVue.setPreferredSize(new Dimension(500, 100));

        add(mainVrtVue);
        add(selectionVrtVue);
        add(plateauVue);
        add(selectionRgeVue);
        add(mainRgeVue);
    }

    public PaquetVue getMainVrtVue() {
        return mainVrtVue;
    }

    public PaquetVue getSelectionVrtVue() {
        return selectionVrtVue;
    }

    public PlateauVue getPlateauVue() {
        return plateauVue;
    }

    public PaquetVue getSelectionRgeVue() {
        return selectionRgeVue;
    }

    public PaquetVue getMainRgeVue() {
        return mainRgeVue;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D dessin = (Graphics2D) g;
    }

    @Override
    public void mettreAJour() {
        if (prog.getEtat() == Programme.ETAT_EN_JEU)
            repaint();
    }
}
