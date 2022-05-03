package Controleur;

import Modele.*;

abstract class Joueur {
    Jeu jeu;
    int num;

    Joueur(int num, Jeu jeu) {
        this.jeu = jeu;
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
