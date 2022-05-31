package Vue.Composants.ComposantsJeu;

import Global.Images;
import Modele.Plateau;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class JetonVue extends JPanel {
    Shape forme;
    Image img;

    public JetonVue() {
        setBackground(new Color(0, 0, 0, 0));
        img = Images.COURONNE_GRD;
    }

    public void mettreAJour(boolean face) {
        img = face == Plateau.FACE_GRD_CRN ? Images.COURONNE_GRD : Images.COURONNE_PTT;
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
    }
}
