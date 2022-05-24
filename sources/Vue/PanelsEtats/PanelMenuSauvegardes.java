package Vue.PanelsEtats;

import Controleur.ControleurMediateur;
import Modele.Jeu;
import Modele.Programme;
import Vue.*;
import Vue.Adaptateurs.AdaptateurBoutons;
import Vue.ComponentsMenus.*;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;

public class PanelMenuSauvegardes extends PanelEtat {
    BoutonSauvegarde[] boutonsSauvegardes;
    BoutonSupprimer[] boutonsSupprimer;
    BoutonMenu boutonMenuRetour;

    public PanelMenuSauvegardes(ControleurMediateur ctrl, InterfaceGraphique vue, Programme prog) {
        super(ctrl, vue, prog);

        JLabel texte = new JLabel("Sauvegardes");
        texte.setFont(new Font(Font.DIALOG, 0, 30));

        boutonsSauvegardes = new BoutonSauvegarde[Programme.NB_SAUVEGARDES];
        boutonsSupprimer = new BoutonSupprimer[Programme.NB_SAUVEGARDES];
        for (int i = 0; i < Programme.NB_SAUVEGARDES; i++) {
            boutonsSauvegardes[i] = new BoutonSauvegarde("Sauvegarde vide");
            boutonsSupprimer[i] = new BoutonSupprimer("X");
        }

        boutonMenuRetour = new BoutonMenu("Retour");

        for (BoutonSauvegarde bouton : boutonsSauvegardes)
            bouton.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));
        for (BoutonSupprimer bouton : boutonsSupprimer)
            bouton.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));
        boutonMenuRetour.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));

        setLayout(new GridBagLayout());
        add(new JLabel(), new GridBagConstraints(0, 0, 1, 1, 0.33, 1.0 / (Programme.NB_SAUVEGARDES+4), GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        add(new JLabel(), new GridBagConstraints(1, 0, 1, 1, 0.30, 1.0 / (Programme.NB_SAUVEGARDES+4), GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        add(texte, new GridBagConstraints(1, 1, 1, 1, 0.30, 1.0 / (Programme.NB_SAUVEGARDES+4), GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        for (int i = 0; i < Programme.NB_SAUVEGARDES; i++)
            add(boutonsSauvegardes[i], new GridBagConstraints(1, 2+i, 1, 1, 0.30, 1.0 / (Programme.NB_SAUVEGARDES+4), GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 0, 5, 0), 0, 0));
        add(boutonMenuRetour, new GridBagConstraints(1, 3+Programme.NB_SAUVEGARDES, 2, 1, 0.34, 1.0 / (Programme.NB_SAUVEGARDES+4), GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        add(new JLabel(), new GridBagConstraints(1, 4+Programme.NB_SAUVEGARDES, 1, 1, 0.30, 1.0 / (Programme.NB_SAUVEGARDES+4), GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        for (int i = 0; i < Programme.NB_SAUVEGARDES; i++)
            add(boutonsSupprimer[i], new GridBagConstraints(2, 2+i, 1, 1, 0.04, 1.0 / (Programme.NB_SAUVEGARDES+4), GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 0, 5, 0), 0, 0));
        add(new JLabel(), new GridBagConstraints(3, 0, 1, 1, 0.33, 1.0 / (Programme.NB_SAUVEGARDES+4), GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public void mettreAJour() {
        if (prog.getEtat() == Programme.ETAT_MENU_SAUVEGARDES) {
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
            repaint();
        }
    }
}
