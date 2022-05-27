package Vue.PanelsEtats;

import Controleur.ControleurMediateur;
import Modele.Programme;
import Vue.*;
import Vue.Adaptateurs.AdaptateurBoutons;
import Vue.Adaptateurs.AdaptateurSouris;
import Vue.ComponentsMenus.BoutonMenu;

import javax.swing.*;
import java.awt.*;

public class PanelMenuPrincipal extends PanelEtat {
    public static final int jouer1vs1 = 0;
    public static final int jouerVsIA = 1;
    public static final int sauvegardes = 2;
    public static final int options = 3;
    public static final int tutoriel = 4;
    public static final int credits = 5;
    public static final int quitter = 6;
    BoutonMenu[] boutons;

    public PanelMenuPrincipal(ControleurMediateur ctrl, InterfaceGraphique vue, Programme prog) {
        super(ctrl, vue, prog);

        /* Construction des composants */
        boutons = new BoutonMenu[7];
        boutons[jouer1vs1] = new BoutonMenu("Jouer 1vs1");
        boutons[jouerVsIA] = new BoutonMenu("Joueur contre IA");
        boutons[sauvegardes] = new BoutonMenu("Sauvegardes");
        boutons[options] = new BoutonMenu("Options");
        boutons[tutoriel] = new BoutonMenu("Tutoriel");
        boutons[credits] = new BoutonMenu("Crédits");
        boutons[quitter] = new BoutonMenu("Quitter");

        /* Disposition dans le panel */
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridheight = 9;
        gbc.weightx = 12;
        add(Box.createGlue(), gbc);

        gbc.gridx = 2;
        gbc.weightx = 75;
        add(Box.createGlue(), gbc);

        gbc.gridx = 1;
        gbc.gridheight = 1;
        gbc.weightx = 13;
        gbc.weighty = 30;
        add(Box.createGlue(), gbc);

        gbc.gridy = 8;
        add(Box.createGlue(), gbc);

        gbc.gridy = 1;
        gbc.weighty = 6;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 0, 5, 0);
        for (BoutonMenu bouton : boutons) {
            add(bouton, gbc);
            gbc.gridy++;
        }

        /* Retransmission des evenements au controleur */
        for (BoutonMenu bouton : boutons) {
            bouton.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));
            bouton.addMouseListener(new AdaptateurSouris(ctrl, vue, prog));
        }
    }

    public BoutonMenu getBouton(int indice) {
        return boutons[indice];
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public void mettreAJour() {
        if (prog.getEtat() == Programme.ETAT_MENU_PRINCIPAL)
            repaint();
    }
}
