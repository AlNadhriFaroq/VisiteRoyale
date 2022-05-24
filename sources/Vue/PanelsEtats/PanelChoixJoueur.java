package Vue.PanelsEtats;

import Controleur.ControleurMediateur;
import Global.Images;
import Modele.*;
import Vue.Adaptateurs.AdaptateurBoutons;
import Vue.*;
import Vue.ComponentsMenus.BoutonMenu;

import javax.swing.*;
import java.awt.*;

public class PanelChoixJoueur extends PanelEtat {
    BoutonMenu boutonMenuGauche;
    BoutonMenu boutonMenuDroite;

    public PanelChoixJoueur(ControleurMediateur ctrl, InterfaceGraphique vue, Programme prog) {
        super(ctrl, vue, prog);

        imgFond = Images.FOND_JEU;

        JLabel txt1 = new JLabel("Tirage du joueur qui commence.");
        txt1.setHorizontalAlignment(JLabel.CENTER);
        JLabel txt2 = new JLabel("Main gauche ou main droite ?");
        txt2.setHorizontalAlignment(JLabel.CENTER);
        boutonMenuGauche = new BoutonMenu("Gauche");
        boutonMenuDroite = new BoutonMenu("Droite");

        boutonMenuGauche.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));
        boutonMenuDroite.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));

        JPanel panelBoutons = new JPanel();
        panelBoutons.setBackground(new Color(0, 0, 0, 0));
        panelBoutons.setLayout(new GridLayout(1, 3));
        panelBoutons.add(boutonMenuGauche);
        panelBoutons.add(Box.createHorizontalGlue());
        panelBoutons.add(boutonMenuDroite);

        JPanel sousPanel = new JPanel();
        sousPanel.setBackground(new Color(94, 125, 203, 0));
        sousPanel.setLayout(new GridLayout(4, 1));
        sousPanel.add(txt1);
        sousPanel.add(txt2);
        sousPanel.add(Box.createVerticalGlue());
        sousPanel.add(panelBoutons);

        add(new Cadre(sousPanel, 10, 10, 10, 10));
    }

    public BoutonMenu getBoutonGauche() {
        return boutonMenuGauche;
    }

    public BoutonMenu getBoutonDroite() {
        return boutonMenuDroite;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public void mettreAJour() {
        if (prog.getEtat() == Programme.ETAT_EN_JEU && prog.getJeu().getEtatJeu() == Jeu.ETAT_CHOIX_JOUEUR)
            repaint();
    }
}
