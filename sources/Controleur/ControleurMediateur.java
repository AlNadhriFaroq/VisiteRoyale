package Controleur;

import Modele.*;

import java.util.Random;

public class ControleurMediateur {
    final int lenteurAttente = 10;

    Jeu jeu;
    Joueur[] joueurs;
    int[] difficultes;
    int decompte;

    public ControleurMediateur(Jeu jeu) {
        this.jeu = jeu;
        joueurs = new Joueur[2];
        joueurs[0] = new JoueurHumain(Jeu.JOUEUR_VRT, jeu);
        //joueurs[0] = new JoueurIA(Jeu.JOUEUR_VRT, jeu, 0);
        joueurs[1] = new JoueurHumain(Jeu.JOUEUR_RGE, jeu);
        //joueurs[1] = new JoueurIA(Jeu.JOUEUR_RGE, jeu, 0);
    }

    public void clicSouris(int x, int y) {
        System.out.println("Clic souris : (" + x + ", " + y + ")");
    }

    public void tictac() {
        if (jeu.getJoueurCourant() != Jeu.JOUEUR_IND && !jeu.estTerminee())
            if (decompte == 0) {
                joueurs[jeu.getJoueurCourant()].tempsEcoule();
                decompte = lenteurAttente;
            } else {
                decompte--;
            }
    }

    public void definirJoueurQuiCommence() {
        Random r = new Random();
        jeu.definirJoueurQuiCommence(r.nextInt(2));
    }

    public void selectionnerCarte(Carte carte) {
        Coup coup = new Coup(jeu.getJoueurCourant(), Coup.CHOISIR_CARTE, carte, null, Plateau.DIRECTION_IND);
        jouer(coup);
    }

    public void selectionnerPion(Pion pion) {
        Coup coup = new Coup(jeu.getJoueurCourant(), Coup.CHOISIR_PION, null, pion, Plateau.DIRECTION_IND);
        jouer(coup);
    }

    public void selectionnerDirection(int direction) {
        Coup coup = new Coup(jeu.getJoueurCourant(), Coup.CHOISIR_DIRECTION, null, null, direction);
        jouer(coup);
    }

    public void activerPouvoirSor() {
        Coup coup = new Coup(jeu.getJoueurCourant(), Coup.ACTIVER_POUVOIR_SOR, null, null, Plateau.DIRECTION_IND);
        jouer(coup);
    }

    public void activerPouvoirFou() {
        Coup coup = new Coup(jeu.getJoueurCourant(), Coup.ACTIVER_POUVOIR_FOU, null, null, Plateau.DIRECTION_IND);
        jouer(coup);
    }

    public void finirTour() {
        Coup coup = new Coup(jeu.getJoueurCourant(), Coup.FINIR_TOUR, null, null, Plateau.DIRECTION_IND);
        jouer(coup);
    }

    public void jouer(Coup coup) {
        if (coup != null)
            joueurs[jeu.getJoueurCourant()].jouer(coup);
    }

    public void annuler() {
        if (jeu.peutAnnuler())
            jeu.annulerCoup();
    }

    public void refaire() {
        if (jeu.peutRefaire())
            jeu.refaireCoup();
    }

    public void basculerIA(int joueur, boolean type) {
        if (type)
            joueurs[joueur] = new JoueurIA(joueur, jeu, difficultes[joueur]);
        else
            joueurs[joueur] = new JoueurHumain(joueur, jeu);
    }

    public void changerDifficulte(int joueur, int difficulte) {
        difficultes[joueur] = difficulte;
    }

    public void nouvellePartie() {
        jeu.nouvellePartie();
    }

    public void quitter() {
        System.exit(0);
    }
}
