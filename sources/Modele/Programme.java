package Modele;

import Patterns.Observable;

import java.util.Date;
import java.io.*;
import java.text.SimpleDateFormat;

public class Programme extends Observable {
    public static final int ETAT_ACCUEIL = 0;
    public static final int ETAT_MENU_PRINCIPAL = 1;
    public static final int ETAT_EN_JEU = 2;
    public static final int ETAT_MENU_JEU = 3;
    public static final int ETAT_MENU_SAUVEGARDES = 4;
    public static final int ETAT_MENU_OPTIONS = 5;
    public static final int ETAT_TUTORIEL = 6;
    public static final int ETAT_CREDITS = 7;
    public static final int ETAT_FIN_PROGRAMME = 8;

    private static final String DOSSIER = System.getProperty("user.home") + File.separator + "VisiteRoyale" + File.separator + "sauvegardes";
    public static final int NB_SAUVEGARDES = 5;

    int etat;
    Jeu jeu;
    boolean[] joueursSontIA;
    File dossier;
    String[] sauvegardes;
    int pageTutoriel;

    public Programme() {
        etat = ETAT_ACCUEIL;

        jeu = new Jeu();
        jeu.setEtatJeu(Jeu.ETAT_FIN_DE_PARTIE);

        joueursSontIA = new boolean[2];

        dossier = new File(DOSSIER);
        dossier.mkdirs();
        sauvegardes = dossier.list();

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

    public String[] getSauvegardes() {
        return sauvegardes;
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
        //jeu.nouvellePartiePersonalise(Jeu.JOUEUR_RGE, 10, 5, 11, 5, 5, 13, Plateau.FACE_PTT_CRN, 0);
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
        int ancienJoueurCourant = jeu.getJoueurCourant();
        jeu.annulerCoup();
        if (jeu.getJoueurCourant() != ancienJoueurCourant && joueursSontIA[jeu.getJoueurCourant()])
            while (jeu.getJoueurCourant() != ancienJoueurCourant)
                jeu.annulerCoup();
        mettreAJour();
    }

    public void refaireCoup() {
        int ancienJoueurCourant = jeu.getJoueurCourant();
        jeu.refaireCoup();
        if (jeu.getJoueurCourant() != ancienJoueurCourant && joueursSontIA[jeu.getJoueurCourant()])
            while (jeu.getJoueurCourant() != ancienJoueurCourant)
                jeu.refaireCoup();
        mettreAJour();
    }

    public void changerEtat(int etat) {
        setEtat(etat);
        mettreAJour();
    }

    public void retourMenuPrecedant() {
        if ((etat == ETAT_MENU_OPTIONS || etat == ETAT_TUTORIEL || etat == ETAT_MENU_SAUVEGARDES) && jeu.getEtatJeu() != Jeu.ETAT_FIN_DE_PARTIE) {
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

    public void sauvegarderPartie(int sauvegarde) {
        try {
            File fichier;
            SimpleDateFormat date = new SimpleDateFormat("yy-MM-dd-HH-mm-ss");
            String nom = DOSSIER + File.separator + "Partie_vs_" + (joueursSontIA[Jeu.JOUEUR_VRT] ? "IA" : "JH") + "_du_" + date.format(new Date()) + ".sauvegarde";

            if (sauvegarde < sauvegardes.length) {
                fichier = new File(sauvegardes[sauvegarde]);
                fichier.renameTo(new File(nom));
            } else {
                fichier = new File(nom);
                fichier.createNewFile();
            }

            FileOutputStream fichierOut = new FileOutputStream(fichier);
            ObjectOutputStream out = new ObjectOutputStream(fichierOut);
            out.writeObject(jeu);
            out.close();
            fichierOut.close();
            mettreAJourSauvegardes();
        } catch (Exception e) {
            throw new RuntimeException("Controleur.ControleurMediateur.sauvegarderPartie() : Impossible de sauvegarder cette partie.\n" + e);
        }
    }

    public void chargerSauvegarde(int sauvegarde) {
        try {
            FileInputStream fichier = new FileInputStream(DOSSIER + File.separator + sauvegardes[sauvegarde]);
            ObjectInputStream in = new ObjectInputStream(fichier);
            jeu = (Jeu) in.readObject();
            in.close();
            fichier.close();

            etat = ETAT_EN_JEU;
            joueursSontIA[Jeu.JOUEUR_VRT] = sauvegardes[sauvegarde].split("_")[2].equals("IA");
            joueursSontIA[Jeu.JOUEUR_RGE] = false;
            mettreAJour();
        } catch (Exception e) {
            throw new RuntimeException("Controleur.ControleurMediateur.chargerSauvegarde() : Impossible de charger cette sauvegarde.\n" + e);
        }
    }

    public void supprimerSauvegarde(int sauvegarde) {
        try {
            File fichier = new File(DOSSIER + File.separator + sauvegardes[sauvegarde]);
            fichier.delete();
            mettreAJourSauvegardes();
        } catch (Exception e) {
            throw new RuntimeException("Controleur.ControleurMediateur.supprimerSauvegarde() : Impossible de supprimer cette sauvegarde.\n" + e);
        }
    }

    public void mettreAJourSauvegardes() {
        sauvegardes = dossier.list();
        mettreAJour();
    }

    public void changerPageTutoriel(int sens) {
        if (sens == 1 && pageTutoriel < 10)
            setPageTutoriel(pageTutoriel + 1);
        else if (sens == -1 && pageTutoriel > 0)
            setPageTutoriel(pageTutoriel - 1);
        mettreAJour();
    }

    public boolean partieEstSauvegardable() {
        return jeu.getEtatJeu() != Jeu.ETAT_FIN_DE_PARTIE;
    }

    public boolean sauvegardeEstChargeable(int sauvegarde) {
        return jeu.getEtatJeu() == Jeu.ETAT_FIN_DE_PARTIE && sauvegarde < sauvegardes.length;
    }

    public boolean sauvegardeEstSupprimable(int sauvegarde) {
        return sauvegarde < sauvegardes.length;
    }

    public boolean peutAnnuler() {
        return jeu.peutAnnuler() &&
                (!joueursSontIA[jeu.getCoupPasse().getJoueur()] ||
                        (joueursSontIA[jeu.getCoupPasse().getJoueur()] && jeu.getNbTour() > 1));
    }

    public boolean peutRefaire() {
        return jeu.peutRefaire();
    }

    @Override
    public String toString() {
        return Integer.toString(etat);
    }
}
