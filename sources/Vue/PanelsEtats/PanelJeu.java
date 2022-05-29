package Vue.PanelsEtats;

import Controleur.ControleurMediateur;
import Global.Configuration;
import Global.Images;
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

    Jeu jeu;

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
        plateauVue = new PlateauVue(jeu, jeu.getPlateau());
        mainVrtVue = new PaquetVue(jeu, jeu.getMain(Jeu.JOUEUR_VRT), GBC.PAGE_START);
        mainRgeVue = new PaquetVue(jeu, jeu.getMain(Jeu.JOUEUR_RGE), GBC.PAGE_END);
        selectionVrtVue = new PaquetVue(jeu, jeu.getSelectionCartes(Jeu.JOUEUR_VRT), GBC.CENTER);
        selectionRgeVue = new PaquetVue(jeu, jeu.getSelectionCartes(Jeu.JOUEUR_RGE), GBC.CENTER);

        piocheVue = new PiocheVue(jeu.getPioche(), true);
        defausseVue = new PiocheVue(jeu.getDefausse(), false);

        boutonPause = new BoutonJeu("");
        boutonPause.setIcon(new ImageIcon(Images.TEXTE_OUVRIR_MENU.getScaledInstance(30, 30, 0)));
        boutonIndice = new BoutonJeu("");
        boutonIndice.setIcon(new ImageIcon(Images.TEXTE_INDICE.getScaledInstance(30, 30, 0)));
        boutonAnnuler = new BoutonJeu("");
        boutonAnnuler.setIcon(new ImageIcon(Images.TEXTE_ANNULER_REFAIRE.getScaledInstance(30, 30, 0)));
        boutonRefaire = new BoutonJeu("");
        boutonRefaire.setIcon(new ImageIcon(Images.tournerImage(Images.TEXTE_ANNULER_REFAIRE, 180).getScaledInstance(30, 30, 0)));
        boutonPouvoirSor = new BoutonJeu("");
        boutonPouvoirSor.setIcon(new ImageIcon(Images.POUVOIR_SOR.getScaledInstance(60, 60, 0)));
        boutonPouvoirFou = new BoutonJeu("");
        boutonPouvoirFou.setIcon(new ImageIcon(Images.POUVOIR_FOU.getScaledInstance(60, 60, 0)));
        boutonFinTour = new BoutonJeu("Fin de tour");

        ImageChateau chateauVrt = new ImageChateau(Images.CHATEAU_VRT, true);
        ImageChateau chateauRge = new ImageChateau(Images.CHATEAU_RGE, false);

        /* Disposition des composants dans le panel */
        add(plateauVue, new GBC(2, 2, 1, 3).setAnchor(GBC.CENTER));

        add(mainVrtVue, new GBC(2, 0).setAnchor(GBC.PAGE_START));
        add(mainRgeVue, new GBC(2, 6).setAnchor(GBC.PAGE_END));
        add(selectionVrtVue, new GBC(2, 1).setAnchor(GBC.PAGE_START));
        add(selectionRgeVue, new GBC(2, 5).setAnchor(GBC.PAGE_END));

        add(piocheVue, new GBC(0, 2, 2, 1).setWeight(16, 11));
        add(defausseVue, new GBC(0, 4, 2, 1).setWeight(16, 11));

        add(boutonPause, new GBC(4, 0).setWeight(8, 16).setAnchor(GBC.FIRST_LINE_END));
        add(boutonIndice, new GBC(3, 0).setWeight(8, 16).setAnchor(GBC.FIRST_LINE_END));
        add(boutonAnnuler, new GBC(0, 3).setWeight(8, 11));
        add(boutonRefaire, new GBC(1, 3).setWeight(8, 11));
        add(boutonPouvoirSor, new GBC(3, 2, 2, 1).setWeight(16, 11));
        add(boutonPouvoirFou, new GBC(3, 4, 2, 1).setWeight(16, 11));
        add(boutonFinTour, new GBC(3, 5, 2, 2).setWeight(16, 33).setAnchor(GBC.LINE_END));

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
        plateauVue.redimensionner(getHeight() / 3);
        mainVrtVue.redimensionner(getHeight() / 6);
        mainRgeVue.redimensionner(getHeight() / 6);
        selectionVrtVue.redimensionner(getHeight() / 6);
        selectionRgeVue.redimensionner(getHeight() / 6);
        piocheVue.redimensionner((8 * getHeight()) / (6 * 9));
        defausseVue.redimensionner((8 * getHeight()) / (6 * 9));
    }

    public void mettreAJour() {
        boolean mainCachee = Boolean.parseBoolean(Configuration.instance().lire("MainCachee"));
        boolean[] facesCachees = {false, false};
        boolean[] parcourables = {true, true};
        boolean[] selectionnables = {false, false};

        if (!prog.getJoueurEstIA(Jeu.JOUEUR_VRT) && !prog.getJoueurEstIA(Jeu.JOUEUR_RGE)) {
            facesCachees[1 - jeu.getJoueurCourant()] = mainCachee;
            parcourables[1 - jeu.getJoueurCourant()] = false;
        } else if (prog.getJoueurEstIA(Jeu.JOUEUR_VRT) && !prog.getJoueurEstIA(Jeu.JOUEUR_RGE)) {
            facesCachees[Jeu.JOUEUR_VRT] = mainCachee;
            parcourables[Jeu.JOUEUR_VRT] = false;
        } else if (!prog.getJoueurEstIA(Jeu.JOUEUR_VRT) && prog.getJoueurEstIA(Jeu.JOUEUR_RGE)) {
            facesCachees[Jeu.JOUEUR_RGE] = mainCachee;
            parcourables[Jeu.JOUEUR_RGE] = false;
        }
        selectionnables[jeu.getJoueurCourant()] = true;

        plateauVue.mettreAJour();
        mainVrtVue.mettreAJour(facesCachees[Jeu.JOUEUR_VRT], parcourables[Jeu.JOUEUR_VRT], selectionnables[Jeu.JOUEUR_VRT]);
        mainRgeVue.mettreAJour(facesCachees[Jeu.JOUEUR_RGE], parcourables[Jeu.JOUEUR_RGE], selectionnables[Jeu.JOUEUR_RGE]);
        selectionVrtVue.mettreAJour(false, false, false);
        selectionRgeVue.mettreAJour(false, false, false);

        piocheVue.mettreAJour();
        defausseVue.mettreAJour();

        repaint();
    }
}
