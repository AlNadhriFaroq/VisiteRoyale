package Vue.PanelsEtats;

import Controleur.ControleurMediateur;
import Global.Images;
import Modele.*;
import Vue.Adaptateurs.AdaptateurBoutons;
import Vue.*;
import Vue.ComponentsMenus.BoutonMenu;

import javax.swing.*;
import java.awt.*;

public class PanelFinPartie extends PanelEtat {
    BoutonMenu boutonMenuNouvellePartie;
    BoutonMenu boutonMenuRetour;

    Image img;

    public PanelFinPartie(ControleurMediateur ctrl, InterfaceGraphique vue, Programme prog) {
        super(ctrl, vue, prog);

        imgFond = Images.FOND_JEU;

        img = Images.TEXTE_VICTOIRE;
        boutonMenuNouvellePartie = new BoutonMenu("Nouvelle partie");
        boutonMenuRetour = new BoutonMenu("Retour au menu principal");

        boutonMenuNouvellePartie.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));
        boutonMenuRetour.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));

        JPanel panelBoutons = new JPanel();
        panelBoutons.setBackground(new Color(0, 0, 0, 0));
        panelBoutons.setLayout(new GridLayout(2, 1, 0, 10));
        panelBoutons.add(boutonMenuNouvellePartie);
        panelBoutons.add(boutonMenuRetour);

        add(new Cadre(panelBoutons, 10, 10, 7, 5));
    }

    public BoutonMenu getBoutonNouvellePartie() {
        return boutonMenuNouvellePartie;
    }

    public BoutonMenu getBoutonRetour() {
        return boutonMenuRetour;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int hauteur = (2 * getWidth() / 3) * img.getHeight(null) / img.getWidth(null);
        dessin.drawImage(img, getWidth() / 6, (getHeight() / 4) - (hauteur / 2), 2 * getWidth() / 3, hauteur, null);
    }

    @Override
    public void mettreAJour() {
        if (prog.getEtat() == Programme.ETAT_EN_JEU && prog.getJeu().getEtatJeu() == Jeu.ETAT_FIN_DE_PARTIE) {
            if (prog.getJoueurEstIA(Jeu.JOUEUR_VRT) && !prog.getJoueurEstIA(Jeu.JOUEUR_RGE))
                img = prog.getJeu().getJoueurGagnant() == Jeu.JOUEUR_RGE ? Images.TEXTE_VICTOIRE : Images.TEXTE_DEFAITE;
            else
                img = prog.getJeu().getJoueurGagnant() == Jeu.JOUEUR_VRT ? Images.TEXTE_VICTOIRE_VRT : Images.TEXTE_VICTOIRE_RGE;
            repaint();
        }
    }
}
