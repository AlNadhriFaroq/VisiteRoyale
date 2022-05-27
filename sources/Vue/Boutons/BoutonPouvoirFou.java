package Vue.Boutons;

import Controleur.ControleurMediateur;
import Modele.Jeu;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class BoutonPouvoirFou extends Bouton {
    private Image imageCouleur;
    private Image imageGris;

    private String path = "/Images/Pions/";
    private  String nom = "FOU";
    private String nom2 = "FOUgris";

    public BoutonPouvoirFou(ControleurMediateur ctrl, Jeu jeu) {

        super(ctrl, jeu);
        try {
            this.imageCouleur = ImageIO.read(getClass().getResource(path + nom+".png"));
            this.imageCouleur = this.imageCouleur.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            this.imageGris = ImageIO.read(getClass().getResource(path + nom2+".png"));
            this.imageGris = this.imageGris.getScaledInstance(40, 40, Image.SCALE_SMOOTH);

        }catch (IOException e){
            System.out.println(path+nom+".png");
        }


        this.setIcon(new ImageIcon(this.imageCouleur));
        this.setBackground(Color.cyan);
    }

    @Override
    void action() {
        if (jeu.peutUtiliserPouvoirFou()) {
            ctrl.activerPouvoirFou();
            System.out.println("Pouvoir fou");
        }
    }

    public Image getImageCouleur() {
        return imageCouleur;
    }

    public Image getImageGris() {
        return imageGris;
    }
}
