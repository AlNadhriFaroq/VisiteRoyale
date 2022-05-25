package IA;

import Modele.Coup;
import Modele.Jeu;
import Modele.Pion;
import Modele.Plateau;
import Structures.Tas;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class IAMinMax extends IA {
    private static final int PROFONDEUR = 1;
    Tas<List<Coup>> lj;

    Hashtable<Jeu, List<Coup>> jeuListeCoup;
    List<Integer> lpoids;
    List<Coup> lcf;
    int tailleLcf;

    public IAMinMax(Jeu jeu) {
        super(jeu);
        lj = new Tas<>();
        lcf = new ArrayList<>();
    }

    @Override
    public Coup calculerCoup() {
        Coup cp = null;
        if (tailleLcf == lcf.size()) {
            evaluationTour();
            lcf = lj.extraire();
            System.out.println(lcf);
        }
        if (tailleLcf != lcf.size()) {
            cp = lcf.get(tailleLcf);
            tailleLcf++;
        }
        if (cp.getTypeCoup() == Coup.FINIR_TOUR) {
            tailleLcf = 0;
            lcf.clear();
            lj = new Tas<>();
        }
        return cp;
    }

    private boolean estFeuille(Jeu jeu) {
        return false;
    }

    private int minMaxA(Jeu jeu, int profondeur) {
        int valeur;
        if (estFeuille(jeu)) {
            return evaluationTerrain();
        }
        if (profondeur == PROFONDEUR) {
            return 0;
        } else {
            //List<Jeu> lj  =  evaluationTour(jeu);
            valeur = Integer.MIN_VALUE;
            /*for (Jeu j: lj)
                 valeur =  Math.max(evaluationTerrain(j),minMaxB(j, profondeur+1));*/
        }
        return valeur;
    }

    private int minMaxB(Jeu jeu, int profondeur) {
        int valeur;
        if (estFeuille(jeu))
            return evaluationTerrain();
        if (profondeur == PROFONDEUR)
            return 0;
        else {
            //List<Jeu> lj =  evaluationTour(jeu);
            valeur = Integer.MAX_VALUE;
            /*for (Jeu j: lj){
                valeur =  Math.min(evaluationTerrain(j),minMaxA(j, profondeur+1));
            }*/
        }
        return valeur;
    }

    private void evaluationTour() {
        List<Coup> listeCoup = new ArrayList<>();
        List<Coup> lc = jeu.calculerListeCoup();
        for (Coup c : lc) {
            evaluationTourRec(c, listeCoup);
            listeCoup.remove(listeCoup.size() - 1);
            c.fixerJeu(jeu);
            c.desexecuter();
        }
    }

    private void evaluationTourRec(Coup coup, List<Coup> listeCoup) {
        jeu.jouerCoup(coup);
        listeCoup.add(coup);
        if (coup.getTypeCoup() == Coup.FINIR_TOUR) {
            List<Coup> tmp = new ArrayList<>(listeCoup);
            lj.inserer(tmp, evaluationTerrain());
        } else {
            List<Coup> lc = jeu.calculerListeCoup();
            for (Coup c : lc) {
                evaluationTourRec(c, listeCoup);
                listeCoup.remove(listeCoup.size() - 1);
                c.fixerJeu(jeu);
                c.desexecuter();
            }
        }
    }

    private int evaluationTerrain() {
        int valeur = 0;
        if (jeu.getJoueurCourant() != Jeu.JOUEUR_RGE) {
            if (jeu.getPlateau().getPositionPion(Pion.ROI) == Plateau.CHATEAU_RGE)
                valeur += 1000;
            valeur += jeu.getPlateau().getPositionPion(Pion.GAR_VRT) - Plateau.FONTAINE;
            valeur += jeu.getPlateau().getPositionPion(Pion.GAR_RGE) - Plateau.FONTAINE;
            valeur += jeu.getPlateau().getPositionPion(Pion.SOR) - Plateau.FONTAINE;
            valeur += jeu.getPlateau().getPositionPion(Pion.FOU) - Plateau.FONTAINE;
            valeur += jeu.getPlateau().getPositionPion(Pion.ROI) - Plateau.FONTAINE;
            if (jeu.getPlateau().getPositionPion(Pion.FOU) > jeu.getPlateau().getPositionPion(Pion.ROI))
                valeur += 1;
            else if (jeu.getPlateau().getPositionPion(Pion.FOU) < jeu.getPlateau().getPositionPion(Pion.ROI))
                valeur -= 1;
        }

        if (jeu.getJoueurCourant() != Jeu.JOUEUR_VRT) {
            if (jeu.getPlateau().getPositionPion(Pion.ROI) == Plateau.CHATEAU_VRT)
                valeur += 1000;
            valeur += Plateau.FONTAINE - jeu.getPlateau().getPositionPion(Pion.GAR_VRT);
            valeur += Plateau.FONTAINE - jeu.getPlateau().getPositionPion(Pion.GAR_RGE);
            valeur += Plateau.FONTAINE - jeu.getPlateau().getPositionPion(Pion.FOU);
            valeur += Plateau.FONTAINE - jeu.getPlateau().getPositionPion(Pion.SOR);
            valeur += Plateau.FONTAINE - jeu.getPlateau().getPositionPion(Pion.ROI);
            if (jeu.getPlateau().getPositionPion(Pion.FOU) < jeu.getPlateau().getPositionPion(Pion.ROI))
                valeur += 1;
            else if (jeu.getPlateau().getPositionPion(Pion.FOU) > jeu.getPlateau().getPositionPion(Pion.ROI))
                valeur -= 1;
        }
        return valeur;
    }
}