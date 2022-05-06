package Controleur;

import Modele.*;
import java.util.*;

class IAAleatoire extends IA {
    Random r;

    public IAAleatoire(Jeu jeu) {
        super(jeu);
        r = new Random();
    }

    @Override
    public Coup elaborerCoup() {
        int typeCarte = r.nextInt(8);
        return null;

    }

    int choixTypeCoup(Random r){
        int typeCoup = r.nextInt(5);

        while(!typeCoupCorrect(typeCoup)){
            typeCoup = r.nextInt(5);
        }

        return typeCoup;

    }

    boolean typeCoupCorrect(int typeCoup){
        boolean possible;
        switch(typeCoup){
            case Coup.DEPLACEMENT:
                possible = true;
                break;
            case Coup.PRIVILEGE_ROI:
                possible = jeu.peutUtiliserPrivilegeRoi();
                break;
            case Coup.POUVOIR_SOR:
                possible = jeu.peutUtiliserPouvoirSorcier();
                break;
            case Coup.POUVOIR_FOU:
                possible = jeu.peutUtiliserPouvoirFou();
                break;
            case Coup.FIN_TOUR:
                possible = jeu.peutFinirTour();
                break;
            default:
                possible = false;
                break;
        }
        return possible;
    }
}
