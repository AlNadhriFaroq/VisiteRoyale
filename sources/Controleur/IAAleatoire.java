package Controleur;

import Modele.*;

import java.util.*;

class IAAleatoire extends IA {
    Random r;

    public IAAleatoire(Jeu jeu) {
        super(jeu);
        r = new Random();
    }

    @Override
    public Coup elaborerCoup() {
        Coup resultat = null;

        return resultat;
    }
}
