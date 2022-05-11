package Controleur;

import Modele.*;

import javax.swing.*;
import java.util.*;

class IAAleatoire extends IA {
    Random r;
    int joueur;
    int tailleMain;
    int typeCoup;
    Carte carte;
    Pion pion;
    int direction;

    IAAleatoire(Jeu jeu) {
        super(jeu);
        r = new Random();
        joueur = jeu.getJoueurCourant();
    }
/*
    @Override
    Coup calculerCoup() {
        tailleMain = jeu.getMain(joueur).getTaille();
        cartes = new Carte[2];
        pions = new Pion[2];
        destinations = new int[2];
        Coup coup;
        if(typeCoup == Coup.POUVOIR_SOR) {
            typeCoup = Coup.FIN_TOUR;
            coup = jeu.creerCoup(typeCoup, cartes, pions, destinations);
        }
        else{
            do {
                System.out.println("IA joue");
                choisirTypeCoup();
                System.out.println("Type coup est : " + typeCoup);
                choisirCartesDirections();
                System.out.println("Type coup = " + typeCoup);
                System.out.println("cartes = " + cartes[0]);
                System.out.println("Pions = " + pions[0]);
                System.out.println("Destination = " + destinations[0]);
                coup = jeu.creerCoup(typeCoup, cartes, pions, destinations);
            } while (coup == null);
        }
        return coup;
    }

    private void choisirTypeCoup(){
        do {
            typeCoup = r.nextInt(10);
            if(typeCoup <= 5)
                typeCoup = 0;
            else
                typeCoup -= 5;
            //typeCoup = 2;
            System.out.println("type coup choix : " + typeCoup);
        } while (!typeCoupEstCorrect());
    }

    private boolean typeCoupEstCorrect() {
        boolean possible;
        switch (typeCoup) {
            case Coup.DEPLACEMENT:
                possible = jeu.peutDeplacer();
                this.typeCoup = Coup.DEPLACEMENT;
                System.out.println("possible = " + possible);
                break;
            case Coup.PRIVILEGE_ROI:
                possible = jeu.peutUtiliserPrivilegeRoi();
                this.typeCoup = Coup.PRIVILEGE_ROI;
                break;
            case Coup.POUVOIR_SOR:
                possible = jeu.peutUtiliserPouvoirSorcier();
                System.out.println("possible = " + possible);
                this.typeCoup = Coup.POUVOIR_SOR;
                break;
            case Coup.POUVOIR_FOU:
                possible = jeu.peutUtiliserPouvoirFou();
                System.out.println("possible = " + possible);
                this.typeCoup = Coup.POUVOIR_FOU;
                break;
            case Coup.FIN_TOUR:
                possible = jeu.peutFinirTour();
                this.typeCoup = Coup.FIN_TOUR;
                break;
            default:
                possible = false;
                break;
            }
        return possible;
    }

    private void choisirCartesDirections() {
        switch(typeCoup) {
            case Coup.DEPLACEMENT:
                System.out.println("on va aller dans choisir carte deplacements");
                choisirCartesDeplacement();
                break;
            case Coup.PRIVILEGE_ROI:
                choisirCartesPrivilegesRoi();
                break;
            case Coup.POUVOIR_SOR:
                choisirCartePouvoirSorcier();
                break;
            case Coup.POUVOIR_FOU:
                choisirCartesPouvoirFou();
                break;
            default:
                break;
        }
    }

    private void choisirCartesDeplacement() {
        int direction;
        Carte carte;
        Pion pion = null;
        boolean carteSpeciale = false;
        do{
            //System.out.println("boucle infinie ?");
            carte = choisirCartes();
            //System.out.println("carte = " + carte);
            if (carte.estDeplacementGarCentre() || carte.estDeplacementFouCentre())
                carteSpeciale = true;
            else if (carte.estDeplacementGar1Plus1())
                carteSpeciale = estCarteSpeciale(carte);
            direction = choisirDirections();

            if (carte.getType().equals(Type.ROI))
                pion = Pion.ROI;
            if (carte.getType().equals(Type.GAR))
                if (r.nextBoolean())
                    pion = Pion.GAR_VRT;
                else
                    pion = Pion.GAR_RGE;
            else if (carte.getType().equals(Type.SOR))
                pion = Pion.SOR;
            else if (carte.getType().equals(Type.FOU))
                pion = Pion.FOU;
            //System.out.println("pion est " + pion);
        } while (!carteSpeciale && !jeu.getPlateau().pionEstDeplacable(pion, jeu.getPlateau().getPositionPion(pion) + carte.getDeplacement() * direction));

        if (!carteSpeciale) {
            //System.out.println("pas une carte speciale");
            cartes[0] = carte;
            pions[0] = pion;
            destinations[0] = jeu.getPlateau().getPositionPion(pion) + direction * carte.getDeplacement();
        }
    }

    private Carte choisirCartes() {
        Carte carte;
        do {
            carte = jeu.getMain(joueur).getCarte(r.nextInt(tailleMain));
        } while (!jeu.peutUtiliserCarte(carte));
        return carte;
    }

    private Pion choisirPion() {
        return Pion.valeurEnPion(r.nextInt(5));
    }

    private int choisirDirections() {
        if (r.nextInt(100)+1 > 80)
            return -Jeu.getDirectionJoueur(joueur);
        else
            return Jeu.getDirectionJoueur(joueur);
    }

    private void choisirCartesPrivilegesRoi() {
        int cartesRoi = 0;
        for (int i = 0; cartesRoi < 2; i++) {
            if (jeu.getMain(joueur).getCarte(i).getType().equals(Type.ROI)) {
                cartes[cartesRoi] = jeu.getMain(joueur).getCarte(i);
                cartesRoi ++;
            }
        }
        int direction;
        do {
            direction = choisirDirections();
        } while (!jeu.peutUtiliserPrivilegeRoi(cartes, direction));
        destinations[0] = direction;
    }

    private void choisirCartePouvoirSorcier() {
        Pion pion;
        do {
            pion = choisirPion();
        } while (!jeu.peutUtiliserPouvoirSorcier(pion));
        pions[0] = pion;
    }

    private void choisirCartesPouvoirFou() {
        int direction;
        Carte carte;
        Pion pion;
        boolean milieu = false;

        do {
            System.out.println("boucle ok");
            carte = choisirCartes();
            System.out.println("Carte = " + carte);
            direction = choisirDirections();
            System.out.println("direction = " + direction);
            if (jeu.getTypeCourant() == Type.GAR)
                pion = Pion.valeurEnPion(r.nextInt(2) + 1);
            else
                pion = choisirPion();

            if (carte.estDeplacementFouCentre() && jeu.getPlateau().pionEstDeplacable(pion, Plateau.FONTAINE)) {
                cartes[0] = carte;
                pions[0] = pion;
                milieu = true;
            }
        } while (!milieu && !carte.getType().equals(Type.FOU) && !jeu.getTypeCourant().equals(pion.getType()) &&
                 !jeu.getPlateau().pionEstDeplacable(pion, jeu.getPlateau().getPositionPion(pion) + direction * carte.getDeplacement()));

        if (!milieu) {
            cartes[0] = carte;
            pions[0] = pion;
            destinations[0] = jeu.getPlateau().getPositionPion(pion) + direction * carte.getDeplacement();
        }
    }

    void choixAllEstCarteSpeciale(Carte carte, int d1, int d2){
        cartes[0] = carte;
        pions[0] = Pion.GAR_VRT;
        pions[1] = Pion.GAR_RGE;
        destinations[0] = d1;
        destinations[1] = d2;
    }
    private boolean estCarteSpeciale(Carte carte) {
        boolean ok = false;
        boolean[][] dir = new boolean[2][4];

        if (jeu.getPlateau().pionEstDeplacable(Pion.GAR_VRT, jeu.getPlateau().getPositionPion(Pion.GAR_VRT) + 1))
            dir[0][0] = true;
        if (jeu.getPlateau().pionEstDeplacable(Pion.GAR_VRT, jeu.getPlateau().getPositionPion(Pion.GAR_VRT) - 1))
            dir[0][1] = true;
        if (jeu.getPlateau().pionEstDeplacable(Pion.GAR_VRT, jeu.getPlateau().getPositionPion(Pion.GAR_VRT) + 2))
            dir[0][2] = true;
        if (jeu.getPlateau().pionEstDeplacable(Pion.GAR_VRT, jeu.getPlateau().getPositionPion(Pion.GAR_VRT) - 2))
            dir[0][3] = true;
        if (jeu.getPlateau().pionEstDeplacable(Pion.GAR_RGE, jeu.getPlateau().getPositionPion(Pion.GAR_RGE) + 1))
            dir[1][0] = true;
        if (jeu.getPlateau().pionEstDeplacable(Pion.GAR_RGE, jeu.getPlateau().getPositionPion(Pion.GAR_RGE) - 1))
            dir[1][1] = true;
        if (jeu.getPlateau().pionEstDeplacable(Pion.GAR_RGE, jeu.getPlateau().getPositionPion(Pion.GAR_RGE) + 2))
            dir[1][2] = true;
        if (jeu.getPlateau().pionEstDeplacable(Pion.GAR_RGE, jeu.getPlateau().getPositionPion(Pion.GAR_RGE) - 2))
            dir[1][3] = true;

        int i = r.nextInt(2);
        int j = r.nextInt(4);

        switch (j) {
            case 0:
                if (i == 0) {
                    if (dir[i][j]) {
                        if (dir[1][j]) {
                            choixAllEstCarteSpeciale(carte, jeu.getPlateau().getPositionPion(Pion.GAR_VRT) + 1, jeu.getPlateau().getPositionPion(Pion.GAR_RGE) + 1);
                            ok = true;
                        } else if (dir[1][1]) {
                            choixAllEstCarteSpeciale(carte, jeu.getPlateau().getPositionPion(Pion.GAR_VRT) + 1, jeu.getPlateau().getPositionPion(Pion.GAR_RGE) - 1);
                            ok = true;
                        }
                    }
                } else {
                    if (dir[i][j]) {
                        if (dir[0][j]) {
                            choixAllEstCarteSpeciale(carte, jeu.getPlateau().getPositionPion(Pion.GAR_VRT) + 1, jeu.getPlateau().getPositionPion(Pion.GAR_RGE) + 1);
                            ok = true;
                        } else if (dir[i][1]) {
                            choixAllEstCarteSpeciale(carte, jeu.getPlateau().getPositionPion(Pion.GAR_VRT) - 1, jeu.getPlateau().getPositionPion(Pion.GAR_RGE) + 1);
                            ok = true;
                        }
                    }
                }
            case 1:
                if (i == 0) {
                    if (dir[i][j]) {
                        if (dir[1][j]) {
                            choixAllEstCarteSpeciale(carte, jeu.getPlateau().getPositionPion(Pion.GAR_VRT) - 1, jeu.getPlateau().getPositionPion(Pion.GAR_RGE) - 1);
                            ok = true;
                        } else if (dir[1][0]) {
                            choixAllEstCarteSpeciale(carte, jeu.getPlateau().getPositionPion(Pion.GAR_VRT) - 1, jeu.getPlateau().getPositionPion(Pion.GAR_RGE) + 1);
                            ok = true;
                        }
                    }
                } else {
                    if (dir[i][j]) {
                        if (dir[0][j]) {
                            choixAllEstCarteSpeciale(carte, jeu.getPlateau().getPositionPion(Pion.GAR_VRT) - 1, jeu.getPlateau().getPositionPion(Pion.GAR_RGE) - 1);
                            ok = true;
                        } else if (dir[0][0]) {
                            choixAllEstCarteSpeciale(carte, jeu.getPlateau().getPositionPion(Pion.GAR_VRT) + 1, jeu.getPlateau().getPositionPion(Pion.GAR_RGE) - 1);
                            ok = true;
                        }
                    }
                }
            case 2:
                if (dir[i][j]) {
                    cartes[0] = carte;
                    if (i == 0)
                        pions[0] = Pion.GAR_VRT;
                    else
                        pions[0] = Pion.GAR_RGE;
                    destinations[0] = jeu.getPlateau().getPositionPion(pions[0]) + 2;
                    ok = true;
                }
                break;
            case 3:
                if (dir[i][j]) {
                    cartes[0] = carte;
                    if (i == 0)
                        pions[0] = Pion.GAR_VRT;
                    else
                        pions[0] = Pion.GAR_RGE;
                    destinations[0] = jeu.getPlateau().getPositionPion(pions[0]) - 2;
                    ok = true;
                }
                break;
        }
        return ok;
    }

 */
}
