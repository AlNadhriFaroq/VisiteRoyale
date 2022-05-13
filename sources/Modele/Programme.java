package Modele;

import Patterns.Observable;

public class Programme extends Observable {
    public static final int ETAT_ACCUEIL = 0;
    public static final int ETAT_MENU_PRINCIPAL = 1;
    public static final int ETAT_EN_JEU = 2;
    public static final int ETAT_MENU_JEU = 3;
    public static final int ETAT_MENU_OPTIONS = 4;
    public static final int ETAT_TUTORIEL = 5;
    public static final int ETAT_CREDITS = 6;
    public static final int ETAT_FIN_PROGRAMME = 7;

    int etat;
    Jeu jeu;
    boolean joueurVrtEstIA, joueurRgeEstIA;

    public Programme(){
        jeu = new Jeu() ;
        etat = ETAT_ACCUEIL;
    }

    public int getEtat() {
        return etat;
    }

    public Jeu getJeu() {
        return jeu;
    }

    public boolean getJoueurVrtEstIA() {
        return joueurVrtEstIA;
    }

    public boolean getJoueurRgeEstIA() {
        return joueurRgeEstIA;
    }

    public void commencerProgramme() {
        etat = ETAT_MENU_PRINCIPAL;
        mettreAJour();
    }

    public void nouvellePartie(boolean joueurVrtEstIA, boolean joueurRgeEstIA) {
        etat = ETAT_EN_JEU ;
        this.joueurVrtEstIA = joueurVrtEstIA;
        this.joueurRgeEstIA = joueurRgeEstIA;
        jeu.nouvellePartie();
        mettreAJour();
    }

    public void definirJoueurQuiCommence(int joueur) {
        jeu.definirJoueurQuiCommence(joueur);
        mettreAJour();
    }

    public void sauvegarderPartie() {
    }

    public void chargerPartie() {
    }

    public void ouvrirMenuJeu() {
        etat = ETAT_MENU_JEU;
        mettreAJour();
    }

    public void reprendrePartie() {
        etat = ETAT_EN_JEU;
        mettreAJour();
    }

    public void ouvrirCredits() {
        etat = ETAT_CREDITS;
        mettreAJour();
    }

    public void retourMenuPrincipal() {
        etat = ETAT_MENU_PRINCIPAL;
        mettreAJour();
    }

    public void parametres() {
        etat = ETAT_MENU_OPTIONS;
        mettreAJour();
    }

    public void quitter() {
        etat = ETAT_FIN_PROGRAMME;
        mettreAJour();
    }

    public void jouerCoup(Coup coup) {
        jeu.jouerCoup(coup);
        mettreAJour();
    }

    public void annulerCoup() {
        jeu.annulerCoup();
        mettreAJour();
    }

    public void refaireCoup() {
        jeu.refaireCoup();
        mettreAJour();
    }

    @Override
    public String toString() {
        String txt  = "";

        switch (etat) {
            case ETAT_ACCUEIL:
                txt += "Ouverture en cours. Veuillez patienter...";
                break;
            case ETAT_MENU_PRINCIPAL:
                txt += "VISITE ROYALE\n";
                txt += "1. Nouvelle partie 1v1\n";
                txt += "2. Nouvelle partie contre IA\n";
                txt += "3. Charger une partie\n";
                txt += "4. Options\n";
                txt += "5. Tutoriel\n";
                txt += "6. Crédits\n";
                txt += "7. Quitter";
                break;
            case ETAT_EN_JEU:
                txt += jeu.toString();
                break;
            case ETAT_MENU_JEU:
                txt += "PAUSE\n";
                txt += "1. Reprende la partie\n";
                txt += "2. Nouvelle partie\n";
                txt += "3. Sauvegarder la partie\n";
                txt += "4. Options\n";
                txt += "5. Tutoriel\n";
                txt += "6. Retour au menu principal";
                break;
            case ETAT_MENU_OPTIONS:
                txt += "Retour";
                break;
            case ETAT_TUTORIEL:
                txt += "Bonne chance";
                break;
            case ETAT_CREDITS:
                txt += "Université Grenoble-Alpes\n";
                txt += "Licence Informatique générale 3e année\n";
                txt += "Programmation et projet logiciel\n\n";
                txt += "Sous la direction de :\n";
                txt += "   Gabriela González Sáez\n\n";
                txt += "Développeurs :\n";
                txt += "   Faroq Al-Nadhari\n";
                txt += "   Nadim Babba\n";
                txt += "   Rodolphe Beguin\n";
                txt += "   Maxime Bouchenoua\n";
                txt += "   Sacha Isaac--Chassande\n";
                txt += "   Landry Rolland";
                break;
            case ETAT_FIN_PROGRAMME:
                txt += "Fermeture en cours...";
                break;
            default:
                throw new RuntimeException("Modele.Programme.toString() : Etat invalide.");
        }

        return txt;
    }
}
