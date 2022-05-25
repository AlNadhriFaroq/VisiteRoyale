package Controleur;

import Global.Configuration;
import IA.*;
import Modele.*;
import Global.Audios;

import java.util.Random;

public class ControleurMediateur {
    final int lenteurAttente = 10;

    Programme prog;
    IA[] joueursIA;
    int decompte;

    public ControleurMediateur(Programme prog) {
        this.prog = prog;
        joueursIA = new IA[2];
        Audios.setVolume(Audios.getVolume());
    }

    public void clicSouris(int x, int y) {
        System.out.println("Clic souris : (" + x + ", " + y + ")");
    }

    public void tictac() {
        if (decompte == 0) {
            if (prog.getEtat() == Programme.ETAT_EN_JEU && joueursIA[prog.getJeu().getJoueurCourant()] != null) {
                if (prog.getJeu().getEtatJeu() == Jeu.ETAT_CHOIX_JOUEUR) {
                    Random r = new Random();
                    prog.definirJoueurQuiCommence(r.nextInt(2));
                } else if (prog.getJeu().getEtatJeu() != Jeu.ETAT_FIN_DE_PARTIE) {
                    Coup coup = joueursIA[prog.getJeu().getJoueurCourant()].elaborerCoup();
                    jouer(coup);
                }
            }
            decompte = lenteurAttente;
        } else {
            decompte--;
        }
    }

    public void nouvellePartie(boolean joueurVrtEstIA, boolean joueurRgeEstIA) {
        definirJoueurIA(Jeu.JOUEUR_VRT, joueurVrtEstIA);
        definirJoueurIA(Jeu.JOUEUR_RGE, joueurRgeEstIA);
        prog.nouvellePartie(joueurVrtEstIA, joueurRgeEstIA);
        Audios.MUSIQUE_MENUS1.arreter();
    }

    private void definirJoueurIA(int joueur, boolean estIA) {
        if (estIA) {
            String difficulte = Configuration.instance().lire(joueur == Jeu.JOUEUR_VRT ? "NiveauDifficulteIA" : "NiveauDifficulteIA2");
            switch (Integer.parseInt(difficulte)) {
                case IA.DEBUTANT:
                    joueursIA[joueur] = new IAAleatoire(prog.getJeu());
                    break;
                case IA.AMATEUR:
                    joueursIA[joueur] = new IAAleatoirePonderee(prog.getJeu());
                    break;
                case IA.INTERMEDIAIRE:
                    joueursIA[joueur] = new IAStrategie(prog.getJeu());
                    break;
                case IA.PROFESSIONNEL:
                    joueursIA[joueur] = new IAMinMax(prog.getJeu());
                    break;
                case IA.EXPERT:
                    joueursIA[joueur] = new IAStrategie(prog.getJeu());
                    break;
                default:
                    throw new RuntimeException("Controleur.JoueurIA() : Difficulté de l'IA introuvable.");
            }
        } else {
            joueursIA[joueur] = null;
        }
    }


    public void definirJoueurQuiCommence() {
        Random r = new Random();
        prog.definirJoueurQuiCommence(r.nextInt(2));
    }

    public void selectionnerCarte(Carte carte) {
        Coup coup = new Coup(prog.getJeu().getJoueurCourant(), Coup.CHOISIR_CARTE, carte, null, Plateau.DIRECTION_IND);
        jouer(coup);
    }

    public void selectionnerPion(Pion pion) {
        Coup coup = new Coup(prog.getJeu().getJoueurCourant(), Coup.CHOISIR_PION, null, pion, Plateau.DIRECTION_IND);
        jouer(coup);
    }

    public void selectionnerDirection(int direction) {
        Coup coup = new Coup(prog.getJeu().getJoueurCourant(), Coup.CHOISIR_DIRECTION, null, null, direction);
        jouer(coup);
    }

    public void activerPouvoirSor() {
        Coup coup = new Coup(prog.getJeu().getJoueurCourant(), Coup.ACTIVER_POUVOIR_SOR, null, null, Plateau.DIRECTION_IND);
        jouer(coup);
    }

    public void activerPouvoirFou() {
        Coup coup = new Coup(prog.getJeu().getJoueurCourant(), Coup.ACTIVER_POUVOIR_FOU, null, null, Plateau.DIRECTION_IND);
        jouer(coup);
    }

    public void finirTour() {
        Coup coup = new Coup(prog.getJeu().getJoueurCourant(), Coup.FINIR_TOUR, null, null, Plateau.DIRECTION_IND);
        jouer(coup);
    }

    private void jouer(Coup coup) {
        if (coup != null)
            prog.jouerCoup(coup);
        if (prog.getJeu().estTerminee())
            if (prog.getJoueurEstIA(Jeu.JOUEUR_VRT) && !prog.getJoueurEstIA(Jeu.JOUEUR_RGE))
                if (prog.getJeu().getJoueurGagnant() == Jeu.JOUEUR_RGE)
                    Audios.SON_VICTOIRE.jouer();
                else
                    Audios.SON_DEFAITE.jouer();
            else
                Audios.SON_VICTOIRE.jouer();
    }

    public void annuler() {
        prog.annulerCoup();
    }

    public void refaire() {
        prog.refaireCoup();
    }

    public void demarrerProgramme() {
        prog.changerEtat(Programme.ETAT_MENU_PRINCIPAL);
        Audios.MUSIQUE_MENUS1.boucler();
    }

    public void reprendrePartie() {
        prog.changerEtat(Programme.ETAT_EN_JEU);
        Audios.MUSIQUE_MENUS1.arreter();
    }

    public void ouvrirMenuJeu() {
        prog.changerEtat(Programme.ETAT_MENU_JEU);
        Audios.MUSIQUE_MENUS1.boucler();
    }

    public void ouvrirMenuSauvegardes() {
        prog.changerEtat(Programme.ETAT_MENU_SAUVEGARDES);
        Audios.MUSIQUE_MENUS1.boucler();
    }

    public void ouvrirMenuOptions() {
        prog.changerEtat(Programme.ETAT_MENU_OPTIONS);
    }

    public void changerVolume(int changement) {
        if (changement == -1)
            Audios.diminuerVolume();
        else if (changement == 1)
            Audios.augmenterVolume();
        else
            Audios.arreterDemarrer();
    }

    public void ouvrirTutoriel() {
        prog.changerEtat(Programme.ETAT_TUTORIEL);
    }

    public void ouvrirCredits() {
        prog.changerEtat(Programme.ETAT_CREDITS);
    }

    public void retourMenuPrecedant() {
        prog.retourMenuPrecedant();
        Audios.MUSIQUE_MENUS1.boucler();
    }

    public void quitter() {
        prog.quitter();
        System.exit(0);
    }

    public void sauvegarderPartie(int sauvegarde) {
        prog.sauvegarderPartie(sauvegarde);
    }

    public void chargerSauvegarde(int sauvegarde) {
        prog.chargerSauvegarde(sauvegarde);
        definirJoueurIA(Jeu.JOUEUR_VRT, prog.getJoueurEstIA(Jeu.JOUEUR_VRT));
        definirJoueurIA(Jeu.JOUEUR_RGE, prog.getJoueurEstIA(Jeu.JOUEUR_RGE));
    }

    public void supprimerSauvegarde(int sauvegarde) {
        prog.supprimerSauvegarde(sauvegarde);
    }

    public void changerPleinEcran() {
        Configuration.instance().ecrire("PleinEcran", Boolean.toString(!Boolean.parseBoolean(Configuration.instance().lire("PleinEcran"))));
    }

    public void changerDifficulte(int joueur, int difficulte) {
        Configuration.instance().ecrire(joueur == Jeu.JOUEUR_VRT ? "NiveauDifficulteIA" : "NiveauDifficulteIA2", Integer.toString(difficulte));
    }

    public void changerPageTutoriel(int sens) {
        prog.changerPageTutoriel(sens);
    }
}
