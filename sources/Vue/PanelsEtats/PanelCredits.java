package Vue.PanelsEtats;

import Controleur.ControleurMediateur;
import Modele.Programme;
import Vue.*;
import Vue.Adaptateurs.*;
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

        JLabel[] textes = new JLabel[25];
        textes[0] = new JLabel("CREDITS", JLabel.CENTER);
        textes[1] = new JLabel(" ", JLabel.CENTER);
        textes[2] = new JLabel("Université Grenoble-Alpes", JLabel.CENTER);
        textes[3] = new JLabel("Licence Informatique générale 3e année", JLabel.CENTER);
        textes[4] = new JLabel("Programmation et projet logiciel", JLabel.CENTER);
        textes[5] = new JLabel(" ", JLabel.CENTER);
        textes[6] = new JLabel("Sous la direction de :", JLabel.CENTER);
        textes[7] = new JLabel("Gabriela Nicole González Sáez", JLabel.CENTER);
        textes[8] = new JLabel(" ", JLabel.CENTER);
        textes[9] = new JLabel("Développeurs :", JLabel.CENTER);
        textes[10] = new JLabel("Faroq Al-Nadhari", JLabel.CENTER);
        textes[11] = new JLabel("Nadim Babba", JLabel.CENTER);
        textes[12] = new JLabel("Rodolphe Beguin", JLabel.CENTER);
        textes[13] = new JLabel("Maxime Bouchenoua", JLabel.CENTER);
        textes[14] = new JLabel("Sacha Isaac--Chassande", JLabel.CENTER);
        textes[15] = new JLabel("Landry Rolland", JLabel.CENTER);
        textes[16] = new JLabel(" ", JLabel.CENTER);
        textes[17] = new JLabel("Créateurs du jeu de société :", JLabel.CENTER);
        textes[18] = new JLabel("Auteur : Reiner Knizia", JLabel.CENTER);
        textes[19] = new JLabel("Illustrateur : Karl James Mountford", JLabel.CENTER);
        textes[20] = new JLabel("Chefs de projet : Mathilde Audinet, Adrien Fenouillet", JLabel.CENTER);
        textes[21] = new JLabel("Rédacteur : Mathilde Audinet, Reiner Knizia", JLabel.CENTER);
        textes[22] = new JLabel("Graphiste : Cindy Roth", JLabel.CENTER);
        textes[23] = new JLabel("Relecteur : Xavier Taverne", JLabel.CENTER);
        textes[24] = new JLabel(" ", JLabel.CENTER);

        for (JLabel texte : textes)
            texte.setForeground(Color.WHITE);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 0, 0, 220));
        panel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        panel.setLayout(new GridBagLayout());

        /* Disposition des composants dans le panel */
        panel.add(Box.createGlue(), new GBC(0, 0, 1, 28).setWeightx(10));
        panel.add(Box.createGlue(), new GBC(2, 0, 1, 28).setWeightx(10));
        panel.add(Box.createGlue(), new GBC(1, 0).setWeight(80, 1));
        panel.add(Box.createGlue(), new GBC(1, 27).setWeight(80, 1));

        GBC gbc = new GBC(1, 0).setWeightx(80).setFill(GBC.BOTH);
        for (int i = 0; i < textes.length; i++)
            panel.add(textes[i], gbc.setgridy(i + 1));

        panel.add(boutonRetour, new GBC(1, textes.length + 1).setWeightx(80).setAnchor(GBC.CENTER));

        add(Box.createGlue(), new GBC(0, 0, 1, 3).setWeightx(46));
        add(Box.createGlue(), new GBC(2, 0, 1, 3).setWeightx(46));
        add(Box.createGlue(), new GBC(1, 0).setWeight(8, 40));
        add(Box.createGlue(), new GBC(1, 2).setWeight(8, 40));
        add(panel, new GBC(1, 1).setWeight(8, 20).setFill(GBC.BOTH));

        /* Retransmission des événements au contrôleur */
        boutonRetour.addActionListener(new AdaptateurBoutons(ctrl, fenetre, prog));
        boutonRetour.addMouseListener(new AdaptateurSouris(ctrl, fenetre, prog));
    }

    public BoutonMenu getBoutonRetour() {
        return boutonRetour;
    }
}
