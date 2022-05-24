package Vue.ComponentsJeu;

import javax.swing.*;
import java.awt.*;

public class ImageChateau extends JPanel {
    Image img;
    boolean tournee;

    public ImageChateau(Image img, boolean tournee) {
        this.img = img;
        this.tournee = tournee;
        setBackground(new Color(0, 0, 0, 0));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D dessin = (Graphics2D) g;
        if (tournee)
            dessin.drawImage(img, 0, getHeight(), getWidth(), -getHeight(), null);
        else
            dessin.drawImage(img, 0, 0, getWidth(), getHeight(), null);
    }
}
