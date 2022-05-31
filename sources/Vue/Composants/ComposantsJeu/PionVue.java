package Vue.Composants.ComposantsJeu;

import Global.Images;
import Modele.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class PionVue extends JPanel {
    Pion pion;
    boolean selectionnable;
    boolean dessus;
    boolean selectionne;

    Shape forme;
    Image img;

    public PionVue(Pion pion) {
        this.pion = pion;
        selectionnable = false;
        dessus = false;
        selectionne = false;

        setBackground(new Color(0, 0, 0, 0));
        img = Images.getImagePion(pion.toString());
    }

    public Pion getPion() {
        return pion;
    }

    public boolean estSelectionnable() {
        return selectionnable;
    }

    public void mettreAJour(boolean selectionnable, boolean dessus, boolean selectionne) {
        this.selectionnable = selectionnable;
        this.dessus = dessus;
        this.selectionne = selectionne;
        repaint();
    }

    @Override
    public boolean contains(int x, int y) {
        if (forme == null || !forme.getBounds().equals(getBounds()))
            forme = new Ellipse2D.Float(2, 2, getWidth() - 4, getHeight() - 4);
        return forme.contains(x, y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D dessin = (Graphics2D) g;

        dessin.drawImage(img, 2, 2, getWidth() - 4, getHeight() - 4, null);
        if (selectionne || selectionnable) {
            dessin.setColor(selectionne ? new Color(255, 166, 0, 255) : (dessus ? new Color(255, 97, 90, 255) : new Color(243, 11, 2, 255)));
            dessin.setStroke(new BasicStroke(4));
            dessin.drawOval(4, 4, getWidth() - 8, getHeight() - 8);
        } else {
            dessin.setColor(new Color(0, 0, 0, 10));
            dessin.fillOval(2, 2, getWidth() - 4, getHeight() - 4);
        }
        dessin.setColor(Color.BLACK);
        dessin.setStroke(new BasicStroke(1));
        dessin.drawOval(2, 2, getWidth() - 4, getHeight() - 4);
    }
}
