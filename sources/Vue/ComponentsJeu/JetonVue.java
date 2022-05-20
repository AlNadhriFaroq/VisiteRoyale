package Vue.ComponentsJeu;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class JetonVue extends JPanel {
    Image img;

    public JetonVue() {
        setBackground(new Color(0, 0, 0, 0));
        String nom = "Images" + File.separator + "Cartes" + File.separator + "R1" + ".png";
        try {
            img = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(nom));
        } catch (Exception e) {
            throw new RuntimeException("Vue.ComponentsJeu.CarteVue() : Impossible d'ouvrir l'image.\n" + e);
        }
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
