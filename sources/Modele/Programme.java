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
    boolean[] joueursSontIA;
    int pageTutoriel;

    public Programme() {
        etat = ETAT_ACCUEIL;
        jeu = new Jeu();
        jeu.setEtatJeu(Jeu.ETAT_FIN_DE_PARTIE);
        joueursSontIA = new boolean[2];
        pageTutoriel = 0;
    }

    public int getEtat() {
        return etat;
    }

    public Jeu getJeu() {
        return jeu;
    }

    public boolean getJoueurEstIA(int joueur) {
        return joueursSontIA[joueur];
    }

    public int getPageTutoriel() {
        return pageTutoriel;
    }

    void setEtat(int etat) {
        this.etat = etat;
    }

    void setJoueurEstIA(int joueur, boolean estIA) {
        joueursSontIA[joueur] = estIA;
    }

    void setPageTutoriel(int page) {
        pageTutoriel = page;
    }

    public void nouvellePartie(boolean joueurVrtEstIA, boolean joueurRgeEstIA) {
        etat = ETAT_EN_JEU;
        jeu.nouvellePartie();
        //jeu.nouvellePartiePersonalise(Jeu.JOUEUR_VRT, 8, 6, 10, 7, 9, 8, Plateau.FACE_GRD_CRN, 0);
        joueursSontIA[Jeu.JOUEUR_VRT] = joueurVrtEstIA;
        joueursSontIA[Jeu.JOUEUR_RGE] = joueurRgeEstIA;
        mettreAJour();
    }

    public void definirJoueurQuiCommence(int joueur) {
        jeu.definirJoueurQuiCommence(joueur);
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

    public void sauvegarderPartie() {
    }

    public void chargerPartie() {
    }

    public void changerEtat(int etat) {
        setEtat(etat);
        mettreAJour();
    }

    public void retourMenuPrecedant() {
        if ((etat == ETAT_MENU_OPTIONS || etat == ETAT_TUTORIEL) && jeu.getEtatJeu() != Jeu.ETAT_FIN_DE_PARTIE) {
            etat = ETAT_MENU_JEU;
        } else {
            etat = ETAT_MENU_PRINCIPAL;
            jeu.setEtatJeu(Jeu.ETAT_FIN_DE_PARTIE);
        }
        mettreAJour();
    }

    public void quitter() {
        etat = ETAT_FIN_PROGRAMME;
        mettreAJour();
    }

    public void changerPageTutoriel(int sens) {
        if (sens == 1 && pageTutoriel < 10)
            setPageTutoriel(pageTutoriel + 1);
        else if (sens == -1 && pageTutoriel > 0)
            setPageTutoriel(pageTutoriel - 1);
        mettreAJour();
    }

    @Override
    public String toString() {
        return Integer.toString(etat);
    }
}
