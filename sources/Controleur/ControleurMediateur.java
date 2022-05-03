package Controleur;

import Modele.*;
import Vue.InterfaceGraphique;

public class ControleurMediateur {
    final int lenteurAttente = 50;

    Jeu jeu;
    InterfaceGraphique vue;
    Joueur[] joueurs;
    int[] difficultes;
    int decompte;


    public ControleurMediateur(Jeu jeu) {
        this.jeu = jeu;
    }

    public void ajouterInterfaceGraphique(InterfaceGraphique vue) {
        this.vue = vue;
    }

    public void toucheClavier(String touche) {
        switch (touche) {
            case "Annuler":
                annuler();
                break;
            case "Refaire":
                refaire();
                break;
            case "PleinEcran":
                basculerPleinEcran();
                break;
            case "NouvellePartie":
                nouvellePartie();
                break;
            case "Quitter":
                System.exit(0);
                break;
            default:
                System.out.println("Touche inconnue : " + touche);
        }
    }

    public void clicSouris(int x, int y) {
        return;
    }

    public void tictac() {
        if (!jeu.estTerminee()) {
            if (decompte == 0) {
                joueurs[jeu.joueurCourant()].tempsEcoule();
                decompte = lenteurAttente;
            } else {
                decompte--;
            }
        }
    }

    public void jouer(Coup coup) {
        if (coup != null) {
            jeu.jouerCoup(coup);
        }
    }

    public void annuler() {
        if (jeu.peutAnnuler()) {
            Coup coup = jeu.annulerCoup();
        }
    }

    public void refaire() {
        if (jeu.peutRefaire()) {
            Coup coup = jeu.refaireCoup();
        }
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

    public void basculerPleinEcran() {
        vue.basculerPleinEcran();
    }

    public void nouvellePartie() {
        jeu.nouvellePartie();
    }
}
