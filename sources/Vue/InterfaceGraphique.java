package Vue;

import Controleur.ControleurMediateur;
import Global.Configuration;
import Modele.Jeu;
import Patterns.Observateur;

import javax.swing.*;
import java.awt.*;

public class InterfaceGraphique implements Runnable, Observateur {
    GraphicsDevice device;
    Jeu jeu;
    ControleurMediateur ctrl;
    JFrame frame;
    PartieGraphique prtGrph;
    /* autres attributs */

    public InterfaceGraphique(Jeu jeu, ControleurMediateur ctrl) {
        this.jeu = jeu;
        this.ctrl = ctrl;
    }

    public static void demarrer(Jeu jeu, ControleurMediateur ctrl) {
        InterfaceGraphique vue = new InterfaceGraphique(jeu, ctrl);
        ctrl.ajouterInterfaceGraphique(vue);
        SwingUtilities.invokeLater(vue);
    }

    public void run() {
        frame = new JFrame();
        prtGrph = new PartieGraphique(jeu);

        /* Creation des autres components */

        /* Retransmission des evenements au controleur */
        frame.addKeyListener(new AdaptateurClavier(ctrl));
        prtGrph.addMouseListener(new AdaptateurSouris(prtGrph, ctrl));

        /* Mise en place de l'interface */
        frame.add(prtGrph);
        jeu.ajouterObservateur(this);

        /* Configuration de la fenetre */
        int largeur = Integer.parseInt(Configuration.instance().lire("FenetreLargeur"));
        int hauteur = Integer.parseInt(Configuration.instance().lire("FenetreHauteur"));
        frame.setTitle("Projet Jeu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(largeur, hauteur);
        frame.setResizable(true);
        frame.setVisible(true);
    }

    public void mettreAJourPleinEcran() {
        if (device == null)
            device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (device.isFullScreenSupported()) {
            frame.dispose();
            if (Boolean.parseBoolean(Configuration.instance().lire("PleinEcran"))) {
                frame.setUndecorated(true);
                frame.setVisible(true);
                device.setFullScreenWindow(frame);
            } else {
                frame.setUndecorated(false);
                frame.setVisible(true);
                device.setFullScreenWindow(null);
            }
        }
    }

    @Override
    public void mettreAJour() {
        /* mise a jour des components de l'interface graphique */
    }
}
