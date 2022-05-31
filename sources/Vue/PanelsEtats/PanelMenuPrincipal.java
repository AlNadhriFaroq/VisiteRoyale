package Vue.PanelsEtats;

import Controleur.ControleurMediateur;
import Modele.Programme;
import Vue.*;
import Vue.Adaptateurs.*;
import Vue.Composants.ComposantsMenus.BoutonMenu;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class PanelMenuPrincipal extends JPanel {
    ControleurMediateur ctrl;
    Fenetre fenetre;
    Programme prog;

    public static final int jouer1vs1 = 0;
    public static final int jouerVsIA = 1;
    public static final int sauvegardes = 2;
    public static final int options = 3;
    public static final int tutoriel = 4;
    public static final int credits = 5;
    public static final int quitter = 6;
    BoutonMenu[] boutons;

    public PanelMenuPrincipal(ControleurMediateur ctrl, Fenetre fenetre, Programme prog) {
        super();
        this.ctrl = ctrl;
        this.fenetre = fenetre;
        this.prog = prog;

        setBackground(new Color(0, 0, 0, 0));
        setLayout(new GridBagLayout());

        /* Construction des composants */
        boutons = new BoutonMenu[7];
        boutons[jouer1vs1] = new BoutonMenu("Jouer 1vs1");
        boutons[jouerVsIA] = new BoutonMenu("Joueur contre IA");
        boutons[sauvegardes] = new BoutonMenu("Sauvegardes");
        boutons[options] = new BoutonMenu("Options");
        boutons[tutoriel] = new BoutonMenu("Tutoriel");
        boutons[credits] = new BoutonMenu("Crédits");
        boutons[quitter] = new BoutonMenu("Quitter");

        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 0, 0, 200));
        panel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        panel.setLayout(new GridBagLayout());

        /* Disposition dans le panel */
        panel.add(Box.createGlue(), new GBC(0, 0, 1, 15).setWeightx(10));
        panel.add(Box.createGlue(), new GBC(2, 0, 1, 15).setWeightx(10));
        panel.add(Box.createGlue(), new GBC(1, 0).setWeight(80, 26));
        panel.add(Box.createGlue(), new GBC(1, 14).setWeight(80, 26));

        for (int i = 0; i < 7; i++) {
            panel.add(boutons[i], new GBC(1, 2 * i + 1).setWeight(80, 5).setFill(GBC.BOTH));
            if (i != 6)
                panel.add(Box.createGlue(), new GBC(1, 2 * i + 2).setWeight(80, 2));
        }

        add(panel, new GBC(0, 0).setWeight(15, 1).setFill(GBC.BOTH));
        add(Box.createGlue(), new GBC(1, 0).setWeight(85, 1));

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
