package Controleur;

import IA.*;
import Modele.*;

import java.util.Random;

class JoueurIA extends Joueur {
    IA ia;

    JoueurIA(int num, Programme prog, int difficulte) {
        super(num, prog);

        switch (difficulte) {
            case IA.DEBUTANT:
                ia = new IAAleatoire(prog.getJeu());
                break;
            case IA.AMATEUR:
                ia = new IAAleatoirePonderee(prog.getJeu());
                break;
            case IA.INTERMEDIAIRE:
                ia = new IAStrategie(prog.getJeu());
                break;
            case IA.PROFESSIONNEL:
                ia = new IAStrategie(prog.getJeu());
                break;
            case IA.EXPERT:
                ia = new IAStrategie(prog.getJeu());
                break;
            default:
                throw new RuntimeException("Controleur.JoueurIA() : Difficulté de l'IA introuvable.");
        }
    }

    @Override
    void tempsEcoule() {
        if (prog.getJeu().getEtatJeu() == Jeu.ETAT_CHOIX_JOUEUR) {
            Random r = new Random();
            prog.definirJoueurQuiCommence(r.nextInt(2));
        } else if (prog.getJeu().getEtatJeu() != Jeu.ETAT_FIN_DE_PARTIE) {
            Coup coup = ia.elaborerCoup();
            prog.jouerCoup(coup);
        }
        num();
    }
}
