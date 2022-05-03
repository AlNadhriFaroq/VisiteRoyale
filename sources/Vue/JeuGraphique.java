package Vue;

import Modele.*;
import Patterns.Observateur;

import javax.swing.*;
import java.awt.*;

public class JeuGraphique extends JComponent implements Observateur {
    Graphics2D dessin;
    Jeu jeu;

    public JeuGraphique(Jeu jeu) {
        this.jeu = jeu;
    }

    public int hauteur() {
        return getHeight();
    }

    public int largeur() {
        return getWidth();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        dessin = (Graphics2D) graphics;
        dessin.clearRect(0, 0, getWidth(), getHeight());

        /* dessin du jeu */

        jeu.ajouterObservateur(this);
    }

    @Override
    public void mettreAJour() {
        repaint();
    }
}
