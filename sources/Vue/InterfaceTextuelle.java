package Vue;

import Controleur.ControleurMediateur;
import Modele.*;

import java.util.Scanner;
import javax.swing.*;

public class InterfaceTextuelle extends InterfaceUtilisateur {
    Jeu jeu;

    public InterfaceTextuelle(Programme prog, ControleurMediateur ctrl) {
        super(prog, ctrl);
        jeu = prog.getJeu();
    }

    public void interpreterCommande(String commande) {
        String cmd = commande.toLowerCase().replaceAll("\\s+", "");

        switch (prog.getEtat()) {
            case Programme.ETAT_ACCUEIL:
                System.out.println("Ouverture en cours. Veuillez patienter...");
                break;
            case Programme.ETAT_MENU_PRINCIPAL:
                interpreterCommandeMenuPrincipal(cmd);
                break;
            case Programme.ETAT_EN_JEU:
                interpreterCommandeEnJeu(cmd);
                break;
            case Programme.ETAT_MENU_JEU:
                interpreterCommandeMenuJeu(cmd);
                break;
            case Programme.ETAT_MENU_SAUVEGARDES:
                interpreterCommandeMenuSauvegardes(cmd);
                break;
            case Programme.ETAT_MENU_OPTIONS:
                interpreterCommandeMenuOptions(cmd);
                break;
            case Programme.ETAT_TUTORIEL:
                interpreterCommandeTutoriel(cmd);
                break;
            case Programme.ETAT_CREDITS:
                ctrl.retourMenuPrecedant();
                break;
            case Programme.ETAT_FIN_PROGRAMME:
                System.out.println("Fermeture en cours...");
                break;
            default:
                throw new RuntimeException("Vue.InterfaceTextuelle.interpreterComande() : Etat du programme non reconnu.");
        }
    }

    public void interpreterCommandeMenuPrincipal(String cmd) {
        switch (cmd) {
            case "1":
            case "1v1":
                ctrl.nouvellePartie(false, false);
                break;
            case "2":
            case "ia":
            case "vsia":
                ctrl.nouvellePartie(true, false);
                break;
            case "3":
            case "charger":
                ctrl.ouvrirMenuSauvegardes();
                break;
            case "4":
            case "opt":
            case "options":
                ctrl.ouvrirMenuOptions();
                break;
            case "5":
            case "tuto":
            case "tutoriel":
                ctrl.ouvrirTutoriel();
                break;
            case "6":
            case "credits":
                ctrl.ouvrirCredits();
                break;
            case "7":
            case "quitter":
                ctrl.quitter();
                break;
            case "iavsia":
                ctrl.nouvellePartie(true, true);
                break;
            default:
                System.out.println("Commande invalide.");
                System.out.print("Commande > ");
                break;
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

    public void interpreterCommandeMenuJeu(String cmd) {
        switch (cmd) {
            case "1":
            case "reprendre":
                ctrl.reprendrePartie();
                break;
            case "2":
            case "nouvelle":
            case "nouvellepartie":
                ctrl.nouvellePartie(prog.getJoueurEstIA(Jeu.JOUEUR_VRT), prog.getJoueurEstIA(Jeu.JOUEUR_RGE));
                break;
            case "3":
            case "sauvegarder":
                ctrl.ouvrirMenuSauvegardes();
                break;
            case "4":
            case "opt":
            case "options":
                ctrl.ouvrirMenuOptions();
                break;
            case "5":
            case "tuto":
            case "tutoriel":
                ctrl.ouvrirTutoriel();
                break;
            case "6":
            case "menu":
            case "menuprincipal":
                ctrl.retourMenuPrecedant();
                break;
            default:
                System.out.println("Commande invalide.");
                System.out.print("Commande > ");
                break;
        }
    }

    void interpreterCommandeMenuSauvegardes(String cmd) {
        int sauvegarde;
        switch (cmd) {
            case "-1":
            case "-2":
            case "-3":
            case "-4":
            case "-5":
                sauvegarde = -Integer.parseInt(cmd) - 1;
                if (prog.sauvegardeEstSupprimable(sauvegarde)) {
                    ctrl.supprimerSauvegarde(sauvegarde);
                } else {
                    System.out.println("Impossible de supprimer une sauvegarde vide.");
                    System.out.println("Commande > ");
                }
                break;
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
                sauvegarde = Integer.parseInt(cmd) - 1;
                if (prog.partieEstSauvegardable()) {
                    ctrl.sauvegarderPartie(sauvegarde);
                } else if (prog.sauvegardeEstChargeable(sauvegarde)) {
                    ctrl.chargerSauvegarde(sauvegarde);
                } else {
                    System.out.println("Impossible de charger une sauvegarde vide.");
                    System.out.println("Commande > ");
                }
                break;
            case "6":
            case "retour":
                ctrl.retourMenuPrecedant();
                break;
            default:
                System.out.println("Commande invalide.");
                System.out.print("Commande > ");
                break;
        }
    }

    void interpreterCommandeMenuOptions(String cmd) {
        switch (cmd) {
            case "1":
            case "retour":
                ctrl.retourMenuPrecedant();
                break;
            default:
                System.out.println("Commande invalide.");
                System.out.print("Commande > ");
                break;
        }
    }

    void interpreterCommandeTutoriel(String cmd) {
        switch (cmd) {
            case "<":
            case "prec":
            case "precedant":
                ctrl.changerPageTutoriel(-1);
                break;
            case ">":
            case "suiv":
            case "suivant":
                ctrl.changerPageTutoriel(1);
                break;
            case "retour":
                ctrl.retourMenuPrecedant();
                break;
            default:
                System.out.println("Commande invalide.");
                System.out.print("Commande > ");
                break;
        }
    }

    void interpreterCommandeChoixJoueur(String cmd) {
        switch (cmd) {
            case "g":
            case "d":
            case "gauche":
            case "droite":
                ctrl.definirJoueurQuiCommence();
                break;
            case "aide":
            case "help":
                System.out.println("G / Gauche : Choisir la main de gauche.");
                System.out.println("D / Droite : Choisir la main de droite.");
                System.out.println("Pause      : Ouvrir le menu du jeu.");
                System.out.print("\nCommande > ");
                break;
            case "pause":
                ctrl.ouvrirMenuJeu();
                break;
            default:
                System.out.println("Commande invalide.");
                System.out.print("Commande > ");
                break;
        }
    }

    void interpreterCommandeChoixCarte(String cmd) {
        if (jeu.peutUtiliserPouvoirSorcier() && (cmd.equals("sorcier") || cmd.equals("sor"))) {
            ctrl.activerPouvoirSor();
        } else if (jeu.peutUtiliserPouvoirFou() && cmd.equals("fou")) {
            ctrl.activerPouvoirFou();
        } else if (jeu.peutFinirTour() && (cmd.equals("fin") || cmd.equals("fintour"))) {
            ctrl.finirTour();
        } else if (cmd.equals("annuler")) {
            ctrl.annuler();
        } else if (cmd.equals("refaire")) {
            ctrl.refaire();
        } else if (cmd.equals("aide") || cmd.equals("help")) {
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
            System.out.println("Pause    : Ouvrir le menu du jeu.");
            System.out.print("\nCommande > ");
        } else if (cmd.equals("pause")) {
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
            System.out.print("Commande > ");
        }
    }

    void interpreterCommandeChoixPion(String cmd) {
        if (cmd.equals("annuler")) {
            ctrl.annuler();
        } else if (cmd.equals("refaire")) {
            ctrl.refaire();
        } else if (cmd.equals("aide") || cmd.equals("help")) {
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
            System.out.println("Pause    : Ouvrir le menu du jeu.");
            System.out.print("\nCommande > ");
        } else if (cmd.equals("pause")) {
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
            System.out.print("Commande > ");
        }
    }

    void interpreterCommandeChoixDirection(String cmd) {
        if (jeu.peutUtiliserPrivilegeRoi() && cmd.equals("r1")) {
            ctrl.selectionnerCarte(Carte.R1);
        } else if (cmd.equals("gv") || cmd.equals("gr")) {
            Pion pion = Pion.texteEnPion(cmd.toUpperCase());
            if (jeu.peutSelectionnerPion(pion))
                ctrl.selectionnerPion(pion);
            else
                System.out.println("Commande non reconnue.");
        } else if (cmd.equals("annuler")) {
            ctrl.annuler();
        } else if (cmd.equals("refaire")) {
            ctrl.refaire();
        } else if (cmd.equals("aide") || cmd.equals("help")) {
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
            System.out.println("Pause    : Ourvrir le menu du jeu.");
            System.out.print("\nCommande > ");
        } else if (cmd.equals("pause")) {
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
            System.out.print("Commande > ");
        }
    }

    void interpreterCommandeFinDePartie(String cmd) {
        switch (cmd) {
            case "1":
            case "relancer":
            case "nouvelle":
            case "nouvellepartie":
                ctrl.nouvellePartie(prog.getJoueurEstIA(Jeu.JOUEUR_VRT), prog.getJoueurEstIA(Jeu.JOUEUR_RGE));
                break;
            case "2":
            case "retour":
            case "quitter":
            case "menu":
            case "menuprincipal":
                ctrl.retourMenuPrecedant();
                break;
            default:
                System.out.println("Commande invalide.");
                System.out.print("Commande > ");
                break;
        }
    }

    @Override
    public void run() {
        mettreAJour();

        Timer timer = new Timer(16, new AdaptateurTemps(ctrl));
        timer.start();

        Scanner s = new Scanner(System.in);

        prog.changerEtat(Programme.ETAT_MENU_PRINCIPAL);
        while (prog.getEtat() != Programme.ETAT_FIN_PROGRAMME)
            interpreterCommande(s.nextLine());
    }

    @Override
    public void mettreAJour() {
        jeu = prog.getJeu();
        System.out.println();
        afficherProgramme();
        if (prog.getEtat() != Programme.ETAT_ACCUEIL && prog.getEtat() != Programme.ETAT_FIN_PROGRAMME)
            System.out.print("\nCommande > ");
    }

    private void afficherProgramme() {
        switch (prog.getEtat()) {
            case Programme.ETAT_ACCUEIL:
                System.out.println("Ouverture en cours. Veuillez patienter...");
                break;
            case Programme.ETAT_MENU_PRINCIPAL:
                System.out.println("VISITE ROYALE");
                System.out.println("1. Nouvelle partie 1v1");
                System.out.println("2. Nouvelle partie contre IA");
                System.out.println("3. Charger une partie");
                System.out.println("4. Options");
                System.out.println("5. Tutoriel");
                System.out.println("6. Crédits");
                System.out.println("7. Quitter");
                break;
            case Programme.ETAT_EN_JEU:
                afficherJeu();
                break;
            case Programme.ETAT_MENU_JEU:
                System.out.println("PAUSE");
                System.out.println("1. Reprende la partie");
                System.out.println("2. Nouvelle partie");
                System.out.println("3. Sauvegarder la partie");
                System.out.println("4. Options");
                System.out.println("5. Tutoriel");
                System.out.println("6. Retour au menu principal");
                break;
            case Programme.ETAT_MENU_SAUVEGARDES:
                System.out.println("SAUVEGARDES");
                for (int i = 0; i < prog.getSauvegardes().length; i++)
                    System.out.println((i + 1) + ". " + prog.getSauvegardes()[i]);
                for (int i = prog.getSauvegardes().length; i < Programme.NB_SAUVEGARDES; i++)
                    System.out.println((i + 1) + ". Sauvegarde vide");
                System.out.println((Programme.NB_SAUVEGARDES + 1) + ". Retour");
                System.out.println("Précéder d'un '-' le numéro d'une sauvegarde pour la supprimer.");
                break;
            case Programme.ETAT_MENU_OPTIONS:
                afficherOptions();
                break;
            case Programme.ETAT_TUTORIEL:
                afficherTutoriel();
                break;
            case Programme.ETAT_CREDITS:
                System.out.println("Université Grenoble-Alpes");
                System.out.println("Licence Informatique générale 3e année");
                System.out.println("Programmation et projet logiciel\n");
                System.out.println("Sous la direction de :");
                System.out.println("   Gabriela González Sáez\n");
                System.out.println("Développeurs :");
                System.out.println("   Faroq Al-Nadhari");
                System.out.println("   Nadim Babba");
                System.out.println("   Rodolphe Beguin");
                System.out.println("   Maxime Bouchenoua");
                System.out.println("   Sacha Isaac--Chassande");
                System.out.println("   Landry Rolland");
                break;
            case Programme.ETAT_FIN_PROGRAMME:
                System.out.println("Fermeture en cours...");
                break;
            default:
                throw new RuntimeException("Modele.Programme.toString() : Etat invalide.");
        }
    }

    private void afficherJeu() {
        switch (prog.getJeu().getEtatJeu()) {
            case Jeu.ETAT_CHOIX_JOUEUR:
                System.out.println("Tirage du joueur qui commence.");
                System.out.println("Main gauche ou main droite ?");
                break;
            case Jeu.ETAT_CHOIX_CARTE:
            case Jeu.ETAT_CHOIX_PION:
            case Jeu.ETAT_CHOIX_DIRECTION:
                afficherEnteteJeu(prog.getJeu());
                System.out.print("     Main vert  : ");
                afficherPaquet(prog.getJeu().getMain(Jeu.JOUEUR_VRT), prog.getJeu().getJoueurCourant() == Jeu.JOUEUR_VRT);
                System.out.print("                  ");
                afficherPaquet(prog.getJeu().getSelectionCartes(Jeu.JOUEUR_VRT), false);
                afficherPlateau(prog.getJeu());
                System.out.print("                  ");
                afficherPaquet(prog.getJeu().getSelectionCartes(Jeu.JOUEUR_RGE), false);
                System.out.print("     Main rouge : ");
                afficherPaquet(prog.getJeu().getMain(Jeu.JOUEUR_RGE), prog.getJeu().getJoueurCourant() == Jeu.JOUEUR_RGE);
                break;
            case Jeu.ETAT_FIN_DE_PARTIE:
                System.out.println(prog.getJeu().toString() + "\n");
                if (prog.getJoueurEstIA(Jeu.JOUEUR_VRT) && !prog.getJoueurEstIA(Jeu.JOUEUR_RGE))
                    if (prog.getJeu().getJoueurGagnant() == Jeu.JOUEUR_RGE)
                        System.out.println("VOUS AVEZ GAGNEZ !!!\n");
                    else
                        System.out.println("VOUS AVEZ PERDU...\n");
                else
                    System.out.println("VICTOIRE DE " + Jeu.joueurEnTexte(prog.getJeu().getJoueurGagnant()).toUpperCase() + " !!!\n");
                System.out.println("FIN DE PARTIE");
                System.out.println("1. Nouvelle partie");
                System.out.println("2. Retour au menu principal");
                break;
            default:
                throw new RuntimeException("Modele.Programme.toString() : Etat invalide.");
        }
    }

    private void afficherOptions() {
        System.out.println("OPTIONS");
        System.out.println("Pas d'option pour le moment...");
        System.out.println("1. Retour");
    }

    private void afficherTutoriel() {
        System.out.println("TUTORIEL (page " + prog.getPageTutoriel() + ")");
        switch (prog.getPageTutoriel()) {
            case 0:
                System.out.println("Pas de tutoriel pour le moment... (page 0)");
                break;
            case 1:
                System.out.println("Pas de tutoriel pour le moment... (page 1)");
                break;
            case 2:
                System.out.println("Pas de tutoriel pour le moment... (page 2)");
                break;
            case 3:
                System.out.println("Pas de tutoriel pour le moment... (page 3)");
                break;
            case 4:
                System.out.println("Pas de tutoriel pour le moment... (page 4)");
                break;
            case 5:
                System.out.println("Pas de tutoriel pour le moment...  (page 5)");
                break;
            case 6:
                System.out.println("Pas de tutoriel pour le moment...  (page 6)");
                break;
            case 7:
                System.out.println("Pas de tutoriel pour le moment...  (page 7)");
                break;
            case 8:
                System.out.println("Pas de tutoriel pour le moment...  (page 8)");
                break;
            case 9:
                System.out.println("Pas de tutoriel pour le moment...  (page 9)");
                break;
            case 10:
                System.out.println("Pas de tutoriel pour le moment...  (page 10)");
                break;
            default:
                System.out.println("Page inexistante.");
                break;
        }
        System.out.println("< Precedant       Retour       Suivant >");
    }

    private void afficherEnteteJeu(Jeu jeu) {
        String joueurCourant = Jeu.joueurEnTexte(jeu.getJoueurCourant());
        String couleurJoueur = jeu.getJoueurCourant() == Jeu.JOUEUR_VRT ? Couleur.VERT : Couleur.ROUGE;

        System.out.print("AU TOUR DE : ");
        System.out.printf("%-12s", Couleur.formaterTexte(joueurCourant.toUpperCase(), couleurJoueur));
        System.out.printf("%25s\n" , "Pioche : " + jeu.getPioche().getTaille());
    }

    private void afficherPaquet(Paquet paquet, boolean estJoueurCourant) {
        for (int i = 0; i < paquet.getTaille(); i++) {
            Carte carte = paquet.getCarte(i);
            if (estJoueurCourant && prog.getJeu().peutSelectionnerCarte(carte)) {
                if (carte.getType().equals(Type.ROI))
                    System.out.print(Couleur.formaterTexte(carte.toString(), 190, 68, 125, true, false, false, false));
                else if (carte.getType().equals(Type.GAR))
                    System.out.print(Couleur.formaterTexte(carte.toString(), 149, 169, 177, true, false, false, false));
                else if (carte.getType().equals(Type.SOR))
                    System.out.print(Couleur.formaterTexte(carte.toString(), 255, 133, 55, true, false, false, false));
                else if (carte.getType().equals(Type.FOU))
                    System.out.print(Couleur.formaterTexte(carte.toString(), 100, 199, 194, true, false, false, false));
            } else {
                if (carte.getType().equals(Type.ROI))
                    System.out.print(Couleur.formaterTexte(carte.toString(), 190, 68, 125));
                else if (carte.getType().equals(Type.GAR))
                    System.out.print(Couleur.formaterTexte(carte.toString(), 149, 169, 177));
                else if (carte.getType().equals(Type.SOR))
                    System.out.print(Couleur.formaterTexte(carte.toString(), 255, 133, 55));
                else if (carte.getType().equals(Type.FOU))
                    System.out.print(Couleur.formaterTexte(carte.toString(), 100, 199, 194));
            }
            System.out.print(" ");
        }
        System.out.println();
    }

    private void afficherPlateau(Jeu jeu) {
        Plateau plateau = jeu.getPlateau();

        for (int l = 0; l < 4; l++) {
            for (int c = Plateau.BORDURE_VRT; c <= Plateau.BORDURE_RGE; c++) {
                String txt;
                boolean pionSelectionnable = false;

                if (l == 0 && c == plateau.getPositionCouronne()) {
                    txt = plateau.getFaceCouronne() == Plateau.FACE_GRD_CRN ? "C " : "c ";
                } else if (l == 1 && c == plateau.getPositionPion(Pion.SOR)) {
                    txt = Pion.SOR + " ";
                    pionSelectionnable = jeu.peutSelectionnerPion(Pion.SOR);
                } else if (l == 2 && c == plateau.getPositionPion(Pion.GAR_VRT)) {
                    txt = Pion.GAR_VRT.toString();
                    pionSelectionnable = jeu.peutSelectionnerPion(Pion.GAR_VRT);
                } else if (l == 2 && c == plateau.getPositionPion(Pion.ROI)) {
                    txt = Pion.ROI + " ";
                    pionSelectionnable = jeu.peutSelectionnerPion(Pion.ROI);
                } else if (l == 2 && c == plateau.getPositionPion(Pion.GAR_RGE)) {
                    txt = Pion.GAR_RGE.toString();
                    pionSelectionnable = jeu.peutSelectionnerPion(Pion.GAR_RGE);
                } else if (l == 3 && c == plateau.getPositionPion(Pion.FOU)) {
                    txt = Pion.FOU + " ";
                    pionSelectionnable = jeu.peutSelectionnerPion(Pion.FOU);
                } else {
                    txt = "  ";
                }

                afficherCase(c, txt, pionSelectionnable);
            }
            System.out.println();
        }
    }

    private void afficherCase(int c, String txt, boolean pionSelectionnable) {
        int[] couleurs = Couleur.obtenirCouleur(c);

        if (txt.equals("  "))
            System.out.print(Couleur.formaterTexte(txt, Couleur.NOIR, couleurs[0], couleurs[1], couleurs[2], false, false, false, false));
        else
            System.out.print(Couleur.formaterTexte(txt, 100, 100, 100, couleurs[0], couleurs[1], couleurs[2], pionSelectionnable, false, false, true));

        if (c != Plateau.BORDURE_RGE)
            System.out.print(" ");
    }
}
