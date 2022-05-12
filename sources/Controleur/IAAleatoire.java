package Controleur;

import Modele.*;

import java.util.*;

class IAAleatoire extends IA {
    Random r;

    IAAleatoire(Jeu jeu) {
        super(jeu);
        r = new Random();
    }

    @Override
    public Coup calculerCoup() {
        Carte carte;
        Pion pion;
        switch (jeu.getEtatJeu()) {
            case Jeu.ETAT_CHOIX_CARTE:
                System.out.println("Etat choix carte");
                if (jeu.getActivationPouvoirFou()) {
                    if ((carte = choisirCarte()) != null)
                        return new Coup(jeu.getJoueurCourant(), Coup.CHOISIR_CARTE, carte, null, Plateau.DIRECTION_IND);
                    else
                        return new Coup(jeu.getJoueurCourant(), Coup.FINIR_TOUR, null, null, Plateau.DIRECTION_IND);
                } else {
                    switch (choisirPouvoir()) {
                        case Coup.CHOISIR_CARTE:
                            return new Coup(jeu.getJoueurCourant(), Coup.CHOISIR_CARTE, choisirCarte(), null, Plateau.DIRECTION_IND);
                        case Coup.ACTIVER_POUVOIR_SOR:
                            return new Coup(jeu.getJoueurCourant(), Coup.ACTIVER_POUVOIR_SOR, null, null, Plateau.DIRECTION_IND);
                        case Coup.ACTIVER_POUVOIR_FOU:
                            return new Coup(jeu.getJoueurCourant(), Coup.ACTIVER_POUVOIR_FOU, null, null, Plateau.DIRECTION_IND);
                        case Coup.FINIR_TOUR:
                            return new Coup(jeu.getJoueurCourant(), Coup.FINIR_TOUR, null, null, Plateau.DIRECTION_IND);
                        default:
                            throw new RuntimeException("Controleur.IAAleatoire.calculerCoup() : Choix pouvoir invalide.");
                    }
                }
            case Jeu.ETAT_CHOIX_PION:
                System.out.println("Etat choix pion");
                return new Coup(jeu.getJoueurCourant(), Coup.CHOISIR_PION, null, choisirPion(), Plateau.DIRECTION_IND);
            case Jeu.ETAT_CHOIX_DIRECTION:
                System.out.println("Etat choix direction");
                int random = r.nextInt(2);
                if (random == 0 && (carte = choisirCarte()) != null)
                    return new Coup(jeu.getJoueurCourant(), Coup.CHOISIR_CARTE, carte, null, Plateau.DIRECTION_IND);
                else if (random == 0 && (pion = choisirPion()) != null)
                    return new Coup(jeu.getJoueurCourant(), Coup.CHOISIR_PION, null, pion, Plateau.DIRECTION_IND);
                else
                    return new Coup(jeu.getJoueurCourant(), Coup.CHOISIR_DIRECTION, null, null, choisirDirection());
            default:
                throw new RuntimeException("Controleur.IAALeatoire.calculerCoup() : Erreur d'etat dans le jeu.");
        }
    }

    int choisirPouvoir() {
        List<Integer> pouvoirsJouables = new ArrayList<>();
        int nbCartesJouables = nbCartesJouables();
        for (int i = 0; i < 4; i++) {
            switch (i) {
                case Coup.CHOISIR_CARTE:
                    if (nbCartesJouables != 0)
                        pouvoirsJouables.add(i);
                    break;
                case Coup.ACTIVER_POUVOIR_SOR:
                    if (jeu.peutUtiliserPouvoirSorcier())
                        pouvoirsJouables.add(i);
                    break;
                case Coup.ACTIVER_POUVOIR_FOU:
                    if (jeu.peutUtiliserPouvoirFou())
                        pouvoirsJouables.add(i);
                    break;
                case Coup.FINIR_TOUR:
                    if (jeu.peutFinirTour())
                        pouvoirsJouables.add(i);
                    break;
            }
        }
        if (!pouvoirsJouables.isEmpty())
            return pouvoirsJouables.get(r.nextInt(pouvoirsJouables.size()));
        return -1;
    }

    int nbCartesJouables() {
        List<Carte> cartesJouables = new ArrayList<>();
        for (int i = 0; i < jeu.getMain(jeu.getJoueurCourant()).getTaille(); i++) {
            Carte carte = jeu.getMain(jeu.getJoueurCourant()).getCarte(i);
            if (jeu.peutSelectionnerCarte(carte))
                cartesJouables.add(carte);
        }
        return cartesJouables.size();
    }

    Carte choisirCarte() {
        List<Carte> cartesJouables = new ArrayList<>();
        for (int i = 0; i < jeu.getMain(jeu.getJoueurCourant()).getTaille(); i++) {
            Carte carte = jeu.getMain(jeu.getJoueurCourant()).getCarte(i);
            if (jeu.peutSelectionnerCarte(carte))
                cartesJouables.add(carte);
        }
        if (!cartesJouables.isEmpty())
            return cartesJouables.get(r.nextInt(cartesJouables.size()));
        return null;
    }

    Pion choisirPion() {
        List<Pion> pionsJouables = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Pion pion = Pion.valeurEnPion(i);
            if (jeu.peutSelectionnerPion(pion))
                pionsJouables.add(pion);
        }
        if (!pionsJouables.isEmpty())
            return pionsJouables.get(r.nextInt(pionsJouables.size()));
        return null;
    }

    int choisirDirection() {
        List<Integer> directionsJouables = new ArrayList<>();
        int rand;
        for (int i = 0; i < 2; i++) {
            int direction;
            if (i == 0)
                direction = Plateau.DIRECTION_VRT;
            else
                direction = Plateau.DIRECTION_RGE;
            if (jeu.peutSelectionnerDirection(direction))
                directionsJouables.add(direction);
        }
        if (!directionsJouables.isEmpty()){
            rand = r.nextInt(9);
            if(directionsJouables.size() == 1){
                return directionsJouables.get(0);
            }
            else{
                if(jeu.getJoueurCourant() == Jeu.JOUEUR_VRT){
                    if(rand <= 7){
                        return Plateau.DIRECTION_VRT;
                    }
                    else{
                        return Plateau.DIRECTION_RGE;
                    }
                }
                else{
                    if(rand <= 7){
                        return Plateau.DIRECTION_RGE;
                    }
                    else{
                        return Plateau.DIRECTION_VRT;
                    }
                }
            }
        }
        return Plateau.DIRECTION_IND;
    }
}