package Vue.PanelsEtats;

import Controleur.ControleurMediateur;
import Modele.Jeu;
import Modele.Programme;
import Vue.*;
import Vue.Adaptateurs.*;
import Vue.Composants.ComposantsMenus.*;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.util.regex.Pattern;

public class PanelMenuSauvegardes extends JPanel {
    ControleurMediateur ctrl;
    Fenetre fenetre;
    Programme prog;

    BoutonSauvegarde[] boutonsSauvegardes;
    BoutonSupprimer[] boutonsSupprimer;
    BoutonMenu boutonMenuRetour;

    public PanelMenuSauvegardes(ControleurMediateur ctrl, Fenetre fenetre, Programme prog) {
        super();
        this.ctrl = ctrl;
        this.fenetre = fenetre;
        this.prog = prog;

        setBackground(new Color(0, 0, 0, 0));
        setLayout(new GridBagLayout());

        /* Construction des composants */
        JLabel texte = new JLabel("Sauvegardes");
        texte.setFont(new Font(null).deriveFont(30f));

        boutonsSauvegardes = new BoutonSauvegarde[Programme.NB_SAUVEGARDES];
        boutonsSupprimer = new BoutonSupprimer[Programme.NB_SAUVEGARDES];
        for (int i = 0; i < Programme.NB_SAUVEGARDES; i++) {
            boutonsSauvegardes[i] = new BoutonSauvegarde("Sauvegarde vide");
            boutonsSupprimer[i] = new BoutonSupprimer("X");
        }

        boutonMenuRetour = new BoutonMenu("Retour");

        JPanel panel = new JPanel();
        panel.setBackground(new Color(142, 142, 225, 255));
        panel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        panel.setLayout(new GridBagLayout());

        /* Disposition des composants dans le panel */
        panel.add(Box.createGlue(), new GBC(0, 0, 1, Programme.NB_SAUVEGARDES * 2 + 5).setWeightx(10));
        panel.add(Box.createGlue(), new GBC(17, 0, 1, Programme.NB_SAUVEGARDES * 2 + 5).setWeightx(10));
        panel.add(Box.createGlue(), new GBC(1, 0, 16, 1).setWeight(80, 10));
        panel.add(Box.createGlue(), new GBC(1, Programme.NB_SAUVEGARDES * 2 + 4, 16, 1).setWeight(80, 10));

        int weighty = (80 - ((Programme.NB_SAUVEGARDES + 1) * 2)) / (Programme.NB_SAUVEGARDES + 2);
        panel.add(texte, new GBC(1, 1, 16, 1).setWeight(80, weighty).setAnchor(GBC.LINE_START));
        for (int i = 0; i < 16; i++)
            panel.add(Box.createGlue(), new GBC(i + 1, 2).setWeight(5, 2));
        for (int i = 0; i < Programme.NB_SAUVEGARDES; i++) {
            if (i != 0)
                panel.add(Box.createGlue(), new GBC(1, 2 * i + 3, i, 1).setWeight(i * 5, weighty));
            panel.add(boutonsSauvegardes[i], new GBC(i + 1, 2 * i + 3, 10, 1).setWeight(50, weighty).setFill(GBC.BOTH));
            panel.add(boutonsSupprimer[i], new GBC(i + 11, 2 * i + 3, 2, 1).setWeight(10, weighty).setFill(GBC.BOTH));
            if (i != Programme.NB_SAUVEGARDES - 1)
                panel.add(Box.createGlue(), new GBC(i + 13, 2 * i + 3, 4 - i, 1).setWeight(20 - (i * 5), weighty));
            panel.add(Box.createGlue(), new GBC(1, 2 * i + 4, 16, 1).setWeight(80, 2));
        }
        panel.add(boutonMenuRetour, new GBC(1, Programme.NB_SAUVEGARDES * 2 + 3, 16, 1).setWeight(80, weighty).setAnchor(GBC.LINE_END));

        add(Box.createGlue(), new GBC(0, 0, 1, 3).setWeightx(30));
        add(Box.createGlue(), new GBC(2, 0, 1, 3).setWeightx(30));
        add(Box.createGlue(), new GBC(1, 0).setWeight(40, 15));
        add(Box.createGlue(), new GBC(1, 2).setWeight(40, 15));
        add(panel, new GBC(1, 1).setWeight(40, 70).setFill(GBC.BOTH));

        /* Retransmission des événements au contrôleur */
        for (BoutonSauvegarde bouton : boutonsSauvegardes) {
            bouton.addActionListener(new AdaptateurBoutons(ctrl, fenetre, prog));
            bouton.addMouseListener(new AdaptateurSouris(ctrl, fenetre, prog));
        }
        for (BoutonSupprimer bouton : boutonsSupprimer) {
            bouton.addActionListener(new AdaptateurBoutons(ctrl, fenetre, prog));
            bouton.addMouseListener(new AdaptateurSouris(ctrl, fenetre, prog));
        }
        boutonMenuRetour.addActionListener(new AdaptateurBoutons(ctrl, fenetre, prog));
        boutonMenuRetour.addMouseListener(new AdaptateurSouris(ctrl, fenetre, prog));
    }

    public BoutonSauvegarde getBoutonSauvegarde(int indice) {
        return boutonsSauvegardes[indice];
    }

    public BoutonSupprimer getBoutonSupprimer(int indice) {
        return boutonsSupprimer[indice];
    }

    public BoutonMenu getBoutonRetour() {
        return boutonMenuRetour;
    }

    public void mettreAJour() {
        for (int i = 0; i < prog.getSauvegardes().length; i++) {
            boutonsSauvegardes[i].setEnabled(true);
            boutonsSupprimer[i].setEnabled(true);
            boutonsSauvegardes[i].setText(prog.getSauvegardes()[i].split(Pattern.quote("."))[0]);
        }
        for (int i = prog.getSauvegardes().length; i < Programme.NB_SAUVEGARDES; i++) {
            boutonsSauvegardes[i].setEnabled(prog.getJeu().getEtatJeu() != Jeu.ETAT_FIN_DE_PARTIE);
            boutonsSupprimer[i].setEnabled(false);
            boutonsSauvegardes[i].setText("Sauvegarde vide");
        }
    }
}
