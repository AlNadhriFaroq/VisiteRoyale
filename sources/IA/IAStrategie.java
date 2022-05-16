package IA;

import Modele.*;

import java.util.*;

public class IAStrategie extends IA {
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
    int posRoi;
    int posGardeVert;
    int posGardeRouge;
    int posSorcier;
    int posFou;

    public IAStrategie(Jeu jeu) {
        super(jeu);
        r = new Random();
    }

    @Override
    public Coup calculerCoup() {
        pionsSurFontaine = pionsSurFontaine();
        pionsChateauAdverse = pionDansChateau(joueurCourant);
        pionsDucheAdverse = pionsDansDuche(joueurCourant);
        int joueur = 1 - joueurCourant;
        pionsChateau = pionDansChateau(joueur);
        pionsDuche = pionsDansDuche(joueur);
        joueurCourant = jeu.getJoueurCourant();
        posRoi = jeu.getPlateau().getPositionPion(Pion.ROI);
        posGardeVert = jeu.getPlateau().getPositionPion(Pion.GAR_VRT);
        posGardeRouge = jeu.getPlateau().getPositionPion(Pion.GAR_RGE);
        posSorcier = jeu.getPlateau().getPositionPion(Pion.SOR);
        posFou = jeu.getPlateau().getPositionPion(Pion.FOU);
        Coup coup;
        List<Coup> lc = jeu.calculerListeCoup();
        if (lc.size() == 1) {
            return lc.get(0);
        }
        switch (jeu.getEtatJeu()) {
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
                throw new RuntimeException("IA.IAMoyenne.calculerCoup() : Etat du jeu invalide.");

        }
        System.out.println(coup.toString());
        return coup;
    }

    private Coup choisirCarte(List<Coup> lc) {
        Coup coup = null;
        //coup = carteEnFonctionNombreEtDistance();

        if(jeu.getTypeCourant().equals(Type.IND)){
            coup = choixPouvoirSorcier(lc);
        }

        if(coup != null){
            return coup;
        }

        if(jeu.getTypeCourant().equals(Type.IND)){
            Type type = carteEnFonctionNombre();
            if(type != null){
                coup = choisirCarte(lc, type);
            }
        }

        if(coup != null){
            return coup;
        }

        if(!jeu.getTypeCourant().equals(Type.IND)){
            coup = choisirCarte(lc, jeu.getTypeCourant());
        }

        if(coup != null){
            if(jeu.getTypeCourant().equals(Type.FOU) && coup.getCarte().estDeplacementFouCentre()){
                if(joueurCourant == Jeu.JOUEUR_RGE && posFou < Plateau.FONTAINE){
                    return coup;
                }
                if(joueurCourant == Jeu.JOUEUR_VRT && posFou > Plateau.FONTAINE){
                    return coup;
                }
                else{ // on supprime FM des possibilites
                    lc.remove(coup);
                    for(Coup c : lc){
                        if (c.getCarte() != null && c.getCarte().estDeplacementFouCentre()){
                            lc.remove(c);
                        }
                    }
                    coup = null;
                }
            }
            else if(jeu.getTypeCourant().equals(Type.GAR) && coup.getCarte().estDeplacementGarCentre()){
                System.out.println("veut faire garde centre");
                if(joueurCourant == Jeu.JOUEUR_RGE && posGardeVert == (posRoi - 1)){
                    lc.remove(coup);
                    for(Coup c : lc){
                        if (c.getCarte() != null && c.getCarte().estDeplacementGarCentre()){
                            lc.remove(c);
                        }
                    }
                    coup = null;
                }
                else if(joueurCourant == Jeu.JOUEUR_VRT && posGardeRouge == (posRoi + 1)){
                    lc.remove(coup);
                    for(Coup c : lc){
                        if (c.getCarte() != null && c.getCarte().estDeplacementGarCentre()){
                            lc.remove(c);
                        }
                    }
                    coup = null;
                }
            }
        }

        if (pionsChateauAdverse == 0) { //pas de pions dans le chateau adverse
            System.out.println("Aucun pion dans le chateau adverse");
            if (pionsDucheAdverse == 0) { //pas de pions dans le duche adverse
                System.out.println("Aucun pion dans duche adverse");
                if (pionsChateau == 0) { // pas de pions dans notre chateau
                    System.out.println("Aucun pion dans notre chateau");
                    if (pionsSurFontaine == 0) { // aucun pion dans la fontaine, tout dans notre duche
                        System.out.println("Aucun pion dans la fontaine");
                        System.out.println("a fait un coup aleatoire 105");
                        return choisirCoupAleaCarte(lc);
                    } else {//pion sur fontaine
                        System.out.println("Pion dans la fontaine");
                        if ((pionsSurFontaine & sorcier) == sorcier && (jeu.getTypeCourant().equals(Type.SOR) || jeu.getTypeCourant().equals(Type.IND))) {
                            if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.SOR) >= 1) {
                                coup = choisirCarteFontaine(lc, Pion.SOR);
                            }
                        } else if ((pionsSurFontaine & fou) == fou && (jeu.getTypeCourant().equals(Type.FOU) || jeu.getTypeCourant().equals(Type.IND))) {
                            if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.FOU) >= 1) {
                                coup = choisirCarteFontaine(lc, Pion.FOU);
                            }
                        } else if ((pionsSurFontaine & gardeRouge) == gardeRouge && joueurCourant == Jeu.JOUEUR_RGE && (jeu.getTypeCourant().equals(Type.GAR) || jeu.getTypeCourant().equals(Type.IND))) {
                            if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) >= 1 && jeu.getPlateau().getPositionPion(Pion.GAR_RGE) + 1 < jeu.getPlateau().getPositionPion(Pion.ROI)) {
                                coup = choisirCarteFontaine(lc, Pion.GAR_RGE);
                            }
                        } else if ((pionsSurFontaine & gardeVert) == gardeVert && joueurCourant == Jeu.JOUEUR_VRT && (jeu.getTypeCourant().equals(Type.GAR) || jeu.getTypeCourant().equals(Type.IND))) {
                            if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) >= 1 && jeu.getPlateau().getPositionPion(Pion.GAR_VRT) - 1 > jeu.getPlateau().getPositionPion(Pion.ROI)) {
                                coup = choisirCarteFontaine(lc, Pion.GAR_VRT);
                            }
                        } else {
                            System.out.println("a fait un coup aleatoire 126");
                            return choisirCoupAleaCarte(lc);
                        }
                    }
                } else {
                    System.out.println("Pion dans notre chateau");
                    if ((pionsChateau & gardeVert) == gardeVert && joueurCourant == Jeu.JOUEUR_VRT && (jeu.getTypeCourant().equals(Type.GAR) || jeu.getTypeCourant().equals(Type.IND))) {
                        if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.ROI) >= 1 && jeu.getPlateau().getPositionPion(Pion.ROI) - 1 > jeu.getPlateau().getPositionPion(Pion.GAR_VRT)) {
                            coup = choisirCarte(lc, Type.ROI);
                        }
                    } else if ((pionsChateau & gardeRouge) == gardeRouge && joueurCourant == Jeu.JOUEUR_RGE && (jeu.getTypeCourant().equals(Type.GAR) || jeu.getTypeCourant().equals(Type.IND))) {
                        if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.ROI) >= 1 && jeu.getPlateau().getPositionPion(Pion.ROI) + 1 < jeu.getPlateau().getPositionPion(Pion.GAR_VRT)) {
                            coup = choisirCarte(lc, Type.ROI);
                        }
                    } else {
                        System.out.println("a fait un coup aleatoire 141 ");
                        return choisirCoupAleaCarte(lc);
                    }
                }
            } else {
                System.out.println("Pion dans duche adverse");
                if ((pionsDucheAdverse & sorcier) == sorcier && (jeu.getTypeCourant().equals(Type.SOR) || jeu.getTypeCourant().equals(Type.IND))) {
                    if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.SOR) >= 1) {
                        coup = choisirCarte(lc, Type.SOR);
                    }
                } else if ((pionsDucheAdverse & fou) == fou && (jeu.getTypeCourant().equals(Type.FOU) || jeu.getTypeCourant().equals(Type.IND))) {
                    if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.FOU) >= 1) {
                        if(!jeu.getMain(joueurCourant).contientCarte(Carte.FM)){
                            System.out.println("ne contient pas carte FM en main");
                            coup = choisirCarte(lc, Type.FOU);
                        }
                        else {
                            System.out.println("carte FM en main");
                            for(Coup c : lc){
                                if(c.getCarte().estDeplacementFouCentre()){
                                    return c;
                                }
                            }
                        }
                    }
                } else if ((pionsDucheAdverse & roi) == roi && (jeu.getTypeCourant().equals(Type.ROI) || jeu.getTypeCourant().equals(Type.IND))) {
                    if(joueurCourant == Jeu.JOUEUR_RGE && posGardeRouge > posRoi + 1 || joueurCourant == Jeu.JOUEUR_VRT && posGardeVert < posRoi - 1){
                        if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.ROI) >= 1) {
                            coup = choisirCarte(lc, Type.ROI);
                        }
                    }
                } else if ((pionsDucheAdverse & gardeRouge) == gardeRouge && joueurCourant == Jeu.JOUEUR_RGE && (jeu.getTypeCourant().equals(Type.GAR) || jeu.getTypeCourant().equals(Type.IND))) {
                    if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) >= 1) {
                        coup = choisirCarte(lc, Type.GAR);
                    }
                } else if ((pionsDucheAdverse & gardeVert) == gardeVert && joueurCourant == Jeu.JOUEUR_VRT && (jeu.getTypeCourant().equals(Type.GAR) || jeu.getTypeCourant().equals(Type.IND))) {
                    if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) >= 1) {
                        coup = choisirCarte(lc, Type.GAR);
                    }
                } else if ((pionsDucheAdverse & gardeVert) == gardeVert && joueurCourant == Jeu.JOUEUR_RGE && (jeu.getTypeCourant().equals(Type.GAR) || jeu.getTypeCourant().equals(Type.IND))) {
                    if(jeu.getMain(joueurCourant).contientCarte(Carte.GC) && (posGardeVert + 4) <= posRoi){
                        System.out.println("Choix garde centre duche");
                        for(Coup c : lc){
                            if(c.getCarte() != null && c.getCarte().estDeplacementGarCentre()){
                                return c;
                            }
                        }
                    }
                    if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) >= 1) {
                        coup = choisirCarte(lc, Type.GAR);
                    }
                    if((pionsDucheAdverse & roi) == roi){
                        if(coup != null && coup.getCarte().estDeplacementGarCentre() && posRoi < Plateau.FONTAINE - 1){
                            lc.remove(coup);
                            coup = choisirCarte(lc, Type.GAR);
                        }
                    }
                } else if ((pionsDucheAdverse & gardeRouge) == gardeRouge && joueurCourant == Jeu.JOUEUR_VRT && (jeu.getTypeCourant().equals(Type.GAR) || jeu.getTypeCourant().equals(Type.IND))) {
                    if(jeu.getMain(joueurCourant).contientCarte(Carte.GC) && (posGardeRouge - 4) >= posRoi){
                        System.out.println("Choix garde centre duche");
                        for(Coup c : lc){
                            if(c.getCarte() != null && c.getCarte().estDeplacementGarCentre()){
                                return c;
                            }
                        }
                    }
                    if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) >= 1) {
                        coup = choisirCarte(lc, Type.GAR);
                    }
                    if((pionsDucheAdverse & roi) == roi){
                        if(coup != null && coup.getCarte().estDeplacementGarCentre() && posRoi > Plateau.FONTAINE + 1){
                            lc.remove(coup);
                            coup = choisirCarte(lc, Type.GAR);
                        }
                    }
                }
            }
        } else {
            System.out.println("Pion dans le chateau adverse");
            if ((pionsChateauAdverse & sorcier) == sorcier && (jeu.getTypeCourant().equals(Type.SOR) || jeu.getTypeCourant().equals(Type.IND))) {
                if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.SOR) >= 1) {
                    coup = choisirCarte(lc, Type.SOR);
                }
            } else if ((pionsChateauAdverse & fou) == fou && (jeu.getTypeCourant().equals(Type.FOU) || jeu.getTypeCourant().equals(Type.IND))) {
                if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.FOU) >= 1) {
                    if(!jeu.getMain(joueurCourant).contientCarte(Carte.FM)){
                        coup = choisirCarte(lc, Type.FOU);
                    }
                    else {
                        for(Coup c : lc){
                            if(c.getCarte().estDeplacementFouCentre()){
                                return c;
                            }
                        }
                    }
                }
            } else if ((pionsChateauAdverse & gardeVert) == gardeVert && joueurCourant == Jeu.JOUEUR_RGE && (jeu.getTypeCourant().equals(Type.GAR) || jeu.getTypeCourant().equals(Type.IND))) {
                if(jeu.getMain(joueurCourant).contientCarte(Carte.GC) && posGardeRouge < Plateau.CHATEAU_RGE){
                    System.out.println("Choix garde centre");
                    for(Coup c : lc){
                        if(c.getCarte() != null && c.getCarte().estDeplacementGarCentre()){
                            return c;
                        }
                    }
                }
                else if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) >= 1) {
                    coup = choisirCarte(lc, Type.GAR);
                }
            } else if ((pionsChateauAdverse & gardeRouge) == gardeRouge && joueurCourant == Jeu.JOUEUR_VRT && (jeu.getTypeCourant().equals(Type.GAR) || jeu.getTypeCourant().equals(Type.IND))) {
                if(jeu.getMain(joueurCourant).contientCarte(Carte.GC) && posGardeVert > Plateau.CHATEAU_VRT){
                    System.out.println("Choix garde centre");
                    for(Coup c : lc){
                        if(c.getCarte() != null && c.getCarte().estDeplacementGarCentre()){
                            return c;
                        }
                    }
                }
                else if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) >= 1) {
                    coup = choisirCarte(lc, Type.GAR);
                }
            }
        }

        if(coup != null && coup.getCarte() != null && !coup.getCarte().estDeplacementFouCentre()){
            if(coup.getCarte().getType().equals(Type.SOR)){
                coup = verifNonDebordement(coup.getCarte().getType(), posSorcier, lc, coup);
            }
            else if(coup.getCarte().getType().equals(Type.FOU)){
                coup = verifNonDebordement(coup.getCarte().getType(), posFou, lc, coup);
            }
        }

        if (coup == null) {
            System.out.println("a fait un coup aleatoire 233");
            return choisirCoupAleaCarte(lc);
        } else {
            return coup;
        }
    }
    //on regarde s'il y a des pieces dans le chateau adverse
    private int pionDansChateau(int joueur) {
        int pions = 0;
        if (joueur != Jeu.JOUEUR_VRT) {
            if (jeu.getPlateau().pionDansChateauVrt(Pion.GAR_VRT)) {
                pions |= gardeVert;
            }
            if (jeu.getPlateau().pionDansChateauVrt(Pion.SOR)) {
                pions |= sorcier;
            }
            if (jeu.getPlateau().pionDansChateauVrt(Pion.FOU)) {
                pions |= fou;
            }
        } else {
            if (jeu.getPlateau().pionDansChateauRge(Pion.GAR_RGE)) {
                pions |= gardeRouge;
            }
            if (jeu.getPlateau().pionDansChateauRge(Pion.SOR)) {
                pions |= sorcier;
            }
            if (jeu.getPlateau().pionDansChateauRge(Pion.FOU)) {
                pions |= fou;
            }

        }
        return pions;
    }

    private int pionsDansDuche(int joueur) {
        int pions = 0;
        if (joueur != Jeu.JOUEUR_VRT) {
            if (jeu.getPlateau().pionDansDucheVrt(Pion.GAR_VRT)) {
                pions |= gardeVert;
            }
            if (jeu.getPlateau().pionDansDucheVrt(Pion.SOR)) {
                pions |= sorcier;
            }
            if (jeu.getPlateau().pionDansDucheVrt(Pion.FOU)) {
                pions |= fou;
            }
            if (jeu.getPlateau().pionDansDucheVrt(Pion.ROI)) {
                pions |= roi;
            }
            if (jeu.getPlateau().pionDansDucheVrt(Pion.GAR_RGE)) {
                pions |= gardeRouge;
            }
        } else {
            if (jeu.getPlateau().pionDansDucheRge(Pion.GAR_VRT)) {
                pions |= gardeRouge;
            }
            if (jeu.getPlateau().pionDansDucheRge(Pion.SOR)) {
                pions |= sorcier;
            }
            if (jeu.getPlateau().pionDansDucheRge(Pion.FOU)) {
                pions |= fou;
            }
            if (jeu.getPlateau().pionDansDucheRge(Pion.ROI)) {
                pions |= roi;
            }
            if (jeu.getPlateau().pionDansDucheRge(Pion.GAR_RGE)) {
                pions |= gardeRouge;
            }
        }
        return pions;
    }

    private int pionsSurFontaine() {
        int pions = 0;
        if (jeu.getPlateau().pionDansFontaine(Pion.ROI)) {
            pions |= roi;
        }
        if (jeu.getPlateau().pionDansFontaine(Pion.GAR_RGE)) {
            pions |= gardeRouge;
        }
        if (jeu.getPlateau().pionDansFontaine(Pion.GAR_VRT)) {
            pions |= gardeVert;
        }
        if (jeu.getPlateau().pionDansFontaine(Pion.SOR)) {
            pions |= sorcier;
        }
        if (jeu.getPlateau().pionDansFontaine(Pion.FOU)) {
            pions |= fou;
        }
        return pions;
    }

    private Coup choisirCoupAlea(List<Coup> lc) {
        List<Coup> listeDirectionOk = new ArrayList<>();
        for (Coup c : lc) {
            if (joueurCourant == Jeu.JOUEUR_VRT) {
                if (c.getDirection() == Plateau.DIRECTION_VRT) {
                    listeDirectionOk.add(c);
                }
            } else {
                if (c.getDirection() == Plateau.DIRECTION_RGE) {
                    listeDirectionOk.add(c);
                }
            }
        }
        if (!listeDirectionOk.isEmpty()) {
            return listeDirectionOk.get(r.nextInt(listeDirectionOk.size()));
        } else {
            return lc.get(r.nextInt(lc.size()));
        }
    }

    private Coup choisirCoupAleaCarte(List<Coup> lc) {
        return lc.get(r.nextInt(lc.size()));
    }

    private Coup choisirCarteFontaine(List<Coup> lc, Pion pion) {
        Coup coup = null;
        int nbDeplacement = 0;
        for (Coup c : lc) {
            if (c.getCarte() != null && c.getCarte().getType().equals(pion.getType())) {
                if (nbDeplacement < c.getCarte().getDeplacement()) {
                    nbDeplacement = c.getCarte().getDeplacement();
                    coup = c;
                }
            }
        }
        return coup;
    }

    private Coup choixPouvoirSorcier(List<Coup> lc){
        if(joueurCourant == Jeu.JOUEUR_RGE){
            if((posSorcier > (posRoi + 3) && posSorcier < posGardeRouge || posSorcier > posGardeRouge + 3) || (posSorcier < posRoi && posSorcier > (posGardeVert + 3))){
                for(Coup c : lc){
                    if(c.getTypeCoup() == Coup.ACTIVER_POUVOIR_SOR){
                        return c;
                    }
                }
            }
        }
        else{
            if((posSorcier < (posRoi - 3) && posSorcier > posGardeVert || posSorcier < posGardeVert - 3) || (posSorcier > posRoi && posSorcier < (posGardeRouge - 3))){
                for(Coup c : lc){
                    if(c.getTypeCoup() == Coup.ACTIVER_POUVOIR_SOR){
                        return c;
                    }
                }
            }
        }
        return null;
    }

    private Coup choisirCarte(List<Coup> lc, Type type) {
        for (Coup c : lc) {
            if (c.getCarte() != null && c.getCarte().getType().equals(type)) {
                return c;
            }
        }
        return null;
    }

    private Coup choisirPion(List<Coup> lc) {
        Coup coup = null;
        if (jeu.getActivationPouvoirSor()) {
            if(joueurCourant == Jeu.JOUEUR_RGE){
                if(posSorcier > (posRoi + 3) && posSorcier < posGardeRouge){
                    for(Coup c : lc){
                        if(c.getPion().equals(Pion.ROI)){
                            return c;
                        }
                    }
                }
                if(posSorcier > posGardeRouge + 3){
                    for(Coup c : lc){
                        if(c.getPion().equals(Pion.GAR_RGE)){
                            return c;
                        }
                    }
                }
                if(posSorcier < posRoi && posGardeVert < (posSorcier + 3)){
                    for(Coup c : lc){
                        if(c.getPion().equals(Pion.GAR_VRT)){
                            return c;
                        }
                    }
                }
            }
            else{
                if(posSorcier < (posRoi - 3) && posSorcier > posGardeVert){
                    for(Coup c : lc){
                        if(c.getPion().equals(Pion.ROI)){
                            return c;
                        }
                    }
                }
                if(posSorcier < posGardeVert - 3){
                    for(Coup c : lc){
                        if(c.getPion().equals(Pion.GAR_VRT)){
                            return c;
                        }
                    }
                }
                if(posSorcier > posRoi && posGardeRouge > (posSorcier - 3)){
                    for(Coup c : lc){
                        if(c.getPion().equals(Pion.GAR_RGE)){
                            return c;
                        }
                    }
                }
            }
        } else if (jeu.getActivationPouvoirFou()) {
            coup = choisirCoupAlea(lc);
        } else {
            if (joueurCourant == Jeu.JOUEUR_RGE) {
                if ((pionsChateauAdverse & gardeVert) == gardeVert && posGardeVert < (posRoi - 1)) {
                    for (Coup c : lc) {
                        if (c.getPion().equals(Pion.GAR_VRT)) {
                            coup = c;
                        }
                    }
                } else if (((pionsDucheAdverse & gardeRouge) == gardeRouge || (pionsSurFontaine & gardeRouge) == gardeRouge)) {
                    for (Coup c : lc) {
                        if (c.getPion().equals(Pion.GAR_RGE)) {
                            coup = c;
                        }
                    }
                }
                else if((pionsDucheAdverse & gardeVert) == gardeVert && posGardeVert < (posRoi - 1)){
                    for (Coup c : lc) {
                        if (c.getPion().equals(Pion.GAR_VRT)) {
                            coup = c;
                        }
                    }
                }
                else if (posRoi > Plateau.FONTAINE && ((pionsDucheAdverse & gardeVert) == gardeVert || ((pionsSurFontaine & gardeVert) == gardeVert) && (posRoi > Plateau.FONTAINE + 1))) {
                    for (Coup c : lc) {
                        if (c.getPion().equals(Pion.GAR_VRT)) {
                            coup = c;
                        }
                    }
                } else if ((pionsDuche & gardeRouge) == gardeRouge && jeu.getPlateau().getPositionPion(Pion.GAR_RGE) != Plateau.BORDURE_RGE) {
                    for (Coup c : lc) {
                        if (c.getPion().equals(Pion.GAR_RGE)) {
                            coup = c;
                        }
                    }
                }
            } else {
                if ((pionsChateauAdverse & gardeRouge) == gardeRouge && posGardeRouge > (posRoi + 1)) {
                    for (Coup c : lc) {
                        if (c.getPion().equals(Pion.GAR_RGE)) {
                            coup = c;
                        }
                    }
                } else if (((pionsDucheAdverse & gardeVert) == gardeVert || (pionsSurFontaine & gardeVert) == gardeVert)) {
                    for (Coup c : lc) {
                        if (c.getPion().equals(Pion.GAR_VRT)) {
                            coup = c;
                        }
                    }
                }
                else if((pionsDucheAdverse & gardeRouge) == gardeRouge && posGardeRouge > (posRoi + 1)){
                    for (Coup c : lc) {
                        if (c.getPion().equals(Pion.GAR_RGE)) {
                            coup = c;
                        }
                    }
                }else if (posRoi < Plateau.FONTAINE && ((pionsDucheAdverse & gardeRouge) == gardeRouge || ((pionsSurFontaine & gardeRouge) == gardeRouge) && posRoi < Plateau.FONTAINE - 1)) {
                    for (Coup c : lc) {
                        if (c.getPion().equals(Pion.GAR_RGE)) {
                            coup = c;
                        }
                    }
                } else if ((pionsDuche & gardeVert) == gardeVert && jeu.getPlateau().getPositionPion(Pion.GAR_VRT) != Plateau.BORDURE_VRT) {
                    for (Coup c : lc) {
                        if (c.getPion().equals(Pion.GAR_VRT)) {
                            coup = c;
                        }
                    }
                }
            }
        }
        if (coup != null) {
            return coup;
        }
        return choisirCoupAlea(lc);
    }

    private Coup choisirDirection(List<Coup> lc) {
        Coup coup = null;
        //privilege du roi des 2 cartes roi si possible
        if(jeu.getTypeCourant().equals(Type.ROI)){
            coup = jouePrivilegeRoi(lc);
        }
        if(coup != null){return coup;}

        if(jeu.getTypeCourant().equals(Type.GAR)){
            coup = joueGarde2(lc);
        }

        if(coup != null){return coup;}

        int possibilites = nbPossibilites(lc);
        if ((possibilites & 8) == 8) {
            if (joueurCourant == Jeu.JOUEUR_RGE) {
                if (posGardeRouge < Plateau.BORDURE_RGE) {
                    for (Coup c : lc) {
                        if (c.getCarte() != null) {
                            coup = c;
                        }
                    }
                } else {
                    for (Coup c : lc) {
                        if (c.getDirection() == Plateau.DIRECTION_RGE) {
                            coup = c;
                        }
                    }
                }
            } else if (joueurCourant == Jeu.JOUEUR_VRT) {
                if (posGardeVert > Plateau.BORDURE_VRT) {
                    for (Coup c : lc) {
                        if (c.getCarte() != null) {
                            coup = c;
                        }
                    }
                } else {
                    for (Coup c : lc) {
                        if (c.getDirection() == Plateau.DIRECTION_VRT) {
                            coup = c;
                        }
                    }
                }
            }
        } else if ((possibilites & 4) == 4) {
            boolean versNous = false;
            boolean gVert = false;
            boolean gRouge = false;
            if (joueurCourant == Jeu.JOUEUR_RGE) {
                if (lc.size() == 2) {
                    for (Coup c : lc) {
                        if (c.getDirection() == Plateau.DIRECTION_RGE) {
                            versNous = true;
                            coup = c;
                        }
                        if (c.getPion() != null && c.getPion().equals(Pion.GAR_RGE)) {
                            gVert = true;
                        } else if (c.getPion() != null && c.getPion().equals(Pion.GAR_VRT)) {
                            gRouge = true;
                        }
                    }
                    if (gVert && versNous) {
                        return coup;
                    } else if (gRouge && versNous) {
                        return coup;
                    } else if (gVert && !versNous) {
                        for (Coup c : lc) {
                            if (c.getPion() != null) {
                                return c;
                            }
                        }
                    } else if (gRouge && !versNous) {
                        for (Coup c : lc) {
                            if (c.getPion() != null) {
                                return c;
                            }
                        }
                    }
                } else {
                    for (Coup c : lc) {
                        if (c.getDirection() == Plateau.DIRECTION_RGE) {
                            return c;
                        }
                    }
                }
            } else {
                if (lc.size() == 2) {
                    for (Coup c : lc) {
                        if (c.getDirection() == Plateau.DIRECTION_VRT) {
                            versNous = true;
                            coup = c;
                        }
                        if (c.getPion() != null && c.getPion().equals(Pion.GAR_RGE)) {
                            gVert = true;
                        } else if (c.getPion() != null && c.getPion().equals(Pion.GAR_VRT)) {
                            gRouge = true;
                        }
                    }
                    if (gRouge && versNous) {
                        return coup;
                    } else if (gVert && versNous) {
                        return coup;
                    } else if (gVert && !versNous) {
                        for (Coup c : lc) {
                            if (c.getPion() != null) {
                                return c;
                            }
                        }
                    } else if (gRouge && !versNous) {
                        for (Coup c : lc) {
                            if (c.getPion() != null) {
                                return c;
                            }
                        }
                    }
                } else {
                    for (Coup c : lc) {
                        if (c.getDirection() == Plateau.DIRECTION_VRT) {
                            return c;
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

    private int nbPossibilites(List<Coup> lc) {
        int possibles = 0;
        for (Coup c : lc) {
            if (c.getDirection() != Plateau.DIRECTION_IND && c.getDirection() == Plateau.DIRECTION_VRT) {
                possibles |= 1;
            } else if (c.getDirection() != Plateau.DIRECTION_IND && c.getDirection() == Plateau.DIRECTION_RGE) {
                possibles |= 2;
            } else if (c.getPion() != null) {
                possibles |= 4;
            } else if (c.getCarte() != null) {
                possibles |= 8;
            }
        }
        return possibles;
    }

    private Type carteEnFonctionNombre(){
        int nbRoi;
        int nbSor;
        int nbGarde;
        int nbFou;
        nbFou = jeu.getMain(joueurCourant).getNombreTypeCarte(Type.FOU);
        nbSor = jeu.getMain(joueurCourant).getNombreTypeCarte(Type.SOR);
        nbRoi = jeu.getMain(joueurCourant).getNombreTypeCarte(Type.ROI);
        nbGarde = jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR);

        if(nbFou >= 5)
            return Type.FOU;
        if(nbSor >= 5)
            return Type.SOR;
        if(nbGarde >= 5)
            return Type.GAR;
        if(nbRoi >= 5)
            return Type.ROI;
        return null;
    }

    Coup jouePrivilegeRoi(List<Coup> lc){
        Coup coup = null;
        if(jeu.getTypeCourant().equals(Type.ROI)){
            for (Coup c : lc) {
                if (c.getCarte() != null && c.getCarte().getType().equals(Type.ROI)) {
                    coup = c;
                }
            }
        }
        return coup;
    }

    Coup joueGarde2(List<Coup> lc){
        Coup coup = null;
        if(joueurCourant == Jeu.JOUEUR_RGE){
            if(posGardeVert == (posRoi - 1)){
                for(Coup c : lc){
                  if(c.getDirection() == Plateau.DIRECTION_RGE){
                      coup = c;
                      break;
                  }
                }
            }
        }
        else{
            if(posGardeRouge == (posRoi + 1)){
                for(Coup c : lc){
                    if(c.getDirection() == Plateau.DIRECTION_VRT){
                        coup = c;
                        break;
                    }
                }
            }
        }
        return coup;
    }

    private Coup verifNonDebordement(Type type, int position, List<Coup> lc, Coup coup){
        if(jeu.getTypeCourant().equals(type)){
            if(joueurCourant == Jeu.JOUEUR_RGE && (position + coup.getCarte().getDeplacement()) > Plateau.BORDURE_RGE){
                lc.remove(coup);
                for(int i = 0; i < lc.size(); i ++){
                    if(lc.get(i).getCarte() != null && lc.get(i).getCarte().getDeplacement() + position > Plateau.BORDURE_RGE){
                        lc.remove(lc.get(i));
                    }
                }
                coup = null;
            }
            else if(joueurCourant == Jeu.JOUEUR_VRT && (position - coup.getCarte().getDeplacement()) < Plateau.BORDURE_VRT){
                lc.remove(coup);
                for(int i = 0; i < lc.size(); i ++){
                    if(lc.get(i).getCarte() != null && lc.get(i).getCarte().getDeplacement() - position < Plateau.BORDURE_VRT){
                        lc.remove(lc.get(i));
                    }
                }
                coup = null;
            }
        }
        return coup;
    }
}

