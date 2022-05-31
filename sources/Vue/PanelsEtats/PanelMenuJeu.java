package Vue.PanelsEtats;

import Controleur.ControleurMediateur;
import Modele.Programme;
import Vue.*;
import Vue.Adaptateurs.*;
import Vue.Composants.ComposantsMenus.BoutonMenu;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class PanelMenuJeu extends JPanel {
    ControleurMediateur ctrl;
    Fenetre fenetre;
    Programme prog;

    public static final int reprendre = 0;
    public static final int nouvellePartie = 1;
    public static final int sauvegardes = 2;
    public static final int options = 3;
    public static final int tutoriel = 4;
    public static final int retour = 5;
    BoutonMenu[] boutons;

    public PanelMenuJeu(ControleurMediateur ctrl, Fenetre fenetre, Programme prog) {
        super();
        this.ctrl = ctrl;
        this.fenetre = fenetre;
        this.prog = prog;

        setBackground(new Color(0, 0, 0, 0));
        setLayout(new GridBagLayout());

        /* Construction des composants */
        boutons = new BoutonMenu[6];
        boutons[reprendre] = new BoutonMenu("Reprendre");
        boutons[nouvellePartie] = new BoutonMenu("Nouvelle partie");
        boutons[sauvegardes] = new BoutonMenu("Sauvegardes");
        boutons[options] = new BoutonMenu("Options");
        boutons[tutoriel] = new BoutonMenu("Tutoriel");
        boutons[retour] = new BoutonMenu("Retour au menu principal");

        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 0, 0, 210));
        panel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        panel.setLayout(new GridBagLayout());

        /* Disposition des composants dans le panel */
        panel.add(Box.createGlue(), new GBC(0, 0, 1, 13).setWeightx(10));
        panel.add(Box.createGlue(), new GBC(2, 0, 1, 13).setWeightx(10));
        panel.add(Box.createGlue(), new GBC(1, 0).setWeight(80, 10));
        panel.add(Box.createGlue(), new GBC(1, 12).setWeight(80, 10));

        for (int i = 0; i < 6; i++) {
            panel.add(boutons[i], new GBC(1, 2 * i + 1).setWeight(80, 10).setFill(GBC.BOTH));
            if (i != 5)
                panel.add(Box.createGlue(), new GBC(1, 2 * i + 2).setWeight(80, 4));
        }

        add(Box.createGlue(), new GBC(0, 0, 1, 3).setWeightx(40));
        add(Box.createGlue(), new GBC(2, 0, 1, 3).setWeightx(40));
        add(Box.createGlue(), new GBC(1, 0).setWeight(8, 25));
        add(Box.createGlue(), new GBC(1, 2).setWeight(8, 25));
        add(panel, new GBC(1, 1).setWeight(20, 50).setFill(GBC.BOTH));

        /* Retransmission des evenements au controleur */
        for (BoutonMenu bouton : boutons) {
            bouton.addActionListener(new AdaptateurBoutons(ctrl, fenetre, prog));
            bouton.addMouseListener(new AdaptateurSouris(ctrl, fenetre, prog));
        }
    }

    public BoutonMenu getBouton(int indice) {
        return boutons[indice];
    }

    public void redimensionner() {
        for (BoutonMenu bouton : boutons)
            bouton.setFont(new Font(null).deriveFont(Font.BOLD, (float) bouton.getHeight() / 3));
    }
}
