package Controleur;

import Modele.*;

class JoueurHumain extends Joueur {
    JoueurHumain(int num, Programme prog) {
        super(num, prog);
    }

    @Override
    boolean jouer(Coup coup) {
        prog.jouerCoup(coup);
        return prog.getJeu().getJoueurCourant() != num();
    }
}