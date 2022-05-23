package Vue.PanelsEtats;

import Controleur.ControleurMediateur;
import Global.Images;
import Modele.*;
import Vue.*;
import Vue.Adaptateurs.*;
import Vue.ComponentsJeu.*;

import javax.swing.*;
import java.awt.*;

public class PanelJeu extends Panel {
    Jeu jeu;

    PaquetVue mainVrtVue;
    PaquetVue selectionVrtVue;
    PlateauVue plateauVue;
    PaquetVue selectionRgeVue;
    PaquetVue mainRgeVue;

    Bouton boutonPause;
    Bouton boutonAnnuler;
    Bouton boutonRefaire;
    Bouton boutonFinTour;
    Bouton boutonPouvoirSor;
    Bouton boutonPouvoirFou;

    public PanelJeu(ControleurMediateur ctrl, InterfaceGraphique vue, Programme prog) {
        super(ctrl, vue, prog);
        jeu = prog.getJeu();

        setLayout(new GridLayout(3, 1, 0, 10));
        imgFond = Images.FOND_JEU;

        /* Construction des composants */
        mainVrtVue = new PaquetVue(jeu.getMain(Jeu.JOUEUR_VRT));
        selectionVrtVue = new PaquetVue(jeu.getSelectionCartes(Jeu.JOUEUR_VRT));
        plateauVue = new PlateauVue(jeu.getPlateau());
        selectionRgeVue = new PaquetVue(jeu.getSelectionCartes(Jeu.JOUEUR_RGE));
        mainRgeVue = new PaquetVue(jeu.getMain(Jeu.JOUEUR_RGE));

        boutonPause = new Bouton("Pause");
        boutonAnnuler = new Bouton("Annuler");
        boutonRefaire = new Bouton("Refaire");
        boutonFinTour = new Bouton("Fin de tour");
        boutonPouvoirSor = new Bouton("Sorcier");
        boutonPouvoirFou = new Bouton("Fou");

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

        boutonPause.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));
        boutonAnnuler.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));
        boutonRefaire.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));
        boutonFinTour.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));
        boutonPouvoirSor.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));
        boutonPouvoirFou.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));

        /* Disposition des composants dans la fenetre */
        Box boxBoutonsHistorique = Box.createHorizontalBox();
        boxBoutonsHistorique.add(boutonAnnuler);
        boxBoutonsHistorique.add(Box.createHorizontalGlue());
        boxBoutonsHistorique.add(boutonRefaire);

        Box boxBoutonsPouvoirs = Box.createVerticalBox();
        boxBoutonsPouvoirs.add(boutonPouvoirSor);
        boxBoutonsPouvoirs.add(Box.createVerticalGlue());
        boxBoutonsPouvoirs.add(boutonPouvoirFou);

        Box boxMainVrt = Box.createVerticalBox();
        boxMainVrt.add(mainVrtVue);
        boxMainVrt.add(selectionVrtVue);

        Box boxMainRge = Box.createVerticalBox();
        boxMainRge.add(selectionRgeVue);
        boxMainRge.add(mainRgeVue);

        JPanel panelVrt = new JPanel();
        panelVrt.setBackground(new Color(0, 0, 0, 0));
        panelVrt.setLayout(new BorderLayout());
        panelVrt.add(Box.createGlue(), BorderLayout.WEST);
        panelVrt.add(boxMainVrt, BorderLayout.CENTER);
        panelVrt.add(boutonPause, BorderLayout.EAST);

        JPanel panelRge = new JPanel();
        panelRge.setBackground(new Color(0, 0, 0, 0));
        panelRge.setLayout(new BorderLayout());
        panelRge.add(Box.createGlue(), BorderLayout.WEST);
        panelRge.add(boxMainRge, BorderLayout.CENTER);
        panelRge.add(boutonFinTour, BorderLayout.EAST);

        JPanel panelPlateau = new JPanel();
        panelPlateau.setBackground(new Color(0, 0, 0, 0));
        panelPlateau.setLayout(new BorderLayout());
        panelPlateau.add(boxBoutonsHistorique, BorderLayout.WEST);
        panelPlateau.add(plateauVue, BorderLayout.CENTER);
        panelPlateau.add(boxBoutonsPouvoirs, BorderLayout.EAST);

        add(panelVrt);
        add(panelPlateau);
        add(panelRge);
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

    public Bouton getBoutonPause() {
        return boutonPause;
    }

    public Bouton getBoutonAnnuler() {
        return boutonAnnuler;
    }

    public Bouton getBoutonRefaire() {
        return boutonRefaire;
    }

    public Bouton getBoutonFinTour() {
        return boutonFinTour;
    }

    public Bouton getBoutonPouvoirSor() {
        return boutonPouvoirSor;
    }

    public Bouton getBoutonPouvoirFou() {
        return boutonPouvoirFou;
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
