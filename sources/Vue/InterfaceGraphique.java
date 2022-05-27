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
        device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
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
        int largeur, hauteur;

        /* Configuration initiale de la fenetre */
        largeur = Toolkit.getDefaultToolkit().getScreenSize().width / 2;
        hauteur = largeur * Images.TEXTE_TITRE.getHeight(null) / Images.TEXTE_TITRE.getWidth(null);
        pages = new CardLayout();
        fenetre = new JFrame();
        fenetre.setUndecorated(true);
        fenetre.setTitle("Visite Royale");
        fenetre.setBackground(new Color(0, 0, 0, 0));
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setSize(largeur, hauteur);
        fenetre.setLocationRelativeTo(fenetre.getParent());
        fenetre.setResizable(false);
        fenetre.setLayout(pages);

        /* Affichage de la fenetre d'accueil */
        panelAccueil = new PanelAccueil(ctrl, this, prog);
        fenetre.add(panelAccueil, Integer.toString(Programme.ETAT_ACCUEIL));
        pages.show(fenetre.getContentPane(), Integer.toString(Programme.ETAT_ACCUEIL));
        fenetre.setVisible(true);

        /* Construction des composants */
        largeur = Integer.parseInt(Configuration.instance().lire("FenetreLargeur"));
        hauteur = Integer.parseInt(Configuration.instance().lire("FenetreHauteur"));
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
        fenetre.setBackground(Color.GRAY);
        fenetre.setSize(largeur, hauteur);
        fenetre.setLocationRelativeTo(fenetre.getParent());
        fenetre.setResizable(true);
        fenetre.dispose();
        fenetre.setUndecorated(false);
        ctrl.demarrerProgramme();
        fenetre.setVisible(true);
    }

    public void mettreAJourPleinEcran() {
        if (device.isFullScreenSupported()) {
            fenetre.dispose();
            if (Boolean.parseBoolean(Configuration.instance().lire("PleinEcran"))) {
                fenetre.setUndecorated(true);
                device.setFullScreenWindow(fenetre);
                fenetre.setVisible(true);
            } else {
                fenetre.setUndecorated(false);
                device.setFullScreenWindow(null);
                fenetre.setVisible(true);
            }
        }
    }

    public void redimensionner(int largeur, int hauteur) {
        fenetre.setSize(largeur, hauteur);
    }

    @Override
    public void mettreAJour() {
        if (etat != prog.getEtat()) {
            etat = prog.getEtat();
            pages.show(fenetre.getContentPane(), Integer.toString(etat));
        }
    }
}
