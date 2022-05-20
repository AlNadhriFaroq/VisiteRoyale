package Vue;

import javax.swing.*;

public class Bouton extends JButton {

    public Bouton(String texte) {
        super(texte);
    }

    public void mettreAJour() {
        repaint();
    }
}
