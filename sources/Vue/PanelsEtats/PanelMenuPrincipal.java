package Vue.PanelsEtats;

import Controleur.ControleurMediateur;
import Modele.Programme;
import Vue.*;
import Vue.Adaptateurs.AdaptateurBoutons;
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

        boutons = new BoutonMenu[7];
        boutons[jouer1vs1] = new BoutonMenu("Jouer 1vs1");
        boutons[jouerVsIA] = new BoutonMenu("Joueur contre IA");
        boutons[sauvegardes] = new BoutonMenu("Sauvegardes");
        boutons[options] = new BoutonMenu("Options");
        boutons[tutoriel] = new BoutonMenu("Tutoriel");
        boutons[credits] = new BoutonMenu("Crédits");
        boutons[quitter] = new BoutonMenu("Quitter");

        for (BoutonMenu bouton : boutons)
            bouton.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));

        JPanel panelBoutons = new JPanel();
        panelBoutons.setBackground(new Color(0, 0, 0, 0));
        panelBoutons.setLayout(new GridLayout(7, 1, 0, 10));
        for (BoutonMenu bouton : boutons)
            panelBoutons.add(bouton);

        add(new Cadre(panelBoutons, 1, 7, 1, 1));
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
