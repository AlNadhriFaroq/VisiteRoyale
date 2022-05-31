package Vue.Composants.ComposantsJeu;

import Global.Images;
import Modele.*;

import javax.swing.*;
import java.awt.*;

public class ImageFinPartie extends JPanel {
    Programme prog;

    Image img;

    public ImageFinPartie(Programme prog) {
        this.prog = prog;

        img = Images.TEXTE_VICTOIRE;

        setBackground(new Color(0, 0, 0, 0));
        setOpaque(false);
    }

    public void mettreAJour() {
        if (prog.getJoueurEstIA(Jeu.JOUEUR_VRT) && !prog.getJoueurEstIA(Jeu.JOUEUR_RGE))
            img = prog.getJeu().getJoueurGagnant() == Jeu.JOUEUR_RGE ? Images.TEXTE_VICTOIRE : Images.TEXTE_DEFAITE;
        else
            img = prog.getJeu().getJoueurGagnant() == Jeu.JOUEUR_VRT ? Images.TEXTE_VICTOIRE_VRT : Images.TEXTE_VICTOIRE_RGE;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D dessin = (Graphics2D) g;
        int hauteur = getHeight();
        int largeur = hauteur * img.getWidth(null) / img.getHeight(null);
        if (largeur > getWidth()) {
            largeur = getWidth();
            hauteur = largeur * img.getHeight(null) / img.getWidth(null);
        }
        dessin.drawImage(img, getWidth() / 2 - largeur / 2, getHeight() / 2 - hauteur / 2, largeur, hauteur, null);
    }
}
