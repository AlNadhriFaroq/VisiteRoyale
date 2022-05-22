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
    boolean fouSurGardeVert;
    boolean fouSurGardeRouge;
    boolean fouSurRoi;
    boolean fouSurSorcier;
    boolean fmPouvoirFou;

    List<Coup> lc;
    public IAStrategie1_1(Jeu jeu) {
        super(jeu);
        r = new Random();
        gagnantAvecCouronne = false;
        defausseCarte = false;
        fouSurGardeRouge = false;
        fouSurGardeVert = false;
        fouSurRoi = false;
        fouSurSorcier = false;
        fmPouvoirFou = false;
    }

    @Override
    public Coup calculerCoup() {
        System.out.println("IA strategie 1 1 !!!");
        lc = jeu.calculerListeCoup();
        System.out.println("ce que je recois lc : " + lc);
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
        Coup coup;
        if (lc.size() == 1) {
            System.out.println("size = 1");
            if(lc.get(0).getTypeCoup() != Coup.FINIR_TOUR && !jeu.getTypeCourant().equals(Type.IND)){
                if(jeu.getEtatJeu() != Jeu.ETAT_CHOIX_DIRECTION)
                    lc.add(new Coup(joueurCourant, Coup.FINIR_TOUR, null, null, Plateau.DIRECTION_IND));
            }
            else
                return lc.get(0);
        }
        switch (jeu.getEtatJeu()) {
            case Jeu.ETAT_CHOIX_CARTE:
                coup = choisirCarte();
                break;
            case Jeu.ETAT_CHOIX_PION:
                System.out.println();
                coup = choisirPion();
                break;
            case Jeu.ETAT_CHOIX_DIRECTION:
                coup = choisirDirection();
                break;
            case Jeu.ETAT_FIN_DE_PARTIE:
                return lc.get(0);
            default:
                throw new RuntimeException("IA.IAMoyenne.calculerCoup() : Etat du jeu invalide.");

        }
        return coup;
    }

    private Coup choisirCarte() {
        Coup coup = null;

        if(jeu.getTypeCourant().equals(Type.IND) && !jeu.getActivationPouvoirFou()) {
            gagnantAvecCouronne = false;
            defausseCarte = false;
            fouSurGardeRouge = false;
            fouSurGardeVert = false;
            fouSurRoi = false;
            fouSurSorcier = false;
            System.out.println("choisis carte ou pouvoir debut tour");
            coup = choisirCartePouvoirDebutTour(); //coup gagnant ou type gagnant s il y a sinon pouvoir sorcier si ca vaut le coup sinon le max de carte en main
            if(coup != null){
                return coup;
            }
        }

        if(defausseCarte){
            return choisirCarte(jeu.getTypeCourant());
        }

        if(!jeu.getTypeCourant().equals(Type.IND) && !jeu.getActivationPouvoirFou()){
            System.out.println("type courant : " + jeu.getTypeCourant());
            coup = choisirCarte(jeu.getTypeCourant());
        }

        if((!jeu.getTypeCourant().equals(Type.IND) && jeu.getActivationPouvoirFou()) || (jeu.getTypeCourant().equals(Type.IND) && jeu.getActivationPouvoirFou()) ){
            System.out.println("pouvoir fou activee ");
            if(fmPouvoirFou){
                System.out.println("fm utilise");
                for(Coup c : lc){
                    if(c.getCarte() != null && c.getCarte().estDeplacementFouCentre()){
                        fmPouvoirFou = false;
                        return c;
                    }
                }
            }
            List<Coup> tmp = new ArrayList<>();

            for(Coup c : lc){
                if(c.getCarte() != null){
                    if(!c.getCarte().estDeplacementFouCentre()){
                        tmp.add(c);
                    }
                }
                else{
                    tmp.add(c);
                }
            }
            lc = tmp ;
            System.out.println("lc apres aveoir enbleve deplacement fou centre");
            if(jeu.getTypeCourant().equals(Type.IND)&& !fouSurGardeVert && !fouSurGardeRouge){
                System.out.println("premier coup pouvoir fou");
                System.out.println("lc : " + lc);
                return choisirCoupAleaCarte();
            }

            tmp = new ArrayList<>();
            int i = 0;
            int tailleLc = lc.size();

            for (int j = 0; j < tailleLc; j ++ ){
                if (lc.get(j).getTypeCoup() == Coup.FINIR_TOUR){
                    tmp.add(lc.get(j));
                    tailleLc --;
                    break;
                }
            }

            System.out.println("taille = " + tailleLc);
            System.out.println("lc = " + lc);
            if(lc.size() == 0){
                return tmp.get(0);
            }
            coup = lc.get(i);

            if(fouSurGardeRouge){
                System.out.println("fou sur garde rouge");
                while(i < tailleLc && ((joueurCourant == Jeu.JOUEUR_RGE && coup.getCarte().getDeplacement() + jeu.getPlateau().getPositionPion(Pion.GAR_RGE) <= Plateau.BORDURE_RGE) ||
                        (-coup.getCarte().getDeplacement() + jeu.getPlateau().getPositionPion(Pion.GAR_RGE)) > posRoi && joueurCourant == Jeu.JOUEUR_VRT)){
                    System.out.println(-coup.getCarte().getDeplacement() + jeu.getPlateau().getPositionPion(Pion.GAR_RGE));
                    tmp.add(lc.get(i));
                    i++;
                    if(i < tailleLc)
                        coup = lc.get(i);
                }
            }
            else if(fouSurGardeVert){
                System.out.println("fou sur garde vert");
                while(i < tailleLc && ((joueurCourant == Jeu.JOUEUR_RGE && coup.getCarte().getDeplacement() + jeu.getPlateau().getPositionPion(Pion.GAR_VRT) < posRoi) ||
                        (-coup.getCarte().getDeplacement() + jeu.getPlateau().getPositionPion(Pion.GAR_VRT)) >= Plateau.BORDURE_VRT && joueurCourant == Jeu.JOUEUR_VRT)){
                    System.out.println(-coup.getCarte().getDeplacement() + jeu.getPlateau().getPositionPion(Pion.GAR_VRT));
                    tmp.add(lc.get(i));
                    i++;
                    if(i < tailleLc)
                        coup = lc.get(i);
                }
            }
            else if(fouSurRoi || fouSurSorcier){
                while(i < tailleLc && ((joueurCourant == Jeu.JOUEUR_RGE && coup.getCarte().getDeplacement() + jeu.getPlateau().getPositionPion(Pion.typeEnPion(jeu.getTypeCourant())) <= Plateau.BORDURE_RGE) ||
                        (-coup.getCarte().getDeplacement() + jeu.getPlateau().getPositionPion(Pion.typeEnPion(jeu.getTypeCourant())) >= Plateau.BORDURE_VRT && joueurCourant == Jeu.JOUEUR_VRT))){
                    System.out.println(-coup.getCarte().getDeplacement() + jeu.getPlateau().getPositionPion(Pion.GAR_RGE));
                    if(fouSurRoi){
                        if(joueurCourant == Jeu.JOUEUR_VRT){
                            if(posRoi - coup.getCarte().getDeplacement() > posGardeVert){
                                tmp.add(lc.get(i));
                            }
                        }
                        else if(joueurCourant == Jeu.JOUEUR_RGE){
                            if(posRoi + coup.getCarte().getDeplacement() < posGardeRouge){
                                tmp.add(lc.get(i));
                            }
                        }
                    }
                    else{
                        tmp.add(lc.get(i));
                    }
                    i++;
                    if(i < tailleLc)
                        coup = lc.get(i);
                }
            }

            System.out.println("tmp :" + tmp);

            lc = tmp;
            if(!jeu.getTypeCourant().equals(Type.IND))
                lc.add(new Coup(joueurCourant, Coup.FINIR_TOUR, null, null, Plateau.DIRECTION_IND));

            coup = choisirCarte(Type.FOU);

            if(coup == null){
                for (Coup c : lc){
                    if(c.getTypeCoup() == Coup.FINIR_TOUR){
                        return c;
                    }
                }
            }
        }

        coup = verifDeGCetFM(coup);

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
                            return choisirCoupAleaCarte();
                        } else {//pion sur fontaine
                            //ajouter des conditions comme joueur rouge et garde rouge sur fontaine
                            System.out.println("Pion dans la fontaine");
                            if ((pionsSurFontaine & sorcier) == sorcier && (jeu.getTypeCourant().equals(Type.SOR) || jeu.getTypeCourant().equals(Type.IND))) {
                                if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.SOR) >= 1) {
                                    coup = choisirCarteFontaine(Pion.SOR);
                                }
                            } else if ((pionsSurFontaine & fou) == fou && (jeu.getTypeCourant().equals(Type.FOU) || jeu.getTypeCourant().equals(Type.IND))) {
                                if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.FOU) >= 1) {
                                    coup = choisirCarteFontaine(Pion.FOU);
                                }
                            } else if ((pionsSurFontaine & gardeRouge) == gardeRouge && joueurCourant == Jeu.JOUEUR_RGE && (jeu.getTypeCourant().equals(Type.GAR) || jeu.getTypeCourant().equals(Type.IND))) {
                                if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) >= 1 && jeu.getPlateau().getPositionPion(Pion.GAR_RGE) + 1 < jeu.getPlateau().getPositionPion(Pion.ROI)) {
                                    coup = choisirCarteFontaine(Pion.GAR_RGE);
                                }
                            } else if ((pionsSurFontaine & gardeVert) == gardeVert && joueurCourant == Jeu.JOUEUR_VRT && (jeu.getTypeCourant().equals(Type.GAR) || jeu.getTypeCourant().equals(Type.IND))) {
                                if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) >= 1 && jeu.getPlateau().getPositionPion(Pion.GAR_VRT) - 1 > jeu.getPlateau().getPositionPion(Pion.ROI)) {
                                    coup = choisirCarteFontaine(Pion.GAR_VRT);
                                }
                            } else {
                                System.out.println("a fait un coup aleatoire 126");
                                return choisirCoupAleaCarte();
                            }
                        }
                    } else {
                        System.out.println("Pion dans notre chateau");
                        if ((pionsChateau & gardeVert) == gardeVert && joueurCourant == Jeu.JOUEUR_VRT && (jeu.getTypeCourant().equals(Type.GAR) || jeu.getTypeCourant().equals(Type.IND))) {
                            if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.ROI) >= 1 && jeu.getPlateau().getPositionPion(Pion.ROI) - 1 > jeu.getPlateau().getPositionPion(Pion.GAR_VRT)) {
                                coup = choisirCarte(Type.ROI);
                            }
                        } else if ((pionsChateau & gardeRouge) == gardeRouge && joueurCourant == Jeu.JOUEUR_RGE && (jeu.getTypeCourant().equals(Type.GAR) || jeu.getTypeCourant().equals(Type.IND))) {
                            if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.ROI) >= 1 && jeu.getPlateau().getPositionPion(Pion.ROI) + 1 < jeu.getPlateau().getPositionPion(Pion.GAR_VRT)) {
                                coup = choisirCarte(Type.ROI);
                            }
                        } else {
                            System.out.println("a fait un coup aleatoire 141 ");
                            // a remplace, par exemple si fou on ne veut pas le deplacer, faudrait surement faire sur tous en fait, c est dans la cas ou pas pions duche adverse
                            return choisirCoupAleaCarte();
                        }
                    }
                } else {
                    System.out.println("Pion dans duche adverse");
                    if ((pionsDucheAdverse & sorcier) == sorcier){
                        if((jeu.getTypeCourant().equals(Type.SOR) || jeu.getTypeCourant().equals(Type.IND)) && jeu.getMain(joueurCourant).getNombreTypeCarte(Type.SOR) >= 1) {
                            System.out.println("sorcier duche adverse et carte >= 1");
                            coup = choisirCarte(Type.SOR);
                        }
                        if((jeu.getTypeCourant().equals(Type.SOR) || jeu.getTypeCourant().equals(Type.IND)) && jeu.getMain(joueurCourant).getNombreTypeCarte(Type.SOR) == 0) {
                            System.out.println("sorcier duche adverse et carte == 0");
                            if (jeu.peutUtiliserPouvoirFou() && jeu.getMain(joueurCourant).getNombreTypeCarte(Type.FOU) > 0){
                                for (Coup c : lc) {
                                    if (c.getTypeCoup() == Coup.ACTIVER_POUVOIR_FOU) {
                                        fouSurSorcier = true;
                                        System.out.println("fous sur sorcier a true");
                                        return c;
                                    }
                                }
                            }
                        }
                    } else if ((pionsDucheAdverse & fou) == fou && (jeu.getTypeCourant().equals(Type.FOU) || jeu.getTypeCourant().equals(Type.IND)) && jeu.getMain(joueurCourant).getNombreTypeCarte(Type.FOU) >= 1) {
                            if (!jeu.getMain(joueurCourant).contientCarte(Carte.FM)) {
                                System.out.println("ne contient pas carte FM en main");
                                coup = choisirCarte(Type.FOU);
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
                            coup = choisirCarte(Type.ROI);
                        } else if ((jeu.getTypeCourant().equals(Type.ROI) || jeu.getTypeCourant().equals(Type.IND)) && jeu.getMain(joueurCourant).getNombreTypeCarte(Type.ROI) == 0) {
                            System.out.println("roi duche adverse et carte == 0");
                            if (jeu.peutUtiliserPouvoirFou() && jeu.getMain(joueurCourant).getNombreTypeCarte(Type.FOU) > 0) {
                                for (Coup c : lc) {
                                    if (c.getTypeCoup() == Coup.ACTIVER_POUVOIR_FOU) {
                                        fouSurSorcier = true;
                                        return c;
                                    }
                                }
                            }
                        }
                    }
                    else if ((pionsDucheAdverse & gardeRouge) == gardeRouge && joueurCourant == Jeu.JOUEUR_RGE && (jeu.getTypeCourant().equals(Type.GAR) || jeu.getTypeCourant().equals(Type.IND))){
                         if(jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) >= 1){
                             fouSurGardeRouge = true;
                             coup = choisirCarte(Type.GAR);
                         }
                         else{
                             if (jeu.peutUtiliserPouvoirFou() && jeu.getMain(joueurCourant).getNombreTypeCarte(Type.FOU) > 0) {
                                 int dist = distancePionFontaineJoueurCarteFM(Pion.GAR_RGE);
                                 if(pouvoirFouSurGarde(retourneDistanceMax(Type.GAR), dist, Pion.GAR_RGE)){
                                     System.out.println("peut faire sur gr");
                                 }
                                 else{
                                     System.out.println("ne peut pas faire sur gr");
                                     System.out.println(nbMAxEnMain());
                                     return choisirCarte(nbMAxEnMain());
                                 }
                                 if(dist == 0){
                                     List<Coup> tmp = new ArrayList<>();
                                     for (Coup c : lc){
                                         if(c.getCarte() != null){
                                             if(!c.getCarte().estDeplacementFouCentre()){
                                                 tmp.add(c);
                                             }
                                         }
                                         else{
                                             tmp.add(c);
                                         }
                                     }
                                     lc = tmp;
                                 }
                                 for (Coup c : lc) {
                                     if (c.getTypeCoup() == Coup.ACTIVER_POUVOIR_FOU) {
                                         //fouSurGardeRouge = true;
                                         return c;
                                     }
                                 }
                             }
                         }
                    }
                    else if ((pionsDucheAdverse & gardeVert) == gardeVert && joueurCourant == Jeu.JOUEUR_VRT && (jeu.getTypeCourant().equals(Type.GAR) || jeu.getTypeCourant().equals(Type.IND))) {
                        if(jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) >= 1){
                            fouSurGardeVert = true;
                            coup = choisirCarte(Type.GAR);
                        }
                        else{
                            if (jeu.peutUtiliserPouvoirFou() && jeu.getMain(joueurCourant).getNombreTypeCarte(Type.FOU) > 0) {
                                System.out.println("jeu.peutUtiliserPouvoirFou() && jeu.getMain(joueurCourant).getNombreTypeCarte(Type.FOU) > 0");
                                int dist = distancePionFontaineJoueurCarteFM(Pion.GAR_VRT);
                                if(pouvoirFouSurGarde(retourneDistanceMax(Type.GAR), dist, Pion.GAR_VRT)){
                                    System.out.println("peut faire sur gv");
                                }
                                else{
                                    System.out.println("ne peut pas faire sur gv");
                                    System.out.println(nbMAxEnMain());
                                    return choisirCarte(nbMAxEnMain());
                                }
                                if(dist == 0){
                                    List<Coup> tmp = new ArrayList<>();
                                    for (Coup c : lc){
                                        if(c.getCarte() != null){
                                            if(!c.getCarte().estDeplacementFouCentre()){
                                                tmp.add(c);
                                            }
                                        }
                                        else{
                                            tmp.add(c);
                                        }
                                    }
                                    lc = tmp;
                                }
                                for (Coup c : lc) {
                                    if (c.getTypeCoup() == Coup.ACTIVER_POUVOIR_FOU) {
                                        //fouSurGardeVert = true;
                                        return c;
                                    }
                                }
                            }
                        }

                    } else if ((pionsDucheAdverse & gardeVert) == gardeVert && joueurCourant == Jeu.JOUEUR_RGE && (jeu.getTypeCourant().equals(Type.GAR) || jeu.getTypeCourant().equals(Type.IND))) { // si garde vert dans chateau
                        System.out.println("(pionsDucheAdverse & gardeVert) == gardeVert && joueurCourant == Jeu.JOUEUR_RGE && (jeu.getTypeCourant().equals(Type.GAR) || jeu.getTypeCourant().equals(Type.IND))");
                        if(jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) == 0){
                            if (jeu.peutUtiliserPouvoirFou() && jeu.getMain(joueurCourant).getNombreTypeCarte(Type.FOU) > 0) {
                                System.out.println("jeu.peutUtiliserPouvoirFou() && jeu.getMain(joueurCourant).getNombreTypeCarte(Type.FOU) > 0");
                                int dist = distancePionFontaineJoueurCarteFM(Pion.GAR_VRT);
                                if(pouvoirFouSurGarde(retourneDistanceMax(Type.GAR), dist, Pion.GAR_VRT)){
                                    System.out.println("peut faire sur gv");
                                }
                                else{
                                    System.out.println("ne peut pas faire sur gv");
                                    System.out.println(nbMAxEnMain());
                                    return choisirCarte(nbMAxEnMain());
                                }
                                if(dist == 0){
                                    List<Coup> tmp = new ArrayList<>();
                                    for (Coup c : lc){
                                        if(c.getCarte() != null){
                                            if(!c.getCarte().estDeplacementFouCentre()){
                                                tmp.add(c);
                                            }
                                        }
                                        else{
                                            tmp.add(c);
                                        }
                                    }
                                    lc = tmp;
                                }
                                for (Coup c : lc) {
                                    if (c.getTypeCoup() == Coup.ACTIVER_POUVOIR_FOU) {
                                        //fouSurGardeVert = true;
                                        return c;
                                    }
                                }
                            }
                        }

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
                            coup = retournerCarteType(Type.FIN);
                        }
                        else if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) >= 1) {
                            System.out.println("jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) >= 1");
                            fouSurGardeVert = true;
                            coup = choisirCarte(Type.GAR);
                        }
                        if ((pionsDucheAdverse & roi) == roi) {
                            if (coup != null && coup.getCarte() != null && coup.getCarte().estDeplacementGarCentre() && posRoi < Plateau.FONTAINE - 1) {
                                lc.remove(coup);
                                fouSurGardeVert = true;
                                coup = choisirCarte(Type.GAR);
                            }
                        }
                    } else if ((pionsDucheAdverse & gardeRouge) == gardeRouge && joueurCourant == Jeu.JOUEUR_VRT && (jeu.getTypeCourant().equals(Type.GAR) || jeu.getTypeCourant().equals(Type.IND))) {
                        if(jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) == 0){
                            if (jeu.peutUtiliserPouvoirFou() && jeu.getMain(joueurCourant).getNombreTypeCarte(Type.FOU) > 0){
                                int dist = distancePionFontaineJoueurCarteFM(Pion.GAR_RGE);
                                if(pouvoirFouSurGarde(retourneDistanceMax(Type.GAR), dist, Pion.GAR_RGE)){
                                    System.out.println("peut faire sur gr");
                                }
                                else{
                                    System.out.println("ne peut pas faire sur gr");
                                    System.out.println(nbMAxEnMain());
                                    return choisirCarte(nbMAxEnMain());
                                }
                                if(dist == 0){
                                    List<Coup> tmp = new ArrayList<>();
                                    for (Coup c : lc){
                                        if(c.getCarte() != null){
                                            if(!c.getCarte().estDeplacementFouCentre()){
                                                tmp.add(c);
                                            }
                                        }
                                        else{
                                            tmp.add(c);
                                        }
                                    }
                                    lc = tmp;
                                }
                                for (Coup c : lc) {
                                    if (c.getTypeCoup() == Coup.ACTIVER_POUVOIR_FOU) {
                                        //fouSurGardeRouge = true;
                                        return c;
                                    }
                                }
                            }
                        }
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
                            coup = retournerCarteType(Type.FIN);
                        }
                        else if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) >= 1) {
                            fouSurGardeRouge = true;
                            coup = choisirCarte(Type.GAR);
                        }
                        if ((pionsDucheAdverse & roi) == roi) {
                            if (coup != null && coup.getCarte() != null && coup.getCarte().estDeplacementGarCentre() && posRoi > Plateau.FONTAINE + 1) {
                                lc.remove(coup);
                                fouSurGardeRouge = true;
                                coup = choisirCarte(Type.GAR);
                            }
                        }
                    }
                }
            } else {
                System.out.println("Pion dans le chateau adverse");
                if ((pionsChateauAdverse & sorcier) == sorcier) {
                    System.out.println("sorcier duche adverse");
                    if((jeu.getTypeCourant().equals(Type.SOR) || jeu.getTypeCourant().equals(Type.IND)) && jeu.getMain(joueurCourant).getNombreTypeCarte(Type.SOR) >= 1) {
                        System.out.println("sorcier duche adverse et carte >= 1");
                        coup = choisirCarte(Type.SOR);
                    }
                    else if((jeu.getTypeCourant().equals(Type.SOR) || jeu.getTypeCourant().equals(Type.IND)) && jeu.getMain(joueurCourant).getNombreTypeCarte(Type.SOR) == 0){
                        System.out.println("sorcier duche adverse et carte == 0");
                        if(jeu.peutUtiliserPouvoirFou() && jeu.getMain(joueurCourant).getNombreTypeCarte(Type.FOU) > 0){
                            for(Coup c : lc){
                                if(c.getTypeCoup() == Coup.ACTIVER_POUVOIR_FOU){
                                    fouSurSorcier = true;
                                    return c;
                                }
                            }
                        }
                    }
                } else if ((pionsChateauAdverse & fou) == fou && (jeu.getTypeCourant().equals(Type.FOU) || jeu.getTypeCourant().equals(Type.IND)) && jeu.getMain(joueurCourant).getNombreTypeCarte(Type.FOU) >= 1) {
                        if (!jeu.getMain(joueurCourant).contientCarte(Carte.FM)) {
                            coup = choisirCarte(Type.FOU);
                        } else {
                            for (Coup c : lc) {
                                if (c.getCarte().estDeplacementFouCentre()) {
                                    return c;
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
                    }

                    if(jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) == 0 && jeu.getMain(joueurCourant).getNombreTypeCarte(Type.ROI) > 3){
                        if(posGardeRouge < Plateau.CHATEAU_RGE){
                            System.out.println("garde dans chateau mais pas de carte garde");
                            return choisirCarte(Type.ROI);
                        }
                    }
                    else if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) >= 1) {
                        if(retourneDistanceMax(Type.GAR) < nbRoi / 2){
                            System.out.println("garde dans chateau et le fait d avoir des carte roi et faire coup roi avantage");
                            coup = choisirCarte(Type.ROI);
                        }
                        else{
                            System.out.println("garde dans chateau et le fait d avoir des carte roi et faire coup roi avantage pas");
                            fouSurGardeVert = true;
                            coup = choisirCarte(Type.GAR);
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
                    if(jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) == 0 && jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) > 3){
                        if(retourneDistanceMax(Type.GAR) < nbRoi / 2 && posGardeVert > Plateau.CHATEAU_VRT){
                            System.out.println("garde dans chateau mais pas de carte garde");
                            return choisirCarte(Type.ROI);
                        }
                    }
                    else if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) >= 1) {
                        if(retourneDistanceMax(Type.GAR) < nbRoi / 2){
                            System.out.println("garde dans chateau et le fait d avoir des carte roi et faire coup roi avantage");
                            coup = choisirCarte(Type.ROI);
                        }
                        else{
                            System.out.println("garde dans chateau et le fait d avoir des carte roi et faire coup roi avantage pas");
                            fouSurGardeRouge = true;
                            coup = choisirCarte(Type.GAR);
                        }
                    }
                }
            }
        }

        if(coup != null && coup.getCarte() != null) {
            if(jeu.peutUtiliserPouvoirFou() && !coup.getCarte().getType().equals(Type.GAR)){
                int distance = 0;
                System.out.println("pouvoir fou possible sur sorcier ou roi");
                if(coup.getCarte().getType().equals(Type.ROI)){
                    System.out.println("pouvoir fou possible sur roi");
                    if(jeu.getMain(joueurCourant).contientCarte(Carte.FM))
                        distance = distancePionFontaineJoueurCarteFM(Pion.ROI);
                    if (distance != 0 && (((pionsSurFontaine&gardeRouge)== gardeRouge)|| (pionsSurFontaine&gardeVert)==gardeVert ) && jeu.getMain(joueurCourant).contientCarte(Carte.FM) ){
                        List<Coup> tmp = new ArrayList<>();
                        for (Coup c :lc){
                            if (c.getCarte()!= null){
                                if(!c.getCarte().estDeplacementFouCentre()){
                                    tmp.add(c);
                                }
                            }
                            else {
                                tmp.add(c);
                            }
                        }
                        lc= tmp;
                    }
                    if(pouvoirFouSurRoi(retourneDistanceMax(Type.ROI), distance)){
                        fouSurRoi = true;
                        for(Coup c : lc){
                            if(c.getTypeCoup() == Coup.ACTIVER_POUVOIR_FOU){
                                return c;
                            }
                        }
                    }
                    else{
                        return coup;
                    }
                }
                else if(coup.getCarte().getType().equals(Type.SOR)){
                    System.out.println("pouvoir fou possible sur sorcier");
                    if(jeu.getMain(joueurCourant).contientCarte(Carte.FM))
                        distance = distancePionFontaineJoueurCarteFM(Pion.SOR);
                    fouSurSorcier = true;
                    if(distance + retourneDistanceMax(Type.FOU) > retourneDistanceMax(Type.SOR)){
                        for(Coup c : lc){
                            if(c.getTypeCoup() == Coup.ACTIVER_POUVOIR_FOU){
                                return c;
                            }
                        }
                    }
                    else{
                        return coup;
                    }
                }
            } else if(jeu.peutUtiliserPouvoirFou() && coup.getCarte().getType().equals(Type.GAR)){
                System.out.println(lc);
                System.out.println("pouvoir fou possible sur garde");
                int distance = 0;
                if((gardeVert & pionsChateauAdverse) == gardeVert || (gardeVert & pionsDucheAdverse) == gardeVert){
                    System.out.println("(gardeVert & pionsChateauAdverse) == gardeVert || (gardeVert & pionsDucheAdverse) == gardeVert ");
                        if(jeu.getMain(joueurCourant).contientCarte(Carte.FM) && (pionsSurFontaine & roi) != roi  ){
                            distance += Plateau.FONTAINE - jeu.getPlateau().getPositionPion(Pion.GAR_VRT);
                            System.out.println("distance = " + distance);

                        }
                        if (distance != 0 && jeu.getMain(joueurCourant).contientCarte(Carte.FM) && (pionsSurFontaine & roi) == roi ||
                                distance == 0 && jeu.getMain(joueurCourant).contientCarte(Carte.FM)){
                            List<Coup> tmp = new ArrayList<>();
                            for (Coup c :lc){
                                if (c.getCarte()!= null){
                                    if(!c.getCarte().estDeplacementFouCentre()){
                                        tmp.add(c);
                                    }
                                }
                                else {
                                    tmp.add(c);
                                }
                            }
                            lc = tmp;
                        }
                    if(distance > 0 && (pionsSurFontaine & roi) != roi && jeu.getMain(joueurCourant).contientCarte(Carte.FM)){
                        fmPouvoirFou = true;
                    }
                        fouSurGardeVert = true;
                }
                else if((gardeRouge & pionsChateauAdverse) == gardeRouge || (gardeRouge & pionsDucheAdverse) == gardeRouge){
                        if(jeu.getMain(joueurCourant).contientCarte(Carte.FM)&& (pionsSurFontaine&roi)!=roi){
                            distance += Plateau.FONTAINE - jeu.getPlateau().getPositionPion(Pion.GAR_RGE);
                            System.out.println("distance = " + distance);
                        }
                        System.out.println(distance);
                        if ((distance != 0 && jeu.getMain(joueurCourant).contientCarte(Carte.FM) && (pionsSurFontaine&roi)==roi) ||
                            distance== 0 && jeu.getMain(joueurCourant).contientCarte(Carte.FM)){
                            List<Coup> tmp = new ArrayList<>();
                            for (Coup c :lc){
                                if (c.getCarte()!= null){
                                    if(!c.getCarte().estDeplacementFouCentre()){
                                        tmp.add(c);
                                    }
                                }
                                else {
                                    tmp.add(c);
                                }
                            }
                            lc = tmp;

                        }
                    if(distance > 0 && (pionsSurFontaine & roi) != roi && jeu.getMain(joueurCourant).contientCarte(Carte.FM)){
                        fmPouvoirFou = true;
                    }
                        fouSurGardeRouge = true;
                }
                if(fouSurGardeVert){
                    if(pouvoirFouSurGarde(retourneDistanceMax(Type.GAR), distance, Pion.GAR_VRT)){
                        System.out.println("on veut activer pouvoir fou sur garde");
                        System.out.println(lc);
                        for(Coup c : lc){
                            if (c.getTypeCoup() == Coup.ACTIVER_POUVOIR_FOU){
                                System.out.println("pouvoir fou activer");
                                return c;
                            }
                        }
                    }
                    fouSurGardeVert = false;
                }
                else if(fouSurGardeRouge){
                    if(pouvoirFouSurGarde(retourneDistanceMax(Type.GAR), distance, Pion.GAR_RGE)){
                        System.out.println("on veut activer pouvoir fou sur garde");
                        System.out.println(lc);
                        for(Coup c : lc){
                            if (c.getTypeCoup() == Coup.ACTIVER_POUVOIR_FOU){
                                System.out.println("pouvoir fou activer");
                                return c;
                            }
                        }
                    }
                    fouSurGardeRouge = false;
                }

            }

            /*if (!coup.getCarte().estDeplacementFouCentre()) {
                System.out.println("verif debordement");
                if (coup.getCarte().getType().equals(Type.SOR)) {
                    coup = verifNonDebordement(coup.getCarte().getType(), posSorcier, coup);
                } else if (coup.getCarte().getType().equals(Type.FOU)) {
                    coup = verifNonDebordement(coup.getCarte().getType(), posFou, coup);
                }
            }*/
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
            System.out.println("coup alea 669");
            System.out.println(lc);
            if(lc.size() == 1)
                return lc.get(0);
            for (Coup c : lc){
                if(c.getTypeCoup() == Coup.FINIR_TOUR){
                    return c;
                }
            }
            if(jeu.getTypeCourant().equals(Type.IND)){
                    return choisirCarte(nbMAxEnMain());
            }
        } else {
            return coup;
        }
        return coup;
    }

    private int distancePionFontaineJoueurCarteFM(Pion pion){
        System.out.println("distancePionFontaineJoueurCarteFM(Pion pion)");
        int distance = 0;
        boolean b = pion.equals(Pion.GAR_RGE) || pion.equals(Pion.GAR_VRT);
        if(jeu.getPlateau().getPositionPion(pion) > Plateau.FONTAINE && joueurCourant == Jeu.JOUEUR_VRT){
            if(jeu.getMain(joueurCourant).contientCarte(Carte.FM) && b && (pionsSurFontaine & roi) == roi) {
                return 0;
            }
            if(jeu.getMain(joueurCourant).contientCarte(Carte.FM) && pion.equals(Pion.ROI) && ((pionsSurFontaine & gardeVert) == gardeVert || (pionsSurFontaine & gardeRouge) == gardeRouge)){
                return 0;
            }
            else{
                System.out.println("joueur vert choix fou");
                distance += jeu.getPlateau().getPositionPion(pion) - Plateau.FONTAINE;
                System.out.println(distance);
                fmPouvoirFou = true;
            }
        }
        else if(jeu.getPlateau().getPositionPion(pion) < Plateau.FONTAINE && joueurCourant == Jeu.JOUEUR_RGE ){
            if(jeu.getMain(joueurCourant).contientCarte(Carte.FM) && b && (pionsSurFontaine & roi) == roi){
                return 0;
            }
            if(jeu.getMain(joueurCourant).contientCarte(Carte.FM) && pion.equals(Pion.ROI) && ((pionsSurFontaine & gardeVert) == gardeVert || (pionsSurFontaine & gardeRouge) == gardeRouge)){
                return 0;
            }
            else{
                System.out.println("joueur rouge choix fou");
                distance += Plateau.FONTAINE - jeu.getPlateau().getPositionPion(pion);
                System.out.println(distance);
                fmPouvoirFou = true;
            }
        }
        return distance;
    }

    private boolean pouvoirFouSurRoi(int distanceCarteRoi, int distanceFM){
        System.out.println("test pouvoir fou sur roi");
        if(joueurCourant == Jeu.JOUEUR_VRT && posRoi > posGardeVert + 1){
            int distRoiGvert = posRoi - posGardeVert;
            int distanceMaxCumulee = distanceFM;
            int distanceMaxEnUnCoup = distanceFM;
            System.out.println(lc);
            for(Coup c : lc){
                System.out.println(c);
                 if(c.getCarte() != null && c.getCarte().getType().equals(Type.FOU) && !c.getCarte().estDeplacementFouCentre()){
                     if(c.getCarte().getDeplacement() > distanceMaxEnUnCoup && c.getCarte().getDeplacement() + distanceFM < distRoiGvert){
                         distanceMaxEnUnCoup = c.getCarte().getDeplacement();
                     }
                     if(distanceMaxCumulee + c.getCarte().getDeplacement() + distanceFM < distRoiGvert){
                         distanceMaxCumulee += c.getCarte().getDeplacement();
                     }
                 }
            }
            System.out.println("lc : " + lc);
            System.out.println("distance max 1 coup : " + distanceMaxEnUnCoup);
            System.out.println("distance max cumule : " + distanceMaxCumulee);
            System.out.println("distance max roi garde vert : " + distRoiGvert);
            System.out.println("distance carte roi : " + distanceCarteRoi);
            if(distanceMaxCumulee > distanceCarteRoi && distanceMaxEnUnCoup <= distanceMaxCumulee && distanceMaxCumulee < distRoiGvert){
                return true;
            }
            if(distanceMaxEnUnCoup > distanceCarteRoi && distanceMaxEnUnCoup < distRoiGvert){
                return true;
            }
        }
        else if(joueurCourant == Jeu.JOUEUR_RGE){
            int distRoiGrouge = posGardeRouge - posRoi;
            int distanceMaxCumulee = distanceFM;
            int distanceMaxEnUnCoup = distanceFM;
            for(Coup c : lc){
                if(c.getCarte() != null && c.getCarte().getType().equals(Type.FOU) && !c.getCarte().estDeplacementFouCentre()){
                    if(c.getCarte().getDeplacement() > distanceMaxEnUnCoup && c.getCarte().getDeplacement() + distanceFM < distRoiGrouge){
                        distanceMaxEnUnCoup = c.getCarte().getDeplacement();
                    }
                    if(distanceMaxCumulee + c.getCarte().getDeplacement() + distanceFM > distRoiGrouge){
                        distanceMaxCumulee += c.getCarte().getDeplacement();
                    }
                }
            }
            System.out.println("lc : " + lc);
            System.out.println("disnce max 1 coup : " + distanceMaxEnUnCoup);
            System.out.println("disnce max cumulee : " + distanceMaxCumulee);
            System.out.println("disnce max distRoigarerouge : " + distRoiGrouge);
            System.out.println("distance carte roi : " + distanceCarteRoi);
            if(distanceMaxCumulee > distanceCarteRoi && distanceMaxEnUnCoup <= distanceMaxCumulee && distanceMaxCumulee < distRoiGrouge){
                return true;
            }
            if(distanceMaxEnUnCoup > distanceCarteRoi && distanceMaxEnUnCoup < distRoiGrouge){
                return true;
            }
        }
        else{
            return false;
        }
        return false;
    }

    private boolean pouvoirFouSurGarde(Pion pionGarde){
        boolean ok = false;
        for(Coup c : lc){
            if(c.getCarte() != null && c.getCarte().getType().equals(Type.FOU) && !c.getCarte().estDeplacementFouCentre()){
                if(jeu.getPlateau().pionEstDeplacable(pionGarde, jeu.getPlateau().getPositionPion(pionGarde) + c.getCarte().getDeplacement() * Jeu.getDirectionJoueur(joueurCourant))){
                    System.out.println("on peut dfeplacer pion garde : " + pionGarde);
                    ok = true;
                }
            }
        }
        return ok;
    }

    private boolean pouvoirFouSurGarde(int distanceCarteGarde, int distanceFM, Pion pionGarde){
        boolean ok = pouvoirFouSurGarde(pionGarde);
        if(!ok){
            System.out.println("on ne peut pas deplacer le garde : " + pionGarde + " les cartes fous ne le permettent pas");
            boolean autreGarde = false;
            if(pionGarde == Pion.GAR_RGE){
                autreGarde = pouvoirFouSurGarde(Pion.GAR_VRT);
                System.out.println("on peut deplacer le garde : garde vert les cartes fous le permettent");
                fouSurGardeVert = true;
                fouSurGardeRouge = false;
            }
            if(pionGarde == Pion.GAR_VRT){
                autreGarde = pouvoirFouSurGarde(Pion.GAR_RGE);
                System.out.println("on peut deplacer le garde : garde rouge les cartes fous le permettent");
                fouSurGardeRouge = true;
                fouSurGardeVert = false;
            }
            if(!autreGarde)
                return false;
        }
        else{
            if(pionGarde == Pion.GAR_RGE)
                fouSurGardeRouge = true;
            if(pionGarde == Pion.GAR_VRT)
                fouSurGardeVert = true;
        }
        System.out.println("test pouvoir fou sur garde");
        if(joueurCourant == Jeu.JOUEUR_VRT && fouSurGardeVert){
            System.out.println("joueurCourant == Jeu.JOUEUR_VRT && fouSurGardeVert");
            int distRoiGvert = posGardeVert - Plateau.BORDURE_VRT;
            int distanceMaxCumulee = distanceFM;
            int distanceMaxEnUnCoup = distanceFM;
            for(Coup c : lc){
                if(c.getCarte() != null && c.getCarte().getType().equals(Type.FOU) && !c.getCarte().estDeplacementFouCentre()){
                    if(c.getCarte().getDeplacement() > distanceMaxEnUnCoup && c.getCarte().getDeplacement() + distanceFM < distRoiGvert){
                        distanceMaxEnUnCoup = c.getCarte().getDeplacement();
                    }
                    if(distanceMaxCumulee + c.getCarte().getDeplacement() + distanceFM < distRoiGvert){
                        distanceMaxCumulee += c.getCarte().getDeplacement();
                    }
                }
            }
            System.out.println("dist carte garde " + distanceCarteGarde);
            System.out.println("dist FM " + distanceFM);
            System.out.println("dist roi garde vert " + distRoiGvert);
            System.out.println("distanceMaxCumulee : " + distanceMaxCumulee);
            System.out.println("distanceMaxEnUnCoup" + distanceMaxEnUnCoup);
            if(distanceMaxCumulee > distanceCarteGarde && distanceMaxEnUnCoup <= distanceMaxCumulee && distanceMaxEnUnCoup < distRoiGvert){
                System.out.println("fouSurGardeVert = true;");
                fouSurGardeVert = true;
                return true;
            }
            if(distanceMaxEnUnCoup > distanceCarteGarde && distanceMaxEnUnCoup < distRoiGvert){
                fouSurGardeVert = true;
                System.out.println("fouSurGardeVert = true;");
                return true;
            }
        }
        else if(joueurCourant == Jeu.JOUEUR_VRT && fouSurGardeRouge){
            int distRoiGrouge = posGardeRouge - posRoi;
            int distanceMaxCumulee = distanceFM;
            int distanceMaxEnUnCoup = distanceFM;
            for(Coup c : lc){
                if(c.getCarte() != null && c.getCarte().getType().equals(Type.FOU) && !c.getCarte().estDeplacementFouCentre()){
                    if(c.getCarte().getDeplacement() > distanceMaxEnUnCoup && c.getCarte().getDeplacement() + distanceFM < distRoiGrouge){
                        distanceMaxEnUnCoup = c.getCarte().getDeplacement();
                    }
                    if(distanceMaxCumulee + c.getCarte().getDeplacement() + distanceFM < distRoiGrouge){
                        distanceMaxCumulee += c.getCarte().getDeplacement();
                    }
                }
            }
            System.out.println("dist carte garde " + distanceCarteGarde);
            System.out.println("dist FM " + distanceFM);
            System.out.println("dist roi garde vert " + distRoiGrouge);
            System.out.println("distanceMaxCumulee : " + distanceMaxCumulee);
            System.out.println("distanceMaxEnUnCoup" + distanceMaxEnUnCoup);

            if(distanceMaxCumulee > distanceCarteGarde && distanceMaxEnUnCoup <= distanceMaxCumulee && distanceMaxEnUnCoup < distRoiGrouge){
                fouSurGardeRouge = true;
                return true;
            }
            if(distanceMaxEnUnCoup > distanceCarteGarde && distanceMaxEnUnCoup < distRoiGrouge){
                fouSurGardeRouge = true;
                return true;
            }
        }
        else if(joueurCourant == Jeu.JOUEUR_RGE && fouSurGardeVert){
            System.out.println("joueurCourant == Jeu.JOUEUR_RGE && posGardeVert + 1 < posRoi");
            int distRoiGvert = posRoi - posGardeVert;
            int distanceMaxCumulee = distanceFM;
            int distanceMaxEnUnCoup = distanceFM;
            for(Coup c : lc){
                if(c.getCarte() != null && c.getCarte().getType().equals(Type.FOU) && !c.getCarte().estDeplacementFouCentre()){
                    if(c.getCarte().getDeplacement() > distanceMaxEnUnCoup && c.getCarte().getDeplacement() + distanceFM < distRoiGvert){
                        distanceMaxEnUnCoup = c.getCarte().getDeplacement();
                    }
                    if(posRoi + (distanceMaxCumulee + c.getCarte().getDeplacement() + distanceFM) < distRoiGvert){
                        distanceMaxCumulee += c.getCarte().getDeplacement();
                    }
                }
            }
            System.out.println("dist carte garde " + distanceCarteGarde);
            System.out.println("dist FM " + distanceFM);
            System.out.println("dist roi garde vert " + distRoiGvert);
            System.out.println("distanceMaxCumulee : " + distanceMaxCumulee);
            System.out.println("distanceMaxEnUnCoup" + distanceMaxEnUnCoup);
            if(distanceMaxCumulee > distanceCarteGarde && distanceMaxEnUnCoup <= distanceMaxCumulee && distanceMaxEnUnCoup < distRoiGvert){
                fouSurGardeVert = true;
                return true;
            }
            if(distanceMaxEnUnCoup > distanceCarteGarde && distanceMaxEnUnCoup < distRoiGvert){
                fouSurGardeVert = true;
                return true;
            }
        }
        else if(joueurCourant == Jeu.JOUEUR_RGE && fouSurGardeRouge){
            System.out.println("joueurCourant == Jeu.JOUEUR_RGE && fouSurGardeRouge");
            int distRoiGrouge = Plateau.BORDURE_RGE - posGardeRouge;
            int distanceMaxCumulee = distanceFM;
            int distanceMaxEnUnCoup = distanceFM;
            for(Coup c : lc){
                if(c.getCarte() != null && c.getCarte().getType().equals(Type.FOU) && !c.getCarte().estDeplacementFouCentre()){
                    if(c.getCarte().getDeplacement() > distanceMaxEnUnCoup && c.getCarte().getDeplacement() + distanceFM < distRoiGrouge){
                        distanceMaxEnUnCoup = c.getCarte().getDeplacement();
                    }
                    if(posRoi + (distanceMaxCumulee + c.getCarte().getDeplacement() + distanceFM) < distRoiGrouge){
                        distanceMaxCumulee += c.getCarte().getDeplacement();
                    }
                }
            }
            if(distanceMaxCumulee > distanceCarteGarde && distanceMaxEnUnCoup <= distanceMaxCumulee && distanceMaxEnUnCoup < distRoiGrouge){
                fouSurGardeRouge = true;
                return true;
            }
            if(distanceMaxEnUnCoup > distanceCarteGarde && distanceMaxEnUnCoup < distRoiGrouge){
                fouSurGardeRouge = true;
                return true;
            }
        }
        else{
            return false;
        }
        return false;
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

    private Coup choisirCoupAlea() {
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
            if(lc.size() == 0 && !jeu.getTypeCourant().equals(Type.IND))
                return new Coup(joueurCourant, Coup.FINIR_TOUR, null, null, Plateau.DIRECTION_IND);
            return lc.get(r.nextInt(lc.size()));
        }
    }

    private Coup choisirCoupAleaCarte() {
        if(lc.size() == 0){
            return new Coup(joueurCourant, Coup.FINIR_TOUR, null, null, Plateau.DIRECTION_IND);
        }
        return lc.get(r.nextInt(lc.size()));
    }

    private Coup choisirCarteFontaine(Pion pion) {
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

    private Coup choixPouvoirSorcier(){
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

    private Coup choisirCarte(Type type) {
        System.out.println(lc);
        for (Coup c : lc) {
            if (c.getCarte() != null && c.getCarte().getType().equals(type)) {
                return c;
            }
        }
        return null;
    }


    private Coup choixCoupFonctionPion(Pion pion){
        for(Coup c : lc){
            if(c.getPion().equals(pion)){
                return c;
            }
        }
        return null;
    }
    private Coup choisirPion() {
        Coup coup = null;
        if(jeu.getActivationPouvoirFou()){
            if(fouSurGardeRouge){
                System.out.println("fou sur garde rouge");
                return choixCoupFonctionPion(Pion.GAR_RGE);
            }
            if(fouSurGardeVert){
                System.out.println("fou sur garde vert");
                return choixCoupFonctionPion(Pion.GAR_VRT);
            }
            if(fouSurRoi){
                System.out.println("fou sur roi");
                return choixCoupFonctionPion(Pion.ROI);
            }
            if(fouSurSorcier){
                System.out.println("fou sur sorcier");
                return choixCoupFonctionPion(Pion.SOR);
            }
        } else if(gagnantAvecCouronne){
            switch(joueurCourant){
                case Jeu.JOUEUR_VRT:
                    for(Coup c : lc){
                        if(c.getPion().equals(Pion.GAR_VRT)){
                            return c;
                        }
                    }
                    break;
                case Jeu.JOUEUR_RGE:
                    for(Coup c : lc){
                        if(c.getPion().equals(Pion.GAR_RGE)){
                            return c;
                        }
                    }
                    break;
            }
        } else if (jeu.getActivationPouvoirSor()) {
            if(joueurCourant == Jeu.JOUEUR_RGE){
                if(posSorcier > (posRoi + 3) && posSorcier < posGardeRouge){
                    for(Coup c : lc){
                        if(c.getPion().equals(Pion.ROI)){
                            return c;
                        }
                    }
                } else if(posSorcier > posGardeRouge + 3){
                    for(Coup c : lc){
                        if(c.getPion().equals(Pion.GAR_RGE)){
                            return c;
                        }
                    }
                } else if(posSorcier < posRoi && posGardeVert < (posSorcier + 3)){
                    for(Coup c : lc){
                        if(c.getPion().equals(Pion.GAR_VRT)){
                            return c;
                        }
                    }
                }
            } else {
                if(posSorcier < (posRoi - 3) && posSorcier > posGardeVert){
                    for(Coup c : lc){
                        if(c.getPion().equals(Pion.ROI)){
                            return c;
                        }
                    }
                }
                else if(posSorcier < posGardeVert - 3){
                    for(Coup c : lc){
                        if(c.getPion().equals(Pion.GAR_VRT)){
                            return c;
                        }
                    }
                }
                else if(posSorcier > posRoi && posGardeRouge > (posSorcier - 3)){
                    for(Coup c : lc){
                        if(c.getPion().equals(Pion.GAR_RGE)){
                            return c;
                        }
                    }
                }
            }
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
                } else if((pionsDucheAdverse & gardeVert) == gardeVert && posGardeVert < (posRoi - 1)){
                    for (Coup c : lc) {
                        if (c.getPion().equals(Pion.GAR_VRT)) {
                            coup = c;
                        }
                    }
                } else if (posRoi > Plateau.FONTAINE && ((pionsDucheAdverse & gardeVert) == gardeVert || ((pionsSurFontaine & gardeVert) == gardeVert) && (posRoi > Plateau.FONTAINE + 1))) {
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
                } else if((pionsDucheAdverse & gardeRouge) == gardeRouge && posGardeRouge > (posRoi + 1)){
                    for (Coup c : lc) {
                        if (c.getPion().equals(Pion.GAR_RGE)) {
                            coup = c;
                        }
                    }
                } else if (posRoi < Plateau.FONTAINE && ((pionsDucheAdverse & gardeRouge) == gardeRouge || ((pionsSurFontaine & gardeRouge) == gardeRouge) && posRoi < Plateau.FONTAINE - 1)) {
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
        return choisirCoupAlea();
    }

    private Coup choisirDirection() {
        Coup coup = null;
        //privilege du roi des 2 cartes roi si possible
        if(jeu.getTypeCourant().equals(Type.ROI)){
            coup = jouePrivilegeRoi();
        }
        if(coup != null){return coup;}

        if(jeu.getTypeCourant().equals(Type.GAR) && !jeu.getActivationPouvoirFou()){
            coup = joueGarde2();
        }

        if(coup != null){return coup;}

        int possibilites = nbPossibilites();
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
        return choisirCoupAlea();
    }

    private int nbPossibilites() {
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

    Coup jouePrivilegeRoi(){
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

    Coup joueGarde2(){
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

    private Coup verifNonDebordement(Type type, int position, Coup coup){
        if(jeu.getTypeCourant().equals(type) || jeu.getTypeCourant().equals(Type.IND)){
            System.out.println("on verifie le debordement !");
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

    private Coup choisirCartePouvoirDebutTour(){
        Coup coup;
        coup = coupGagnant();
        if(coup != null){return coup;}

        coup = choixPouvoirSorcier();
        if(coup != null){return coup;}

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
                coup = choisirCarte(type);
            }
        }

        coup = verifDeGCetFM(coup);
        if(coup == null)
            coup = retournerCarteType(type);

        return coup;
    }

    private Coup coupGagnant(){
        int gagnantRoi;
        gagnantRoi = coupGagnantRoiChateau();
        switch(gagnantRoi){
            case 1:              //si on doit deplacer roi
                System.out.println("coup gagnant Roi roi");
                return retournerCarteType(Type.ROI);
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
                return retournerCarteType(Type.SOR);
            case 2: //garde vert
            case 4: //garde rouge
                System.out.println("coup gagnant couronne gardes");
                gagnantAvecCouronne = true;
                return retournerCarteType(Type.GAR);
            case 16: // fou
                System.out.println("coup gagnant couronne fous");
                return retournerCarteType(Type.FOU);
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
            if(jeu.getPioche().getTaille() <= (Math.max(nbFou, Math.max(nbGarde, Math.max(nbRoi, nbSor))))){
                System.out.println("gagnant a la defausse");
                defausseCarte = true;
                return retournerCarteType(nbMAxEnMain());
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
        return ((jeu.getPlateau().getPositionPion(pion) + deplacement * direction) <= Plateau.CHATEAU_VRT ||
                (jeu.getPlateau().getPositionPion(pion) + deplacement * direction) >= Plateau.CHATEAU_RGE) && deplacement > 0;
    }

    private Coup retournerCarteType(Type type){
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

    private Coup verifDeGCetFM(Coup coup){ //on verifie si les cartes sont utilisee a bon escient
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
                        if (c.getCarte() != null ){
                            if(!c.getCarte().estDeplacementFouCentre()){
                                tmp.add(c);
                            }
                        }
                        else{
                            tmp.add(c);
                        }
                    }
                    lc = tmp;
                    if((pionsChateau & fou) == fou && !jeu.getTypeCourant().equals(Type.IND)){
                        for(Coup c : lc){
                            if(c.getTypeCoup() == Coup.FINIR_TOUR){
                                return  c;
                            }
                        }
                    }
                    else if((pionsDuche & fou) == fou){
                        for(Coup c : lc){
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
                        for(Coup c : lc){
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
                    System.out.println("veut faire garde centre 1 mais gc pas utile");
                    List<Coup> tmp = new ArrayList<>();
                    for(Coup c : lc) {
                        if (c.getCarte() != null ){
                            if(!c.getCarte().estDeplacementGarCentre()){
                                tmp.add(c);
                            }
                        }
                         else{
                            tmp.add(c);
                        }
                    }
                    lc = tmp;
                    System.out.println("lc apres avoir voulu faire fgarde centre " + lc);
                    coup = null;
                }
                else if(joueurCourant == Jeu.JOUEUR_VRT && ((posRoi - posGardeVert > posGardeRouge - posRoi)) && (pionsChateauAdverse & gardeRouge) != gardeRouge){
                    List<Coup> tmp = new ArrayList<>();
                    for(Coup c : lc) {
                        if (c.getCarte() != null ){
                            if(!c.getCarte().estDeplacementGarCentre()){
                                tmp.add(c);
                            }
                        }
                        else{
                            tmp.add(c);
                        }
                    }
                    lc = tmp;
                    coup = null;
                }
            }
        }
        return coup;
    }

    private int retourneDistanceMax(Type type){
        int dist = 0;
        for(Coup c : lc){
            if(c.getCarte() != null && c.getCarte().getType().equals(type)){
                if(c.getCarte().getDeplacement() == 3 && type.equals(Type.GAR)){
                    dist += 2;
                }
                else if(c.getCarte().getDeplacement() == 6 && type.equals(Type.FOU)){
                    dist += 0;
                }
                else{
                    dist += c.getCarte().getDeplacement();
                }
            }
        }
        return dist;
    }

    private Type nbMAxEnMain(){
        int g = jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR);
        int s = jeu.getMain(joueurCourant).getNombreTypeCarte(Type.SOR);
        int f = jeu.getMain(joueurCourant).getNombreTypeCarte(Type.FOU);
        int r = jeu.getMain(joueurCourant).getNombreTypeCarte(Type.ROI);

        int max = Math.max(g, Math.max(s, Math.max(f, r)));

        if(max == s)
            return  Type.SOR;
        if(max == r)
            return  Type.ROI;
        if(max == g)
            return  Type.GAR;
        else
            return  Type.FOU;
    }
}