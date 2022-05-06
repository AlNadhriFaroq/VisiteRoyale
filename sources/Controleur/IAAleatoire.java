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
        Coup c;
        do{
            c = jeu.creerCoup(typeCoup, cartes, pions, deplacements, directions);
        }while(c == null);
        return c;
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
        Carte carte;
        int pion;
        boolean carteSpeciale = false;
        do{
            carte = choixCartes();
            if(carte.estDeplacementGarCentre() || carte.estDeplacementFouCentre()){
                carteSpeciale = true;
            }
            else if(carte.estDeplacementGar1Plus1()){
                carteSpeciale = estCarteSpeciale(carte);
            }
            direction = choixDirections();
            pion = carte.getType();
        }while(!carteSpeciale && !jeu.pionEstDeplacable(pion, jeu.getPositionPion(pion) + carte.getDeplacement() * direction));
        if(!carteSpeciale) {
            cartes[cartes.length - 1] = carte;
            directions[directions.length - 1] = direction;
            pions[pions.length - 1] = pion;
            deplacements[deplacements.length - 1] = carte.getDeplacement();
        }
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
        for(int i = 0; cartesRoi < 2; i++){
            if(jeu.getMain(joueur).getCarte(i).estType(Type.ROI)){
                cartes[cartesRoi] = jeu.getMain(joueur).getCarte(i);
                cartesRoi ++;
            }
        }
        int direction;
        do{direction = choixDirections();
        }while(jeu.peutUtiliserPrivilegeRoi(cartes, direction));
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
        boolean milieu = false;
        do{
            carte = choixCartes();
            direction = choixDirections();
            if (jeu.getTypeCourant() == Type.GAR)
                pion = r.nextInt(2) + 1;
            else
                pion = choixPion();
            if(carte.estDeplacementFouCentre()){
                if(jeu.pionEstDeplacable(pion, 8)){
                    cartes[0] = carte;
                    pions[0] = pion;
                    milieu = true;
                }
            }
        }while(!milieu && !jeu.pionEstDeplacable(pion, jeu.getPositionPion(pion) + carte.getDeplacement() * direction) && !carte.estType(Type.FOU) && !(jeu.getTypeCourant() == jeu.getTypePion(pion)));
        if(!milieu){
            cartes[cartes.length - 1] = carte;
            directions[directions.length - 1] = direction;
            pions[pions.length - 1] = pion;
            deplacements[deplacements.length - 1] = carte.getDeplacement();
        }
    }


    void nettoyer(){
        cartes = null;
        pions = null;
        deplacements = null;
        directions = null;
    }

    boolean estCarteSpeciale(Carte carte){
        boolean ok = false;
        boolean[][] dir = new boolean[2][4];
        if(jeu.pionEstDeplacable(Pion.GAR_VRT, jeu.getPositionPion(Pion.GAR_VRT) + 1)){
            dir[0][0] = true;
        }
        if(jeu.pionEstDeplacable(Pion.GAR_VRT, jeu.getPositionPion(Pion.GAR_VRT) - 1)){
            dir[0][1] = true;
        }
        if(jeu.pionEstDeplacable(Pion.GAR_VRT, jeu.getPositionPion(Pion.GAR_VRT) + 2)){
            dir[0][2] = true;
        }
        if(jeu.pionEstDeplacable(Pion.GAR_VRT, jeu.getPositionPion(Pion.GAR_VRT) - 2)){
            dir[0][3] = true;
        }
        if(jeu.pionEstDeplacable(Pion.GAR_RGE, jeu.getPositionPion(Pion.GAR_RGE) + 1)){
            dir[1][0] = true;
        }
        if(jeu.pionEstDeplacable(Pion.GAR_RGE, jeu.getPositionPion(Pion.GAR_RGE) - 1)){
            dir[1][1] = true;
        }
        if(jeu.pionEstDeplacable(Pion.GAR_RGE, jeu.getPositionPion(Pion.GAR_RGE) + 2)){
            dir[1][2] = true;
        }
        if(jeu.pionEstDeplacable(Pion.GAR_RGE, jeu.getPositionPion(Pion.GAR_RGE) - 2)){
            dir[1][3] = true;
        }

        int i = r.nextInt(2);
        int j = r.nextInt(4);

        switch (j){
            case 0:
                if(i == 0){
                    if(dir[i][j]){
                        if(dir[1][j]){
                            cartes[0] = carte;
                            directions[0] = -1;
                            directions[1] = -1;
                            pions[0] = Pion.GAR_VRT;
                            pions[1] = Pion.GAR_RGE;
                            deplacements[0] = 1;
                            deplacements[1] = 1;
                            ok = true;
                        }
                        else if (dir[1][1]) {
                            cartes[0] = carte;
                            directions[0] = 1;
                            directions[1] = -1;
                            pions[0] = Pion.GAR_VRT;
                            pions[1] = Pion.GAR_RGE;
                            deplacements[0] = 1;
                            deplacements[1] = 1;
                            ok = true;
                        }
                    }
                }
                else{
                    if(dir[i][j]){
                        if(dir[0][j]){
                            cartes[0] = carte;
                            directions[0] = 1;
                            directions[1] = 1;
                            pions[0] = Pion.GAR_RGE;
                            pions[1] = Pion.GAR_VRT;
                            deplacements[0] = 1;
                            deplacements[1] = 1;
                            ok = true;
                        }
                        else if (dir[i][1]) {
                            cartes[0] = carte;
                            directions[0] = 1;
                            directions[1] = -1;
                            pions[0] = Pion.GAR_RGE;
                            pions[1] = Pion.GAR_VRT;
                            deplacements[0] = 1;
                            deplacements[1] = 1;
                            ok = true;
                        }
                    }
                }
            case 1:
                if(i == 0){
                    if(dir[i][j]){
                        if(dir[1][j]){
                            cartes[0] = carte;
                            directions[0] = -1;
                            directions[1] = -1;
                            pions[0] = Pion.GAR_VRT;
                            pions[1] = Pion.GAR_RGE;
                            deplacements[0] = 1;
                            deplacements[1] = 1;
                            ok = true;
                        }
                        else if (dir[1][0]) {
                            cartes[0] = carte;
                            directions[0] = -1;
                            directions[1] = 1;
                            pions[0] = Pion.GAR_VRT;
                            pions[1] = Pion.GAR_RGE;
                            deplacements[0] = 1;
                            deplacements[1] = 1;
                            ok = true;
                        }
                    }
                }
                else{
                    if(dir[i][j]){
                        if(dir[0][j]){
                            cartes[0] = carte;
                            directions[0] = -1;
                            directions[1] = -1;
                            pions[0] = Pion.GAR_RGE;
                            pions[1] = Pion.GAR_VRT;
                            deplacements[0] = 1;
                            deplacements[1] = 1;
                            ok = true;
                        }
                        else if (dir[0][0]) {
                            cartes[0] = carte;
                            directions[0] = -1;
                            directions[1] = 1;
                            pions[0] = Pion.GAR_RGE;
                            pions[1] = Pion.GAR_VRT;
                            deplacements[0] = 1;
                            deplacements[1] = 1;
                            ok = true;
                        }
                    }
                }
            case 2:
                if(dir[i][j]){
                    cartes[0] = carte;
                    directions[0] = 1;
                    if(i == 0)
                        pions[0] = Pion.GAR_VRT;
                    else
                        pions[0] = Pion.GAR_RGE;
                    deplacements[0] = 2;
                    ok = true;
                }
                break;
            case 3:
                if(dir[i][j]){
                    cartes[0] = carte;
                    directions[0] = -1;
                    if(i == 0)
                        pions[0] = Pion.GAR_VRT;
                    else
                        pions[0] = Pion.GAR_RGE;
                    deplacements[0] = 2;
                    ok = true;
                }
                break;
        }
        return ok;
    }
}
