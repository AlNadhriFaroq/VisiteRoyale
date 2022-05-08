package Vue;

import Controleur.ControleurMediateur;
import Modele.Jeu;

import javax.swing.*;
import java.awt.*;

public class InterfaceGraphique extends InterfaceUtilisateur {
    static final int LARGEURFENETRE = 1000;
    static final int HAUTEURFENETRE = 800;

    GraphicsDevice device;
    boolean pleinEcran;
    JFrame frame;
    JeuVue jeuGrph;

    public InterfaceGraphique(Jeu jeu, ControleurMediateur ctrl) {
        super(jeu, ctrl);
        pleinEcran = false;
        creerFenetre();
    }

    private void creerFenetre() {
        frame = new JFrame();
        jeuGrph = new JeuVue(jeu);

        /* Creation des autres components */

        /* Retransmission des evenements au controleur */
        frame.addKeyListener(new AdaptateurClavier(this, ctrl));
        jeuGrph.addMouseListener(new AdaptateurSouris(jeuGrph, ctrl));

        /* Mise en place de l'interface */
        frame.add(jeuGrph);
        Timer timer = new Timer(16, new AdaptateurTemps(ctrl));
        timer.start();

        /* Configuration de la fenetre */
        frame.setTitle("Visite Royale");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(LARGEURFENETRE, HAUTEURFENETRE);
        frame.setResizable(true);
        frame.setVisible(true);
    }

    @Override
    public void run() {
        SwingUtilities.invokeLater(this);
    }

    @Override
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
