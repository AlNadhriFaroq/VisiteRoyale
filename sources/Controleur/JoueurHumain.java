package Controleur;

import Modele.*;

class JoueurHumain extends Joueur {
    JoueurHumain(int num, Jeu jeu) {
        super(num, jeu);
    }

    @Override
    boolean jouer(Coup coup) {
        jeu.jouerCoup(coup);
        if (jeu.getJoueurCourant() != num())
            return true;
        return false;
    }
}