package Vue;

import Controleur.ControleurMediateur;
import Modele.Jeu;
import Patterns.Observateur;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class InterfaceGraphique extends InterfaceUtilisateur implements Runnable {


    GraphicsDevice device;
    boolean pleinEcran;
    Jeu jeu;
    ControleurMediateur ctrl;
    JFrame frame;
    PlateauFrame plateau;
    JeuGraphique jeuGrph;
    /* autres attributs, les autres components */

    public InterfaceGraphique(Jeu jeu, ControleurMediateur ctrl) {
        this.jeu = jeu;
        this.ctrl = ctrl;
        pleinEcran = false;
        this.plateau = new PlateauFrame(this.jeu);
    }

    public static void demarrer(Jeu jeu, ControleurMediateur ctrl) {
        InterfaceGraphique vue = new InterfaceGraphique(jeu, ctrl);
        //ctrl.ajouterInterfaceUtilisateur(vue);
        SwingUtilities.invokeLater(vue);
    }

    public void run() {
        frame = this.plateau.getFrame();
        jeuGrph = new JeuGraphique(jeu);

        /* Creation des autres components */
        /*
        CarteVue carteVue = new CarteVue();
        carteVue.setCarte(this.jeu.getPioche().getCarte(0));
        carteVue.setBorder(new EmptyBorder(0, 30, 0 ,30));
        frame.add(carteVue);
        */
        /* Retransmission des evenements au controleur */
        //frame.addKeyListener(new AdaptateurClavier(vue,ctrl));
        jeuGrph.addMouseListener(new AdaptateurSouris(jeuGrph, ctrl));

        /* Mise en place de l'interface */
        frame.add(jeuGrph);


        jeu.ajouterObservateur(this);
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
    }
}
