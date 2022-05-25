package Vue.ComponentsMenus;

import javax.swing.*;
import java.awt.*;

public class BoutonMenu extends JButton {
    public static final Color couleurNormal = new Color(253, 62, 69, 255);
    public static final Color couleurClaire = new Color(247, 225, 201, 255);
    public static final Color couleurFoncee = new Color(84, 12, 45, 255);

    public BoutonMenu(String texte) {
        super(texte);

        setBorder(BorderFactory.createRaisedBevelBorder());
        setForeground(couleurClaire);
        setBackground(couleurNormal);
        setFocusable(false);
        setContentAreaFilled(false);
        setOpaque(true);
    }

    public void redimensionner(int largeur, int hauteur) {
        Dimension dim = new Dimension(largeur, hauteur);
        setPreferredSize(dim);
        setMinimumSize(dim);
        setMaximumSize(dim);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public void mettreAJour() {
        repaint();
    }
}
