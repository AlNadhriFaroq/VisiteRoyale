package IA;

import Modele.Coup;
import Modele.Jeu;
import Modele.Pion;
import Structures.Tas;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

public class IAMinMax extends IA{
    private static final int  PROFONDEUR = 1 ;
    Tas<Jeu> lj;
    //List<Jeu> lj;
    List<Integer> lpoids;
    Hashtable<Jeu,List<Coup>> listeDeJeu;
    Hashtable<Jeu,Integer> listePoidJeuConf ;
    Hashtable<Jeu,Jeu>predecessor;
    List<Coup> listeDeCoup ;
    List<List<Coup>> nomdelaliste;


    public IAMinMax(Jeu jeu) {
        super(jeu);
        lj = new Tas<>();
    }
    public Coup calculerCoup (){
        Random r = new Random();
        //System.out.println("lj : " + evaluationTour(jeu));
        evaluationTour(jeu);
        Jeu j = lj.extraire();
        List<Coup> lc = jeu.calculerListeCoup();
        return lc.get(r.nextInt(lc.size()));
    }

    private boolean  estFeuille(Jeu jeu){
        return false;
    }
    private int minMaxA (Jeu jeu , int profondeur){
        int valeur ;
        if (estFeuille(jeu)){
            return evaluationTerrain(jeu);
        }
        if (profondeur== PROFONDEUR ){
            return 0 ;
        }
        else {
            //List<Jeu> lj  =  evaluationTour(jeu);
            valeur = Integer.MIN_VALUE ;
            /*for (Jeu j: lj)
                 valeur =  Math.max(evaluationTerrain(j),minMaxB(j, profondeur+1));*/
        }
        return valeur ;
    }
    private int minMaxB (Jeu jeu , int profondeur){
        int valeur ;
        if (estFeuille(jeu))
            return evaluationTerrain(jeu);
        if (profondeur==  PROFONDEUR)
            return 0;
        else {
            //List<Jeu> lj =  evaluationTour(jeu);
            valeur = Integer.MAX_VALUE ;
            /*for (Jeu j: lj){
                valeur =  Math.min(evaluationTerrain(j),minMaxA(j, profondeur+1));
            }*/
        }
        return valeur ;
    }

    private void evaluationTour (Jeu jeu){
        List<Coup> lc =  jeu.calculerListeCoup();
        for (Coup c : lc){
            evaluationTourRec(c , jeu);
        }
    }
    private void evaluationTourRec (Coup coup , Jeu jeu){
        jeu.jouerCoup(coup);
        if (coup.getTypeCoup() == Coup.FINIR_TOUR ) {
            lj.inserer(jeu, evaluationTerrain(jeu));
        }else{
            List<Coup> lc = jeu.calculerListeCoup();
            Jeu jeuSuite;
            for (Coup c : lc){
                jeuSuite = jeu.clone();
                evaluationTourRec(c, jeuSuite);
            }
        }
    }

    private int evaluationTerrain (Jeu jeu){
        int valeur = 0;
        if(jeu.getJoueurCourant() == Jeu.JOUEUR_RGE){
            if(jeu.pionDansChateau(Jeu.JOUEUR_VRT, Pion.GAR_VRT))
                valeur -= 2;
            else if(jeu.pionDansDuche(Jeu.JOUEUR_VRT, Pion.GAR_VRT))
                valeur -= 1;
            else if(jeu.pionDansDuche(Jeu.JOUEUR_RGE, Pion.GAR_VRT))
                valeur += 1;
            if(jeu.pionDansDuche(Jeu.JOUEUR_VRT, Pion.GAR_RGE))
                valeur -= 1;
            else if(jeu.pionDansChateau(Jeu.JOUEUR_RGE, Pion.GAR_RGE))
                valeur += 2;
            else if(jeu.pionDansDuche(Jeu.JOUEUR_RGE, Pion.GAR_RGE))
                valeur += 1;
            if(jeu.pionDansChateau(Jeu.JOUEUR_VRT, Pion.SOR))
                valeur -= 3;
            else if(jeu.pionDansDuche(Jeu.JOUEUR_VRT, Pion.SOR))
                valeur -= 1;
            else if(jeu.pionDansChateau(Jeu.JOUEUR_RGE, Pion.SOR))
                valeur += 3;
            else if(jeu.pionDansDuche(Jeu.JOUEUR_RGE, Pion.SOR))
                valeur += 1;
            if(jeu.pionDansChateau(Jeu.JOUEUR_VRT, Pion.FOU))
                valeur -= 2;
            else if(jeu.pionDansDuche(Jeu.JOUEUR_VRT, Pion.FOU))
                valeur -= 1;
            else if(jeu.pionDansDuche(Jeu.JOUEUR_RGE, Pion.FOU))
                valeur += 1;
            else if(jeu.pionDansChateau(Jeu.JOUEUR_RGE, Pion.FOU))
                valeur += 2;
            if(jeu.pionDansDuche(Jeu.JOUEUR_RGE, Pion.ROI))
                valeur += 1;
            else if(jeu.pionDansDuche(Jeu.JOUEUR_VRT, Pion.ROI))
                valeur -= 1;
            if (jeu.getPlateau().getPositionPion(Pion.FOU) > jeu.getPlateau().getPositionPion(Pion.ROI))
                valeur += 1;
            else if(jeu.getPlateau().getPositionPion(Pion.FOU) < jeu.getPlateau().getPositionPion(Pion.ROI))
                valeur -= 1;
        }

        if(jeu.getJoueurCourant() == Jeu.JOUEUR_VRT) {
            if(jeu.pionDansChateau(Jeu.JOUEUR_VRT, Pion.GAR_VRT))
                valeur += 2;
            else if(jeu.pionDansDuche(Jeu.JOUEUR_VRT, Pion.GAR_VRT))
                valeur += 1;
            else if(jeu.pionDansDuche(Jeu.JOUEUR_RGE, Pion.GAR_VRT))
                valeur += 1;
            if(jeu.pionDansDuche(Jeu.JOUEUR_VRT, Pion.GAR_RGE))
                valeur += 1;
            else if(jeu.pionDansChateau(Jeu.JOUEUR_RGE, Pion.GAR_RGE))
                valeur -= 2;
            else if(jeu.pionDansDuche(Jeu.JOUEUR_RGE, Pion.GAR_RGE))
                valeur -= 1;
            if(jeu.pionDansChateau(Jeu.JOUEUR_VRT, Pion.SOR))
                valeur += 3;
            else if(jeu.pionDansDuche(Jeu.JOUEUR_VRT, Pion.SOR))
                valeur += 1;
            else if(jeu.pionDansChateau(Jeu.JOUEUR_RGE, Pion.SOR))
                valeur -= 3;
            else if(jeu.pionDansDuche(Jeu.JOUEUR_RGE, Pion.SOR))
                valeur -= 1;
            if(jeu.pionDansChateau(Jeu.JOUEUR_VRT, Pion.FOU))
                valeur += 2;
            else if(jeu.pionDansDuche(Jeu.JOUEUR_VRT, Pion.FOU))
                valeur += 1;
            else if(jeu.pionDansDuche(Jeu.JOUEUR_RGE, Pion.FOU))
                valeur -= 1;
            else if(jeu.pionDansChateau(Jeu.JOUEUR_RGE, Pion.FOU))
                valeur -= 2;
            if(jeu.pionDansDuche(Jeu.JOUEUR_VRT, Pion.ROI))
                valeur += 1;
            else if(jeu.pionDansDuche(Jeu.JOUEUR_RGE, Pion.ROI))
                valeur -= 1;
            if (jeu.getPlateau().getPositionPion(Pion.FOU) < jeu.getPlateau().getPositionPion(Pion.ROI))
                valeur += 1;
            else if(jeu.getPlateau().getPositionPion(Pion.FOU) > jeu.getPlateau().getPositionPion(Pion.ROI))
                valeur -= 1;
        }
        return valeur;
    }
}
