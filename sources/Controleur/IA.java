package Controleur;

import Modele.*;

abstract class IA {
    private Jeu jeu;

    public IA (Jeu jeu) {
        this.jeu = jeu.clone();
    }

    public Coup elaborerCoup() {
        return null;
    }
}
