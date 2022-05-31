package Vue;

import Controleur.ControleurMediateur;
import Global.*;
import Modele.*;
import Vue.Adaptateurs.*;
import Vue.Composants.ImageFond;
import Vue.PanelsEtats.*;

import javax.swing.*;
import java.awt.*;

public class Fenetre extends JFrame {
    GraphicsDevice device;

    ControleurMediateur ctrl;
    Programme prog;

    private final ImageFond imageFond;

    private final PanelJeu panelJeu;

    private final CardLayout layoutMenu;
    private final JPanel panelMenu;
    private final PanelChoixJoueur panelChoixJoueur;
    private final PanelFinPartie panelFinPartie;
    private final PanelMenuPrincipal panelMenuPrincipal;
    private final PanelMenuJeu panelMenuJeu;
    private final PanelMenuSauvegardes panelMenuSauvegardes;
    private final PanelMenuOptions panelMenuOptions;
    private final PanelTutoriel panelTutoriel;
    private final PanelCredits panelCredits;

    public Fenetre(ControleurMediateur ctrl, Programme prog) {
        super();
        this.ctrl = ctrl;
        this.prog = prog;
        device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        int largeur, hauteur;

        /* Configuration initiale de la fenêtre */
        largeur = Toolkit.getDefaultToolkit().getScreenSize().width / 2;
        hauteur = largeur * Images.TEXTE_TITRE.getHeight(null) / Images.TEXTE_TITRE.getWidth(null);

        setUndecorated(true);
        setTitle("Visite Royale");
        setIconImage(Images.ICONE);
        setBackground(new Color(0, 0, 0, 0));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(largeur, hauteur);
        setLocationRelativeTo(getParent());
        setResizable(false);

        imageFond = new ImageFond(prog);
        imageFond.setBounds(0, 0, getWidth(), getHeight());
        JLayeredPane pane = getLayeredPane();
        pane.setOpaque(false);
        pane.add(imageFond, JLayeredPane.DEFAULT_LAYER);
        setVisible(true);

        /* Construction des composants */
        panelJeu = new PanelJeu(ctrl, this, prog);

        layoutMenu = new CardLayout();
        panelMenu = new JPanel(layoutMenu);
        panelMenu.setBackground(new Color(0, 0, 0, 0));
        panelMenu.setOpaque(false);
        panelChoixJoueur = new PanelChoixJoueur(ctrl, this, prog);
        panelFinPartie = new PanelFinPartie(ctrl, this, prog);
        panelMenuPrincipal = new PanelMenuPrincipal(ctrl, this, prog);
        panelMenuJeu = new PanelMenuJeu(ctrl, this, prog);
        panelMenuSauvegardes = new PanelMenuSauvegardes(ctrl, this, prog);
        panelMenuOptions = new PanelMenuOptions(ctrl, this, prog);
        panelTutoriel = new PanelTutoriel(ctrl, this, prog);
        panelCredits = new PanelCredits(ctrl, this, prog);

        /* Disposition des composants dans la fenetre */
        panelMenu.add(panelChoixJoueur, "choixJoueur");
        panelMenu.add(panelFinPartie, "finPartie");
        panelMenu.add(panelMenuPrincipal, "menuPrincipal");
        panelMenu.add(panelMenuJeu, "menuJeu");
        panelMenu.add(panelMenuSauvegardes, "menuSauvegardes");
        panelMenu.add(panelMenuOptions, "menuOptions");
        panelMenu.add(panelTutoriel, "tutoriel");
        panelMenu.add(panelCredits, "credits");

        pane.add(panelJeu, JLayeredPane.PALETTE_LAYER);
        pane.add(panelMenu, JLayeredPane.MODAL_LAYER);

        /* Retransmission des evenements au controleur */
        addKeyListener(new AdaptateurClavier(ctrl, this, prog));
        addComponentListener(new AdaptateurComposant(ctrl, this, prog));
    }

    public void demarrer() {
        /* Configuration finale de la fenêtre */
        int largeur = Integer.parseInt(Configuration.instance().lire("FenetreLargeur"));
        int hauteur = Integer.parseInt(Configuration.instance().lire("FenetreHauteur"));

        setVisible(false);
        setBackground(Color.GRAY);
        setSize(largeur, hauteur);
        setLocationRelativeTo(getParent());
        setResizable(true);
        dispose();
        setUndecorated(false);
        ctrl.demarrerProgramme();
        setVisible(true);
        mettreAJourPleinEcran();
    }

    public PanelChoixJoueur getPanelChoixJoueur() {
        return panelChoixJoueur;
    }

    public PanelJeu getPanelJeu() {
        return panelJeu;
    }

    public PanelFinPartie getPanelFinPartie() {
        return panelFinPartie;
    }

    public PanelMenuPrincipal getPanelMenuPrincipal() {
        return panelMenuPrincipal;
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

    public void mettreAJourPleinEcran() {
        if (device.isFullScreenSupported()) {
            dispose();
            if (Boolean.parseBoolean(Configuration.instance().lire("PleinEcran"))) {
                setUndecorated(true);
                device.setFullScreenWindow(this);
            } else {
                setUndecorated(false);
                device.setFullScreenWindow(null);
            }
            setVisible(true);
            redimensionner();
        }
    }

    public void redimensionner() {
        int largeur = getContentPane().getSize().width;
        int hauteur = getContentPane().getSize().height;

        imageFond.setBounds(0, 0, largeur, hauteur);

        panelJeu.setBounds(0, 0, largeur, hauteur);
        panelMenu.setBounds(0, 0, largeur, hauteur);
        panelChoixJoueur.setBounds(0, 0, largeur, hauteur);
        panelFinPartie.setBounds(0, 0, largeur, hauteur);
        panelMenuPrincipal.setBounds(0, 0, largeur, hauteur);
        panelMenuJeu.setBounds(0, 0, largeur, hauteur);
        panelMenuSauvegardes.setBounds(0, 0, largeur, hauteur);
        panelMenuOptions.setBounds(0, 0, largeur, hauteur);
        panelTutoriel.setBounds(0, 0, largeur, hauteur);
        panelCredits.setBounds(0, 0, largeur, hauteur);

        panelChoixJoueur.redimensionner();
        panelJeu.redimensionner();
        panelFinPartie.redimensionner();
        panelMenuPrincipal.redimensionner();
        panelMenuJeu.redimensionner();
        panelMenuSauvegardes.redimensionner();
    }

    public void mettreAJour() {
        imageFond.mettreAJour();

        if (prog.getEtat() == Programme.ETAT_EN_JEU || prog.getJeu().getEtatJeu() != Jeu.ETAT_FIN_DE_PARTIE) {
            panelJeu.mettreAJour();
            panelJeu.setVisible(true);
        } else {
            panelJeu.setVisible(false);
        }

        if (prog.getEtat() == Programme.ETAT_EN_JEU &&
                (prog.getJeu().getEtatJeu() == Jeu.ETAT_CHOIX_CARTE ||
                        prog.getJeu().getEtatJeu() == Jeu.ETAT_CHOIX_PION ||
                        prog.getJeu().getEtatJeu() == Jeu.ETAT_CHOIX_DIRECTION)) {
            panelMenu.setVisible(false);
        } else {
            panelMenu.setVisible(true);
            switch (prog.getEtat()) {
                case Programme.ETAT_EN_JEU:
                    switch (prog.getJeu().getEtatJeu()) {
                        case Jeu.ETAT_CHOIX_JOUEUR:
                            panelChoixJoueur.repaint();
                            layoutMenu.show(panelMenu, "choixJoueur");
                            break;
                        case Jeu.ETAT_FIN_DE_PARTIE:
                            panelFinPartie.mettreAJour();
                            layoutMenu.show(panelMenu, "finPartie");
                            break;
                    }
                    break;
                case Programme.ETAT_MENU_PRINCIPAL:
                    panelMenuPrincipal.repaint();
                    layoutMenu.show(panelMenu, "menuPrincipal");
                    break;
                case Programme.ETAT_MENU_JEU:
                    panelMenuJeu.repaint();
                    layoutMenu.show(panelMenu, "menuJeu");
                    break;
                case Programme.ETAT_MENU_SAUVEGARDES:
                    panelMenuSauvegardes.mettreAJour();
                    layoutMenu.show(panelMenu, "menuSauvegardes");
                    break;
                case Programme.ETAT_MENU_OPTIONS:
                    panelMenuOptions.mettreAJour();
                    layoutMenu.show(panelMenu, "menuOptions");
                    break;
                case Programme.ETAT_TUTORIEL:
                    panelTutoriel.mettreAJour();
                    layoutMenu.show(panelMenu, "tutoriel");
                    break;
                case Programme.ETAT_CREDITS:
                    panelCredits.repaint();
                    layoutMenu.show(panelMenu, "credits");
                    break;
            }
        }

        repaint();
    }
}
