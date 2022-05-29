package Vue.PanelsEtats;

import Controleur.ControleurMediateur;
import Modele.Programme;
import Vue.*;
import Vue.Adaptateurs.AdaptateurBoutons;
import Vue.Adaptateurs.AdaptateurSouris;
import Vue.Composants.ComposantsMenus.BoutonMenu;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class PanelCredits extends JPanel {
    ControleurMediateur ctrl;
    Fenetre fenetre;
    Programme prog;

    BoutonMenu boutonRetour;

    public PanelCredits(ControleurMediateur ctrl, Fenetre fenetre, Programme prog) {
        super();
        this.ctrl = ctrl;
        this.fenetre = fenetre;
        this.prog = prog;

        setBackground(new Color(0, 0, 0, 0));
        setLayout(new GridBagLayout());

        /* Construction des composants */
        boutonRetour = new BoutonMenu("Retour");

        JLabel txt1 = new JLabel("CREDITS", JLabel.CENTER);
        JLabel txt2 = new JLabel("Université Grenoble-Alpes", JLabel.CENTER);
        JLabel txt3 = new JLabel("Licence Informatique générale 3e année", JLabel.CENTER);
        JLabel txt4 = new JLabel("Programmation et projet logiciel", JLabel.CENTER);
        JLabel txt5 = new JLabel("Sous la direction de :", JLabel.CENTER);
        JLabel txt6 = new JLabel("Gabriela Nicole González Sáez", JLabel.CENTER);
        JLabel txt7 = new JLabel("Développeurs :", JLabel.CENTER);
        JLabel txt8 = new JLabel("Faroq Al-Nadhari", JLabel.CENTER);
        JLabel txt9 = new JLabel("Nadim Babba", JLabel.CENTER);
        JLabel txt10 = new JLabel("Rodolphe Beguin", JLabel.CENTER);
        JLabel txt11 = new JLabel("Maxime Bouchenoua", JLabel.CENTER);
        JLabel txt12 = new JLabel("Sacha Isaac--Chassande", JLabel.CENTER);
        JLabel txt13 = new JLabel("Landry Rolland", JLabel.CENTER);
        JLabel txt14 = new JLabel("Créateurs du jeu de société :", JLabel.CENTER);
        JLabel txt15 = new JLabel("Auteur : Reiner Knizia", JLabel.CENTER);
        JLabel txt16 = new JLabel("Illustrateur : Karl James Mountford", JLabel.CENTER);
        JLabel txt17 = new JLabel("Chefs de projet : Mathilde Audinet, Adrien Fenouillet", JLabel.CENTER);
        JLabel txt18 = new JLabel("Rédacteur : Mathilde Audinet, Reiner Knizia", JLabel.CENTER);
        JLabel txt19 = new JLabel("Graphiste : Cindy Roth", JLabel.CENTER);
        JLabel txt20 = new JLabel("Relecteur : Xavier Taverne", JLabel.CENTER);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(142, 142, 225, 255));
        panel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        panel.setLayout(new GridBagLayout());

        /* Disposition des composants dans le panel */
        panel.add(Box.createGlue(), new GBC(0, 0, 1, 28).setWeightx(10));
        panel.add(Box.createGlue(), new GBC(2, 0, 1, 28).setWeightx(10));
        panel.add(Box.createGlue(), new GBC(1, 0).setWeight(80, 12));
        panel.add(Box.createGlue(), new GBC(1, 27).setWeight(80, 12));

        GBC gbc = new GBC(1, 1).setWeight(80, 1).setFill(GBC.BOTH);
        panel.add(txt1, gbc);
        panel.add(Box.createGlue(), gbc.setgridy(2).setWeighty(12));
        panel.add(txt2, gbc.setgridy(3));
        panel.add(txt3, gbc.setgridy(4));
        panel.add(txt4, gbc.setgridy(5));
        panel.add(Box.createGlue(), gbc.setgridy(6).setWeighty(12));
        panel.add(txt5, gbc.setgridy(7));
        panel.add(txt6, gbc.setgridy(8));
        panel.add(Box.createGlue(), gbc.setgridy(9).setWeighty(12));
        panel.add(txt7, gbc.setgridy(10));
        panel.add(txt8, gbc.setgridy(11));
        panel.add(txt9, gbc.setgridy(12));
        panel.add(txt10, gbc.setgridy(13));
        panel.add(txt11, gbc.setgridy(14));
        panel.add(txt12, gbc.setgridy(15));
        panel.add(txt13, gbc.setgridy(16));
        panel.add(Box.createGlue(), gbc.setgridy(17).setWeighty(12));
        panel.add(txt14, gbc.setgridy(18));
        panel.add(txt15, gbc.setgridy(19));
        panel.add(txt16, gbc.setgridy(20));
        panel.add(txt17, gbc.setgridy(21));
        panel.add(txt18, gbc.setgridy(22));
        panel.add(txt19, gbc.setgridy(23));
        panel.add(txt20, gbc.setgridy(24));
        panel.add(Box.createGlue(), gbc.setgridy(25).setWeighty(12));
        panel.add(boutonRetour, gbc.setgridy(26).setFill(GBC.VERTICAL));

        add(Box.createGlue(), new GBC(0, 0, 1, 3).setWeightx(46));
        add(Box.createGlue(), new GBC(2, 0, 1, 3).setWeightx(46));
        add(Box.createGlue(), new GBC(1, 0).setWeight(8, 20));
        add(Box.createGlue(), new GBC(1, 2).setWeight(8, 20));
        add(panel, new GBC(1, 1).setWeight(8, 60).setFill(GBC.BOTH));

        /* Retransmission des événements au contrôleur */
        boutonRetour.addActionListener(new AdaptateurBoutons(ctrl, fenetre, prog));
        boutonRetour.addMouseListener(new AdaptateurSouris(ctrl, fenetre, prog));
    }

    public BoutonMenu getBoutonRetour() {
        return boutonRetour;
    }
}
