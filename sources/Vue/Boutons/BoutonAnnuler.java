package Vue.Boutons;

import Controleur.ControleurMediateur;
import Modele.Jeu;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class BoutonAnnuler extends Bouton {
    private Image image;
    private  Image image2;
    private String path = "/Images/Boutons/";
    private  String nom = "ANNULER";
    private String nom2 = "ANNULER2";

    public BoutonAnnuler(ControleurMediateur ctrl, Jeu jeu) {
        super(ctrl, jeu);
        try {
            this.image = ImageIO.read(getClass().getResource(path + nom+".png"));
            this.image = this.image.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            this.image2 = ImageIO.read(getClass().getResource(path + nom2+".png"));
            this.image2 = this.image.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            this.setIcon(new ImageIcon(this.image));
        }catch (IOException e){
            System.out.println(path+nom+".png");
        }
        this.setBackground(Color.lightGray);
    }

    @Override
    void action() {
        if (jeu.peutAnnuler()) {
            ctrl.annuler();
            System.out.println("Annule");
        }
    }

    public Image getImage() {
        return image;
    }

    public Image getImage2() {
        return image2;
    }
}
