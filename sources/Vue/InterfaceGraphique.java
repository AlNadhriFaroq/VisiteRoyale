package Vue;

import Controleur.ControleurMediateur;
import Modele.Programme;

import javax.swing.*;
import java.awt.*;

public class InterfaceGraphique extends InterfaceUtilisateur implements Runnable {
    GraphicsDevice device;
    boolean pleinEcran;
    Programme prog;
    ControleurMediateur ctrl;
    JFrame frame;
    JeuVue jeuVue;

    public InterfaceGraphique(Programme prog, ControleurMediateur ctrl) {
        this.prog = prog;
        this.ctrl = ctrl;
        this.ctrl.nouvellePartie(false, false);
        this.ctrl.definirJoueurQuiCommence();
        pleinEcran = false;
        this.jeuVue = new JeuVue(ctrl, this.prog.getJeu());
    }

    public static void demarrer(Programme prog, ControleurMediateur ctrl) {
        InterfaceGraphique vue = new InterfaceGraphique(prog, ctrl);
        SwingUtilities.invokeLater(vue);
    }

    public void run() {
        frame = this.jeuVue.getFrame();

        /* Retransmission des evenements au controleur */
        //frame.addKeyListener(new AdaptateurClavier(vue,ctrl));
        jeuVue.addMouseListener(new AdaptateurSouris(jeuVue, ctrl));

        /* Mise en place de l'interface */
        frame.add(jeuVue);

        prog.ajouterObservateur(this);
        Timer timer = new Timer(16, new AdaptateurTemps(ctrl));
        timer.start();

        /* Configuration de la fenetre */
        frame.setTitle("Visite Royale");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        jeuVue.repaint();
        System.out.println(prog.getJeu().getJoueurCourant());
    }
}
