package IA;

import Modele.*;

import java.util.*;

public class IAStrategie1_1 extends IA {
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
    int tailleMain;
    int nbRoi;
    int nbSor;
    int nbGarde;
    int nbFou;
    boolean gagnantAvecCouronne;
    boolean defausseCarte;
    List<Coup> lc;
    public IAStrategie1_1(Jeu jeu) {
        super(jeu);
        r = new Random();
        gagnantAvecCouronne = false;
        defausseCarte = false;
    }

    @Override
    public Coup calculerCoup() {
        lc = jeu.calculerListeCoup();
        joueurCourant = jeu.getJoueurCourant();
        pionsSurFontaine = pionsSurFontaine();
        pionsChateauAdverse = pionDansChateau(joueurCourant);
        pionsDucheAdverse = pionsDansDuche(joueurCourant);
        pionsChateau = pionDansChateau(1 - joueurCourant);
        pionsDuche = pionsDansDuche(1 - joueurCourant);
        posRoi = jeu.getPlateau().getPositionPion(Pion.ROI);
        posGardeVert = jeu.getPlateau().getPositionPion(Pion.GAR_VRT);
        posGardeRouge = jeu.getPlateau().getPositionPion(Pion.GAR_RGE);
        posSorcier = jeu.getPlateau().getPositionPion(Pion.SOR);
        posFou = jeu.getPlateau().getPositionPion(Pion.FOU);
        tailleMain = jeu.getMain(joueurCourant).getTaille();
        nbFou = jeu.getMain(joueurCourant).getNombreTypeCarte(Type.FOU);
        nbSor = jeu.getMain(joueurCourant).getNombreTypeCarte(Type.SOR);
        nbRoi = jeu.getMain(joueurCourant).getNombreTypeCarte(Type.ROI);
        nbGarde = jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR);
       /* System.out.println("pionsduche " + pionsDuche);
        System.out.println("pionsduchAdverse " + pionsDucheAdverse);
        System.out.println("pionschateau " + pionsChateau);
        System.out.println("pionschateau adverse " + pionsChateauAdverse);*/
        Coup coup;
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
        return coup;
    }

    private Coup choisirCarte(List<Coup> lc) {
        Coup coup = null;

        if(jeu.getTypeCourant().equals(Type.IND)) {
            coup = choisirCartePouvoirDebutTour(lc); //coup gagnant ou type gagnant s il y a sinon pouvoir sorcier si ca vaut le coup sinon le max de carte en main
            if(coup != null){
                return coup;
            }
        }

        if(!jeu.getTypeCourant().equals(Type.IND)){
            coup = choisirCarte(lc, jeu.getTypeCourant());
        }

        coup = verifDeGCetFM(lc, coup);

        if(coup == null) {
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
                            //ajouter des conditions comme joueur rouge et garde rouge sur fontaine
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
                            // a remplace, par exemple si fou on ne veut pas le deplacer, faudrait surement faire sur tous en fait, c est dans la cas ou pas pions duche adverse
                            return choisirCoupAleaCarte(lc);
                        }
                    }
                } else {
                    System.out.println("Pion dans duche adverse");
                    if ((pionsDucheAdverse & sorcier) == sorcier && (jeu.getTypeCourant().equals(Type.SOR) || jeu.getTypeCourant().equals(Type.IND)) && jeu.getMain(joueurCourant).getNombreTypeCarte(Type.SOR) >= 1) {
                            coup = choisirCarte(lc, Type.SOR);
                    } else if ((pionsDucheAdverse & fou) == fou && (jeu.getTypeCourant().equals(Type.FOU) || jeu.getTypeCourant().equals(Type.IND)) && jeu.getMain(joueurCourant).getNombreTypeCarte(Type.FOU) >= 1) {
                            if (!jeu.getMain(joueurCourant).contientCarte(Carte.FM)) {
                                System.out.println("ne contient pas carte FM en main");
                                coup = choisirCarte(lc, Type.FOU);
                            } else {
                                System.out.println("carte FM en main");
                                for (Coup c : lc) {
                                    if (c.getCarte().estDeplacementFouCentre()) {
                                        return c;
                                    }
                                }
                            }
                    } else if ((pionsDucheAdverse & roi) == roi && (jeu.getTypeCourant().equals(Type.ROI) || jeu.getTypeCourant().equals(Type.IND)) && jeu.getMain(joueurCourant).getNombreTypeCarte(Type.ROI) >= 1) {
                        if (joueurCourant == Jeu.JOUEUR_RGE && posGardeRouge > posRoi + 1 || joueurCourant == Jeu.JOUEUR_VRT && posGardeVert < posRoi - 1) {
                                coup = choisirCarte(lc, Type.ROI);
                        }
                    } else if ((pionsDucheAdverse & gardeRouge) == gardeRouge && joueurCourant == Jeu.JOUEUR_RGE && (jeu.getTypeCourant().equals(Type.GAR) || jeu.getTypeCourant().equals(Type.IND)) && jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) >= 1) {
                            coup = choisirCarte(lc, Type.GAR);
                    } else if ((pionsDucheAdverse & gardeVert) == gardeVert && joueurCourant == Jeu.JOUEUR_VRT && (jeu.getTypeCourant().equals(Type.GAR) || jeu.getTypeCourant().equals(Type.IND)) && jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) >= 1) {
                            coup = choisirCarte(lc, Type.GAR);
                    } else if ((pionsDucheAdverse & gardeVert) == gardeVert && joueurCourant == Jeu.JOUEUR_RGE && (jeu.getTypeCourant().equals(Type.GAR) || jeu.getTypeCourant().equals(Type.IND))) { // si garde vert dans chateau
                        if (jeu.getMain(joueurCourant).contientCarte(Carte.GC) && (posGardeVert + 4) <= posRoi &&  (pionsChateau & gardeRouge) != gardeRouge) {
                            System.out.println("Choix garde centre duche");
                            for (Coup c : lc) {
                                if (c.getCarte() != null && c.getCarte().estDeplacementGarCentre()) {
                                    return c;
                                }
                            }
                        }
                        if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) == 1 && jeu.getMain(joueurCourant).contientCarte(Carte.GC)) {
                            System.out.println("1 seule carte gc on joue pas");
                            coup = retournerCarteType(lc, Type.FIN);
                        }
                        else if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) >= 1) {
                            coup = choisirCarte(lc, Type.GAR);
                        }
                        if ((pionsDucheAdverse & roi) == roi) {
                            if (coup != null && coup.getCarte() != null && coup.getCarte().estDeplacementGarCentre() && posRoi < Plateau.FONTAINE - 1) {
                                lc.remove(coup);
                                coup = choisirCarte(lc, Type.GAR);
                            }
                        }
                    } else if ((pionsDucheAdverse & gardeRouge) == gardeRouge && joueurCourant == Jeu.JOUEUR_VRT && (jeu.getTypeCourant().equals(Type.GAR) || jeu.getTypeCourant().equals(Type.IND))) {
                        if (jeu.getMain(joueurCourant).contientCarte(Carte.GC) && (posGardeRouge - 4) >= posRoi) {
                            System.out.println("Choix garde centre duche");
                            for (Coup c : lc) {
                                if (c.getCarte() != null && c.getCarte().estDeplacementGarCentre()) {
                                    return c;
                                }
                            }
                        }
                        if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) == 1 && jeu.getMain(joueurCourant).contientCarte(Carte.GC)) {
                            System.out.println("1 seule carte gc on joue pas");
                            coup = retournerCarteType(lc, Type.FIN);
                        }
                        else if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) >= 1) {
                            coup = choisirCarte(lc, Type.GAR);
                        }
                        if ((pionsDucheAdverse & roi) == roi) {
                            if (coup != null && coup.getCarte() != null && coup.getCarte().estDeplacementGarCentre() && posRoi > Plateau.FONTAINE + 1) {
                                lc.remove(coup);
                                coup = choisirCarte(lc, Type.GAR);
                            }
                        }
                    }
                }
            } else {
                System.out.println("Pion dans le chateau adverse");
                if ((pionsChateauAdverse & sorcier) == sorcier && (jeu.getTypeCourant().equals(Type.SOR) || jeu.getTypeCourant().equals(Type.IND))) {
                    if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.SOR) >= 1){
                        if(jeu.peutUtiliserPouvoirFou() && retourneDistanceMax(lc, Type.FOU) > retourneDistanceMax(lc, Type.SOR)){
                            System.out.println("active pouvoir fou");
                            for(Coup c : lc){
                                if(c.getTypeCoup() == Coup.ACTIVER_POUVOIR_FOU){
                                    return c;
                                }
                            }
                        }
                        else{
                            coup = choisirCarte(lc, Type.SOR);
                        }
                    }
                } else if ((pionsChateauAdverse & fou) == fou && (jeu.getTypeCourant().equals(Type.FOU) || jeu.getTypeCourant().equals(Type.IND))) {
                    if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.FOU) >= 1) {
                        if (!jeu.getMain(joueurCourant).contientCarte(Carte.FM)) {
                            coup = choisirCarte(lc, Type.FOU);
                        } else {
                            for (Coup c : lc) {
                                if (c.getCarte().estDeplacementFouCentre()) {
                                    return c;
                                }
                            }
                        }
                    }
                } else if ((pionsChateauAdverse & gardeVert) == gardeVert && joueurCourant == Jeu.JOUEUR_RGE && (jeu.getTypeCourant().equals(Type.GAR) || jeu.getTypeCourant().equals(Type.IND))) {
                    if (jeu.getMain(joueurCourant).contientCarte(Carte.GC) && (posRoi - posGardeVert >= posGardeRouge - posRoi)) {
                        System.out.println("Choix garde centre et garde vert plus loin ou egale en distance que garde rouge par rapport au roi");
                        for (Coup c : lc) {
                            if (c.getCarte() != null && c.getCarte().estDeplacementGarCentre()) {
                                return c;
                            }
                        }
                    } else if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) >= 1) {
                        if(retourneDistanceMax(lc, Type.GAR) > nbRoi / 2 && posGardeRouge - posRoi < nbRoi / 2){
                            System.out.println("garde dans chateau mais le fait d avoir des carte roi et faire coup roi avantage");
                            coup = choisirCarte(lc, Type.ROI);
                        }
                        else{
                            System.out.println("garde dans chateau et le fait d avoir des carte roi et faire coup roi avantage pas");
                            coup = choisirCarte(lc, Type.GAR);
                        }
                    }
                } else if ((pionsChateauAdverse & gardeRouge) == gardeRouge && joueurCourant == Jeu.JOUEUR_VRT && (jeu.getTypeCourant().equals(Type.GAR) || jeu.getTypeCourant().equals(Type.IND))) {
                    if (jeu.getMain(joueurCourant).contientCarte(Carte.GC) && (posRoi - posGardeVert <= posGardeRouge - posRoi)) {
                        System.out.println("Choix garde centre");
                        for (Coup c : lc) {
                            if (c.getCarte() != null && c.getCarte().estDeplacementGarCentre()) {
                                return c;
                            }
                        }
                    }
                    else if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) >= 1) {
                        if(retourneDistanceMax(lc, Type.GAR) > nbRoi / 2 && posRoi - posGardeVert < nbRoi / 2){
                            System.out.println("garde dans chateau mais le fait d avoir des carte roi et faire coup roi avantage");
                            coup = choisirCarte(lc, Type.ROI);
                        }
                        else{
                            System.out.println("garde dans chateau et le fait d avoir des carte roi et faire coup roi avantage pas");
                            coup = choisirCarte(lc, Type.GAR);
                        }
                    }
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
            if((jeu.getTypeCourant().equals(Type.SOR) && (pionsChateau & sorcier) == sorcier) || (jeu.getTypeCourant().equals(Type.FOU) && (pionsChateau & fou) == fou) || (jeu.getTypeCourant().equals(Type.GAR) && (pionsChateau & gardeRouge) == gardeRouge) || (jeu.getTypeCourant().equals(Type.GAR) && (pionsChateau & gardeVert) == gardeVert)){
                for (Coup c : lc){
                    if(c.getTypeCoup() == Coup.FINIR_TOUR){
                        return c;
                    }
                }
            }
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
                pions |= gardeVert;
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
        if(gagnantAvecCouronne){
            switch(joueurCourant){
                case Jeu.JOUEUR_VRT:
                    for(Coup c : lc){
                        if(c.getPion().equals(Pion.GAR_VRT)){
                            return c;
                        }
                    }
                case Jeu.JOUEUR_RGE:
                    for(Coup c : lc){
                        if(c.getPion().equals(Pion.GAR_RGE)){
                            return c;
                        }
                    }
            }
        }
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
            if(joueurCourant == Jeu.JOUEUR_RGE && posGardeRouge != Plateau.BORDURE_RGE){
                for (Coup c : lc) {
                    if (c.getCarte() != null && c.getCarte().getType().equals(Type.ROI)) {
                        coup = c;
                    }
                }
            }
            if(joueurCourant == Jeu.JOUEUR_VRT && posGardeVert != Plateau.BORDURE_VRT){
                for (Coup c : lc) {
                    if (c.getCarte() != null && c.getCarte().getType().equals(Type.ROI)) {
                        coup = c;
                    }
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
                List<Coup> tmp = new ArrayList<>(lc);
                for(Coup c : tmp){
                    if(c.getCarte() != null && c.getCarte().getDeplacement() + position > Plateau.BORDURE_RGE){
                        System.out.println("remove rouge");
                        lc.remove(c);
                    }
                    if(lc.size() == 0){
                        break;
                    }
                }
                coup = null;
            }
            else if(joueurCourant == Jeu.JOUEUR_VRT && (position - coup.getCarte().getDeplacement()) < Plateau.BORDURE_VRT){
                lc.remove(coup);
                List<Coup> tmp = new ArrayList<>(lc);
                for(Coup c : tmp){
                    if(c.getCarte() != null && c.getCarte().getDeplacement() - position < Plateau.BORDURE_VRT){
                        System.out.println("remove vert");
                        lc.remove(c);
                    }
                    if(lc.size() == 0){
                        break;
                    }
                }
                coup = null;
            }
        }
        return coup;
    }

    private Coup choisirCartePouvoirDebutTour(List<Coup> lc){
        Coup coup;
        coup = coupGagnant(lc);
        if(coup != null){return coup;}

        coup = choixPouvoirSorcier(lc);
        if(coup != null){return coup;}

        if(jeu.peutUtiliserPouvoirFou()){
            coup = choixPouvoirFou(lc);
            if(coup != null){return coup;}
        }

        Type type = carteEnFonctionNombre();
        if(type != null){
            System.out.println("type n est pas nul + de 5 cartes en main 1 " + type);
            if(type.equals(Type.GAR) && jeu.getMain(joueurCourant).contientCarte(Carte.GC)){
                for(Coup c : lc){
                    if(c.getCarte() != null && c.getCarte().estDeplacementGarCentre()){
                        coup = c;
                    }
                }
            }
            else if(type.equals(Type.FOU) && jeu.getMain(joueurCourant).contientCarte(Carte.FM)){
                for(Coup c : lc){
                    if(c.getCarte() != null && c.getCarte().estDeplacementFouCentre()){
                        coup = c;
                    }
                }
            }
            else{
                coup = choisirCarte(lc, type);
            }
        }

        coup = verifDeGCetFM(lc, coup);
        if(coup == null)
            coup = retournerCarteType(lc, type);

        return coup;
    }

    private Coup coupGagnant(List<Coup> lc){
        int gagnantRoi;
        gagnantRoi = coupGagnantRoiChateau();
        switch(gagnantRoi){
            case 1:              //si on doit deplacer roi
                System.out.println("coup gagnant Roi roi");
                return retournerCarteType(lc, Type.ROI);
            case 8:     //si on doit faire pouvoir sorcier
                System.out.println("coup gagnant Roi sorcier");
                if(jeu.getTypeCourant().equals(Type.IND)){
                    for(Coup c : lc){
                        if(c.getTypeCoup() == Coup.ACTIVER_POUVOIR_SOR){
                            return c;
                        }
                    }
                }
                return null;
            default:
                System.out.println("pas de coup gagnant Roi");
        }

        int gagnantCouronne = coupGagnantCouronne();
        switch (gagnantCouronne){
            case 8: //sorcier
                System.out.println("coup gagnant couronne sorcier");
                return retournerCarteType(lc, Type.SOR);
            case 2: //garde vert
            case 4: //garde rouge
                System.out.println("coup gagnant couronne gardes");
                gagnantAvecCouronne = true;
                return retournerCarteType(lc, Type.GAR);
            case 16: // fou
                System.out.println("coup gagnant couronne fous");
                return retournerCarteType(lc, Type.FOU);
            case -1:
                System.out.println("coup gagnant couronne -1 pas encore mis en place");
                break;
            case -3: // activer pouvoir sorcier
                System.out.println("on gagne en faisant un tp d'un pion grace au sorcier");
                for(Coup c : lc){
                    if (c.getTypeCoup() == Coup.ACTIVER_POUVOIR_SOR){
                        return c;
                    }
                }
                break;
            default:
                System.out.println("pas de coup gagnant couronne");
        }

        if(jeu.getPlateau().getFaceCouronne() == Plateau.FACE_PTT_CRN) {
            int gagnantPioche = coupGagnantFinPioche();
            switch (gagnantPioche) {
                case 1:
                    System.out.println("coup gagnant pioche roi");
                    return retournerCarteType(lc, Type.ROI);
                case 8: //sorcier
                    System.out.println("coup gagnant pioche sor");
                    return retournerCarteType(lc, Type.SOR);
                case 2: //garde vert
                    System.out.println("coup gagnant pioche gar");
                    return retournerCarteType(lc, Type.GAR);
                case 16: // fou
                    System.out.println("coup gagnant pioche fou");
                    return retournerCarteType(lc, Type.FOU);
                default:
                    System.out.println("pas de coup gagnant pioche");
            }
        }
        return null;
    }

    private int coupGagnantRoiChateau(){
        int nbCarteRoi = jeu.getMain(joueurCourant).getNombreTypeCarte(Type.ROI);
        if(joueurCourant == Jeu.JOUEUR_VRT){                //pour le joueur vert
            if(posRoi - nbCarteRoi <= Plateau.CHATEAU_VRT){ // s'il y a assez de cartes pour amener le roi dans le chateau alors
                if(posGardeVert == Plateau.BORDURE_VRT){    //si le garde vert tout a gauche on peut gagner
                    return roi;
                }
                else{                                     //sinon si le garde vert pas tout a gauche, on regarde si avec le privilege on peut l y amener et amer le roi ensuite
                    for(int i = 1; i <= nbCarteRoi / 2 && posGardeVert - i >= Plateau.BORDURE_VRT; i++){
                        if(posGardeVert - i == Plateau.BORDURE_VRT && posRoi - (nbCarteRoi - i * 2) - i <= Plateau.CHATEAU_VRT){
                            return roi;
                        }
                    }
                }
            }
            if(posSorcier == Plateau.CHATEAU_VRT && posGardeVert == Plateau.BORDURE_VRT && jeu.getTypeCourant().equals(Type.IND)){
                return sorcier;
            }
        }
        else if(joueurCourant == Jeu.JOUEUR_RGE){                //pour le joueur vert
            if(posRoi + nbCarteRoi >= Plateau.CHATEAU_VRT){ // s'il y a assez de cartes pour amener le roi dans le chateau alors
                if(posGardeRouge == Plateau.BORDURE_RGE){    //si le garde rouge tout a la bordure on peut gagner
                    return roi;
                }
                else{                                       //
                    for(int i = 1; i <= nbCarteRoi / 2 && posGardeRouge + i <= Plateau.BORDURE_RGE; i++){
                        if(posGardeRouge + i == Plateau.BORDURE_RGE && posRoi + (nbCarteRoi - i * 2) + i >= Plateau.CHATEAU_RGE){
                            return roi;
                        }
                    }
                }
            }
            if(posSorcier == Plateau.CHATEAU_RGE && posGardeRouge == Plateau.BORDURE_RGE && jeu.getTypeCourant().equals(Type.IND)){
                return sorcier;
            }
        }
        return -1;
    }

    private int coupGagnantCouronne(){
        int nbPieceChateau = 0;
        System.out.println("essaie de trouver coup gagnant couronne");
        System.out.println("joueur courant = " + joueurCourant);
        System.out.println("pionsduche " + pionsDuche);
        System.out.println("pionsduchAdverse " + pionsDucheAdverse);
        System.out.println("pionschateau " + pionsChateau);
        System.out.println("pionschateau adverse " + pionsChateauAdverse);
        if(((gardeRouge & pionsDuche) == gardeRouge || (gardeRouge & pionsChateau) == gardeRouge) && ((gardeVert & pionsDuche) == gardeVert || (gardeVert & pionsChateau) == gardeVert ) && (roi & pionsDuche) == roi){
            System.out.println("cour dans duche");
            nbPieceChateau ++;
        }
        boolean f = false;
        boolean s = false;
        boolean gr = false;
        boolean gv = false;
        if((sorcier & pionsChateau) == sorcier){
            s = true;
            nbPieceChateau ++;
        }
        if((fou & pionsChateau) == fou){
            f = true;
            nbPieceChateau ++;
        }
        if(joueurCourant == Jeu.JOUEUR_VRT){
            System.out.println("joueur vert dans coup gagnant couronne");
            if((gardeVert & pionsChateau) == gardeVert){
                nbPieceChateau ++;
                gv = true;
            }
            if(jeu.getPlateau().getPositionCouronne() - nbPieceChateau <= Plateau.CHATEAU_VRT){
                return -1;
            }
            else if(jeu.getPlateau().getPositionCouronne() - (nbPieceChateau + 1) <= Plateau.CHATEAU_VRT){
                if((sorcier & pionsDuche) == sorcier && posRoi < posSorcier && ((gardeRouge & pionsDucheAdverse) == gardeRouge || (gardeRouge & pionsChateauAdverse) == gardeRouge)){
                    System.out.println("tp garde pouvoir fou");
                    return -3;
                }
                if(!s) {
                    if (peutMettrePionDansChateau(Pion.SOR, Plateau.DIRECTION_VRT)) {
                        System.out.println("sorcier peut etre mis dans chateau vert");
                        return sorcier;
                    }
                }
                if(!f) {
                    if(peutMettrePionDansChateau(Pion.FOU, Plateau.DIRECTION_VRT)){
                        System.out.println("fou peut etre mis dans chateau vert");
                        return fou;
                    }
                }
                if(!gv){
                    if(peutMettrePionDansChateau(Pion.GAR_VRT, Plateau.DIRECTION_VRT)){
                        System.out.println("garde vert peut etre mis dans chateau vert");
                        return gardeVert;
                    }
                }
            }
        }
        else if(joueurCourant == Jeu.JOUEUR_RGE){
            if((gardeRouge & pionsChateau) == gardeRouge){
                nbPieceChateau ++;
                gr = true;
            }
            if(jeu.getPlateau().getPositionCouronne() + nbPieceChateau >= Plateau.CHATEAU_RGE){
                return -1;
            }
            else if(jeu.getPlateau().getPositionCouronne() + nbPieceChateau + 1 >= Plateau.CHATEAU_RGE){
                System.out.println("si on deplace un pion on gagne a la couronne");
                if((sorcier & pionsDuche) == sorcier && posRoi > posSorcier && ((gardeVert & pionsDucheAdverse) == gardeVert || (gardeVert & pionsChateauAdverse) == gardeVert || (gardeVert & pionsSurFontaine) == gardeVert )){
                    System.out.println("tp garde pouvoir sor");
                    return -3;
                }
                if(!s){
                    if(peutMettrePionDansChateau(Pion.SOR, Plateau.DIRECTION_RGE)){
                        System.out.println("sorcier peut etre mis dans chateau rouge");
                        return sorcier;
                    }
                }
                if(!f){
                    if(peutMettrePionDansChateau(Pion.FOU, Plateau.DIRECTION_RGE)){
                        System.out.println("fou peut etre mis dans chateau rouge");
                        return fou;
                    }
                }
                if(!gr){
                    if(peutMettrePionDansChateau(Pion.GAR_RGE, Plateau.DIRECTION_RGE)){
                        System.out.println("garde rouge peut etre mis dans chateau rouge");
                        return gardeRouge;
                    }
                }
            }
        }
        return -2;
    }

    private int coupGagnantFinPioche(){
        int max;
        if(jeu.getPioche().getTaille() <= (max = Math.max(nbFou, Math.max(nbGarde, Math.max(nbRoi, nbSor))))){
            defausseCarte = true;
            if(nbFou == max)
                return fou;
            if(nbRoi == max)
                return roi;
            if(nbSor == max)
                return sorcier;
            else
                return gardeVert;
        }
        return -1;
    }

    private boolean peutMettrePionDansChateau(Pion pion, int direction){
        switch(pion.toString()){
            case "F":
                return deplacementDansChateau(Type.FOU, direction, Pion.FOU);
            case "S":
                return deplacementDansChateau(Type.SOR, direction, Pion.SOR);
            case "GR":
                return deplacementDansChateau(Type.GAR, direction, Pion.GAR_RGE);
            case "GV":
                return deplacementDansChateau(Type.GAR, direction, Pion.GAR_VRT);
        }
        return false;
    }

    private boolean deplacementDansChateau(Type type, int direction, Pion pion){
        int deplacement = 0;
        System.out.println("pion : " + pion);
        for(int i = 0; i < tailleMain; i++){
            if((jeu.getMain(joueurCourant).getCarte(i).getDeplacement() != 3 && type.equals(Type.GAR) || jeu.getMain(joueurCourant).getCarte(i).getDeplacement() != 6 && type.equals(Type.FOU)) && jeu.getMain(joueurCourant).getCarte(i).getType().equals(type)) {
                if (((jeu.getPlateau().getPositionPion(pion) + jeu.getMain(joueurCourant).getCarte(i).getDeplacement() * direction) <= Plateau.CHATEAU_VRT &&
                       (jeu.getPlateau().getPositionPion(pion) + jeu.getMain(joueurCourant).getCarte(i).getDeplacement() * direction) >= Plateau.BORDURE_VRT) ||
                       ((jeu.getPlateau().getPositionPion(pion) + jeu.getMain(joueurCourant).getCarte(i).getDeplacement() * direction) >= Plateau.CHATEAU_RGE &&
                       (jeu.getPlateau().getPositionPion(pion) + jeu.getMain(joueurCourant).getCarte(i).getDeplacement() * direction) <= Plateau.BORDURE_RGE)) {
                    System.out.println("deplacement dans chateau possible");
                    return true;
                }
            }
            System.out.println("else de deplacement dans chateau");
            if(jeu.getMain(joueurCourant).getCarte(i).getDeplacement() == 6 && jeu.getMain(joueurCourant).getCarte(i).getType().equals(type)){
                System.out.println("fm en main");
                switch(direction){
                    case Plateau.DIRECTION_VRT:
                        System.out.println("si fm on regarde si ca nous arrange");
                        if(posFou - Plateau.FONTAINE > 0){
                            System.out.println("fm permet de rapprocher le fou");
                            deplacement += (posFou - Plateau.FONTAINE);
                        }
                        break;
                    case Plateau.DIRECTION_RGE:
                        System.out.println("si fm on regarde si ca nous arrange");
                        if(Plateau.FONTAINE - posFou> 0){
                            System.out.println("fm permet de rapprocher le fou");
                            deplacement += Plateau.FONTAINE - posFou;
                        }
                        break;
                }
            }else if(jeu.getMain(joueurCourant).getCarte(i).getDeplacement() == 3 && jeu.getMain(joueurCourant).getCarte(i).getType().equals(type)){
                deplacement += 0;
            }
            else if(jeu.getMain(joueurCourant).getCarte(i).getType().equals(type)){
                System.out.println("ajoute au deplacement la valeur correspondante : " + jeu.getMain(joueurCourant).getCarte(i).getDeplacement());
                deplacement += jeu.getMain(joueurCourant).getCarte(i).getDeplacement();
            }
        }
            System.out.println("deplacement = " + deplacement);
            if(((jeu.getPlateau().getPositionPion(pion) + deplacement * direction) <= Plateau.CHATEAU_VRT ||
                    (jeu.getPlateau().getPositionPion(pion) + deplacement * direction) >= Plateau.CHATEAU_RGE)  && deplacement > 0){
                return true;
            }


        return  false;
    }

    private Coup retournerCarteType(List<Coup> lc, Type type){
        Coup finTour = null;
        for(Coup c : lc){
            if(c.getCarte() != null && c.getCarte().getType().equals(type)){
                return c; // on retourne une carte roi
            }
            if(c.getTypeCoup() == Coup.FINIR_TOUR){
                finTour = c;
            }
        }
        return finTour;
    }

    private Coup choixPouvoirFou(List<Coup> lc){

        return null;
    }

    private Coup verifDeGCetFM(List<Coup> lc, Coup coup){ //on verifie si les cartes sont utilisee a bon escient
        if(coup != null){
            if(jeu.getTypeCourant().equals(Type.FOU) && coup.getCarte().estDeplacementFouCentre() || jeu.getTypeCourant().equals(Type.IND) && coup.getCarte().estDeplacementFouCentre()){
                if(joueurCourant == Jeu.JOUEUR_RGE && posFou < Plateau.FONTAINE){
                    return coup;
                }
                if(joueurCourant == Jeu.JOUEUR_VRT && posFou > Plateau.FONTAINE){
                    return coup;
                }
                else{ // on supprime FM des possibilites
                    System.out.println("supprime les fms de la liste");
                    List<Coup> tmp = new ArrayList<>();
                    for(Coup c : lc) {
                        if (c.getCarte() != null && !c.getCarte().estDeplacementFouCentre()) {
                            tmp.add(c);
                        }
                    }
                    this.lc = tmp;
                    /*for(Coup c : lc){
                        if (c.getCarte() != null && !c.getCarte().estDeplacementFouCentre()){
                            return c;
                        }
                    }*/
                    if((pionsChateau & fou) == fou){
                        for(Coup c : this.lc){
                            if(c.getTypeCoup() == Coup.FINIR_TOUR){
                                return  c;
                            }
                        }
                    }
                    else if((pionsDuche & fou) == fou){
                        for(Coup c : this.lc){
                            if(c.getCarte() != null && c.getCarte().getType().equals(Type.FOU) &&
                               ((posFou + c.getCarte().getDeplacement() * jeu.getSelectionDirections(joueurCourant) >= Plateau.CHATEAU_RGE &&
                                 posFou + c.getCarte().getDeplacement() * jeu.getSelectionDirections(joueurCourant) <= Plateau.BORDURE_RGE) ||
                                 posFou + c.getCarte().getDeplacement() * jeu.getSelectionDirections(joueurCourant) <= Plateau.CHATEAU_VRT &&
                                 posFou + c.getCarte().getDeplacement() * jeu.getSelectionDirections(joueurCourant) >= Plateau.BORDURE_VRT) ){
                                return  c;
                            }

                        }
                    }
                    else{
                        for(Coup c : this.lc){
                            if(c.getCarte() != null && c.getCarte().getType().equals(Type.FOU) && (posFou + c.getCarte().getDeplacement() * jeu.getSelectionDirections(joueurCourant) <= Plateau.BORDURE_RGE ||
                                    posFou + c.getCarte().getDeplacement() * jeu.getSelectionDirections(joueurCourant) >= Plateau.BORDURE_VRT)){
                                return  c;
                            }
                        }
                    }
                    coup = null;
                }
            }
            else if(jeu.getTypeCourant().equals(Type.GAR) && coup.getCarte().estDeplacementGarCentre() || jeu.getTypeCourant().equals(Type.IND) && coup.getCarte().estDeplacementGarCentre()){
                System.out.println("veut faire garde centre 1");
                if(joueurCourant == Jeu.JOUEUR_RGE && ((posRoi - posGardeVert < posGardeRouge - posRoi)) && (pionsChateauAdverse & gardeVert) != gardeVert){
                    System.out.println("");
                    List<Coup> tmp = new ArrayList<>();
                    for(Coup c : lc) {
                        if (c.getCarte() != null && !c.getCarte().estDeplacementGarCentre()) {
                            tmp.add(c);
                        }
                    }
                    this.lc = tmp;
                    /*for(Coup c : lc){
                        if (c.getCarte() != null && !c.getCarte().estDeplacementGarCentre()){
                            return c;
                        }
                    }*/
                    coup = null;
                }
                else if(joueurCourant == Jeu.JOUEUR_VRT && ((posRoi - posGardeVert > posGardeRouge - posRoi)) && (pionsChateauAdverse & gardeRouge) != gardeRouge){
                    /*for(Coup c : lc){
                        if (c.getCarte() != null && !c.getCarte().estDeplacementGarCentre()){
                            return c;
                        }
                    }*/
                    List<Coup> tmp = new ArrayList<>();
                    for(Coup c : lc) {
                        if (c.getCarte() != null && !c.getCarte().estDeplacementGarCentre()) {
                            tmp.add(c);
                        }
                    }
                    this.lc = tmp;
                    coup = null;
                }
            }
        }
        return coup;
    }

    private int retourneDistanceMax(List<Coup> lc, Type type){
        int dist = 0;
        for(Coup c : lc){
            if(c.getCarte() != null && (c.getCarte().getDeplacement() != 3 && type.equals(Type.GAR) ||  (c.getCarte().getDeplacement() != 6 && type.equals(Type.FOU)))){
                dist += c.getCarte().getDeplacement();
            }
        }
        return dist;
    }
}