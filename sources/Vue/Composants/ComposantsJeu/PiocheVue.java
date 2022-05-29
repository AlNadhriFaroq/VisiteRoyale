package Vue.Composants.ComposantsJeu;

import Global.Images;
import Modele.Paquet;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class PiocheVue extends JPanel {
    Paquet paquet;
    boolean faceCachee;

    Shape shape;
    Image img;
    JLabel nbCartes;

    public PiocheVue(Paquet paquet, boolean faceCachee) {
        this.paquet = paquet;
        this.faceCachee = faceCachee;
        img = Images.CARTE_VIDE;

        setBackground(new Color(0, 0, 0, 0));
        setOpaque(false);
        setLayout(new BorderLayout());

        nbCartes = new JLabel("", JLabel.CENTER);
        nbCartes.setForeground(Color.BLACK);
        nbCartes.setVisible(false);

        add(nbCartes, BorderLayout.CENTER);
    }

    public JLabel getTxtNbCartes() {
        return nbCartes;
    }

    public void redimensionner(int hauteurCarte) {
        Dimension dim = new Dimension(hauteurCarte, hauteurCarte * img.getWidth(null) / img.getHeight(null));
        setMinimumSize(dim);
        setMaximumSize(dim);
        setPreferredSize(dim);
        setSize(dim);
        nbCartes.setFont(new Font(null).deriveFont((float) hauteurCarte / 2));
    }

    public void mettreAJour() {
        if (paquet.estVide())
            img = Images.getImageCarte("Vide");
        else if (faceCachee)
            img = Images.getImageCarte("Dos");
        else
            img = Images.getImageCarte(paquet.getCarte(paquet.getTaille() - 1).toString());
        nbCartes.setText(Integer.toString(paquet.getTaille()));
        repaint();
    }

    @Override
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds()))
            shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
        return shape.contains(x, y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D dessin = (Graphics2D) g;
        dessin.drawImage(Images.tournerImage(img, 270), 0, 0, getWidth() - 1, getHeight() - 1, null);
    }
}
