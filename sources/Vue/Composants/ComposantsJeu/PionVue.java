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
            dessin.setColor(selectionne ? Color.BLUE : (dessus ? Color.WHITE : Color.YELLOW));
            dessin.setStroke(new BasicStroke(3));
            dessin.drawOval(1, 1, getWidth() - 3, getHeight() - 3);
        } else {
            dessin.setColor(new Color(0, 0, 0, 10));
            dessin.fillOval(2, 2, getWidth() - 4, getHeight() - 4);
        }
    }
}
