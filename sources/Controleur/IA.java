package Controleur;

import Modele.*;

abstract class IA {
    public static final int FACILE = 0;
    public static final int MOYEN = 1;
    public static final int DIFFICILE = 2;

    private Jeu jeuReel;
    protected Jeu jeu;

    IA (Jeu jeu) {
        jeuReel = jeu;
    }

    final Coup elaborerCoup() {
        jeu = jeuReel.clone();
        return calculerCoup();
    }

    Coup calculerCoup() {
        return null;
    }
}
