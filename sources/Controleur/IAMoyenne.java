package Controleur;

import Modele.*;

import java.util.Random;

public class IAMoyenne extends IA{
    //int roi, garde, fou, sorcier;
    Random r;
    int joueur;
    /*int tailleMain;
    int typeCoup;
    Carte[] cartes;
    Pion[] pions;
    int[] destinations;*/

    IAMoyenne(Jeu jeu) {
        super(jeu);
        r = new Random();
        joueur = jeu.getJoueurCourant();
    }
/*
    @Override
    Coup calculerCoup() {
        tailleMain = jeu.getMain(joueur).getTaille();
        nombreTypeCartes();
        int type = choixTypesCartes();
        cartes = new Carte[2];
        pions = new Pion[2];
        destinations = new int[2];
        Coup coup;
        if(typeCoup == Coup.POUVOIR_SOR) {
            typeCoup = Coup.FIN_TOUR;
            coup = jeu.creerCoup(typeCoup, cartes, pions, destinations);
        }
        else{
            do {
                coup = jeu.creerCoup(typeCoup, cartes, pions, destinations);
            } while (coup == null);
        }
        return coup;
    }

    void nombreTypeCartes(){
        fou = jeu.getMain(joueur).getNombreTypeCarte(Type.FOU);
        roi = jeu.getMain(joueur).getNombreTypeCarte(Type.ROI);
        garde = jeu.getMain(joueur).getNombreTypeCarte(Type.GAR);
        sorcier = jeu.getMain(joueur).getNombreTypeCarte(Type.SOR);
    }

    int choixTypesCartes(){

        return 1;
    }

    boolean coupEstGagnantEtCoupRoiDansChateau(){
        Jeu j = jeu.clone();
        boolean coupGagnant = false;
        switch(joueur){
            case Jeu.JOUEUR_VRT:
                if(jeu.getPlateau().getPositionPion(Pion.GAR_VRT) < jeu.getPlateau().CHATEAU_VRT){
                    if(jeu.getPlateau().getPositionPion(Pion.SOR) == jeu.getPlateau().CHATEAU_VRT && jeu.getTypeCourant().equals(Type.IND)){
                        pions[0] = Pion.ROI;
                        typeCoup = Coup.POUVOIR_SOR;
                        coupGagnant = true;
                    }
                    else if(jeu.getPlateau().getPositionPion(Pion.ROI) - roi <= jeu.getPlateau().CHATEAU_VRT){
                        int i = 0;
                        Carte carte = jeu.getMain(joueur).getCarte(i);
                        while(carte.getType() != Type.ROI && i < tailleMain){
                            i++;
                            carte = jeu.getMain(joueur).getCarte(i);
                        }
                        cartes[0] = carte;
                        pions[0] = Pion.ROI;
                        destinations[0] = - 1;
                        //return new Coup(joueur, Coup.DEPLACEMENT, cartes, pions, destinations);
                        coupGagnant = true;
                    }
                }
                break;
            case Jeu.JOUEUR_RGE:
                if(jeu.getPlateau().getPositionPion(Pion.GAR_RGE) < jeu.getPlateau().CHATEAU_RGE){
                    if(jeu.getPlateau().getPositionPion(Pion.SOR) == jeu.getPlateau().CHATEAU_VRT && jeu.getTypeCourant().equals(Type.IND)){
                        pions[0] = Pion.ROI;
                        typeCoup = Coup.POUVOIR_SOR;
                        coupGagnant = true;
                    }
                    else if(jeu.getPlateau().getPositionPion(Pion.ROI) + roi >= jeu.getPlateau().CHATEAU_RGE){
                        int i = 0;
                        Carte carte = jeu.getMain(joueur).getCarte(i);
                        while(carte.getType() != Type.ROI && i < tailleMain){
                            i++;
                            carte = jeu.getMain(joueur).getCarte(i);
                        }
                        cartes[0] = carte;
                        pions[0] = Pion.ROI;
                        destinations[0] = 1;
                        //return new Coup(joueur, Coup.DEPLACEMENT, cartes, pions, destinations);
                        coupGagnant = true;
                    }
                    else if(coupGagnantRoiChateauPouvoirFou()) {
                        coupGagnant = true;
                    }
                }
                break;
            default:
                System.err.println("Le joueur n'existe pas");
                break;
        }
        return coupGagnant;
    }

    boolean coupGagnantRoiChateauPouvoirFou(){
        if(!jeu.peutUtiliserPouvoirFou())
            return false;
        int positionFou, positionRoi;
        positionFou = jeu.getPlateau().getPositionPion(Pion.FOU);
        positionRoi = jeu.getPlateau().getPositionPion(Pion.ROI);
        int[] nbDeplacement = new int[fou];
        int [] nbCartesFinirCoup = new int[fou];
        int nbCarteFou = 0;
        int sommeDeplacement = 0;
        int maxDeplacement = 0;
        Carte carte;
        for(int i = 0; nbCarteFou < fou; i++){
            carte = jeu.getMain(joueur).getCarte(i);
            if(carte.getType().equals(Type.FOU) && !carte.estDeplacementFouCentre()){
                nbDeplacement[nbCarteFou] = carte.getDeplacement();
                sommeDeplacement += carte.getDeplacement();
                if(carte.getDeplacement() > maxDeplacement)
                    maxDeplacement = carte.getDeplacement();
            }
        }
        if(sommeDeplacement + jeu.getPlateau().getPositionPion(Pion.ROI) >= jeu.getPlateau().CHATEAU_RGE){
            for(int d = 0; d < nbDeplacement.length; d++){
                if(jeu.getPlateau().CHATEAU_RGE - d < positionFou && jeu.getPlateau().CHATEAU_RGE >= positionRoi)
                    nbCartesFinirCoup[d] = nbDeplacement[d];
            }
            for(int de: nbCartesFinirCoup){
                if(positionRoi + de == jeu.getPlateau().CHATEAU_RGE){
                    for(int c = 0; c < tailleMain; c++){
                        if(jeu.getMain(joueur).getCarte(c).getDeplacement() == de){
                            cartes[0] = jeu.getMain(joueur).getCarte(c);
                            break;
                        }
                    }
                    pions[0] = Pion.ROI;
                    destinations[0] = positionRoi + de;
                    return true;
                }
            }
            int doublon = 0;
            int distance = 0;
            if(nbDeplacement.length >= 2){
            for(int i = 0; i < nbDeplacement.length; i++){
                for(int j = 0; j < nbDeplacement.length; j++){
                    if(nbDeplacement[i] == nbCartesFinirCoup[j] && doublon == 0)
                        doublon ++;
                    else {
                        distance = nbDeplacement[i] + nbCartesFinirCoup[j];
                    }
                    if((distance + positionRoi) == jeu.getPlateau().CHATEAU_RGE)
                        return true;
                }
            }
        }
        }
        else
            return false;
        return true;
    }*/
}
