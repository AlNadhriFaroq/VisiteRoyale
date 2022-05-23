package Vue.ComponentsJeu;

import Global.Images;
import Modele.Pion;

import javax.swing.*;
import java.awt.*;

public class PionVue extends JPanel {
    Pion pion;
    Image img;

    public PionVue(Pion pion) {
        this.pion = pion;
        setBackground(new Color(0, 0, 0, 0));
        img = Images.getImagePion(pion.toString());
    }

    public Pion getPion() {
        return pion;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D dessin = (Graphics2D) g;
        dessin.drawImage(img, 0, 0, getWidth(), getHeight(), null);
    }

    public void mettreAJour() {
        repaint();
    }
}
