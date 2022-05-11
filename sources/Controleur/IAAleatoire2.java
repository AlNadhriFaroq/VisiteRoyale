package Controleur;

import Modele.*;

import java.util.Random;

class IAAleatoire2 extends IA {
    Random r;
    int joueur;
    int tailleMain;
    IAAleatoire2(Jeu jeu) {
        super(jeu);
        r = new Random();

    }

    @Override
    public Coup calculerCoup(){
        joueur = jeu.getJoueurCourant();
        tailleMain = jeu.getMain(joueur).getTaille();
        switch(jeu.getEtatJeu()){
            case Jeu.ETAT_CHOIX_CARTE:
                System.out.println("etat choix carte");
                Coup c = choixCarte();
                while(c == null){
                    c = choixCarte();
                }
                return c;
            case Jeu.ETAT_CHOIX_PION:
                System.out.println("etat choix pion");
                return choixPion();
            case Jeu.ETAT_CHOIX_DIRECTION:
                System.out.println("etat choix direction");
                return choixDirection();
            default:
                System.err.println("Erreur d'etat dans le jeu");
                return null;
        }
    }

    Coup choixCarte(){
        Carte carte;
        if(!jeu.getActivationPouvoirFou() && !jeu.getActivationPouvoirSor() && jeu.getActivationPrivilegeRoi() == 0 && !jeu.peutFinirTour()){ //tout debut
            System.out.println("Debut du tour");
            int c = r.nextInt(3);
            switch (c){
                case Coup.CHOISIR_CARTE:
                    carte = choisirCartes();
                    System.out.println("Choisir carte : " + carte);
                    if(carte == null){
                        return new Coup(joueur, Coup.FINIR_TOUR, null, null, 0);
                    }
                    return new Coup(joueur, Coup.CHOISIR_CARTE, carte, null, 0);
                case Coup.ACTIVER_POUVOIR_SOR:
                    System.out.println("Activer Pouvoir Sorcier");
                    if(!jeu.peutUtiliserPouvoirSorcier()){
                        return null;
                    }
                    return new Coup(joueur, Coup.ACTIVER_POUVOIR_SOR, null, null, 0);
                case Coup.ACTIVER_POUVOIR_FOU:
                    System.out.println("Activer Pouvoir Fou");
                    return new Coup(joueur, Coup.ACTIVER_POUVOIR_FOU, null, null, 0);
                case Coup.FINIR_TOUR:
                    System.out.println("Finir Tour");
                    return new Coup(joueur, Coup.FINIR_TOUR, null, null, 0);
            }
        }
        else if(jeu.getActivationPouvoirFou()){
            carte = choisirCartes();
            if(carte == null){
                return new Coup(joueur, Coup.FINIR_TOUR, null, null, 0);
            }
            return new Coup(joueur, Coup.CHOISIR_CARTE, carte, null, 0);
        }
        if(!jeu.peutUtiliserPouvoirSorcier() && ! jeu.peutUtiliserPouvoirFou()){
            return new Coup(joueur, Coup.FINIR_TOUR, null, null, 0);
        }
            return null;
    }
    private Carte choisirCartes(){
        if(jeu.getMain(joueur).getTaille() == 0){
            return null;
        }
        Carte carte;
        int arret = 0;
        do {
            carte = jeu.getMain(joueur).getCarte(r.nextInt(tailleMain));
            arret ++;
        } while (!jeu.peutSelectionnerCarte(carte) && arret < 100);
        return carte;
    }

    Coup choixPion(){
        if(jeu.getActivationPouvoirSor()){
            Pion pion;
            int p;
            do{
                p = r.nextInt(3);
                pion = Pion.valeurEnPion(p);
            }while(!jeu.peutSelectionnerPion(pion));
            return new Coup(joueur, Coup.CHOISIR_PION, null, pion, 0);
        }
        else if(jeu.getActivationPouvoirFou()){
            if(jeu.getTypeCourant().equals(Type.IND)){
                Pion pion;
                int p;
                do{
                    p = r.nextInt(4);
                    pion = Pion.valeurEnPion(p);
                }while(!jeu.peutSelectionnerPion(pion));
                return new Coup(joueur, Coup.CHOISIR_PION, null, pion, 0);
            }
            else if(jeu.getTypeCourant().equals(Type.GAR)){
                int g = r.nextInt(2);
                if (g == 0){
                    if(jeu.peutSelectionnerPion(Pion.GAR_VRT)){
                        return new Coup(joueur, Coup.CHOISIR_PION, null, Pion.GAR_VRT, 0);
                    }
                    else{
                        return new Coup(joueur, Coup.CHOISIR_PION, null, Pion.GAR_RGE, 0);
                    }
                }
                else{
                    if(jeu.peutSelectionnerPion(Pion.GAR_RGE)){
                        return new Coup(joueur, Coup.CHOISIR_PION, null, Pion.GAR_RGE, 0);
                    }
                    else{
                        return new Coup(joueur, Coup.CHOISIR_PION, null, Pion.GAR_VRT, 0);
                    }
                }
            }
        }
        if(!jeu.getActivationPouvoirFou() && !jeu.getActivationPouvoirSor() && jeu.getTypeCourant().equals(Type.GAR)){

            int g = r.nextInt(2);
            if (g == 0){
                if(jeu.peutSelectionnerPion(Pion.GAR_VRT)){
                    return new Coup(joueur, Coup.CHOISIR_PION, null, Pion.GAR_VRT, 0);
                }
                else{
                    return new Coup(joueur, Coup.CHOISIR_PION, null, Pion.GAR_RGE, 0);
                }
            }
            else{
                if(jeu.peutSelectionnerPion(Pion.GAR_RGE)){
                    return new Coup(joueur, Coup.CHOISIR_PION, null, Pion.GAR_RGE, 0);
                }
                else{
                    return new Coup(joueur, Coup.CHOISIR_PION, null, Pion.GAR_VRT, 0);
                }
            }
        }
        return null;
    }

    Coup choixDirection(){
        if(jeu.getActivationPouvoirFou()){
            int d = r.nextInt(2);
            if(d == 0){
                d = -1;
            }
            if (jeu.peutSelectionnerDirection(d)){
                return new Coup(joueur, Coup.CHOISIR_DIRECTION, null, null, d);
            }
            else{
                return new Coup(joueur, Coup.CHOISIR_DIRECTION, null, null, -d);
            }
        }
        else{
            if(jeu.getActivationPrivilegeRoi() == 1){
                int nbRoi = jeu.getMain(joueur).getNombreTypeCarte(Type.ROI);
                if(nbRoi >= 1){
                    int roi = r.nextInt(2);
                    if(roi == 0){
                        int d = r.nextInt(2);
                        if(d == 0){
                            d = -1;
                        }
                        if (jeu.peutSelectionnerDirection(d)){
                            return new Coup(joueur, Coup.CHOISIR_DIRECTION, null, null, d);
                        }
                        else{
                            return new Coup(joueur, Coup.CHOISIR_DIRECTION, null, null, -d);
                        }
                    }
                    else{
                        Carte carteRoi = jeu.getMain(joueur).getCarte(0);
                        for(int i = 0; i < tailleMain; i++){
                            if(jeu.getMain(joueur).getCarte(i).getType().equals(Type.ROI)){
                                carteRoi = jeu.getMain(joueur).getCarte(i);
                                break;
                            }
                        }
                        return new Coup(joueur, Coup.CHOISIR_DIRECTION, carteRoi, null, 0);
                    }
                }
            }
            else{
                int d = r.nextInt(2);
                if(d == 0){
                    d = -1;
                }
                if (jeu.peutSelectionnerDirection(d)){
                    return new Coup(joueur, Coup.CHOISIR_DIRECTION, null, null, d);
                }
                else{
                    return new Coup(joueur, Coup.CHOISIR_DIRECTION, null, null, -d);
                }
            }
        }
        return null;
    }
}
