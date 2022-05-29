package Vue.Composants.ComposantsJeu;

import javax.swing.*;
import java.awt.*;

public class BoutonJeu extends JButton {
    public static final Color couleurNormal = new Color(215, 40, 39, 255);
    public static final Color couleurClaire = new Color(233, 137, 84, 255);
    public static final Color couleurFoncee = new Color(142, 76, 58, 255);

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
