package Controleur;

import Modele.*;
import Vue.*;

import java.util.Random;

public class ControleurMediateur {
    final int lenteurAttente = 50;

    Jeu jeu;
    InterfaceUtilisateur vue;
    Joueur[] joueurs;
    int[] difficultes;
    int decompte;


    public ControleurMediateur(Jeu jeu) {
        this.jeu = jeu;
        joueurs = new Joueur[2];
        joueurs[0] = new JoueurHumain(Jeu.JOUEUR_VRT, jeu);
        joueurs[1] = new JoueurHumain(Jeu.JOUEUR_RGE, jeu);
    }

    public void ajouterInterfaceUtilisateur(InterfaceUtilisateur vue) {
        this.vue = vue;
    }

    public void executerCommande(String cmd) {
        Coup coup;

        switch (cmd) {
            case "nouvelle partie":
                nouvellePartie();
                break;
            case "gauche":
            case "droite":
                definirJoueurQuiCommence();
                break;
            case "annuler":
                annuler();
                break;
            case "refaire":
                refaire();
                break;
            case "quitter":
                System.exit(0);
                break;
            default :
                int typeCoup;
                int[][] pionDepDir = new int[3][2];
                Carte[] cartes = new Carte[2];

                String[] cmds = cmd.split(" ");
                typeCoup = Integer.parseInt(cmds[0]);
                cartes[0] = Paquet.texteEnCarte(cmds[1]);
                for (int i = 2; i < cmds.length; i++)
                    pionDepDir[i - 2][0] = Integer.parseInt(cmds[i]);

                if ((coup = jeu.creerCoup(typeCoup, cartes, pionDepDir[0], pionDepDir[1], pionDepDir[2])) != null) {
                    coup.fixerJeu(jeu);
                    jouer(coup);
                    if (jeu.estTerminee()) {
                        System.out.println(Jeu.joueurEnTexte(jeu.getJoueurGagnant()) + " a gagné !");
                        nouvellePartie();
                    }
                else
                    System.out.println("Coup non autorisé");
                break;
            }
        }
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
                break;
        }
    }

    public void clicSouris(int x, int y) {
        //
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
        if (coup != null) {
            jeu.jouerCoup(coup);
        }
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

    public void basculerPleinEcran() {
        vue.basculerPleinEcran();
    }

    public void nouvellePartie() {
        jeu.nouvellePartie();
    }

    public void definirJoueurQuiCommence() {
        Random r = new Random();
        jeu.definirJoueurQuiCommence(r.nextInt(2));
    }
}
