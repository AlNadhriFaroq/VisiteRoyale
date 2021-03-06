package IA;

import Modele.*;
import Structures.Tas;

import java.util.ArrayList;
import java.util.List;

public class IAStrategieSansPouvoirFou extends IA {
    Tas<List<Coup>> lj;
    int poidsPlateauMax;
    int nombrePas;
    List<Coup> lcf;
    List<Coup> minMaxA;
    List<Coup> minMaxB;
    int tailleLcf;
    int valeur;
    boolean aVuGc;

    public IAStrategieSansPouvoirFou(Jeu jeu) {
        super(jeu);
        lj = new Tas<>(true);
        lcf = new ArrayList<>();
        minMaxA = new ArrayList<>();
        minMaxB = new ArrayList<>();
        poidsPlateauMax = 0;
        nombrePas = 0;
        aVuGc = false;
    }

    @Override
    public Coup calculerCoup() {
        Coup cp = null;
        nombrePas = 0;

        if (tailleLcf == lcf.size()) {
            evaluerTour(new ArrayList<>(), lj);
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
            lj = new Tas<>(true);
            poidsPlateauMax = 0;
            nombrePas = 0;
        }

        return cp;
    }

    private void evaluerTour(List<Coup> listeCoup, Tas<List<Coup>> tas) {
        List<Coup> lc = jeu.calculerListeCoup();
        if(jeu.peutUtiliserPouvoirFou()){
            List<Coup> tmp = new ArrayList<>();
            for(Coup c : lc){
                if(c.getTypeCoup() != Coup.ACTIVER_POUVOIR_FOU)
                    tmp.add(c);
            }
            lc = tmp;
        }
        if (!aVuGc) {
            if (jeu.getMain(jeu.getJoueurCourant()).contientCarte(Carte.GC)) {
                aVuGc = true;
                List<Coup> tmp = new ArrayList<>();
                for (Coup coup : lc) {
                    if (coup.getCarte() != null && coup.getCarte().estDeplacementGarCentre()) {
                        tmp.add(coup);
                        break;
                    }
                }
                for (Coup coup : lc) {
                    if (coup.getCarte() != null && coup.getCarte().estDeplacementGarCentre()) {
                    } else {
                        tmp.add(coup);
                    }
                }
                lc.clear();
                lc.addAll(tmp);
            }
        }
        Coup prec = null;
        for (Coup coup : lc) {
            if (poidsPlateauMax > 500 || nombrePas > 1500000)
                return;
            if (jeu.getEtatJeu() == Jeu.ETAT_CHOIX_CARTE) {
                if (coup.getCarte() != null)
                    if (prec == null || !prec.getCarte().equals(coup.getCarte())) {
                        prec = coup;
                    } else {
                        continue;
                    }
            }
            jeu.jouerCoup(coup);
            listeCoup.add(coup);
            if (coup.getTypeCoup() == Coup.FINIR_TOUR || jeu.getEtatJeu() == Jeu.ETAT_FIN_DE_PARTIE) {
                nombrePas++;
                if (jeu.getEtatJeu() == Jeu.ETAT_FIN_DE_PARTIE) {
                    if (jeu.getPlateau().pionDansChateauRge(Pion.ROI) && jeu.getJoueurCourant() == Jeu.JOUEUR_VRT) {
                        valeur -= 3000;
                    } else if (jeu.getPlateau().pionDansChateauVrt(Pion.ROI) && jeu.getJoueurCourant() == Jeu.JOUEUR_RGE) {
                        valeur -= 3000;
                    } else if (jeu.getPlateau().pionDansChateauRge(Pion.ROI) && jeu.getJoueurCourant() == Jeu.JOUEUR_RGE) {
                        valeur += 3000;
                    } else if (jeu.getPlateau().pionDansChateauVrt(Pion.ROI) && jeu.getJoueurCourant() == Jeu.JOUEUR_VRT) {
                        valeur += 3000;
                    }
                }
                tas.inserer(new ArrayList<>(listeCoup), evaluerPlateau());
                poidsPlateauMax = valeur;
                valeur = 0;
            } else
                evaluerTour(listeCoup, tas);
            listeCoup.remove(listeCoup.size() - 1);
            coup.desexecuter();
        }
    }

    private int evaluerPlateau() {
        if (jeu.getJoueurCourant() != Jeu.JOUEUR_RGE) {
            if(jeu.getEtatJeu() == Jeu.ETAT_FIN_DE_PARTIE && Plateau.FACE_PTT_CRN && jeu.getDefausse().getTaille() == 0){
                if(jeu.getPlateau().getPositionPion(Pion.ROI) > Plateau.FONTAINE){
                    valeur += 3000;
                }
                else if(jeu.getPlateau().getPositionPion(Pion.ROI) < Plateau.FONTAINE){
                    valeur -= 3000;
                }
            }
            if (jeu.getPlateau().getPositionCouronne() >= Plateau.CHATEAU_RGE)
                valeur += 3000;
            else if (jeu.getPlateau().getPositionCouronne() <= Plateau.CHATEAU_VRT)
                valeur -= 3000;
            if (jeu.getPlateau().getPositionPion(Pion.SOR) <= Plateau.CHATEAU_VRT)
                valeur -= 5;
            if (jeu.getPlateau().getPositionPion(Pion.SOR) >= Plateau.CHATEAU_RGE)
                valeur += 5;
            valeur += jeu.getPlateau().getPositionPion(Pion.GAR_VRT) - Plateau.FONTAINE;
            valeur += jeu.getPlateau().getPositionPion(Pion.GAR_RGE) - Plateau.FONTAINE;
            valeur += jeu.getPlateau().getPositionPion(Pion.SOR) - Plateau.FONTAINE;
            valeur += jeu.getPlateau().getPositionPion(Pion.FOU) - Plateau.FONTAINE;
            valeur += (jeu.getPlateau().getPositionPion(Pion.ROI) - Plateau.FONTAINE) * 2;
            if (jeu.getPlateau().getPositionPion(Pion.FOU) > jeu.getPlateau().getPositionPion(Pion.ROI))
                valeur += 1;
            else if (jeu.getPlateau().getPositionPion(Pion.FOU) < jeu.getPlateau().getPositionPion(Pion.ROI))
                valeur -= 1;
            if (jeu.getPlateau().getPositionPion(Pion.GAR_RGE) >= Plateau.CHATEAU_RGE)
                valeur += 2;
            if (jeu.getPlateau().getPositionPion(Pion.FOU) >= Plateau.CHATEAU_RGE)
                valeur += 2;
            if (jeu.getPlateau().getPositionPion(Pion.GAR_VRT) <= Plateau.CHATEAU_VRT)
                valeur -= 2;
            if (jeu.getPlateau().getPositionPion(Pion.FOU) <= Plateau.CHATEAU_VRT)
                valeur += 2;
        }

        if (jeu.getJoueurCourant() != Jeu.JOUEUR_VRT) {
            if(jeu.getEtatJeu() == Jeu.ETAT_FIN_DE_PARTIE && Plateau.FACE_PTT_CRN && jeu.getDefausse().getTaille() == 0){
                if(jeu.getPlateau().getPositionPion(Pion.ROI) < Plateau.FONTAINE){
                    valeur += 3000;
                }
                else if(jeu.getPlateau().getPositionPion(Pion.ROI) > Plateau.FONTAINE){
                    valeur -= 3000;
                }
            }
            if (jeu.getPlateau().getPositionCouronne() <= Plateau.CHATEAU_VRT)
                valeur += 3000;
            else if (jeu.getPlateau().getPositionCouronne() >= Plateau.CHATEAU_RGE)
                valeur -= 3000;
            if (jeu.getPlateau().getPositionCouronne() <= Plateau.CHATEAU_VRT)
                valeur += 1000;
            if (jeu.getPlateau().getPositionPion(Pion.SOR) >= Plateau.CHATEAU_RGE)
                valeur -= 5;
            if (jeu.getPlateau().getPositionPion(Pion.SOR) <= Plateau.CHATEAU_VRT)
                valeur += 5;
            valeur += Plateau.FONTAINE - jeu.getPlateau().getPositionPion(Pion.GAR_VRT);
            valeur += Plateau.FONTAINE - jeu.getPlateau().getPositionPion(Pion.GAR_RGE);
            valeur += Plateau.FONTAINE - jeu.getPlateau().getPositionPion(Pion.FOU);
            valeur += Plateau.FONTAINE - jeu.getPlateau().getPositionPion(Pion.SOR);
            valeur += (Plateau.FONTAINE - jeu.getPlateau().getPositionPion(Pion.ROI)) * 2;
            if (jeu.getPlateau().getPositionPion(Pion.FOU) < jeu.getPlateau().getPositionPion(Pion.ROI))
                valeur += 1;
            else if (jeu.getPlateau().getPositionPion(Pion.FOU) > jeu.getPlateau().getPositionPion(Pion.ROI))
                valeur -= 1;
            if (jeu.getPlateau().getPositionPion(Pion.GAR_VRT) <= Plateau.CHATEAU_VRT)
                valeur += 2;
            if (jeu.getPlateau().getPositionPion(Pion.FOU) <= Plateau.CHATEAU_VRT)
                valeur += 2;
            if (jeu.getPlateau().getPositionPion(Pion.GAR_RGE) >= Plateau.CHATEAU_RGE)
                valeur -= 2;
            if (jeu.getPlateau().getPositionPion(Pion.FOU) >= Plateau.CHATEAU_RGE)
                valeur -= 2;
        }
        return valeur;
    }
}