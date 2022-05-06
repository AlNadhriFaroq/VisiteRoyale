package Controleur;

import java.util.Random;
import Modele.*;

class JoueurIA extends Joueur {
    Random r;
    IA IA;

    JoueurIA(int num, Jeu jeu, int difficulte) {
        super(num, jeu);
        r = new Random();

        if (difficulte == IA.getFacile())
            IA = new IAAleatoire(jeu);
        else if (difficulte == IA.getMoyen())
            IA = new IAAleatoire(jeu);
        else if (difficulte == IA.getDifficile())
            IA = new IAAleatoire(jeu);
        else
            System.err.println("Bug : IA introuvable");
    }

    @Override
    boolean tempsEcoule() {
        Coup coup = IA.elaborerCoup();
        jeu.jouerCoup(coup);
        if (jeu.getJoueurCourant() != num())
            return true;
        return false;
    }
}
