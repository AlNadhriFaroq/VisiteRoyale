package Vue.PanelsEtats;

import Controleur.ControleurMediateur;
import Modele.*;
import Vue.*;
import Vue.Adaptateurs.*;
import Vue.Composants.ComposantsMenus.BoutonMenu;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class PanelChoixJoueur extends JPanel {
    ControleurMediateur ctrl;
    Fenetre fenetre;
    Programme prog;

    BoutonMenu boutonGauche;
    BoutonMenu boutonDroite;

    public PanelChoixJoueur(ControleurMediateur ctrl, Fenetre fenetre, Programme prog) {
        super();
        this.ctrl = ctrl;
        this.fenetre = fenetre;
        this.prog = prog;

        setBackground(new Color(0, 0, 0, 0));
        setLayout(new GridBagLayout());

        /* Construction des composants */
        JLabel txt1 = new JLabel("Tirage du joueur qui commence.", JLabel.CENTER);
        JLabel txt2 = new JLabel("Main gauche ou main droite ?", JLabel.CENTER);

        txt1.setForeground(Color.WHITE);
        txt2.setForeground(Color.WHITE);

        boutonGauche = new BoutonMenu("Gauche");
        boutonDroite = new BoutonMenu("Droite");

        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 0, 0, 205));
        panel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        panel.setLayout(new GridBagLayout());

        /* Disposition des composants dans le panel */
        panel.add(Box.createGlue(), new GBC(0, 0, 1, 6).setWeightx(10));
        panel.add(Box.createGlue(), new GBC(4, 0, 1, 6).setWeightx(10));
        panel.add(Box.createGlue(), new GBC(1, 0, 3, 0).setWeight(80, 10));
        panel.add(Box.createGlue(), new GBC(1, 5, 3, 1).setWeight(80, 10));

        panel.add(txt1, new GBC(1, 1, 3, 1).setWeight(80, 25).setFill(GBC.BOTH));
        panel.add(txt2, new GBC(1, 2, 3, 1).setWeight(80, 25).setFill(GBC.BOTH));
        panel.add(Box.createGlue(), new GBC(1, 3, 3, 1).setWeight(80, 10));
        panel.add(boutonGauche, new GBC(1, 4).setWeight(35, 30).setFill(GBC.BOTH));
        panel.add(Box.createGlue(), new GBC(2, 4).setWeight(10, 30));
        panel.add(boutonDroite, new GBC(3, 4).setWeight(35, 30).setFill(GBC.BOTH));

        add(Box.createGlue(), new GBC(0, 0, 1, 3).setWeightx(35));
        add(Box.createGlue(), new GBC(2, 0, 1, 3).setWeightx(35));
        add(Box.createGlue(), new GBC(1, 0).setWeight(30, 40));
        add(Box.createGlue(), new GBC(1, 2).setWeight(30, 40));
        add(panel, new GBC(1, 1).setWeight(30, 20).setFill(GBC.BOTH));

        /* Retransmission des événements au contrôleur */
        boutonGauche.addActionListener(new AdaptateurBoutons(ctrl, fenetre, prog));
        boutonDroite.addActionListener(new AdaptateurBoutons(ctrl, fenetre, prog));
        boutonGauche.addMouseListener(new AdaptateurSouris(ctrl, fenetre, prog));
        boutonDroite.addMouseListener(new AdaptateurSouris(ctrl, fenetre, prog));
    }

    public BoutonMenu getBoutonGauche() {
        return boutonGauche;
    }

    public BoutonMenu getBoutonDroite() {
        return boutonDroite;
    }

    public void redimensionner() {
        boutonGauche.setFont(new Font(null).deriveFont(Font.BOLD, (float) boutonGauche.getHeight() / 3));
        boutonDroite.setFont(new Font(null).deriveFont(Font.BOLD, (float) boutonDroite.getHeight() / 3));
    }
}
