package Controleur;

import Modele.Coup;

import java.util.*;

class IAAleatoire extends IA {
    Random r;

    public IAAleatoire() {
        r = new Random();
    }

    @Override
    public List<Coup> jouer() {
        List<Coup> resultat = new ArrayList();
        Coup coup = null;

        resultat.add(coup);
        return resultat;
    }
}
