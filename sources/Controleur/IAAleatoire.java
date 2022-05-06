package Controleur;

import Modele.*;
import java.util.*;

class IAAleatoire extends IA {
    Random r;
    int joueur;
    int tailleMain;
    int typeCoup;
    Carte[] cartes;
    int[] pions;
    int[] deplacements;
    int[] directions;
    public IAAleatoire(Jeu jeu) {
        super(jeu);
        r = new Random();
        joueur = jeu.getJoueurCourant();

    }

    @Override
    public Coup elaborerCoup() {
        tailleMain = jeu.getMain(joueur).getTaille();
        nettoyer();
        int typeCoup = choixTypeCoup();
        choixCartesDirections(typeCoup);
        return new Coup(joueur, typeCoup, cartes, pions, deplacements, directions);
    }

    void choixCartesDirections(int typeCoup){
        switch(typeCoup){
            case Coup.DEPLACEMENT:
                choixCartesDeplacement();
                break;
            case Coup.PRIVILEGE_ROI:
                choixCartesPrivilegesRoi();
                break;
            case Coup.POUVOIR_SOR:
                choixCartePouvoirSorcier();
                break;
            case Coup.POUVOIR_FOU:
                choixCartesPouvoirFou();
                break;
            default:
                break;
        }
    }

    int choixTypeCoup(){
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
                this.typeCoup = Coup.DEPLACEMENT;
                break;
            case Coup.PRIVILEGE_ROI:
                possible = jeu.peutUtiliserPrivilegeRoi();
                this.typeCoup = Coup.PRIVILEGE_ROI;
                break;
            case Coup.POUVOIR_SOR:
                possible = jeu.peutUtiliserPouvoirSorcier();
                this.typeCoup = Coup.POUVOIR_SOR;
                break;
            case Coup.POUVOIR_FOU:
                possible = jeu.peutUtiliserPouvoirFou();
                this.typeCoup = Coup.POUVOIR_FOU;
                break;
            case Coup.FIN_TOUR:
                possible = jeu.peutFinirTour();
                this.typeCoup = Coup.FIN_TOUR;
                break;
            default:
                possible = false;
                break;
        }
        return possible;
    }

    void choixCartesDeplacement(){
        int direction;
        int nbGarde = 0;
        Carte carte;
        int pion;
        do{
            carte = choixCartes();
            if(carte.estDeplacementGar1Plus1() || carte.estDeplacementGarCentre() || carte.estDeplacementFouCentre()){
                nbGarde = r.nextInt(2) +1;
                if (nbGarde == 1)
                    return;
            }
            direction = choixDirections();
            pion = carte.getType();
        }while(!jeu.pionEstDeplacable(pion, jeu.getPositionPion(pion) + carte.getDeplacement()));
        if(carte.getType() == Carte.DEPLACEMENT_GAR_1PLUS1){
            int rand = 0;
            if(jeu.pionEstDeplacable(Pion.GAR_RGE, 1) || jeu.pionEstDeplacable(Pion.GAR_RGE, -1)) {
                rand++;
                if (jeu.pionEstDeplacable(Pion.GAR_VRT, 1) || jeu.pionEstDeplacable(Pion.GAR_VRT, -1)) {
                    rand++;
                }
            }
            if(rand == 2)
                nbGarde = r.nextInt(2);


        }
        cartes[cartes.length - 1] = carte;
        directions[directions.length - 1] = direction;
        pions[pions.length - 1] = pion;
        deplacements[deplacements.length - 1] = carte.getDeplacement();
    }

    Carte choixCartes(){
        Carte carte;
        do{
            carte = jeu.getMain(joueur).getCarte(r.nextInt(tailleMain));
        }while((jeu.getTypeCourant() != carte.getType()) && !(jeu.getTypeCourant() == Type.IND));
        return carte;
    }

    int choixPion(){
        return r.nextInt(5);
    }
    int choixDirections(){
        int direction = r.nextInt(100) + 1;
        if(direction > 80)
            direction = -jeu.getDirectionJoueur(joueur);
        else
            direction = jeu.getDirectionJoueur(joueur);
        return direction;
    }

    void choixCartesPrivilegesRoi(){
        int cartesRoi = 0;
        for(int i = 0; i < tailleMain && cartesRoi < 2; i++){
            if(jeu.getMain(joueur).getCarte(i).estType(Type.ROI)){
                cartes[cartesRoi] = jeu.getMain(joueur).getCarte(i);
                cartesRoi ++;
            }
        }
        int direction;
        do{direction = choixDirections();
        }while(jeu.peutUtiliserPrivilegeRoi(joueur, cartes, direction));
        directions[0] = direction;
    }

    void choixCartePouvoirSorcier(){
        int pion;
        do{
            pion = choixPion();
        }while(jeu.peutUtiliserPouvoirSorcier(pion));
        pions[0] = pion;
    }

    void choixCartesPouvoirFou(){
        int direction;
        Carte carte;
        int pion;
        do{
            carte = choixCartes();
            direction = choixDirections();
            if (jeu.getTypeCourant() == Type.GAR)
                pion = r.nextInt(2) + 1;
            else
                pion = choixPion();
        }while(!jeu.pionEstDeplacable(pion, carte.getDeplacement()) && !carte.estType(Type.FOU) && !(jeu.getTypeCourant() == pion));
        cartes[cartes.length - 1] = carte;
        directions[directions.length - 1] = direction;

        pions[pions.length - 1] = pion;
        deplacements[deplacements.length - 1] = carte.getDeplacement();
    }


    void nettoyer(){
        cartes = null;
        pions = null;
        deplacements = null;
        directions = null;
    }
}
