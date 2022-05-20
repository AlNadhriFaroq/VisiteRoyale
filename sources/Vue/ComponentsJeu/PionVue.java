package Vue.ComponentsJeu;

import Modele.Pion;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class PionVue extends JPanel {
    Pion pion;
    Image img;

    public PionVue(Pion pion) {
        this.pion = pion;
        setBackground(new Color(0, 0, 0, 0));
        String nom = "Images" + File.separator + "Pions" + File.separator + pion.toString() + ".png";
        try {
            img = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(nom));
        } catch (Exception e) {
            throw new RuntimeException("Vue.ComponentsJeu.CarteVue() : Impossible d'ouvrir l'image.\n" + e);
        }
    }

    public Pion getPion() {
        return pion;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D dessin = (Graphics2D) g;
        dessin.drawImage(img.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH), 0, 0, null);
    }

    public void mettreAJour() {
        repaint();
    }
}
