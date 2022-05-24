package Vue.PanelsEtats;

import Controleur.ControleurMediateur;
import Modele.Programme;
import Vue.Adaptateurs.AdaptateurBoutons;
import Vue.ComponentsMenus.BoutonMenu;
import Vue.InterfaceGraphique;

import javax.swing.*;
import java.awt.*;

public class PanelMenuOptions extends PanelEtat {
    BoutonMenu boutonMenuRetour;

    public PanelMenuOptions(ControleurMediateur ctrl, InterfaceGraphique vue, Programme prog) {
        super(ctrl, vue, prog);

        JLabel texte = new JLabel("Options");
        boutonMenuRetour = new BoutonMenu("Retour");

        boutonMenuRetour.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));

        add(texte, BorderLayout.NORTH);
        add(boutonMenuRetour);
    }

    public BoutonMenu getBoutonRetour() {
        return boutonMenuRetour;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public void mettreAJour() {
        if (prog.getEtat() == Programme.ETAT_MENU_OPTIONS)
            repaint();
    }
}
