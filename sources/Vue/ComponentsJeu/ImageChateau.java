package Vue.ComponentsJeu;

import Global.Images;

import javax.swing.*;
import java.awt.*;

public class ImageChateau extends JPanel {
    Image img;
    int sens;

    public ImageChateau(Image img, int sens) {
        this.img = img;
        this.sens = sens;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D dessin = (Graphics2D) g;
        dessin.setBackground(Color.CYAN);
        dessin.drawImage(img, 0, 0, getWidth(), getHeight(), null);
        dessin.setStroke(new BasicStroke(3));
        dessin.drawRect(0, 0, getWidth(), getHeight());
    }
}
