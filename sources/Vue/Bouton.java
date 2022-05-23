package Vue;

import javax.swing.*;
import java.awt.*;

public class Bouton extends JButton {
    private final Color couleurTexte = Color.BLACK;
    private final Color couleurFond = Color.BLACK;
    private final Color couleurDessus = Color.BLACK;
    private final Color couleurClique = Color.BLACK;

    public Bouton(String texte) {
        super(texte);
    }

    public void mettreAJour() {
        repaint();
    }
}
