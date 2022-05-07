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
        joueurs[0] = new JoueurHumain(Jeu.JOUEUR_VRT, jeu);
        joueurs[1] = new JoueurHumain(Jeu.JOUEUR_RGE, jeu);
    }

    public void executerCommande(String cmd) {
        if (cmd.toLowerCase().startsWith("quitter"))
            System.exit(0);

        switch (jeu.getEtatJeu()) {
            case Jeu.ETAT_CHOIX_JOUEUR:
                if (cmd.toLowerCase().startsWith("g") ||
                    cmd.toLowerCase().startsWith("d") ||
                    cmd.toLowerCase().startsWith("gauche") ||
                    cmd.toLowerCase().startsWith("droite") ||
                    cmd.toLowerCase().startsWith("main gauche") ||
                    cmd.toLowerCase().startsWith("main droite")) {
                    definirJoueurQuiCommence();
                } else if (cmd.toLowerCase().startsWith("aide")) {
                    System.out.println("Gauche  : Choisir la main de gauche.");
                    System.out.println("Droite  : Choisir la main de droite.");
                    System.out.println("Aide    : Afficher cette aide.");
                    System.out.println("Quitter : Quitter le programme.");
                }
                break;

            case Jeu.ETAT_EN_JEU:
                String[] cmds = cmd.split(" ");
                Coup coup;
                int typeCoup = -1;
                Carte[] cartes = new Carte[2];
                Pion[] pions = new Pion[2];
                int[] destinations = new int[2];

                if (cmd.toLowerCase().startsWith("aide")) {
                    System.out.println("<C> <D>                 : Déplacé le pion indiqué par la carte C dans la direction D.");
                    System.out.println("G1 <G> <D>              : Déplacer le Garde G d'une case dans la direction D.");
                    System.out.println("G2 <G> <D>              : Déplacer le Garde G de deux cases dans la direction D.");
                    System.out.println("G2 <G> <D> <G> <D>      : Déplacer les Gardes G d'une case respectivement dans les directions D données.");
                    System.out.println("GC                      : Déplacer les Gardes sur les cases adjacentes au Roi.");
                    System.out.println("FM                      : Déplacer le Fou sur la Fontaine.");
                    System.out.println("Privilege roi <D>       : Jouer le privilège du Roi, déplacer la Cour dans la direction D.");
                    System.out.println("Pouvoir sorcier <P>     : Jouer le pouvoir du Sorcier, déplacer le pion P à la position du Sorcier.");
                    System.out.println("Pouvoir fou FM <P>      : Jouer le pouvoir du Fou, déplacer le pion P sur la Fontaine.");
                    System.out.println("Pouvoir fou <C> <P> <D> : Jouer le pouvoir du Fou, déplacer le pion P dans la direction D.");
                    System.out.println("Fin de tour             : Finir son tour.");
                    System.out.println("Annuler                 : Annuler le dernier coup joué.");
                    System.out.println("Refaire                 : Refaire le dernier coup joué.");
                    System.out.println("Nouvelle partie         : Lancer une nouvelle partie.");
                    System.out.println("Aide                    : Afficher cette aide.");
                    System.out.println("Quitter                 : Quitter le programme.");
                    break;
                } else if (cmd.toLowerCase().startsWith("nouvelle partie")) {
                    nouvellePartie();
                    break;
                } else if (cmd.toLowerCase().startsWith("annuler")) {
                    annuler();
                    break;
                } else if (cmd.toLowerCase().startsWith("refaire")) {
                    refaire();
                    break;
                } else if (cmd.toLowerCase().startsWith("fin de tour")) {
                    typeCoup = Coup.FIN_TOUR;
                } else if (cmd.toLowerCase().startsWith("privilege roi ") && cmds.length > 2) {
                    typeCoup = Coup.PRIVILEGE_ROI;
                    cartes[0] = Carte.texteEnCarte("R1");
                    cartes[1] = Carte.texteEnCarte("R1");
                    destinations[0] = Plateau.texteEnDirection(cmds[2]);
                } else if (cmd.toLowerCase().startsWith("pouvoir sorcier ") && cmds.length > 2) {
                    typeCoup = Coup.POUVOIR_SOR;
                    pions[0] = Pion.texteEnPion(cmds[2]);
                } else if (cmd.toLowerCase().startsWith("pouvoir fou ") && cmds.length > 4) {
                    typeCoup = Coup.POUVOIR_FOU;
                    cartes[0] = Carte.texteEnCarte(cmds[2]);
                    pions[0] = Pion.texteEnPion(cmds[3]);
                    if (!cartes[0].estDeplacementFouCentre())
                        destinations[0] = jeu.getPlateau().getPositionPion(pions[0]) + Plateau.texteEnDirection(cmds[4]) * cartes[0].getDeplacement();
                } else if (cmd.startsWith("GC") || cmd.startsWith("FM")) {
                    typeCoup = Coup.DEPLACEMENT;
                    cartes[0] = Carte.texteEnCarte(cmds[0]);
                } else if (cmd.startsWith("G2 ") && cmds.length > 4) {
                    typeCoup = Coup.DEPLACEMENT;
                    cartes[0] = Carte.texteEnCarte(cmds[0]);
                    pions[0] = Pion.texteEnPion(cmds[1]);
                    destinations[0] = jeu.getPlateau().getPositionPion(pions[0]) + Plateau.texteEnDirection(cmds[2]);
                    pions[1] = Pion.texteEnPion(cmds[3]);
                    destinations[1] = jeu.getPlateau().getPositionPion(pions[1]) + Plateau.texteEnDirection(cmds[4]);
                } else if (cmd.startsWith("G2 ") && cmds.length > 2) {
                    typeCoup = Coup.DEPLACEMENT;
                    cartes[0] = Carte.texteEnCarte(cmds[0]);
                    pions[0] = Pion.texteEnPion(cmds[1]);
                    destinations[0] = jeu.getPlateau().getPositionPion(pions[0]) + Plateau.texteEnDirection(cmds[2]) * 2;
                } else if (cmd.startsWith("G1 ") && cmds.length > 2) {
                    typeCoup = Coup.DEPLACEMENT;
                    cartes[0] = Carte.texteEnCarte(cmds[0]);
                    pions[0] = Pion.texteEnPion(cmds[1]);
                    destinations[0] = jeu.getPlateau().getPositionPion(pions[0]) + Plateau.texteEnDirection(cmds[2]);
                } else if (cmd.startsWith("R1 ") && cmds.length > 1) {
                    typeCoup = Coup.DEPLACEMENT;
                    cartes[0] = Carte.texteEnCarte(cmds[0]);
                    pions[0] = Pion.ROI;
                    destinations[0] = jeu.getPlateau().getPositionPion(Pion.ROI) + Plateau.texteEnDirection(cmds[1]);
                } else if (cmd.charAt(0) == 'S' && cmds.length > 1) {
                    typeCoup = Coup.DEPLACEMENT;
                    cartes[0] = Carte.texteEnCarte(cmds[0]);
                    pions[0] = Pion.SOR;
                    destinations[0] = jeu.getPlateau().getPositionPion(Pion.SOR) + Plateau.texteEnDirection(cmds[1]) * cartes[0].getDeplacement();
                } else if (cmd.charAt(0) == 'F' && cmds.length > 1) {
                    typeCoup = Coup.DEPLACEMENT;
                    cartes[0] = Carte.texteEnCarte(cmds[0]);
                    pions[0] = Pion.FOU;
                    destinations[0] = jeu.getPlateau().getPositionPion(Pion.FOU) + Plateau.texteEnDirection(cmds[1]) * cartes[0].getDeplacement();
                } else {
                    break;
                }

                if (typeCoup != -1 && (coup = jeu.creerCoup(typeCoup, cartes, pions, destinations)) != null) {
                    coup.fixerJeu(jeu);
                    jouer(coup);
                    if (jeu.estTerminee()) {
                        System.out.println(Jeu.joueurEnTexte(jeu.getJoueurGagnant()) + " a gagné !");
                        nouvellePartie();
                    }
                } else {
                    System.out.println("Coup non autorisé");
                }
                break;

            case Jeu.ETAT_GAME_OVER:
                if (cmd.toLowerCase().startsWith("nouvelle partie")) {
                    nouvellePartie();
                } else if (cmd.toLowerCase().startsWith("aide")) {
                    System.out.println("Nouvelle partie : Lancer une nouvelle partie.");
                    System.out.println("Aide            : Afficher cette aide.");
                    System.out.println("Quitter         : Quitter le programme.");
                }
                break;

            default:
                break;
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
        //vue.basculerPleinEcran();
    }

    public void nouvellePartie() {
        jeu.nouvellePartie();
    }

    public void definirJoueurQuiCommence() {
        Random r = new Random();
        jeu.definirJoueurQuiCommence(r.nextInt(2));
    }
}
