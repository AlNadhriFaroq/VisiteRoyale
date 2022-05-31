package Vue.Composants;

import javax.swing.*;
import java.awt.*;
import java.awt.font.*;
import java.awt.geom.AffineTransform;

public class TexteAContour extends JLabel {
    protected Color couleurContour;
    protected Stroke epaisseur;

    public TexteAContour() {
        super();
    }

    public TexteAContour(String text) {
        super(text);
    }

    public TexteAContour(String text, int alignment) {
        super(text, alignment);
    }

    public Color getCouleurContour() {
        return couleurContour;
    }

    public Stroke getEpaisseur() {
        return epaisseur;
    }

    public void setCouleurContour(Color c) {
        couleurContour = c;
        repaint();
    }

    public void setEpaisseur(Stroke s) {
        epaisseur = s;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        Dimension dim = getSize();
        Insets ecarts = getInsets();
        int x = ecarts.left;
        int y = ecarts.top;
        int largeur = dim.width - ecarts.left - ecarts.right;
        int hauteur = dim.height - ecarts.top - ecarts.bottom;

        if (isOpaque()) {
            g.setColor(getBackground());
            g.fillRect(0, 0, dim.width, dim.height);
        }
        paintBorder(g);

        Graphics2D dessin = (Graphics2D) g;
        dessin.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        dessin.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        TextLayout tl = new TextLayout(getText(), getFont(), dessin.getFontRenderContext());
        Shape formeSource = tl.getOutline(AffineTransform.getShearInstance(0.0, 0.0));
        Rectangle rectTexte = formeSource.getBounds();

        float xTexte = x - rectTexte.x;
        switch (getHorizontalAlignment()) {
            case CENTER:
                xTexte = x + (largeur - rectTexte.width) / 2;
                break;
            case RIGHT:
                xTexte = x + (largeur - rectTexte.width);
                break;
        }
        float yTexte = y + hauteur / 2 + tl.getAscent() / 4;

        Shape forme = AffineTransform.getTranslateInstance(xTexte, yTexte).createTransformedShape(formeSource);

        if (couleurContour != null) {
            dessin.setColor(couleurContour);
            if (epaisseur != null)
                dessin.setStroke(epaisseur);
            dessin.draw(forme);
        }

        dessin.setColor(getForeground());
        dessin.fill(forme);
    }
}
