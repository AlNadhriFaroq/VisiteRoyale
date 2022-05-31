package Vue.PanelsEtats;

import Controleur.ControleurMediateur;
import Modele.*;
import Vue.Adaptateurs.AdaptateurBoutons;
import Vue.*;
import Vue.Adaptateurs.AdaptateurSouris;
import Vue.Composants.ComposantsJeu.ImageFinPartie;
import Vue.Composants.ComposantsMenus.BoutonMenu;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class PanelFinPartie extends JPanel {
    ControleurMediateur ctrl;
    Fenetre fenetre;
    Programme prog;

    ImageFinPartie img;
    BoutonMenu boutonNouvellePartie;
    BoutonMenu boutonRetour;

    public PanelFinPartie(ControleurMediateur ctrl, Fenetre fenetre, Programme prog) {
        super();
        this.ctrl = ctrl;
        this.fenetre = fenetre;
        this.prog = prog;

        setBackground(new Color(0, 0, 0, 0));
        setLayout(new GridBagLayout());

        /* Construction des composants */
        img = new ImageFinPartie(prog);
        boutonNouvellePartie = new BoutonMenu("Nouvelle partie");
        boutonRetour = new BoutonMenu("Retour au menu principal");

        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 0, 0, 205));
        panel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        panel.setLayout(new GridBagLayout());

        /* Disposition des composants dans le panel */
        panel.add(Box.createGlue(), new GBC(0, 0, 1, 4).setWeightx(10));
        panel.add(Box.createGlue(), new GBC(4, 0, 1, 4).setWeightx(10));
        panel.add(Box.createGlue(), new GBC(1, 0, 3, 1).setWeight(80, 10));
        panel.add(Box.createGlue(), new GBC(1, 4, 1, 3).setWeight(80, 10));

        panel.add(img, new GBC(1, 1, 3, 1).setWeight(80, 50).setFill(GBC.BOTH));
        panel.add(Box.createGlue(), new GBC(1, 2, 3, 1).setWeight(80, 10));
        panel.add(boutonNouvellePartie, new GBC(1, 3).setWeight(30, 20).setFill(GBC.BOTH));
        panel.add(Box.createGlue(), new GBC(2, 3).setWeight(10, 20));
        panel.add(boutonRetour, new GBC(3, 3).setWeight(40, 20).setFill(GBC.BOTH));

        add(Box.createGlue(), new GBC(0, 0, 1, 3).setWeightx(20));
        add(Box.createGlue(), new GBC(2, 0, 1, 3).setWeightx(20));
        add(Box.createGlue(), new GBC(1, 0).setWeight(60, 30));
        add(Box.createGlue(), new GBC(1, 2).setWeight(60, 30));
        add(panel, new GBC(1, 1).setWeight(60, 40).setFill(GBC.BOTH));

        /* Retransmission des événements au contrôleur */
        boutonNouvellePartie.addActionListener(new AdaptateurBoutons(ctrl, fenetre, prog));
        boutonRetour.addActionListener(new AdaptateurBoutons(ctrl, fenetre, prog));
        boutonNouvellePartie.addMouseListener(new AdaptateurSouris(ctrl, fenetre, prog));
        boutonRetour.addMouseListener(new AdaptateurSouris(ctrl, fenetre, prog));
    }

    public BoutonMenu getBoutonNouvellePartie() {
        return boutonNouvellePartie;
    }

    public BoutonMenu getBoutonRetour() {
        return boutonRetour;
    }

    public void redimensionner() {
        boutonNouvellePartie.setFont(new Font(null).deriveFont(Font.BOLD, (float) boutonNouvellePartie.getHeight() / 3));
        boutonRetour.setFont(new Font(null).deriveFont(Font.BOLD, (float) boutonRetour.getHeight() / 3));
    }

    public void mettreAJour() {
        img.mettreAJour();
        repaint();
    }
}
