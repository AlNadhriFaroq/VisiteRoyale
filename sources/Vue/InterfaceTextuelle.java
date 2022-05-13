package Vue;

import Controleur.ControleurMediateur;
import Modele.*;

import javax.swing.*;
import java.util.Scanner;

public class InterfaceTextuelle extends InterfaceUtilisateur {
    Jeu jeu;

    public InterfaceTextuelle(Programme prog, ControleurMediateur ctrl) {
        super(prog, ctrl);
        jeu = prog.getJeu();
    }

    public void interpreterCommande(String cmd) {
        switch (prog.getEtat()) {
            case Programme.ETAT_ACCUEIL:
                System.out.println("Ouverture en cours. Veuillez patienter...");
                break;
            case Programme.ETAT_MENU_PRINCIPALE:
                switch (cmd) {
                    case "1":
                        ctrl.nouvellePartie(false, false);
                        break;
                    case "2":
                        ctrl.nouvellePartie(true, false);
                        break;
                    case "3":
                        System.out.println("Chargement non implémenté.");
                        break;
                    case "4":
                        System.out.println("Options non implémenté.");
                        break;
                    case "5":
                        System.out.println("Tutoriel non implémenté.");
                        break;
                    case "6":
                        ctrl.ouvrirCredits();
                        break;
                    case "7":
                        ctrl.quitter();
                        break;
                    default:
                        System.out.println("Commande invalide.");
                        System.out.print("Commande > ");
                        break;
                }
                break;
            case Programme.ETAT_EN_JEU:
                interpreterCommandeEnJeu(cmd);
                break;
            case Programme.ETAT_MENU_JEU:
                switch (cmd) {
                    case "1":
                        ctrl.reprendrePartie();
                        break;
                    case "2":
                        ctrl.nouvellePartie(prog.getJoueurVrtEstIA(), prog.getJoueurRgeEstIA());
                        break;
                    case "3":
                        System.out.println("Sauvegarde non implémenté.");
                        break;
                    case "4":
                        System.out.println("Options non implémenté.");
                        break;
                    case "5":
                        System.out.println("Tutoriel non implémenté.");
                        break;
                    case "6":
                        ctrl.abandonnerPartie();
                        break;
                    default:
                        System.out.println("Commande invalide.");
                        System.out.print("Commande > ");
                        break;
                }
                break;
            case Programme.ETAT_MENU_PARAMETRES:
                System.out.println("Options non implémenté.");
                break;
            case Programme.ETAT_TUTORIEL:
                System.out.println("Tutoriel non implémenté.");
                break;
            case Programme.ETAT_CREDITS:
                ctrl.retourMenu();
                break;
            case Programme.ETAT_FIN_APP:
                break;
            default:
                throw new RuntimeException("Vue.InterfaceTextuelle.interpreterComande() : Etat du programme non reconnu.");
        }
    }

    public void interpreterCommandeEnJeu(String cmd) {
        switch (jeu.getEtatJeu()) {
            case Jeu.ETAT_CHOIX_JOUEUR:
                interpreterCommandeChoixJoueur(cmd);
                break;
            case Jeu.ETAT_CHOIX_CARTE:
                interpreterCommandeChoixCarte(cmd);
                break;
            case Jeu.ETAT_CHOIX_PION:
                interpreterCommandeChoixPion(cmd);
                break;
            case Jeu.ETAT_CHOIX_DIRECTION:
                interpreterCommandeChoixDirection(cmd);
                break;
            case Jeu.ETAT_FIN_DE_PARTIE:
                interpreterCommandeFinDePartie(cmd);
                break;
            default:
                throw new RuntimeException("Vue.InterfaceTextuelle.interpreterComandeEnJeu() : Etat du jeu non reconnu.");
        }
    }

    void interpreterCommandeChoixJoueur(String cmd) {
        if (cmd.equalsIgnoreCase("g") || cmd.equalsIgnoreCase("d") ||
                cmd.equalsIgnoreCase("gauche") || cmd.equalsIgnoreCase("droite")) {
            ctrl.definirJoueurQuiCommence();
        } else if (cmd.equalsIgnoreCase("aide") || cmd.equalsIgnoreCase("help")) {
            System.out.println("G / Gauche : Choisir la main de gauche.");
            System.out.println("D / Droite : Choisir la main de droite.");
            System.out.println("Aide       : Afficher cette aide.");
            System.out.println("Pause      : Ouvrir le menu du jeu.");
            System.out.print("\nCommande > ");
        } else if (cmd.toLowerCase().startsWith("pause")) {
            ctrl.ouvrirMenuJeu();
        } else {
            System.out.println("Commande invalide.");
        }
    }

    void interpreterCommandeChoixCarte(String cmd) {
        if (jeu.peutUtiliserPouvoirSorcier() && (cmd.equalsIgnoreCase("sorcier") || cmd.equalsIgnoreCase("sor"))) {
            ctrl.activerPouvoirSor();
        } else if (jeu.peutUtiliserPouvoirFou() && cmd.equalsIgnoreCase("fou")) {
            ctrl.activerPouvoirFou();
        } else if (jeu.peutFinirTour() && cmd.equalsIgnoreCase("fin") || cmd.equalsIgnoreCase("fin tour")) {
            ctrl.finirTour();
        } else if (cmd.equalsIgnoreCase("annuler")) {
            ctrl.annuler();
        } else if (cmd.equalsIgnoreCase("refaire")) {
            ctrl.refaire();
        } else if (cmd.equalsIgnoreCase("aide") || cmd.equalsIgnoreCase("help")) {
            if (jeu.peutSelectionnerCarte(Carte.R1))
                System.out.println("R1       : Sélectionner une carte Roi pour déplacer le pion Roi d'une case.");
            if (jeu.peutSelectionnerCarte(Carte.G1))
                System.out.println("G1       : Sélectionner une carte Garde pour déplacer un pion Garde d'une case.");
            if (jeu.peutSelectionnerCarte(Carte.G2))
                System.out.println("G2       : Sélectionner une carte Garde pour déplacer un pion Garde de deux cases ou deux gardes d'une case.");
            if (jeu.peutSelectionnerCarte(Carte.GC))
                System.out.println("GC       : Sélectionner une carte Garde pour déplacer les deux pions Gardes sur les cases adjacentes au Roi.");
            if (jeu.peutSelectionnerCarte(Carte.S1))
                System.out.println("S1       : Sélectionner une carte Sorcier pour déplacer le pion Sorcier d'une case.");
            if (jeu.peutSelectionnerCarte(Carte.S2))
                System.out.println("S2       : Sélectionner une carte Sorcier pour déplacer le pion Sorcier de deux cases.");
            if (jeu.peutSelectionnerCarte(Carte.S3))
                System.out.println("S3       : Sélectionner une carte Sorcier pour déplacer le pion Sorcier de trois cases.");
            if (jeu.peutSelectionnerCarte(Carte.F1))
                System.out.println("F1       : Sélectionner une carte Fou pour déplacer le pion Fou d'une case.");
            if (jeu.peutSelectionnerCarte(Carte.F2))
                System.out.println("F2       : Sélectionner une carte Fou pour déplacer le pion Fou de deux cases.");
            if (jeu.peutSelectionnerCarte(Carte.F3))
                System.out.println("F3       : Sélectionner une carte Fou pour déplacer le pion Fou de trois cases.");
            if (jeu.peutSelectionnerCarte(Carte.F4))
                System.out.println("F4       : Sélectionner une carte Fou pour déplacer le pion Fou de quatre cases.");
            if (jeu.peutSelectionnerCarte(Carte.F5))
                System.out.println("F5       : Sélectionner une carte Fou pour déplacer le pion Fou de cinq cases.");
            if (jeu.peutSelectionnerCarte(Carte.FM))
                System.out.println("FM       : Sélectionner une carte Fou pour déplacer le pion Fou sur la Fontaine.");
            if (jeu.peutUtiliserPouvoirSorcier())
                System.out.println("Sorcier  : Activer le pouvoir du Sorcier, déplacant un pion P à la position du Sorcier.");
            if (jeu.peutUtiliserPouvoirFou())
                System.out.println("Fou      : Activer le pouvoir du Fou, déplacant un pion avec les cartes Fou.");
            if (jeu.peutFinirTour())
                System.out.println("Fin      : Finir son tour.");
            System.out.println("Annuler  : Annuler le dernier coup joué.");
            System.out.println("Refaire  : Refaire le dernier coup joué.");
            System.out.println("Aide     : Afficher cette aide.");
            System.out.println("Pause    : Ouvrir le menu du jeu.");
            System.out.print("\nCommande > ");
        } else if (cmd.equalsIgnoreCase("pause")) {
            ctrl.ouvrirMenuJeu();
        } else if (cmd.length() == 2) {
            try {
                Carte carte = Carte.texteEnCarte(cmd.toUpperCase());
                if (jeu.peutSelectionnerCarte(carte))
                    ctrl.selectionnerCarte(carte);
                else
                    System.out.println("Aucun coup n'est possible pour jouer cette carte.");
            } catch (RuntimeException e) {
                System.out.println("Cette carte n'existe pas.");
            }
        } else {
            System.out.println("Commande invalide.");
        }
    }

    void interpreterCommandeChoixPion(String cmd) {
        if (cmd.equalsIgnoreCase("annuler")) {
            ctrl.annuler();
        } else if (cmd.equalsIgnoreCase("refaire")) {
            ctrl.refaire();
        } else if (cmd.equalsIgnoreCase("aide") || cmd.equalsIgnoreCase("help")) {
            if (jeu.peutSelectionnerPion(Pion.ROI))
                System.out.println("R        : Selectionner le pion Roi.");
            if (jeu.peutSelectionnerPion(Pion.GAR_VRT))
                System.out.println("GV       : Selectionner le pion Garde qui est du côté du joueur vert.");
            if (jeu.peutSelectionnerPion(Pion.GAR_RGE))
                System.out.println("GR       : Selectionner le pion Garde qui est du côté de joueur rouge.");
            if (jeu.peutSelectionnerPion(Pion.SOR))
                System.out.println("S        : Selectionner le pion Sorcier.");
            System.out.println("Annuler  : Annuler le dernier coup joué.");
            System.out.println("Refaire  : Refaire le dernier coup joué.");
            System.out.println("Aide     : Afficher cette aide.");
            System.out.println("Pause    : Ouvrir le menu du jeu.");
            System.out.print("\nCommande > ");
        } else if (cmd.toLowerCase().startsWith("pause")) {
            ctrl.ouvrirMenuJeu();
        } else if (cmd.length() == 1 || cmd.length() == 2) {
            try {
                Pion pion = Pion.texteEnPion(cmd.toUpperCase());
                if (jeu.peutSelectionnerPion(pion))
                    ctrl.selectionnerPion(pion);
                else
                    System.out.println("Impossible de déplacer ce pion.");
            } catch (RuntimeException e) {
                System.out.println("Ce pion n'existe pas.");
            }
        } else {
            System.out.println("Commande invalide.");
        }
    }

    void interpreterCommandeChoixDirection(String cmd) {
        if (jeu.peutUtiliserPrivilegeRoi() && cmd.equalsIgnoreCase("R1")) {
            ctrl.selectionnerCarte(Carte.R1);
        } else if (cmd.equalsIgnoreCase("GV") || cmd.equalsIgnoreCase("GR")) {
            Pion pion = Pion.texteEnPion(cmd);
            if (jeu.peutSelectionnerPion(pion))
                ctrl.selectionnerPion(pion);
            else
                System.out.println("Commande non reconnue.");
        } else if (cmd.equalsIgnoreCase("annuler")) {
            ctrl.annuler();
        } else if (cmd.equalsIgnoreCase("refaire")) {
            ctrl.refaire();
        } else if (cmd.equalsIgnoreCase("aide") || cmd.equalsIgnoreCase("help")) {
            if (jeu.peutUtiliserPrivilegeRoi())
                System.out.println("R1       : Sélectionner une carte Roi pour déplacer la Cour d'une case.");
            if (jeu.peutSelectionnerPion(Pion.GAR_VRT))
                System.out.println("GV       : Sélectionner le pion Garde pour déplacer un deuxième pion Garde d'une case.");
            if (jeu.peutSelectionnerPion(Pion.GAR_RGE))
                System.out.println("GR       : Sélectionner le pion Garde pour déplacer un deuxième pion Garde d'une case.");
            if (jeu.peutSelectionnerDirection(Plateau.DIRECTION_VRT))
                System.out.println("V        : Sélectionner la direction vers le joueur vert, vers la gauche.");
            if (jeu.peutSelectionnerDirection(Plateau.DIRECTION_RGE))
                System.out.println("R        : Sélectionner la direction vers le joueur rouge, vers la droite.");
            System.out.println("Annuler  : Annuler le dernier coup joué.");
            System.out.println("Refaire  : Refaire le dernier coup joué.");
            System.out.println("Aide     : Afficher cette aide.");
            System.out.println("Pause    : Ourvrir le menu du jeu.");
            System.out.print("\nCommande > ");
        } else if (cmd.toLowerCase().startsWith("pause")) {
            ctrl.ouvrirMenuJeu();
        } else if (cmd.length() <= 6) {
            try {
                int direction = Plateau.texteEnDirection(cmd);
                if (jeu.peutSelectionnerDirection(direction))
                    ctrl.selectionnerDirection(direction);
                else
                    System.out.println("Impossible de jouer ce coup dans cette direction.");
            } catch (RuntimeException e) {
                System.out.println("Cette direction n'existe pas." + e);
            }
        } else {
            System.out.println("Commande invalide.");
        }
    }

    void interpreterCommandeFinDePartie(String cmd) {
        if (cmd.equalsIgnoreCase("annuler")) {
            ctrl.annuler();
        } else if (cmd.equalsIgnoreCase("aide") || cmd.equalsIgnoreCase("help")) {
            System.out.println("Annuler  : Annuler le dernier coup joué.");
            System.out.println("Aide     : Afficher cette aide.");
            System.out.println("Pause    : Ouvrir le menu du jeu.");
            System.out.print("\nCommande > ");
        } else if (cmd.equalsIgnoreCase("pause")) {
            ctrl.ouvrirMenuJeu();
        } else {
            System.out.println("Commande invalide.");
        }
    }

    @Override
    public void run() {
        Timer timer = new Timer(16, new AdaptateurTemps(ctrl));
        timer.start();

        mettreAJour();
        Scanner s = new Scanner(System.in);

        while (prog.getEtat() != Programme.ETAT_FIN_APP)
            interpreterCommande(s.nextLine());
    }

    @Override
    public void mettreAJour() {
        System.out.println("\n" + prog.toString() + "\n");
        System.out.print("Commande > ");
    }
}
