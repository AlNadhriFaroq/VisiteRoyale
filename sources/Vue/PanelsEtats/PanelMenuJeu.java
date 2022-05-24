package Vue.PanelsEtats;

import Controleur.ControleurMediateur;
import Global.Images;
import Modele.Programme;
import Vue.*;
import Vue.Adaptateurs.AdaptateurBoutons;
import Vue.ComponentsMenus.BoutonMenu;

import javax.swing.*;
import java.awt.*;

public class PanelMenuJeu extends PanelEtat {
    BoutonMenu boutonMenuReprendre;
    BoutonMenu boutonMenuNouvellePartie;
    BoutonMenu boutonMenuSauvegardes;
    BoutonMenu boutonMenuOptions;
    BoutonMenu boutonMenuTutoriel;
    BoutonMenu boutonMenuRetour;

    public PanelMenuJeu(ControleurMediateur ctrl, InterfaceGraphique vue, Programme prog) {
        super(ctrl, vue, prog);

        imgFond = Images.FOND_JEU;

        boutonMenuReprendre = new BoutonMenu("Reprendre");
        boutonMenuNouvellePartie = new BoutonMenu("Nouvelle partie");
        boutonMenuSauvegardes = new BoutonMenu("Sauvegardes");
        boutonMenuOptions = new BoutonMenu("Options");
        boutonMenuTutoriel = new BoutonMenu("Tutoriel");
        boutonMenuRetour = new BoutonMenu("Retour au menu principal");

        boutonMenuReprendre.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));
        boutonMenuNouvellePartie.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));
        boutonMenuSauvegardes.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));
        boutonMenuOptions.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));
        boutonMenuTutoriel.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));
        boutonMenuRetour.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));

        JPanel panelBoutons = new JPanel();
        panelBoutons.setBackground(new Color(0, 0, 0, 0));
        panelBoutons.setLayout(new GridLayout(7, 1, 0, 10));

        panelBoutons.add(boutonMenuReprendre);
        panelBoutons.add(boutonMenuNouvellePartie);
        panelBoutons.add(boutonMenuSauvegardes);
        panelBoutons.add(boutonMenuOptions);
        panelBoutons.add(boutonMenuTutoriel);
        panelBoutons.add(boutonMenuRetour);

        add(new Cadre(panelBoutons, 10, 10, 8, 8));
    }

    public BoutonMenu getBoutonReprendre() {
        return boutonMenuReprendre;
    }

    public BoutonMenu getBoutonNouvellePartie() {
        return boutonMenuNouvellePartie;
    }

    public BoutonMenu getBoutonSauvegardes() {
        return boutonMenuSauvegardes;
    }

    public BoutonMenu getBoutonOptions() {
        return boutonMenuOptions;
    }

    public BoutonMenu getBoutonTutoriel() {
        return boutonMenuTutoriel;
    }

    public BoutonMenu getBoutonRetour() {
        return boutonMenuRetour;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public void mettreAJour() {
        if (prog.getEtat() == Programme.ETAT_MENU_JEU)
            repaint();
    }
}
