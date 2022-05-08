package Vue;

import Controleur.ControleurMediateur;
import Modele.*;

import javax.swing.*;
import java.util.Scanner;

public class InterfaceTextuelle extends InterfaceUtilisateur {

    public InterfaceTextuelle(Jeu jeu, ControleurMediateur ctrl) {
        super(jeu, ctrl);
    }

    public void interpreterCommande(String cmd) {
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
                    ctrl.definirJoueurQuiCommence();
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
                int typeCoup;
                Carte[] cartes = new Carte[2];
                Modele.Pion[] pions = new Modele.Pion[2];
                int[] destinations = new int[2];

                if (cmd.equalsIgnoreCase("aide") || cmd.equalsIgnoreCase("help")) {
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
                } else if (cmd.equalsIgnoreCase("nouvelle partie")) {
                    ctrl.nouvellePartie();
                    break;
                } else if (cmd.equalsIgnoreCase("annuler")) {
                    ctrl.annuler();
                    break;
                } else if (cmd.equalsIgnoreCase("refaire")) {
                    ctrl.refaire();
                    break;
                } else if (cmd.equalsIgnoreCase("fin de tour") || cmd.equalsIgnoreCase("fin tour")) {
                    typeCoup = Coup.FIN_TOUR;
                } else if (cmd.toLowerCase().startsWith("privilege roi ") && cmds.length == 3) {
                    typeCoup = Coup.PRIVILEGE_ROI;
                    cartes[0] = Carte.texteEnCarte("R1");
                    cartes[1] = Carte.texteEnCarte("R1");
                    destinations[0] = Plateau.texteEnDirection(cmds[2]);
                } else if (cmd.toLowerCase().startsWith("pouvoir sorcier ") && cmds.length == 3) {
                    typeCoup = Coup.POUVOIR_SOR;
                    pions[0] = Modele.Pion.texteEnPion(cmds[2]);
                } else if (cmd.toLowerCase().startsWith("pouvoir fou ") && cmds.length == 5) {
                    typeCoup = Coup.POUVOIR_FOU;
                    cartes[0] = Carte.texteEnCarte(cmds[2]);
                    pions[0] = Modele.Pion.texteEnPion(cmds[3]);
                    destinations[0] = jeu.getPlateau().getPositionPion(pions[0]) + Plateau.texteEnDirection(cmds[4]) * cartes[0].getDeplacement();
                } else if (cmd.toLowerCase().startsWith("pouvoir fou ") && cmds.length == 4) {
                    typeCoup = Coup.POUVOIR_FOU;
                    cartes[0] = Carte.texteEnCarte(cmds[2]);
                    pions[0] = Modele.Pion.texteEnPion(cmds[3]);
                } else if (cmd.equals("GC") || cmd.equals("FM")) {
                    typeCoup = Coup.DEPLACEMENT;
                    cartes[0] = Carte.texteEnCarte(cmds[0]);
                } else if (cmd.startsWith("G2 ") && cmds.length == 5) {
                    typeCoup = Coup.DEPLACEMENT;
                    cartes[0] = Carte.texteEnCarte(cmds[0]);
                    pions[0] = Modele.Pion.texteEnPion(cmds[1]);
                    destinations[0] = jeu.getPlateau().getPositionPion(pions[0]) + Plateau.texteEnDirection(cmds[2]);
                    pions[1] = Modele.Pion.texteEnPion(cmds[3]);
                    destinations[1] = jeu.getPlateau().getPositionPion(pions[1]) + Plateau.texteEnDirection(cmds[4]);
                } else if (cmd.startsWith("G2 ") && cmds.length == 3) {
                    typeCoup = Coup.DEPLACEMENT;
                    cartes[0] = Carte.texteEnCarte(cmds[0]);
                    pions[0] = Modele.Pion.texteEnPion(cmds[1]);
                    destinations[0] = jeu.getPlateau().getPositionPion(pions[0]) + Plateau.texteEnDirection(cmds[2]) * 2;
                } else if (cmd.startsWith("G1 ") && cmds.length == 3) {
                    typeCoup = Coup.DEPLACEMENT;
                    cartes[0] = Carte.texteEnCarte(cmds[0]);
                    pions[0] = Modele.Pion.texteEnPion(cmds[1]);
                    destinations[0] = jeu.getPlateau().getPositionPion(pions[0]) + Plateau.texteEnDirection(cmds[2]);
                } else if (cmd.startsWith("R1 ") && cmds.length == 2) {
                    typeCoup = Coup.DEPLACEMENT;
                    cartes[0] = Carte.texteEnCarte(cmds[0]);
                    pions[0] = Modele.Pion.ROI;
                    destinations[0] = jeu.getPlateau().getPositionPion(Modele.Pion.ROI) + Plateau.texteEnDirection(cmds[1]);
                } else if (cmd.charAt(0) == 'S' && cmds.length == 2) {
                    typeCoup = Coup.DEPLACEMENT;
                    cartes[0] = Carte.texteEnCarte(cmds[0]);
                    pions[0] = Modele.Pion.SOR;
                    destinations[0] = jeu.getPlateau().getPositionPion(Modele.Pion.SOR) + Plateau.texteEnDirection(cmds[1]) * cartes[0].getDeplacement();
                } else if (cmd.charAt(0) == 'F' && cmds.length == 2) {
                    typeCoup = Coup.DEPLACEMENT;
                    cartes[0] = Carte.texteEnCarte(cmds[0]);
                    pions[0] = Modele.Pion.FOU;
                    destinations[0] = jeu.getPlateau().getPositionPion(Modele.Pion.FOU) + Plateau.texteEnDirection(cmds[1]) * cartes[0].getDeplacement();
                } else {
                    break;
                }

                if ((coup = jeu.creerCoup(typeCoup, cartes, pions, destinations)) != null) {
                    coup.fixerJeu(jeu);
                    ctrl.jouer(coup);
                } else {
                    System.out.println("Coup non autorisé");
                }
                break;

            case Jeu.ETAT_GAME_OVER:
                if (cmd.toLowerCase().startsWith("nouvelle partie")) {
                    ctrl.nouvellePartie();
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

    @Override
    public void run() {
        Timer timer = new Timer(16, new AdaptateurTemps(ctrl));
        timer.start();

        mettreAJour();
        Scanner s = new Scanner(System.in);

        while (true) {
            System.out.print("Commande > ");
            interpreterCommande(s.nextLine());
        }
    }

    @Override
    public void mettreAJour() {
        System.out.println("\n" + jeu.toString() + "\n");
    }
}
