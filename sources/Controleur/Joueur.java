package Controleur;

import Modele.*;

abstract class Joueur {
    Programme prog;
    int num;

    Joueur(int num, Programme prog) {
        this.prog = prog;
        this.num = num;
    }

    int num() {
        return num;
    }

    boolean tempsEcoule() {
        return false;
    }

    boolean jouer(Coup coup) {
        return false;
    }
}
