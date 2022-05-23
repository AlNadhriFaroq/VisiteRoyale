package Vue.PanelsEtats;

import Controleur.ControleurMediateur;
import Global.Images;
import Modele.*;
import Vue.Adaptateurs.AdaptateurBoutons;
import Vue.*;

import javax.swing.*;
import java.awt.*;

public class PanelChoixJoueur extends Panel {
    Bouton boutonGauche;
    Bouton boutonDroite;

    public PanelChoixJoueur(ControleurMediateur ctrl, InterfaceGraphique vue, Programme prog) {
        super(ctrl, vue, prog);

        imgFond = Images.FOND_JEU;

        JLabel txt1 = new JLabel("Tirage du joueur qui commence.");
        txt1.setHorizontalAlignment(JLabel.CENTER);
        JLabel txt2 = new JLabel("Main gauche ou main droite ?");
        txt2.setHorizontalAlignment(JLabel.CENTER);
        boutonGauche = new Bouton("Gauche");
        boutonDroite = new Bouton("Droite");

        boutonGauche.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));
        boutonDroite.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));

        JPanel panelBoutons = new JPanel();
        panelBoutons.setBackground(new Color(0, 0, 0, 0));
        panelBoutons.setLayout(new GridLayout(1, 3));
        panelBoutons.add(boutonGauche);
        panelBoutons.add(Box.createHorizontalGlue());
        panelBoutons.add(boutonDroite);

        JPanel sousPanel = new JPanel();
        sousPanel.setBackground(new Color(94, 125, 203, 0));
        sousPanel.setLayout(new GridLayout(4, 1));
        sousPanel.add(txt1);
        sousPanel.add(txt2);
        sousPanel.add(Box.createVerticalGlue());
        sousPanel.add(panelBoutons);

        add(new Cadre(sousPanel, 10, 10, 10, 10));
    }

    public Bouton getBoutonGauche() {
        return boutonGauche;
    }

    public Bouton getBoutonDroite() {
        return boutonDroite;
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
