package Vue;

import Controleur.ControleurMediateur;
import Modele.Jeu;
import Patterns.Observateur;

import javax.swing.*;
import java.awt.*;

public class InterfaceGraphique implements InterfaceUtilisateur, Runnable {
    static final int LARGEURFENETRE = 1000;
    static final int HAUTEURFENETRE = 800;

    GraphicsDevice device;
    boolean pleinEcran;
    Jeu jeu;
    ControleurMediateur ctrl;
    JFrame frame;
    JeuGraphique jeuGrph;

    public InterfaceGraphique(Jeu jeu, ControleurMediateur ctrl) {
        this.jeu = jeu;
        this.ctrl = ctrl;
        pleinEcran = false;
    }

    public static void demarrer(Jeu jeu, ControleurMediateur ctrl) {
        InterfaceGraphique vue = new InterfaceGraphique(jeu, ctrl);
        SwingUtilities.invokeLater(vue);
    }

    public void run() {
        frame = new JFrame();
        jeuGrph = new JeuGraphique(jeu);

        /* Creation des autres components */

        /* Retransmission des evenements au controleur */
        frame.addKeyListener(new AdaptateurClavier(ctrl));
        jeuGrph.addMouseListener(new AdaptateurSouris(jeuGrph, ctrl));

        /* Mise en place de l'interface */
        frame.add(jeuGrph);
        jeu.ajouterObservateur(this);
        Timer timer = new Timer(16, new AdaptateurTemps(ctrl));
        timer.start();

        /* Configuration de la fenetre */
        frame.setTitle("Visite Royale");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(LARGEURFENETRE, HAUTEURFENETRE);
        frame.setResizable(true);
        frame.setVisible(true);
    }

    public void basculerPleinEcran() {
        if (device == null)
            device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (device.isFullScreenSupported()) {
            frame.dispose();
            if (pleinEcran) {
                frame.setUndecorated(false);
                frame.setVisible(true);
                device.setFullScreenWindow(null);
            } else {
                frame.setUndecorated(true);
                frame.setVisible(true);
                device.setFullScreenWindow(frame);
            }
            pleinEcran = !pleinEcran;
        }
    }

    @Override
    public void mettreAJour() {
        /* mise a jour des components de l'interface graphique */
    }
}
