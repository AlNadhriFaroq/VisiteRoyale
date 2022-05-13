package Controleur;

import Global.Configuration;
import Modele.*;

import java.util.Random;

public class ControleurMediateur {
    final int lenteurAttente = 10;

    Programme prog;
    Joueur[] joueurs;
    int[] difficultes;
    int decompte;

    public ControleurMediateur(Programme prog) {
        this.prog = prog;
        joueurs = new Joueur[2];
        difficultes = new int[2];
        difficultes[0] = Integer.parseInt(Configuration.instance().lire("DifficulteIAjoueurVrt"));
        difficultes[1] = Integer.parseInt(Configuration.instance().lire("DifficulteIAjoueurRge"));
    }

    public void clicSouris(int x, int y) {
        System.out.println("Clic souris : (" + x + ", " + y + ")");
    }

    public void tictac() {
        if (prog.getEtat() == Programme.ETAT_EN_JEU && prog.getJeu().getEtatJeu() != Jeu.ETAT_CHOIX_JOUEUR && prog.getJeu().getEtatJeu() != Jeu.ETAT_FIN_DE_PARTIE)
            if (decompte == 0) {
                joueurs[prog.getJeu().getJoueurCourant()].tempsEcoule();
                decompte = lenteurAttente;
            } else {
                decompte--;
            }
    }

    public void nouvellePartie(boolean joueurVrtEstIA, boolean joueurRgeEstIA) {
        definirJoueurIA(Jeu.JOUEUR_VRT, joueurVrtEstIA);
        definirJoueurIA(Jeu.JOUEUR_RGE, joueurRgeEstIA);
        prog.nouvellePartie(joueurVrtEstIA, joueurRgeEstIA);
    }

    private void definirJoueurIA(int joueur, boolean estIA) {
        if (estIA)
            joueurs[joueur] = new JoueurIA(joueur, prog, difficultes[joueur]);
        else
            joueurs[joueur] = new JoueurHumain(joueur, prog);
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

    public void jouer(Coup coup) {
        if (coup != null)
            joueurs[prog.getJeu().getJoueurCourant()].jouer(coup);
    }

    public void annuler() {
        if (prog.getJeu().peutAnnuler())
            prog.annulerCoup();
    }

    public void refaire() {
        if (prog.getJeu().peutRefaire())
            prog.refaireCoup();
    }

    public void changerDifficulte(int joueur, int difficulte) {
        difficultes[joueur] = difficulte;
    }

    public void ouvrirMenuJeu() {
        prog.ouvrirMenuJeu();
    }

    public void reprendrePartie() {
        prog.reprendrePartie();
    }

    public void abandonnerPartie() {
        prog.abandonnerPartie();
    }

    public void ouvrirCredits() {
        prog.ouvrirCredits();
    }

    public void retourMenu() {
        prog.retourMenu();
    }

    public void quitter() {
        System.exit(0);
    }
}
