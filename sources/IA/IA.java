package IA;

import Modele.*;

public abstract class IA {
    public static final int DEBUTANT = 0;
    public static final int AMATEUR = 1;
    public static final int INTERMEDIAIRE = 2;
    public static final int PROFESSIONNEL = 3;
    public static final int EXPERT = 4;

    private Jeu jeuReel;
    protected Jeu jeu;

    IA(Jeu jeu) {
        jeuReel = jeu;
    }

    public final Coup elaborerCoup() {
        jeu = jeuReel.clone();
        return calculerCoup();
    }

    Coup calculerCoup() {
        return null;
    }
}
