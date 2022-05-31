package IA;

import Modele.*;

import java.util.*;

public class IAAleatoire extends IA {
    Random r;

    public IAAleatoire(Jeu jeu) {
        super(jeu);
        r = new Random();
    }

    @Override
    public Coup calculerCoup() {
        List<Coup> coups = jeu.calculerListeCoup();
        return coups.get(r.nextInt(coups.size()));
    }
}
