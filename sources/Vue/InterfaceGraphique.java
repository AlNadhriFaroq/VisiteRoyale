package Vue;

import Controleur.ControleurMediateur;
import Global.Configuration;
import Global.Images;
import Modele.Programme;
import Vue.Adaptateurs.*;
import Vue.PanelsEtats.*;

import javax.swing.*;
import java.awt.*;

public class InterfaceGraphique extends InterfaceUtilisateur {
    GraphicsDevice device;
    private JFrame fenetre;
    private CardLayout pages;

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
        /* Configuration initiale de la fenetre */
        fenetre = new JFrame();
        fenetre.setSize(700, 500);
        fenetre.setLocationRelativeTo(null);
        fenetre.setUndecorated(true);
        fenetre.setResizable(false);
        fenetre.setBackground(new Color(0, 0, 0, 0));
        pages = new CardLayout();
        fenetre.setLayout(pages);

        /* Affichage de la fenetre d'accueil */
        panelAccueil = new PanelAccueil(ctrl, this, prog);
        fenetre.add(panelAccueil, Integer.toString(Programme.ETAT_ACCUEIL));
        pages.show(fenetre.getContentPane(), Integer.toString(Programme.ETAT_ACCUEIL));
        fenetre.setVisible(true);

        /* Construction des composants */
        panelMenuPrincipal = new PanelMenuPrincipal(ctrl, this, prog);
        panelEnJeu = new PanelEnJeu(ctrl, this, prog);
        panelMenuJeu = new PanelMenuJeu(ctrl, this, prog);
        panelMenuSauvegardes = new PanelMenuSauvegardes(ctrl, this, prog);
        panelMenuOptions = new PanelMenuOptions(ctrl, this, prog);
        panelTutoriel = new PanelTutoriel(ctrl, this, prog);
        panelCredits = new PanelCredits(ctrl, this, prog);

        /* Disposition des composants dans la fenetre */
        fenetre.add(panelMenuPrincipal, Integer.toString(Programme.ETAT_MENU_PRINCIPAL));
        fenetre.add(panelEnJeu, Integer.toString(Programme.ETAT_EN_JEU));
        fenetre.add(panelMenuJeu, Integer.toString(Programme.ETAT_MENU_JEU));
        fenetre.add(panelMenuSauvegardes, Integer.toString(Programme.ETAT_MENU_SAUVEGARDES));
        fenetre.add(panelMenuOptions, Integer.toString(Programme.ETAT_MENU_OPTIONS));
        fenetre.add(panelTutoriel, Integer.toString(Programme.ETAT_TUTORIEL));
        fenetre.add(panelCredits, Integer.toString(Programme.ETAT_CREDITS));

        /* Retransmission des evenements au controleur */
        prog.ajouterObservateur(this);
        fenetre.addKeyListener(new AdaptateurClavier(ctrl, this, prog));
        Timer timer = new Timer(16, new AdaptateurTemps(ctrl));
        timer.start();

        /* Configuration finale de la fenetre */
        fenetre.setVisible(false);
        fenetre.setTitle("Visite Royale");
        fenetre.setBackground(Color.GRAY);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dim = new Dimension(Integer.parseInt(Configuration.instance().lire("FenetreLargeur")), Integer.parseInt(Configuration.instance().lire("FenetreHauteur")));
        fenetre.setSize(1300, 800);
        fenetre.setLocationRelativeTo(null);
        fenetre.setResizable(true);
        ctrl.demarrerProgramme();
        fenetre.dispose();
        fenetre.setUndecorated(false);
        fenetre.setVisible(true);
    }

    public void mettreAJourPleinEcran() {
        if (device == null)
            device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (device.isFullScreenSupported()) {
            fenetre.dispose();
            if (Boolean.parseBoolean(Configuration.instance().lire("PleinEcran"))) {
                fenetre.setUndecorated(true);
                fenetre.setVisible(true);
                device.setFullScreenWindow(fenetre);
            } else {
                fenetre.setUndecorated(false);
                fenetre.setVisible(true);
                device.setFullScreenWindow(null);
            }
        }
    }

    @Override
    public void mettreAJour() {
        if (etat != prog.getEtat()) {
            etat = prog.getEtat();
            pages.show(fenetre.getContentPane(), Integer.toString(etat));
        }
    }
}
