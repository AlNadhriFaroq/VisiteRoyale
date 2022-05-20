package Vue;

import Controleur.ControleurMediateur;
import Global.Configuration;
import Modele.Programme;
import Vue.Adaptateurs.*;
import Vue.PanelsEtats.*;

import javax.swing.*;
import java.awt.*;

public class InterfaceGraphique extends InterfaceUtilisateur {
    GraphicsDevice device;
    JFrame fenetre;
    CardLayout panels;
    private PanelAccueil panelAccueil;
    private PanelMenuPrincipal panelMenuPrincipal;
    private PanelEnJeu panelEnJeu;
    private PanelMenuJeu panelMenuJeu;
    private PanelMenuSauvegardes panelMenuSauvegardes;
    private PanelMenuOptions panelMenuOptions;
    private PanelTutoriel panelTutoriel;
    private PanelCredits panelCredits;

    int etat;

    public InterfaceGraphique(ControleurMediateur ctrl, Programme prog) {
        super(ctrl, prog);
        etat = prog.getEtat();
    }

    public PanelAccueil getPanelAccueil() {
        return panelAccueil;
    }

    public PanelMenuPrincipal getPanelMenuPrincipal() {
        return panelMenuPrincipal;
    }

    public PanelEnJeu getPanelEnJeu() {
        return panelEnJeu;
    }

    public PanelMenuJeu getPanelMenuJeu() {
        return panelMenuJeu;
    }

    public PanelMenuSauvegardes getPanelMenuSauvegardes() {
        return panelMenuSauvegardes;
    }

    public PanelMenuOptions getPanelMenuOptions() {
        return panelMenuOptions;
    }

    public PanelTutoriel getPanelTutoriel() {
        return panelTutoriel;
    }

    public PanelCredits getPanelCredits() {
        return panelCredits;
    }

    public void run() {
        fenetre = new JFrame();
        fenetre.setSize(500, 300);
        fenetre.setLocationRelativeTo(null);
        fenetre.setUndecorated(true);
        fenetre.setResizable(false);

        panels = new CardLayout();
        fenetre.setLayout(panels);

        panelAccueil = new PanelAccueil(ctrl, prog);
        fenetre.add(panelAccueil, Integer.toString(Programme.ETAT_ACCUEIL));
        panels.show(fenetre.getContentPane(), Integer.toString(Programme.ETAT_ACCUEIL));
        fenetre.setVisible(true);

        /* Construction des components */
        panelMenuPrincipal = new PanelMenuPrincipal(prog);
        panelEnJeu = new PanelEnJeu(prog);
        panelMenuJeu = new PanelMenuJeu(prog);
        panelMenuSauvegardes = new PanelMenuSauvegardes(ctrl, prog);
        panelMenuOptions = new PanelMenuOptions(ctrl, prog);
        panelTutoriel = new PanelTutoriel(ctrl, prog);
        panelCredits = new PanelCredits(ctrl, prog);

        /* Disposition des elements dans la fenetre */
        fenetre.add(panelMenuPrincipal, Integer.toString(Programme.ETAT_MENU_PRINCIPAL));
        fenetre.add(panelEnJeu, Integer.toString(Programme.ETAT_EN_JEU));
        fenetre.add(panelMenuJeu, Integer.toString(Programme.ETAT_MENU_JEU));
        fenetre.add(panelMenuSauvegardes, Integer.toString(Programme.ETAT_MENU_SAUVEGARDES));
        fenetre.add(panelMenuOptions, Integer.toString(Programme.ETAT_MENU_OPTIONS));
        fenetre.add(panelTutoriel, Integer.toString(Programme.ETAT_TUTORIEL));
        fenetre.add(panelCredits, Integer.toString(Programme.ETAT_CREDITS));

        /* Retransmission des evenements au controleur */
        associerActions();

        prog.ajouterObservateur(this);
        prog.ajouterObservateur(panelAccueil);
        prog.ajouterObservateur(panelMenuPrincipal);
        prog.ajouterObservateur(panelEnJeu);
        prog.ajouterObservateur(panelMenuJeu);
        prog.ajouterObservateur(panelMenuSauvegardes);
        prog.ajouterObservateur(panelMenuOptions);
        prog.ajouterObservateur(panelTutoriel);
        prog.ajouterObservateur(panelCredits);
        Timer timer = new Timer(16, new AdaptateurTemps(ctrl));
        timer.start();

        /* Configuration finale de la fenetre */
        fenetre.setVisible(false);
        fenetre.dispose();
        fenetre.setTitle("Visite Royale");
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setUndecorated(false);
        Dimension dim = new Dimension(Integer.parseInt(Configuration.instance().lire("FenetreLargeur")), Integer.parseInt(Configuration.instance().lire("FenetreHauteur")));
        fenetre.setSize(800, 800);
        fenetre.setLocationRelativeTo(null);
        fenetre.setResizable(true);
        ctrl.demarrerProgramme();
        fenetre.setVisible(true);
    }

    private void associerActions() {
        fenetre.addKeyListener(new AdaptateurClavier(ctrl, this));

        panelMenuPrincipal.getBoutonJouer1vs1().addActionListener(new AdaptateurBoutons(ctrl, this, prog));
        panelMenuPrincipal.getBoutonJouerVsIA().addActionListener(new AdaptateurBoutons(ctrl, this, prog));
        panelMenuPrincipal.getBoutonSauvegardes().addActionListener(new AdaptateurBoutons(ctrl, this, prog));
        panelMenuPrincipal.getBoutonOptions().addActionListener(new AdaptateurBoutons(ctrl, this, prog));
        panelMenuPrincipal.getBoutonTutoriel().addActionListener(new AdaptateurBoutons(ctrl, this, prog));
        panelMenuPrincipal.getBoutonCredits().addActionListener(new AdaptateurBoutons(ctrl, this, prog));
        panelMenuPrincipal.getBoutonQuitter().addActionListener(new AdaptateurBoutons(ctrl, this, prog));

        panelMenuJeu.getBoutonReprendre().addActionListener(new AdaptateurBoutons(ctrl, this, prog));
        panelMenuJeu.getBoutonNouvellePartie().addActionListener(new AdaptateurBoutons(ctrl, this, prog));
        panelMenuJeu.getBoutonSauvegardes().addActionListener(new AdaptateurBoutons(ctrl, this, prog));
        panelMenuJeu.getBoutonOptions().addActionListener(new AdaptateurBoutons(ctrl, this, prog));
        panelMenuJeu.getBoutonTutoriel().addActionListener(new AdaptateurBoutons(ctrl, this, prog));
        panelMenuJeu.getBoutonRetour().addActionListener(new AdaptateurBoutons(ctrl, this, prog));

        for (int i = 0; i <panelEnJeu.getMainVrtVue().getPaquet().getTaille(); i++)
            panelEnJeu.getMainVrtVue().getCarteVue(i).addMouseListener(new AdaptateurSouris(ctrl, prog));
        for (int i = 0; i <panelEnJeu.getSelectionVrtVue().getPaquet().getTaille(); i++)
            panelEnJeu.getSelectionVrtVue().getCarteVue(i).addMouseListener(new AdaptateurSouris(ctrl, prog));
        for (int i = 0; i < 5; i++)
            panelEnJeu.getPlateauVue().getPionVue(i).addMouseListener(new AdaptateurSouris(ctrl, prog));
        for (int i = 0; i <panelEnJeu.getSelectionRgeVue().getPaquet().getTaille(); i++)
            panelEnJeu.getSelectionRgeVue().getCarteVue(i).addMouseListener(new AdaptateurSouris(ctrl, prog));
        for (int i = 0; i <panelEnJeu.getMainRgeVue().getPaquet().getTaille(); i++)
            panelEnJeu.getMainRgeVue().getCarteVue(i).addMouseListener(new AdaptateurSouris(ctrl, prog));
    }

    public void basculerPleinEcran() {
        if (device == null)
            device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (device.isFullScreenSupported()) {
            boolean pleinEcran = Boolean.parseBoolean(Configuration.instance().lire("PleinEcran"));
            fenetre.dispose();
            if (pleinEcran) {
                fenetre.setUndecorated(false);
                fenetre.setVisible(true);
                device.setFullScreenWindow(null);
            } else {
                fenetre.setUndecorated(true);
                fenetre.setVisible(true);
                device.setFullScreenWindow(fenetre);
            }
            Configuration.instance().ecrire("PleinEcran", Boolean.toString(!pleinEcran));
        }
    }

    @Override
    public void mettreAJour() {
        if (etat != prog.getEtat()) {
            etat = prog.getEtat();
            panels.show(fenetre.getContentPane(), Integer.toString(etat));
        }
    }
}
