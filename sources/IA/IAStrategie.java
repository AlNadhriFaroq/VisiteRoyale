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
    boolean joueRoiPiocheGagnante;
    List<Coup> lc;

    public IAStrategie(Jeu jeu) {
        super(jeu);
        r = new Random();
        gagnantAvecCouronne = false;
        defausseCarte = false;
        fouSurGardeRouge = false;
        fouSurGardeVert = false;
        fouSurRoi = false;
        fouSurSorcier = false;
        fmPouvoirFou = false;
        joueRoiPiocheGagnante = false;
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
        Coup coup;
        if (lc.size() == 1) {
            if (lc.get(0).getTypeCoup() != Coup.FINIR_TOUR && !jeu.getTypeCourant().equals(Type.IND)) {
                if (jeu.getEtatJeu() != Jeu.ETAT_CHOIX_DIRECTION)
                    lc.add(new Coup(Coup.FINIR_TOUR, null, null, Plateau.DIRECTION_IND));
            } else {
                return lc.get(0);
            }
        }
        switch (jeu.getEtatJeu()) {
            case Jeu.ETAT_CHOIX_CARTE:
                coup = choisirCarte();
                break;
            case Jeu.ETAT_CHOIX_PION:
                coup = choisirPion();
                break;
            case Jeu.ETAT_CHOIX_DIRECTION:
                coup = choisirDirection();
                break;
            case Jeu.ETAT_FIN_DE_PARTIE:
                return lc.get(0);
            default:
                throw new RuntimeException("IA.IAStrategie.calculerCoup() : Etat du jeu invalide.");
        }
        return coup;
    }

    private Coup choisirCarte() {
        Coup coup = null;

        if (jeu.getTypeCourant().equals(Type.IND) && !jeu.getActivationPouvoirFou()) {
            gagnantAvecCouronne = false;
            defausseCarte = false;
            fouSurGardeRouge = false;
            fouSurGardeVert = false;
            fouSurRoi = false;
            fouSurSorcier = false;
            fmPouvoirFou = false;
            joueRoiPiocheGagnante = false;
            coup = choisirCartePouvoirDebutTour(); //coup gagnant ou type gagnant s il y a sinon pouvoir sorcier si ca vaut le coup sinon le max de carte en main
            if (coup != null)
                return coup;
        }

        if (defausseCarte){
            if(joueRoiPiocheGagnante){
                if(posRoi + nbRoi * Jeu.getDirectionJoueur(joueurCourant) - 1 > posGardeVert && posRoi + nbRoi * Jeu.getDirectionJoueur(joueurCourant) + 1 < posGardeRouge)
                    return choisirCarte(Type.ROI);
                else {
                    for(Coup c : lc){
                        if(c.getTypeCoup() == Coup.FINIR_TOUR)
                            return c;
                    }
                }
            }
            else
                return choisirCarte(jeu.getTypeCourant());
        }

        if (!jeu.getTypeCourant().equals(Type.IND) && !jeu.getActivationPouvoirFou())
            coup = choisirCarte(jeu.getTypeCourant());

        if ((!jeu.getTypeCourant().equals(Type.IND) && jeu.getActivationPouvoirFou()) || (jeu.getTypeCourant().equals(Type.IND) && jeu.getActivationPouvoirFou())) {
            if (fmPouvoirFou) {
                for (Coup c : lc) {
                    if (c.getCarte() != null && c.getCarte().estDeplacementFouCentre()) {
                        fmPouvoirFou = false;
                        return c;
                    }
                }
            }
            List<Coup> tmp = new ArrayList<>();

            for (Coup c : lc) {
                if (c.getCarte() != null) {
                    if (!c.getCarte().estDeplacementFouCentre())
                        tmp.add(c);
                } else {
                    tmp.add(c);
                }
            }
            lc = tmp;
            if (jeu.getTypeCourant().equals(Type.IND) && !fouSurGardeVert && !fouSurGardeRouge)
                return choisirCoupAleaCarte();

            tmp = new ArrayList<>();
            int i = 0;
            int tailleLc = lc.size();

            for (int j = 0; j < tailleLc; j++) {
                if (lc.get(j).getTypeCoup() == Coup.FINIR_TOUR) {
                    tmp.add(lc.get(j));
                    tailleLc--;
                    break;
                }
            }
            if (lc.size() == 0) {
                if (tmp.size() == 0)
                    return new Coup(Coup.FINIR_TOUR, null, null, Plateau.DIRECTION_IND);
                return tmp.get(0);
            }
            coup = lc.get(i);

            if (fouSurGardeRouge) {
                while (i < tailleLc && ((joueurCourant == Jeu.JOUEUR_RGE && coup.getCarte().getDeplacement() + jeu.getPlateau().getPositionPion(Pion.GAR_RGE) <= Plateau.BORDURE_RGE) ||
                        (-coup.getCarte().getDeplacement() + jeu.getPlateau().getPositionPion(Pion.GAR_RGE)) > posRoi && joueurCourant == Jeu.JOUEUR_VRT)) {
                    tmp.add(lc.get(i));
                    i++;
                    if (i < tailleLc)
                        coup = lc.get(i);
                }
            } else if (fouSurGardeVert) {
                while (i < tailleLc && ((joueurCourant == Jeu.JOUEUR_RGE && coup.getCarte().getDeplacement() + jeu.getPlateau().getPositionPion(Pion.GAR_VRT) < posRoi) ||
                        (-coup.getCarte().getDeplacement() + jeu.getPlateau().getPositionPion(Pion.GAR_VRT)) >= Plateau.BORDURE_VRT && joueurCourant == Jeu.JOUEUR_VRT)) {
                    tmp.add(lc.get(i));
                    i++;
                    if (i < tailleLc)
                        coup = lc.get(i);
                }
            } else if (fouSurRoi || fouSurSorcier) {
                while (i < tailleLc && ((joueurCourant == Jeu.JOUEUR_RGE && coup.getCarte().getDeplacement() + jeu.getPlateau().getPositionPion(Pion.typeEnPion(jeu.getTypeCourant())) <= Plateau.BORDURE_RGE) ||
                        (-coup.getCarte().getDeplacement() + jeu.getPlateau().getPositionPion(Pion.typeEnPion(jeu.getTypeCourant())) >= Plateau.BORDURE_VRT && joueurCourant == Jeu.JOUEUR_VRT))) {
                    if (fouSurRoi) {
                        if (joueurCourant == Jeu.JOUEUR_VRT) {
                            if (posRoi - coup.getCarte().getDeplacement() > posGardeVert)
                                tmp.add(lc.get(i));
                        } else if (joueurCourant == Jeu.JOUEUR_RGE) {
                            if (posRoi + coup.getCarte().getDeplacement() < posGardeRouge)
                                tmp.add(lc.get(i));
                        }
                    } else {
                        tmp.add(lc.get(i));
                    }
                    i++;
                    if (i < tailleLc)
                        coup = lc.get(i);
                }
            }

            lc = tmp;
            if (!jeu.getTypeCourant().equals(Type.IND))
                lc.add(new Coup(Coup.FINIR_TOUR, null, null, Plateau.DIRECTION_IND));

            coup = choisirCarte(Type.FOU);

            if (coup == null) {
                for (Coup c : lc)
                    if (c.getTypeCoup() == Coup.FINIR_TOUR)
                        return c;
            }
        }

        coup = verifDeGCetFM(coup);

        if (coup == null) {
            if (pionsChateauAdverse == 0) { //pas de pions dans le chateau adverse
                if (pionsDucheAdverse == 0) { //pas de pions dans le duche adverse
                    if (pionsChateau == 0) { // pas de pions dans notre chateau
                        if (pionsSurFontaine == 0) { // aucun pion dans la fontaine, tout dans notre duche
                            return choisirCoupAleaCarte();
                        } else {//pion sur fontaine
                            //ajouter des conditions comme joueur rouge et garde rouge sur fontaine
                            if ((pionsSurFontaine & sorcier) == sorcier && (jeu.getTypeCourant().equals(Type.SOR) || jeu.getTypeCourant().equals(Type.IND))) {
                                if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.SOR) >= 1)
                                    coup = choisirCarteFontaine(Pion.SOR);
                            }
                            if (coup == null && (pionsSurFontaine & fou) == fou && (jeu.getTypeCourant().equals(Type.FOU) || jeu.getTypeCourant().equals(Type.IND))) {
                                if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.FOU) >= 1)
                                    coup = choisirCarteFontaine(Pion.FOU);
                            }
                            if (coup == null && (pionsSurFontaine & gardeRouge) == gardeRouge && joueurCourant == Jeu.JOUEUR_RGE && (jeu.getTypeCourant().equals(Type.GAR) || jeu.getTypeCourant().equals(Type.IND))) {
                                if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) >= 1 && jeu.getPlateau().getPositionPion(Pion.GAR_RGE) + 1 < jeu.getPlateau().getPositionPion(Pion.ROI))
                                    coup = choisirCarteFontaine(Pion.GAR_RGE);
                            }
                            if (coup == null && (pionsSurFontaine & gardeVert) == gardeVert && joueurCourant == Jeu.JOUEUR_VRT && (jeu.getTypeCourant().equals(Type.GAR) || jeu.getTypeCourant().equals(Type.IND))) {
                                if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) >= 1 && jeu.getPlateau().getPositionPion(Pion.GAR_VRT) - 1 > jeu.getPlateau().getPositionPion(Pion.ROI))
                                    coup = choisirCarteFontaine(Pion.GAR_VRT);
                            } else
                                return choisirCoupAleaCarte();
                        }
                    } else {
                        if ((pionsChateau & gardeVert) == gardeVert && joueurCourant == Jeu.JOUEUR_VRT && (jeu.getTypeCourant().equals(Type.GAR) || jeu.getTypeCourant().equals(Type.IND))) {
                            if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.ROI) >= 1 && jeu.getPlateau().getPositionPion(Pion.ROI) - 1 > jeu.getPlateau().getPositionPion(Pion.GAR_VRT))
                                coup = choisirCarte(Type.ROI);
                        }
                        if (coup == null && (pionsChateau & gardeRouge) == gardeRouge && joueurCourant == Jeu.JOUEUR_RGE && (jeu.getTypeCourant().equals(Type.GAR) || jeu.getTypeCourant().equals(Type.IND))) {
                            if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.ROI) >= 1 && jeu.getPlateau().getPositionPion(Pion.ROI) + 1 < jeu.getPlateau().getPositionPion(Pion.GAR_VRT))
                                coup = choisirCarte(Type.ROI);
                        } else
                            // a remplace, par exemple si fou on ne veut pas le deplacer, faudrait surement faire sur tous en fait, c est dans la cas ou pas pions duche adverse
                            return choisirCoupAleaCarte();
                    }
                } else {
                    if ((pionsDucheAdverse & sorcier) == sorcier) {
                        if ((jeu.getTypeCourant().equals(Type.SOR) || jeu.getTypeCourant().equals(Type.IND)) && jeu.getMain(joueurCourant).getNombreTypeCarte(Type.SOR) >= 1)
                            coup = choisirCarte(Type.SOR);
                        if ((jeu.getTypeCourant().equals(Type.SOR) || jeu.getTypeCourant().equals(Type.IND)) && jeu.getMain(joueurCourant).getNombreTypeCarte(Type.SOR) == 0) {
                            if (jeu.peutUtiliserPouvoirFou() && jeu.getMain(joueurCourant).getNombreTypeCarte(Type.FOU) > 0) {
                                int dist = distancePionFontaineJoueurCarteFM(Pion.SOR);
                                if (dist == 0) {
                                    List<Coup> tmp = new ArrayList<>();
                                    for (Coup c : lc) {
                                        if (c.getCarte() != null) {
                                            if (!c.getCarte().estDeplacementFouCentre())
                                                tmp.add(c);
                                        } else
                                            tmp.add(c);
                                    }
                                    lc = tmp;
                                } else
                                    fmPouvoirFou = true;
                                for (Coup c : lc) {
                                    if (c.getTypeCoup() == Coup.ACTIVER_POUVOIR_FOU) {
                                        fouSurSorcier = true;
                                        return c;
                                    }
                                }
                            }
                        }
                    }
                    if (coup == null && (pionsDucheAdverse & fou) == fou && (jeu.getTypeCourant().equals(Type.FOU) || jeu.getTypeCourant().equals(Type.IND)) && jeu.getMain(joueurCourant).getNombreTypeCarte(Type.FOU) >= 1) {
                        if (!jeu.getMain(joueurCourant).contientCarte(Carte.FM))
                            coup = choisirCarte(Type.FOU);
                        else {
                            for (Coup c : lc) {
                                if (c.getCarte().estDeplacementFouCentre())
                                    return c;
                            }
                        }
                    }
                    if (coup == null && (pionsDucheAdverse & roi) == roi && (jeu.getTypeCourant().equals(Type.ROI) || jeu.getTypeCourant().equals(Type.IND)) && jeu.getMain(joueurCourant).getNombreTypeCarte(Type.ROI) >= 1) {
                        if (joueurCourant == Jeu.JOUEUR_RGE && posGardeRouge > posRoi + 1 || joueurCourant == Jeu.JOUEUR_VRT && posGardeVert < posRoi - 1)
                            coup = choisirCarte(Type.ROI);
                        else if ((jeu.getTypeCourant().equals(Type.ROI) || jeu.getTypeCourant().equals(Type.IND)) && jeu.getMain(joueurCourant).getNombreTypeCarte(Type.ROI) == 0) {
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
                    if (coup == null && (pionsDucheAdverse & gardeRouge) == gardeRouge && joueurCourant == Jeu.JOUEUR_RGE && (jeu.getTypeCourant().equals(Type.GAR) || jeu.getTypeCourant().equals(Type.IND))) {
                        if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) >= 1) {
                            fouSurGardeRouge = true;
                            coup = choisirCarte(Type.GAR);
                        } else {
                            if (jeu.peutUtiliserPouvoirFou() && jeu.getMain(joueurCourant).getNombreTypeCarte(Type.FOU) > 0) {
                                int dist = distancePionFontaineJoueurCarteFM(Pion.GAR_RGE);
                                if (pouvoirFouSurGarde(retourneDistanceMax(Type.GAR), dist, Pion.GAR_RGE)) {
                                } else
                                    return choisirCarte(nbMAxEnMain());
                                if (dist == 0) {
                                    List<Coup> tmp = new ArrayList<>();
                                    for (Coup c : lc) {
                                        if (c.getCarte() != null) {
                                            if (!c.getCarte().estDeplacementFouCentre())
                                                tmp.add(c);
                                        } else
                                            tmp.add(c);
                                    }
                                    lc = tmp;
                                }
                                for (Coup c : lc) {
                                    if (c.getTypeCoup() == Coup.ACTIVER_POUVOIR_FOU)
                                        return c;
                                }
                            }
                        }
                    }
                    if (coup == null && (pionsDucheAdverse & gardeVert) == gardeVert && joueurCourant == Jeu.JOUEUR_VRT && (jeu.getTypeCourant().equals(Type.GAR) || jeu.getTypeCourant().equals(Type.IND))) {
                        if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) >= 1) {
                            fouSurGardeVert = true;
                            coup = choisirCarte(Type.GAR);
                        } else {
                            if (jeu.peutUtiliserPouvoirFou() && jeu.getMain(joueurCourant).getNombreTypeCarte(Type.FOU) > 0) {
                                int dist = distancePionFontaineJoueurCarteFM(Pion.GAR_VRT);
                                if (pouvoirFouSurGarde(retourneDistanceMax(Type.GAR), dist, Pion.GAR_VRT)) {
                                } else
                                    return choisirCarte(nbMAxEnMain());
                                if (dist == 0) {
                                    List<Coup> tmp = new ArrayList<>();
                                    for (Coup c : lc) {
                                        if (c.getCarte() != null) {
                                            if (!c.getCarte().estDeplacementFouCentre())
                                                tmp.add(c);
                                        } else
                                            tmp.add(c);
                                    }
                                    lc = tmp;
                                }
                                for (Coup c : lc) {
                                    if (c.getTypeCoup() == Coup.ACTIVER_POUVOIR_FOU)
                                        //fouSurGardeVert = true;
                                        return c;
                                }
                            }
                        }

                    }
                    if (coup == null && (pionsDucheAdverse & gardeVert) == gardeVert && joueurCourant == Jeu.JOUEUR_RGE && (jeu.getTypeCourant().equals(Type.GAR) || jeu.getTypeCourant().equals(Type.IND))) { // si garde vert dans chateau
                        if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) == 0) {
                            if (jeu.peutUtiliserPouvoirFou() && jeu.getMain(joueurCourant).getNombreTypeCarte(Type.FOU) > 0) {
                                int dist = distancePionFontaineJoueurCarteFM(Pion.GAR_VRT);
                                if (pouvoirFouSurGarde(retourneDistanceMax(Type.GAR), dist, Pion.GAR_VRT)) {
                                } else
                                    return choisirCarte(nbMAxEnMain());
                                if (dist == 0) {
                                    List<Coup> tmp = new ArrayList<>();
                                    for (Coup c : lc) {
                                        if (c.getCarte() != null) {
                                            if (!c.getCarte().estDeplacementFouCentre())
                                                tmp.add(c);
                                        } else
                                            tmp.add(c);
                                    }
                                    lc = tmp;
                                }
                                for (Coup c : lc) {
                                    if (c.getTypeCoup() == Coup.ACTIVER_POUVOIR_FOU)
                                        //fouSurGardeVert = true;
                                        return c;
                                }
                            }
                        }

                        if (jeu.getMain(joueurCourant).contientCarte(Carte.GC) && (posGardeVert + 4) <= posRoi && (pionsChateau & gardeRouge) != gardeRouge) {
                            for (Coup c : lc) {
                                if (c.getCarte() != null && c.getCarte().estDeplacementGarCentre())
                                    return c;
                            }
                        }
                        if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) == 1 && jeu.getMain(joueurCourant).contientCarte(Carte.GC))
                            coup = retournerCarteType(Type.FIN);
                        else if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) >= 1) {
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
                    }
                    if (coup == null && (pionsDucheAdverse & gardeRouge) == gardeRouge && joueurCourant == Jeu.JOUEUR_VRT && (jeu.getTypeCourant().equals(Type.GAR) || jeu.getTypeCourant().equals(Type.IND))) {
                        if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) == 0) {
                            if (jeu.peutUtiliserPouvoirFou() && jeu.getMain(joueurCourant).getNombreTypeCarte(Type.FOU) > 0) {
                                int dist = distancePionFontaineJoueurCarteFM(Pion.GAR_RGE);
                                if (pouvoirFouSurGarde(retourneDistanceMax(Type.GAR), dist, Pion.GAR_RGE)) {
                                } else
                                    return choisirCarte(nbMAxEnMain());
                                if (dist == 0) {
                                    List<Coup> tmp = new ArrayList<>();
                                    for (Coup c : lc) {
                                        if (c.getCarte() != null) {
                                            if (!c.getCarte().estDeplacementFouCentre())
                                                tmp.add(c);
                                        } else
                                            tmp.add(c);
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
                            for (Coup c : lc) {
                                if (c.getCarte() != null && c.getCarte().estDeplacementGarCentre())
                                    return c;
                            }
                        }
                        if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) == 1 && jeu.getMain(joueurCourant).contientCarte(Carte.GC))
                            coup = retournerCarteType(Type.FIN);
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
                if ((pionsChateauAdverse & sorcier) == sorcier) {
                    if ((jeu.getTypeCourant().equals(Type.SOR) || jeu.getTypeCourant().equals(Type.IND)) && jeu.getMain(joueurCourant).getNombreTypeCarte(Type.SOR) >= 1)
                        coup = choisirCarte(Type.SOR);
                    else if ((jeu.getTypeCourant().equals(Type.SOR) || jeu.getTypeCourant().equals(Type.IND)) && jeu.getMain(joueurCourant).getNombreTypeCarte(Type.SOR) == 0) {
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
                if (coup == null && (pionsChateauAdverse & fou) == fou && (jeu.getTypeCourant().equals(Type.FOU) || jeu.getTypeCourant().equals(Type.IND)) && jeu.getMain(joueurCourant).getNombreTypeCarte(Type.FOU) >= 1) {
                    if (!jeu.getMain(joueurCourant).contientCarte(Carte.FM))
                        coup = choisirCarte(Type.FOU);
                    else {
                        for (Coup c : lc) {
                            if (c.getCarte().estDeplacementFouCentre()) {
                                return c;
                            }
                        }
                    }
                }
                if (coup == null && (pionsChateauAdverse & gardeVert) == gardeVert && joueurCourant == Jeu.JOUEUR_RGE && (jeu.getTypeCourant().equals(Type.GAR) || jeu.getTypeCourant().equals(Type.IND))) {
                    if (jeu.getMain(joueurCourant).contientCarte(Carte.GC) && (posRoi - posGardeVert >= posGardeRouge - posRoi)) {
                        for (Coup c : lc) {
                            if (c.getCarte() != null && c.getCarte().estDeplacementGarCentre())
                                return c;
                        }
                    }

                    if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) == 0 && jeu.getMain(joueurCourant).getNombreTypeCarte(Type.ROI) > 3) {
                        if (posGardeRouge < Plateau.CHATEAU_RGE) {
                            return choisirCarte(Type.ROI);
                        }
                    }
                    if (coup == null && jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) >= 1) {
                        if (retourneDistanceMax(Type.GAR) < nbRoi / 2)
                            coup = choisirCarte(Type.ROI);
                        else {
                            fouSurGardeVert = true;
                            coup = choisirCarte(Type.GAR);
                        }
                    }
                }
                if (coup == null && (pionsChateauAdverse & gardeRouge) == gardeRouge && joueurCourant == Jeu.JOUEUR_VRT && (jeu.getTypeCourant().equals(Type.GAR) || jeu.getTypeCourant().equals(Type.IND))) {
                    if (jeu.getMain(joueurCourant).contientCarte(Carte.GC) && (posRoi - posGardeVert <= posGardeRouge - posRoi)) {
                        for (Coup c : lc) {
                            if (c.getCarte() != null && c.getCarte().estDeplacementGarCentre())
                                return c;
                        }
                    }
                    if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) == 0 && jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) > 3) {
                        if (retourneDistanceMax(Type.GAR) < nbRoi / 2 && posGardeVert > Plateau.CHATEAU_VRT)
                            return choisirCarte(Type.ROI);
                    } else if (jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR) >= 1) {
                        if (retourneDistanceMax(Type.GAR) < nbRoi / 2)
                            coup = choisirCarte(Type.ROI);
                        else {
                            fouSurGardeRouge = true;
                            coup = choisirCarte(Type.GAR);
                        }
                    }
                }
            }
        }

        if (coup != null && coup.getCarte() != null) {
            if (jeu.peutUtiliserPouvoirFou() && !coup.getCarte().getType().equals(Type.GAR)) {
                int distance = 0;
                if (coup.getCarte().getType().equals(Type.ROI)) {
                    if (jeu.getMain(joueurCourant).contientCarte(Carte.FM))
                        distance = distancePionFontaineJoueurCarteFM(Pion.ROI);
                    if (distance > 0 && (pionsSurFontaine & gardeVert) != gardeVert && (pionsSurFontaine & gardeRouge) != gardeRouge)
                        fmPouvoirFou = true;
                    if (distance != 0 && (((pionsSurFontaine & gardeRouge) == gardeRouge) || (pionsSurFontaine & gardeVert) == gardeVert) && jeu.getMain(joueurCourant).contientCarte(Carte.FM)) {
                        List<Coup> tmp = new ArrayList<>();
                        for (Coup c : lc) {
                            if (c.getCarte() != null) {
                                if (!c.getCarte().estDeplacementFouCentre())
                                    tmp.add(c);
                            } else
                                tmp.add(c);
                        }
                        lc = tmp;
                    }
                    if (pouvoirFouSurRoi(retourneDistanceMax(Type.ROI), distance)) {
                        fouSurRoi = true;
                        for (Coup c : lc) {
                            if (c.getTypeCoup() == Coup.ACTIVER_POUVOIR_FOU)
                                return c;
                        }
                    } else
                        return coup;
                } else if (coup.getCarte().getType().equals(Type.SOR)) {
                    if (jeu.getMain(joueurCourant).contientCarte(Carte.FM))
                        distance = distancePionFontaineJoueurCarteFM(Pion.SOR);
                    fouSurSorcier = true;
                    if (distance + retourneDistanceMax(Type.FOU) > retourneDistanceMax(Type.SOR)) {
                        for (Coup c : lc) {
                            if (c.getTypeCoup() == Coup.ACTIVER_POUVOIR_FOU)
                                return c;
                        }
                    } else
                        return coup;
                }
            } else if (jeu.peutUtiliserPouvoirFou() && coup.getCarte().getType().equals(Type.GAR)) {
                int distance = 0;
                if ((gardeVert & pionsChateauAdverse) == gardeVert || (gardeVert & pionsDucheAdverse) == gardeVert) {
                    if (jeu.getMain(joueurCourant).contientCarte(Carte.FM) && (pionsSurFontaine & roi) != roi)
                        distance += Plateau.FONTAINE - jeu.getPlateau().getPositionPion(Pion.GAR_VRT);
                    if (distance != 0 && jeu.getMain(joueurCourant).contientCarte(Carte.FM) && (pionsSurFontaine & roi) == roi ||
                            distance == 0 && jeu.getMain(joueurCourant).contientCarte(Carte.FM)) {
                        List<Coup> tmp = new ArrayList<>();
                        for (Coup c : lc) {
                            if (c.getCarte() != null) {
                                if (!c.getCarte().estDeplacementFouCentre())
                                    tmp.add(c);
                            } else
                                tmp.add(c);
                        }
                        lc = tmp;
                    }
                    if (distance > 0 && (pionsSurFontaine & roi) != roi && jeu.getMain(joueurCourant).contientCarte(Carte.FM))
                        fmPouvoirFou = true;
                    fouSurGardeVert = true;
                } else if ((gardeRouge & pionsChateauAdverse) == gardeRouge || (gardeRouge & pionsDucheAdverse) == gardeRouge) {
                    if (jeu.getMain(joueurCourant).contientCarte(Carte.FM) && (pionsSurFontaine & roi) != roi)
                        distance += Plateau.FONTAINE - jeu.getPlateau().getPositionPion(Pion.GAR_RGE);
                    if ((distance != 0 && jeu.getMain(joueurCourant).contientCarte(Carte.FM) && (pionsSurFontaine & roi) == roi) ||
                            distance == 0 && jeu.getMain(joueurCourant).contientCarte(Carte.FM)) {
                        List<Coup> tmp = new ArrayList<>();
                        for (Coup c : lc) {
                            if (c.getCarte() != null) {
                                if (!c.getCarte().estDeplacementFouCentre()) {
                                    tmp.add(c);
                                }
                            } else
                                tmp.add(c);
                        }
                        lc = tmp;
                    }
                    if (distance > 0 && (pionsSurFontaine & roi) != roi && jeu.getMain(joueurCourant).contientCarte(Carte.FM))
                        fmPouvoirFou = true;
                    fouSurGardeRouge = true;
                }
                if (fouSurGardeVert) {
                    if (pouvoirFouSurGarde(retourneDistanceMax(Type.GAR), distance, Pion.GAR_VRT)) {
                        for (Coup c : lc) {
                            if (c.getTypeCoup() == Coup.ACTIVER_POUVOIR_FOU)
                                return c;
                        }
                    }
                    fouSurGardeVert = false;
                } else if (fouSurGardeRouge) {
                    if (pouvoirFouSurGarde(retourneDistanceMax(Type.GAR), distance, Pion.GAR_RGE)) {
                        for (Coup c : lc) {
                            if (c.getTypeCoup() == Coup.ACTIVER_POUVOIR_FOU)
                                return c;
                        }
                    }
                    fouSurGardeRouge = false;
                }
            }
            if (!coup.getCarte().estDeplacementFouCentre()) {
                if (coup.getCarte().getType().equals(Type.SOR))
                    coup = verifNonDebordement(coup.getCarte().getType(), posSorcier, coup);
                else if (coup.getCarte().getType().equals(Type.FOU) && !jeu.getActivationPouvoirFou())
                    coup = verifNonDebordement(coup.getCarte().getType(), posFou, coup);
            }
        }
        if (coup == null) {
            if ((jeu.getTypeCourant().equals(Type.SOR) && (pionsChateau & sorcier) == sorcier) || (jeu.getTypeCourant().equals(Type.FOU) && (pionsChateau & fou) == fou) || (jeu.getTypeCourant().equals(Type.GAR) && (pionsChateau & gardeRouge) == gardeRouge) || (jeu.getTypeCourant().equals(Type.GAR) && (pionsChateau & gardeVert) == gardeVert)) {
                for (Coup c : lc) {
                    if (c.getTypeCoup() == Coup.FINIR_TOUR)
                        return c;
                }
            }
            if (lc.size() == 1)
                return lc.get(0);
            for (Coup c : lc) {
                if (c.getTypeCoup() == Coup.FINIR_TOUR)
                    return c;
            }
            if (jeu.getTypeCourant().equals(Type.IND))
                return choisirCarte(nbMAxEnMain());
        } else
            return coup;
        return coup;
    }

    private int distancePionFontaineJoueurCarteFM(Pion pion) {
        int distance = 0;
        boolean b = pion.equals(Pion.GAR_RGE) || pion.equals(Pion.GAR_VRT);
        if (jeu.getPlateau().getPositionPion(pion) > Plateau.FONTAINE && joueurCourant == Jeu.JOUEUR_VRT) {
            if (jeu.getMain(joueurCourant).contientCarte(Carte.FM) && b && (pionsSurFontaine & roi) == roi)
                return 0;
            if (jeu.getMain(joueurCourant).contientCarte(Carte.FM) && pion.equals(Pion.ROI) && ((pionsSurFontaine & gardeVert) == gardeVert || (pionsSurFontaine & gardeRouge) == gardeRouge))
                return 0;
            else {
                distance += jeu.getPlateau().getPositionPion(pion) - Plateau.FONTAINE;
                if (pion.equals(Pion.SOR))
                    fmPouvoirFou = true;
            }
        } else if (jeu.getPlateau().getPositionPion(pion) < Plateau.FONTAINE && joueurCourant == Jeu.JOUEUR_RGE) {
            if (jeu.getMain(joueurCourant).contientCarte(Carte.FM) && b && (pionsSurFontaine & roi) == roi)
                return 0;
            if (jeu.getMain(joueurCourant).contientCarte(Carte.FM) && pion.equals(Pion.ROI) && ((pionsSurFontaine & gardeVert) == gardeVert || (pionsSurFontaine & gardeRouge) == gardeRouge))
                return 0;
            else {
                distance += Plateau.FONTAINE - jeu.getPlateau().getPositionPion(pion);
                if (pion.equals(Pion.SOR))
                    fmPouvoirFou = true;
            }
        }
        return distance;
    }

    private boolean pouvoirFouSurRoi(int distanceCarteRoi, int distanceFM) {
        if (joueurCourant == Jeu.JOUEUR_VRT && posRoi > posGardeVert + 1) {
            int distRoiGvert = posRoi - posGardeVert;
            int distanceMaxCumulee = distanceFM;
            int distanceMaxEnUnCoup = distanceFM;
            for (Coup c : lc) {
                if (c.getCarte() != null && c.getCarte().getType().equals(Type.FOU) && !c.getCarte().estDeplacementFouCentre()) {
                    if (c.getCarte().getDeplacement() > distanceMaxEnUnCoup && c.getCarte().getDeplacement() + distanceFM < distRoiGvert)
                        distanceMaxEnUnCoup = c.getCarte().getDeplacement();
                    if (distanceMaxCumulee + c.getCarte().getDeplacement() + distanceFM < distRoiGvert)
                        distanceMaxCumulee += c.getCarte().getDeplacement();
                }
            }
            if (distanceMaxCumulee > distanceCarteRoi && distanceMaxEnUnCoup <= distanceMaxCumulee && distanceMaxCumulee < distRoiGvert)
                return true;
            if (distanceMaxEnUnCoup > distanceCarteRoi && distanceMaxEnUnCoup < distRoiGvert)
                return true;
        } else if (joueurCourant == Jeu.JOUEUR_RGE) {
            int distRoiGrouge = posGardeRouge - posRoi;
            int distanceMaxCumulee = distanceFM;
            int distanceMaxEnUnCoup = distanceFM;
            for (Coup c : lc) {
                if (c.getCarte() != null && c.getCarte().getType().equals(Type.FOU) && !c.getCarte().estDeplacementFouCentre()) {
                    if (c.getCarte().getDeplacement() > distanceMaxEnUnCoup && c.getCarte().getDeplacement() + distanceFM < distRoiGrouge)
                        distanceMaxEnUnCoup = c.getCarte().getDeplacement();
                    if (distanceMaxCumulee + c.getCarte().getDeplacement() + distanceFM > distRoiGrouge)
                        distanceMaxCumulee += c.getCarte().getDeplacement();
                }
            }
            if (distanceMaxCumulee > distanceCarteRoi && distanceMaxEnUnCoup <= distanceMaxCumulee && distanceMaxCumulee < distRoiGrouge)
                return true;
            if (distanceMaxEnUnCoup > distanceCarteRoi && distanceMaxEnUnCoup < distRoiGrouge)
                return true;
        } else
            return false;
        return false;
    }

    private boolean pouvoirFouSurGarde(Pion pionGarde) {
        boolean ok = false;
        for (Coup c : lc) {
            if (c.getCarte() != null && c.getCarte().getType().equals(Type.FOU) && !c.getCarte().estDeplacementFouCentre()) {
                if (jeu.getPlateau().pionEstDeplacable(pionGarde, jeu.getPlateau().getPositionPion(pionGarde) + c.getCarte().getDeplacement() * Jeu.getDirectionJoueur(joueurCourant)))
                    ok = true;
            }
        }
        return ok;
    }

    private boolean pouvoirFouSurGarde(int distanceCarteGarde, int distanceFM, Pion pionGarde) {
        boolean ok = pouvoirFouSurGarde(pionGarde);
        if (!ok) {
            boolean autreGarde = false;
            if (pionGarde == Pion.GAR_RGE) {
                autreGarde = pouvoirFouSurGarde(Pion.GAR_VRT);
                fouSurGardeVert = true;
                fouSurGardeRouge = false;
            }
            if (pionGarde == Pion.GAR_VRT) {
                autreGarde = pouvoirFouSurGarde(Pion.GAR_RGE);
                fouSurGardeRouge = true;
                fouSurGardeVert = false;
            }
            if (!autreGarde)
                return false;
        } else {
            if (pionGarde == Pion.GAR_RGE) {
                fouSurGardeRouge = true;
                fouSurGardeVert = false;
            }
            if (pionGarde == Pion.GAR_VRT) {
                fouSurGardeVert = true;
                fouSurGardeRouge = false;
            }

        }
        if (joueurCourant == Jeu.JOUEUR_VRT && fouSurGardeVert) {
            int distRoiGvert = posGardeVert - Plateau.BORDURE_VRT;
            int distanceMaxCumulee = distanceFM;
            int distanceMaxEnUnCoup = distanceFM;
            for (Coup c : lc) {
                if (c.getCarte() != null && c.getCarte().getType().equals(Type.FOU) && !c.getCarte().estDeplacementFouCentre()) {
                    if (c.getCarte().getDeplacement() > distanceMaxEnUnCoup && c.getCarte().getDeplacement() + distanceFM < distRoiGvert)
                        distanceMaxEnUnCoup = c.getCarte().getDeplacement();
                    if (distanceMaxCumulee + c.getCarte().getDeplacement() + distanceFM < distRoiGvert)
                        distanceMaxCumulee += c.getCarte().getDeplacement();
                }
            }
            if (distanceMaxCumulee > distanceCarteGarde && distanceMaxEnUnCoup <= distanceMaxCumulee && distanceMaxEnUnCoup < distRoiGvert) {
                fouSurGardeVert = true;
                return true;
            }
            if (distanceMaxEnUnCoup > distanceCarteGarde && distanceMaxEnUnCoup < distRoiGvert) {
                fouSurGardeVert = true;
                return true;
            }
        } else if (joueurCourant == Jeu.JOUEUR_VRT && fouSurGardeRouge) {
            int distRoiGrouge = posGardeRouge - posRoi;
            int distanceMaxCumulee = distanceFM;
            int distanceMaxEnUnCoup = distanceFM;
            for (Coup c : lc) {
                if (c.getCarte() != null && c.getCarte().getType().equals(Type.FOU) && !c.getCarte().estDeplacementFouCentre()) {
                    if (c.getCarte().getDeplacement() > distanceMaxEnUnCoup && c.getCarte().getDeplacement() + distanceFM < distRoiGrouge)
                        distanceMaxEnUnCoup = c.getCarte().getDeplacement();
                    if (distanceMaxCumulee + c.getCarte().getDeplacement() + distanceFM < distRoiGrouge)
                        distanceMaxCumulee += c.getCarte().getDeplacement();
                }
            }
            if (distanceMaxCumulee > distanceCarteGarde && distanceMaxEnUnCoup <= distanceMaxCumulee && distanceMaxEnUnCoup < distRoiGrouge) {
                fouSurGardeRouge = true;
                return true;
            }
            if (distanceMaxEnUnCoup > distanceCarteGarde && distanceMaxEnUnCoup < distRoiGrouge) {
                fouSurGardeRouge = true;
                return true;
            }
        } else if (joueurCourant == Jeu.JOUEUR_RGE && fouSurGardeVert) {
            int distRoiGvert = posRoi - posGardeVert;
            int distanceMaxCumulee = distanceFM;
            int distanceMaxEnUnCoup = distanceFM;
            for (Coup c : lc) {
                if (c.getCarte() != null && c.getCarte().getType().equals(Type.FOU) && !c.getCarte().estDeplacementFouCentre()) {
                    if (c.getCarte().getDeplacement() > distanceMaxEnUnCoup && c.getCarte().getDeplacement() + distanceFM < distRoiGvert)
                        distanceMaxEnUnCoup = c.getCarte().getDeplacement();
                    if (posRoi + (distanceMaxCumulee + c.getCarte().getDeplacement() + distanceFM) < distRoiGvert)
                        distanceMaxCumulee += c.getCarte().getDeplacement();
                }
            }
            if (distanceMaxCumulee > distanceCarteGarde && distanceMaxEnUnCoup <= distanceMaxCumulee && distanceMaxEnUnCoup < distRoiGvert) {
                fouSurGardeVert = true;
                return true;
            }
            if (distanceMaxEnUnCoup > distanceCarteGarde && distanceMaxEnUnCoup < distRoiGvert) {
                fouSurGardeVert = true;
                return true;
            }
        } else if (joueurCourant == Jeu.JOUEUR_RGE && fouSurGardeRouge) {
            int distRoiGrouge = Plateau.BORDURE_RGE - posGardeRouge;
            int distanceMaxCumulee = distanceFM;
            int distanceMaxEnUnCoup = distanceFM;
            for (Coup c : lc) {
                if (c.getCarte() != null && c.getCarte().getType().equals(Type.FOU) && !c.getCarte().estDeplacementFouCentre()) {
                    if (c.getCarte().getDeplacement() > distanceMaxEnUnCoup && c.getCarte().getDeplacement() + distanceFM < distRoiGrouge)
                        distanceMaxEnUnCoup = c.getCarte().getDeplacement();
                    if (posRoi + (distanceMaxCumulee + c.getCarte().getDeplacement() + distanceFM) < distRoiGrouge)
                        distanceMaxCumulee += c.getCarte().getDeplacement();
                }
            }
            if (distanceMaxCumulee > distanceCarteGarde && distanceMaxEnUnCoup <= distanceMaxCumulee && distanceMaxEnUnCoup < distRoiGrouge) {
                fouSurGardeRouge = true;
                return true;
            }
            if (distanceMaxEnUnCoup > distanceCarteGarde && distanceMaxEnUnCoup < distRoiGrouge) {
                fouSurGardeRouge = true;
                return true;
            }
        } else
            return false;
        return false;
    }

    //on regarde s'il y a des pieces dans le chateau adverse
    private int pionDansChateau(int joueur) {
        int pions = 0;
        if (joueur != Jeu.JOUEUR_VRT) {
            if (jeu.getPlateau().pionDansChateauVrt(Pion.GAR_VRT))
                pions |= gardeVert;
            if (jeu.getPlateau().pionDansChateauVrt(Pion.SOR))
                pions |= sorcier;
            if (jeu.getPlateau().pionDansChateauVrt(Pion.FOU))
                pions |= fou;
        } else {
            if (jeu.getPlateau().pionDansChateauRge(Pion.GAR_RGE))
                pions |= gardeRouge;
            if (jeu.getPlateau().pionDansChateauRge(Pion.SOR))
                pions |= sorcier;
            if (jeu.getPlateau().pionDansChateauRge(Pion.FOU))
                pions |= fou;
        }
        return pions;
    }

    private int pionsDansDuche(int joueur) {
        int pions = 0;
        if (joueur != Jeu.JOUEUR_VRT) {
            if (jeu.getPlateau().pionDansDucheVrt(Pion.GAR_VRT))
                pions |= gardeVert;
            if (jeu.getPlateau().pionDansDucheVrt(Pion.SOR))
                pions |= sorcier;
            if (jeu.getPlateau().pionDansDucheVrt(Pion.FOU))
                pions |= fou;
            if (jeu.getPlateau().pionDansDucheVrt(Pion.ROI))
                pions |= roi;
            if (jeu.getPlateau().pionDansDucheVrt(Pion.GAR_RGE))
                pions |= gardeRouge;
        } else {
            if (jeu.getPlateau().pionDansDucheRge(Pion.GAR_VRT))
                pions |= gardeVert;
            if (jeu.getPlateau().pionDansDucheRge(Pion.SOR))
                pions |= sorcier;
            if (jeu.getPlateau().pionDansDucheRge(Pion.FOU))
                pions |= fou;
            if (jeu.getPlateau().pionDansDucheRge(Pion.ROI))
                pions |= roi;
            if (jeu.getPlateau().pionDansDucheRge(Pion.GAR_RGE))
                pions |= gardeRouge;
        }
        return pions;
    }

    private int pionsSurFontaine() {
        int pions = 0;
        if (jeu.getPlateau().pionDansFontaine(Pion.ROI))
            pions |= roi;
        if (jeu.getPlateau().pionDansFontaine(Pion.GAR_RGE))
            pions |= gardeRouge;
        if (jeu.getPlateau().pionDansFontaine(Pion.GAR_VRT))
            pions |= gardeVert;
        if (jeu.getPlateau().pionDansFontaine(Pion.SOR))
            pions |= sorcier;
        if (jeu.getPlateau().pionDansFontaine(Pion.FOU))
            pions |= fou;
        return pions;
    }

    private Coup choisirCoupAlea() {
        List<Coup> listeDirectionOk = new ArrayList<>();
        for (Coup c : lc) {
            if (joueurCourant == Jeu.JOUEUR_VRT) {
                if (c.getDirection() == Plateau.DIRECTION_VRT)
                    listeDirectionOk.add(c);
            } else {
                if (c.getDirection() == Plateau.DIRECTION_RGE)
                    listeDirectionOk.add(c);
            }
        }
        if (!listeDirectionOk.isEmpty())
            return listeDirectionOk.get(r.nextInt(listeDirectionOk.size()));
        else {
            if (lc.size() == 0 && !jeu.getTypeCourant().equals(Type.IND))
                return new Coup(Coup.FINIR_TOUR, null, null, Plateau.DIRECTION_IND);
            return lc.get(r.nextInt(lc.size()));
        }
    }

    private Coup choisirCoupAleaCarte() {
        if (lc.size() == 0)
            return new Coup(Coup.FINIR_TOUR, null, null, Plateau.DIRECTION_IND);
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

    private Coup choixPouvoirSorcier() {
        if (joueurCourant == Jeu.JOUEUR_RGE) {
            if ((posSorcier > (posRoi + 3) && posSorcier < posGardeRouge || posSorcier > posGardeRouge + 3) || (posSorcier < posRoi && posSorcier > (posGardeVert + 3))) {
                for (Coup c : lc) {
                    if (c.getTypeCoup() == Coup.ACTIVER_POUVOIR_SOR)
                        return c;
                }
            }
        } else {
            if ((posSorcier < (posRoi - 3) && posSorcier > posGardeVert || posSorcier < posGardeVert - 3) || (posSorcier > posRoi && posSorcier < (posGardeRouge - 3))) {
                for (Coup c : lc) {
                    if (c.getTypeCoup() == Coup.ACTIVER_POUVOIR_SOR)
                        return c;
                }
            }
        }
        return null;
    }

    private Coup choisirCarte(Type type) {
        for (Coup c : lc) {
            if (c.getCarte() != null && c.getCarte().getType().equals(type))
                return c;
        }
        return null;
    }


    private Coup choixCoupFonctionPion(Pion pion) {
        for (Coup c : lc) {
            if (c.getPion().equals(pion))
                return c;
        }
        return null;
    }

    private Coup choisirPion() {
        Coup coup = null;
        if (jeu.getActivationPouvoirFou()) {
            if (fouSurGardeRouge)
                return choixCoupFonctionPion(Pion.GAR_RGE);
            if (fouSurGardeVert)
                return choixCoupFonctionPion(Pion.GAR_VRT);
            if (fouSurRoi)
                return choixCoupFonctionPion(Pion.ROI);
            if (fouSurSorcier)
                return choixCoupFonctionPion(Pion.SOR);
        } else if (gagnantAvecCouronne) {
            switch (joueurCourant) {
                case Jeu.JOUEUR_VRT:
                    for (Coup c : lc) {
                        if (c.getPion().equals(Pion.GAR_VRT))
                            return c;
                    }
                    break;
                case Jeu.JOUEUR_RGE:
                    for (Coup c : lc) {
                        if (c.getPion().equals(Pion.GAR_RGE))
                            return c;
                    }
                    break;
            }
        } else if (jeu.getActivationPouvoirSor()) {
            if (joueurCourant == Jeu.JOUEUR_RGE) {
                if (posSorcier > (posRoi + 3) && posSorcier < posGardeRouge) {
                    for (Coup c : lc) {
                        if (c.getPion().equals(Pion.ROI))
                            return c;
                    }
                } else if (posSorcier > posGardeRouge + 3) {
                    for (Coup c : lc) {
                        if (c.getPion().equals(Pion.GAR_RGE))
                            return c;
                    }
                } else if (posSorcier < posRoi && posGardeVert < (posSorcier + 3)) {
                    for (Coup c : lc) {
                        if (c.getPion().equals(Pion.GAR_VRT))
                            return c;
                    }
                }
            } else {
                if (posSorcier < (posRoi - 3) && posSorcier > posGardeVert) {
                    for (Coup c : lc) {
                        if (c.getPion().equals(Pion.ROI))
                            return c;
                    }
                } else if (posSorcier < posGardeVert - 3) {
                    for (Coup c : lc) {
                        if (c.getPion().equals(Pion.GAR_VRT))
                            return c;
                    }
                } else if (posSorcier > posRoi && posGardeRouge > (posSorcier - 3)) {
                    for (Coup c : lc) {
                        if (c.getPion().equals(Pion.GAR_RGE))
                            return c;
                    }
                }
            }
        } else {
            if (joueurCourant == Jeu.JOUEUR_RGE) {
                if ((pionsChateauAdverse & gardeVert) == gardeVert && posGardeVert < (posRoi - 1)) {
                    for (Coup c : lc) {
                        if (c.getPion().equals(Pion.GAR_VRT))
                            coup = c;
                    }
                } else if (((pionsDucheAdverse & gardeRouge) == gardeRouge || (pionsSurFontaine & gardeRouge) == gardeRouge)) {
                    for (Coup c : lc) {
                        if (c.getPion().equals(Pion.GAR_RGE))
                            coup = c;
                    }
                } else if ((pionsDucheAdverse & gardeVert) == gardeVert && posGardeVert < (posRoi - 1)) {
                    for (Coup c : lc) {
                        if (c.getPion().equals(Pion.GAR_VRT))
                            coup = c;
                    }
                } else if (posRoi > Plateau.FONTAINE && ((pionsDucheAdverse & gardeVert) == gardeVert || ((pionsSurFontaine & gardeVert) == gardeVert) && (posRoi > Plateau.FONTAINE + 1))) {
                    for (Coup c : lc) {
                        if (c.getPion().equals(Pion.GAR_VRT))
                            coup = c;
                    }
                } else if ((pionsDuche & gardeRouge) == gardeRouge && jeu.getPlateau().getPositionPion(Pion.GAR_RGE) != Plateau.BORDURE_RGE) {
                    for (Coup c : lc) {
                        if (c.getPion().equals(Pion.GAR_RGE))
                            coup = c;
                    }
                }
            } else {
                if ((pionsChateauAdverse & gardeRouge) == gardeRouge && posGardeRouge > (posRoi + 1)) {
                    for (Coup c : lc) {
                        if (c.getPion().equals(Pion.GAR_RGE))
                            coup = c;
                    }
                } else if (((pionsDucheAdverse & gardeVert) == gardeVert || (pionsSurFontaine & gardeVert) == gardeVert)) {
                    for (Coup c : lc) {
                        if (c.getPion().equals(Pion.GAR_VRT))
                            coup = c;
                    }
                } else if ((pionsDucheAdverse & gardeRouge) == gardeRouge && posGardeRouge > (posRoi + 1)) {
                    for (Coup c : lc) {
                        if (c.getPion().equals(Pion.GAR_RGE))
                            coup = c;
                    }
                } else if (posRoi < Plateau.FONTAINE && ((pionsDucheAdverse & gardeRouge) == gardeRouge || ((pionsSurFontaine & gardeRouge) == gardeRouge) && posRoi < Plateau.FONTAINE - 1)) {
                    for (Coup c : lc) {
                        if (c.getPion().equals(Pion.GAR_RGE))
                            coup = c;
                    }
                } else if ((pionsDuche & gardeVert) == gardeVert && jeu.getPlateau().getPositionPion(Pion.GAR_VRT) != Plateau.BORDURE_VRT) {
                    for (Coup c : lc) {
                        if (c.getPion().equals(Pion.GAR_VRT))
                            coup = c;
                    }
                }
            }
        }
        if (coup != null)
            return coup;
        return choisirCoupAlea();
    }

    private Coup choisirDirection() {
        if(joueRoiPiocheGagnante && posRoi + nbRoi * Jeu.getDirectionJoueur(joueurCourant) - 1 > posGardeVert && posRoi + nbRoi * Jeu.getDirectionJoueur(joueurCourant) + 1 < posGardeRouge){
            System.out.println("test 1");
            for(Coup c : lc){
                if(c.getDirection() != Plateau.DIRECTION_IND && c.getDirection() == Jeu.getDirectionJoueur(joueurCourant))
                    return c;
            }
        }
        if(joueRoiPiocheGagnante && (posRoi + Jeu.getDirectionJoueur(joueurCourant) == posGardeVert || posRoi + Jeu.getDirectionJoueur(joueurCourant) == posGardeRouge)){
            System.out.println("test 2");
            for(Coup c : lc){
                if(c.getCarte() != null && c.getCarte().equals(Carte.R1))
                    return c;
            }
        }
        Coup coup = null;
        //privilege du roi des 2 cartes roi si possible
        if (jeu.getTypeCourant().equals(Type.ROI))
            coup = jouePrivilegeRoi();
        if (coup != null) {
            return coup;
        }

        if (jeu.getTypeCourant().equals(Type.GAR) && !jeu.getActivationPouvoirFou())
            coup = joueGarde2();

        if (coup != null) {
            return coup;
        }

        int possibilites = nbPossibilites();
        if ((possibilites & 8) == 8) {
            if (joueurCourant == Jeu.JOUEUR_RGE) {
                if (posGardeRouge < Plateau.BORDURE_RGE) {
                    for (Coup c : lc) {
                        if (c.getCarte() != null)
                            coup = c;
                    }
                } else {
                    for (Coup c : lc) {
                        if (c.getDirection() == Plateau.DIRECTION_RGE)
                            coup = c;
                    }
                }
            } else if (joueurCourant == Jeu.JOUEUR_VRT) {
                if (posGardeVert > Plateau.BORDURE_VRT) {
                    for (Coup c : lc) {
                        if (c.getCarte() != null)
                            coup = c;
                    }
                } else {
                    for (Coup c : lc) {
                        if (c.getDirection() == Plateau.DIRECTION_VRT)
                            coup = c;
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
                        if (c.getPion() != null && c.getPion().equals(Pion.GAR_RGE))
                            gVert = true;
                        else if (c.getPion() != null && c.getPion().equals(Pion.GAR_VRT))
                            gRouge = true;
                    }
                    if (gVert && versNous)
                        return coup;
                    else if (gRouge && versNous)
                        return coup;
                    else if (gVert && !versNous) {
                        for (Coup c : lc) {
                            if (c.getPion() != null)
                                return c;
                        }
                    } else if (gRouge && !versNous) {
                        for (Coup c : lc) {
                            if (c.getPion() != null)
                                return c;
                        }
                    }
                } else {
                    for (Coup c : lc) {
                        if (c.getDirection() == Plateau.DIRECTION_RGE)
                            return c;
                    }
                }
            } else {
                if (lc.size() == 2) {
                    for (Coup c : lc) {
                        if (c.getDirection() == Plateau.DIRECTION_VRT) {
                            versNous = true;
                            coup = c;
                        }
                        if (c.getPion() != null && c.getPion().equals(Pion.GAR_RGE))
                            gVert = true;
                        else if (c.getPion() != null && c.getPion().equals(Pion.GAR_VRT))
                            gRouge = true;
                    }
                    if (gRouge && versNous)
                        return coup;
                    else if (gVert && versNous)
                        return coup;
                    else if (gVert && !versNous) {
                        for (Coup c : lc) {
                            if (c.getPion() != null)
                                return c;
                        }
                    } else if (gRouge && !versNous) {
                        for (Coup c : lc) {
                            if (c.getPion() != null)
                                return c;
                        }
                    }
                } else {
                    for (Coup c : lc) {
                        if (c.getDirection() == Plateau.DIRECTION_VRT)
                            return c;
                    }
                }
            }
        }
        if (coup != null)
            return coup;
        return choisirCoupAlea();
    }

    private int nbPossibilites() {
        int possibles = 0;
        for (Coup c : lc) {
            if (c.getDirection() != Plateau.DIRECTION_IND && c.getDirection() == Plateau.DIRECTION_VRT)
                possibles |= 1;
            else if (c.getDirection() != Plateau.DIRECTION_IND && c.getDirection() == Plateau.DIRECTION_RGE)
                possibles |= 2;
            else if (c.getPion() != null)
                possibles |= 4;
            else if (c.getCarte() != null)
                possibles |= 8;
        }
        return possibles;
    }

    private Type carteEnFonctionNombre() {
        if (nbFou >= 5)
            return Type.FOU;
        if (nbSor >= 5)
            return Type.SOR;
        if (nbGarde >= 5)
            return Type.GAR;
        if (nbRoi >= 5)
            return Type.ROI;
        return null;
    }

    Coup jouePrivilegeRoi() {
        Coup coup = null;
        if (jeu.getTypeCourant().equals(Type.ROI)) {
            if (joueurCourant == Jeu.JOUEUR_RGE && posGardeRouge != Plateau.BORDURE_RGE) {
                for (Coup c : lc) {
                    if (c.getCarte() != null && c.getCarte().getType().equals(Type.ROI))
                        coup = c;
                }
            }
            if (joueurCourant == Jeu.JOUEUR_VRT && posGardeVert != Plateau.BORDURE_VRT) {
                for (Coup c : lc) {
                    if (c.getCarte() != null && c.getCarte().getType().equals(Type.ROI))
                        coup = c;
                }
            }
        }
        return coup;
    }

    Coup joueGarde2() {
        Coup coup = null;
        if (joueurCourant == Jeu.JOUEUR_RGE) {
            if (posGardeVert == (posRoi - 1)) {
                for (Coup c : lc) {
                    if (c.getDirection() == Plateau.DIRECTION_RGE) {
                        coup = c;
                        break;
                    }
                }
            }
        } else {
            if (posGardeRouge == (posRoi + 1)) {
                for (Coup c : lc) {
                    if (c.getDirection() == Plateau.DIRECTION_VRT) {
                        coup = c;
                        break;
                    }
                }
            }
        }
        return coup;
    }

    private Coup verifNonDebordement(Type type, int position, Coup coup) {
        if (jeu.getTypeCourant().equals(type) || jeu.getTypeCourant().equals(Type.IND)) {
            if (joueurCourant == Jeu.JOUEUR_RGE && (position + coup.getCarte().getDeplacement()) > Plateau.BORDURE_RGE) {
                lc.remove(coup);
                List<Coup> tmp = new ArrayList<>(lc);
                for (Coup c : tmp) {
                    if (c.getCarte() != null && c.getCarte().getDeplacement() + position > Plateau.BORDURE_RGE)
                        lc.remove(c);
                    if (lc.size() == 0)
                        break;
                }
                coup = null;
            } else if (joueurCourant == Jeu.JOUEUR_VRT && (position - coup.getCarte().getDeplacement()) < Plateau.BORDURE_VRT) {
                lc.remove(coup);
                List<Coup> tmp = new ArrayList<>(lc);
                for (Coup c : tmp) {
                    if (c.getCarte() != null && c.getCarte().getDeplacement() - position < Plateau.BORDURE_VRT)
                        lc.remove(c);
                    if (lc.size() == 0)
                        break;
                }
                coup = null;
            }
        }
        return coup;
    }

    private Coup choisirCartePouvoirDebutTour() {
        Coup coup;
        coup = coupGagnant();
        if (coup != null) {
            return coup;
        }

        coup = choixPouvoirSorcier();
        if (coup != null) {
            return coup;
        }

        Type type = carteEnFonctionNombre();
        if (type != null) {
            if (type.equals(Type.GAR) && jeu.getMain(joueurCourant).contientCarte(Carte.GC)) {
                for (Coup c : lc) {
                    if (c.getCarte() != null && c.getCarte().estDeplacementGarCentre())
                        coup = c;
                }
            } else if (type.equals(Type.FOU) && jeu.getMain(joueurCourant).contientCarte(Carte.FM)) {
                for (Coup c : lc) {
                    if (c.getCarte() != null && c.getCarte().estDeplacementFouCentre())
                        coup = c;
                }
            } else
                coup = choisirCarte(type);
        }
        coup = verifDeGCetFM(coup);
        if (coup == null)
            coup = retournerCarteType(type);

        return coup;
    }

    private Coup coupGagnant() {
        int gagnantRoi;
        gagnantRoi = coupGagnantRoiChateau();
        switch (gagnantRoi) {
            case 1:              //si on doit deplacer roi
                return retournerCarteType(Type.ROI);
            case 8:     //si on doit faire pouvoir sorcier
                if (jeu.getTypeCourant().equals(Type.IND)) {
                    for (Coup c : lc) {
                        if (c.getTypeCoup() == Coup.ACTIVER_POUVOIR_SOR)
                            return c;
                    }
                }
                return null;
            default:
                break;
        }

        int gagnantCouronne = coupGagnantCouronne();
        switch (gagnantCouronne) {
            case 8: //sorcier
                return retournerCarteType(Type.SOR);
            case 2: //garde vert
            case 4: //garde rouge
                gagnantAvecCouronne = true;
                return retournerCarteType(Type.GAR);
            case 16: // fou
                return retournerCarteType(Type.FOU);
            case -1:
                break;
            case -3: // activer pouvoir sorcier
                for (Coup c : lc) {
                    if (c.getTypeCoup() == Coup.ACTIVER_POUVOIR_SOR)
                        return c;
                }
                break;
            default:
                break;
        }

        if (jeu.getPlateau().getFaceCouronne() == Plateau.FACE_PTT_CRN) {
            if (jeu.getPioche().getTaille() <= (Math.max(nbFou, Math.max(nbGarde, Math.max(nbRoi, nbSor))))) {
                if(piocheRoiGagnant()){
                    System.out.println("pioche roi gagnant");
                    defausseCarte = true;
                    if(!joueRoiPiocheGagnante)
                        return retournerCarteType(nbMAxEnMain());
                    else
                        return retournerCarteType(Type.ROI);
                }
            }
        }
        return null;
    }

    private boolean piocheRoiGagnant(){
        if(nbRoi == 0)
            return false;
        if ((pionsDuche & roi) == roi){
            return true;
        }
        if(jeu.getJoueurCourant() == Jeu.JOUEUR_VRT) {
            if(posRoi - nbRoi <= Plateau.FONTAINE && (posRoi - nbRoi > posGardeVert || (posRoi - nbRoi/2 <= Plateau.FONTAINE && posGardeVert + nbRoi / 2 >= Plateau.BORDURE_VRT))){
                joueRoiPiocheGagnante = true;
                return true;
        }
            else
                return false;
        }
        if(jeu.getJoueurCourant() == Jeu.JOUEUR_RGE) {
            if((posRoi + nbRoi >= Plateau.FONTAINE || posRoi + nbRoi / 2 >= Plateau.FONTAINE) && (posRoi + nbRoi < posGardeRouge || (posRoi + nbRoi / 2 >= Plateau.FONTAINE && posGardeRouge + nbRoi / 2 <= Plateau.BORDURE_RGE))){
                System.out.println("pioche gagant roi ");
                joueRoiPiocheGagnante = true;
                return true;
            }
            else
                return false;
        }
        return false;
    }

    private int coupGagnantRoiChateau() {
        int nbCarteRoi = jeu.getMain(joueurCourant).getNombreTypeCarte(Type.ROI);
        if (joueurCourant == Jeu.JOUEUR_VRT) {                //pour le joueur vert
            if (posRoi - nbCarteRoi <= Plateau.CHATEAU_VRT) { // s'il y a assez de cartes pour amener le roi dans le chateau alors
                if (posGardeVert == Plateau.BORDURE_VRT)   //si le garde vert tout a gauche on peut gagner
                    return roi;
                else {                                     //sinon si le garde vert pas tout a gauche, on regarde si avec le privilege on peut l y amener et amer le roi ensuite
                    for (int i = 1; i <= nbCarteRoi / 2 && posGardeVert - i >= Plateau.BORDURE_VRT; i++) {
                        if (posGardeVert - i == Plateau.BORDURE_VRT && posRoi - (nbCarteRoi - i * 2) - i <= Plateau.CHATEAU_VRT)
                            return roi;
                    }
                }
            }
            if (posSorcier == Plateau.CHATEAU_VRT && posGardeVert == Plateau.BORDURE_VRT && jeu.getTypeCourant().equals(Type.IND))
                return sorcier;
        } else if (joueurCourant == Jeu.JOUEUR_RGE) {                //pour le joueur vert
            if (posRoi + nbCarteRoi >= Plateau.CHATEAU_VRT) { // s'il y a assez de cartes pour amener le roi dans le chateau alors
                if (posGardeRouge == Plateau.BORDURE_RGE)   //si le garde rouge tout a la bordure on peut gagner
                    return roi;
                else {                                       //
                    for (int i = 1; i <= nbCarteRoi / 2 && posGardeRouge + i <= Plateau.BORDURE_RGE; i++) {
                        if (posGardeRouge + i == Plateau.BORDURE_RGE && posRoi + (nbCarteRoi - i * 2) + i >= Plateau.CHATEAU_RGE)
                            return roi;
                    }
                }
            }
            if (posSorcier == Plateau.CHATEAU_RGE && posGardeRouge == Plateau.BORDURE_RGE && jeu.getTypeCourant().equals(Type.IND))
                return sorcier;
        }
        return -1;
    }

    private int coupGagnantCouronne() {
        int nbPieceChateau = 0;
        if (((gardeRouge & pionsDuche) == gardeRouge || (gardeRouge & pionsChateau) == gardeRouge) && ((gardeVert & pionsDuche) == gardeVert || (gardeVert & pionsChateau) == gardeVert) && (roi & pionsDuche) == roi)
            nbPieceChateau++;
        boolean f = false;
        boolean s = false;
        boolean gr = false;
        boolean gv = false;
        if ((sorcier & pionsChateau) == sorcier) {
            s = true;
            nbPieceChateau++;
        }
        if ((fou & pionsChateau) == fou) {
            f = true;
            nbPieceChateau++;
        }
        if (joueurCourant == Jeu.JOUEUR_VRT) {
            if ((gardeVert & pionsChateau) == gardeVert) {
                nbPieceChateau++;
                gv = true;
            }
            if (jeu.getPlateau().getPositionCouronne() - nbPieceChateau <= Plateau.CHATEAU_VRT)
                return -1;
            else if (jeu.getPlateau().getPositionCouronne() - (nbPieceChateau + 1) <= Plateau.CHATEAU_VRT) {
                if ((sorcier & pionsDuche) == sorcier && posRoi < posSorcier && ((gardeRouge & pionsDucheAdverse) == gardeRouge || (gardeRouge & pionsChateauAdverse) == gardeRouge))
                    return -3;
                if (!s) {
                    if (peutMettrePionDansChateau(Pion.SOR, Plateau.DIRECTION_VRT))
                        return sorcier;
                }
                if (!f) {
                    if (peutMettrePionDansChateau(Pion.FOU, Plateau.DIRECTION_VRT))
                        return fou;
                }
                if (!gv) {
                    if (peutMettrePionDansChateau(Pion.GAR_VRT, Plateau.DIRECTION_VRT))
                        return gardeVert;
                }
            }
        } else if (joueurCourant == Jeu.JOUEUR_RGE) {
            if ((gardeRouge & pionsChateau) == gardeRouge) {
                nbPieceChateau++;
                gr = true;
            }
            if (jeu.getPlateau().getPositionCouronne() + nbPieceChateau >= Plateau.CHATEAU_RGE)
                return -1;
            else if (jeu.getPlateau().getPositionCouronne() + nbPieceChateau + 1 >= Plateau.CHATEAU_RGE) {
                if ((sorcier & pionsDuche) == sorcier && posRoi > posSorcier && ((gardeVert & pionsDucheAdverse) == gardeVert || (gardeVert & pionsChateauAdverse) == gardeVert || (gardeVert & pionsSurFontaine) == gardeVert))
                    return -3;
                if (!s) {
                    if (peutMettrePionDansChateau(Pion.SOR, Plateau.DIRECTION_RGE))
                        return sorcier;
                }
                if (!f) {
                    if (peutMettrePionDansChateau(Pion.FOU, Plateau.DIRECTION_RGE))
                        return fou;
                }
                if (!gr) {
                    if (peutMettrePionDansChateau(Pion.GAR_RGE, Plateau.DIRECTION_RGE))
                        return gardeRouge;
                }
            }
        }
        return -2;
    }

    private boolean peutMettrePionDansChateau(Pion pion, int direction) {
        switch (pion.toString()) {
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

    private boolean deplacementDansChateau(Type type, int direction, Pion pion) {
        int deplacement = 0;
        for (int i = 0; i < tailleMain; i++) {
            if ((jeu.getMain(joueurCourant).getCarte(i).getDeplacement() != 3 && type.equals(Type.GAR) || jeu.getMain(joueurCourant).getCarte(i).getDeplacement() != 6 && type.equals(Type.FOU)) && jeu.getMain(joueurCourant).getCarte(i).getType().equals(type)) {
                if (((jeu.getPlateau().getPositionPion(pion) + jeu.getMain(joueurCourant).getCarte(i).getDeplacement() * direction) <= Plateau.CHATEAU_VRT &&
                        (jeu.getPlateau().getPositionPion(pion) + jeu.getMain(joueurCourant).getCarte(i).getDeplacement() * direction) >= Plateau.BORDURE_VRT) ||
                        ((jeu.getPlateau().getPositionPion(pion) + jeu.getMain(joueurCourant).getCarte(i).getDeplacement() * direction) >= Plateau.CHATEAU_RGE &&
                                (jeu.getPlateau().getPositionPion(pion) + jeu.getMain(joueurCourant).getCarte(i).getDeplacement() * direction) <= Plateau.BORDURE_RGE))
                    return true;
            }
            if (jeu.getMain(joueurCourant).getCarte(i).getDeplacement() == 6 && jeu.getMain(joueurCourant).getCarte(i).getType().equals(type)) {
                switch (direction) {
                    case Plateau.DIRECTION_VRT:
                        if (posFou - Plateau.FONTAINE > 0)
                            deplacement += (posFou - Plateau.FONTAINE);
                        break;
                    case Plateau.DIRECTION_RGE:
                        if (Plateau.FONTAINE - posFou > 0)
                            deplacement += Plateau.FONTAINE - posFou;
                        break;
                }
            } else if (jeu.getMain(joueurCourant).getCarte(i).getDeplacement() == 3 && jeu.getMain(joueurCourant).getCarte(i).getType().equals(type))
                deplacement += 0;
            else if (jeu.getMain(joueurCourant).getCarte(i).getType().equals(type))
                deplacement += jeu.getMain(joueurCourant).getCarte(i).getDeplacement();
        }
        return ((jeu.getPlateau().getPositionPion(pion) + deplacement * direction) <= Plateau.CHATEAU_VRT ||
                (jeu.getPlateau().getPositionPion(pion) + deplacement * direction) >= Plateau.CHATEAU_RGE) && deplacement > 0;
    }

    private Coup retournerCarteType(Type type) {
        Coup finTour = null;
        for (Coup c : lc) {
            if (c.getCarte() != null && c.getCarte().getType().equals(type))
                return c; // on retourne une carte roi
            if (c.getTypeCoup() == Coup.FINIR_TOUR)
                finTour = c;
        }
        return finTour;
    }

    private Coup verifDeGCetFM(Coup coup) { //on verifie si les cartes sont utilisee a bon escient
        if (coup != null) {
            if (jeu.getTypeCourant().equals(Type.FOU) && coup.getCarte().estDeplacementFouCentre() || jeu.getTypeCourant().equals(Type.IND) && coup.getCarte().estDeplacementFouCentre()) {
                if (joueurCourant == Jeu.JOUEUR_RGE && posFou < Plateau.FONTAINE)
                    return coup;
                if (joueurCourant == Jeu.JOUEUR_VRT && posFou > Plateau.FONTAINE)
                    return coup;
                else { // on supprime FM des possibilites
                    List<Coup> tmp = new ArrayList<>();
                    for (Coup c : lc) {
                        if (c.getCarte() != null) {
                            if (!c.getCarte().estDeplacementFouCentre())
                                tmp.add(c);
                        } else
                            tmp.add(c);
                    }
                    lc = tmp;
                    if ((pionsChateau & fou) == fou && !jeu.getTypeCourant().equals(Type.IND)) {
                        for (Coup c : lc) {
                            if (c.getTypeCoup() == Coup.FINIR_TOUR)
                                return c;
                        }
                    } else if ((pionsDuche & fou) == fou) {
                        for (Coup c : lc) {
                            if (c.getCarte() != null && c.getCarte().getType().equals(Type.FOU) &&
                                    ((posFou + c.getCarte().getDeplacement() * jeu.getSelectionDirections(joueurCourant) >= Plateau.CHATEAU_RGE &&
                                            posFou + c.getCarte().getDeplacement() * jeu.getSelectionDirections(joueurCourant) <= Plateau.BORDURE_RGE) ||
                                            posFou + c.getCarte().getDeplacement() * jeu.getSelectionDirections(joueurCourant) <= Plateau.CHATEAU_VRT &&
                                                    posFou + c.getCarte().getDeplacement() * jeu.getSelectionDirections(joueurCourant) >= Plateau.BORDURE_VRT))
                                return c;

                        }
                    } else {
                        for (Coup c : lc) {
                            if (c.getCarte() != null && c.getCarte().getType().equals(Type.FOU) && (posFou + c.getCarte().getDeplacement() * jeu.getSelectionDirections(joueurCourant) <= Plateau.BORDURE_RGE ||
                                    posFou + c.getCarte().getDeplacement() * jeu.getSelectionDirections(joueurCourant) >= Plateau.BORDURE_VRT))
                                return c;
                        }
                    }
                    coup = null;
                }
            } else if (jeu.getTypeCourant().equals(Type.GAR) && coup.getCarte().estDeplacementGarCentre() || jeu.getTypeCourant().equals(Type.IND) && coup.getCarte().estDeplacementGarCentre()) {
                if (joueurCourant == Jeu.JOUEUR_RGE && ((posRoi - posGardeVert < posGardeRouge - posRoi)) && (pionsChateauAdverse & gardeVert) != gardeVert) {
                    List<Coup> tmp = new ArrayList<>();
                    for (Coup c : lc) {
                        if (c.getCarte() != null) {
                            if (!c.getCarte().estDeplacementGarCentre())
                                tmp.add(c);
                        } else
                            tmp.add(c);
                    }
                    lc = tmp;
                    coup = null;
                } else if (joueurCourant == Jeu.JOUEUR_VRT && ((posRoi - posGardeVert > posGardeRouge - posRoi)) && (pionsChateauAdverse & gardeRouge) != gardeRouge) {
                    List<Coup> tmp = new ArrayList<>();
                    for (Coup c : lc) {
                        if (c.getCarte() != null) {
                            if (!c.getCarte().estDeplacementGarCentre())
                                tmp.add(c);
                        } else
                            tmp.add(c);
                    }
                    lc = tmp;
                    coup = null;
                }
            }
        }
        return coup;
    }

    private int retourneDistanceMax(Type type) {
        int dist = 0;
        for (Coup c : lc) {
            if (c.getCarte() != null && c.getCarte().getType().equals(type)) {
                if (c.getCarte().getDeplacement() == 3 && type.equals(Type.GAR))
                    dist += 2;
                else if (c.getCarte().getDeplacement() == 6 && type.equals(Type.FOU))
                    dist += 0;
                else
                    dist += c.getCarte().getDeplacement();
            }
        }
        return dist;
    }

    private Type nbMAxEnMain() {
        int g = jeu.getMain(joueurCourant).getNombreTypeCarte(Type.GAR);
        int s = jeu.getMain(joueurCourant).getNombreTypeCarte(Type.SOR);
        int f = jeu.getMain(joueurCourant).getNombreTypeCarte(Type.FOU);
        int r = jeu.getMain(joueurCourant).getNombreTypeCarte(Type.ROI);

        int max = Math.max(g, Math.max(s, Math.max(f, r)));

        if (max == s)
            return Type.SOR;
        if (max == r)
            return Type.ROI;
        if (max == g)
            return Type.GAR;
        else
            return Type.FOU;
    }
}
