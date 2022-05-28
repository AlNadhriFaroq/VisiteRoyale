package Vue.PanelsEtats;

import Controleur.ControleurMediateur;
import Global.Images;
import Modele.*;
import Vue.*;
import Vue.Adaptateurs.*;
import Vue.Composants.ComposantsJeu.*;
import Vue.Composants.ComposantsMenus.BoutonMenu;

import javax.swing.*;
import java.awt.*;

public class PanelJeu extends JPanel {
    ControleurMediateur ctrl;
    Fenetre fenetre;
    Programme prog;

    Jeu jeu;

    PlateauVue plateauVue;
    PaquetVue mainVrtVue;
    PaquetVue mainRgeVue;
    PaquetVue selectionVrtVue;
    PaquetVue selectionRgeVue;

    BoutonMenu boutonMenuPause;
    BoutonMenu boutonMenuIndice;
    BoutonMenu boutonMenuAnnuler;
    BoutonMenu boutonMenuRefaire;
    BoutonMenu boutonMenuPouvoirSor;
    BoutonMenu boutonMenuPouvoirFou;
    BoutonMenu boutonMenuFinTour;

    public PanelJeu(ControleurMediateur ctrl, Fenetre fenetre, Programme prog) {
        super();
        this.ctrl = ctrl;
        this.fenetre = fenetre;
        this.prog = prog;

        jeu = prog.getJeu();

        setBackground(new Color(0, 0, 0, 0));
        setOpaque(false);
        setLayout(new GridBagLayout());

        /* Construction des composants */
        plateauVue = new PlateauVue(jeu.getPlateau());
        mainVrtVue = new PaquetVue(jeu.getMain(Jeu.JOUEUR_VRT), true);
        mainRgeVue = new PaquetVue(jeu.getMain(Jeu.JOUEUR_RGE), false);
        selectionVrtVue = new PaquetVue(jeu.getSelectionCartes(Jeu.JOUEUR_VRT), false);
        selectionRgeVue = new PaquetVue(jeu.getSelectionCartes(Jeu.JOUEUR_RGE), true);

        boutonMenuPause = new BoutonMenu("Pause");
        boutonMenuIndice = new BoutonMenu("Indice");
        boutonMenuAnnuler = new BoutonMenu("Annuler");
        boutonMenuRefaire = new BoutonMenu("Refaire");
        boutonMenuPouvoirSor = new BoutonMenu("Sorcier");
        boutonMenuPouvoirFou = new BoutonMenu("Fou");
        boutonMenuFinTour = new BoutonMenu("Fin de tour");

        ImageChateau chateauVrt = new ImageChateau(Images.CHATEAU_VRT, true);
        ImageChateau chateauRge = new ImageChateau(Images.CHATEAU_RGE, false);

        /* Disposition des composants dans le panel */
        add(plateauVue, new GBC(2, 2, 1, 3).setWeight(66, 33));

        add(mainVrtVue, new GBC(2, 0).setWeight(66, 16).setAnchor(GBC.PAGE_START));
        add(mainRgeVue, new GBC(2, 6).setWeight(66, 16).setAnchor(GBC.PAGE_END));
        add(selectionVrtVue, new GBC(2, 1).setWeight(66, 16).setAnchor(GBC.PAGE_START));
        add(selectionRgeVue, new GBC(2, 5).setWeight(66, 16).setAnchor(GBC.PAGE_END));

        add(boutonMenuPause, new GBC(4, 0).setWeight(8, 16).setAnchor(GBC.FIRST_LINE_END));
        add(boutonMenuIndice, new GBC(3, 0).setWeight(8, 16).setAnchor(GBC.FIRST_LINE_END));
        add(boutonMenuAnnuler, new GBC(0, 3).setWeight(8, 11));
        add(boutonMenuRefaire, new GBC(1, 3).setWeight(8, 11));
        add(boutonMenuPouvoirSor, new GBC(3, 2, 2, 1).setWeight(16, 11));
        add(boutonMenuPouvoirFou, new GBC(3, 4, 2, 1).setWeight(16, 11));
        add(boutonMenuFinTour, new GBC(3, 6, 2, 1).setWeight(16, 16).setAnchor(GBC.LAST_LINE_END));

        add(chateauVrt, new GBC(0, 0, 2, 2).setWeight(16, 33).setAnchor(GBC.FIRST_LINE_START).setFill(GBC.BOTH));
        add(chateauRge, new GBC(0, 5, 2, 2).setWeight(16, 33).setAnchor(GBC.LAST_LINE_START).setFill(GBC.BOTH));

        /* Retransmission des événements au contrôleur */
        for (int i = 0; i < Jeu.TAILLE_MAIN; i++) {
            mainVrtVue.getCarteVue(i).addMouseListener(new AdaptateurSouris(ctrl, fenetre, prog));
            selectionVrtVue.getCarteVue(i).addMouseListener(new AdaptateurSouris(ctrl, fenetre, prog));
            selectionRgeVue.getCarteVue(i).addMouseListener(new AdaptateurSouris(ctrl, fenetre, prog));
            mainRgeVue.getCarteVue(i).addMouseListener(new AdaptateurSouris(ctrl, fenetre, prog));
        }
        for (int i = 0; i < 5; i++)
            plateauVue.getPionVue(i).addMouseListener(new AdaptateurSouris(ctrl, fenetre, prog));
        for (int i = Plateau.BORDURE_VRT; i <= Plateau.BORDURE_RGE; i++)
            plateauVue.getCaseVue(i).addMouseListener(new AdaptateurSouris(ctrl, fenetre, prog));

        boutonMenuPause.addActionListener(new AdaptateurBoutons(ctrl, fenetre, prog));
        boutonMenuAnnuler.addActionListener(new AdaptateurBoutons(ctrl, fenetre, prog));
        boutonMenuRefaire.addActionListener(new AdaptateurBoutons(ctrl, fenetre, prog));
        boutonMenuFinTour.addActionListener(new AdaptateurBoutons(ctrl, fenetre, prog));
        boutonMenuPouvoirSor.addActionListener(new AdaptateurBoutons(ctrl, fenetre, prog));
        boutonMenuPouvoirFou.addActionListener(new AdaptateurBoutons(ctrl, fenetre, prog));
    }

    public PaquetVue getMainVue(int joueur) {
        return joueur == Jeu.JOUEUR_VRT ? mainVrtVue : mainRgeVue;
    }

    public PaquetVue getSelectionVue(int joueur) {
        return joueur == Jeu.JOUEUR_VRT ? selectionVrtVue : selectionRgeVue;
    }

    public PlateauVue getPlateauVue() {
        return plateauVue;
    }

    public BoutonMenu getBoutonPause() {
        return boutonMenuPause;
    }

    public BoutonMenu getBoutonAnnuler() {
        return boutonMenuAnnuler;
    }

    public BoutonMenu getBoutonRefaire() {
        return boutonMenuRefaire;
    }

    public BoutonMenu getBoutonFinTour() {
        return boutonMenuFinTour;
    }

    public BoutonMenu getBoutonPouvoirSor() {
        return boutonMenuPouvoirSor;
    }

    public BoutonMenu getBoutonPouvoirFou() {
        return boutonMenuPouvoirFou;
    }

    public void redimensionner() {
        plateauVue.redimensionner(getHeight() / 3);
        mainVrtVue.redimensionner(getHeight() / 6);
        mainRgeVue.redimensionner(getHeight() / 6);
        selectionVrtVue.redimensionner(getHeight() / 6);
        selectionRgeVue.redimensionner(getHeight() / 6);
    }

    public void mettreAJour() {
        boolean[] mainsCachees = {false, false};
        if (!prog.getJoueurEstIA(Jeu.JOUEUR_VRT) && !prog.getJoueurEstIA(Jeu.JOUEUR_RGE))
            mainsCachees[1 - jeu.getJoueurCourant()] = true;
        else if (prog.getJoueurEstIA(Jeu.JOUEUR_VRT) && !prog.getJoueurEstIA(Jeu.JOUEUR_RGE))
            mainsCachees[Jeu.JOUEUR_VRT] = true;
        else if (!prog.getJoueurEstIA(Jeu.JOUEUR_VRT) && prog.getJoueurEstIA(Jeu.JOUEUR_RGE))
            mainsCachees[Jeu.JOUEUR_RGE] = true;

        plateauVue.mettreAJour();
        mainVrtVue.mettreAJour(mainsCachees[Jeu.JOUEUR_VRT]);
        mainRgeVue.mettreAJour(mainsCachees[Jeu.JOUEUR_RGE]);
        selectionVrtVue.mettreAJour(false);
        selectionRgeVue.mettreAJour(false);

        repaint();
    }
}
