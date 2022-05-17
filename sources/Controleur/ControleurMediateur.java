package Controleur;

import Global.Configuration;
import IA.*;
import Modele.*;
import Vue.Audio;

import java.io.*;
import java.util.Random;

public class ControleurMediateur {
    final int lenteurAttente = 10;

    Programme prog;
    IA[] joueursIA;
    int decompte;
    Audio audio;

    public ControleurMediateur(Programme prog) {
        this.prog = prog;
        joueursIA = new IA[2];
        audio = new Audio();
        audio.boucler(Audio.MUSIQUE_MENUS1);
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
        audio.arreter(Audio.MUSIQUE_MENUS1);
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
                    joueursIA[joueur] = new IAStrategie1_1(prog.getJeu());
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
            audio.jouer(Audio.SON_VICTOIRE);
    }

    public void annuler() {
        if (prog.getJeu().peutAnnuler())
            prog.annulerCoup();
    }

    public void refaire() {
        if (prog.getJeu().peutRefaire())
            prog.refaireCoup();
    }

    public void reprendrePartie() {
        prog.changerEtat(Programme.ETAT_EN_JEU);
        audio.arreter(Audio.MUSIQUE_MENUS1);
    }

    public void ouvrirMenuJeu() {
        prog.changerEtat(Programme.ETAT_MENU_JEU);
        audio.boucler(Audio.MUSIQUE_MENUS1);
    }

    public void ouvrirMenuSauvegardes() {
        prog.changerEtat(Programme.ETAT_MENU_SAUVEGARDES);
        audio.boucler(Audio.MUSIQUE_MENUS1);
    }

    public void ouvrirMenuOptions() {
        prog.changerEtat(Programme.ETAT_MENU_OPTIONS);
    }

    public void ouvrirTutoriel() {
        prog.changerEtat(Programme.ETAT_TUTORIEL);
    }

    public void ouvrirCredits() {
        prog.changerEtat(Programme.ETAT_CREDITS);
    }

    public void retourMenuPrecedant() {
        prog.retourMenuPrecedant();
        audio.boucler(Audio.MUSIQUE_MENUS1);
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
    }

    public void supprimerSauvegarde(int sauvegarde) {
        prog.supprimerSauvegarde(sauvegarde);
    }

    public void changerDifficulte(int joueur, int difficulte) {
        Configuration.instance().ecrire(joueur == Jeu.JOUEUR_VRT ? "NiveauDifficulteIA" : "NiveauDifficulteIA2", Integer.toString(difficulte));
    }

    public void changerPageTutoriel(int sens) {
        prog.changerPageTutoriel(sens);
    }
}
