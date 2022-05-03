package Controleur;

import Modele.*;

class JoueurHumain extends Joueur {
    JoueurHumain(int num, Jeu jeu) {
        super(num, jeu);
    }

    @Override
    boolean jouer(Coup coup) {
        if (jeu.estJouable(coup)) {
            jeu.jouerCoup(coup);
            if (jeu.joueurCourant() != num())
                return true;
        }
        return false;
    }
}