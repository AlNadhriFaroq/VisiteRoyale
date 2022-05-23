package Vue;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Bouton extends JButton {
    private final Color couleurTexte = new Color(0, 0, 0, 255);
    private final Color couleurFond = new Color(255, 0, 0, 255);
    private final Color couleurDessus = new Color(169, 0, 0, 255);
    private final Color couleurClique = new Color(119, 8, 8, 255);
    private final Color couleurBordure = new Color(255, 255, 255, 255);

    public Bouton(String texte) {
        super(texte);

        setBorder(BorderFactory.createLineBorder(couleurBordure));
        setForeground(couleurTexte);
        setBackground(couleurFond);
        setFocusable(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(couleurDessus);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(couleurFond);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(couleurClique);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(couleurFond);
            }
        });
    }

    @Override
    protected void paintBorder(Graphics g) {
        super.paintBorder(g);
        g.setColor(couleurBordure);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getModel().isArmed() ? couleurClique : getBackground());
        super.paintComponent(g);
    }

    public void mettreAJour() {
        repaint();
    }
}
