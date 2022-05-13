package Controleur;

import java.util.Random;
import Modele.*;

class JoueurIA extends Joueur {
    IA ia;

    JoueurIA(int num, Programme prog, int difficulte) {
        super(num, prog);

        if (difficulte == IA.FACILE)
            ia = new IAAleatoire(prog.getJeu());
        else if (difficulte == IA.MOYEN)
            ia = new IAMoyenne(prog.getJeu());
        else if (difficulte == IA.DIFFICILE)
            ia = new IAAleatoire(prog.getJeu());
        else
            throw new RuntimeException("Controleur.JoueurIA() : Difficulté de l'IA introuvable.");
    }

    @Override
    boolean tempsEcoule() {
        if (prog.getJeu().getEtatJeu() != Jeu.ETAT_FIN_DE_PARTIE) {
            Coup coup = ia.elaborerCoup();
            prog.jouerCoup(coup);
        }
        return prog.getJeu().getJoueurCourant() != num();
    }
}
