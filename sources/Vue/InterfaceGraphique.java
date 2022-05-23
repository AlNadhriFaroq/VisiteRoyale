package Vue;

import Controleur.ControleurMediateur;
import Global.Configuration;
import Modele.Programme;
import Vue.Adaptateurs.*;
import Vue.PanelsEtats.*;
import Vue.PanelsEtats.Panel;

import javax.swing.*;
import java.awt.*;

public class InterfaceGraphique extends InterfaceUtilisateur {
    GraphicsDevice device;
    private JFrame fenetre;
    private CardLayout pages;

    private Panel[] panels;

    int etat;

    public InterfaceGraphique(ControleurMediateur ctrl, Programme prog) {
        super(ctrl, prog);
        etat = prog.getEtat();
        panels = new Panel[8];
    }

    public Panel getPanel(int etat) {
        return panels[etat];
    }

    public void run() {
        /* Configuration initiale de la fenetre */
        fenetre = new JFrame();
        fenetre.setSize(500, 300);
        fenetre.setLocationRelativeTo(null);
        fenetre.setUndecorated(true);
        fenetre.setResizable(false);
        fenetre.setBackground(new Color(0, 0, 0, 0));
        pages = new CardLayout();
        fenetre.setLayout(pages);

        /* Affichage de la fenetre d'accueil */
        panels[Programme.ETAT_ACCUEIL] = new PanelAccueil(ctrl, this, prog);
        fenetre.add(panels[Programme.ETAT_ACCUEIL], Integer.toString(Programme.ETAT_ACCUEIL));
        pages.show(fenetre.getContentPane(), Integer.toString(Programme.ETAT_ACCUEIL));
        fenetre.setVisible(true);

        /* Construction des composants */
        panels[Programme.ETAT_MENU_PRINCIPAL] = new PanelMenuPrincipal(ctrl, this, prog);
        panels[Programme.ETAT_EN_JEU] = new PanelEnJeu(ctrl, this, prog);
        panels[Programme.ETAT_MENU_JEU] = new PanelMenuJeu(ctrl, this, prog);
        panels[Programme.ETAT_MENU_SAUVEGARDES] = new PanelMenuSauvegardes(ctrl, this, prog);
        panels[Programme.ETAT_MENU_OPTIONS] = new PanelMenuOptions(ctrl, this, prog);
        panels[Programme.ETAT_TUTORIEL] = new PanelTutoriel(ctrl, this, prog);
        panels[Programme.ETAT_CREDITS] = new PanelCredits(ctrl, this, prog);

        /* Disposition des composants dans la fenetre */
        fenetre.add(panels[Programme.ETAT_MENU_PRINCIPAL], Integer.toString(Programme.ETAT_MENU_PRINCIPAL));
        fenetre.add(panels[Programme.ETAT_EN_JEU], Integer.toString(Programme.ETAT_EN_JEU));
        fenetre.add(panels[Programme.ETAT_MENU_JEU], Integer.toString(Programme.ETAT_MENU_JEU));
        fenetre.add(panels[Programme.ETAT_MENU_SAUVEGARDES], Integer.toString(Programme.ETAT_MENU_SAUVEGARDES));
        fenetre.add(panels[Programme.ETAT_MENU_OPTIONS], Integer.toString(Programme.ETAT_MENU_OPTIONS));
        fenetre.add(panels[Programme.ETAT_TUTORIEL], Integer.toString(Programme.ETAT_TUTORIEL));
        fenetre.add(panels[Programme.ETAT_CREDITS], Integer.toString(Programme.ETAT_CREDITS));

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
