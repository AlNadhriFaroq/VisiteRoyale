package Vue.Composants.ComposantsJeu;

import Global.Images;
import Modele.*;
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
        nbCartes.setForeground(Color.WHITE);
        nbCartes.setCouleurContour(Color.BLACK);
        nbCartes.setEpaisseur(new BasicStroke(4f));

        nbCartes.setVisible(false);

        add(nbCartes, new GBC(0, 0).setWeight(1, 1).setAnchor(GBC.CENTER));
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
        Paquet paquet = pioche ? prog.getJeu().getPioche() : prog.getJeu().getDefausse();

        if (paquet.estVide())
            img = Images.getImageCarte("Vide");
        else if (pioche)
            img = Images.getImageCarte("Dos");
        else
            img = Images.getImageCarte(paquet.getCarte(paquet.getTaille() - 1).toString());

        nbCartes.setText(Integer.toString(paquet.getTaille()));
        nbCartes.setVisible(pioche && paquet.getTaille() <= 8 && prog.getJeu().getPlateau().getFaceCouronne() == Plateau.FACE_PTT_CRN);

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
