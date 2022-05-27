package Vue.PanelsEtats;

import Controleur.ControleurMediateur;
import Global.Images;
import Modele.Programme;
import Vue.*;
import Vue.Adaptateurs.*;
import Vue.ComponentsMenus.BoutonMenu;

import javax.swing.*;
import java.awt.*;

public class PanelMenuJeu extends PanelEtat {
    public static final int reprendre = 0;
    public static final int nouvellePartie = 1;
    public static final int sauvegardes = 2;
    public static final int options = 3;
    public static final int tutoriel = 4;
    public static final int retour = 5;
    BoutonMenu[] boutons;

    public PanelMenuJeu(ControleurMediateur ctrl, InterfaceGraphique vue, Programme prog) {
        super(ctrl, vue, prog);

        imgFond = Images.FOND_JEU;

        /* Construction des composants */
        boutons = new BoutonMenu[6];
        boutons[reprendre] = new BoutonMenu("Reprendre");
        boutons[nouvellePartie] = new BoutonMenu("Nouvelle partie");
        boutons[sauvegardes] = new BoutonMenu("Sauvegardes");
        boutons[options] = new BoutonMenu("Options");
        boutons[tutoriel] = new BoutonMenu("Tutoriel");
        boutons[retour] = new BoutonMenu("Retour au menu principal");

        /* Disposition des composants dans le panel */
        JPanel panelBoutons = new JPanel();
        panelBoutons.setBackground(new Color(0, 0, 0, 0));
        panelBoutons.setLayout(new GridLayout(7, 1, 0, 10));

        for (BoutonMenu bouton : boutons)
            panelBoutons.add(bouton);

        add(new Cadre(panelBoutons, 4, 4, 1, 1));

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
        if (prog.getEtat() == Programme.ETAT_MENU_JEU)
            repaint();
    }
}
