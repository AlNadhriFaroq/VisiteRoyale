package Controleur;

import Modele.*;

import java.util.Random;

public class ControleurMediateur {
    final int lenteurAttente = 50;

    Jeu jeu;
    Joueur[] joueurs;
    int[] difficultes;
    int decompte;

    public ControleurMediateur(Jeu jeu) {
        this.jeu = jeu;
        joueurs = new Joueur[2];
        joueurs[0] = new JoueurIA(Jeu.JOUEUR_VRT, jeu, IA.FACILE);
        joueurs[1] = new JoueurHumain(Jeu.JOUEUR_RGE, jeu);
    }

    public void clicSouris(int x, int y) {
        System.out.println("Clic souris : (" + x + ", " + y + ")");
    }

    public void tictac() {
        if (!jeu.estTerminee()) {
            if (decompte == 0) {
                joueurs[jeu.getJoueurCourant()].tempsEcoule();
                decompte = lenteurAttente;
            } else {
                decompte--;
            }
        }
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

    public void definirJoueurQuiCommence() {
        Random r = new Random();
        jeu.definirJoueurQuiCommence(r.nextInt(2));
    }
}
