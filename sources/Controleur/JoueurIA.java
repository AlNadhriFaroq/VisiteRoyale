package Controleur;

import java.util.Random;
import Modele.*;

class JoueurIA extends Joueur {
    Random r;
    IA ia;

    JoueurIA(int num, Jeu jeu, int difficulte) {
        super(num, jeu);
        r = new Random();

        if (difficulte == IA.FACILE)
            ia = new IAAleatoire(jeu);
        else if (difficulte == IA.MOYEN)
            ia = new IAAleatoire(jeu);
        else if (difficulte == IA.DIFFICILE)
            ia = new IAAleatoire(jeu);
        else
            System.err.println("Bug : IA introuvable");
    }

    @Override
    boolean tempsEcoule() {
        if (jeu.getEtatJeu() == Jeu.ETAT_EN_JEU) {
            Coup coup = ia.elaborerCoup();
            jeu.jouerCoup(coup);
        }
        return jeu.getJoueurCourant() != num();
    }
}
