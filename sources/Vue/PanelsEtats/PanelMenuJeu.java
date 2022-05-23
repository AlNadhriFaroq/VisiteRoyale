package Vue.PanelsEtats;

import Controleur.ControleurMediateur;
import Global.Images;
import Modele.Programme;
import Vue.*;
import Vue.Adaptateurs.AdaptateurBoutons;

import javax.swing.*;
import java.awt.*;

public class PanelMenuJeu extends Panel {
    Bouton boutonReprendre;
    Bouton boutonNouvellePartie;
    Bouton boutonSauvegardes;
    Bouton boutonOptions;
    Bouton boutonTutoriel;
    Bouton boutonRetour;

    public PanelMenuJeu(ControleurMediateur ctrl, InterfaceGraphique vue, Programme prog) {
        super(ctrl, vue, prog);

        imgFond = Images.FOND_JEU;

        boutonReprendre = new Bouton("Reprendre");
        boutonNouvellePartie = new Bouton("Nouvelle partie");
        boutonSauvegardes = new Bouton("Sauvegardes");
        boutonOptions = new Bouton("Options");
        boutonTutoriel = new Bouton("Tutoriel");
        boutonRetour = new Bouton("Retour au menu principal");

        boutonReprendre.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));
        boutonNouvellePartie.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));
        boutonSauvegardes.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));
        boutonOptions.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));
        boutonTutoriel.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));
        boutonRetour.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));

        JPanel panelBoutons = new JPanel();
        panelBoutons.setBackground(new Color(0, 0, 0, 0));
        panelBoutons.setLayout(new GridLayout(7, 1, 0, 10));

        panelBoutons.add(boutonReprendre);
        panelBoutons.add(boutonNouvellePartie);
        panelBoutons.add(boutonSauvegardes);
        panelBoutons.add(boutonOptions);
        panelBoutons.add(boutonTutoriel);
        panelBoutons.add(boutonRetour);

        add(new Cadre(panelBoutons, 10, 10, 8, 8));
    }

    public Bouton getBoutonReprendre() {
        return boutonReprendre;
    }

    public Bouton getBoutonNouvellePartie() {
        return boutonNouvellePartie;
    }

    public Bouton getBoutonSauvegardes() {
        return boutonSauvegardes;
    }

    public Bouton getBoutonOptions() {
        return boutonOptions;
    }

    public Bouton getBoutonTutoriel() {
        return boutonTutoriel;
    }

    public Bouton getBoutonRetour() {
        return boutonRetour;
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
