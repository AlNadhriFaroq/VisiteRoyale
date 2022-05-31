package Vue.Composants.ComposantsJeu;

import javax.swing.*;
import java.awt.*;

public class BoutonJeu extends JButton {
    public static final Color couleurNormal = new Color(211, 86, 28, 255);
    public static final Color couleurClaire = new Color(245, 160, 89, 255);
    public static final Color couleurFoncee = new Color(161, 57, 15, 255);

    public BoutonJeu(String texte) {
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
}
