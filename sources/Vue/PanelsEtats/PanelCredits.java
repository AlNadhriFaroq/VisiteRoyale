package Vue.PanelsEtats;

import Controleur.ControleurMediateur;
import Modele.Programme;
import Vue.Adaptateurs.AdaptateurBoutons;
import Vue.*;
import Vue.ComponentsMenus.BoutonMenu;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class PanelCredits extends PanelEtat {
    BoutonMenu boutonMenuRetour;

    public PanelCredits(ControleurMediateur ctrl, InterfaceGraphique vue, Programme prog) {
        super(ctrl, vue, prog);

        boutonMenuRetour = new BoutonMenu("Retour");
        boutonMenuRetour.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));

        JPanel panelTextes = new JPanel();
        panelTextes.setBackground(new Color(0, 0, 0, 0));
        panelTextes.setLayout(new GridLayout(26, 1));
        panelTextes.add(new JLabel("CREDITS", JLabel.CENTER));
        panelTextes.add(Box.createVerticalGlue());
        panelTextes.add(new JLabel("Université Grenoble-Alpes", JLabel.CENTER));
        panelTextes.add(new JLabel("Licence Informatique générale 3e année", JLabel.CENTER));
        panelTextes.add(new JLabel("Programmation et projet logiciel", JLabel.CENTER));
        panelTextes.add(Box.createVerticalGlue());
        panelTextes.add(new JLabel("Sous la direction de :", JLabel.CENTER));
        panelTextes.add(new JLabel("Gabriela Nicole González Sáez", JLabel.CENTER));
        panelTextes.add(Box.createVerticalGlue());
        panelTextes.add(new JLabel("Développeurs :", JLabel.CENTER));
        panelTextes.add(new JLabel("Faroq Al-Nadhari", JLabel.CENTER));
        panelTextes.add(new JLabel("Nadim Babba", JLabel.CENTER));
        panelTextes.add(new JLabel("Rodolphe Beguin", JLabel.CENTER));
        panelTextes.add(new JLabel("Maxime Bouchenoua", JLabel.CENTER));
        panelTextes.add(new JLabel("Sacha Isaac--Chassande", JLabel.CENTER));
        panelTextes.add(new JLabel("Landry Rolland", JLabel.CENTER));
        panelTextes.add(Box.createVerticalGlue());
        panelTextes.add(new JLabel("Créateurs du jeu de société :", JLabel.CENTER));
        panelTextes.add(new JLabel("Auteur : Reiner Knizia", JLabel.CENTER));
        panelTextes.add(new JLabel("Illustrateur : Karl James Mountford", JLabel.CENTER));
        panelTextes.add(new JLabel("Chefs de projet : Mathilde Audinet, Adrien Fenouillet", JLabel.CENTER));
        panelTextes.add(new JLabel("Rédacteur : Mathilde Audinet, Reiner Knizia", JLabel.CENTER));
        panelTextes.add(new JLabel("Graphiste : Cindy Roth", JLabel.CENTER));
        panelTextes.add(new JLabel("Relecteur : Xavier Taverne", JLabel.CENTER));
        panelTextes.add(Box.createVerticalGlue());
        panelTextes.add(boutonMenuRetour);

        JPanel sousPanel = new JPanel();
        sousPanel.setBackground(new Color(142, 142, 225, 255));
        sousPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        sousPanel.add(new Cadre(panelTextes, 1, 1, 1, 1));

        add(new Cadre(sousPanel, 6, 6, 1, 1));
    }

    public BoutonMenu getBoutonRetour() {
        return boutonMenuRetour;
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public void mettreAJour() {
        if (prog.getEtat() == Programme.ETAT_CREDITS)
            repaint();
    }
}
