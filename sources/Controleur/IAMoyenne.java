package Controleur;

import Modele.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IAMoyenne extends IA{
    Random r;
    int roi = 1;
    int gardeVert = 2;
    int gardeRouge = 4;
    int sorcier = 8;
    int fou = 16;
    int joueurCourant;
    int pionsSurFontaine;
    int pionsChateauAdverse;
    int pionsDucheAdverse;
    int pionsChateau;
    int pionsDuche;

    IAMoyenne(Jeu jeu) {
        super(jeu);
        r = new Random();
    }

    @Override
    Coup calculerCoup() {
        pionsSurFontaine = pionsSurFontaine();
        pionsChateauAdverse = pionDansChateau(joueurCourant);
        pionsDucheAdverse =  pionsDansDuche(joueurCourant);
        int joueur = 1 - joueurCourant;
        pionsChateau = pionDansChateau(joueur);
        pionsDuche =  pionsDansDuche(joueur);
        joueurCourant = jeu.getJoueurCourant();
        Coup coup = null;
        List<Coup> lc = jeu.calculerListeCoup();
        if(lc.size() == 1){
            return lc.get(0);
        }
        switch (jeu.getEtatJeu()){
            case Jeu.ETAT_CHOIX_CARTE:
                coup = choisirCarte(lc);
                break;
            case Jeu.ETAT_CHOIX_PION:
                coup = choisirPion(lc);
                break;
            case Jeu.ETAT_CHOIX_DIRECTION:
                coup = choisirDirection(lc);
                break;
            case Jeu.ETAT_FIN_DE_PARTIE:
                return lc.get(0);
            default:
                throw new RuntimeException("Controleur.IAMoyenne.calculerCoup() : Etat du jeu invalide.");

        }
        return coup;
    }

    private Coup choisirCarte(List<Coup> lc){
        Coup coup = null;
        if(pionsChateauAdverse == 0){ //pas de pions dans le chateau adverse
           if(pionsDucheAdverse == 0){ //pas de pions dans le duche adverse
                if(pionsChateau == 0){ // pas de pions dans notre chateau
                    if(pionsSurFontaine == 0){ // aucun pion dans la fontaine, tout dans notre duche
                        return choisirCoupAleaCarte(lc);
                    }
                    else{//pion sur fontaine
                        if((pionsSurFontaine & sorcier) == 1){
                            if(jeu.getMain(joueurCourant).getNombreTypeCarte(Type.SOR) >= 1){
                                coup = choisirCarteFontaine(lc, Pion.SOR);
                            }
                        }
                        else if((pionsSurFontaine & fou) == 1){
                            if(jeu.getMain(joueurCourant).getNombreTypeCarte(Type.FOU) >= 1){
                                coup = choisirCarteFontaine(lc, Pion.FOU);
                            }
                        }
                        else if((pionsSurFontaine & gardeRouge) == 1 && joueurCourant == Jeu.JOUEUR_RGE){
                            if(jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) >= 1 && jeu.getPlateau().getPositionPion(Pion.GAR_RGE) + 1 <  jeu.getPlateau().getPositionPion(Pion.ROI)){
                                coup = choisirCarteFontaine(lc, Pion.GAR_RGE);
                            }
                        }
                        else if((pionsSurFontaine & gardeVert) == 1 && joueurCourant == Jeu.JOUEUR_VRT){
                            if(jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) >= 1 && jeu.getPlateau().getPositionPion(Pion.GAR_VRT) - 1 >  jeu.getPlateau().getPositionPion(Pion.ROI)){
                                coup = choisirCarteFontaine(lc, Pion.GAR_VRT);
                            }
                        }
                        else{
                            return choisirCoupAleaCarte(lc);
                        }
                    }
                }
                else{
                    if((pionsChateau & sorcier) == 1 && (pionsChateau & gardeVert) == 0 && joueurCourant == Jeu.JOUEUR_VRT){
                        if(jeu.peutUtiliserPouvoirSorcier()){
                            coup = choisirPouvoirSorcier(lc);
                        }
                    }
                    else if((pionsChateau & sorcier) == 1 && (pionsChateau & gardeRouge) == 0 && joueurCourant == Jeu.JOUEUR_RGE){
                        if(jeu.peutUtiliserPouvoirSorcier()){
                            coup = choisirPouvoirSorcier(lc);
                        }
                    }
                    else if ((pionsChateau & gardeVert) == 1 && joueurCourant == Jeu.JOUEUR_VRT) {
                        if(jeu.getMain(joueurCourant).getNombreTypeCarte(Type.ROI) >= 1 && jeu.getPlateau().getPositionPion(Pion.ROI) - 1 > jeu.getPlateau().getPositionPion(Pion.GAR_VRT)){
                            coup = choisirCarte(lc, Type.ROI);
                        }
                    }
                    else if ((pionsChateau & gardeRouge) == 1 && joueurCourant == Jeu.JOUEUR_RGE) {
                        if(jeu.getMain(joueurCourant).getNombreTypeCarte(Type.ROI) >= 1 && jeu.getPlateau().getPositionPion(Pion.ROI) + 1 < jeu.getPlateau().getPositionPion(Pion.GAR_VRT)){
                            coup = choisirCarte(lc, Type.ROI);
                        }
                    }
                    else{
                        return choisirCoupAleaCarte(lc);
                    }
                }
           }
           else{
               if((pionsDucheAdverse & sorcier) == 1){
                   if(jeu.getMain(joueurCourant).getNombreTypeCarte(Type.SOR) >= 1) {
                       coup = choisirCarte(lc, Type.SOR);
                   }
               }
               else if((pionsDucheAdverse & fou) == 1){
                   if(jeu.getMain(joueurCourant).getNombreTypeCarte(Type.FOU) >= 1) {
                       coup = choisirCarte(lc, Type.FOU);
                   }
               }
               else if((pionsDucheAdverse & roi) == 1){
                   if(jeu.getMain(joueurCourant).getNombreTypeCarte(Type.ROI) >= 1) {
                       coup = choisirCarte(lc, Type.ROI);
                   }
               }
               else if((pionsDucheAdverse & gardeRouge) == 1 && joueurCourant == Jeu.JOUEUR_RGE){
                   if(jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) >= 1) {
                       coup = choisirCarte(lc, Type.GAR);
                   }
               }
               else if((pionsDucheAdverse & gardeVert) == 1 && joueurCourant == Jeu.JOUEUR_VRT){
                   if(jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) >= 1) {
                       coup = choisirCarte(lc, Type.GAR);
                   }
               }
               else if((pionsDucheAdverse & gardeVert) == 1 && joueurCourant == Jeu.JOUEUR_RGE){
                   if(jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) >= 1) {
                       coup = choisirCarte(lc, Type.GAR);
                   }
               }
               else if((pionsDucheAdverse & gardeRouge) == 1 && joueurCourant == Jeu.JOUEUR_VRT){
                   if(jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) >= 1) {
                       coup = choisirCarte(lc, Type.GAR);
                   }
               }
           }
        }
        else{
            if((pionsChateauAdverse & sorcier) == 1){
                if(jeu.getMain(joueurCourant).getNombreTypeCarte(Type.SOR) >= 1) {
                    coup = choisirCarte(lc, Type.SOR);
                }
            }
            else if((pionsChateauAdverse & fou) == 1){
                if(jeu.getMain(joueurCourant).getNombreTypeCarte(Type.FOU) >= 1) {
                    coup = choisirCarte(lc, Type.FOU);
                }
            }
            else if((pionsChateauAdverse & gardeRouge) == 1 && joueurCourant == Jeu.JOUEUR_RGE){
                if(jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) >= 1) {
                    coup = choisirCarte(lc, Type.GAR);
                }
            }
            else if((pionsChateauAdverse & gardeVert) == 1 && joueurCourant == Jeu.JOUEUR_VRT){
                if(jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) >= 1) {
                    coup = choisirCarte(lc, Type.GAR);
                }
            }

        }
        if(coup == null){
            return choisirCoupAleaCarte(lc);
        }
        else{
            return coup;
        }
    }

    //on regarde s'il y a des pieces dans le chateau adverse
    private int pionDansChateau(int joueur){
        int pions = 0;
        if(joueur != Jeu.JOUEUR_VRT){
            if(jeu.getPlateau().pionDansChateauVrt(Pion.GAR_VRT)){
                pions |= gardeVert;
            }
            if(jeu.getPlateau().pionDansChateauVrt(Pion.SOR)){
                pions |= sorcier;
            }
            if(jeu.getPlateau().pionDansChateauVrt(Pion.FOU)){
                pions |= fou;
            }
        }
        else{
            if(jeu.getPlateau().pionDansChateauRge(Pion.GAR_VRT)){
                pions |= gardeRouge;
            }
            if(jeu.getPlateau().pionDansChateauRge(Pion.SOR)){
                pions |= sorcier;
            }
            if(jeu.getPlateau().pionDansChateauRge(Pion.FOU)){
                pions |= fou;
            }

        }
        return pions;
    }

    private int pionsDansDuche(int joueur){
        int pions = 0;
        if(joueur != Jeu.JOUEUR_VRT){
            if(jeu.getPlateau().pionDansDucheVrt(Pion.GAR_VRT)){
                pions |= gardeVert;
            }
            if(jeu.getPlateau().pionDansDucheVrt(Pion.SOR)){
                pions |= sorcier;
            }
            if(jeu.getPlateau().pionDansDucheVrt(Pion.FOU)){
                pions |= fou;
            }
            if(jeu.getPlateau().pionDansDucheVrt(Pion.ROI)){
                pions |= roi;
            }
        }
        else{
            if(jeu.getPlateau().pionDansDucheRge(Pion.GAR_VRT)){
                pions |= gardeRouge;
            }
            if(jeu.getPlateau().pionDansDucheRge(Pion.SOR)){
                pions |= sorcier;
            }
            if(jeu.getPlateau().pionDansDucheRge(Pion.FOU)){
                pions |= fou;
            }
            if(jeu.getPlateau().pionDansDucheRge(Pion.ROI)){
                pions |= roi;
            }
        }
        return pions;
    }

    private int pionsSurFontaine(){
        int pions = 0;
        if(jeu.getPlateau().pionDansFontaine(Pion.ROI)){
            pions |= roi;
        }
        if(jeu.getPlateau().pionDansFontaine(Pion.GAR_RGE)){
            pions |= gardeRouge;
        }
        if(jeu.getPlateau().pionDansFontaine(Pion.GAR_VRT)){
            pions |= gardeVert;
        }
        if(jeu.getPlateau().pionDansFontaine(Pion.SOR)){
            pions |= sorcier;
        }
        if(jeu.getPlateau().pionDansFontaine(Pion.FOU)){
            pions |= fou;
        }
        return pions;
    }

    private Coup choisirCoupAlea(List<Coup> lc){
        List<Coup> listeDirectionOk = new ArrayList<>();
        for(Coup c : lc){
            if (joueurCourant == Jeu.JOUEUR_VRT) {
                if(c.getDirection() == Plateau.DIRECTION_VRT){
                    listeDirectionOk.add(c);
                }
            }
            else{
                if(c.getDirection() == Plateau.DIRECTION_RGE){
                    listeDirectionOk.add(c);
                }
            }
        }
        if(!listeDirectionOk.isEmpty()){
            return listeDirectionOk.get(r.nextInt(listeDirectionOk.size()));
        }
        else{
            return lc.get(r.nextInt(lc.size()));
        }
    }

    private Coup choisirCoupAleaCarte(List<Coup> lc){
        return lc.get(r.nextInt(lc.size()));
    }

    private Coup choisirCarteFontaine(List<Coup> lc, Pion pion){
        Coup coup = null;
        int nbDeplacement = 0;
        for(Coup c : lc){
            if(c.getCarte().getType().equals(pion.getType())){
                if(nbDeplacement < c.getCarte().getDeplacement()){
                    nbDeplacement = c.getCarte().getDeplacement();
                    coup = c;
                }
            }
        }
        return coup;
    }


    private Coup choisirPouvoirSorcier(List<Coup> lc){
        for (Coup c : lc){
            if(c.getTypeCoup() == Coup.ACTIVER_POUVOIR_SOR){
                return c;
            }
        }
        return null;
    }

    private Coup choisirCarte(List<Coup> lc, Type type){
        for (Coup c : lc){
            if(c.getCarte().getType().equals(type)){
                return c;
            }
        }
        return null;
    }

    private Coup choisirPion(List<Coup> lc){
        Coup coup = null;
        int posRoi = jeu.getPlateau().getPositionPion(Pion.ROI);
        int posGardeVert = jeu.getPlateau().getPositionPion(Pion.GAR_VRT);
        int posGardeRouge = jeu.getPlateau().getPositionPion(Pion.GAR_RGE);
        int posSorcier = jeu.getPlateau().getPositionPion(Pion.SOR);
        if(jeu.getActivationPouvoirSor()){
            if(joueurCourant == Jeu.JOUEUR_RGE){
                if(posRoi < posSorcier){
                    for(Coup c : lc){
                        if(c.getPion().equals(Pion.ROI)){
                            coup = c;
                        }
                    }
                }
                else{
                    for(Coup c : lc){
                        if(c.getPion().equals(Pion.GAR_VRT)){
                            coup = c;
                        }
                    }
                }
            }
            else{
                if(posRoi > posSorcier){
                    for(Coup c : lc){
                        if(c.getPion().equals(Pion.ROI)){
                            coup = c;
                        }
                    }
                }
                else{
                    for(Coup c : lc){
                        if(c.getPion().equals(Pion.GAR_RGE)){
                            coup = c;
                        }
                    }
                }
            }
        }
        else if(jeu.getActivationPouvoirFou()){
            coup = choisirCoupAlea(lc);
        }
        else{
            if(joueurCourant == Jeu.JOUEUR_RGE){
                if((pionsChateauAdverse & gardeVert) == 1){
                    for(Coup c : lc){
                        if(c.getPion().equals(Pion.GAR_VRT)){
                            coup = c;
                        }
                    }
                }
                else if((pionsDucheAdverse & gardeRouge) == 1 || (pionsSurFontaine & gardeRouge) == 1){
                    for(Coup c : lc){
                        if(c.getPion().equals(Pion.GAR_RGE)){
                            coup = c;
                        }
                    }
                }
                else if(posRoi > Plateau.FONTAINE && ((pionsDucheAdverse & gardeVert) == 1 || (pionsSurFontaine & gardeVert) == 1)){
                    for(Coup c : lc){
                        if(c.getPion().equals(Pion.GAR_VRT)){
                            coup = c;
                        }
                    }
                }
                else if((pionsDuche & gardeRouge) == 1){
                    for(Coup c : lc){
                        if(c.getPion().equals(Pion.GAR_RGE)){
                            coup = c;
                        }
                    }
                }

            }
        }
        if(coup != null){
            return coup;
        }
        return choisirCoupAlea(lc);
    }

    private Coup choisirDirection(List<Coup> lc){
        return choisirCoupAlea(lc);
    }
}
