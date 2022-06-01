package Vue.PanelsEtats;

import Controleur.ControleurMediateur;
import Global.*;
import Modele.*;
import Vue.*;
import Vue.Adaptateurs.*;
import Vue.Composants.ComposantsJeu.*;

import javax.swing.*;
import java.awt.*;

public class PanelJeu extends JPanel {
    ControleurMediateur ctrl;
    Fenetre fenetre;
    Programme prog;

    PlateauVue plateauVue;
    PaquetVue mainVrtVue;
    PaquetVue mainRgeVue;
    PaquetVue selectionVrtVue;
    PaquetVue selectionRgeVue;
    PiocheVue piocheVue;
    PiocheVue defausseVue;

    BoutonJeu boutonPause;
    BoutonJeu boutonIndice;
    BoutonJeu boutonAnnuler;
    BoutonJeu boutonRefaire;
    BoutonJeu boutonPouvoirSor;
    BoutonJeu boutonPouvoirFou;
    BoutonJeu boutonFinTour;

    ImageChateau chateauVrt;
    ImageChateau chateauRge;

    public PanelJeu(ControleurMediateur ctrl, Fenetre fenetre, Programme prog) {
        super();
        this.ctrl = ctrl;
        this.fenetre = fenetre;
        this.prog = prog;

        setBackground(new Color(0, 0, 0, 0));
        setOpaque(false);
        setLayout(new GridBagLayout());

        /* Construction des composants */
        plateauVue = new PlateauVue(prog);
        mainVrtVue = new PaquetVue(prog, PaquetVue.MAIN_VRT);
        mainRgeVue = new PaquetVue(prog, PaquetVue.MAIN_RGE);
        selectionVrtVue = new PaquetVue(prog, PaquetVue.SELECTION_VRT);
        selectionRgeVue = new PaquetVue(prog, PaquetVue.SELECTION_RGE);

        piocheVue = new PiocheVue(prog, true);
        defausseVue = new PiocheVue(prog, false);

        boutonPause = new BoutonJeu("");
        boutonIndice = new BoutonJeu("");
        boutonAnnuler = new BoutonJeu("");
        boutonRefaire = new BoutonJeu("");
        boutonPouvoirSor = new BoutonJeu("");
        boutonPouvoirFou = new BoutonJeu("");
        boutonFinTour = new BoutonJeu("Fin de tour");

        chateauVrt = new ImageChateau(Images.CHATEAU_VRT, false);
        chateauRge = new ImageChateau(Images.CHATEAU_RGE, false);

        /* Disposition des composants dans le panel */
        add(plateauVue, new GBC(2, 2, 3, 2));

        add(mainVrtVue, new GBC(3, 0).setAnchor(GBC.PAGE_START));
        add(mainRgeVue, new GBC(3, 5).setAnchor(GBC.PAGE_END));
        add(selectionVrtVue, new GBC(3, 1).setAnchor(GBC.PAGE_START));
        add(selectionRgeVue, new GBC(3, 4).setAnchor(GBC.PAGE_END));

        add(piocheVue, new GBC(0, 2, 2, 1).setWeight(1, 1));
        add(defausseVue, new GBC(0, 3, 2, 1).setWeight(1, 1));

        add(boutonIndice, new GBC(5, 0).setWeight(1, 1).setAnchor(GBC.FIRST_LINE_END));
        add(boutonPause, new GBC(6, 0).setWeight(1, 1).setAnchor(GBC.FIRST_LINE_END));
        add(boutonPouvoirSor, new GBC(5, 2, 2, 1).setWeight(1, 1));
        add(boutonPouvoirFou, new GBC(5, 3, 2, 1).setWeight(1, 1));
        add(boutonFinTour, new GBC(5, 4, 2, 1).setWeight(1, 1).setAnchor(GBC.CENTER));
        add(boutonAnnuler, new GBC(5, 5, 1, 2).setWeight(1, 1).setAnchor(GBC.CENTER));
        add(boutonRefaire, new GBC(6, 5, 1, 2).setWeight(1, 1).setAnchor(GBC.CENTER));

        add(chateauVrt, new GBC(2, 0, 1, 2).setWeight(1, 1).setAnchor(GBC.PAGE_END));
        add(chateauRge, new GBC(4, 4, 1, 2).setWeight(1, 1).setAnchor(GBC.PAGE_END));

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

        piocheVue.addMouseListener(new AdaptateurSouris(ctrl, fenetre, prog));

        boutonPause.addActionListener(new AdaptateurBoutons(ctrl, fenetre, prog));
        boutonIndice.addActionListener(new AdaptateurBoutons(ctrl, fenetre, prog));
        boutonAnnuler.addActionListener(new AdaptateurBoutons(ctrl, fenetre, prog));
        boutonRefaire.addActionListener(new AdaptateurBoutons(ctrl, fenetre, prog));
        boutonFinTour.addActionListener(new AdaptateurBoutons(ctrl, fenetre, prog));
        boutonPouvoirSor.addActionListener(new AdaptateurBoutons(ctrl, fenetre, prog));
        boutonPouvoirFou.addActionListener(new AdaptateurBoutons(ctrl, fenetre, prog));

        boutonPause.addMouseListener(new AdaptateurSouris(ctrl, fenetre, prog));
        boutonIndice.addMouseListener(new AdaptateurSouris(ctrl, fenetre, prog));
        boutonAnnuler.addMouseListener(new AdaptateurSouris(ctrl, fenetre, prog));
        boutonRefaire.addMouseListener(new AdaptateurSouris(ctrl, fenetre, prog));
        boutonFinTour.addMouseListener(new AdaptateurSouris(ctrl, fenetre, prog));
        boutonPouvoirSor.addMouseListener(new AdaptateurSouris(ctrl, fenetre, prog));
        boutonPouvoirFou.addMouseListener(new AdaptateurSouris(ctrl, fenetre, prog));
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

    public PiocheVue getPiocheVue() {
        return piocheVue;
    }

    public BoutonJeu getBoutonPause() {
        return boutonPause;
    }

    public BoutonJeu getBoutonAnnuler() {
        return boutonAnnuler;
    }

    public BoutonJeu getBoutonRefaire() {
        return boutonRefaire;
    }

    public BoutonJeu getBoutonFinTour() {
        return boutonFinTour;
    }

    public BoutonJeu getBoutonPouvoirSor() {
        return boutonPouvoirSor;
    }

    public BoutonJeu getBoutonPouvoirFou() {
        return boutonPouvoirFou;
    }

    public void redimensionner() {
        int hauteurPlateau = getHeight() / 3;
        int hauteurPaquet = getHeight() / 6;
        int hauteurCarte = (8 * getHeight()) / (6 * 9);
        int largeurCarte = hauteurCarte * Images.CARTE_VIDE.getWidth(null) / Images.CARTE_VIDE.getHeight(null);
        int tailleBouton = largeurCarte * 3 / 5;
        int tailleBoutonPouvoir = largeurCarte * 5 / 6;

        plateauVue.redimensionner(hauteurPlateau);
        mainVrtVue.redimensionner(hauteurPaquet);
        mainRgeVue.redimensionner(hauteurPaquet);
        selectionVrtVue.redimensionner(hauteurPaquet);
        selectionRgeVue.redimensionner(hauteurPaquet);

        piocheVue.redimensionner(hauteurCarte);
        defausseVue.redimensionner(hauteurCarte);

        boutonPause.redimensionner(tailleBouton, tailleBouton);
        boutonIndice.redimensionner(tailleBouton, tailleBouton);
        boutonPouvoirSor.redimensionner(tailleBoutonPouvoir, tailleBoutonPouvoir);
        boutonPouvoirFou.redimensionner(tailleBoutonPouvoir, tailleBoutonPouvoir);
        boutonFinTour.redimensionner(tailleBoutonPouvoir * 2, tailleBoutonPouvoir);
        boutonAnnuler.redimensionner(tailleBouton, tailleBouton);
        boutonRefaire.redimensionner(tailleBouton, tailleBouton);

        chateauVrt.redimensionner(hauteurPlateau * 4 / 5);
        chateauRge.redimensionner(hauteurPlateau * 4 / 5);

        boutonPause.setIcon(new ImageIcon(Images.TEXTE_OUVRIR_MENU.getScaledInstance(tailleBouton - 10, tailleBouton - 10, 0)));
        boutonIndice.setIcon(new ImageIcon(Images.TEXTE_INDICE.getScaledInstance(tailleBouton - 10, tailleBouton - 10, 0)));
        boutonAnnuler.setIcon(new ImageIcon(Images.TEXTE_ANNULER_REFAIRE.getScaledInstance(tailleBouton - 10, tailleBouton - 10, 0)));
        boutonRefaire.setIcon(new ImageIcon(Images.tournerImage(Images.TEXTE_ANNULER_REFAIRE, 180).getScaledInstance(tailleBouton - 10, tailleBouton - 10, 0)));
        boutonPouvoirSor.setIcon(new ImageIcon(Images.POUVOIR_SOR.getScaledInstance(tailleBoutonPouvoir - 3, tailleBoutonPouvoir - 3, 0)));
        boutonPouvoirFou.setIcon(new ImageIcon(Images.POUVOIR_FOU.getScaledInstance(tailleBoutonPouvoir - 3, tailleBoutonPouvoir - 3, 0)));
        boutonFinTour.setFont(new Font(null).deriveFont(Font.BOLD, (float) tailleBoutonPouvoir / 4));
    }

    public void mettreAJour() {
        boolean mainCachee = Boolean.parseBoolean(Configuration.instance().lire("MainCachee"));
        boolean[] facesCachees = {false, false};
        boolean[] parcourables = {true, true};
        boolean[] selectionnables = {false, false};

        if (!prog.getJoueurEstIA(Jeu.JOUEUR_VRT) && !prog.getJoueurEstIA(Jeu.JOUEUR_RGE)) {
            facesCachees[1 - prog.getJeu().getJoueurCourant()] = mainCachee;
            parcourables[1 - prog.getJeu().getJoueurCourant()] = false;
        } else if (prog.getJoueurEstIA(Jeu.JOUEUR_VRT) && !prog.getJoueurEstIA(Jeu.JOUEUR_RGE)) {
            facesCachees[Jeu.JOUEUR_VRT] = mainCachee;
            parcourables[Jeu.JOUEUR_VRT] = false;
        } else if (!prog.getJoueurEstIA(Jeu.JOUEUR_VRT) && prog.getJoueurEstIA(Jeu.JOUEUR_RGE)) {
            facesCachees[Jeu.JOUEUR_RGE] = mainCachee;
            parcourables[Jeu.JOUEUR_RGE] = false;
        }
        selectionnables[prog.getJeu().getJoueurCourant()] = true;

        plateauVue.mettreAJour();
        mainVrtVue.mettreAJour(facesCachees[Jeu.JOUEUR_VRT], parcourables[Jeu.JOUEUR_VRT], selectionnables[Jeu.JOUEUR_VRT]);
        mainRgeVue.mettreAJour(facesCachees[Jeu.JOUEUR_RGE], parcourables[Jeu.JOUEUR_RGE], selectionnables[Jeu.JOUEUR_RGE]);
        selectionVrtVue.mettreAJour(false, false, false);
        selectionRgeVue.mettreAJour(false, false, false);

        piocheVue.mettreAJour();
        defausseVue.mettreAJour();

        boutonAnnuler.setEnabled(prog.peutAnnuler());
        boutonRefaire.setEnabled(prog.peutRefaire());
        boutonPouvoirSor.setEnabled(prog.getJeu().peutUtiliserPouvoirSorcier());
        boutonPouvoirFou.setEnabled(prog.getJeu().peutUtiliserPouvoirFou());
        boutonFinTour.setEnabled(prog.getJeu().peutFinirTour());

        boutonAnnuler.repaint();
        boutonRefaire.repaint();
        boutonPouvoirSor.repaint();
        boutonPouvoirFou.repaint();
        boutonFinTour.repaint();

        repaint();
    }
}
