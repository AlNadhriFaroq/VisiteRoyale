package Controleur;

import Modele.*;

class JoueurHumain extends Joueur {
    JoueurHumain(int num, Programme prog) {
        super(num, prog);
    }

    @Override
    void jouer(Coup coup) {
        prog.jouerCoup(coup);
        num();
    }
}