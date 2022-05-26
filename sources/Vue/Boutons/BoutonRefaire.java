package Vue.Boutons;

import Controleur.ControleurMediateur;
import Modele.Jeu;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class BoutonRefaire extends Bouton {
    private Image image;
    private Image image2;
    private String path = "/Images/Boutons/";
    private String nom = "REFAIRE";
    private String nom2 = "REFAIRE2";
    int BoutonLargeur = 90 / 2;
    int BoutonHauteur = BoutonLargeur;

    public BoutonRefaire(ControleurMediateur ctrl, Jeu jeu) {
        super(ctrl, jeu);
        try {
            this.image = ImageIO.read(getClass().getResource(path + nom + ".png"));
            this.image = this.image.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            this.image2 = ImageIO.read(getClass().getResource(path + nom2 + ".png"));
            this.image2 = this.image.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            this.setIcon(new ImageIcon(this.image));
        } catch (IOException e) {
            System.out.println(path + nom + ".png");
        }
        this.setBackground(Color.lightGray);
    }


    @Override
    void action() {
        if (jeu.peutRefaire()) {
            ctrl.refaire();
            System.out.println("Refait");
        }
    }

    public Image getImage2() {
        return image2;
    }

    public Image getImage() {
        return image;
    }
}

