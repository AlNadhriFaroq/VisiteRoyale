package Controleur;

import java.util.Random;
import Modele.*;

class JoueurIA extends Joueur {
    Random r;
    IA IA;

    JoueurIA(int num, Jeu jeu, int difficulte) {
        super(num, jeu);
        r = new Random();

        switch (difficulte) {
        case 0:
            IA = new IAAleatoire(jeu);
            break;
        case 1:
            IA = new IAAleatoire(jeu);
            break;
        case 2:
            IA = new IAAleatoire(jeu);
            break;
        default:
            System.err.println("Bug : IA introuvable");
            System.exit(1);
            break;
        }
    }

    @Override
    boolean tempsEcoule() {
        Coup coup = IA.elaborerCoup();
        jeu.jouerCoup(coup);
        if (jeu.joueurCourant() != num())
            return true;
        return false;
    }
}
