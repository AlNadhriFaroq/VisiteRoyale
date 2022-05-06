package Controleur;

import Modele.*;

import java.util.Random;

abstract class IA {
    public static final int FACILE = 0;
    public static final int MOYEN = 1;
    public static final int DIFFICILE = 2;

    protected Jeu jeu;

    public IA (Jeu jeu) {
        this.jeu = jeu.clone();
    }

    public Coup elaborerCoup() {
        return null;
    }
}
