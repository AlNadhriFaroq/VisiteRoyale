package Vue.PanelsEtats;

import Controleur.ControleurMediateur;
import Global.Images;
import Modele.*;
import Vue.Adaptateurs.AdaptateurBoutons;
import Vue.*;

import javax.swing.*;
import java.awt.*;

public class PanelFinPartie extends Panel {
    Bouton boutonNouvellePartie;
    Bouton boutonRetour;

    Image img;

    public PanelFinPartie(ControleurMediateur ctrl, InterfaceGraphique vue, Programme prog) {
        super(ctrl, vue, prog);

        imgFond = Images.FOND_JEU;

        img = Images.TEXTE_VICTOIRE;
        boutonNouvellePartie = new Bouton("Nouvelle partie");
        boutonRetour = new Bouton("Retour au menu principal");

        boutonNouvellePartie.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));
        boutonRetour.addActionListener(new AdaptateurBoutons(ctrl, vue, prog));

        JPanel panelBoutons = new JPanel();
        panelBoutons.setBackground(new Color(0, 0, 0, 0));
        panelBoutons.setLayout(new GridLayout(2, 1, 0, 10));
        panelBoutons.add(boutonNouvellePartie);
        panelBoutons.add(boutonRetour);

        add(new Cadre(panelBoutons, 10, 10, 7, 5));
    }

    public Bouton getBoutonNouvellePartie() {
        return boutonNouvellePartie;
    }

    public Bouton getBoutonRetour() {
        return boutonRetour;
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
