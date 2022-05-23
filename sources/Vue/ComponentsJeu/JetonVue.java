package Vue.ComponentsJeu;

import Global.Images;
import Modele.Plateau;

import javax.swing.*;
import java.awt.*;

public class JetonVue extends JPanel {
    Image img;

    public JetonVue() {
        setBackground(new Color(0, 0, 0, 0));
        img = Images.COURONNE_GRD;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D dessin = (Graphics2D) g;
        dessin.drawImage(img, 0, 0, getWidth()-1, getHeight()-1, null);
    }

    public void mettreAJour(boolean face) {
        img = face == Plateau.FACE_GRD_CRN ? Images.COURONNE_GRD : Images.COURONNE_PTT;
        repaint();
    }
}
