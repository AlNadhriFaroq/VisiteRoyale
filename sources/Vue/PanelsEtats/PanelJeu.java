package Vue.PanelsEtats;

import Controleur.ControleurMediateur;
import Global.Images;
import Modele.*;
import Vue.*;
import Vue.Adaptateurs.*;
import Vue.ComponentsJeu.*;
import Vue.ComponentsMenus.BoutonMenu;

import java.awt.*;

public class PanelJeu extends PanelEtat {
    Jeu jeu;

    PaquetVue mainVrtVue;
    PaquetVue selectionVrtVue;
    PlateauVue plateauVue;
    PaquetVue selectionRgeVue;
    PaquetVue mainRgeVue;

    BoutonMenu boutonMenuPause;
    BoutonMenu boutonMenuIndice;
    BoutonMenu boutonMenuAnnuler;
    BoutonMenu boutonMenuRefaire;
    BoutonMenu boutonMenuPouvoirSor;
    BoutonMenu boutonMenuPouvoirFou;
    BoutonMenu boutonMenuFinTour;

    public PanelJeu(ControleurMediateur ctrl, InterfaceGraphique vue, Programme prog) {
        super(ctrl, vue, prog);
        jeu = prog.getJeu();

        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        imgFond = Images.FOND_JEU;

        /* Construction des composants */
        mainVrtVue = new PaquetVue(jeu.getMain(Jeu.JOUEUR_VRT));
        selectionVrtVue = new PaquetVue(jeu.getSelectionCartes(Jeu.JOUEUR_VRT));
        plateauVue = new PlateauVue(jeu.getPlateau());
        selectionRgeVue = new PaquetVue(jeu.getSelectionCartes(Jeu.JOUEUR_RGE));
        mainRgeVue = new PaquetVue(jeu.getMain(Jeu.JOUEUR_RGE));

        boutonMenuPause = new BoutonMenu("Pause");
        boutonMenuIndice = new BoutonMenu("Indice");
        boutonMenuAnnuler = new BoutonMenu("Annuler");
        boutonMenuRefaire = new BoutonMenu("Refaire");
        boutonMenuPouvoirSor = new BoutonMenu("Sorcier");
        boutonMenuPouvoirFou = new BoutonMenu("Fou");
        boutonMenuFinTour = new BoutonMenu("Fin de tour");

        ImageChateau chateauVrt = new ImageChateau(Images.CHATEAU_VRT, true);
        ImageChateau chateauRge = new ImageChateau(Images.CHATEAU_RGE, false);

        /* Retransmission des evenements au controleur */
        for (int i = 0; i < Jeu.TAILLE_MAIN; i++) {
            mainVrtVue.getCarteVue(i).addMouseListener(new AdaptateurSouris(ctrl, vue, prog));
            selectionVrtVue.getCarteVue(i).addMouseListener(new AdaptateurSouris(ctrl, vue, prog));
            selectionRgeVue.getCarteVue(i).addMouseListener(new AdaptateurSouris(ctrl, vue, prog));
            mainRgeVue.getCarteVue(i).addMouseListener(new AdaptateurSouris(ctrl, vue, prog));
        }
        for (int i = 0; i < 5; i++)
            plateauVue.getPionVue(i).addMouseListener(new AdaptateurSouris(ctrl, vue, prog));
        for (int i = Plateau.BORDURE_VRT; i <= Plateau.BORDURE_RGE; i++)
            plateauVue.getCaseVue(i).addMouseListener(new AdaptateurSouris(ctrl, vue, prog));

        boutonMenuPause.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));
        boutonMenuAnnuler.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));
        boutonMenuRefaire.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));
        boutonMenuFinTour.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));
        boutonMenuPouvoirSor.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));
        boutonMenuPouvoirFou.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));

        /* Disposition des composants dans la fenetre */
        add(chateauVrt, new GridBagConstraints(0, 0, 2, 2, 1.0 / 15.0, 1.0 / 6.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        add(mainVrtVue, new GridBagConstraints(2, 0, 1, 1, 10.0 / 15.0, 1.0 / 6.0, GridBagConstraints.PAGE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        add(selectionVrtVue, new GridBagConstraints(2, 1, 1, 1, 10.0 / 15.0, 1.0 / 6.0, GridBagConstraints.PAGE_END, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        add(boutonMenuIndice, new GridBagConstraints(3, 0, 1, 1, 1.0 / 15.0, 1.0 / 6.0, GridBagConstraints.FIRST_LINE_END, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        add(boutonMenuPause, new GridBagConstraints(4, 0, 1, 1, 1.0 / 15.0, 1.0 / 6.0, GridBagConstraints.FIRST_LINE_END, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

        add(boutonMenuAnnuler, new GridBagConstraints(0, 3, 1, 1, 1.0 / 15.0, 1.0 / 9.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        add(boutonMenuRefaire, new GridBagConstraints(1, 3, 1, 1, 1.0 / 15.0, 1.0 / 9.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

        add(plateauVue, new GridBagConstraints(2, 2, 1, 3, 10.0 / 15.0, 1.0 / 9.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

        add(boutonMenuPouvoirSor, new GridBagConstraints(3, 2, 2, 1, 1.0 / 15.0, 1.0 / 9.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        add(boutonMenuPouvoirFou, new GridBagConstraints(3, 4, 2, 1, 1.0 / 15.0, 1.0 / 9.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

        add(chateauRge, new GridBagConstraints(0, 5, 2, 2, 1.0 / 15.0, 1.0 / 6.0, GridBagConstraints.LAST_LINE_START, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        add(selectionRgeVue, new GridBagConstraints(2, 5, 1, 1, 10.0 / 15.0, 1.0 / 6.0, GridBagConstraints.PAGE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        add(mainRgeVue, new GridBagConstraints(2, 6, 1, 1, 10.0 / 15.0, 1.0 / 6.0, GridBagConstraints.PAGE_END, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        add(boutonMenuFinTour, new GridBagConstraints(3, 6, 2, 1, 1.0 / 15.0, 1.0 / 6.0, GridBagConstraints.LAST_LINE_END, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public void mettreAJour() {
        if (prog.getEtat() == Programme.ETAT_EN_JEU && (prog.getJeu().getEtatJeu() == Jeu.ETAT_CHOIX_CARTE || prog.getJeu().getEtatJeu() == Jeu.ETAT_CHOIX_PION || prog.getJeu().getEtatJeu() == Jeu.ETAT_CHOIX_DIRECTION)) {
            boolean[] mainsCachees = {false, false};
            if (!prog.getJoueurEstIA(Jeu.JOUEUR_VRT) && !prog.getJoueurEstIA(Jeu.JOUEUR_RGE))
                mainsCachees[1 - jeu.getJoueurCourant()] = true;
            else if (prog.getJoueurEstIA(Jeu.JOUEUR_VRT) && !prog.getJoueurEstIA(Jeu.JOUEUR_RGE))
                mainsCachees[Jeu.JOUEUR_VRT] = true;
            else if (!prog.getJoueurEstIA(Jeu.JOUEUR_VRT) && prog.getJoueurEstIA(Jeu.JOUEUR_RGE))
                mainsCachees[Jeu.JOUEUR_RGE] = true;

            mainVrtVue.mettreAJour(mainsCachees[Jeu.JOUEUR_VRT]);
            selectionVrtVue.mettreAJour(false);
            plateauVue.mettreAJour();
            selectionRgeVue.mettreAJour(false);
            mainRgeVue.mettreAJour(mainsCachees[Jeu.JOUEUR_RGE]);

            repaint();
        }
    }
}
