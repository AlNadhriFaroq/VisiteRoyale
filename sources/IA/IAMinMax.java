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
            evaluerTour(new ArrayList<>());
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
            return evaluerPlateau();
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
            return evaluerPlateau();
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

    private void evaluerTour(List<Coup> listeCoup) {
        List<Coup> lc = jeu.calculerListeCoup();
        for (Coup coup : lc) {
            jeu.jouerCoup(coup);
            listeCoup.add(coup);
            if (coup.getTypeCoup() == Coup.FINIR_TOUR || jeu.getEtatJeu() == Jeu.ETAT_FIN_DE_PARTIE)
                lj.inserer(new ArrayList<>(listeCoup), evaluerPlateau());
            else
                evaluerTour(listeCoup);
            listeCoup.remove(listeCoup.size() - 1);
            coup.desexecuter();
        }
    }

    private int evaluerPlateau() {
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