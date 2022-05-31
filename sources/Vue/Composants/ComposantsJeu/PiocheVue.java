package Vue.Composants.ComposantsJeu;

import Global.Images;
import Modele.Paquet;
import Modele.Programme;
import Vue.Composants.TexteAContour;
import Vue.GBC;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class PiocheVue extends JPanel {
    Programme prog;
    boolean pioche;

    Shape shape;
    Image img;
    TexteAContour nbCartes;

    public PiocheVue(Programme prog, boolean pioche) {
        this.prog = prog;
        this.pioche = pioche;
        img = Images.CARTE_VIDE;

        setBackground(new Color(0, 0, 0, 0));
        setOpaque(false);
        setLayout(new GridBagLayout());

        nbCartes = new TexteAContour("", 0);
        nbCartes.setLeftShadow(2,2,Color.BLACK);
        nbCartes.setRightShadow(2,2, Color.BLACK);
        nbCartes.setForeground(Color.white);

        nbCartes.setVisible(false);

        add(nbCartes, new GBC(0,0).setWeight(1,1).setAnchor(GBC.CENTER));
    }

    public TexteAContour getTxtNbCartes() {
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
        Paquet paquet = pioche ? prog.getJeu().getPioche() : prog.getJeu().getDefausse();
        if (paquet.estVide())
            img = Images.getImageCarte("Vide");
        else if (pioche)
            img = Images.getImageCarte("Dos");
        else
            img = Images.getImageCarte(paquet.getCarte(paquet.getTaille() - 1).toString());
        nbCartes.setText(Integer.toString(paquet.getTaille()));
        repaint();
    }

    @Override
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds()))
            shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15);
        return shape.contains(x, y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D dessin = (Graphics2D) g;
        dessin.drawImage(Images.tournerImage(img, 270), 0, 0, getWidth(), getHeight(), null);
    }
}
