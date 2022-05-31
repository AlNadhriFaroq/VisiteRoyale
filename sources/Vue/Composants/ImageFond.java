package Vue.Composants;

import Global.Images;
import Modele.Programme;

import javax.swing.*;
import java.awt.*;

public class ImageFond extends JPanel {
    Programme prog;

    Image img;

    public ImageFond(Programme prog) {
        this.prog = prog;

        img = Images.TEXTE_TITRE;

        setBackground(new Color(0, 0, 0, 0));
    }

    public void mettreAJour() {
        switch (prog.getEtat()) {
            case Programme.ETAT_ACCUEIL:
                img = Images.TEXTE_TITRE;
                break;
            case Programme.ETAT_MENU_PRINCIPAL:
                img = Images.FOND_MENU;
                break;
            case Programme.ETAT_EN_JEU:
                img = Images.FOND_JEU;
                break;
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D dessin = (Graphics2D) g;
        int hauteur = getHeight();
        int largeur = hauteur * img.getWidth(null) / img.getHeight(null);
        if (largeur < getWidth()) {
            largeur = getWidth();
            hauteur = largeur * img.getHeight(null) / img.getWidth(null);
        }
        dessin.drawImage(img, getWidth() / 2 - largeur / 2, getHeight() / 2 - hauteur / 2, largeur, hauteur, null);
    }
}
