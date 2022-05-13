package Controleur;

import java.util.Random;
import Modele.*;

class JoueurIA extends Joueur {
    IA ia;

    JoueurIA(int num, Jeu jeu, int difficulte) {
        super(num, jeu);

        if (difficulte == IA.FACILE)
            ia = new IAAleatoire(jeu);
        else if (difficulte == IA.MOYEN)
            ia = new IAMoyenne(jeu);
        else if (difficulte == IA.DIFFICILE)
            ia = new IAAleatoire(jeu);
        else
            System.err.println("Bug : IA introuvable");
    }

    @Override
    boolean tempsEcoule() {
        if (jeu.getEtatJeu() == Jeu.ETAT_CHOIX_JOUEUR) {
            Random r = new Random();
            jeu.definirJoueurQuiCommence(r.nextInt(2));
        } else if (jeu.getEtatJeu() != Jeu.ETAT_FIN_DE_PARTIE) {
            Coup coup = ia.elaborerCoup();
            jeu.jouerCoup(coup);
        }
        return jeu.getJoueurCourant() != num();
    }
}
