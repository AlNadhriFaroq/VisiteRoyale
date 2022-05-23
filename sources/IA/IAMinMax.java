package IA;

import Modele.Coup;
import Modele.Jeu;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class IAMinMax extends IA{
    private static final int  PROFONDEUR = 1 ;
    List<Coup> lc;
    Hashtable<Jeu,List<Coup>> listeDeJeu;
    Hashtable<Jeu,Integer> listePoidJeuConf ;
    Hashtable<Jeu,Jeu>predecessor;
    List<Coup> listeDeCoup ;
    List<List<Coup>> nomdelaliste;


    public IAMinMax(Jeu jeu) {
        super(jeu);
    }
    public Coup calculerCoup (){
        return null;
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
            List<Jeu> lj  =  evaluationTour(jeu);
            valeur = Integer.MIN_VALUE ;
            for (Jeu j: lj)
                 valeur =  Math.max(evaluationTerrain(j),minMaxB(j, profondeur+1));
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
            List<Jeu> lj =  evaluationTour(jeu);
            valeur = Integer.MAX_VALUE ;
            for (Jeu j: lj){
                valeur =  Math.min(evaluationTerrain(j),minMaxA(j, profondeur+1));
            }
        }
        return valeur ;
    }
    private int evaluationTerrain (Jeu jeu){
        return 0;
    }
    private List<Jeu> evaluationTour (Jeu jeu){
        List<Coup> listeconf =  null;
        List<Jeu> lj = null ;
        List<Coup> lc =  jeu.calculerListeCoup();
        for (Coup c : lc){
            listeconf = new ArrayList<>( );
            evaluationTourRec(lj,c , jeu , listeconf );
        }
        return null;
    }
    private void evaluationTourRec (List<Jeu> lj, Coup coup , Jeu jeu, List<Coup> listconf){
        listconf.add(coup);
        Jeu jeuNew = jeu.clone();
        jeuNew.jouerCoup(coup);
        if (coup.getTypeCoup() == Coup.FINIR_TOUR ) {
            lj.add(jeuNew);
            listeDeJeu.put(jeu,listconf);
            listePoidJeuConf.put(jeu,evaluationTerrain(jeu));
        }
        List<Coup> lc = jeuNew.calculerListeCoup();
        for (Coup c : lc){
            evaluationTourRec(lj,c, jeuNew,listconf);
        }

    }
}
